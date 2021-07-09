package com.example.instagramclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.fragments.PostDetailsFragment;
import com.example.instagramclone.fragments.ProfileUserFragment;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

import static com.example.instagramclone.fragments.PostsFragment.minID;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    public static final String KEY_PROFILE = "profilePic";


    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private ImageView ivProfile;
        private ImageView ivHeart;
        boolean liked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            ivHeart = itemView.findViewById(R.id.ivHeart);

            // OnClick listener to view the post in detail
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity activity = (MainActivity) context;
                    FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                    PostDetailsFragment fragment = PostDetailsFragment.newInstance(posts.get(getAdapterPosition()));
                    ft.replace(R.id.flContainer, fragment);
                    ft.commit();
                }
            });

            // Set on click listeners to profile pic and username to open profile page
            ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openProfile();
                }
            });

            tvUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openProfile();
                }
            });

        }

        // Function to open the profile page of a user if either the profile picture or username
        // is selected
        private void openProfile() {
            MainActivity activity = (MainActivity) context;
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ProfileUserFragment fragment = ProfileUserFragment.newInstance(posts.get(getAdapterPosition()));
            ft.replace(R.id.flContainer, fragment);
            ft.commit();
        }



        public void bind(Post post) {
            // Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            liked = false;
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }

            // Check that the id is smaller than the minID and update accordingly to user
            // endless pagination
            Long id = Long.valueOf(posts.get(getAdapterPosition()).getCreatedAt().getTime());
            if (id < minID) minID = id;

            // Get the correct user and profile image
            ParseUser user = post.getUser();
            ParseFile profileImage = user.getParseFile(KEY_PROFILE);

            // If the user does not have a profile picture use the default
            if (profileImage != null) {
                Glide.with(context).load(profileImage.getUrl()).into(ivProfile);
            } else {
                Glide.with(context).load(R.drawable.pfp).into(ivProfile);
            }

            ivHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (liked) ivHeart.setImageResource(R.drawable.ufi_heart);
                    else {
                        ivHeart.setImageResource(R.drawable.ufi_heart_active);
                    }
                    liked = !liked;
                }
            });

        }


    }
}
