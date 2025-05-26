package Question2;

import java.util.*;

public class MultiThreading {
    static Map<String, String> skillMap = Map.of(
            "Java", "Java Developer needed with Spring Boot experience.",
            "Python", "Python Developer needed for AI project.",
            "React", "Frontend React Developer required.",
            "SQL", "Database expert needed with SQL proficiency."
    );

    static String sharedSkill = null;

    public static void main(String[] args) {
        Object lock = new Object();

        Thread generatorThread = new Thread(() -> {
            synchronized (lock) {
                try {
                    List<String> keys = new ArrayList<>(skillMap.keySet());
                    Random rand = new Random();
                    Thread.sleep(1000);
                    sharedSkill = keys.get(rand.nextInt(keys.size()));
                    System.out.println("Generated Skill: " + sharedSkill);
                    lock.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread descriptionThread = new Thread(() -> {
            synchronized (lock) {
                try {
                    while (sharedSkill == null) {
                        lock.wait();
                    }
                    String desc = skillMap.get(sharedSkill);
                    System.out.println("Job Description: " + desc);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        generatorThread.start();
        descriptionThread.start();
    }
}
