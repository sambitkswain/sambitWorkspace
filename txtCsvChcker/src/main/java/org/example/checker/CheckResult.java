package org.example.checker;

import java.util.ArrayList;
import java.util.List;

public class CheckResult {

    private final List<String> available;
    private final List<String> notAvailable;

    public CheckResult() {
        this.available = new ArrayList<>();
        this.notAvailable = new ArrayList<>();
    }

    public void addAvailable(String value) {
        available.add(value);
    }

    public void addNotAvailable(String value) {
        notAvailable.add(value);
    }

    public List<String> getAvailable() {
        return available;
    }

    public List<String> getNotAvailable() {
        return notAvailable;
    }

    public int getTotal() {
        return available.size() + notAvailable.size();
    }
}