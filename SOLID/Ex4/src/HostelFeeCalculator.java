import java.util.*;

public class HostelFeeCalculator {
    private final FakeBookingRepo repo;
    private final RoomPricingRegistry roomPricing;

    public HostelFeeCalculator(FakeBookingRepo repo) {
        this.repo = repo;
        this.roomPricing = new RoomPricingRegistry();
    }

    public HostelFeeCalculator(FakeBookingRepo repo, RoomPricingRegistry roomPricing) {
        this.repo = repo;
        this.roomPricing = roomPricing;
    }

    public void process(BookingRequest req) {
        Money monthly = calculateMonthly(req);
        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000));
        repo.save(bookingId, req, monthly, deposit);
    }

    private Money calculateMonthly(BookingRequest req) {
        Money base = roomPricing.getPrice(req.roomType);

        Money addOnTotal = new Money(0.0);
        for (AddOn a : req.addOns) {
            addOnTotal = addOnTotal.plus(a.getPrice());
        }

        return base.plus(addOnTotal);
    }
}
