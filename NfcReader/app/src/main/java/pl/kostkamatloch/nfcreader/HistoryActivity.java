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

import pl.kostkamatloch.nfcreader.controller.GPSTracker;
import pl.kostkamatloch.nfcreader.controller.RestController;
import pl.kostkamatloch.nfcreader.controller.VolleyGetCallback;
import pl.kostkamatloch.nfcreader.model.webservice.NfcTag;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;
    RestController rest;
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
            public void onSucces(List<NfcTag> tags) {
                ArrayList<String> tagList = new ArrayList<>();
                for(NfcTag tag : tags)
                {
                    String adress = GPSTracker.getAddress(tag.getLatitude(),tag.getLongitude());

                    Date date = new Date(Long.parseLong(tag.getDate()));

                    tagList.add(date.toString()+"\nID: "+tag.getId().toString()+" TagID: "+tag.getIdTag()
                            +"Technologies: "+tag.getTechnologies()+"\nDescription: "+tag.getDescription()
                            +"\nAdress: "+adress);

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(HistoryActivity.this, R.layout.list_item,tagList);
                listView.setAdapter(adapter);
            }
            @Override
            public  void onFailure(VolleyError error){
                Toast.makeText(HistoryActivity.this,"Brak połączenia z serverem",Toast.LENGTH_LONG).show();
            }
        });

    }
}
