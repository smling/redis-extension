package redis.clients.jedis.extension;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.extension.commands.GetCommand;
import redis.clients.jedis.extension.commands.SetCommand;
import redis.clients.jedis.params.SetParams;

/**
 * Extension from Jedis to enforce implement lazy loading and write though approach.
 */
public class JedisExtension extends Jedis {
    /**
     * Get the value of the specified key. If the key does not exist it will execute command to get result. No matter the result it is, it will set to redis.
     * @param key
     * @param command
     * @return Bulk reply
     */
    public String get(final String key, GetCommand command) {
        String redisReply=this.get(key);
        if (redisReply!=null)
            return redisReply;
        String commandReturn=command.execute();
        this.set(key, commandReturn);
        return commandReturn;
    }

    /**
     * Set the command return as value of the key. The string can't be longer than 1073741824 bytes (1 GB).
     * @param key
     * @param command
     * @param params
     * @return Status code reply
     */
    public String set(final String key, SetCommand command, final SetParams params) {
        String commandReturn=command.execute();
        return this.set(key, commandReturn, params);
    }
}
