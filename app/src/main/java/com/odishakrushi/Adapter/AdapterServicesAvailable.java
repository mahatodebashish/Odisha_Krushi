package com.odishakrushi.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.odishakrushi.Model.ListItem;
import com.odishakrushi.Model.ListServiceAvailable;
import com.odishakrushi.R;

/**
 * Created by Debashish on 16-09-2017.
 */
public class AdapterServicesAvailable extends RecyclerView.Adapter<AdapterServicesAvailable.ViewHolder> {


    private List<ListServiceAvailable> listItems;
    private Context context;

    public AdapterServicesAvailable(List<ListServiceAvailable> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_services_available,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListServiceAvailable listItem = listItems.get(position);
        holder.txtname.setText(listItem.getBusinessman_name());
        holder.txtphone.setText(listItem.getContact_no());
        holder.txtemail.setText(listItem.getEmail());
        holder.txtwebsite.setText(listItem.getWebsite());
        holder.txtfarmname.setText(listItem.getName_of_farm());
        holder.txtareaofbusiness.setText(listItem.getArea_of_business());
        holder.txt_deals_in_product.setText(listItem.getDeals_in_product());
        //   Picasso.with(context).load(listItem.getImgurl()).into(holder.mobileImage);



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txtname,txtphone,txtemail,txtwebsite,txtfarmname,
                txtareaofbusiness,txt_deals_in_product;

        public ViewHolder(View itemView) {
            super(itemView);
            txtname = (TextView) itemView.findViewById(R.id.txtname);
            txtphone = (TextView) itemView.findViewById(R.id.txtphone);
            txtemail = (TextView) itemView.findViewById(R.id.txtemail);
            txtwebsite = (TextView) itemView.findViewById(R.id.txtwebsite);
            txtfarmname = (TextView) itemView.findViewById(R.id.txtfarmname);
            txtareaofbusiness = (TextView) itemView.findViewById(R.id.txtareaofbusiness);
            txt_deals_in_product = (TextView) itemView.findViewById(R.id.txt_deals_in_product);

        }

    }
}
