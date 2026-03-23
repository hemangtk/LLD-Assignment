public class Pen {
    private Refill refill;
    private boolean isOpen;
    private final WriteStrategy writeStrategy;
    private final OpenCloseStrategy openCloseStrategy;

    public Pen(Refill refill, WriteStrategy writeStrategy, OpenCloseStrategy openCloseStrategy) {
        this.refill = refill;
        this.writeStrategy = writeStrategy;
        this.openCloseStrategy = openCloseStrategy;
        this.isOpen = false;
    }

    public void start() {
        if (isOpen) {
            System.out.println("Pen is already open.");
            return;
        }
        openCloseStrategy.open();
        this.isOpen = true;
    }

    public void close() {
        if (!isOpen) {
            System.out.println("Pen is already closed.");
            return;
        }
        openCloseStrategy.close();
        this.isOpen = false;
    }

    public void write() {
        if (!isOpen) {
            throw new IllegalStateException("Cannot write! Pen is closed. Call start() first.");
        }
        System.out.print("[" + refill.getColor() + "] ");
        writeStrategy.write();
    }

    public void refill(Refill newRefill) {
        if (isOpen) {
            throw new IllegalStateException("Cannot refill while pen is open. Call close() first.");
        }
        this.refill = newRefill;
        System.out.println("Refilled pen with " + newRefill.getColor() + " " + newRefill.getType() + " refill.");
    }

    public InkColor getColor() { return refill.getColor(); }
    public boolean isOpen() { return isOpen; }
}
