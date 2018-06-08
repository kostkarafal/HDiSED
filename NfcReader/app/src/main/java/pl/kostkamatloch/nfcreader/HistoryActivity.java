package pl.kostkamatloch.nfcreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import pl.kostkamatloch.nfcreader.controller.RestController;

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
        rest.getAllTags(listView);

    }
}
