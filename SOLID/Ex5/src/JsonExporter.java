import java.nio.charset.StandardCharsets;

/**
 * JSON exporter.
 * Null-request handling is now in the base class, so this exporter
 * no longer needs its own null guard (LSP-consistent).
 */
public class JsonExporter extends Exporter {

    // No additional validation needed -- JSON accepts any non-null request.

    @Override
    protected ExportResult doExport(ExportRequest req) {
        String json = "{\"title\":\"" + escape(req.title)
                + "\",\"body\":\"" + escape(req.body) + "\"}";
        return new ExportResult("application/json",
                json.getBytes(StandardCharsets.UTF_8));
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}
