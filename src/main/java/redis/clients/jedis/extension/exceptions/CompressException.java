package redis.clients.jedis.extension.exceptions;

public class CompressException extends Exception{
    public CompressException(String message, Exception exception) {
        super(message, exception);
    }
}
