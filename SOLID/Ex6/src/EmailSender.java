/**
 * Sends notifications via email.
 *
 * Channel-specific encoding: body is truncated to 40 characters for email display.
 * This is a presentation concern, not a precondition tightening.
 */
public class EmailSender extends NotificationSender {
    private static final int MAX_BODY_LENGTH = 40;

    public EmailSender(AuditLog audit) { super(audit); }

    @Override
    protected void doSend(Notification n) {
        String body = n.body;
        if (body.length() > MAX_BODY_LENGTH) {
            body = body.substring(0, MAX_BODY_LENGTH);
        }
        System.out.println("EMAIL -> to=" + n.email + " subject=" + n.subject + " body=" + body);
        audit.add("email sent");
    }
}
