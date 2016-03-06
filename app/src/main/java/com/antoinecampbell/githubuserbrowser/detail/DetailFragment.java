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
import java.util.List;

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
    private static int followers;
    private static List<String> languagesList;
    private static List<String> projectsList = new ArrayList<>();
    @InjectView(R.id.fragment_detail_avatar_imageview)
    ImageView avatarImageView;

    @InjectView(R.id.followersid) TextView txtVw_followers;

    @InjectView(R.id.languagesid) TextView txtVw_languages;

    @InjectView(R.id.projectsid) TextView txtVw_projects;

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

//        Log.i("args", "Followers " + user.getFollowers());
        Log.i("args", "FollowersURL " + user.getFollowersUrl());
//        Log.i("args", " " + user.getU());

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
                languagesList = new ArrayList<String>(0);
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

                        if (currentLang != null && !languagesList.contains(currentLang))
                        {
                            languagesList.add(currentLang);
                            Log.i("arg", "language added: " + currentLang);
                        }
                    }

                    for (String lang : languagesList)
                    {
                        if (lang == null)
                        {
                            languagesList.remove(lang);
                        }
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                projectsList = names;

                Log.i("args", "JSONArray names" + Arrays.toString(names.toArray()));
                Log.i("args", "forks_count " + forks_count);

                if (languagesList.size() != 0)
                {
                    Log.i("args", "languages " + Arrays.toString(languagesList.toArray()));
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
                    Log.i("args", "jsonString: " + jsonString);
                    Log.i("args", "# followers " + jo.optInt("followers"));

                    followers = jo.optInt("followers");

                    Log.i("args", "followers in onResponse " + followers);
                    Log.i("args", "e-mail " + jo.optString("email"));
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

        Log.i("args", "***followers " + followers);
        txtVw_followers.setText(getText(R.string.followers) + ": " + followers);

//        String userLangs = "";
//        Object[] langArray = languagesList.toArray();
//        for (int i = 0; i < langArray.length; i++)
//        {
//            if (langArray[i] != null)
//            {
//                userLangs += (String) langArray[i];
//            }
//        }

        try
        {
            if (languagesList.size() > 0)
            {
                Log.i("args", "***languagesList" + Arrays.toString(languagesList.toArray()));

                String[] langArray = Arrays.copyOf(languagesList.toArray(), languagesList.toArray().length, String[].class);
                String langStr = "";
//                for (int i = 0; i < languagesList.size(); i++)
                for (int i = 0; i < langArray.length; i++)
                {
//                    if (languagesList.get(i) != null && i < languagesList.size() - 1)
                    if (langArray[i] != null && !langArray[i].equals("null") && i < langArray.length - 1)
                    {
//                        langStr += languagesList.get(i) + ", ";
                        langStr += langArray[i] + ", ";
                    }
                    else if (langArray[i] != null && !langArray[i].equals("null"))
                    {
                        // Last language, don't add a comma after
//                        langStr += languagesList.get(i);
                        langStr += langArray[i];
                    }
                }

                Log.i("args", "langStr " + langStr);
                txtVw_languages.setText(getText(R.string.languages) + ": " + langStr);
            }
            else
            {
                txtVw_languages.setText("This user has no preferred language");
            }
        }
        catch (NullPointerException e)
        {
            txtVw_languages.setText("This user has no preferred language");
        }


        // Display user's projects
        String strProjects = "";
        for (int i = 0; i < projectsList.size(); i++)
        {
            if (i < projectsList.size() - 1)
            {
                strProjects += projectsList.get(i) + ", ";
            }
            else
            {
                // Last project, don't print a comma after
                strProjects += projectsList.get(i);
            }
        }

        if (!strProjects.isEmpty())
        {
            txtVw_projects.setText(getText(R.string.projects) + ": " + strProjects);
        }
        else
        {
            txtVw_projects.setText("No projects to show");
        }


        return view;
    }
}
