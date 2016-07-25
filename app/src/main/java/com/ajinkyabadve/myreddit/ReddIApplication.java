package com.ajinkyabadve.myreddit;

import android.app.Application;
import android.content.Context;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Ajinkya on 25/07/2016.
 */
public class ReddIApplication extends Application {


    private ReddItService openWeatherMapService;
    private Scheduler defaultSubscribeScheduler;

    public static ReddIApplication get(Context context) {
        return (ReddIApplication) context.getApplicationContext();
    }

    public ReddItService getReddItService() {
        if (openWeatherMapService == null) {
            openWeatherMapService = ReddItService.Factory.create();
        }
        return openWeatherMapService;
    }


    //For setting mocks during testing
    public void setReddItService(ReddItService openWeatherMap) {
        this.openWeatherMapService = openWeatherMap;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }
}
