public class HourlyPricingStrategy implements PricingStrategy {
    private final double hourlyRate;

    public HourlyPricingStrategy(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateFee(long entryTime, long exitTime) {
        long durationMillis = exitTime - entryTime;
        double hours = Math.ceil(durationMillis / (1000.0 * 60 * 60));
        if (hours <= 0) hours = 1;
        return hours * hourlyRate;
    }
}
