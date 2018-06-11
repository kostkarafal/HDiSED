package pl.kostkamatloch.nfcreader.controller;

import com.android.volley.VolleyError;

import java.util.List;

import pl.kostkamatloch.nfcreader.model.webservice.NfcTag;

/**
 * Created by Rafal on 08.06.2018.
 */

public interface VolleyGetCallback {
    void onSucces(List<NfcTag> tags);

    void onFailure(VolleyError error);

}
