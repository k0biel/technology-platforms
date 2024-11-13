package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task {
    private final long number;

    public Task(long number) {
        this.number = number;
    }

    public long getNumber() {
        return number;
    }

    public List<Long> getDivisors() {
        List<Long> divisors = new ArrayList<>();
        for (long i = 1; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                divisors.add(i);
                if (number / i != i) {
                    divisors.add(number / i);
                }
            }
        }
        Collections.sort(divisors);
        return divisors;
    }
}