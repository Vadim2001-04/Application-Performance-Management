package com.example.ioperf.generator;

import com.example.ioperf.model.CurrencyRate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
    private static final Random RANDOM = new Random(42); // воспроизводимость

    public static List<CurrencyRate> generate(int count) {
        List<CurrencyRate> data = new ArrayList<>(count);
        long baseTime = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            int currencyId = RANDOM.nextInt(100);
            double rate = 50 + RANDOM.nextDouble() * 50; // 50–100
            long timestamp = baseTime + i * 1000;
            data.add(new CurrencyRate(currencyId, rate, timestamp));
        }
        return data;
    }
}