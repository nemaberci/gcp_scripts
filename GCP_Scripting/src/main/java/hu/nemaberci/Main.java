package hu.nemaberci;

import java.io.IOException;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        try {
            ComputeEngineController.listAllInstances("felho-prog-378008");
            for (int i = 0; i < 10; i++) {
                var timeBeforeSend = Instant.now();
                var returnedTime = CloudFunctionsController.callFunc("https://whats-the-time-rht2bduiyq-ez.a.run.app", "idk");
                System.out.println("Millis between request and response: " + Long.valueOf(returnedTime.toEpochMilli() - timeBeforeSend.toEpochMilli()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}