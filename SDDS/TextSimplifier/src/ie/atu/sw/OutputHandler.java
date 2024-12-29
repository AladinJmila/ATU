package ie.atu.sw;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

public class OutputHandler {
    private String outputPath = "./";
    private String fileName = generateFormattedDateTime() + " - simplified.txt";

    OutputHandler() {
    }

    OutputHandler(String inputPath) {
        this.fileName = generateFormattedDateTime() + " - " + extractFileName(inputPath) + " - simplified.txt";
    }

    OutputHandler(String outputPath, String inputPath) {
        this.outputPath = outputPath;
        this.fileName = generateFormattedDateTime() + " - " + extractFileName(inputPath) + " - simplified.txt";
    }

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
        Utilities.launchFile(filePath);
    }

    private String extractFileName(String path) {
        var pathParts = path.split("/");
        var fileName = pathParts[pathParts.length - 1];
        return fileName.split("\\.")[0];
    }

    private String generateFormattedDateTime() {
        return LocalDateTime.now().toString().replace("T", " ").split("\\.")[0].replaceAll(":", "_");
    }
}
