package pl.kostkamatloch.nfcreader.controller;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Rafal on 08.06.2018.
 */
//nessecary interface to be able to get data from REST response
public interface VolleyPostCallback {

    void onSucces(JSONObject response);
    void onFailure(VolleyError error);
}
