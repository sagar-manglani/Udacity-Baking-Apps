package com.udacity.udacitybakingapps.IdlingResource;

import androidx.test.espresso.idling.CountingIdlingResource;

public class EspressoIdlingResource {

    private final static String Resource="Global";

    public static CountingIdlingResource resource = new CountingIdlingResource(Resource);

    public static void increment(){
        resource.increment();
    }

    public static void decrement(){
        if(!resource.isIdleNow())
            resource.decrement();
    }

}
