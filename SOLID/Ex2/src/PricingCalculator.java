import java.util.*;

public class PricingCalculator {

    public List<InvoiceLineItem> computeLineItems(List<OrderLine> lines, Map<String, MenuItem> menu) {
        List<InvoiceLineItem> items = new ArrayList<>();
        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            double lineTotal = item.price * l.qty;
            items.add(new InvoiceLineItem(item.name, l.qty, lineTotal));
        }
        return items;
    }

    public double computeSubtotal(List<InvoiceLineItem> items) {
        double subtotal = 0.0;
        for (InvoiceLineItem item : items) {
            subtotal += item.lineTotal;
        }
        return subtotal;
    }
}
