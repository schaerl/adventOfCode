package ch.feomathar.adventofcode.dayone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.feomathar.adventofcode.ProgramError;

public class ReportRepair {
    private ReportRepair() {

    }

    public static String execute(String inputFileName, int targetSum) {
        List<Integer> numbers = parseFile(inputFileName);
        List<Integer> selectedNumbers = findTwoNumbersThatAddUp(numbers, targetSum);
        if (selectedNumbers.isEmpty()) {
            return "";
        } else {
            System.out.println("Numbers found are " + selectedNumbers.get(0) + " and " + selectedNumbers.get(1));
            return Integer.toString(selectedNumbers.get(0) * selectedNumbers.get(1));
        }
    }

    public static String execute2(String inputFileName, int targetSum) {
        List<Integer> numbers = parseFile(inputFileName);
        List<Integer> selectedNumbers = findThreeNumbersThatAddUp(numbers, targetSum);
        if (selectedNumbers.isEmpty()) {
            return "";
        } else {
            System.out.println(
                    "Numbers found are " + selectedNumbers.get(0) + " and " + selectedNumbers.get(1) + " and "
                    + selectedNumbers.get(2));
            return Integer.toString(selectedNumbers.get(0) * selectedNumbers.get(1) * selectedNumbers.get(2));
        }
    }

    private static List<Integer> parseFile(String fileName) {
        ArrayList<Integer> result = new ArrayList<>();
        File file = new File(fileName);
        try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                int parsedNumber = Integer.parseInt(line);
                result.add(parsedNumber);
            }
        } catch (IOException e) {
            throw new ProgramError(e);
        }

        return result;
    }

    private static List<Integer> findTwoNumbersThatAddUp(List<Integer> inputs, int targetSum) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < inputs.size() - 1; i++) {
            for (int j = i + 1; j < inputs.size(); j++) {
                if (inputs.get(i) + inputs.get(j) == targetSum) {
                    result.add(inputs.get(i));
                    result.add(inputs.get(j));
                    return result;
                }
            }
        }
        return Collections.emptyList();
    }

    private static List<Integer> findThreeNumbersThatAddUp(List<Integer> inputs, int targetSum) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < inputs.size() - 2; i++) {
            for (int j = i + 1; j < inputs.size() - 1; j++) {
                for (int k = j + 1; k < inputs.size(); k++) {
                    if (inputs.get(i) + inputs.get(j) + inputs.get(k) == targetSum) {
                        result.add(inputs.get(i));
                        result.add(inputs.get(j));
                        result.add(inputs.get(k));
                        return result;
                    }
                }
            }
        }
        return Collections.emptyList();
    }

}
