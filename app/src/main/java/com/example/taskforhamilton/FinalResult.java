package com.example.taskforhamilton;

public class FinalResult {
    private final String getCurrencyAndAmount;
    private final float rate;

    public FinalResult( String getCurrencyAndAmount, float amount) {

        this.getCurrencyAndAmount = getCurrencyAndAmount;
        this.rate = amount;
    }


    public float getRate() {
        return rate;
    }

    public String getCurrencyAndAmount() {
        return getCurrencyAndAmount;
    }


}
