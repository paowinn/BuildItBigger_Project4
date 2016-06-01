package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.alvarpao.jokesdisplay.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivityFragment extends Fragment implements IFetchJokeListener {

    // Wrapper class for AsyncTask
    AsyncJokeFetcher asyncJokeFetcher;
    InterstitialAd mInterstitialAd;
    private ProgressBar spinnerProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        asyncJokeFetcher = new AsyncJokeFetcher(this);

        spinnerProgressBar  = (ProgressBar) root.findViewById(R.id.progress_bar);
        // Initially the progress bar is invisible to the user
        spinnerProgressBar.setVisibility(View.GONE);

        // Create an ad request for banner ad
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Add device IDs for the 3 test devices and emulator in order to display ads in
        // these devices
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getString(R.string.test_device_id_1))
                .addTestDevice(getString(R.string.test_device_id_2))
                .addTestDevice(getString(R.string.test_device_id_3))
                .build();
        mAdView.loadAd(adRequest);

        // Add interstitial ad after clicking the button and before displaying the joke
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load a new ad for next time
                requestNewInterstitial();
                tellJoke();
            }
        });

        // Load an ad to have it ready and show it when the user clicks the button
        requestNewInterstitial();

        Button btnTellJoke = (Button) root.findViewById(R.id.button_tell_joke);
        btnTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    // If the ad for some reason didn't load proceed with displaying the joke
                    tellJoke();
                }
            }
        });

        return root;
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getString(R.string.test_device_id_1))
                .addTestDevice(getString(R.string.test_device_id_2))
                .addTestDevice(getString(R.string.test_device_id_3))
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void tellJoke(){

        // Show the progress bar to the user until the joke is displayed
        spinnerProgressBar.setVisibility(View.VISIBLE);
        // Execute AsyncTask that fetches joke
        asyncJokeFetcher.fetchJoke();
    }


    @Override
    public void fetchJokeCompleted(String joke) {

        // Display Java joke using activity in created Android library
        Intent jokeIntent = new Intent(getActivity(), JokeActivity.class);
        jokeIntent.putExtra(JokeActivity.JOKE_KEY, joke);
        startActivity(jokeIntent);
        // Once the joke is displayed hide the progress bar
        spinnerProgressBar.setVisibility(View.GONE);
    }

}