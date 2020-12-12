package ch.feomathar.adventofcode.dayfour;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PassportProcessing {

    private static final String[] REQ_PROPS = new String[]{ "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"};

    public static int execute(String fileName) {
        List<Passport> passports = parseInput(fileName);
        int countValid = 0;
        for (Passport passport : passports) {
            if (passport.isValid()) {
                countValid++;
            }
        }
        return countValid;
    }

    private static boolean checkValid(Passport passport) {
        boolean result = true;
        for (String property : REQ_PROPS) {
            result &= passport.hasProperty(property);
        }
        return result;
    }

    private static List<Passport> parseInput(String fileName) {
        List<Passport> result = new ArrayList<>();
        File file = new File(fileName);
        try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            Passport current = new Passport();
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    result.add(current);
                    current = new Passport();
                } else {
                    String[] properties = line.split(" ");
                    for (String keyValue : properties) {
                        String[] keyVal = keyValue.split(":");
                        current.addProperty(keyVal[0], keyVal[1]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static class Passport {
        private final Map<String, String> properties = new HashMap<>();

        public void addProperty(String key, String value) {
            properties.put(key, value);
        }

        public boolean hasProperty(String key) {
            return properties.get(key) != null;
        }

        public boolean isValid() {
            boolean result = true;
            for (String property : REQ_PROPS) {
                String value = properties.get(property);
                if (value == null || value.isBlank()) {
                    return false;
                }
                switch (property) {
                    case "byr":
                        result &= Integer.parseInt(value) >= 1920 && Integer.parseInt(value) <= 2002 ;
                        break;
                    case "iyr":
                        result &= Integer.parseInt(value) >= 2010 && Integer.parseInt(value) <= 2020;
                        break;
                    case "eyr":
                        result &= Integer.parseInt(value) >= 2020 && Integer.parseInt(value) <= 2030;
                        break;
                    case "hgt":
                        if (value.length() <= 2) {
                            return false;
                        }
                        String numbers = value.substring(0, value.length() - 2);
                        int parsed = Integer.parseInt(numbers);
                        if (value.endsWith("cm")) {
                            result &= parsed >= 150 && parsed <= 193;
                        } else if (value.endsWith("in")) {
                            result &= parsed >= 59 && parsed <= 76;
                        } else{
                            return false;
                        }
                        break;
                    case "hcl":
                        result &= value.matches("#[0-9a-f]{6}");
                        break;
                    case "ecl":
                        if (!List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(value)) {
                            return false;
                        }
                        break;
                    case "pid":
                        result &= value.matches("\\d{9}");
                        break;
                }
            }
            return result;
        }
    }
}
