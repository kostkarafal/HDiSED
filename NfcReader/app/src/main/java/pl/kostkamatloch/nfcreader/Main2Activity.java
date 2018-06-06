package pl.kostkamatloch.nfcreader;

import android.app.PendingIntent;
import android.content.Intent;

import android.nfc.NfcAdapter;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import pl.kostkamatloch.nfcreader.model.parser.NfcIntent;


public class Main2Activity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        text = (TextView) findViewById(R.id.textNfc);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

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

        text.setText(message);

    }



    private void showWirelessSettings()
    {
        Toast.makeText(this,"Włącz NFC",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        startActivity(intent);
    }


}
