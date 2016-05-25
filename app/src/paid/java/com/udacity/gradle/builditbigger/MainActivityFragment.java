package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.alvarpao.jokesdisplay.JokeActivity;

/**
 * Created by alvarpao on 5/19/2016.
 */
public class MainActivityFragment extends Fragment implements IFetchJokeListener {

    // Wrapper class for AsyncTask
    AsyncJokeFetcher asyncJokeFetcher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        asyncJokeFetcher = new AsyncJokeFetcher(this);
        Button btnTellJoke = (Button) root.findViewById(R.id.button_tell_joke);
        btnTellJoke.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJoke();
            }
        });

        return root;
    }

    private void tellJoke(){
        // Execute AsyncTask that fetches joke
        asyncJokeFetcher.fetchJoke();
    }


    @Override
    public void fetchJokeCompleted(String joke) {
        // Display Java joke using activity in created Android library
        Intent jokeIntent = new Intent(getActivity(), JokeActivity.class);
        jokeIntent.putExtra(JokeActivity.JOKE_KEY, joke);
        startActivity(jokeIntent);
    }
}
