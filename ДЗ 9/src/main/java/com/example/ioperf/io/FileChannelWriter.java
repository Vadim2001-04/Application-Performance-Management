package com.example.ioperf.io;

import com.example.ioperf.model.CurrencyRate;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

public class FileChannelWriter {
    private static final int RECORD_SIZE = 20;

    public static void writeSequentially(List<CurrencyRate> data, String filename) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw");
             FileChannel channel = raf.getChannel()) {
            raf.setLength(0);
            ByteBuffer buffer = ByteBuffer.allocate(RECORD_SIZE);
            for (CurrencyRate r : data) {
                buffer.putInt(r.currencyId);
                buffer.putDouble(r.rate);
                buffer.putLong(r.timestamp);
                buffer.flip();
                channel.write(buffer);
                buffer.clear();
            }
            channel.force(true);
        }
    }

    public static List<CurrencyRate> readSequentially(String filename, int count) throws IOException {
        List<CurrencyRate> result = new java.util.ArrayList<>(count);
        try (RandomAccessFile raf = new RandomAccessFile(filename, "r");
             FileChannel channel = raf.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(RECORD_SIZE);
            for (int i = 0; i < count; i++) {
                buffer.clear();
                channel.read(buffer);
                buffer.flip();
                int id = buffer.getInt();
                double rate = buffer.getDouble();
                long ts = buffer.getLong();
                result.add(new CurrencyRate(id, rate, ts));
            }
        }
        return result;
    }
}