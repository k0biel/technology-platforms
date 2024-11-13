package org.example;

import java.util.*;

public class Main {
    public static void printMages(List<Mage> mages, String option) {
        if (option.equals("natural")) {
            Collections.sort(mages);
        } else if (option.equals("alternative")) {
            Collections.sort(mages, new MageComparator());
        }
        for (Mage mage : mages) {
            System.out.println(mage);
        }
    }

    public static Map<Mage, Integer> createMap(Mage mage, String option) {
        Map<Mage, Integer> result;
        if (option.equals("natural")) {
            result = new TreeMap<>();
        } else if (option.equals("alternative")) {
            result = new TreeMap<>(new MageComparator());
        } else {
            result = new HashMap<>();
        }
        result.put(mage, mage.count());
        for (Mage apprentice : mage.apprentices) {
            result.putAll(createMap(apprentice, option));
        }
        return result;
    }

    public static void main(String[] args) {
        String option = "alternative";

        Mage mage1 = new Mage("A_Mag", 20, 100, option);
        Mage mage2 = new Mage("B_Mag", 15, 150, option);
        Mage mage3 = new Mage("C_Mag", 15, 120, option);
        Mage mage4 = new Mage("D_Mag", 30, 200, option);
        Mage mage5 = new Mage("E_Mag", 25, 180, option);
        Mage mage6 = new Mage("F_Mag", 10, 90, option);
        Mage mage7 = new Mage("G_Mag", 5, 80, option);
        Mage mage8 = new Mage("H_Mag", 35, 210, option);
        Mage mage9 = new Mage("I_Mag", 40, 220, option);
        Mage mage10 = new Mage("J_Mag", 45, 230, option);

        mage1.addApprentice(mage2);
        mage1.addApprentice(mage3);
        mage2.addApprentice(mage4);
        mage2.addApprentice(mage5);
        mage3.addApprentice(mage6);
        mage3.addApprentice(mage7);
        mage4.addApprentice(mage8);
        mage5.addApprentice(mage9);
        mage6.addApprentice(mage10);

        List<Mage> mages = Arrays.asList(mage1, mage2, mage3, mage4, mage5, mage6, mage7, mage8, mage9, mage10);
        printMages(mages, option);

        System.out.println(createMap(mage1, option));
    }
}