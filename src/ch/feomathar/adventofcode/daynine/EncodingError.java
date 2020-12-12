package ch.feomathar.adventofcode.daynine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EncodingError {


    public static long execute(String fileName) {
        List<Long> numbers = parseInput(fileName);
        LinkedList<Long> rollingWindow = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            rollingWindow.add(numbers.get(i));
        }
        int index  = 0;
        for (int i = 25; i < numbers.size(); i++) {
            if (!possibleSum(rollingWindow, numbers.get(i))) {
                index = i;
                break;
            }
            rollingWindow.pop();
            rollingWindow.add(numbers.get(i));
        }
        List<Long> test = contiguousRange(index, numbers);
        test.sort(Long::compareTo);
        return test.get(0) + test.get(test.size()-1);
    }

    private static List<Long> contiguousRange(int index, List<Long> numbers) {
        for (int i = 0; i < index; i++) {
            List<Long> checked = new ArrayList<>();
            int sum = 0;
            for (int j = i; j < index; j++) {
                sum += numbers.get(j);
                checked.add(numbers.get(j));
                if (sum == numbers.get(index)) {
                    return checked;
                } else if (sum > numbers.get(index)) {
                    break;
                }
            }
        }
        return Collections.emptyList();
    }

    private static boolean possibleSum(LinkedList<Long> summands, long sum) {
        for (int i = 0; i < summands.size() - 1; i++) {
            for (int j = i; j < summands.size(); j++) {
                if (summands.get(i) + summands.get(j) == sum) {
                    // System.out.printf("%s + %s = %s\n", summands.get(i), summands.get(j), sum);
                    return true;
                }
            }
        }
        return false;
    }

    private static List<Long> parseInput(String fileName) {
        List<Long> result = new ArrayList<>();
        File file = new File(fileName);
        try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(Long.parseLong(line));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

