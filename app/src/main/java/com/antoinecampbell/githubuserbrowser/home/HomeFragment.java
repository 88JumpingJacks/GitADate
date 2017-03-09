package com.antoinecampbell.githubuserbrowser.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

// Import other local classes to interface with other parts of the application
import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.detail.DetailActivity;
import com.antoinecampbell.githubuserbrowser.model.User;
import com.antoinecampbell.githubuserbrowser.model.UsersResponse;
import com.antoinecampbell.githubuserbrowser.service.GithubService;
import com.antoinecampbell.githubuserbrowser.service.ServiceUtils;

import java.util.ArrayList;

// Import libraries used for simplified view injection
import butterknife.ButterKnife;
import butterknife.InjectView;

// Import libraries for handling asynchronous web callbacks
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/*
* The Home Fragment - used by Home Activity
* Fragment to go with the Home View to handle interfacing with the operating system and user
* Creates and loads GridView with local Github user profiles
*/
public class HomeFragment extends Fragment
        implements Callback<UsersResponse>, AdapterView.OnItemClickListener {

    // Used for debugging purposes to TAG where logs are coming from
    private static final String TAG = HomeFragment.class.getSimpleName();

    // Variables used to set number of page specific items and track current page number
    private static final int ITEMS_PER_PAGE = 50;
    private int page; 

    // Purposely set to default visibility so it is available in the test which shares the package
    HomeFragmentGridViewAdapter adapter;

    // Variable to hold a GithubService API instance to get user data
    GithubService githubService;

    // Injecting a new view to hold and display all possible matches
    @InjectView(android.R.id.empty)
    View emptyView;
    @InjectView(R.id.fragment_home_gridview)
    GridView gridView;

    /* 
    * Getter to allow other parts of application to instantiate a new Home Fragment
    */
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    /*
    * Called when the fragment is created, and sets necessary variables to load profiles
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                               // calls create view and other necessary set up functions

        if (savedInstanceState == null) {                                 // if not loading from memory
            page = 1;                                                     // set the starting page to 1
            adapter = null;                                               // resets current adapter
            githubService = ServiceUtils.getGithubService(getActivity()); // get a Github API instance
        }
    }

    /*
    * Called when the view is created, to build layout and setup the user interface / view
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_home, container, false);  // Creates view by building / inflating the home fragment view
        ButterKnife.inject(this, view);                                          //injects inflated view into application instance
        gridView.setOnItemClickListener(this);                                   //pass all interaction to the grid-view object to handle interactions

        return view; 
    }

    /*
    * Called when the application is being loaded from memory
    */
    @Override
    public void onResume() {
        super.onResume();                                               // make all other necessary functions to resume application
        githubService.getCharlotteUsers(page, ITEMS_PER_PAGE, this);    // query Github for users in area, using page and number of results. Return results to this class
    }

     /*
     * Called when a Github user query is successful and the call back returns to this class.
     * fills view with local Github user profiles
     */
    @Override
    public void success(UsersResponse usersResponse, Response response) { 
        // If there are github users on this page who are active then, display profiles
        // If not fill the layout with empty profiles
        if (usersResponse.getItems() != null && getActivity() != null) {
            adapter = new HomeFragmentGridViewAdapter(getActivity(), usersResponse.getItems()); // Fill this page with a new grid filled with the results for this page
            gridView.setAdapter(adapter);                                                       // Change the current view adapter to display the new results
        } else {
            adapter = new HomeFragmentGridViewAdapter(getActivity(), new ArrayList<User>());    // Create a new empty adapter
            gridView.setEmptyView(emptyView);                                                   // Set the view to be empty (Empty Profiles)
            gridView.setEmptyView(emptyView);                                                   // Redundant
        }
    }

    /*
     * Called when a Github user query fails for any reason.
     * Creates an empty adapter to make it seem like no profiles could be found
     */
    @Override
    public void failure(RetrofitError error) {
        adapter = new HomeFragmentGridViewAdapter(getActivity(), new ArrayList<User>());    // Create a new empty adapter
        gridView.setEmptyView(emptyView);                                                   // Set the view to be empty (Empty Profiles)
        gridView.setAdapter(adapter);                                                       // Change the current view adapter to display the empty Profiles
        Log.e(TAG, error.getMessage());                                                     // Log error message with this classes TAG to track the issue
        Log.e(TAG, error.getUrl());                                                         // Log error URL with this classes TAG to track the issue
    }

    /*
    * Called when the user clicks on a git profile.
    * Creates an intent on the profile, and opens a detailed view
    */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User user = (User) parent.getItemAtPosition(position);              // Get the git user profile selected
        Intent intent = new Intent(getActivity(), DetailActivity.class);    // Get the current application thread and set an intent on it to open a detailed view
        intent.putExtras(DetailActivity.newInstanceBundle(user));           // load intent with data from selected profile
        startActivity(intent);                                              // execute loaded intent, display the detailed view
    }
}
