package com.example.taskforhamilton.ui.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskforhamilton.ExchangeInfo;
import com.example.taskforhamilton.FinalResult;
import com.example.taskforhamilton.MainActivity;
import com.example.taskforhamilton.R;

import java.util.Timer;
import java.util.TimerTask;

public class SecondFragment extends Fragment {

    TextView exchangeFrom;
    TextView exchangeTo;
    TextView precedes;
    TextView countTimer;
    Button button;

    int tt=30;

    Timer timer;

    Handler aHandler;

    public static SecondFragment newInstance(String exchangeFrom, String exchangeTo, int amount) {
        SecondFragment secondFragment = new SecondFragment();

        Bundle args = new Bundle();
        args.putInt("amount", amount);
        args.putString("exchangeFrom",exchangeFrom);
        args.putString("exchangeTo",exchangeTo);
        secondFragment.setArguments(args);

        return secondFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);
        MainViewModel mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        exchangeFrom = rootView.findViewById(R.id.currencyAndAmountFrom);
        precedes = rootView.findViewById(R.id.precedes);
        exchangeTo = rootView.findViewById(R.id.currencyAndAmountTo);

        countTimer = rootView.findViewById(R.id.timer);

        button = rootView.findViewById(R.id.accept);

        Bundle args = getArguments();
        int currentAmount = args.getInt("amount");
        float rateValue = mViewModel.getRate(getContext(),args.getString("exchangeFrom"),args.getString("exchangeTo"));

        int changeAmount = (int) (currentAmount * rateValue);

        String getAmountAndCurrency = String.valueOf(changeAmount) + " " +args.getString("exchangeTo");
        String payAmountAndCurrency = args.getInt("amount") + " " +args.getString("exchangeFrom");

        exchangeFrom.setText(payAmountAndCurrency);
        exchangeTo.setText(getAmountAndCurrency);
        precedes.setText("precedes");


        aHandler = new Handler();
        aHandler.post(runnable);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setMessage("Are you sure you want to exit?")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                getActivity().finish();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alert = builder.create();
//                alert.show();

                final Dialog dialog = new Dialog(getActivity());
// Include dialog.xml file
                dialog.setContentView(R.layout.coutom_dialog);

                TextView textView = dialog.findViewById(R.id.description);
                textView.setText("You are about to get " + getAmountAndCurrency+ " for "+ payAmountAndCurrency +". Do you approve this transaction?" );

                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.btn_cancel);
// if decline button is clicked, close the custom dialog
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        dialog.dismiss();
                    }
                });

                Button videoButton = (Button) dialog.findViewById(R.id.btn_approve);
// if decline button is clicked, close the custom dialog
                videoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FinalResult finalResult = new FinalResult(getAmountAndCurrency, rateValue);
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.wakeThirdFragment(finalResult);

                        dialog.dismiss();

                    }
                });

            }
        });

        return rootView;
    }

    final Runnable runnable = new Runnable() {
        public void run() {
// TODO Auto-generated method stub

            if (tt > 0) {
                countTimer.setText(Integer.toString(tt-1) + " second");
                tt--;
                aHandler.postDelayed(runnable, 1000);
            }else{
                countTimer.setText("碰");
            }
        }
    };
    @Override
    public void onPause() {
// TODO Auto-generated method stub
        super.onPause();
        if (aHandler != null) {
            aHandler.removeCallbacks(runnable);
        }
    }


}
