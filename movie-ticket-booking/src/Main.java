import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BookingService service = new BookingService();

        // --- Setup movies ---
        Movie m1 = new Movie("MOV1", "Pushpa 2", 160);
        Movie m2 = new Movie("MOV2", "Stree 2", 140);
        service.addMovie(m1);
        service.addMovie(m2);

        // --- Setup theatres ---
        List<Seat> screen1Seats = Arrays.asList(
                new Seat("A1"), new Seat("A2"), new Seat("A3"), new Seat("A4"),
                new Seat("B1"), new Seat("B2"), new Seat("B3"), new Seat("B4"));
        List<Seat> screen2Seats = Arrays.asList(
                new Seat("A1"), new Seat("A2"), new Seat("A3"),
                new Seat("B1"), new Seat("B2"), new Seat("B3"));

        Screen scr1 = new Screen("SCR1", "Screen 1", screen1Seats);
        Screen scr2 = new Screen("SCR2", "Screen 2", screen2Seats);
        Theatre t1 = new Theatre("TH1", "PVR Phoenix", "Bangalore", Arrays.asList(scr1, scr2));

        Screen scr3 = new Screen("SCR3", "Audi 1", screen1Seats);
        Theatre t2 = new Theatre("TH2", "INOX Forum", "Bangalore", Arrays.asList(scr3));

        Theatre t3 = new Theatre("TH3", "PVR Juhu", "Mumbai", Arrays.asList(
                new Screen("SCR4", "Screen 1", screen2Seats)));

        service.addTheatre(t1);
        service.addTheatre(t2);
        service.addTheatre(t3);

        // --- Admin adds shows ---
        System.out.println("=== Admin: Adding Shows ===");
        Show s1 = new Show("SH1", m1, scr1, t1, "2:30 PM", 250);
        Show s2 = new Show("SH2", m2, scr2, t1, "6:00 PM", 200);
        Show s3 = new Show("SH3", m1, scr3, t2, "9:00 PM", 300);
        service.addShow(s1);
        service.addShow(s2);
        service.addShow(s3);

        // --- API: showTheatres(city) ---
        System.out.println("\n=== Theatres in Bangalore ===");
        for (Theatre t : service.showTheatres("Bangalore")) {
            System.out.println("  " + t);
        }

        // --- API: showMovies(city) ---
        System.out.println("\n=== Movies in Bangalore ===");
        for (Movie m : service.showMovies("Bangalore")) {
            System.out.println("  " + m);
        }

        // --- API: bookTickets ---
        System.out.println("\n=== Booking Tickets ===");
        MovieTicket ticket1 = service.bookTickets("SH1", Arrays.asList("A1", "A2"));
        MovieTicket ticket2 = service.bookTickets("SH1", Arrays.asList("B1", "B2", "B3"));

        // --- Try booking already-taken seats ---
        System.out.println("\n=== Duplicate Seat Booking ===");
        service.bookTickets("SH1", Arrays.asList("A1", "A3")); // A1 already taken

        // --- Available seats ---
        System.out.println("\n=== Available Seats for SH1 ===");
        System.out.println("  " + service.getAvailableSeats("SH1"));

        // --- Concurrency test: two threads race to book same seats ---
        System.out.println("\n=== Concurrency Test ===");
        Show s4 = new Show("SH4", m2, scr3, t2, "3:00 PM", 200);
        service.addShow(s4);

        Thread user1 = new Thread(() -> {
            MovieTicket t_1 = service.bookTickets("SH4", Arrays.asList("A1", "A2"));
            System.out.println("  User1: " + (t_1 != null ? "booked " + t_1.getTicketId() : "FAILED"));
        });
        Thread user2 = new Thread(() -> {
            MovieTicket t_2 = service.bookTickets("SH4", Arrays.asList("A1", "A2"));
            System.out.println("  User2: " + (t_2 != null ? "booked " + t_2.getTicketId() : "FAILED"));
        });
        user1.start();
        user2.start();
        user1.join();
        user2.join();

        // --- Cancellation with refund ---
        System.out.println("\n=== Cancellation ===");
        if (ticket1 != null) {
            service.cancelBooking(ticket1.getTicketId());
            System.out.println("Seats after cancel: " + service.getAvailableSeats("SH1"));
        }

        // --- Double cancel ---
        System.out.println("\n=== Double Cancel ===");
        if (ticket1 != null) {
            service.cancelBooking(ticket1.getTicketId()); // should fail
        }
    }
}
