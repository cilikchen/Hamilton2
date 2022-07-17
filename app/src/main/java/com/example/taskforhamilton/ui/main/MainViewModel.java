package com.example.taskforhamilton.ui.main;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.taskforhamilton.MyCallback;
import com.example.taskforhamilton.model.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel {

    private List<String> currencies;
    private final Repository repo = new Repository();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<String> getCurrency(Context context) {
        if (currencies == null) {
            currencies = new LinkedList<>();
            loadCurrency(context);
        }
        return currencies;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadCurrency(Context context) {
        currencies = repo.getAvailableCurrency(context);
    }

    public float getRate(Context context, String exchangeFrom, String exchangeTo){
        return repo.getExchangeRate(context,exchangeFrom,exchangeTo);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pullData(MyCallback myCallback, Context context) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> myCallback.onData(getCurrency(context)));
    }

}