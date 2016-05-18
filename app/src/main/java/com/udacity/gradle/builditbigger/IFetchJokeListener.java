package com.udacity.gradle.builditbigger;

/**
 * Created by alvarpao on 5/17/2016.
 */

// Event listener used to execute the necessary code after the AsyncTask (FetchJokeTask) is
// completed (used in both the MainActivity and for AndroidTest of the AsyncTask)
public interface IFetchJokeListener {

    void fetchJokeCompleted(String joke);
}
