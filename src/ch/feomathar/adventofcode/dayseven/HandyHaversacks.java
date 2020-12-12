package ch.feomathar.adventofcode.dayseven;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandyHaversacks {
    private static final Pattern LINE_START = Pattern.compile("(.*) bags contain ");
    private static final Pattern REST_SPLIT = Pattern.compile("(\\d+) (.*) bag[s]?");

    public static int execute(String fileName) {
        ReachesMap map = parseInput(fileName);
        return map.getFillNumber("shiny gold");
    }

    private static ReachesMap parseInput(String fileName) {
        ReachesMap rMap = new ReachesMap();
        File file = new File(fileName);
        try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher m = LINE_START.matcher(line);
                m.find();
                String origin = m.group(1);
                List<ReachesMap.BagContents> reaches = new ArrayList<>();
                String rest = line.substring(m.group(0).length());
                if (rest.startsWith("no")){
                    // do nothing to reaches
                } else {
                    String[] splitUp = rest.split("[,\\.]");
                    for (String reach : splitUp) {
                        Matcher mm = REST_SPLIT.matcher(reach);
                        mm.find();
                        reaches.add(new ReachesMap.BagContents(Integer.parseInt(mm.group(1)), mm.group(2)));
                    }
                }
                rMap.addValue(origin, reaches);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rMap;
    }

    private static class ReachesMap {
        private final HashMap<String, Set<BagContents>> map = new HashMap<>();

        public void addValue(String from, List<BagContents> tos) {
            Set<BagContents> existing = map.get(from);
            if (existing == null) {
                existing = new HashSet<>();
                map.put(from, existing);
            }
            existing.addAll(tos);
        }

        // public int countOriginsOf(String target){
        //     int prevSize = 0;
        //     Set<String> validOrigins = new HashSet<>();
        //     for (Map.Entry<String, Set<BagContents>> entry : map.entrySet()) {
        //         if (entry.getValue().contains(target)) {
        //             validOrigins.add(entry.getKey());
        //         }
        //     }
        //     do {
        //         Set<String> findings = new HashSet<>();
        //         prevSize = validOrigins.size();
        //         for (String validOrigin : validOrigins) {
        //             for (Map.Entry<String, Set<BagContents>> entry : map.entrySet()) {
        //                 if (entry.getValue().contains(validOrigin)) {
        //                     findings.add(entry.getKey());
        //                 }
        //             }
        //         }
        //         validOrigins.addAll(findings);
        //     } while (validOrigins.size() != prevSize);
        //     return validOrigins.size();
        // }

        public int getFillNumber(String from) {
            Set<BagContents> original = map.get(from);
            if (original.isEmpty()) {
                return 0;
            }
            int sum = 0;
            for (BagContents bagContents : original) {
                sum += bagContents.amount * getFillNumber(bagContents.color) + bagContents.amount;
            }
            return sum;
        }

        public static class BagContents{
            int amount;
            String color;

            public BagContents(int amount, String color) {
                this.amount = amount;
                this.color = color;
            }
        }
    }
}
