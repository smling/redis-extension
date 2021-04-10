package redis.clients.jedis.extension;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.extension.commands.GetCommand;
import redis.clients.jedis.extension.commands.SetCommand;
import redis.clients.jedis.params.SetParams;

public class JavaExtensionTest {
    String dummyKey="dummy-key";
    String dummyResponse="Dummy response";

    protected JedisExtendsion jedis = new JedisExtendsion();
    @Test
    void getTest() {
        String response = jedis.get(dummyKey, new GetCommand() {
            @Override
            public String execute() {
                return dummyResponse;
            }
        });
        Assertions.assertNotNull(response);
    }

    @Test
    void setTest() {
        String statusCode=jedis.set(dummyKey, new SetCommand() {
            @Override
            public String execute() {
                return dummyResponse+=" from SetCommand.";
            }
        }, new SetParams());
        Assertions.assertEquals(statusCode, StatusCodes.OK.getStatusCode());
    }
}
