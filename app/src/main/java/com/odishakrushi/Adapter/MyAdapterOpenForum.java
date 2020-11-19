package com.odishakrushi.Adapter;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.odishakrushi.Model.ListItem;
import com.odishakrushi.Model.ListOpenForum;
import com.odishakrushi.R;

/**
 * Created by Debashish on 16-09-2017.
 */
public class MyAdapterOpenForum extends RecyclerView.Adapter<MyAdapterOpenForum.ViewHolder> {


    String path="";
    private List<ListOpenForum> listItems;
    private Context context;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    String str_is_answer="";

    public MyAdapterOpenForum(List<ListOpenForum> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_openforum,parent,false);
        return new ViewHolder(v);



    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ListOpenForum listItem = listItems.get(position);

        holder.open_forum_question_id.setText(listItem.getOpen_forum_question_id());
        holder.group.setText(listItem.getGroup());
        holder.first_name.setText(listItem.getFirst_name());
        holder.farmer_name.setText(listItem.getFarmer_name());

        if(listItem.getQuestext().equals("null")||listItem.getQuestext()==null||listItem.getQuestext().equals(""))
            holder.questxt.setText(" - - - ");
        else
            holder.questxt.setText(listItem.getQuestext());


        holder.date.setText(listItem.getDate());

        if(listItem.getSubject().equals("null")||listItem.getSubject()==null||listItem.equals(""))
        holder.subject.setText(" - - - ");
        else
            holder.subject.setText(listItem.getSubject());

        str_is_answer=listItem.getIs_answer();
        if(str_is_answer.equals("Yes"))
        {
            holder.linearLayout.setBackgroundColor(Color.argb(50,144,238,144));
            holder.is_answer.setBackgroundColor(Color.parseColor("#008000"));
            holder.is_answer.setText(context.getString(R.string.answered));
        }
        else {
            holder.linearLayout.setBackgroundColor(Color.WHITE);
            holder.is_answer.setBackgroundColor(Color.parseColor("#FF0000"));
            holder.is_answer.setText(context.getString(R.string.not_answered));
        }

        if(listItem.getUrl().equals("") || listItem.getUrl()==null || listItem.getUrl().equals("null")){
            holder.thumbnailImage.setVisibility(View.GONE);
        }
        //holder.is_answer.setText(listItem.getIs_answer());
        //Picasso.with(context).load(listItem.getImgurl()).into(holder.mobileImage);



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       public TextView open_forum_question_id,q_id,group,first_name,farmer_name,questxt,date,is_answer,subject;
       public ProgressBar progressBar;
       public LinearLayout linearLayout;
       public ImageView thumbnailImage;

        public ViewHolder(View itemView) {
            super(itemView);
            open_forum_question_id = (TextView) itemView.findViewById(R.id.open_forum_question_id);
            q_id = (TextView) itemView.findViewById(R.id.q_id);
            group = (TextView) itemView.findViewById(R.id.group);
            first_name = (TextView) itemView.findViewById(R.id.first_name);
            farmer_name = (TextView) itemView.findViewById(R.id.farmer_name);
            questxt = (TextView) itemView.findViewById(R.id.questxt);
            date = (TextView) itemView.findViewById(R.id.date);
            is_answer = (TextView) itemView.findViewById(R.id.is_answer);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            thumbnailImage = (ImageView) itemView.findViewById(R.id.thumbnailImage);
            subject =  itemView.findViewById(R.id.subject);
        }

    }

}



