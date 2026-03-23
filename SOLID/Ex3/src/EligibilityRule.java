public interface EligibilityRule {
    /** Returns a failure reason if the student fails this rule, or null if they pass. */
    String check(StudentProfile s);
}
