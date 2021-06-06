package com.odishakrushi.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.odishakrushi.Model.ListItemExt;
import com.odishakrushi.R;

/**
 * Created by Debashish on 16-09-2017.
 */
public class MyAdapterExt extends RecyclerView.Adapter<MyAdapterExt.ViewHolder> {

    private List<ListItemExt> listItems;
    private Context context;

    public MyAdapterExt(List<ListItemExt> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_ans_by_extoff,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItemExt listItem = listItems.get(position);
        holder.questext.setText(listItem.getQuestext());
        holder.quesaskeddate.setText("Date: "+listItem.getQuestion_date());
        holder.answereddate.setText("Answered Date: "+listItem.getAns_dt());
        holder.dropdown.setText("Tag: "+listItem.getDropdown());
        holder.anstext.setText(listItem.getAnstext());
        //   Picasso.with(context).load(listItem.getImgurl()).into(holder.mobileImage);



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView questext,quesaskeddate,answereddate,anstext,dropdown;

        public ViewHolder(View itemView) {
            super(itemView);
            questext = (TextView) itemView.findViewById(R.id.questext);
            quesaskeddate = (TextView) itemView.findViewById(R.id.quesaskeddate);
            answereddate = (TextView) itemView.findViewById(R.id.answereddate);
            anstext = (TextView) itemView.findViewById(R.id.anstext);
            dropdown = (TextView) itemView.findViewById(R.id.dropdown);
        }

    }
}
