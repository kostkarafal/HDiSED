package pl.kostkamatloch.nfcreader;


import android.content.Context;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pl.kostkamatloch.nfcreader.controller.GPSTracker;


public class MainActivity extends AppCompatActivity {

    GPSTracker gps;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        final Button buttonHistory = findViewById(R.id.buttonHistory);
        final Button buttonNfcReader = findViewById(R.id.buttonNfcReader);
        final Button buttonMaps = findViewById(R.id.buttonMap);
        final Button buttonSettings = findViewById(R.id.buttonSettings);
        gps = new GPSTracker(MainActivity.this);


        buttonNfcReader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NfcReaderActivity.class);
                startActivity(intent);

            }
        });

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume()
    {
        super.onResume();


    }



}
