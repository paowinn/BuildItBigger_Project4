package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.example.alvarpao.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by alvarpao on 5/17/2016.
 */

// Wrapper class for AsyncTask FetchJokeTask, needed for easier testing of asynchronous AsyncTask.
// Recommended practice in numerous blogs and StackOverflow posts
public class AsyncJokeFetcher {

    // Event listener to execute actions to display the Joke using another activity (implemented in
    // MainActivity) or check if the joke returned was none-empty in case this class is used in an
    // AndroidTest
    private IFetchJokeListener fetchJokeListener;

    public AsyncJokeFetcher(IFetchJokeListener fetchJokeListener){
        this.fetchJokeListener = fetchJokeListener;
    }

    public void fetchJoke(){
        new FetchJokeTask().execute();
    }

    class FetchJokeTask extends AsyncTask<Void, Void, String> {

        private MyApi myApiService = null;

        @Override
        protected String doInBackground(Void... params) {

            if(myApiService == null) {

                // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://builditbigger-1300.appspot.com/_ah/api/");
                // end options for devappserver

                myApiService = builder.build();
            }

            try {
                return myApiService.getJoke().execute().getData();
            }

            catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Display Java joke using activity in created Android library
            fetchJokeListener.fetchJokeCompleted(result);
        }
    }
}
