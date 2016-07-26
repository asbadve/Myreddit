package com.ajinkyabadve.myreddit.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajinkyabadve.myreddit.R;
import com.squareup.picasso.Picasso;

public class PostFragment extends Fragment {
    public static final String ARG_TITLE = "postTitle";
    private static final String ARG_THUMBNAIL = "postThumbnail";
    private static final String ARG_POST_LINK = "postPostLink";
    private static final String ARG_CREATOR = "postCreator";
    private String postTitle, thumbnailLink, postLink, creator;
    public static final String BASE_URL_POST = "https://www.reddit.com";

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
        setHasOptionsMenu(false);
        if (getArguments() != null) {
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
        View fragmentView = inflater.inflate(R.layout.fragment_post, container, false);
        TextView textViewTitle = (TextView) fragmentView.findViewById(R.id.textViewTitle);
        textViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(BASE_URL_POST + postLink));
                startActivity(i);
            }
        });
        TextView textViewCreator = (TextView) fragmentView.findViewById(R.id.textViewCreator);
        ImageView imageViewThumbnail = (ImageView) fragmentView.findViewById(R.id.imageViewThumbnail);
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
