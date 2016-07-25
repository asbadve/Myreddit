package com.ajinkyabadve.myreddit.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.ajinkyabadve.myreddit.model.Child;
import com.ajinkyabadve.myreddit.model.Data_;
import com.ajinkyabadve.myreddit.view.PostFragment;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Ajinkya on 25/07/2016.
 */
public class ReddItViewPagerAdapter extends FragmentPagerAdapter {
    private final Context context;
    private final List<Child> children;
    private final SparseArray<WeakReference<Fragment>> instantiatedFragments = new SparseArray<>();
    private int pageCount;


    public ReddItViewPagerAdapter(FragmentManager fm, Context context, List<Child> children) {
        super(fm);
        this.context = context;
        this.children = children;
        this.pageCount = children.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final Fragment fragment = (Fragment) super.instantiateItem(container, position);
        instantiatedFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        instantiatedFragments.remove(position);
        super.destroyItem(container, position, object);
    }


    @Override
    public Fragment getItem(int position) {
        Child child = children.get(position);
        Data_ data_ = child.getData();
        String postTitle = data_.getTitle();
        String thumbnailLink = data_.getThumbnail();
        String postLink = data_.getPermalink();
        String creator = data_.getAuthor();
        return PostFragment.newInstance(postTitle, thumbnailLink, postLink, creator);
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
