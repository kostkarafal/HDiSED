package pl.kostkamatloch.nfcreader.controller;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import pl.kostkamatloch.nfcreader.MainActivity;

/**
 * Created by Rafal on 11.06.2018.
 */

public class GpsLocation {

//get adress from geographic coordinates
    public static String getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(MainActivity.context, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);


            Log.v("IGA", "Address " + add);

            return add;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return "";
    }
}
