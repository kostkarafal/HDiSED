package pl.kostkamatloch.nfcreader;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pl.kostkamatloch.nfcreader.controller.TagController;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.AddTagButton);
        final Button buttonSecondActivity = findViewById(R.id.buttonNewActivity);
        final EditText editTextName = findViewById(R.id.editTextName);
        final EditText editTextContent = findViewById(R.id.editTextContent);
        final EditText editTextDescription = findViewById(R.id.editTextDescription);
        final EditText editTextLocalization = findViewById(R.id.editTextLocalization);

        TagController tag = new TagController(this);

        tag.getAllTags();
       // tag.addTag("android","android content","android description","android localization");

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tag.addTag(editTextName.getText().toString(),editTextContent.getText().toString(),editTextDescription.getText().toString(),editTextLocalization.getText().toString());
                Toast.makeText(MainActivity.this, "Dodano nowy Tag",Toast.LENGTH_LONG).show();

            }
        });

        buttonSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);

            }
        });
    }
}
