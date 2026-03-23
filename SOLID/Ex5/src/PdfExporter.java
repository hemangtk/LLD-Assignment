import java.nio.charset.StandardCharsets;

/**
 * PDF exporter.
 * Format constraint: body must be 20 characters or fewer.
 * This constraint is declared via validate(), keeping the
 * base Exporter contract intact (LSP).
 */
public class PdfExporter extends Exporter {

    private static final int MAX_BODY_LENGTH = 20;

    @Override
    protected void validate(ExportRequest req) {
        if (req.body != null && req.body.length() > MAX_BODY_LENGTH) {
            throw new IllegalArgumentException(
                    "PDF cannot handle content > " + MAX_BODY_LENGTH + " chars");
        }
    }

    @Override
    protected ExportResult doExport(ExportRequest req) {
        String fakePdf = "PDF(" + req.title + "):" + req.body;
        return new ExportResult("application/pdf",
                fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}
