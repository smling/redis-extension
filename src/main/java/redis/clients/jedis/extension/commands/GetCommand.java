package redis.clients.jedis.extension.commands;

/**
 * Command to execute if no result found from redis.
 */
public interface GetCommand {
    String execute();
}
