package com.example.ioperf.io;

import com.example.ioperf.model.CurrencyRate;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

public class MappedFileWriter {
    private static final int RECORD_SIZE = 20;

    public static void writeSequentially(List<CurrencyRate> data, String filename) throws IOException {
        long fileSize = (long) data.size() * RECORD_SIZE;
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            raf.setLength(fileSize);
            try (FileChannel channel = raf.getChannel()) {
                MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);
                for (CurrencyRate r : data) {
                    buffer.putInt(r.currencyId);
                    buffer.putDouble(r.rate);
                    buffer.putLong(r.timestamp);
                }
                buffer.force();
            }
        }
    }

    public static void updateRecord(String filename, long fileSize, int index, double newRate) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            try (FileChannel channel = raf.getChannel()) {
                MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);
                long position = (long) index * RECORD_SIZE + 4;
                buffer.putDouble(position, newRate);
                buffer.force();
            }
        }
    }

    public static List<CurrencyRate> readSequentially(String filename, int count) throws IOException {
        long fileSize = (long) count * RECORD_SIZE;
        List<CurrencyRate> result = new java.util.ArrayList<>(count);
        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            try (FileChannel channel = raf.getChannel()) {
                MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, fileSize);
                for (int i = 0; i < count; i++) {
                    int id = buffer.getInt();
                    double rate = buffer.getDouble();
                    long ts = buffer.getLong();
                    result.add(new CurrencyRate(id, rate, ts));
                }
            }
        }
        return result;
    }
}