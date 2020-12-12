package ch.feomathar.adventofcode;

import java.util.List;
import java.util.stream.Collectors;

import ch.feomathar.adventofcode.dayeight.HandheldHalting;
import ch.feomathar.adventofcode.dayeleven.SeatingSystemKt;
import ch.feomathar.adventofcode.dayfive.BinaryBoarding;
import ch.feomathar.adventofcode.dayfour.PassportProcessing;
import ch.feomathar.adventofcode.daynine.EncodingError;
import ch.feomathar.adventofcode.dayone.CaptchaKt;
import ch.feomathar.adventofcode.dayone.FrequencyKt;
import ch.feomathar.adventofcode.dayone.ReportRepair;
import ch.feomathar.adventofcode.dayone.RocketsKt;
import ch.feomathar.adventofcode.dayone.TaxicabKt;
import ch.feomathar.adventofcode.dayseven.HandyHaversacks;
import ch.feomathar.adventofcode.daysix.CustomCustoms;
import ch.feomathar.adventofcode.dayten.AdapterArray;
import ch.feomathar.adventofcode.daythree.CrossedWiresKt;
import ch.feomathar.adventofcode.daythree.InfiniteHouseKt;
import ch.feomathar.adventofcode.daythree.SlicingKt;
import ch.feomathar.adventofcode.daythree.SpiralMemoryKt;
import ch.feomathar.adventofcode.daythree.TobboganTrajectory;
import ch.feomathar.adventofcode.daythree.TrianglesKt;
import ch.feomathar.adventofcode.daytwo.BathroomKt;
import ch.feomathar.adventofcode.daytwo.CorruptionChecksumKt;
import ch.feomathar.adventofcode.daytwo.InventoryKt;
import ch.feomathar.adventofcode.daytwo.NotMathsKt;
import ch.feomathar.adventofcode.daytwo.PasswordPhilosophy;
import ch.feomathar.adventofcode.daytwo.ProgramAlarmKt;

public class Main {
    public static void main(String[] args){
        // System.out.println(ReportRepair.execute2("src/resources/dayone/2020.txt", 2020));
        // System.out.println(PasswordPhilosophy.execute("src/resources/daytwo/2020.txt"));
        // System.out.println(TobboganTrajectory.execute("src/resources/daythree/2020.txt"));
        // System.out.println(PassportProcessing.execute("src/resources/dayfour/2020.txt"));
        // System.out.println(BinaryBoarding.execute("src/resources/dayfive/2020.txt"));
        // System.out.println(CustomCustoms.execute("src/resources/daysix/2020.txt"));
        // System.out.println(HandyHaversacks.execute("src/resources/dayseven/2020.txt"));
        // System.out.println(HandheldHalting.execute("src/resources/dayeight/2020.txt"));
        // System.out.println(EncodingError.execute("src/resources/daynine/2020.txt"));
        // System.out.println(AdapterArray.execute("src/resources/dayten/2020.txt"));
        // System.out.println(NotMathsKt.execute("src/resources/daytwo/2015.txt"));
        // System.out.println(TaxicabKt.execute("src/resources/dayone/2016.txt"));
        // System.out.println(CaptchaKt.captcha("src/resources/dayone/2017.txt"));
        // System.out.println(FrequencyKt.drift("src/resources/dayone/2018.txt"));
        // System.out.println(RocketsKt.fuel("src/resources/dayone/2019.txt"));
        // System.out.println(BathroomKt.buttons("src/resources/daytwo/2016.txt"));
        // System.out.println(CorruptionChecksumKt.check("src/resources/daytwo/2017.txt"));
        // System.out.println(InventoryKt.findOneDiff("src/resources/daytwo/2018.txt"));
        // System.out.println(ProgramAlarmKt.compute("src/resources/daytwo/2019.txt"));
        // System.out.println(SeatingSystemKt.determine("src/resources/dayeleven/2020.txt"));
        // System.out.println(InfiniteHouseKt.deliver("src/resources/daythree/2015.txt"));
        // System.out.println(TrianglesKt.possible("src/resources/daythree/2016.txt"));
        // System.out.println(SpiralMemoryKt.findLoc());
        // System.out.println(SpiralMemoryKt.generateWeird());
        // System.out.println(SlicingKt.findOverlaps("src/resources/daythree/2018.txt"));
        System.out.println(CrossedWiresKt.findIntersect("src/resources/daythree/2019.txt"));
    }
}
