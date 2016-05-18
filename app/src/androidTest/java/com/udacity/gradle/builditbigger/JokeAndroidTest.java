package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * Created by alvarpao on 5/17/2016.
 */
public class JokeAndroidTest extends AndroidTestCase implements IFetchJokeListener {

    private static final String LOG_TAG = JokeAndroidTest.class.getSimpleName();
    AsyncJokeFetcher jokeFetcher;
    // Use a count down latch to test the AsyncTask that fetches the joke
    CountDownLatch countDownLatch;
    String fetchedJoke = "";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        countDownLatch = new CountDownLatch(1);
        // User wrapper class for AsyncTask so it is easier to test
        jokeFetcher = new AsyncJokeFetcher(this);
    }

    public void testJokeFetched()throws InterruptedException{
        // Run AsyncTask FetchJokeTask()
        jokeFetcher.fetchJoke();
        // Wait until the AsyncTask has been completed in order to test the joke for a non-empty
        // string
        countDownLatch.await();
        assertFalse("Joke returned expected to be a non-empty string", fetchedJoke.isEmpty());
    }

    @Override
    public void fetchJokeCompleted(String joke) {
        // JokeAndroidTest implements IFetchJokeListener, therefore this method gets called once
        // the AsyncTask FetchJokeTask() has been completed

        // Update member variable for assert
        fetchedJoke = joke;
        // Count down latch to zero once the AsyncTask has been completed
        countDownLatch.countDown();
        Log.v(LOG_TAG, joke);
    }
}
