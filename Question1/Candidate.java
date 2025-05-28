package Question1;

import java.util.List;

public class Candidate {
    private String candidateId;
    private String name;
    private List<String> skills;
    private float expectedSalary;
    private int experience;

    public Candidate() {}

    public Candidate(String candidateId, String name, List<String> skills, float expectedSalary, int experience) {
        this.setCandidateId(candidateId);
        this.setName(name);
        this.setSkills(skills);
        this.setExpectedSalary(expectedSalary);
        this.setExperience(experience);
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.length() < 3) {
            throw new IllegalArgumentException("Name must contain at least 3 characters.");
        }
        this.name = name;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public float getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(float expectedSalary) {
        if (expectedSalary <= 30000) {
            throw new IllegalArgumentException("Expected salary must be greater than $30,000.");
        }
        this.expectedSalary = expectedSalary;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        if (experience < 0) {
            throw new IllegalArgumentException("Experience must be a non-negative integer.");
        }
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "candidateId='" + candidateId + '\'' +
                ", name='" + name + '\'' +
                ", skills=" + skills +
                ", expectedSalary=" + expectedSalary +
                ", experience=" + experience +
                '}';
    }
}


//Validation file 
package Question1;

public class ValidationCandidate {

    public void isValidateName(String candidateName) {
        if (candidateName == null || candidateName.trim().length() < 3) {
            throw new IllegalArgumentException("Name must contain at least 3 characters.");
        }
    }

    public void isValidateExpectedSalary(Float candidateSalary) {
        if (candidateSalary == null || candidateSalary <= 30000) {
            throw new IllegalArgumentException("Expected salary must be greater than $30,000.");
        }
    }

    public void isValidateExperience(int candidateExperience) {
        if (candidateExperience < 0) {
            throw new IllegalArgumentException("Experience must be a non-negative integer.");
        }
    }
}


//Test file 
package Question1;

import java.io.*;
import java.util.*;

public class Test {
    public static final String file_name = "candidates_data.bat";

    public static void main(String[] args) {
        List<Candidate> candidates = new ArrayList<>();
        ValidationCandidate validationCandidate = new ValidationCandidate();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Add new Candidate");

            System.out.print("Enter Candidate ID: ");
            String candidateID = scanner.nextLine();

            System.out.print("Enter Candidate Name: ");
            String candidateName = scanner.nextLine();
            validationCandidate.isValidateName(candidateName);

            System.out.print("Enter Candidate Skills (comma-separated): ");
            String skillsLine = scanner.nextLine();
            List<String> candidateSkills = new ArrayList<>();
            for (String skill : skillsLine.split(",")) {
                candidateSkills.add(skill.trim());
            }

            System.out.print("Enter Candidate Salary: ");
            Float candidateSalary = scanner.nextFloat();
            validationCandidate.isValidateExpectedSalary(candidateSalary);

            System.out.print("Enter Candidate Experience: ");
            int candidateExperience = scanner.nextInt();
            validationCandidate.isValidateExperience(candidateExperience);

            candidates.add(new Candidate(candidateID, candidateName, candidateSkills, candidateSalary, candidateExperience));

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file_name))) {
                for (Candidate c : candidates) {
                    String line = c.getCandidateId() + "|" + c.getName() + "|" +
                            String.join(",", c.getSkills()) + "|" +
                            c.getExpectedSalary() + "|" + c.getExperience();
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }
            System.out.println("Successfully saved candidate to file.");

            System.out.println("\nReading candidates from file:");
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file_name))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 5) {
                        String id = parts[0];
                        String name = parts[1];
                        List<String> skills = Arrays.asList(parts[2].split(","));
                        float salary = Float.parseFloat(parts[3]);
                        int exp = Integer.parseInt(parts[4]);

                        System.out.println("ID: " + id + ", Name: " + name + ", Skills: " + skills +
                                ", Salary: " + salary + ", Experience: " + exp);
                    }
                }
            }
            System.out.println("Successfully displayed candidates.");

            scanner.nextLine();
            System.out.print("\nEnter skill to search: ");
            String searchBySkill = scanner.nextLine().trim().toLowerCase();

            boolean found = false;
            for (Candidate c : candidates) {
                for (String skill : c.getSkills()) {
                    if (skill.toLowerCase().equals(searchBySkill)) {
                        System.out.println("Found candidate: " + c);
                        found = true;
                    }
                }
            }
            if (!found) {
                System.out.println("NOT_FOUND");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("File I/O error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}


//Question 2 thread file
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
