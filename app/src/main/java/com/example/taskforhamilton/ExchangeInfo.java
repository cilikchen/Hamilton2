package com.example.taskforhamilton;

public class ExchangeInfo {
    private final String exchangeFrom;
    private final String exchangeTo;
    private final int amount;
    // test for push
    public ExchangeInfo(String exchangeFrom, String exchangeTo, int amount) {
        this.exchangeFrom = exchangeFrom;
        this.exchangeTo = exchangeTo;
        this.amount = amount;
    }


    public int getAmount() {
        return amount;
    }

    public String getExchangeTo() {
        return exchangeTo;
    }

    public String getExchangeFrom() {
        return exchangeFrom;
    }
}
