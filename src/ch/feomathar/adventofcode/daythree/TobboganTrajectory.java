package ch.feomathar.adventofcode.daythree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.feomathar.adventofcode.ProgramError;

public class TobboganTrajectory {
    private TobboganTrajectory() {

    }

    private static final int[][] INPUTS = new int[][] { new int[] { 1, 1 }, new int[] { 3, 1 }, new int[] { 5, 1 },
                                                        new int[] { 7, 1 }, new int[] { 1, 2 } };

    public static int execute(String fileName) {
        Map map = parseInput(fileName);
        List<Integer> treeCounts = new ArrayList<>();
        for (int[] input : INPUTS) {
            treeCounts.add(countTrees(map, input[0], input[1]));
        }
        return treeCounts.stream().reduce(1, (a, b) -> a * b);
    }

    private static int countTrees(Map map, int horizShift, int vertShift) {
        int treeCount = 0;
        int horizontalPosition = 0;
        for (int i = 0; i < map.tiles.size(); i += vertShift) {
            if (map.getTileAtHorizontalAndVertical(horizontalPosition, i) == Map.Terrain.TREE) {
                treeCount++;
            }
            horizontalPosition += horizShift;
        }
        return treeCount;
    }

    private static Map parseInput(String fileName) {
        Map result = new Map();
        File file = new File(fileName);
        try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.extendMapBelow(Map.parseToTerrain(line));
            }
        } catch (IOException e) {
            throw new ProgramError(e);
        }

        return result;
    }

    private static class Map {
        // A vertical list of horizontal lines
        private List<List<Terrain>> tiles = new ArrayList<>();

        public void extendMapBelow(List<Terrain> newLine) {
            tiles.add(newLine);
        }

        public Terrain getTileAtHorizontalAndVertical(int horizontal, int vertical) {
            return tiles.get(vertical).get(horizontal % getWidth());
        }

        public static List<Terrain> parseToTerrain(String coded) {
            List<Terrain> newLine = new ArrayList<>();
            for (char c : coded.toCharArray()) {
                if (c == '.') {
                    newLine.add(Terrain.FREE);
                } else if (c == '#') {
                    newLine.add(Terrain.TREE);
                } else {
                    throw new ProgramError("INVALID TERRAIN CHAR");
                }
            }
            return newLine;
        }

        public int getWidth() {
            return tiles.get(0).size();
        }

        public enum Terrain {
            TREE, FREE
        }
    }
}
