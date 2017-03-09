package com.antoinecampbell.githubuserbrowser.home;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.antoinecampbell.githubuserbrowser.R;                          // Import GitADate recourses 
import com.antoinecampbell.githubuserbrowser.model.User;                 // Import GitADate-User model 
import com.antoinecampbell.githubuserbrowser.service.ServiceUtils;       // Import GitADate-Github services 

import com.squareup.picasso.Picasso;                                     // Import Picasso image processing engine                            

import java.util.List;


/*
* The Home Fragment Grid View Adapter - used by Home Fragment
* Called by Home Fragment to create and interface with the Git profiles.
* Receives git profiles and fabricates the according view fragment.
*/
public class HomeFragmentGridViewAdapter extends BaseAdapter {


    // Used for debugging purposes to TAG where logs are coming from
    private static final String TAG = HomeFragmentGridViewAdapter.class.getSimpleName();

    // Used to keep track of current layout information and git profiles
    private LayoutInflater mLayoutInflater;    // Current layout and views
    private List<User> users;                  // Git profiles on page
    private int mImageSize;                    // Profile image size
    private Picasso mPicasso;                  // Image processing library

    /* Getter
    * Used to get current view information for the Git date profiles
    */
    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    /* Constructor
    * Takes application context and list of Git user profile
    * Constructs a scrollable grid of profiles to be displayed
    */
    public HomeFragmentGridViewAdapter(Context context, List<User> users) {

        this.users = users;                                                                 // Set global profiles
        mLayoutInflater = LayoutInflater.from(context);                                     // Create layout in application context to be viewable

        // Scale the images such that they scale per device and fit in the 'profile cards', that will be used
        int imageSize = context.getResources().getInteger(R.integer.cardview_iamge_size);   // Get the scaled card size that the image will be placed in to make the profile
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();         // Get display metrics of current device for appropriate scaling
        mImageSize = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageSize, displayMetrics);    // Set the image size (global), using the scaled card size and phone display metrics
        
        Log.d(TAG, "ImageSize: " + mImageSize);                                             // Log with this classes tag the current scaled image size                     

        // Get the image drawing context of the application
        mPicasso = ServiceUtils.getPicasso(context);
    }

    /* Getter (overrides default baseAdapter)
    * Used to get current grid information
    * 
    * returns: The number of profiles displayed
    */
    @Override
    public int getCount() {
        return users.size();
    }

    /* Getter (overrides default baseAdapter)
    * Used to get the profile in the grid spot selected
    * 
    * returns: The profile at the selected grid location
    */
    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    /* Getter (overrides default baseAdapter)
    * Used to get the hash-code of the profile in the grid spot selected
    * 
    * returns: The hash unique to the profile at the selected grid location
    */
    @Override
    public long getItemId(int position) {
        return users.get(position).hashCode();
    }

    /* Getter (overrides default baseAdapter)
    * Used when a profile is selected to fill the screen with the profile
    * 
    * returns: The view object that will cover the screen with the selected profile
    */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Background view buffer
        ViewHolder holder;

        // If there is no pre-existing  view to replace
        if (convertView == null) {

            convertView = mLayoutInflater.inflate(R.layout.home_grid_item, parent, false);      // Create a new profile display
            holder = new ViewHolder();                                                          // Create a new buffer
            holder.imageView =
                    (ImageView) convertView
                            .findViewById(R.id.home_grid_item_avatar_imageview);                // Set the default profile avatar
            holder.textView =
                    (TextView) convertView
                            .findViewById(R.id.home_grid_item_username_textview);               // Set the default user-name

            convertView.setTag(holder);                                                         // Add the buffer to the view
        
        // If there is a pre-existing view
        } else {
            holder = (ViewHolder) convertView.getTag();                                         // Get the buffer from the view                                        
        }

        // Replace the profile display in the buffer with the selected one
        User user = getItem(position);                                                          // Get selected profile
        holder.textView.setText(user.getLogin());                                               // Get selected profile user-name
        // If the user has a profile picture get it if not then use the one already loaded
        if (!TextUtils.isEmpty(user.getAvatarUrl())) {
            Uri imageUri = ServiceUtils
                    .getSizedImageUri(parent.getContext(), user.getAvatarUrl(), mImageSize);    // Get the URI for the profile image
            mPicasso.load(imageUri).into(holder.imageView);                                     // Load the image into the buffer
        }

        // return the View with the updated buffered profile to be shown
        return convertView;
    }


}
