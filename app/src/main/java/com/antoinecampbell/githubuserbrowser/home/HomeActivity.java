package com.antoinecampbell.githubuserbrowser.home;

import android.os.Bundle;                           // Import Android Operating System 
import android.support.v7.app.ActionBarActivity;    // Import Android Application Activity 
import com.antoinecampbell.githubuserbrowser.R;     // Import GitADate recourses 

/*
 * The Home View | The Default View
 * The initial action that occurs when the application is opened
 * Is an extension of the Android Activity type, allowing for system and user interaction
 */
public class HomeActivity extends ActionBarActivity {

    /*
     * App Launch - Activity
     * Sets the default layout of the application to the home layout resource.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);         // Run default onCreate actions
        setContentView(R.layout.activity_home);     // Change the mobile view to the home layout

        // If a new instantiation is occurring then set up the Home Activity Fragments for the instance
        if (savedInstanceState == null) {                                                       //if new instance of application

            getSupportFragmentManager().beginTransaction()                                      // Begin an interaction with the applications Fragment Manager
                    .replace(R.id.activity_home_fragment_container,                                    
                            HomeFragment.newInstance(), HomeFragment.class.getSimpleName())     // Replace the default Fragment Manager with the HomeFragment Class    
                    .commit();                                                                  // Finalize and initiate change/transaction 
        }
    }

}
