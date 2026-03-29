import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Core service handling all booking operations.
 * - bookTickets: concurrent-safe via Show's synchronized bookSeats()
 * - showTheatres: filter theatres by city
 * - showMovies: all movies playing in a city
 * - cancel: refund + release seats
 * - addShow: admin operation, synchronized to prevent duplicate show IDs
 */
public class BookingService {
    private final Map<String, Theatre> theatres;       // theatreId -> Theatre
    private final Map<String, Show> shows;             // showId -> Show
    private final Map<String, Movie> movies;           // movieId -> Movie
    private final List<MovieTicket> allBookings;

    public BookingService() {
        this.theatres = new HashMap<>();
        this.shows = new HashMap<>();
        this.movies = new HashMap<>();
        this.allBookings = new ArrayList<>();
    }

    // ============ Setup (Admin) ============

    public void addMovie(Movie movie) {
        movies.put(movie.getId(), movie);
    }

    public void addTheatre(Theatre theatre) {
        theatres.put(theatre.getId(), theatre);
    }

    /** Admin adds a show — synchronized to handle concurrent admin additions. */
    public synchronized void addShow(Show show) {
        if (shows.containsKey(show.getId())) {
            System.out.println("Error: Show " + show.getId() + " already exists.");
            return;
        }
        shows.put(show.getId(), show);
        System.out.println("Show added: " + show);
    }

    // ============ APIs ============

    /** bookTickets(showId, seats) → MovieTicket */
    public MovieTicket bookTickets(String showId, List<String> seatIds) {
        Show show = shows.get(showId);
        if (show == null) {
            System.out.println("Error: Show " + showId + " not found.");
            return null;
        }

        // Concurrent-safe: Show.bookSeats() is synchronized
        boolean success = show.bookSeats(seatIds);
        if (!success) {
            System.out.println("Booking failed: one or more seats unavailable for show " + showId);
            return null;
        }

        double total = seatIds.size() * show.getPricePerSeat();
        MovieTicket ticket = new MovieTicket(show, seatIds, total);
        allBookings.add(ticket);
        System.out.println("Booking confirmed: " + ticket);
        return ticket;
    }

    /** showTheatres(city) → list of theatres in that city */
    public List<Theatre> showTheatres(String city) {
        List<Theatre> result = new ArrayList<>();
        for (Theatre theatre : theatres.values()) {
            if (theatre.getCity().equalsIgnoreCase(city)) {
                result.add(theatre);
            }
        }
        return result;
    }

    /** showMovies(city) → list of movies playing in theatres in that city */
    public List<Movie> showMovies(String city) {
        Set<String> movieIds = shows.values().stream()
                .filter(s -> s.getTheatre().getCity().equalsIgnoreCase(city))
                .map(s -> s.getMovie().getId())
                .collect(Collectors.toSet());

        List<Movie> result = new ArrayList<>();
        for (String mid : movieIds) {
            result.add(movies.get(mid));
        }
        return result;
    }

    /** Cancel a booking — releases seats and processes refund. */
    public double cancelBooking(String ticketId) {
        for (MovieTicket ticket : allBookings) {
            if (ticket.getTicketId().equals(ticketId) && ticket.getStatus() == BookingStatus.CONFIRMED) {
                ticket.getShow().releaseSeats(ticket.getSeatIds());
                ticket.cancel();
                double refund = ticket.getTotalAmount();
                System.out.println("Cancelled ticket " + ticketId + " | Refund: Rs." + refund);
                return refund;
            }
        }
        System.out.println("Error: Ticket " + ticketId + " not found or already cancelled.");
        return 0;
    }

    /** Show available seats for a given show. */
    public Set<String> getAvailableSeats(String showId) {
        Show show = shows.get(showId);
        if (show == null) return Set.of();
        return show.getAvailableSeatIds();
    }
}
