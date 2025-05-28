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

