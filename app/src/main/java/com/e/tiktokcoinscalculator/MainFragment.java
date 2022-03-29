package com.e.tiktokcoinscalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
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

    private void configureButton() {
        Button buttonCalc = getView().findViewById(R.id.btnCalc);
        buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_MainFragment_to_CalcDataFragment);
            }
        });

        Button buttonRec = getView().findViewById(R.id.btnReco);
        buttonRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_MainFragment_to_RecomendationFragment);
            }
        });

        Button buttonDesc = getView().findViewById(R.id.btnDesc);
        buttonDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_MainFragment_to_DescriptionFragment);
            }
        });

        Button buttonTerms = getView().findViewById(R.id.btnTerms);
        buttonTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_MainFragment_to_TermsFragment);
            }
        });
    }
}
