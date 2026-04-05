import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase implements Database {
    private final Map<String, String> data = new HashMap<>();

    @Override
    public String fetch(String key) {
        return data.get(key);
    }

    @Override
    public void store(String key, String value) {
        data.put(key, value);
    }

    public void seed(String key, String value) {
        data.put(key, value);
    }
}
