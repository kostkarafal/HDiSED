package pl.kostkamatloch.nfcreader.controller;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;


import java.util.List;

import pl.kostkamatloch.nfcreader.model.ConvertBytes;
import pl.kostkamatloch.nfcreader.model.parser.NdefMessageParser;
import pl.kostkamatloch.nfcreader.model.record.ParsedNdefRecord;
import pl.kostkamatloch.nfcreader.model.webservice.NfcTag;


/**
 * Created by Rafal on 06.06.2018.
 */
//handle reading data from nfc tag
public class NfcIntent {



    public static NfcTag nfcTag;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String resolveIntent(Intent intent)
    {
        String action = intent.getAction();
        nfcTag = new NfcTag();
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action))
        {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
            nfcTag.setIdTag(Base64.encodeToString(id, Base64.DEFAULT));
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            if(rawMsgs != null)
            {
                msgs = new NdefMessage[rawMsgs.length];

                for(int i=0; i<rawMsgs.length;i++)
                    msgs[i] = (NdefMessage) rawMsgs[i];

                setTechnologies(tag);

                return displayMsgs(msgs);
            }
            else {
                byte[] empty = new byte[0];

                byte[] payload = dumpTagData(tag).getBytes();

                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);

                NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
                msgs = new NdefMessage[]{msg};
            }

            return displayMsgs(msgs);
        }

        return  "";
    }


    private static String displayMsgs(NdefMessage[] msgs)
    {
        if(msgs == null || msgs.length == 0)
            return "";

        StringBuilder builder = new StringBuilder();
        List<ParsedNdefRecord> records = NdefMessageParser.parse(msgs[0]);
        final int size = records.size();

        for(int i = 0; i<size; i++)
        {
            NdefRecord rec;
            ParsedNdefRecord record = records.get(i);
            String str = record.str();
            builder.append(str).append("\n");
            Log.e("Test message",str);
        }

       return builder.toString();
    }

private static void setTechnologies(Tag tag)
{
    String prefix = "android.nfc.tech.";
    StringBuilder technologies = new StringBuilder();
    for (String tech : tag.getTechList()) {
        technologies.append(tech.substring(prefix.length()));
        technologies.append(", ");
    }
    technologies.delete(technologies.length() - 2, technologies.length());
    nfcTag.setTechnologies(technologies.toString());


}

    private static String dumpTagData(Tag tag) {
        StringBuilder sb = new StringBuilder();
        StringBuilder technologies = new StringBuilder();
        byte[] id = tag.getId();


        sb.append("ID (hex): ").append(ConvertBytes.toHex(id)).append('\n');
        sb.append("ID (reversed hex): ").append(ConvertBytes.toReversedHex(id)).append('\n');
        sb.append("ID (dec): ").append(ConvertBytes.toDec(id)).append('\n');
        sb.append("ID (reversed dec): ").append(ConvertBytes.toReversedDec(id)).append('\n');

        String prefix = "android.nfc.tech.";

        sb.append("Technologies: ");

        for (String tech : tag.getTechList()) {
            technologies.append(tech.substring(prefix.length()));
            technologies.append(", ");
        }
        technologies.delete(technologies.length() - 2, technologies.length());
        nfcTag.setTechnologies(technologies.toString());
        sb.append(technologies);

        for (String tech : tag.getTechList()) {
            if (tech.equals(MifareClassic.class.getName())) {
                sb.append('\n');
                String type = "Unknown";

                try {
                    MifareClassic mifareTag = MifareClassic.get(tag);

                    switch (mifareTag.getType()) {
                        case MifareClassic.TYPE_CLASSIC:
                            type = "Classic";
                            break;
                        case MifareClassic.TYPE_PLUS:
                            type = "Plus";
                            break;
                        case MifareClassic.TYPE_PRO:
                            type = "Pro";
                            break;
                    }
                    sb.append("Mifare Classic type: ");
                    sb.append(type);
                    sb.append('\n');

                    sb.append("Mifare size: ");
                    sb.append(mifareTag.getSize() + " bytes");
                    sb.append('\n');

                    sb.append("Mifare sectors: ");
                    sb.append(mifareTag.getSectorCount());
                    sb.append('\n');

                    sb.append("Mifare blocks: ");
                    sb.append(mifareTag.getBlockCount());
                } catch (Exception e) {
                    sb.append("Mifare classic error: " + e.getMessage());
                }
            }

            if (tech.equals(MifareUltralight.class.getName())) {
                sb.append('\n');
                MifareUltralight mifareUlTag = MifareUltralight.get(tag);
                String type = "Unknown";
                switch (mifareUlTag.getType()) {
                    case MifareUltralight.TYPE_ULTRALIGHT:
                        type = "Ultralight";
                        break;
                    case MifareUltralight.TYPE_ULTRALIGHT_C:
                        type = "Ultralight C";
                        break;
                }
                sb.append("Mifare Ultralight type: ");
                sb.append(type);
            }
        }
        return sb.toString();

    }


}
