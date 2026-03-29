import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PeerServer {

    public static void main(String[] args) {
        int port = 5000;
        String sharedFolder = "shared_files";

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for connections...");
            LoggerUtil.log("Server started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                LoggerUtil.log("Client connected from " + socket.getInetAddress());

                new Thread(new ClientHandler(socket, sharedFolder)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
            LoggerUtil.log("Server error: " + e.getMessage());
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private String sharedFolder;
    private Random random = new Random();

    public ClientHandler(Socket socket, String sharedFolder) {
        this.socket = socket;
        this.sharedFolder = sharedFolder;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String requestedFile = dis.readUTF();
            System.out.println("Client requested file: " + requestedFile);
            LoggerUtil.log("Requested file: " + requestedFile);

            File file = new File(sharedFolder, requestedFile);

            if (file.exists() && file.isFile()) {
                dos.writeUTF("FOUND");
                dos.writeLong(file.length());

                String hash = HashUtil.calculateSHA256(file.getPath());
                dos.writeUTF(hash);

                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    long totalSent = 0;
                    long failAfter = file.length() / 2;

                    boolean simulateFailure = random.nextBoolean();

                    while ((bytesRead = fis.read(buffer)) != -1) {
                        dos.write(buffer, 0, bytesRead);
                        totalSent += bytesRead;

                        if (simulateFailure && totalSent >= failAfter) {
                            System.out.println("Simulated transfer interruption.");
                            LoggerUtil.log("Transfer interrupted for file: " + requestedFile);
                            socket.close();
                            return;
                        }
                    }
                }

                System.out.println("File sent successfully.");
                System.out.println("SHA-256: " + hash);
                LoggerUtil.log("File sent successfully: " + requestedFile);
                LoggerUtil.log("SHA-256: " + hash);

            } else {
                dos.writeUTF("NOT_FOUND");
                System.out.println("Requested file not found.");
                LoggerUtil.log("File not found: " + requestedFile);
            }

            socket.close();
            LoggerUtil.log("Connection closed for file request: " + requestedFile);

        } catch (IOException | NoSuchAlgorithmException e) {
            System.out.println("Connection interrupted during transfer.");
            LoggerUtil.log("Connection interrupted/error: " + e.getMessage());
        }
    }
}