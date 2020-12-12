package ch.feomathar.adventofcode.daysix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.feomathar.adventofcode.ProgramError;

public class CustomCustoms {

    private CustomCustoms() {

    }

    public static int execute(String fileName) {
        List<GroupResult> results = parseInput(fileName);
        return results.stream().mapToInt(GroupResult::getAnsweredCount).sum();
    }

    private static List<GroupResult> parseInput(String fileName) {
        List<GroupResult> result = new ArrayList<>();
        File file = new File(fileName);
        GroupResult current = new GroupResult();
        try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    result.add(current);
                    current = new GroupResult();
                    continue;
                }
                Set<Character> set = new HashSet<>();
                line.chars().forEach(ch -> set.add((char) ch));
                current.addAnswers(set);
            }
        } catch (IOException e) {
            throw new ProgramError(e);
        }
        // if lastline was not blank, add last worked on item as well
        if (!current.results.isEmpty()) {
            result.add(current);
        }

        return result;
    }

    private static class GroupResult {
        private final List<Set<Character>> results = new ArrayList<>();

        public void addAnswers(Set<Character> answers) {
            results.add(answers);
        }

        public int getAnsweredCount() {
            Set<Character> result = results.get(0);
            for (int i = 1; i < results.size(); i++) {
                result.retainAll(results.get(i));
            }
            return result.size();
        }
    }
}
