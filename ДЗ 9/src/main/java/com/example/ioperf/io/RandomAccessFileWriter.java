package com.example.ioperf.io;

import com.example.ioperf.model.CurrencyRate;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class RandomAccessFileWriter {
    private static final int RECORD_SIZE = 20; // 4 + 8 + 8

    public static void writeSequentially(List<CurrencyRate> data, String filename) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            raf.setLength(0); // очистить файл
            for (CurrencyRate r : data) {
                raf.writeInt(r.currencyId);
                raf.writeDouble(r.rate);
                raf.writeLong(r.timestamp);
            }
        }
    }

    public static void updateRecord(String filename, int index, double newRate) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            long position = (long) index * RECORD_SIZE;
            raf.seek(position + 4); // пропустить currencyId
            raf.writeDouble(newRate);
        }
    }

    public static List<CurrencyRate> readSequentially(String filename, int count) throws IOException {
        List<CurrencyRate> result = new java.util.ArrayList<>(count);
        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            for (int i = 0; i < count; i++) {
                int id = raf.readInt();
                double rate = raf.readDouble();
                long ts = raf.readLong();
                result.add(new CurrencyRate(id, rate, ts));
            }
        }
        return result;
    }
}