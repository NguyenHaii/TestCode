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
