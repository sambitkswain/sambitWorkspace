import java.io.*;
import java.nio.file.*;
import java.util.*;

public class RemoveDuplicateRows {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter CSV folder path: ");
        String folderPath = scanner.nextLine();

        try {
            Path folder = Paths.get(folderPath);

            // Find CSV file
            Path inputFile = Files.list(folder)
                    .filter(path -> path.toString().toLowerCase().endsWith(".csv"))
                    .findFirst()
                    .orElse(null);

            if (inputFile == null) {
                System.out.println("No CSV file found in the folder.");
                return;
            }

            // Output file
            Path outputFile = folder.resolve("output.csv");

            Set<String> uniqueRows = new LinkedHashSet<>();

            try (BufferedReader reader = Files.newBufferedReader(inputFile);
                 BufferedWriter writer = Files.newBufferedWriter(outputFile)) {

                String line;
                boolean firstRow = true;

                while ((line = reader.readLine()) != null) {

                    // Keep header
                    if (firstRow) {
                        writer.write(line);
                        writer.newLine();
                        firstRow = false;
                        continue;
                    }

                    // Add only unique rows
                    if (uniqueRows.add(line)) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }

            System.out.println();
            System.out.println("Input CSV  : " + inputFile);
            System.out.println("Output CSV : " + outputFile);
            System.out.println("Duplicate rows removed successfully.");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}