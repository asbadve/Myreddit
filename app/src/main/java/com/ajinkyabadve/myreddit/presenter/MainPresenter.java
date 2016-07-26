package com.ajinkyabadve.myreddit.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ajinkyabadve.myreddit.ReddIApplication;
import com.ajinkyabadve.myreddit.ReddItService;
import com.ajinkyabadve.myreddit.Util;
import com.ajinkyabadve.myreddit.model.Child;
import com.ajinkyabadve.myreddit.model.Data;
import com.ajinkyabadve.myreddit.model.ReddIt;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainPresenter implements MainContract.UserActionsListener {

    private static final String TAG = MainPresenter.class.getSimpleName();
    private Context context;
    private MainContract.View mainView;
    private Subscription subscription;

    public MainPresenter(Context context, @NonNull MainContract.View mainView) {
        this.context = context;
        this.mainView = mainView;
        loadReddIt();
    }

    private void loadReddIt() {
        if (!Util.isNetworkAvailable(context)) {
            mainView.NetworkNotAvailable();
            return;
        }
        mainView.setProgressIndicator(true);
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        ReddIApplication reddIApplication = ReddIApplication.get(context);
        ReddItService reddItService = reddIApplication.getReddItService();


        subscription = reddItService.getReddIt()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(reddIApplication.defaultSubscribeScheduler())
                .subscribe(new Subscriber<ReddIt>() {
                    @Override
                    public void onCompleted() {
                        mainView.setProgressIndicator(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        mainView.setProgressIndicator(false);
                    }

                    @Override
                    public void onNext(ReddIt reddIt) {
                        Log.d(TAG, "onNext() called with: " + "reddIt = [" + reddIt + "]");
                        Data data = reddIt.getData();
                        List<Child> children = data.getChildren();
                        mainView.showReddItPost(children);
                    }
                });
    }


    @Override
    public void loadPosts() {
        loadReddIt();
    }
}
