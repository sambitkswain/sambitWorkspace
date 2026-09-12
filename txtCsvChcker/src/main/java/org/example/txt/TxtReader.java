package org.example.txt;

import org.example.model.FieldData;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TxtReader {

    public List<FieldData> read(Path txtPath) throws IOException {

        List<FieldData> fields = new ArrayList<>();

        List<String> lines = Files.readAllLines(
                txtPath,
                StandardCharsets.UTF_8
        );

        for (String line : lines) {

            String value = line.trim();

            if (!value.isEmpty()) {
                fields.add(new FieldData(value));
            }
        }

        return fields;
    }
}