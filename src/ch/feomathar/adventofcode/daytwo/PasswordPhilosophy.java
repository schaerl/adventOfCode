package ch.feomathar.adventofcode.daytwo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.feomathar.adventofcode.ProgramError;

public class PasswordPhilosophy {
    private PasswordPhilosophy() {
        
    }

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
        long charOccur = pw.getPw().chars().filter(c -> c == pw.getCharacter()).count();
        return charOccur >= pw.getMin() && charOccur <= pw.getMax();
    }

    private static boolean checkValidity2(PWAndRestriction pw) {
        return pw.getPw().charAt(pw.getMin() - 1) == pw.getCharacter()
               ^ pw.getPw().charAt(pw.getMax() - 1) == pw.getCharacter();
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
        } catch (IOException e) {
            throw new ProgramError(e);
        }
        return result;
    }

    private static class PWAndRestriction {
        private int min;
        private int max;
        private char character;
        private String pw;

        public PWAndRestriction(int min, int max, char character, String pw) {
            this.setMin(min);
            this.setMax(max);
            this.setCharacter(character);
            this.setPw(pw);
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public char getCharacter() {
            return character;
        }

        public void setCharacter(char character) {
            this.character = character;
        }

        public String getPw() {
            return pw;
        }

        public void setPw(String pw) {
            this.pw = pw;
        }
    }
}
