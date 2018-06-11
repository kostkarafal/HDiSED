package pl.kostkamatloch.nfcreader;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import pl.kostkamatloch.nfcreader.controller.RestController;
import pl.kostkamatloch.nfcreader.controller.VolleyGetCallback;
import pl.kostkamatloch.nfcreader.model.webservice.NfcTag;

import static pl.kostkamatloch.nfcreader.MainActivity.actualLocation;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

     MapView mapView;
     GoogleMap gmap;
    RestController restController;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyBRLno0P7DWKlEwSfTIuj86c-tHCnq9YBs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        restController = new RestController(this);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        restController.getAllTags(new VolleyGetCallback() {
            @Override
            public void onSucces(List<NfcTag> tags) {
                LatLng cameraPosition = new LatLng(tags.get(0).getLatitude(),tags.get(0).getLongitude());
                for(NfcTag tag : tags)
                {
                    LatLng marker = new LatLng(tag.getLatitude(),tag.getLongitude());
                    gmap.addMarker(new MarkerOptions().position(marker).title(tag.getId().toString()));
                }
                gmap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition));
            }
            @Override
            public  void onFailure(VolleyError error){
                Toast.makeText(MapActivity.this,"Brak połączenia z serverem",Toast.LENGTH_LONG).show();
            }
        });


    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

        gmap.setMinZoomPreference(9);
        LatLng actualLatLng = new LatLng(actualLocation.getLatitude(), actualLocation.getLongitude());

        gmap.moveCamera(CameraUpdateFactory.newLatLng(actualLatLng));
    }
}