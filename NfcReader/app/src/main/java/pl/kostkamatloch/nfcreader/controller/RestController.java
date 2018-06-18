package pl.kostkamatloch.nfcreader.controller;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pl.kostkamatloch.nfcreader.model.webservice.NfcTag;


/**
 * Created by Rafal on 06.06.2018.
 */

public class RestController {
    public static  String RestUrl = "https://nfcwebservice.appspot.com/rest/tag";
    private Context context;

    public RestController(Context context)
    {
        this.context = context;
    }





    public void getAllTags(final VolleyGetCallback callback)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final Type listType = new TypeToken<List<NfcTag>>() {
        }.getType();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET, RestUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<NfcTag> tags = new Gson().fromJson(response.toString(), listType);
                        Log.e("Rest GET Response", response.toString());
                        callback.onSucces(tags);
                    }
                },
                error -> callback.onFailure(error)
        );
        requestQueue.add(arrayRequest);
    }


    public void addTag(NfcTag tag, String description, Location location, VolleyPostCallback callback)
    {
        tag.setDescription(description);
        tag.setLatitude(location.getLatitude());
        tag.setLongitude(location.getLongitude());


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonTag = null;
        try {
            jsonTag = new JSONObject(new Gson().toJson(tag));
            Log.e("Json TestNfcTag",jsonTag.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,RestUrl,
                            jsonTag,
                            (JSONObject response) -> callback.onSucces(response),
                            (VolleyError error) -> callback.onFailure(error)
                            );

        requestQueue.add(objectRequest);
    }


    public static void deleteTag(String id, Context context)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                RestUrl+"/"+id,
                null,
                response -> Log.e("Rest Response", response.toString()),
                error -> Log.e("Rest Response", error.toString())

        );

        requestQueue.add(objectRequest);

    }

    public void getTag(int id)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                RestUrl,
                null,
                response -> Log.e("Rest Response", response.toString()),
                error -> Log.e("Rest Response", error.toString())

        );

        requestQueue.add(objectRequest);

    }


}
