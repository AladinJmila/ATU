package ie.atu.sw;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Handles the output generation and file writing operations for the text
 * simplification process.
 * This class manages output file naming, creation, and content writing.
 */
public class OutputHandler {
    private static String outputPath = "./";
    private String fileName = generateFormattedDateTime() + " - simplified.txt";
    private static boolean launchFile = true;

    OutputHandler() {
    }

    /**
     * Constructor that creates an OutputHandler with a filename based on the input
     * file.
     * 
     * @param inputPath The path of the input file used to generate the output
     *                  filename
     */
    OutputHandler(String inputPath) {
        this.fileName = generateFormattedDateTime() + " - " + extractFileName(inputPath) + " - simplified.txt";
    }

    /**
     * Generates and writes the simplified text to an output file.
     * Displays a progress bar during file generation and optionally launches the
     * file.
     *
     * @param linesToPlot List of strings to write to the output file
     * @throws IOException If an I/O error occurs while writing to the file
     */
    public void generateFile(List<String> linesToPlot) throws IOException {
        var filePath = Paths.get(outputPath, fileName).toString();
        var out = new FileWriter(filePath);
        var print = new PrintWriter(out);

        // Write each line to the output file
        linesToPlot.forEach(line -> print.printf("%s%n", line));

        print.println();

        print.close();

        ConsoleLogger.info("Results file will launch automatically!");

        System.out.println(ConsoleColour.GREEN); // Change the colour of the console text
        int size = 100; // The size of the meter. 100 equates to 100%
        for (int i = 0; i < size; i++) { // The loop equates to a sequence of processing steps
            Utilities.printProgress(i + 1, size); // After each (some) steps, update the progress meter
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } // Slows things down so the animation is visible
        }

        // Launch the output file
        if (launchFile)
            Utilities.launchFile(filePath);
    }

    /**
     * Extracts the filename without extension from a given file path.
     *
     * @param path The full path of the file
     * @return The filename without extension
     */
    private String extractFileName(String path) {
        var pathParts = path.split("/");
        var fileName = pathParts[pathParts.length - 1];
        return fileName.split("\\.")[0];
    }

    /**
     * Generates a formatted date-time string for use in filenames.
     * Format: "yyyy-MM-dd HH_mm_ss"
     *
     * @return Formatted date-time string
     */
    private String generateFormattedDateTime() {
        return LocalDateTime.now().toString().replace("T", " ").split("\\.")[0].replaceAll(":", "_");
    }

    /**
     * Sets whether the output file should be automatically launched after
     * generation.
     *
     * @param shouldLaunchFile true to launch the file automatically, false
     *                         otherwise
     */
    public static void setLauchFile(boolean shouldLaunchFile) {
        launchFile = shouldLaunchFile;
    }

    /**
     * Sets the output directory path for generated files.
     *
     * @param newOutputPath The new directory path where output files will be saved
     */
    public static void setOutputPath(String newOutputPath) {
        outputPath = newOutputPath;
    }
}
