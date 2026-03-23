public class Player {
    private final String name;
    private int position;
    private boolean hasFinished;

    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.hasFinished = false;
    }

    public String getName() { return name; }
    public int getPosition() { return position; }
    public boolean hasFinished() { return hasFinished; }

    public void moveTo(int newPosition) {
        this.position = newPosition;
    }

    public void markFinished() {
        this.hasFinished = true;
    }
}
