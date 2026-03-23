import java.util.Random;

public class Dice {
    private final Random random = new Random();
    private final int faces;

    public Dice(int faces) {
        this.faces = faces;
    }

    public int roll() {
        return random.nextInt(faces) + 1;
    }
}
