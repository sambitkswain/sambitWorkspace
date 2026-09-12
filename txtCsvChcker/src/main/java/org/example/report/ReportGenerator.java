package org.example.report;

import org.example.checker.CheckResult;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReportGenerator {

    public void generate(
            CheckResult result,
            Path availablePath,
            Path notAvailablePath) throws IOException {

        Files.createDirectories(
                availablePath.getParent()
        );

        Files.createDirectories(
                notAvailablePath.getParent()
        );

        Files.write(
                availablePath,
                result.getAvailable(),
                StandardCharsets.UTF_8
        );

        Files.write(
                notAvailablePath,
                result.getNotAvailable(),
                StandardCharsets.UTF_8
        );
    }
}