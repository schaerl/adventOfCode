package ch.feomathar.adventofcode.daytwo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordPhilosophy {
    private static final Pattern linePattern = Pattern.compile("(\\d+)-(\\d+) (\\w): (.*)");

    public static int execute(String inputFileName) {
        List<PWAndRestriction> pws = parseFile(inputFileName);
        int valid = 0;
        for (PWAndRestriction pw : pws) {
            if (checkValidity2(pw)) {
                valid++;
            }
        }
        return valid;
    }

    private static boolean checkValidity(PWAndRestriction pw) {
        long charOccur = pw.pw.chars().filter(c -> c == pw.character).count();
        return charOccur >= pw.min && charOccur <= pw.max;
    }

    private static boolean checkValidity2(PWAndRestriction pw) {
        return pw.pw.charAt(pw.min-1) == pw.character ^ pw.pw.charAt(pw.max-1) == pw.character;
    }

    private static List<PWAndRestriction> parseFile(String fileName) {
        ArrayList<PWAndRestriction> result = new ArrayList<>();
        File file = new File(fileName);
        try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher m = linePattern.matcher(line);
                m.find();
                result.add(new PWAndRestriction(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
                        m.group(3).charAt(0), m.group(4)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class PWAndRestriction {
        public int min;
        public int max;
        public char character;
        public String pw;

        public PWAndRestriction(int min, int max, char character, String pw) {
            this.min = min;
            this.max = max;
            this.character = character;
            this.pw = pw;
        }
    }
}
