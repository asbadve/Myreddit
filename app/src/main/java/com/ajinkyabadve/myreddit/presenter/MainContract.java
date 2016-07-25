package com.ajinkyabadve.myreddit.presenter;

import com.ajinkyabadve.myreddit.model.Child;

import java.util.List;

/**
 * Created by Ajinkya on 25/07/2016.
 */
public interface MainContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showReddItPost(List<Child> child);

        void NetworkNotAvailable();
    }

    interface UserActionsListener {
        void loadPosts();
    }
}
