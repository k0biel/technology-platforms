package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class SharedResource {
    private final Queue<Task> tasks = new LinkedList<>();

    public synchronized void addTask(Task task) {
        tasks.add(task);
        notifyAll();
    }

    public synchronized Task getTask() throws InterruptedException {
        while (tasks.isEmpty()) {
            wait();
        }
        return tasks.poll();
    }

    public synchronized void clearTasks() {
        tasks.clear();
    }

    public void loadTasksFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    long number = Long.parseLong(line);
                    addTask(new Task(number));
                }
            }
        }
    }
}