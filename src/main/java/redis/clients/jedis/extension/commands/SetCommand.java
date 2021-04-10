package redis.clients.jedis.extension.commands;

/**
 * Command execute and result will write to redis.
 */
public interface SetCommand {
    String execute();
}