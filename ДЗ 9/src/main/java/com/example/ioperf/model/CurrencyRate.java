package com.example.ioperf.model;

public class CurrencyRate {
    public int currencyId;
    public double rate;
    public long timestamp;

    public CurrencyRate(int currencyId, double rate, long timestamp) {
        this.currencyId = currencyId;
        this.rate = rate;
        this.timestamp = timestamp;
    }
}