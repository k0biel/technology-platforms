package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        SharedResource sharedResource = new SharedResource();
        ResultsResource resultsResource = new ResultsResource("output.txt");

        int numberOfThreads = Integer.parseInt(args[0]);
        sharedResource.loadTasksFromFile("input2.txt");

        for (int i = 0; i < numberOfThreads; i++) {
            WorkerThread workerThread = new WorkerThread(sharedResource, resultsResource);
            workerThread.start();
        }

        Thread inputThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String input = scanner.next();

                if ("exit".equalsIgnoreCase(input)) {
                    sharedResource.clearTasks();
                    System.exit(0);
                }

                long number = Long.parseLong(input);
                Task task = new Task(number);
                sharedResource.addTask(task);
            }
        });

        inputThread.start();
    }
}