package com.e.tiktokcoinscalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class RecomendationFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recomendation, container, false);
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
                Navigation.findNavController(getView()).navigate(R.id.action_RecomendationFragment_to_MainFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void configureButton() {
        Button buttonQuestionOne = getView().findViewById(R.id.btnQuestion1);
        buttonQuestionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_RecomendationFragment_to_RecomendationOneFragment);
            }
        });

        Button buttonQuestionTwo = getView().findViewById(R.id.btnQuestion2);
        buttonQuestionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_RecomendationFragment_to_RecomendationTwoFragment);
            }
        });

        Button buttonQuestionThree = getView().findViewById(R.id.btnQuestion3);
        buttonQuestionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_RecomendationFragment_to_RecomendationThreeFragment);
            }
        });

        Button buttonBackMain = getView().findViewById(R.id.btnBackMain);
        buttonBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_RecomendationFragment_to_MainFragment);
            }
        });
    }
}
