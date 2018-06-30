package pl.kostkamatloch.nfcreader.model.parser;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import pl.kostkamatloch.nfcreader.controller.NfcIntent;
import pl.kostkamatloch.nfcreader.model.record.ParsedNdefRecord;
import pl.kostkamatloch.nfcreader.model.record.SmartPoster;
import pl.kostkamatloch.nfcreader.model.record.TextRecord;
import pl.kostkamatloch.nfcreader.model.record.UriRecord;

import java.util.ArrayList;
import java.util.List;


public class NdefMessageParser {

    private NdefMessageParser() {
    }

    public static List<ParsedNdefRecord> parse(NdefMessage message) {
        return getRecords(message.getRecords());
    }
    //check what type of NFC message is in tag
    public static List<ParsedNdefRecord> getRecords(NdefRecord[] records) {
        List<ParsedNdefRecord> elements = new ArrayList<>();

        for (final NdefRecord record : records) {
            NfcIntent.nfcTag.setTnf(record.getTnf());
            if (UriRecord.isUri(record)) {
                elements.add(UriRecord.parse(record));

            } else if (TextRecord.isText(record)) {
                elements.add(TextRecord.parse(record));

            } else if (SmartPoster.isPoster(record)) {
                elements.add(SmartPoster.parse(record));
            }
            else {
                elements.add(new ParsedNdefRecord() {
                    public String str() {
                        return new String(record.getPayload());
                    }
                });
            }

        }
        return elements;
    }
}