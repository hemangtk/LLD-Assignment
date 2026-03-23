public class ClickStrategy implements OpenCloseStrategy {
    @Override
    public void open() {
        System.out.println("Clicking the button to extend the nib.");
    }

    @Override
    public void close() {
        System.out.println("Clicking the button to retract the nib.");
    }
}
