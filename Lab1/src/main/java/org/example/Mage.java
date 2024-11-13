package org.example;

import java.util.*;

public class Mage implements Comparable<Mage> {
    private String name;
    private int level;
    private double power;
    public Set<Mage> apprentices;

    public Mage(String name, int level, double power, String option) {
        this.name = name;
        this.level = level;
        this.power = power;
        if (option.equals("natural")) {
            this.apprentices = new TreeSet<>();
        } else if (option.equals("alternative")) {
            this.apprentices = new TreeSet<>(new MageComparator());
        } else {
            this.apprentices = new HashSet<>();
        }
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public double getPower() {
        return power;
    }

    public void addApprentice(Mage apprentice) {
        apprentices.add(apprentice);
    }

    @Override
    public String toString() {
        return toString(0);
    }

    private String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("-");
        }
        sb.append(String.format("Mage{name='" + name + "', level=" + level + ", power=" + power + "}\n"));
        for (Mage apprentice : apprentices) {
            sb.append(apprentice.toString(depth + 1));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Mage mage = (Mage) obj;
        return level == mage.level && Double.compare(mage.power, power) == 0 && Objects.equals(name, mage.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, power);
    }

    @Override
    public int compareTo(Mage other) {
        if (this.name.equals(other.name)) {
            if (this.level == other.level) {
                return Double.compare(this.power, other.power);
            } else {
                return Integer.compare(this.level, other.level);
            }
        } else {
            return this.name.compareTo(other.name);
        }
    }

    public int count() {
        int result = 1;
        for (Mage apprentice : apprentices) {
            result += apprentice.count();
        }
        return result;
    }
}