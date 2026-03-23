public interface PricingStrategy {
    double calculateFee(long entryTime, long exitTime);
}
