package redis.clients.jedis.extension.exceptions;

import redis.clients.jedis.exceptions.JedisException;

public class JedisCallableException extends JedisException {
    public JedisCallableException(String message, Exception exception) {
        super(message, exception);
    }
}
