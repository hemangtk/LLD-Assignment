/**
 * Database interface — abstraction over the backing store.
 * On a cache miss, the cache fetches from the database.
 */
public interface Database {
    String fetch(String key);
    void store(String key, String value);
}
