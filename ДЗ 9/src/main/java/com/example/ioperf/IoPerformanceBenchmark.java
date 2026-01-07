package com.example.ioperf;

import com.example.ioperf.generator.DataGenerator;
import com.example.ioperf.io.*;
import com.example.ioperf.model.CurrencyRate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class IoPerformanceBenchmark {

    private static final String FILE_PREFIX = "data_";
    private static final int[] SIZES = {10_000, 50_000, 100_000};

    public static void main(String[] args) throws IOException {
        for (int size : SIZES) {
            System.out.println("\n=== Замеры для " + size + " записей ===");

            List<CurrencyRate> data = DataGenerator.generate(size);
            long fileSize = (long) size * 20;

            // RandomAccessFile
            String file1 = FILE_PREFIX + "raf.dat";
            long time1 = measure(() -> RandomAccessFileWriter.writeSequentially(data, file1));
            long readTime1 = measure(() -> RandomAccessFileWriter.readSequentially(file1, size));
            System.out.printf("RandomAccessFile — запись: %d мс, чтение: %d мс%n", time1, readTime1);
            Files.deleteIfExists(Paths.get(file1));

            // FileChannel
            String file2 = FILE_PREFIX + "channel.dat";
            long time2 = measure(() -> FileChannelWriter.writeSequentially(data, file2));
            long readTime2 = measure(() -> FileChannelWriter.readSequentially(file2, size));
            System.out.printf("FileChannel — запись: %d мс, чтение: %d мс%n", time2, readTime2);
            Files.deleteIfExists(Paths.get(file2));

            // MappedByteBuffer
            String file3 = FILE_PREFIX + "mapped.dat";
            long time3 = measure(() -> MappedFileWriter.writeSequentially(data, file3));
            long readTime3 = measure(() -> MappedFileWriter.readSequentially(file3, size));
            System.out.printf("MappedByteBuffer — запись: %d мс, чтение: %d мс%n", time3, readTime3);
            Files.deleteIfExists(Paths.get(file3));

            // Случайный доступ (только для RAF и Mapped)
            if (size >= 1000) {
                String file4 = FILE_PREFIX + "raf_update.dat";
                RandomAccessFileWriter.writeSequentially(data, file4);
                long updateTime1 = measure(() -> {
                    for (int i = 0; i < 1000; i++) {
                        RandomAccessFileWriter.updateRecord(file4, i, 99.99);
                    }
                });
                System.out.printf("RAF случайное обновление (1000): %d мс%n", updateTime1);
                Files.deleteIfExists(Paths.get(file4));

                String file5 = FILE_PREFIX + "mapped_update.dat";
                MappedFileWriter.writeSequentially(data, file5);
                long updateTime2 = measure(() -> {
                    for (int i = 0; i < 1000; i++) {
                        MappedFileWriter.updateRecord(file5, fileSize, i, 99.99);
                    }
                });
                System.out.printf("Mapped случайное обновление (1000): %d мс%n", updateTime2);
                Files.deleteIfExists(Paths.get(file5));
            }
        }

        // Замер памяти
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        long usedMem = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("\nПиковое потребление памяти: %.2f МБ%n", usedMem / (1024.0 * 1024.0));
    }

    private static long measure(Runnable task) {
        long start = System.nanoTime();
        task.run();
        return (System.nanoTime() - start) / 1_000_000; // мс
    }
}