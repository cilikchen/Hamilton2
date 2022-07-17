package com.example.taskforhamilton;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskforhamilton.ui.main.MainFragment;
import com.example.taskforhamilton.ui.main.SecondFragment;
import com.example.taskforhamilton.ui.main.ThirdFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();

//            getSupportFragmentManager().beginTransaction()
//        .replace(R.id.container, SecondFragment.newInstance("exchangeForm","exchangeTo", 150))
//        .addToBackStack(null)
//        .commit();

        }

//        getSupportFragmentManager().beginTransaction()
//        .replace(R.id.container, SecondFragment.newInstance("exchangeForm","exchangeTo", 150))
//        .addToBackStack(null)
//        .commit();

    }

    public void wakeSecondFragment(ExchangeInfo exchangeInfo){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, SecondFragment.newInstance(exchangeInfo.getExchangeFrom(),exchangeInfo.getExchangeTo(), exchangeInfo.getAmount()))
                .commitNow();

    }

    public void wakeThirdFragment(FinalResult finalResult){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ThirdFragment.newInstance(finalResult.getCurrencyAndAmount(), finalResult.getRate()))
                .commitNow();

    }
}