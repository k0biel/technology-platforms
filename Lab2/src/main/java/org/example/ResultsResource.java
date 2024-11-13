package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;

public class ResultsResource {
    private final Semaphore semaphore = new Semaphore(1);
    private final String filename;

    public ResultsResource(String filename) {
        this.filename = filename;
    }

    public void addResult(String result) {
        try {
            semaphore.acquire();
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
                writer.println(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}