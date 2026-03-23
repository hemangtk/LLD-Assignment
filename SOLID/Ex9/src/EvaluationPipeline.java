public class EvaluationPipeline {
    private final PlagiarismCheckable plagiarismChecker;
    private final Gradable grader;
    private final ReportWritable reportWriter;

    public EvaluationPipeline(PlagiarismCheckable plagiarismChecker, Gradable grader, ReportWritable reportWriter) {
        this.plagiarismChecker = plagiarismChecker;
        this.grader = grader;
        this.reportWriter = reportWriter;
    }

    public void evaluate(Submission sub) {
        Rubric rubric = new Rubric();

        int plag = plagiarismChecker.check(sub);
        System.out.println("PlagiarismScore=" + plag);

        int code = grader.grade(sub, rubric);
        System.out.println("CodeScore=" + code);

        String reportName = reportWriter.write(sub, plag, code);
        System.out.println("Report written: " + reportName);

        int total = plag + code;
        String result = (total >= 90) ? "PASS" : "FAIL";
        System.out.println("FINAL: " + result + " (total=" + total + ")");
    }
}
