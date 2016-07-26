package com.ajinkyabadve.myreddit.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ajinkyabadve.myreddit.ReddIApplication;
import com.ajinkyabadve.myreddit.ReddItService;
import com.ajinkyabadve.myreddit.Util;
import com.ajinkyabadve.myreddit.model.Child;
import com.ajinkyabadve.myreddit.model.Data;
import com.ajinkyabadve.myreddit.model.Data_;
import com.ajinkyabadve.myreddit.model.ReddIt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Ajinkya on 25/07/2016.
 */
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

    private List<Child> parseJason(String JasonString) {
        List<Child> children = new ArrayList<Child>();
        try {
            JSONObject jsonObject = new JSONObject(JasonString);
            JSONObject dataObject = jsonObject.getJSONObject("data");

            JSONArray childrenJSONArray = dataObject.getJSONArray("children");
            int size = childrenJSONArray.length();
            for (int i = 0; i < size; i++) {
                Child child = new Child();
                Data_ data_ = new Data_();
                JSONObject childrenJSONObject = childrenJSONArray.getJSONObject(i);
                JSONObject dataJsonObject = childrenJSONObject.getJSONObject("data");
                data_.setTitle(dataJsonObject.getString("title"));
                data_.setThumbnail(dataJsonObject.getString("thumbnail"));
                data_.setPermalink(dataJsonObject.getString("permalink"));
                data_.setAuthor(dataJsonObject.getString("author"));
                child.setData(data_);
                children.add(child);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return children;
    }


    @Override
    public void loadPosts() {
        loadReddIt();
    }
}
