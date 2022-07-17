package com.example.taskforhamilton;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public interface MyCallback {
    void onData(List<String> currencyList);
}
