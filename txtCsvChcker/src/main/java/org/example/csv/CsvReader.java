package org.example.csv;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

public class CsvReader {

    public List<String> readSearchFields(Path csvPath)
            throws IOException {

        List<String> searchFields = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(
                csvPath,
                StandardCharsets.UTF_8
        );

             CSVReaderHeaderAware csvReader =
                     new CSVReaderHeaderAware(reader)) {

            java.util.Map<String, String> row;

            while ((row = csvReader.readMap()) != null) {

                String searchField = row.get("Search_Field");

                if (searchField != null &&
                        !searchField.trim().isEmpty()) {

                    searchFields.add(searchField.trim());
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return searchFields;
    }
    public List<String> findDuplicates(Path csvPath)
            throws IOException, CsvValidationException {

        List<String> duplicates = new ArrayList<>();

        Set<String> seen = new HashSet<>();
        Set<String> alreadyAdded = new HashSet<>();

        try (Reader reader = Files.newBufferedReader(
                csvPath,
                StandardCharsets.UTF_8
        );
             CSVReader csvReader = new CSVReader(reader)) {

            String[] header = csvReader.readNext();

            if (header == null) {
                return duplicates;
            }

            int searchFieldIndex = -1;

            for (int i = 0; i < header.length; i++) {
                if ("Search_Field".equalsIgnoreCase(
                        header[i].trim())) {

                    searchFieldIndex = i;
                    break;
                }
            }

            if (searchFieldIndex == -1) {
                throw new IOException(
                        "Search_Field column not found."
                );
            }

            String[] row;

            while ((row = csvReader.readNext()) != null) {

                if (searchFieldIndex >= row.length) {
                    continue;
                }

                String value = row[searchFieldIndex];

                if (value == null || value.trim().isEmpty()) {
                    continue;
                }

                value = value.trim();

                if (!seen.add(value)) {

                    if (alreadyAdded.add(value)) {
                        duplicates.add(value);
                    }
                }
            }
        }

        return duplicates;
    }
}