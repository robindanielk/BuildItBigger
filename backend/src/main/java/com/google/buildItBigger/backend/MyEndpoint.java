/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.google.buildItBigger.backend;

import com.example.JokeProviderJava;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myJokeApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.buildItBigger.google.com",
                ownerName = "backend.buildItBigger.google.com",
                packagePath = "")
)
public class MyEndpoint {

    /**
     * A simple endpoint
     */
    @ApiMethod(name = "putJoke")
    public MyJokeBean putJoke(MyJokeBean jokeBean) {
        return jokeBean;
    }

}
