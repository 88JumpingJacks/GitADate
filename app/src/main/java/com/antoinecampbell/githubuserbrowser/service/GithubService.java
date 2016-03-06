package com.antoinecampbell.githubuserbrowser.service;

import com.antoinecampbell.githubuserbrowser.model.User;
import com.antoinecampbell.githubuserbrowser.model.UsersResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface GithubService {

    @GET("/search/users?q=location:Kingston&sort=followers&order=desc")
    void getCharlotteUsers(
            @Query("page")
            int page,
            @Query("per_page")
            int perPage, Callback<UsersResponse> callback);

//    @GET("https://api.github.com/users/{{username}}/")
//    void getUserFollowerCount(
//            @Query("username")
//            String username, Callback<UsersResponse> callback);

    @GET("/users/{{username}}")
    void getUser(
            @Path("username")
            String username, Callback<User> callback);
//
//    @GET("/repos/:owner/:repo/stats/commit_activity")
//    void getCommits(
//            @Query("owner")
//            String owner, Callback<UsersResponse> callback);
}
