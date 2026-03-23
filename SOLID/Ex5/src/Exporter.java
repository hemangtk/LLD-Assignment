/**
 * Base exporter contract (LSP-compliant).
 *
 * Preconditions  : req must be non-null.
 * Postconditions : returns a non-null ExportResult with a valid contentType
 *                  and non-null bytes array.
 *
 * Subclasses declare format-specific constraints via {@link #validate(ExportRequest)}.
 * The template method {@link #export(ExportRequest)} enforces the common
 * precondition (non-null request) and delegates to validate() before the
 * actual export, so callers never need instanceof checks.
 */
public abstract class Exporter {

    /**
     * Template method that enforces the base contract, then delegates.
     * Throws IllegalArgumentException if the request is null or if
     * the subclass validation fails.
     */
    public final ExportResult export(ExportRequest req) {
        if (req == null) {
            throw new IllegalArgumentException("ExportRequest must not be null");
        }
        validate(req);          // subclass-specific checks
        return doExport(req);   // subclass-specific export logic
    }

    /**
     * Validate format-specific constraints.
     * Default implementation accepts every non-null request.
     * Subclasses may override to tighten validation, but must throw
     * IllegalArgumentException (not silently alter data) when the
     * request cannot be honoured.
     */
    protected void validate(ExportRequest req) {
        // base: no additional constraints beyond non-null (checked above)
    }

    /**
     * Perform the actual export.  Called only after validation passes.
     * Subclasses must return a non-null ExportResult.
     */
    protected abstract ExportResult doExport(ExportRequest req);
}
