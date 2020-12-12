package ch.feomathar.adventofcode.dayfive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryBoarding {

    public static int execute(String fileName) {
        List<SeatPosition> seats = parseInput(fileName);
        List<Integer> ids = seats.stream().map(SeatPosition::getID).sorted().collect(Collectors.toList());
        for (int i = 0; i < ids.size()-1; i++) {
            if (ids.get(i) + 1 != ids.get(i + 1)) {
                return ids.get(i) + 1;
            }
        }
        return  -1;
   }

    private static int getMaxId(List<SeatPosition> seats) {
        return seats.stream().map(SeatPosition::getID).max(Integer::compareTo).get();
    }

    private static List<SeatPosition> parseInput(String fileName) {
        List<SeatPosition> result = new ArrayList<>();
        File file = new File(fileName);
        try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                SeatPosition seat = parseSeatPosition(line);
                result.add(seat);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static SeatPosition parseSeatPosition(String line) {
        int lower = 0;
        int upper = 127;
        int left = 0;
        int right = 7;
        int i = 0;
        for (; i < 7; i++) {
            if (line.charAt(i) == 'B') {
                lower = (int) Math.ceil((double)(lower + upper) / 2);
            } else if (line.charAt(i) == 'F') {
                upper = (int) Math.floor((double)(lower + upper) / 2);
            }
        }
        for (; i < 10; i++) {
            if (line.charAt(i) == 'R') {
                left = (int) Math.ceil((double)(left + right) / 2);
            } else if (line.charAt(i) == 'L') {
                right = (int) Math.floor((double)(left + right) / 2);
            }
        }
        return new SeatPosition(lower, left);
    }

    private static class SeatPosition {
        int row;
        int seat;

        public SeatPosition(int row, int seat) {
            this.row = row;
            this.seat = seat;
        }

        public int getID(){
            return row * 8 + seat;
        }
    }
}
