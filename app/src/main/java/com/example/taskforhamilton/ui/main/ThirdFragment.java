package com.example.taskforhamilton.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taskforhamilton.R;

public class ThirdFragment extends Fragment {

    TextView currencyAndAmountFrom;
    TextView rateInformation;


    public static ThirdFragment newInstance(String currencyAndAmount, float rate) {
        ThirdFragment thirdFragment = new ThirdFragment();

        Bundle args = new Bundle();

        args.putFloat("rate", rate);
        args.putString("currencyAndAmount",currencyAndAmount);

        thirdFragment.setArguments(args);

        return thirdFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_third, container, false);
        currencyAndAmountFrom = rootView.findViewById(R.id.currency_and_amount_from);
        rateInformation = rootView.findViewById(R.id.rate_information);

        Bundle args = getArguments();

        String currencyAndAmount = args.getString("currencyAndAmount","");
        float rate = args.getFloat("rate",0);

        String roundOff = String.format("%.2f", rate);

        currencyAndAmountFrom.setText("Great now you have " + currencyAndAmount + " in your account.");
        rateInformation.setText("Your conversion rate was: 1/ " + roundOff);


        return rootView;
    }
}
