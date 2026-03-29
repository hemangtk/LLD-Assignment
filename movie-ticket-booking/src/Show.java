import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A specific screening of a movie on a screen at a given time.
 * Tracks which seats are booked. All seat operations are synchronized
 * to handle concurrent booking.
 */
public class Show {
    private final String id;
    private final Movie movie;
    private final Screen screen;
    private final Theatre theatre;
    private final String showTime;    // e.g., "2:30 PM"
    private final double pricePerSeat;
    private final Set<String> bookedSeatIds;

    public Show(String id, Movie movie, Screen screen, Theatre theatre,
                String showTime, double pricePerSeat) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.theatre = theatre;
        this.showTime = showTime;
        this.pricePerSeat = pricePerSeat;
        this.bookedSeatIds = new HashSet<>();
    }

    /**
     * Attempts to lock the requested seats atomically.
     * Returns true only if ALL seats are available and now booked.
     */
    public synchronized boolean bookSeats(List<String> seatIds) {
        for (String seatId : seatIds) {
            if (bookedSeatIds.contains(seatId)) {
                return false; // at least one seat already taken
            }
        }
        bookedSeatIds.addAll(seatIds);
        return true;
    }

    /** Releases seats back (used during cancellation). */
    public synchronized void releaseSeats(List<String> seatIds) {
        bookedSeatIds.removeAll(seatIds);
    }

    public synchronized Set<String> getAvailableSeatIds() {
        Set<String> available = new HashSet<>();
        for (Seat seat : screen.getSeats()) {
            if (!bookedSeatIds.contains(seat.getSeatId())) {
                available.add(seat.getSeatId());
            }
        }
        return available;
    }

    public String getId() { return id; }
    public Movie getMovie() { return movie; }
    public Screen getScreen() { return screen; }
    public Theatre getTheatre() { return theatre; }
    public String getShowTime() { return showTime; }
    public double getPricePerSeat() { return pricePerSeat; }

    @Override
    public String toString() {
        return movie.getTitle() + " @ " + theatre.getName()
                + " " + screen.getName() + " | " + showTime
                + " | Rs." + pricePerSeat + "/seat";
    }
}
