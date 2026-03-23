import java.util.*;

public class CafeteriaSystem {
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();
    private final PricingCalculator pricing = new PricingCalculator();
    private final InvoiceStore store;
    private int invoiceSeq = 1000;

    public CafeteriaSystem() {
        this.store = new FileStore();
    }

    public CafeteriaSystem(InvoiceStore store) {
        this.store = store;
    }

    public void addToMenu(MenuItem i) { menu.put(i.id, i); }

    public void checkout(String customerType, List<OrderLine> lines) {
        String invId = "INV-" + (++invoiceSeq);

        List<InvoiceLineItem> lineItems = pricing.computeLineItems(lines, menu);
        double subtotal = pricing.computeSubtotal(lineItems);

        double taxPct = TaxRules.taxPercent(customerType);
        double tax = subtotal * (taxPct / 100.0);
        double discount = DiscountRules.discountAmount(customerType, subtotal, lines.size());
        double total = subtotal + tax - discount;

        InvoiceData data = new InvoiceData(invId, lineItems, subtotal, taxPct, tax, discount, total);

        String printable = InvoiceFormatter.format(data);
        System.out.print(printable);

        store.save(invId, printable);
        System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
    }
}
