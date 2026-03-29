public class Seat {
    private final String seatId; // e.g., "A1", "B5"

    public Seat(String seatId) {
        this.seatId = seatId;
    }

    public String getSeatId() { return seatId; }

    @Override
    public String toString() { return seatId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat)) return false;
        return seatId.equals(((Seat) o).seatId);
    }

    @Override
    public int hashCode() { return seatId.hashCode(); }
}
