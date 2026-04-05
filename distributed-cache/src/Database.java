public interface Database {
    String fetch(String key);
    void store(String key, String value);
}
