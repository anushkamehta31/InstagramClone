package com.example.instagramclone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.Post;
import com.example.instagramclone.R;
import com.parse.ParseFile;

import static android.text.format.DateUtils.getRelativeTimeSpanString;

public class PostDetailsFragment extends Fragment {

    private Post post;
    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    private TextView getTvDescription;
    private ImageButton ibBack;

    private TextView tvTimeStamp;
    public static final String TAG = "PostDetailsFragmentCompose";

    public PostDetailsFragment() {
        // Required empty public constructor
    }

    public static PostDetailsFragment newInstance(Post post) {
        // Create a fragment with arguments
        PostDetailsFragment fragment = new PostDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("itemPost", post);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve arguments passed
        post = (Post) getArguments().getParcelable("itemPost");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvUsername = view.findViewById(R.id.tvUsername);
        ivImage = view.findViewById(R.id.ivImage);
        tvDescription = view.findViewById(R.id.tvDescription);
        ibBack = view.findViewById(R.id.ibBack);
        tvTimeStamp = view.findViewById(R.id.tvTimeStamp);

        // Populate all of the views
        tvDescription.setText(post.getDescription());
        tvUsername.setText(post.getUser().getUsername());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(getContext()).load(image.getUrl()).into(ivImage);
        }

        // Return to home screen button
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = new PostsFragment();
                fragmentManager.replace(R.id.flContainer, fragment).commit();
            }
        });

        // Get the time stamp from DateUtils and set it in the textview
        long time = post.getCreatedAt().getTime();
        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(time);
        tvTimeStamp.setText(timeAgo);
    }

}