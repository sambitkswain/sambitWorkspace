package org.example.checker;

import org.example.model.FieldData;

import java.util.List;

public class DataChecker {

    public CheckResult check(
            List<FieldData> txtFields,
            List<String> csvSearchFields) {

        CheckResult result = new CheckResult();

        for (FieldData field : txtFields) {

            String txtValue = field.getValue();

            boolean found = false;

            for (String searchField : csvSearchFields) {

                if (searchField.contains(txtValue)) {

                    found = true;
                    break;
                }
            }

            if (found) {

                result.addAvailable(txtValue);

            } else {

                result.addNotAvailable(txtValue);
            }
        }

        return result;
    }
}