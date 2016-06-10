package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaJoker {

    private List<String> jokes = new ArrayList<>();
    InputStream input;
    BufferedReader reader;
    private final String LOGGER_NAME = JavaJoker.class.getName();

    public JavaJoker() {

        try {

            // Loading the file, reading all jokes and populating the array list with them
            input = getClass().getResourceAsStream("/jokes");
            reader = new BufferedReader(new InputStreamReader(input));
            String joke = reader.readLine();
            while (joke != null) {
                jokes.add(joke);
                joke = reader.readLine();
            }
        }

        catch (FileNotFoundException exception) {
            Logger.getLogger(LOGGER_NAME).log(Level.SEVERE, null, exception);
        }

        catch (IOException exception) {
            Logger.getLogger(LOGGER_NAME).log(Level.SEVERE, null, exception);
        }

        finally {

            try {
                reader.close();
                input.close();
            }

            catch (IOException exception) {
                Logger.getLogger(LOGGER_NAME).log(Level.SEVERE, null, exception);
            }
        }
    }

    public String tellFunnyJoke() {
        // Return a randomly selected joke from the array list loaded from the jokes file
        Random random = new Random();
        int jokeIndex = random.nextInt(jokes.size());
        return jokes.get(jokeIndex);
    }
}
