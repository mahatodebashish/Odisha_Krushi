package com.odishakrushi.Adapter;

import android.content.Context;
import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import com.odishakrushi.Model.ListOpenFanswerers;
import com.odishakrushi.R;

/**
 * Created by Debashish on 16-09-2017.
 */
public class MyAdapterOpenForumAnswersList extends RecyclerView.Adapter<MyAdapterOpenForumAnswersList.ViewHolder> {


    private List<ListOpenFanswerers> listItems;
    private Context context;



    public MyAdapterOpenForumAnswersList(List<ListOpenFanswerers> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_openforum_answerers,parent,false);
        return new ViewHolder(v);



    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ListOpenFanswerers listItem = listItems.get(position);

        holder.answer_text.setText(listItem.getAnswer_text());
        holder.group.setText(listItem.getGroup());
        holder.first_name.setText(listItem.getFirst_name());
        holder.ans_date.setText(listItem.getAns_date());
        // Picasso.with(context).load(listItem.getImgurl()).into(holder.mobileImage);

        if(position%2 == 0){
            holder.itemView.setBackgroundColor(Color.parseColor("#b3ffb3"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#e6ffff"));

        }

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView subject,answer_text,group,first_name,ans_date;;
        public ProgressBar progressBar;


        public ViewHolder(View itemView) {
            super(itemView);

            answer_text = (TextView) itemView.findViewById(R.id.answer_text);
            subject = (TextView) itemView.findViewById(R.id.subject);
            group = (TextView) itemView.findViewById(R.id.group);
            first_name = (TextView) itemView.findViewById(R.id.first_name);
            ans_date = (TextView) itemView.findViewById(R.id.ans_date);
        }

    }

}

