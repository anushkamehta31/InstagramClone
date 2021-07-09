package com.example.instagramclone;

import android.os.Parcelable;
import android.util.Log;

import com.example.instagramclone.fragments.PostDetailsFragment;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.security.Key;
import java.util.Date;


@ParseClassName("Post") // Should match exactly what we named the entity in the Parse dashboard (in this case Post)
public class Post extends ParseObject implements Parcelable {

    // Need to define getters and setters for each Key that we have defined (names of Parse columns)
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    private static final String TAG = "Post";

    public String getDescription() {
        return getString(KEY_DESCRIPTION); // Will get the description on the Parse object
    }

    // Set description
    public  void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    // Get image
    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    // Set image
    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    // Get user
    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    // Set the user
    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

}
