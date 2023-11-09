import java.sql.Timestamp;

public class LogsManager {

    public static void log(String log) {
        System.out.println("\u001b[38;5;15m" +log + "\u001b[0m");
    }

    public static void logInput(String log) {
        System.out.print("\u001b[38;5;15m" +log + "\u001b[0m");
    }

}
