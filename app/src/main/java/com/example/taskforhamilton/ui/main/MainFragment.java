package com.example.taskforhamilton.ui.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskforhamilton.ExchangeInfo;
import com.example.taskforhamilton.MainActivity;
import com.example.taskforhamilton.R;

import java.util.Objects;

public class MainFragment extends Fragment {

    Spinner exchangeFrom;
    Spinner exchangeTo;
    Button button;
    EditText textView;

    String exchangeCurrencyForm = null;
    String exchangeCurrencyTo = null;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        exchangeFrom = rootView.findViewById(R.id.exchange_from);
        exchangeTo = rootView.findViewById(R.id.exchange_to);
        button = rootView.findViewById(R.id.button);
        textView = rootView.findViewById(R.id.textView);




        MainViewModel mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mViewModel.pullData(currencies -> {
            String[] items = currencies.toArray(new String[0]);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
            exchangeFrom.setAdapter(adapter);
            exchangeTo.setAdapter(adapter);
            exchangeFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.v("item", (String) parent.getItemAtPosition(position));
                    exchangeCurrencyForm = (String) parent.getItemAtPosition(position);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }

            });

            exchangeTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.v("item", (String) parent.getItemAtPosition(position));
                    exchangeCurrencyTo= (String) parent.getItemAtPosition(position);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        },getActivity());



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = textView.getText().toString();
                int inputAmount = Integer.parseInt(getText);
                ExchangeInfo exchangeInfo = new ExchangeInfo(exchangeCurrencyForm, exchangeCurrencyTo,inputAmount);
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.wakeSecondFragment(exchangeInfo);


            }
        });




        // initSpinner();


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    private static class Animal {
        public void talk(String something) {}
    }

    private static final class Cat extends Animal {
        public void play() {}
    }

    private Animal getMyPet() {
        return new Cat();
    }

    private void func() {
        Cat cat= (Cat) getMyPet();
    }
}

