package redis.clients.jedis.extension;

import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.extension.exceptions.JedisCallableException;
import redis.clients.jedis.params.SetParams;

import java.util.concurrent.Callable;


/**
 * Extension from Jedis to enforce implement lazy loading and write though approach.
 */
public class JedisPooledExtension extends JedisPooled {
    public JedisPooledExtension(final String host, final int port) {
        super(host, port);
    }
    /**
     * Get the value of the specified key. If the key does not exist it will execute command to get result. No matter the result it is, it will set to redis.
     * @param key Key
     * @param catchMissedCallable Action when not found
     * @return Bulk reply
     */
    public String get(final String key, final Callable<String> catchMissedCallable) {
        String redisReply=this.get(key);
        if (redisReply!=null)
            return redisReply;
        try {
            String commandReturn = catchMissedCallable.call();
            this.set(key, commandReturn);
            return commandReturn;
        } catch (Exception exception) {
            throw new JedisCallableException("Error occurred when execute catch miss action.", exception);
        }
    }

    /**
     * Set the command return as value of the key. The string can't be longer than 1073741824 bytes (1 GB).
     * @param key Key
     * @param setCallable Action for getting data to set in Redis
     * @param params Set parameter
     * @return Status code reply
     */
    public String set(final String key, final Callable<String> setCallable, final SetParams params) {
        try {
            String commandReturn = setCallable.call();
            return this.set(key, commandReturn, params);
        } catch (Exception exception) {
            throw new JedisCallableException("Error occurred when execute set callable.", exception);
        }
    }
}
