package com.odishakrushi.Adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.odishakrushi.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.odishakrushi.Model.ListSearchresults;
import com.odishakrushi.R;

/**
 * Created by Debashish on 16-09-2017.
 */
public class MyAdapterSearch extends RecyclerView.Adapter<MyAdapterSearch.ViewHolder> {


    String path="";
    private List<ListSearchresults> listItems;
    private Context context;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public MyAdapterSearch(List<ListSearchresults> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_search,parent,false);
        return new ViewHolder(v);



    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ListSearchresults listItem = listItems.get(position);


        String village=listItem.getVillage();
        village= Utils.nullCheck(village);
        holder.village.setText(village);

        String farmer_name=listItem.getFarmer_name();
        farmer_name= Utils.nullCheck(farmer_name);
        holder.farmer_name.setText(farmer_name);

        String phone=listItem.getPhone();
        phone= Utils.nullCheck(phone);
        holder.phone.setText(phone);

        String block=listItem.getBlock();
        block= Utils.nullCheck(block);
        holder.block.setText(block);

        String district=listItem.getDistrict();
        district= Utils.nullCheck(district);
        holder.district.setText(district);

        String questext= listItem.getQuestext();
        questext= Utils.nullCheck(questext);
        holder.questext.setText(questext);

        String anstext= listItem.getAnstext();
        anstext= Utils.nullCheck(anstext);
            holder.anstext.setText(anstext);


       final String image_url=listItem.getImage_url();
        if(image_url.contains(".jpg")||image_url.contains(".png")){
            if(!image_url.equalsIgnoreCase("null")||!image_url.equalsIgnoreCase(""))
            {
                Picasso.with(context).load(image_url).into(holder.url);
                holder.url.setVisibility(View.VISIBLE);
                holder.url.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(image_url));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
            }

        }
        else if(image_url.contains(".mp4")){
            holder.url.setVisibility(View.VISIBLE);
            holder.url.setImageResource(R.drawable.ic_video_player);
            holder.url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(image_url));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }





    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       public TextView farmer_name , village  , block, district , phone , questext, anstext;
        public ImageView  url;

        public ViewHolder(View itemView) {
            super(itemView);
            //model = (TextView) itemView.findViewById(R.id.model);
          //  make = (TextView) itemView.findViewById(R.id.make);
            //product_type = (TextView) itemView.findViewById(R.id.product_type);
          //  sub_category_name = (TextView) itemView.findViewById(R.id.sub_category_name);
           // category_name = (TextView) itemView.findViewById(R.id.category_name);
            village = (TextView) itemView.findViewById(R.id.village);
            farmer_name = (TextView) itemView.findViewById(R.id.farmer_name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            block = (TextView) itemView.findViewById(R.id.block);
            district = (TextView) itemView.findViewById(R.id.district);
            questext = (TextView) itemView.findViewById(R.id.questext);
            anstext = (TextView) itemView.findViewById(R.id.anstext);
           // dropdown = (TextView) itemView.findViewById(R.id.dropdown);
            url = (ImageView) itemView.findViewById(R.id.image_url);

        }

    }

}


