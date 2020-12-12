package ch.feomathar.adventofcode.daysix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class CustomCustoms {

    private static final Pattern PATTERN = Pattern.compile("(.*) bag(?:s)+ contain \\d+ (.*) bag(?:s)+[,\\.]");

    public static int execute(String fileName) {
        List<GroupResult> results = parseInput(fileName);
        return results.stream().mapToInt(GroupResult::getAnsweredCount).sum();
    }


    private static List<GroupResult> parseInput(String fileName) {
        List<GroupResult> result = new ArrayList<>();
        File file = new File(fileName);
        try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static class GroupResult {
        private final List<Set<Character>> results = new ArrayList<>();

        public void addAnswers(Set<Character> answers) {
            results.add(answers);
        }

        public int getAnsweredCount(){
            Set<Character> result = results.get(0);
            for (int i = 1; i < results.size(); i++) {
                result.retainAll(results.get(i));
            }
            return result.size();
        }
    }
}
