import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket socket;
    private static boolean packetLossSimulated = false;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());

            String fileName = input.readLine();
            System.out.println("Client requested file: " + fileName);

            File file = new File(fileName);

            if (file.exists()) {
                dataOut.writeUTF("FILE FOUND");

                String originalHash = HashUtil.getSHA256(fileName);
                dataOut.writeUTF(originalHash);

                FileInputStream fileInput = new FileInputStream(file);

                byte[] buffer = new byte[64];
                int bytes;
                int counter = 0;

                while ((bytes = fileInput.read(buffer)) > 0) {
                    counter++;

                    if (!packetLossSimulated && counter == 1) {
                        packetLossSimulated = true;
                        System.out.println("Simulating packet loss (connection closed)");
                        fileInput.close();
                        socket.close();
                        return;
                    }

                    dataOut.write(buffer, 0, bytes);
                }

                fileInput.close();
                System.out.println("File sent successfully");

            } else {
                dataOut.writeUTF("FILE NOT FOUND");
            }

            socket.close();

        } catch (IOException e) {
            System.out.println("Connection interrupted: " + e.getMessage());
        }
    }
}