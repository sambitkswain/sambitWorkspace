package org.sambit.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SkillExtractor {

    public Map<String, List<String>> skillBySection(File file) {

        Map<String, List<String>> skillMap = new LinkedHashMap<>();

        try (PDFDocument document = Loader.loadPDF(file)) {

            PDFTextStripper pdfStripper = new PDFTextStripper();
            String resumeText = pdfStripper.getText(document);

            String[] lines = resumeText.split("\\r?\\n");

            for (String line : lines) {

                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                /*
                 * Expected formats:
                 *
                 * Programming Languages: Java, C, Python
                 * Frameworks: React, Spring Boot, Node.js
                 * Databases: MySQL, PostgreSQL
                 */

                if (line.contains(":")) {

                    String[] parts = line.split(":", 2);

                    String skillType = parts[0].trim();
                    String skillsText = parts[1].trim();

                    if (!skillsText.isEmpty()) {

                        String[] skills = skillsText.split(",");

                        List<String> skillList =
                                new ArrayList<>();

                        for (String skill : skills) {

                            skill = skill.trim();

                            if (!skill.isEmpty()) {
                                skillList.add(skill);
                            }
                        }

                        if (!skillList.isEmpty()) {
                            skillMap.put(skillType, skillList);
                        }
                    }
                }
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Error while extracting skills from resume PDF",
                    e
            );
        }

        return skillMap;
    }


    public String getDataIntoJson(
            Map<String, List<String>> skills) {

        try {

            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(skills);

        } catch (JsonProcessingException e) {

            throw new RuntimeException(
                    "Error while converting skills to JSON",
                    e
            );
        }
    }
}