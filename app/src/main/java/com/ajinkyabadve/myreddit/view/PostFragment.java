package com.ajinkyabadve.myreddit.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajinkyabadve.myreddit.R;
import com.ajinkyabadve.myreddit.model.Child;
import com.squareup.picasso.Picasso;

/**
 * Created by Ajinkya on 25/07/2016.
 */
public class PostFragment extends Fragment {
    private static final String ARG_CHILD = "child";
    public static final String ARG_TITLE = "postTitle";
    private static final String ARG_THUMBNAIL = "postThumbnail";
    private static final String ARG_POST_LINK = "postPostLink";
    private static final String ARG_CREATOR = "postCreator";
    private String postTitle, thumbnailLink, postLink, creator;
    public static final String BASE_URL_POST = "https://www.reddit.com";
    private TextView textViewTitle, textViewCreator;
    private ImageView imageViewThumbnail;

    private View fragmentView;
    private Context context;

    public PostFragment() {
    }

    public static PostFragment newInstance(@Nullable String postTitle, @Nullable String thumbnailLink, @Nullable String postLink, @Nullable String creator) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, postTitle);
        args.putString(ARG_THUMBNAIL, thumbnailLink);
        args.putString(ARG_POST_LINK, postLink);
        args.putString(ARG_CREATOR, creator);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
//            child = getArguments().getString(ARG_CHILD);// TODO: 25/07/2016 make {@link Child} parceble
            postTitle = getArguments().getString(ARG_TITLE);
            thumbnailLink = getArguments().getString(ARG_THUMBNAIL);
            postLink = getArguments().getString(ARG_POST_LINK);
            creator = getArguments().getString(ARG_CREATOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_post, container, false);
        context = getContext();
        textViewTitle = (TextView) fragmentView.findViewById(R.id.textViewTitle);
        textViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(BASE_URL_POST + postLink));
                startActivity(i);
            }
        });
        textViewCreator = (TextView) fragmentView.findViewById(R.id.textViewCreator);
        imageViewThumbnail = (ImageView) fragmentView.findViewById(R.id.imageViewThumbnail);
        textViewTitle.setText(postTitle == null ? "" : postTitle);
        textViewCreator.setText(creator == null ? "" : creator);
        Picasso.with(getContext())
                .load(thumbnailLink)
                .placeholder(R.drawable.ic_default_view)
                .error(R.drawable.ic_default_view)
                .into(imageViewThumbnail);

        return fragmentView;
    }


}
