import java.nio.charset.StandardCharsets;

/**
 * CSV exporter.
 * Format-specific encoding: newlines and commas in the body are replaced
 * with spaces so the output remains valid CSV.  This is documented
 * format encoding behaviour, not an LSP violation.
 */
public class CsvExporter extends Exporter {

    // No additional validation needed -- CSV accepts any non-null request.

    @Override
    protected ExportResult doExport(ExportRequest req) {
        String body = req.body == null ? "" : req.body.replace("\n", " ").replace(",", " ");
        String csv = "title,body\n" + req.title + "," + body + "\n";
        return new ExportResult("text/csv",
                csv.getBytes(StandardCharsets.UTF_8));
    }
}
