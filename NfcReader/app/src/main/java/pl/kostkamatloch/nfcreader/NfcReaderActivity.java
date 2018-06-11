package pl.kostkamatloch.nfcreader;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Location;
import android.nfc.NfcAdapter;

import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import pl.kostkamatloch.nfcreader.controller.NfcIntent;
import pl.kostkamatloch.nfcreader.controller.RestController;
import pl.kostkamatloch.nfcreader.controller.VolleyGetCallback;
import pl.kostkamatloch.nfcreader.controller.VolleyPostCallback;


public class NfcReaderActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private TextView textView;
    private RestController restController;
    private EditText editTextDescription;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location actualLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_reader);
        textView = findViewById(R.id.textNfc);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Button buttonAddTag = findViewById(R.id.buttonAddTag);
        editTextDescription = findViewById(R.id.editTextDescr);
        restController = new RestController(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        buttonAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NfcIntent.nfcTag != null)
                    if (actualLocation != null) {
                        restController.addTag(NfcIntent.nfcTag, editTextDescription.getText().toString(),actualLocation, new VolleyPostCallback() {

                            @Override
                            public void onSucces(JSONObject response) {
                                Toast.makeText(NfcReaderActivity.this, "Dodano tag nfc", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(VolleyError error) {
                                Toast.makeText(NfcReaderActivity.this, "Brak połączenia z serverem", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else
                        Toast.makeText(NfcReaderActivity.this, "Brak dostepnej lokalizacji", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(NfcReaderActivity.this, "Brak zeskanowanego tagu nfc", Toast.LENGTH_SHORT).show();

                NfcIntent.nfcTag = null;
                textView.setText("Przybliż tag NFC do tyłu telefonu");
            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    actualLocation = location;
                }
            }
        });

        if(nfcAdapter == null)
        {
            Toast.makeText(this,"Brak NFC",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        pendingIntent = PendingIntent.getActivity(this,0,
                new Intent(this,this.getClass())
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0);



    }
    @Override
    protected void onResume()
    {
        super.onResume();

        if(nfcAdapter != null)
        {
            if(!nfcAdapter.isEnabled())
                showWirelessSettings();

            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null,null);
        }
    }

    @Override
    protected  void onPause()
    {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected  void onNewIntent(Intent intent)
    {
        setIntent(intent);
        String message = NfcIntent.resolveIntent(intent);
        textView.setText(message);

    }



    private void showWirelessSettings()
    {
        Toast.makeText(this,"Włącz NFC",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        startActivity(intent);
    }


}
