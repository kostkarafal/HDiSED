package pl.kostkamatloch.nfcreader.controller;

import android.content.Context;
import android.util.Log;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
import java.util.Date;
import java.util.List;

import pl.kostkamatloch.nfcreader.R;
import pl.kostkamatloch.nfcreader.model.webservice.NfcTag;
import pl.kostkamatloch.nfcreader.model.webservice.TestNfcTag;

/**
 * Created by Rafal on 06.06.2018.
 */

public class RestController {
    public static  String RestUrl = "http://192.168.8.127:8080/rest/tag";
    private Context context;
    private NfcTag tag;
    //private final List<NfcTag> tags;
    public static List<NfcTag> staticTags;
    public RestController(Context context)
    {
        this.context = context;
    }

    public RestController(Context context, NfcTag tag)
    {
        this.context = context;
        this.tag = tag;
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


    public void getAllTags(ListView listView)
    {
        //final List<NfcTag> tags = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final Type listType = new TypeToken<List<NfcTag>>() {
        }.getType();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET, RestUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<NfcTag> tags = new Gson().fromJson(response.toString(), listType);
                        staticTags = tags;

                        Log.e("Rest GET Response", response.toString());
                        ArrayList<String> tagList = new ArrayList<>();

                        for(NfcTag tag : tags)
                        {
                            String adress = GPSTracker.getAddress(tag.getLatitude(),tag.getLongitude());
                            StringBuilder date = new StringBuilder();
                            date.append(tag.getDate());
                            date.replace(10,11," ");
                            date.delete(16,date.length());
                            tagList.add(date+"\nID: "+tag.getId().toString()+" TagID: "+tag.getIdTag()
                                    +"Technologies: "+tag.getTechnologies()+"\nDescription: "+tag.getDescription()
                                    +"\nAdress: "+adress);

                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.list_item,tagList);
                        listView.setAdapter(adapter);
                    }
                },
                error -> Log.e("Rest GET error Response", error.toString())

        );
        requestQueue.add(arrayRequest);
    }

    public void addTag(String name,String content,String description, double latitude, double longitude)  {
        TestNfcTag testTag = new TestNfcTag(name,content,description,latitude,longitude);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonTag = null;
        try {
            jsonTag = new JSONObject(new Gson().toJson(testTag));
            Log.e("Json TestNfcTag",jsonTag.toString());
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

    public void addTag(NfcTag tag,String description)
    {
        tag.setDescription(description);
        GPSTracker gps = new GPSTracker(context);

        // Check if GPS enabled
        if(gps.canGetLocation()) {

           tag.setLatitude(gps.getLatitude());
            tag.setLongitude(gps.getLongitude());

            } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonTag = null;
        try {
            jsonTag = new JSONObject(new Gson().toJson(tag));
            Log.e("Json TestNfcTag",jsonTag.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                RestUrl,
                jsonTag,
                response -> {Log.e("Rest POST Response", response.toString());
                                Toast.makeText(context,"Dodano tag NFC.",Toast.LENGTH_SHORT).show();
                            },
                error -> {Log.e("Rest POST Response", error.toString());
                    Toast.makeText(context,"Błąd połączenia z serverem.",Toast.LENGTH_SHORT).show();
                    }
        );
        requestQueue.add(objectRequest);


    }


}
