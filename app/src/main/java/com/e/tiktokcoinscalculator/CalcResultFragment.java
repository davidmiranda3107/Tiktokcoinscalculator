package com.e.tiktokcoinscalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class CalcResultFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calc_result, container, false);
        Bundle bundle = this.getArguments();
        int coins = bundle.getInt("coins");
        String price = onCalculateResult(coins);
        TextView resultTxt = view.findViewById(R.id.txtResult);
        resultTxt.setText(price);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureButton();

        GcHelper helper = new GcHelper(getContext());
        helper.init();

        AdView adView = getView().findViewById(R.id.ad_view);
        AdRequest adRequest = helper.onNewAdRequest();
        adView.loadAd(adRequest);
    }

    private String onCalculateResult(int coins) {
        double COIN_PRICE = 0.0155714285714286;
        double result = coins * COIN_PRICE;

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00", symbols);

        String resultStr = getString(R.string.calc_coins) + ": " + coins + " " +
                           getString(R.string.calc_total_eur) + decimalFormat.format(result);
        return resultStr;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.action_CalcResultFragment_to_CalcDataFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void configureButton() {
        Button buttonBackMain = getView().findViewById(R.id.btnBackMain);
        buttonBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_CalcResultFragment_to_MainFragment);
            }
        });

        Button buttonNewCalc = getView().findViewById(R.id.btnNewCalc);
        buttonNewCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_CalcResultFragment_to_CalcDataFragment);
            }
        });
    }
}
