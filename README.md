# Redis Extension
Redis extension from Jedis to enable lazy loading and write though.

## Usage
Sample to get data from Redis It will add record to Redis if not found.
```java
public class test {
    public void testGet(String key) {
        String hostName = "localhost";
        int port = 6379;
        JedisPooledExtension jedis = new JedisPooledExtension(
                hostName, port);
        String response = jedis.get(key, () -> {
            // Get value from actual source.
            return "redis record to be added.";
        });
    }
}
```
Sample to set data from Redis with callback.
```java
public class test {
    public void testSet(String key) {
        String hostName = "localhost";
        int port = 6379;
        JedisPooledExtension jedis = new JedisPooledExtension(
                hostName, port);
        String statusCode = jedis.set(dummyKey, ()-> {
            dummyResponse += " from SetCommand.";
        }, new SetParams());
    }
}
```