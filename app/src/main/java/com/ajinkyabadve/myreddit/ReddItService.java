package com.ajinkyabadve.myreddit;

import com.ajinkyabadve.myreddit.model.ReddIt;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Ajinkya on 25/07/2016.
 */
public interface ReddItService {

    public static final String BASE_URL = "https://www.reddit.com/r/";

    @GET("funny.json")
    Observable<ReddIt> getReddIt();


    class Factory {
        public static ReddItService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(ReddItService.class);
        }


    }
}
