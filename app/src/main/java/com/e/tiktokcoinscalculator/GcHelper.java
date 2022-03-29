package com.e.tiktokcoinscalculator;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.net.MalformedURLException;
import java.net.URL;

public class GcHelper {

    private static final String PUBLISHER_ID = "pub-3161809630065444";
    private static final String INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712";
    private static final String PRIVACY_URL = "https://appsfarm351797208.wordpress.com/tiktok-gems-generator-calc/";
    private Context context;
    private AdView adView;
    private InterstitialAd interstitialAd;
    private ConsentForm consentForm;
    private AdRequest adRequest;
    private boolean showPersonalAds;

    public GcHelper(Context context) {
        this.context = context;
    }

    public void init() {
        //initAds();
        checkAdConsent();
    }

    public void checkAdConsent() {
        ConsentInformation consentInformation = ConsentInformation.getInstance(context);
        consentInformation.requestConsentInfoUpdate(new String[]{PUBLISHER_ID}, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                switch (consentStatus) {
                    case PERSONALIZED:
                        //loadsAds(true);
                        setShowPersonalAds(true);
                        break;
                    case NON_PERSONALIZED:
                        //loadsAds(false);
                        setShowPersonalAds(false);
                        break;
                    case UNKNOWN:
                        displayConsentForm();
                        break;
                }
            }

            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                // Consent form error. Would be nice to have proper error logging. Happens also when user has no internet connection
                if (BuildConfig.BUILD_TYPE.equals("debug")) {
                    Toast.makeText(context, errorDescription, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public AdRequest onNewAdRequest() {
        if (showPersonalAds) {
            return new AdRequest.Builder().build();
        } else {
            return new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, getNonPersonalizedAdsBundle()).build();
        }
    }

    private void loadsAds(boolean showPersonalAds) {
        this.showPersonalAds = showPersonalAds;
        if (showPersonalAds) {
            adRequest = new AdRequest.Builder().build();
        } else {
            adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, getNonPersonalizedAdsBundle()).build();
        }
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                adView.loadAd(adRequest);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                adView.loadAd(adRequest);
            }
        });
        adView.loadAd(adRequest);
        requestNewInterstitial();
    }

    private Bundle getNonPersonalizedAdsBundle() {
        Bundle extras = new Bundle();
        extras.putString("npa", "1");
        return extras;
    }

    private void initAds() {
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(INTERSTITIAL_ID);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
    }

    private void requestNewInterstitial() {
        AdRequest adRequest;
        if (this.showPersonalAds) {
            adRequest = new AdRequest.Builder().build();
        } else {
            adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, getNonPersonalizedAdsBundle()).build();
        }
        interstitialAd.loadAd(adRequest);
    }

    public void showInterstitialAd() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            requestNewInterstitial();
        }
    }

    public void resetConsent() {
        ConsentInformation consentInformation = ConsentInformation.getInstance(context);
        consentInformation.reset();
        //checkAdConsent();
    }

    private void displayConsentForm() {
        consentForm = new ConsentForm.Builder(context, getPrivacyUrl())
                .withListener(new ConsentFormListener() {
                    @Override
                    public void onConsentFormLoaded() {
                        // Consent form has loaded successfully, now show it
                        consentForm.show();
                    }

                    @Override
                    public void onConsentFormOpened() {
                        // Consent form was displayed.
                    }

                    @Override
                    public void onConsentFormClosed(
                            ConsentStatus consentStatus, Boolean userPrefersAdFree) {
                        // Consent form was closed. This callback method contains all the data about user's selection, that you can use.

                    }

                    @Override
                    public void onConsentFormError(String errorDescription) {
                        // Consent form error. Would be nice to have some proper logging
                        if (BuildConfig.BUILD_TYPE.equals("debug")) {
                            Toast.makeText(context, errorDescription, Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .withPersonalizedAdsOption()
                .withNonPersonalizedAdsOption()
                .build();
        consentForm.load();
    }

    private URL getPrivacyUrl() {
        URL privacyUrl = null;
        try {
            privacyUrl = new URL(PRIVACY_URL);
        } catch (MalformedURLException e) {
            // Since this is a constant URL, the exception should never(or always) occur
            e.printStackTrace();
        }
        return privacyUrl;
    }

    public boolean isShowPersonalAds() {
        return showPersonalAds;
    }

    public void setShowPersonalAds(boolean showPersonalAds) {
        this.showPersonalAds = showPersonalAds;
    }

    public AdView getAdView() {
        return adView;
    }

    public void setAdView(AdView adView) {
        this.adView = adView;
    }
}
