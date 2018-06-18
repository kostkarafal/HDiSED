package pl.kostkamatloch.nfcreader;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

import pl.kostkamatloch.nfcreader.controller.GpsLocation;
import pl.kostkamatloch.nfcreader.controller.RestController;
import pl.kostkamatloch.nfcreader.controller.VolleyGetCallback;
import pl.kostkamatloch.nfcreader.model.ConvertBytes;
import pl.kostkamatloch.nfcreader.model.webservice.NfcTag;

/**
 * Created by Rafal on 12.06.2018.
 */

public class CustomAdapter extends ArrayAdapter<NfcTag> implements View.OnClickListener{

    private ArrayList<NfcTag> dataSet;
    Context mContext;
    RestController rest;
    ListView listView;

    // View lookup cache
    private static class ViewHolder {
        TextView txtDate;
        TextView txtID;
        TextView txtDescription;
        TextView txtAdress;
        TextView txtTech;
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

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.textViewItemDate);
            viewHolder.txtID = (TextView) convertView.findViewById(R.id.textViewItemID);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.textViewItemDescription);
            viewHolder.txtAdress = (TextView) convertView.findViewById(R.id.textViewItemAdress);
            viewHolder.txtTech = convertView.findViewById(R.id.textViewItemTech);
            viewHolder.buttonDelete = convertView.findViewById(R.id.buttonDeleteItem);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
    //    result.startAnimation(animation);
        lastPosition = position;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date(Long.parseLong(nfcTag.getDate()));


        viewHolder.txtDate.setText(df.format(date));
        byte[] id = Base64.decode(nfcTag.getIdTag(),Base64.DEFAULT);

        viewHolder.txtID.setText(ConvertBytes.toHex(id));
        viewHolder.txtDescription.setText(nfcTag.getDescription());
        viewHolder.txtAdress.setText(GpsLocation.getAddress(nfcTag.getLatitude(),nfcTag.getLongitude()));
        viewHolder.txtTech.setText(nfcTag.getTechnologies());

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