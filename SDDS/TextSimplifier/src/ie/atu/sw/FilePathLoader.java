package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public final class FilePathLoader {
    private FilePathLoader() {
    }

    public static String loadPath(Scanner scanner, String prompt) {
        String filePath = "";
        boolean isValidFile = false;

        while (!isValidFile) {
            ConsoleLogger.cyanBoldTitle(prompt + " (Example: ./path/fileName.txt): ", true);
            filePath = scanner.next();

            // Check if the provided file path exists
            if (filePath == null) {
                ConsoleLogger.error("Missing Embedding file.");
                continue;
            }

            if (!fileExists(filePath)) {
                ConsoleLogger.error("Invalid path.");
                continue;
            }

            isValidFile = true;
            ConsoleLogger.info("Input file path succesfully added");

        }
        return filePath;
    }

    // Checks if the specified file exists
    private static boolean fileExists(String pathString) {
        Path path = Paths.get(pathString);
        return Files.exists(path);
    }
}
