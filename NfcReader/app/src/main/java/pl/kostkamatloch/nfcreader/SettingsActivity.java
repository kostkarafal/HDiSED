package pl.kostkamatloch.nfcreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pl.kostkamatloch.nfcreader.controller.RestController;

public class SettingsActivity extends AppCompatActivity {

    Button buttonChange;
    Button buttonDefaultURL;
    EditText editTextURL;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        buttonChange = findViewById(R.id.buttonChangeURL);
        buttonDefaultURL = findViewById(R.id.buttonDefaultURL);
        editTextURL = findViewById(R.id.editTextURL);
        textView = findViewById(R.id.textViewURL);

        textView.setText(RestController.RestUrl);

        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestController.RestUrl = editTextURL.getText().toString();
                textView.setText(RestController.RestUrl);

            }
        });

        buttonDefaultURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestController.RestUrl = "http://192.168.8.127:8080/rest/tag";
                textView.setText(RestController.RestUrl);
            }
        });


    }
}
