import java.util.List;
import java.util.Map;

public class OnboardingService {
    private final StudentStore store;
    private final InputParser parser;
    private final StudentValidator validator;
    private final ConfirmationPrinter printer;

    public OnboardingService(StudentStore store) {
        this.store = store;
        this.parser = new InputParser();
        this.validator = new StudentValidator();
        this.printer = new ConfirmationPrinter();
    }

    public void registerFromRawInput(String raw) {
        System.out.println("INPUT: " + raw);

        Map<String, String> kv = parser.parse(raw);

        List<String> errors = validator.validate(kv);
        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }

        String name = kv.getOrDefault("name", "");
        String email = kv.getOrDefault("email", "");
        String phone = kv.getOrDefault("phone", "");
        String program = kv.getOrDefault("program", "");

        String id = IdUtil.nextStudentId(store.count());
        StudentRecord rec = new StudentRecord(id, name, email, phone, program);

        store.save(rec);

        printer.printSuccess(rec, store.count());
    }
}
