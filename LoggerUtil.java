import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LoggerUtil {

    private static final String LOG_FILE = "log.txt";

    public static void log(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(LocalDateTime.now() + " - " + message);
        } catch (IOException e) {
            System.out.println("Logging failed: " + e.getMessage());
        }
    }
}