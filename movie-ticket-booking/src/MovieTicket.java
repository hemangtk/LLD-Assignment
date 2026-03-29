import java.util.List;
import java.util.UUID;

/**
 * Represents a confirmed booking (the ticket).
 */
public class MovieTicket {
    private final String ticketId;
    private final Show show;
    private final List<String> seatIds;
    private final double totalAmount;
    private BookingStatus status;

    public MovieTicket(Show show, List<String> seatIds, double totalAmount) {
        this.ticketId = UUID.randomUUID().toString().substring(0, 8);
        this.show = show;
        this.seatIds = seatIds;
        this.totalAmount = totalAmount;
        this.status = BookingStatus.CONFIRMED;
    }

    public void cancel() { this.status = BookingStatus.CANCELLED; }

    public String getTicketId() { return ticketId; }
    public Show getShow() { return show; }
    public List<String> getSeatIds() { return seatIds; }
    public double getTotalAmount() { return totalAmount; }
    public BookingStatus getStatus() { return status; }

    @Override
    public String toString() {
        return "Ticket " + ticketId
                + " | " + show.getMovie().getTitle()
                + " | " + show.getTheatre().getName() + " " + show.getScreen().getName()
                + " | " + show.getShowTime()
                + " | Seats: " + seatIds
                + " | Rs." + totalAmount
                + " | " + status;
    }
}
