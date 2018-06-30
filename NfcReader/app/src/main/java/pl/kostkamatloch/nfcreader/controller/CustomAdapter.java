package pl.kostkamatloch.nfcreader.controller;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

import pl.kostkamatloch.nfcreader.R;
import pl.kostkamatloch.nfcreader.model.ConvertBytes;
import pl.kostkamatloch.nfcreader.model.webservice.NfcTag;

/**
 * Created by Rafal on 12.06.2018.
 */

// Class creating elements of ListView in history View
public class CustomAdapter extends ArrayAdapter<NfcTag> implements View.OnClickListener{

    private ArrayList<NfcTag> dataSet;
    Context mContext;
    RestController rest;
    ListView listView;


    private static class ViewHolder {
        TextView txtDate;
        TextView txtID;
        TextView txtDescription;
        TextView txtAdress;
        TextView txtTech;
        TextView txtMessage;
        Button buttonDelete;
    }

    public CustomAdapter(ArrayList<NfcTag> data, Context context, RestController rest, ListView listView) {
        super(context, R.layout.list_item, data);
        this.dataSet = data;
        this.mContext=context;
        this.listView = listView;
        this.rest = rest;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        NfcTag nfcTag=(NfcTag)object;

        switch (v.getId())
        {
            case R.id.textViewItemID:
                Snackbar.make(v, "Release date " +nfcTag.getId(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        NfcTag nfcTag = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            //assign ViewHolders to layout elements
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.textViewItemDate);
            viewHolder.txtID = (TextView) convertView.findViewById(R.id.textViewItemID);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.textViewItemDescription);
            viewHolder.txtAdress = (TextView) convertView.findViewById(R.id.textViewItemAdress);
            viewHolder.txtTech = convertView.findViewById(R.id.textViewItemTech);
            viewHolder.buttonDelete = convertView.findViewById(R.id.buttonDeleteItem);
            viewHolder.txtMessage = convertView.findViewById(R.id.textViewMessage);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        lastPosition = position;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date(Long.parseLong(nfcTag.getDate()));

//Assign text to ViewHolders from nfcTag
        viewHolder.txtDate.setText(df.format(date));

        if(nfcTag.getIdTag() != null) {
            byte[] id = Base64.decode(nfcTag.getIdTag(), Base64.DEFAULT);
            viewHolder.txtID.setText(ConvertBytes.toHex(id));
        }
        else
            viewHolder.txtID.setText("brak");

        viewHolder.txtDescription.setText(nfcTag.getDescription());
        viewHolder.txtAdress.setText(GpsLocation.getAddress(nfcTag.getLatitude(),nfcTag.getLongitude()));
        if(nfcTag.getTechnologies() != null)
             viewHolder.txtTech.setText(nfcTag.getTechnologies());
        else
            viewHolder.txtTech.setText("brak");
        if(nfcTag.getText() != null)
            viewHolder.txtMessage.setText(nfcTag.getText());
        else
            viewHolder.txtMessage.setText("Brak");

        //delete delete element from list and database
        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestController.deleteTag(nfcTag.getId().toString(),mContext);

                Toast.makeText(mContext,"Usunieto tag",Toast.LENGTH_SHORT).show();

                rest.getAllTags(new VolleyGetCallback() {
                    @Override
                    public void onSucces(ArrayList<NfcTag> tags) {

                        clear();
                        addAll(tags);

                    }
                    @Override
                    public  void onFailure(VolleyError error){
                        Toast.makeText(mContext,"Brak połączenia z serverem",Toast.LENGTH_LONG).show();
                    }

                });


               notifyDataSetChanged();
            }
        });

        return convertView;
    }
}