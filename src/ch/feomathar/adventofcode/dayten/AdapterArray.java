package ch.feomathar.adventofcode.dayten;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AdapterArray {

    public static long execute(String fileName) {
        List<Integer> numbers = parseInput(fileName);
        numbers.add(0);
        int output = numbers.stream().max(Integer::compareTo).get() + 3;
        numbers.add(output);

        Map<Integer, List<Integer>> validOptions = getValidAdapters(numbers);
        Map<Integer, Long> pathsFromNodeToGoal = new HashMap<>();
        numbers.sort(Integer::compareTo);
        Collections.reverse(numbers);
        for (Integer node : numbers) {
            if (node == output){
                pathsFromNodeToGoal.put(node, 1L);
            } else{
                pathsFromNodeToGoal.put(node, 0L);
            }
        }
        for (Integer node : numbers) {
            if (node == output) {
                continue;
            }
            long paths = 0;
            for (Integer possibleAdapters : validOptions.get(node)) {
                paths += pathsFromNodeToGoal.get(possibleAdapters);
            }
            pathsFromNodeToGoal.put(node, paths);
        }
        return pathsFromNodeToGoal.get(0);
    }

    private static Map<Integer, List<Integer>> getValidAdapters(List<Integer> numbers) {
        numbers.sort(Integer::compareTo);
        Map<Integer, List<Integer>> result = new HashMap<>();
        for (int i = 0; i < numbers.size(); i++) {
            int origin = numbers.get(i);
            List<Integer> valid = new ArrayList<>();
            for (int j = i+1; j <= i + 3 && j < numbers.size(); j++) {
                Integer dest = numbers.get(j);
                if (dest > origin + 3){
                    break;
                } else{
                    valid.add(dest);
                }
            }
            result.put(origin, valid);
        }
        return result;
    }


    private static List<Integer> parseInput(String fileName) {
        List<Integer> result = new ArrayList<>();
        File file = new File(fileName);
        try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(Integer.parseInt(line));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

