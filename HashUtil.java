import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

<<<<<<< HEAD
    public static String getSHA256(String fileName) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            FileInputStream fileInput = new FileInputStream(fileName);

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fileInput.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            fileInput.close();

            byte[] hashBytes = digest.digest();
            StringBuilder hashString = new StringBuilder();

            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }

            return hashString.toString();

        } catch (IOException | NoSuchAlgorithmException e) {
            return null;
        }
=======
    public static String calculateSHA256(String filePath) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] byteArray = new byte[4096];
            int bytesRead;

            while ((bytesRead = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesRead);
            }
        }

        byte[] hashBytes = digest.digest();
        StringBuilder sb = new StringBuilder();

        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
>>>>>>> da39d8bf64658d2dcb238a34490eac57a93fa00c
    }
}