public class ConfirmationPrinter {
    public void printSuccess(StudentRecord rec, int totalCount) {
        System.out.println("OK: created student " + rec.id);
        System.out.println("Saved. Total students: " + totalCount);
        System.out.println("CONFIRMATION:");
        System.out.println(rec);
    }

    public void printErrors(java.util.List<String> errors) {
        System.out.println("ERROR: cannot register");
        for (String e : errors) System.out.println("- " + e);
    }
}
