package org.example;

import java.util.List;

public class WorkerThread extends Thread {
    private final SharedResource sharedResource;
    private final ResultsResource resultsResource;

    public WorkerThread(SharedResource sharedResource, ResultsResource resultsResource) {
        this.sharedResource = sharedResource;
        this.resultsResource = resultsResource;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Task task = sharedResource.getTask();
                List<Long> divisors = task.getDivisors();
                String result = "Number " + task.getNumber() + ": " + divisors;
                resultsResource.addResult(result);
                System.out.println(getName() + " done " + task.getNumber());

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}