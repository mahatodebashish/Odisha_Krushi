package com.odishakrushi.Adapter;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.odishakrushi.Model.ListItem;
import com.odishakrushi.Model.ListSearchresults;
import com.odishakrushi.Model.ListTags;
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

        String model=listItem.getModel();
        if(!model.equalsIgnoreCase("null")||!model.equalsIgnoreCase(""))
        holder.model.setText(model);

        String make=listItem.getMake();
        if(!make.equalsIgnoreCase("null")||!make.equalsIgnoreCase(""))
        holder.make.setText(make);

        String product_type=listItem.getProduct_type();
        if(!product_type.equalsIgnoreCase("null")||!product_type.equalsIgnoreCase(""))
        holder.product_type.setText(product_type);

        String sub_category_name=listItem.getSub_category_name();
        if(!sub_category_name.equalsIgnoreCase("null")||!sub_category_name.equalsIgnoreCase(""))
        holder.sub_category_name.setText(sub_category_name);

        String category_name=listItem.getCategory_name();
        if(!category_name.equalsIgnoreCase("null")||!category_name.equalsIgnoreCase(""))
        holder.category_name.setText(category_name);

        String village=listItem.getVillage();
        if(!village.equalsIgnoreCase("null")||!village.equalsIgnoreCase(""))
        holder.village.setText(village);

        String farmer_name=listItem.getFarmer_name();
        if(!farmer_name.equalsIgnoreCase("null")||!farmer_name.equalsIgnoreCase(""))
        holder.farmer_name.setText(farmer_name);

        String phone=listItem.getPhone();
        if(!phone.equalsIgnoreCase("null")||!phone.equalsIgnoreCase(""))
        holder.phone.setText(phone);

        String block=listItem.getBlock();
        if(!block.equalsIgnoreCase("null")||!block.equalsIgnoreCase(""))
        holder.block.setText(block);

        String district=listItem.getDistrict();
        if(!district.equalsIgnoreCase("null")||!district.equalsIgnoreCase(""))
        holder.district.setText(district);

        String questext= listItem.getQuestext();
        if(!questext.equalsIgnoreCase("null")||!questext.equalsIgnoreCase(""))
        holder.questext.setText(questext);

        String dropdown=listItem.getDropdown();
        if(!dropdown.equalsIgnoreCase("null")||!dropdown.equalsIgnoreCase(""))
        holder.dropdown.setText(dropdown);

       final String image_url=listItem.getImage_url();
        if(image_url.contains(".jpg")||image_url.contains(".png")){
            if(!image_url.equalsIgnoreCase("null")||!image_url.equalsIgnoreCase(""))
            {
                Picasso.with(context).load(image_url).into(holder.imageView);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
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
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageResource(R.drawable.ic_video_player);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
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

       public TextView model,make,product_type,sub_category_name,category_name,village,farmer_name,phone,block,district,questext,dropdown;
       public ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            model = (TextView) itemView.findViewById(R.id.model);
            make = (TextView) itemView.findViewById(R.id.make);
            product_type = (TextView) itemView.findViewById(R.id.product_type);
            sub_category_name = (TextView) itemView.findViewById(R.id.sub_category_name);
            category_name = (TextView) itemView.findViewById(R.id.category_name);
            village = (TextView) itemView.findViewById(R.id.village);
            farmer_name = (TextView) itemView.findViewById(R.id.farmer_name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            block = (TextView) itemView.findViewById(R.id.block);
            district = (TextView) itemView.findViewById(R.id.district);
            questext = (TextView) itemView.findViewById(R.id.questext);
            dropdown = (TextView) itemView.findViewById(R.id.dropdown);
            imageView = (ImageView) itemView.findViewById(R.id.image_url);

        }

    }

}


