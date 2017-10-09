package com.google.buildItBigger.backend;

import com.example.JokeProviderJava;

/**
 * The object model for the data we are sending through endpoints
 */
public class MyJokeBean {

    private JokeProviderJava jokeProviderJava;

    public  MyJokeBean() {
        jokeProviderJava = new JokeProviderJava();
    }

    public String getJoke() {
        return jokeProviderJava.getJokes();
    }
}