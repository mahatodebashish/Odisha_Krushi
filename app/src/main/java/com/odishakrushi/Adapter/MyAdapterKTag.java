package com.odishakrushi.Adapter;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.odishakrushi.Interfaces.KBankInterface;

import java.util.List;

import com.odishakrushi.Model.ListTags;
import com.odishakrushi.R;

/**
 * Created by Debashish on 16-09-2017.
 */
public class MyAdapterKTag extends RecyclerView.Adapter<MyAdapterKTag.ViewHolder> implements KBankInterface {

    String str_kdropdown_id="0";
   // KnowledgeBankDropdownIndex dataPasser;
    String path="";
    private List<ListTags> listItems;
    private Context context;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public MyAdapterKTag() {

    }

    public MyAdapterKTag(List<ListTags> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_tags,parent,false);
        return new ViewHolder(v);



    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ListTags listItem = listItems.get(position);
        final String strTag=listItem.getTags();

        if(!(strTag.equals("null")))
        holder.txt_tags.setText(strTag);
/*
        holder.txt_tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, SearchResults.class);
                intent.putExtra("str_kdropdown_id",str_kdropdown_id);
                intent.putExtra("tag",strTag);
                context.startActivity(intent);
            }
        });*/



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Receive the broadcast random number
        String index = intent.getStringExtra("DROPDOWN_ID");

        str_kdropdown_id=index;
    }

  /*  @Override
    public String onIndexPass(String dropDownIndex) {
      //  str_kdropdown_id=dataPasser.onIndexPass(dropDownIndex);
        return str_kdropdown_id;
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_tags;


        public ViewHolder(View itemView) {
            super(itemView);
            txt_tags = (TextView) itemView.findViewById(R.id.txt_tags);



        }

    }

}

