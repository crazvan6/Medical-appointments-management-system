package packages.Services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CSVLogger {
    private static final String CSV_FILE_PATH = "actions_log.csv";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static CSVLogger instance;

    private CSVLogger() {}

    public static synchronized CSVLogger getInstance() {
        if (instance == null) {
            instance = new CSVLogger();
        }
        return instance;
    }

    public void logAction(String actionName) {
        String timestamp = dtf.format(LocalDateTime.now());
        try (BufferedWriter csvWriter = new BufferedWriter(new FileWriter(CSV_FILE_PATH, true))) {
            csvWriter.write("Operatie: " + actionName + "  Data: " + timestamp);
            csvWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
