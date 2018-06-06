package pl.kostkamatloch.nfcreader.controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import pl.kostkamatloch.nfcreader.model.webservice.Tag;

/**
 * Created by Rafal on 06.06.2018.
 */

public class TagController {
    static private String RestUrl = "http://192.168.8.127:8080/rest/tag";
    private Tag tag;
    private Context context;

    public TagController(Context context)
    {
        this.context = context;
    }

    public void getTag(int id)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                RestUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                }

        );

        requestQueue.add(objectRequest);

    }


    public void getAllTags()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final Type listType = new TypeToken<List<Tag>>() {
        }.getType();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET, RestUrl,
                null,
                response -> {
                    Log.e("Rest GET Response", response.toString());
                    List<Tag> tags;
                    tags = new Gson().fromJson(response.toString(),listType);

                    for(Tag tag : tags)
                    {
                        Log.e("Name:",tag.getName());
                    }
                },
                error -> Log.e("Rest GET Response", error.toString())


        );

        requestQueue.add(arrayRequest);
    }

    public void addTag(String name,String content,String description, String localization)  {
        Tag tag = new Tag(name,content,description,localization);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonTag = null;
        try {
            jsonTag = new JSONObject(new Gson().toJson(tag));
            Log.e("Json Tag",jsonTag.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    RestUrl,
                    jsonTag,
                    response -> Log.e("Rest POST Response", response.toString()),
                    error -> Log.e("Rest POST Response", error.toString())
            );
            requestQueue.add(objectRequest);



    }


}
