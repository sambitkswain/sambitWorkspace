package org.example;

import org.example.checker.CheckResult;
import org.example.checker.DataChecker;
import org.example.csv.CsvReader;
import org.example.model.FieldData;
import org.example.report.ReportGenerator;
import org.example.txt.TxtReader;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {

            System.out.println("========================================");
            System.out.println("          TXT - CSV CHECKER");
            System.out.println("========================================");
            System.out.println();

            // Ask folder path
            System.out.println("Please input folder path:");
            String folderInput = scanner.nextLine().trim();

            Path folderPath = Path.of(folderInput);

            // Validate folder
            if (!folderPath.toFile().exists()) {
                System.out.println();
                System.out.println("ERROR: Folder does not exist.");
                return;
            }

            if (!folderPath.toFile().isDirectory()) {
                System.out.println();
                System.out.println("ERROR: Please enter a folder path.");
                return;
            }

            /*
             * Both files are available in the same folder.
             */
            Path txtPath =
                    folderPath.resolve("csnn_src_Org.txt");

            Path csvPath =
                    folderPath.resolve("successData_CSNN.csv");

            // Validate TXT
            if (!txtPath.toFile().exists()) {
                System.out.println();
                System.out.println(
                        "ERROR: csnn_src_Org.txt does not exist."
                );
                return;
            }

            // Validate CSV
            if (!csvPath.toFile().exists()) {
                System.out.println();
                System.out.println(
                        "ERROR: successData_CSNN.csv does not exist."
                );
                return;
            }

            System.out.println();
            System.out.println("TXT file:");
            System.out.println(txtPath.toAbsolutePath());

            System.out.println();
            System.out.println("CSV file:");
            System.out.println(csvPath.toAbsolutePath());

            // ==============================
            // READ TXT
            // ==============================

            System.out.println();
            System.out.println("Reading TXT file...");

            TxtReader txtReader = new TxtReader();

            List<FieldData> txtFields =
                    txtReader.read(txtPath);

            System.out.println(
                    "TXT values found: " + txtFields.size()
            );

            // ==============================
            // READ CSV
            // ==============================

            System.out.println();
            System.out.println("Reading CSV Search_Field column...");

            CsvReader csvReader = new CsvReader();

            List<String> csvSearchFields =
                    csvReader.readSearchFields(csvPath);

            System.out.println(
                    "CSV Search_Field rows: "
                            + csvSearchFields.size()
            );

            // ==============================
            // FIND DUPLICATES
            // ==============================

            System.out.println();
            System.out.println(
                    "Checking duplicate Search_Field values..."
            );

            List<String> duplicates =
                    csvReader.findDuplicates(csvPath);

            System.out.println(
                    "Duplicate values found: "
                            + duplicates.size()
            );

            // ==============================
            // CHECK DATA
            // ==============================

            System.out.println();
            System.out.println("Checking data...");

            DataChecker dataChecker =
                    new DataChecker();

            CheckResult result =
                    dataChecker.check(
                            txtFields,
                            csvSearchFields
                    );

            /*
             * Create output files in the same
             * folder where TXT file exists.
             */
            Path outputDirectory =
                    txtPath.getParent();

            Path availablePath =
                    outputDirectory.resolve(
                            "AVAILABLE.txt"
                    );

            Path notAvailablePath =
                    outputDirectory.resolve(
                            "NOT_AVAILABLE.txt"
                    );

            Path duplicatePath =
                    outputDirectory.resolve(
                            "DUPLICATE.txt"
                    );

            // ==============================
            // GENERATE AVAILABLE /
            // NOT AVAILABLE
            // ==============================

            ReportGenerator reportGenerator =
                    new ReportGenerator();

            reportGenerator.generate(
                    result,
                    availablePath,
                    notAvailablePath
            );

            // ==============================
            // GENERATE DUPLICATE FILE
            // ==============================

            Files.write(
                    duplicatePath,
                    duplicates,
                    StandardCharsets.UTF_8
            );

            // ==============================
            // FINAL RESULT
            // ==============================

            System.out.println();
            System.out.println("========================================");
            System.out.println("             CHECK RESULT");
            System.out.println("========================================");

            System.out.println(
                    "Total TXT values : "
                            + result.getTotal()
            );

            System.out.println(
                    "Available        : "
                            + result.getAvailable().size()
            );

            System.out.println(
                    "Not Available    : "
                            + result.getNotAvailable().size()
            );

            System.out.println(
                    "Duplicates       : "
                            + duplicates.size()
            );

            System.out.println();

            System.out.println("Available file:");
            System.out.println(
                    availablePath.toAbsolutePath()
            );

            System.out.println();

            System.out.println("Not Available file:");
            System.out.println(
                    notAvailablePath.toAbsolutePath()
            );

            System.out.println();

            System.out.println("Duplicate file:");
            System.out.println(
                    duplicatePath.toAbsolutePath()
            );

            System.out.println();
            System.out.println("========================================");
            System.out.println("          CHECK COMPLETED");
            System.out.println("========================================");

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "ERROR: Unable to process files."
            );

            e.printStackTrace();

        } finally {

            scanner.close();
        }
    }
}