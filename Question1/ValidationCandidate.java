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
