package com.e.tiktokcoinscalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class CalcDataFragment extends Fragment {
    int coins;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calc_data, container, false);

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.action_CalcDataFragment_to_MainFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void configureButton() {
        Button buttonBackMain = getView().findViewById(R.id.btnBackMain);
        buttonBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_CalcDataFragment_to_MainFragment);
            }
        });

        final EditText editText = getView().findViewById(R.id.txtCalcNum);

        Button buttonCalculate = getView().findViewById(R.id.btnCalc);
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("coins",(editText.getText().toString().equals("")) ? 0 : Integer.parseInt(editText.getText().toString()));
                Navigation.findNavController(v).navigate(R.id.action_CalcDataFragment_to_CalcResultFragment, bundle);
            }
        });
    }
}