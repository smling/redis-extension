package redis.clients.jedis.extension.exceptions;

public class CommandExecuteException extends Exception {
    public CommandExecuteException(String message) {
        super(message);
    }
    public CommandExecuteException(String message, Exception exception) { super(message, exception); }
}
