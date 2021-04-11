package redis.clients.jedis.extension;

import redis.clients.jedis.extension.exceptions.CompressException;

import java.io.*;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public class Compressor {
    public byte[] compress(byte[] input) throws CompressException {
        try (DeflaterOutputStream deflater = new DeflaterOutputStream(new ByteArrayOutputStream())) {
            return getDataFromInjectedStream(input, deflater);
        } catch (IOException exception) {
            throw new CompressException("Invalid data during compress data.", exception);
        }
    }
    public byte[] decompress(byte[] input) throws CompressException {
        try (InflaterOutputStream inflater = new InflaterOutputStream(new ByteArrayOutputStream())) {
            return getDataFromInjectedStream(input, inflater);
        } catch (IOException exception) {
            throw new CompressException("Invalid data during decompress data.", exception);
        }
    }
    private byte[] getDataFromInjectedStream(byte[] input, FilterOutputStream filterOutputStream) throws IOException {
        if (input == null || input.length == 0)
            return input;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        filterOutputStream.write(input);
        return os.toByteArray();
    }
}
