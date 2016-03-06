package com.antoinecampbell.githubuserbrowser.detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.model.User;
import com.antoinecampbell.githubuserbrowser.service.GithubService;
import com.antoinecampbell.githubuserbrowser.service.ServiceUtils;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;

public class DetailFragment extends Fragment {

    private static final String KEY_ARG_USER = "KEY_ARG_USER";
    private User user;

    @InjectView(R.id.fragment_detail_avatar_imageview)
    ImageView avatarImageView;

    public static DetailFragment newInstance(User user) {
        Log.i("args", "in newInstance() method!!!");

        DetailFragment fragment = new DetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(KEY_ARG_USER, user);

//        JSONObject jsonObject = new JSONObject("18221276");

        Log.i("args", args.toString());
        Log.i("args", "URL " + user.getUrl());
        Log.i("args", "login " + user.getLogin());
        Log.i("args", "# Repos " + user.getPublicRepos());
        Log.i("args", "Followers " + user.getFollowers());
        Log.i("args", "FollowersURL " + user.getFollowersUrl());
//        Log.i("args", " " + user.getU());
//        Log.i("args", "Projects " + user.);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/users/" + user.getLogin() + "/repos")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                List<String> names = new ArrayList<String>(0);
                Set<String> languages = new HashSet<String>(0);
                int forks_count = 0;

                try
                {
                    String jsonString = response.body().string();
                    JSONArray ja = new JSONArray(jsonString);
                    names = new ArrayList<>();

                    for (int i = 0;i<ja.length();i++){
                        names.add(((JSONObject) ja.get(i)).getString("name"));


//                        Log.i("args", "forks " + ((JSONObject) ja.get(i)).get("forks"));
//                        currentFCount = Integer.getInteger(((JSONObject) ja.get(i)).getString("forks_count"));

//                        Log.i("args", "yo" + ((JSONObject) ja.get(i)).get("forks").getClass());
                        forks_count += (Integer) (((JSONObject) ja.get(i)).get("forks_count"));

                        String currentLang = ((JSONObject) ja.get(i)).getString("language");

                        if (currentLang != null)
                        {
                            languages.add(currentLang);
                            Log.i("arg", "language added: " + currentLang);
                        }
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                Log.i("args", "JSONArray names" + Arrays.toString(names.toArray()));
                Log.i("args", "forks_count " + forks_count);

                if (languages.size() != 0)
                {
                    Log.i("args", "languages " + Arrays.toString(languages.toArray()));
                }
                else
                {
                    Log.i("args", "This user has not listed any languages");
                }
            }
        });


        request = new Request.Builder()
                .url("https://api.github.com/users/" + user.getLogin())
                .build();

        call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try
                {
                    String jsonString = response.body().string();
                    JSONObject jo = new JSONObject(jsonString);
                    Log.i("args", "# followers " + jo.optInt("followers"));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = getArguments().getParcelable(KEY_ARG_USER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.inject(this, view);

        Picasso picasso = ServiceUtils.getPicasso(getActivity());
        int imageSize = getActivity().getResources().getInteger(R.integer.cardview_iamge_size);
        Uri imageUri = ServiceUtils.getSizedImageUri(getActivity(), user.getAvatarUrl(), imageSize);
        picasso.load(imageUri).into(avatarImageView);
//
//        GithubService githubService = ServiceUtils.getGithubService(getActivity());
//        githubService.getCommits(user.toString(), this);

        return view;
    }

    public void setText(String text){
        TextView textView = (TextView) getView().findViewById(R.id.txt_vw_user_info);
        textView.setText(text);
    }
}
