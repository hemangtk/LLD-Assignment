public class Main {
    public static void main(String[] args) {
        // Create a blue gel pen with click mechanism
        Pen gelPen = PenFactory.createPen(PenType.GEL, InkColor.BLUE, MechanismType.CLICK);

        // Create a black fountain pen with cap mechanism
        Pen fountainPen = PenFactory.createPen(PenType.FOUNTAIN, InkColor.BLACK, MechanismType.CAP);

        // Try writing without start() — should throw
        try {
            gelPen.write();
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();

        // Gel pen: start -> write -> close
        gelPen.start();
        gelPen.write();
        gelPen.close();

        System.out.println("\n-------------------\n");

        // Fountain pen: start -> write -> close -> refill -> start -> write -> close
        fountainPen.start();
        fountainPen.write();
        fountainPen.close();
        fountainPen.refill(new Refill(InkColor.RED, RefillType.BOTTLE));
        fountainPen.start();
        fountainPen.write();
        fountainPen.close();

        System.out.println("\n=== Edge Case Tests ===");

        // Double close
        fountainPen.close();

        // Double start
        gelPen.start();
        gelPen.start();
        gelPen.close();

        // Refill while open
        try {
            gelPen.start();
            gelPen.refill(new Refill(InkColor.GREEN, RefillType.TUBE));
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
            gelPen.close();
        }
    }
}
