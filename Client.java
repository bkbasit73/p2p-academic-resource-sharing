import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        String host = "localhost";
        int port = 6000;
        String fileName = "notes.txt";
        String receivedFileName = "received_notes.txt";

        int maxRetries = 3;
        int attempt = 0;

        while (attempt < maxRetries) {

            try {
                Socket socket = new Socket(host, port);
                System.out.println("Connected to server (Attempt " + (attempt + 1) + ")");

                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                DataInputStream dataIn = new DataInputStream(socket.getInputStream());

                output.println(fileName);

                String response = dataIn.readUTF();

                if (response.equals("FILE FOUND")) {

                    String serverHash = dataIn.readUTF();

                    FileOutputStream fileOutput = new FileOutputStream(receivedFileName);

                    byte[] buffer = new byte[4096];
                    int bytes;

                    while ((bytes = dataIn.read(buffer)) > 0) {
                        fileOutput.write(buffer, 0, bytes);
                    }

                    fileOutput.close();
                    socket.close();

                    String clientHash = HashUtil.getSHA256(receivedFileName);

                    if (serverHash != null && serverHash.equals(clientHash)) {
                        System.out.println("File received successfully");
                        System.out.println("Integrity check passed");
                        break;
                    } else {
                        System.out.println("File corrupted, retrying...");
                        attempt++;
                    }

                } else {
                    System.out.println("File not found on server");
                    break;
                }

            } catch (IOException e) {
                System.out.println("Connection failed, retrying...");
                attempt++;
            }
        }

        if (attempt == maxRetries) {
            System.out.println("Download failed after multiple attempts");
        }
    }
}