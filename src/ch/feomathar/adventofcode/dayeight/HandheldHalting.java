package ch.feomathar.adventofcode.dayeight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.feomathar.adventofcode.ProgramError;

public class HandheldHalting {

    public static int execute(String fileName) {
        Program program = parseInput(fileName);
        return program.findFixedAccVal();
    }

    private static Program parseInput(String fileName) {
        Program result = new Program();
        File file = new File(fileName);
        try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.addLine(parseLine(line));
            }
        } catch (IOException e) {
            throw new ProgramError(e);
        }
        return result;
    }

    private static Line parseLine(String line) {
        String[] completeLine = line.split(" ");
        return new Line(OPCode.parse(completeLine[0]), Integer.parseInt(completeLine[1]));
    }

    private static class Program {
        public final List<Line> code = new ArrayList<>();

        public void addLine(Line line) {
            code.add(line);
        }

        public int findLoopingAccVal() {
            return new Execution(code).run();
        }

        public int findFixedAccVal() {
            for (int i = 0; i < code.size(); i++) {
                List<Line> newCode = new ArrayList<>(code);
                Line line = newCode.get(i);
                if (line.opCode == OPCode.JMP) {
                    newCode.set(i, new Line(OPCode.NOP, line.value));
                } else if (line.opCode == OPCode.NOP) {
                    newCode.set(i, new Line(OPCode.JMP, line.value));
                } else {
                    continue;
                }
                Execution ex = new Execution(newCode);
                int acc = ex.run();
                if (!ex.looped) {
                    return acc;
                }
            }
            return -1;
        }
    }

    private static class Execution {
        public final List<Line> code = new ArrayList<>();
        int accumulator = 0;
        int pc = 0;
        boolean[] visitedLine;
        boolean looped = true;

        public Execution(List<Line> lines) {
            code.addAll(lines);
            visitedLine = new boolean[code.size()];
        }

        public int run() {
            while (pc < code.size() && !visitedLine[pc]) {
                Line line = code.get(pc);
                visitedLine[pc] = true;
                switch (line.opCode) {
                    case NOP:
                        pc++;
                        break;
                    case JMP:
                        pc += line.value;
                        break;
                    case ACC:
                        pc++;
                        accumulator += line.value;
                        break;
                }
            }
            if (pc >= code.size()) {
                looped = false;
            }
            return accumulator;
        }
    }

    private static class Line {
        OPCode opCode;
        int value;

        public Line(OPCode opCode, int value) {
            this.opCode = opCode;
            this.value = value;
        }
    }

    private enum OPCode {
        NOP, JMP, ACC;

        static OPCode parse(String val) {
            switch (val) {
                case "nop":
                    return NOP;
                case "jmp":
                    return JMP;
                case "acc":
                    return ACC;
                default:
                    return null;
            }
        }
    }
}

