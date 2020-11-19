package com.odishakrushi.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.Config;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.odishakrushi.Model.ListItem;
import com.odishakrushi.Model.ListSearchresults;
import com.odishakrushi.Model.ListTags;
import com.odishakrushi.Model.ListViewMessages;
import com.odishakrushi.R;

/**
 * Created by Debashish on 16-09-2017.
 */
public class MyAdapterViewMessages extends RecyclerView.Adapter<MyAdapterViewMessages.ViewHolder> {


    String path="";
    private List<ListViewMessages> listItems;
    private Context context;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public MyAdapterViewMessages(List<ListViewMessages> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_messages,parent,false);
        return new ViewHolder(v);



    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

       final ListViewMessages listItem = listItems.get(position);

       // holder.first_name.setText(listItem.getFirst_name());
        holder.description.setText(listItem.getDescription());
        holder.message_text.setText(listItem.getMessage_text());
        holder.date_time.setText(listItem.getDate_time());

        holder.card_view.setCardBackgroundColor(Color.parseColor("#e6ffff"));

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Sure to delete message ?");
                alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                      //  AndroidNetworking.post("http://demo.ratnatechnology.co.in/farmerapp/api/message/del_bymsgId")// Doubt
                        AndroidNetworking.post(Config.baseUrl+"message/del_bymsgId")
                        .addBodyParameter("message_id",listItem.getMessage_id())
                                .setTag("test")
                                .setPriority(Priority.HIGH)
                                .build()
                                .getAsString(new StringRequestListener() {

                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject=new JSONObject(response);
                                            String message=jsonObject.getString("message");
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                            listItems.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position,listItems.size());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(ANError error) {
                                        // handle error
                                        Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView description,message_text,date_time;
        public Button deleteButton;
        public CardView card_view;
        public ViewHolder(View itemView) {
            super(itemView);

           // first_name = (TextView) itemView.findViewById(R.id.first_name);
            description = (TextView) itemView.findViewById(R.id.description);
            message_text = (TextView) itemView.findViewById(R.id.message_text);
            date_time = (TextView) itemView.findViewById(R.id.date_time);
            deleteButton=itemView.findViewById(R.id.deleteButton);
            card_view=itemView.findViewById(R.id.card_view);
        }

    }

}


