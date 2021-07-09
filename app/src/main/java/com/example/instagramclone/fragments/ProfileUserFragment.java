package com.example.instagramclone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.GridAdapter;
import com.example.instagramclone.Post;
import com.example.instagramclone.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProfileUserFragment extends Fragment {

    protected List<Post> userPosts;
    GridView gvPosts;
    GridAdapter adapter;
    TextView tvUsername;
    ImageView ivProfile;
    private Post post;
    public static final String KEY_PROFILE = "profilePic";
    public static final String TAG = "ProfileUserFragment";

    public ProfileUserFragment() {
        // Required empty public constructor
    }

    public static ProfileUserFragment newInstance(Post post) {
        ProfileUserFragment fragment = new ProfileUserFragment();
        Bundle args = new Bundle();
        args.putParcelable("itemPost", post);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        post = (Post) getArguments().getParcelable("itemPost");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivProfile = view.findViewById(R.id.ivProfile);
        tvUsername = view.findViewById(R.id.tvUsername);
        gvPosts = view.findViewById(R.id.gvPosts);
        userPosts = new ArrayList<>();
        adapter = new GridAdapter(getContext(), userPosts);
        gvPosts.setAdapter(adapter);
        queryPosts();

        ParseUser user = post.getUser();
        tvUsername.setText(user.getUsername());
        ParseFile image = user.getParseFile(KEY_PROFILE);

        if (image != null) {
            Glide.with(getContext()).load(image.getUrl()).into(ivProfile);
        } else {
            Glide.with(getContext()).load(R.drawable.pfp).into(ivProfile);
        }
    }

    protected void queryPosts() {
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // Specify what other data we would like to get back
        query.include(Post.KEY_USER);
        // Get current users posts only
        query.whereEqualTo(Post.KEY_USER, post.getUser());
        // Get most recent posts at top
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        // Get all the posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                userPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
}