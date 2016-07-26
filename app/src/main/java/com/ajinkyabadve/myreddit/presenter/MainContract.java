package com.ajinkyabadve.myreddit.presenter;

import com.ajinkyabadve.myreddit.model.Child;

import java.util.List;

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
