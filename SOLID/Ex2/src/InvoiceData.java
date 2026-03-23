import java.util.*;

public class InvoiceData {
    public final String invoiceId;
    public final List<InvoiceLineItem> lineItems;
    public final double subtotal;
    public final double taxPercent;
    public final double tax;
    public final double discount;
    public final double total;

    public InvoiceData(String invoiceId, List<InvoiceLineItem> lineItems,
                       double subtotal, double taxPercent, double tax,
                       double discount, double total) {
        this.invoiceId = invoiceId;
        this.lineItems = lineItems;
        this.subtotal = subtotal;
        this.taxPercent = taxPercent;
        this.tax = tax;
        this.discount = discount;
        this.total = total;
    }
}
