package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * A utility class responsible for loading and managing file paths
 */
public final class FilePathLoader {
    private FilePathLoader() {
    }

    /**
     * Loads and validates a file path from user input.
     * Continuously prompts the user until a valid file path is provided.
     *
     * @param scanner The Scanner object used to read user input
     * @param prompt  The prompt message to display to the user
     * @return A valid file path as a String
     */
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

    /**
     * Checks if a file exists at the specified path.
     *
     * @param pathString The file path to check
     * @return true if the file exists, false otherwise
     */
    private static boolean fileExists(String pathString) {
        Path path = Paths.get(pathString);
        return Files.exists(path);
    }
}
