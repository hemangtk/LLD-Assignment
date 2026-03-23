import java.util.List;

/**
 * Base contract for all notification senders (LSP-compliant).
 *
 * Contract:
 * - send(Notification) validates first via validate(), then delivers.
 *   If validation fails, throws IllegalArgumentException and audits the failure.
 * - subject is optional: channels that don't use it (e.g. SMS) may ignore it.
 * - phone may or may not have a country-code prefix depending on channel requirements;
 *   each channel declares its requirements in validate().
 * - Body encoding (e.g. truncation) is channel-specific and documented per subtype.
 *
 * No subtype may tighten preconditions beyond what the base declares here.
 * Validation differences are expressed through the validate() hook so callers
 * can check ahead of time or let send() throw consistently.
 */
public abstract class NotificationSender {
    protected final AuditLog audit;

    protected NotificationSender(AuditLog audit) {
        this.audit = audit;
    }

    /**
     * Returns a list of validation error messages for the given notification.
     * An empty list means the notification is valid for this channel.
     * Subtypes override this to declare channel-specific requirements.
     */
    protected List<String> validate(Notification n) {
        return List.of();
    }

    /**
     * Template method: validates, then delegates to doSend().
     * If validation fails, throws IllegalArgumentException with the first error
     * and audits nothing (caller is responsible for auditing failures).
     */
    public final void send(Notification n) {
        List<String> errors = validate(n);
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.get(0));
        }
        doSend(n);
    }

    /**
     * Channel-specific sending logic. Called only after validate() passes.
     */
    protected abstract void doSend(Notification n);
}
