import java.util.*;

public class RoomPricingRegistry {
    private final Map<Integer, Money> prices = new HashMap<>();

    public RoomPricingRegistry() {
        register(LegacyRoomTypes.SINGLE, new Money(14000.0));
        register(LegacyRoomTypes.DOUBLE, new Money(15000.0));
        register(LegacyRoomTypes.TRIPLE, new Money(12000.0));
        register(LegacyRoomTypes.DELUXE, new Money(16000.0));
    }

    public void register(int roomType, Money price) {
        prices.put(roomType, price);
    }

    public Money getPrice(int roomType) {
        Money price = prices.get(roomType);
        if (price == null) {
            throw new IllegalArgumentException("Unknown room type: " + roomType);
        }
        return price;
    }
}
