package com.example.instagramclone;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    // Adapter for grid display on profile pictures
    Context context;
    private LayoutInflater inflater;
    private List<Post> userPosts;
    ImageView ivGridImage;

    public GridAdapter(Context context, List<Post> userPosts) {
        this.context = context;
        this.userPosts = userPosts;
    }

    @Override
    public int getCount() {
        return userPosts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, null);
        }
        // Get the reference and current post
        ivGridImage = convertView.findViewById(R.id.ivGridImage);
        Post post = userPosts.get(position);
        ParseFile image = post.getImage();

        // if the image is not null place it in the grid
        if (image != null) {
            Glide.with(context).load(image.getUrl()).into(ivGridImage);
        }
        return convertView;
    }
}
