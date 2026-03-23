import java.util.ArrayList;
import java.util.List;

/**
 * Sends notifications via WhatsApp.
 *
 * Requires phone to start with '+' (country code prefix).
 * This requirement is declared via validate(), not hidden inside send(),
 * so it is part of the visible contract and does not violate LSP.
 */
public class WhatsAppSender extends NotificationSender {
    public WhatsAppSender(AuditLog audit) { super(audit); }

    @Override
    protected List<String> validate(Notification n) {
        List<String> errors = new ArrayList<>();
        if (n.phone == null || !n.phone.startsWith("+")) {
            errors.add("phone must start with + and country code");
        }
        return errors;
    }

    @Override
    protected void doSend(Notification n) {
        System.out.println("WA -> to=" + n.phone + " body=" + n.body);
        audit.add("wa sent");
    }
}
