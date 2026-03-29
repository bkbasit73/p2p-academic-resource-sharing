import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;

public class PeerClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;
        String requestedFile = "sample.txt";
        String downloadFolder = "downloads";
        int maxRetries = 3;

        int attempt = 0;
        boolean success = false;

        while (attempt < maxRetries && !success) {
            attempt++;
            System.out.println("Download attempt #" + attempt);
            LoggerUtil.log("Download attempt #" + attempt + " started for file: " + requestedFile);

            try (Socket socket = new Socket(host, port)) {
                System.out.println("Connected to server!");
                LoggerUtil.log("Connected to server at " + host + ":" + port);

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                dos.writeUTF(requestedFile);

                String response = dis.readUTF();

                if (response.equals("FOUND")) {
                    long fileSize = dis.readLong();
                    String serverHash = dis.readUTF();

                    File outFile = new File(downloadFolder, requestedFile);

                    try (FileOutputStream fos = new FileOutputStream(outFile)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        long remaining = fileSize;

                        while (remaining > 0 &&
                               (bytesRead = dis.read(buffer, 0, (int) Math.min(buffer.length, remaining))) != -1) {
                            fos.write(buffer, 0, bytesRead);
                            remaining -= bytesRead;
                        }

                        if (remaining > 0) {
                            throw new IOException("Transfer incomplete. Retrying...");
                        }
                    }

                    System.out.println("File downloaded successfully: " + outFile.getAbsolutePath());
                    LoggerUtil.log("File downloaded successfully: " + requestedFile);

                    String clientHash = HashUtil.calculateSHA256(outFile.getPath());

                    System.out.println("Server SHA-256: " + serverHash);
                    System.out.println("Client SHA-256: " + clientHash);
                    LoggerUtil.log("Server SHA-256: " + serverHash);
                    LoggerUtil.log("Client SHA-256: " + clientHash);

                    if (serverHash.equals(clientHash)) {
                        System.out.println("Integrity check passed: file is valid.");
                        LoggerUtil.log("Integrity check passed for file: " + requestedFile);
                        success = true;
                    } else {
                        System.out.println("Integrity check failed. Retrying...");
                        LoggerUtil.log("Integrity check failed for file: " + requestedFile);
                    }

                } else {
                    System.out.println("File not found on server.");
                    LoggerUtil.log("File not found on server: " + requestedFile);
                    break;
                }

            } catch (IOException | NoSuchAlgorithmException e) {
                System.out.println("Attempt failed: " + e.getMessage());
                LoggerUtil.log("Download attempt failed: " + e.getMessage());
            }
        }

        if (!success) {
            System.out.println("Download failed after " + maxRetries + " attempts.");
            LoggerUtil.log("Download failed after " + maxRetries + " attempts for file: " + requestedFile);
        }
    }
}