package com.odishakrushi.Adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import com.odishakrushi.Model.ListItem;
import com.odishakrushi.R;

/**
 * Created by Debashish on 16-09-2017.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    String path="";
    private List<ListItem> listItems;
    private Context context;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_asked_ques,parent,false);
        return new ViewHolder(v);



    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ListItem listItem = listItems.get(position);
        holder.questxt.setText(listItem.getQuestext());
        holder.ques_aked_date_time.setText("Date: " + listItem.getQuestion_date());
        String answer_status = listItem.getIs_answered();
        if (answer_status.equals("No"))
            holder.status.setText("Not Answered");
        else if (answer_status.equals("Yes"))
            holder.status.setText("Answered");

        path = listItem.getUrl();
        //   Picasso.with(context).load(listItem.getImgurl()).into(holder.mobileImage);



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView questxt;
        public TextView ques_aked_date_time;
        public TextView status;
        public ProgressBar progressBar;


        public ViewHolder(View itemView) {
            super(itemView);
            questxt = (TextView) itemView.findViewById(R.id.questxt);
            ques_aked_date_time = (TextView) itemView.findViewById(R.id.ques_aked_date_time);
            status = (TextView) itemView.findViewById(R.id.status);



        }

    }

}
