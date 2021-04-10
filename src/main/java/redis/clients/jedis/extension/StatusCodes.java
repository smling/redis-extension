package redis.clients.jedis.extension;

public enum StatusCodes {
    OK("OK")
    ;

    private String code;

    StatusCodes(String code) {
        this.code=code;
    }
    public String getStatusCode() {
        return code;
    }
}
