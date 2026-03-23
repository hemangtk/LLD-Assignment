/**
 * Sends notifications via SMS.
 *
 * SMS does not use the subject field -- this is permitted by the base contract
 * which states that subject is optional per channel.
 */
public class SmsSender extends NotificationSender {
    public SmsSender(AuditLog audit) { super(audit); }

    @Override
    protected void doSend(Notification n) {
        System.out.println("SMS -> to=" + n.phone + " body=" + n.body);
        audit.add("sms sent");
    }
}
