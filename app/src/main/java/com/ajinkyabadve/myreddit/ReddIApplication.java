package com.ajinkyabadve.myreddit;

import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Ajinkya on 25/07/2016.
 */
@ReportsCrashes(mailTo = "asbadve@gmail.com", customReportContent = {
        ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
        ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
        ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT},
        mode = ReportingInteractionMode.DIALOG, resDialogText = R.string.crash_toast_text,
        resDialogIcon = android.R.drawable.ic_dialog_info, resDialogTitle = R.string.crash_title)

public class ReddIApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);

    }

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

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }
}
