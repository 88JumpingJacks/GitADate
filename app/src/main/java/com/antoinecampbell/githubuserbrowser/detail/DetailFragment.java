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
import java.util.Collections;
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
//
//        // todo remove
////        String jsonString = "{[\n" +
////                "  {\n" +
////                "    \"id\": 18221276,\n" +
////                "    \"name\": \"git-consortium\",\n" +
////                "    \"full_name\": \"octocat/git-consortium\",\n" +
////                "    \"owner\": {\n" +
////                "      \"login\": \"octocat\",\n" +
////                "      \"id\": 583231,\n" +
////                "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/583231?v=3\",\n" +
////                "      \"gravatar_id\": \"\",\n" +
////                "      \"url\": \"https://api.github.com/users/octocat\",\n" +
////                "      \"html_url\": \"https://github.com/octocat\",\n" +
////                "      \"followers_url\": \"https://api.github.com/users/octocat/followers\",\n" +
////                "      \"following_url\": \"https://api.github.com/users/octocat/following{/other_user}\",\n" +
////                "      \"gists_url\": \"https://api.github.com/users/octocat/gists{/gist_id}\",\n" +
////                "      \"starred_url\": \"https://api.github.com/users/octocat/starred{/owner}{/repo}\",\n" +
////                "      \"subscriptions_url\": \"https://api.github.com/users/octocat/subscriptions\",\n" +
////                "      \"organizations_url\": \"https://api.github.com/users/octocat/orgs\",\n" +
////                "      \"repos_url\": \"https://api.github.com/users/octocat/repos\",\n" +
////                "      \"events_url\": \"https://api.github.com/users/octocat/events{/privacy}\",\n" +
////                "      \"received_events_url\": \"https://api.github.com/users/octocat/received_events\",\n" +
////                "      \"type\": \"User\",\n" +
////                "      \"site_admin\": false\n" +
////                "    }]}";
//
//        String jsonString = "{\n" +
//                "    \"glossary\": {\n" +
//                "        \"title\": \"example glossary\",\n" +
//                "\t\t\"GlossDiv\": {\n" +
//                "            \"title\": \"S\",\n" +
//                "\t\t\t\"GlossList\": {\n" +
//                "                \"GlossEntry\": {\n" +
//                "                    \"ID\": \"SGML\",\n" +
//                "\t\t\t\t\t\"SortAs\": \"SGML\",\n" +
//                "\t\t\t\t\t\"GlossTerm\": \"Standard Generalized Markup Language\",\n" +
//                "\t\t\t\t\t\"Acronym\": \"SGML\",\n" +
//                "\t\t\t\t\t\"Abbrev\": \"ISO 8879:1986\",\n" +
//                "\t\t\t\t\t\"GlossDef\": {\n" +
//                "                        \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\n" +
//                "\t\t\t\t\t\t\"GlossSeeAlso\": [\"GML\", \"XML\"]\n" +
//                "                    },\n" +
//                "\t\t\t\t\t\"GlossSee\": \"markup\"\n" +
//                "                }\n" +
//                "            }\n" +
//                "        }\n" +
//                "    }\n" +
//                "}";
//
//        String data = "";
//        try
//        {
//            Log.i("args", "in try");
//            JSONObject jsonObj = new JSONObject(jsonString);
//
//            JSONArray jsonArray = jsonObj.optJSONArray("glossary");
//
//            //Iterate the jsonArray and print the info of JSONObjects
//            for(int i=0; i < jsonArray.length(); i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
////                int id = Integer.parseInt(jsonObject.optString("id").toString());
//                String name = jsonObject.optString("GlossTerm").toString();
////                float salary = Float.parseFloat(jsonObject.optString("salary").toString());
//
////                data += "Node"+i+" : \n id= "+ id +" \n Name= "+ name +" \n Salary= "+ salary +" \n ";
//                data += name;
//            }
//
//            Log.i("args", "Data" + data);
//            fragment.setText(data);
//        }
//        catch (JSONException e)
//        {
//            e.printStackTrace();
//        }

//        OkHttp.getHttp();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/users/octocat/repos")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                List<String> names = new ArrayList<String>();

                try
                {
                    String jsonString = response.body().string();
                    JSONArray ja = new JSONArray(jsonString);
                    names = new ArrayList<>();

                    for (int i = 0;i<ja.length();i++){
                        names.add( ((JSONObject) ja.get(i) ).getString("name")  );
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                Log.i("args", "JSONArray names" + Arrays.toString(names.toArray()));

            }

//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                List<String> names = new ArrayList<String>();
//
//                try
//                {
//                    String jsonString = response.body().toString();
//                    JSONArray ja = new JSONArray(jsonString);
//                    names = new ArrayList<>();
//
//                    for (int i = 0;i<ja.length();i++){
//                        names.add( ((JSONObject) ja.get(i) ).getString("name")  );
//                    }
//                }
//                catch (JSONException e)
//                {
//                    e.printStackTrace();
//                }
//
//                Log.i("args", Arrays.toString(names.toArray()));
//            }
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

        return view;
    }

    public void setText(String text){
        TextView textView = (TextView) getView().findViewById(R.id.txt_vw_user_info);
        textView.setText(text);
    }
}
