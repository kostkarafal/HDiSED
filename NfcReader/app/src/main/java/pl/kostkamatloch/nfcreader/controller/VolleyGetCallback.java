package pl.kostkamatloch.nfcreader.controller;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import pl.kostkamatloch.nfcreader.model.webservice.NfcTag;

/**
 * Created by Rafal on 08.06.2018.
 */

public interface VolleyGetCallback {
    void onSucces(List<NfcTag> tags);

    void onFailure(VolleyError error);

}
