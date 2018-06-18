package pl.kostkamatloch.nfcreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.kostkamatloch.nfcreader.controller.GpsLocation;
import pl.kostkamatloch.nfcreader.controller.RestController;
import pl.kostkamatloch.nfcreader.controller.VolleyGetCallback;
import pl.kostkamatloch.nfcreader.model.webservice.NfcTag;

public class HistoryActivity extends AppCompatActivity {

    public static ListView listView;
    public RestController rest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView = findViewById(R.id.listView);
        rest = new RestController(this);
    }



    @Override
    protected void onResume()
    {

        super.onResume();
        rest.getAllTags(new VolleyGetCallback() {
            @Override
            public void onSucces(ArrayList<NfcTag> tags) {
                CustomAdapter adapter = new CustomAdapter(tags,HistoryActivity.this,rest,listView);
                listView.setAdapter(adapter);

            }
            @Override
            public  void onFailure(VolleyError error){
                Toast.makeText(HistoryActivity.this,"Brak połączenia z serverem",Toast.LENGTH_LONG).show();
            }

        });

    }
}
