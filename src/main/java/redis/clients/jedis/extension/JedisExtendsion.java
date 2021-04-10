package redis.clients.jedis.extension;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.extension.commands.GetCommand;
import redis.clients.jedis.extension.commands.SetCommand;
import redis.clients.jedis.params.SetParams;

public class JedisExtendsion extends Jedis {
    public String get(final String key, GetCommand command) {
        String redisReply=this.get(key);
        if (redisReply!=null)
            return redisReply;
        String commandReturn=command.execute();
        this.set(key, commandReturn);
        return commandReturn;
    }

    public String set(final String key, SetCommand command, final SetParams params) {
        String commandReturn=command.execute();
        return this.set(key, commandReturn, params);
    }
}
