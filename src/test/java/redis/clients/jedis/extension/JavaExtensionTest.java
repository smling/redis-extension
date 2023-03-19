package redis.clients.jedis.extension;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.extension.exceptions.JedisCallableException;
import redis.clients.jedis.params.SetParams;

import java.util.stream.Stream;

class JavaExtensionTest {
    static final String REDIS_CONTAINER_IMAGE_NAME = "redis:5.0.3-alpine";
    static final int REDIS_CONTAINER_PORT_NAME = 6379;
    String dummyKey="dummy-key";
    String dummyResponse="Dummy response";

    @Container
    private static final RedisContainer REDIS_CONTAINER =
            new RedisContainer(DockerImageName.parse(REDIS_CONTAINER_IMAGE_NAME))
                    .withExposedPorts(REDIS_CONTAINER_PORT_NAME)
            ;

    @BeforeAll
    static void beforeAll() {
        REDIS_CONTAINER.start();
        Assertions.assertTrue(REDIS_CONTAINER.isRunning());
        JedisPooled jedisPooled = new JedisPooled(REDIS_CONTAINER.getHost(),
                REDIS_CONTAINER.getMappedPort(REDIS_CONTAINER_PORT_NAME))
                ;
        jedisPooled.set("sample", "sample from redis");
    }

    static Stream<Arguments> mockRedisGetData() {
        return Stream.of(
                Arguments.of("sample", "sample from redis"),
                Arguments.of("dummy-key", "Dummy response")
        );
    }
    @ParameterizedTest
    @MethodSource("mockRedisGetData")
    void getTest(String key, String value) {
        Assertions.assertDoesNotThrow(()->{
            JedisPooledExtension jedis = new JedisPooledExtension(
                    REDIS_CONTAINER.getHost(),
                    REDIS_CONTAINER.getMappedPort(REDIS_CONTAINER_PORT_NAME));
            String response = jedis.get(key, ()-> value);
            Assertions.assertNotNull(response, "record can get successfully.");
            Assertions.assertEquals(value, response);
        });
    }

    @Test
    @Disabled("JUnit cannot capture exception but it can capture when run in debug after breakpoint added in throw block.")
    void getTestWithException() {
        Assertions.assertThrows(JedisCallableException.class, ()-> {
            JedisPooledExtension jedis = new JedisPooledExtension(
                    REDIS_CONTAINER.getHost(),
                    REDIS_CONTAINER.getMappedPort(REDIS_CONTAINER_PORT_NAME));
            jedis.get(dummyKey, ()-> {
                throw new RuntimeException("Test exception thrown.");
            });
        });
    }
    @Test
    void setTest() {
        Assertions.assertDoesNotThrow(()->{
            JedisPooledExtension jedis = new JedisPooledExtension(
                    REDIS_CONTAINER.getHost(),
                    REDIS_CONTAINER.getMappedPort(REDIS_CONTAINER_PORT_NAME));
            String statusCode = jedis.set(dummyKey, ()-> dummyResponse += " from SetCommand.", new SetParams());
            Assertions.assertEquals(statusCode, StatusCodes.OK.getStatusCode(), "Record can set successfully.");
        });
    }
    @Test
    void setTestWithException() {
        Assertions.assertThrows(JedisCallableException.class, ()-> {
            JedisPooledExtension jedis = new JedisPooledExtension(
                    REDIS_CONTAINER.getHost(),
                    REDIS_CONTAINER.getMappedPort(REDIS_CONTAINER_PORT_NAME));
            jedis.set(dummyKey, ()-> {
                throw new RuntimeException("Test exception");
                }, new SetParams()
            );
        });
    }
}
