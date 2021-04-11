package redis.clients.jedis.extension;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

public class CompressorTest {
    String deCompressString = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    byte[] compressedBytes;

    Compressor compressor=new Compressor();

    @Test
    void compressTest() {
        Assertions.assertDoesNotThrow(()->{
            compressedBytes=compressor.compress(deCompressString.getBytes(StandardCharsets.UTF_8));
            Assertions.assertTrue(deCompressString.getBytes().length > compressedBytes.length);
        });
    }
    @Test
    void decompressTest() {
        Assertions.assertDoesNotThrow(()->{
            compressedBytes=compressor.compress(deCompressString.getBytes());
            String result=compressor.decompress(compressedBytes).toString();
            Assertions.assertEquals(deCompressString, result);
        });
    }
}
