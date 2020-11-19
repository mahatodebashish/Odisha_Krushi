package com.odishakrushi.Adapter;

/**
 * Created by RatnaDev008 on 8/23/2018.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.odishakrushi.AskQPurchase.MedicineFeedPurchase;
import com.odishakrushi.Model.ListFishForSale;
import com.odishakrushi.Model.ListItem;
import com.odishakrushi.Model.ListItemExt;
import com.odishakrushi.Model.MedicineFeed;
import com.odishakrushi.Model.MedicineFeedPurchaseModel;
import com.odishakrushi.R;

/**
 * Created by Debashish on 16-09-2017.
 */
public class AdapterMedicineFeedPurchase extends RecyclerView.Adapter<AdapterMedicineFeedPurchase.ViewHolder> {

    private List<MedicineFeedPurchaseModel> listItems;
    private Context context;

    public AdapterMedicineFeedPurchase(List<MedicineFeedPurchaseModel> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_medicinefeed_purchase,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MedicineFeedPurchaseModel listItem = listItems.get(position);

        holder.seller_name.setText("Seller name :   "+listItem.getSeller_name());

        String str_medicine=listItem.getMedicine();
        if(str_medicine.equals("null")){
            str_medicine="";
            holder.medicine.setVisibility(View.GONE);
        }
        holder.medicine.setText("Medicine : "+str_medicine);


        String str_medicine_for=listItem.getMedicine_for();
        if(str_medicine_for.equals("null")){
            str_medicine_for="";
            holder.medicine_for.setVisibility(View.GONE);
        }
        holder.medicine_for.setText("Medicine for : "+str_medicine_for);

        String str_feed=listItem.getFeed();
        if(str_feed.equals("null")){
            str_feed="";
            holder.feed.setVisibility(View.GONE);
        }
        holder.feed.setText("Feed : "+str_feed);


        String str_net=listItem.getNet();
        if(str_net.equals("null")){
            str_net=listItem.getNet();
            holder.net.setVisibility(View.GONE);
        }
        holder.net.setText("Net :   "+str_net);


        holder.mobile.setText("Mobile   :   "+listItem.getMobile());


        String str_district=listItem.getDistrict_name();
        if(str_district.equals("null")){
            str_district="";
            holder.district_name.setVisibility(View.GONE);
        }
        holder.district_name.setText("District  :   "+str_district);


        String str_block=listItem.getBlock_name();
        if(str_block.equals("null")){
            str_block="";
            holder.block_name.setVisibility(View.GONE);
        }
        holder.block_name.setText("Block    :   "+str_block);


        String str_village=listItem.getVillage_name();
        if(str_village.equals("null")){
            str_village="";
            holder.village_name.setVisibility(View.GONE);
        }
        holder.village_name.setText("Village    :   "+str_village);
        //   Picasso.with(context).load(listItem.getImgurl()).into(holder.mobileImage);



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView seller_name, medicine, medicine_for, feed, net, mobile, district_name, block_name, village_name;
        public ViewHolder(View itemView) {
            super(itemView);

            seller_name = (TextView) itemView.findViewById(R.id.seller_name);
            medicine = (TextView) itemView.findViewById(R.id.medicine);
            medicine_for = (TextView) itemView.findViewById(R.id.medicine_for);
            feed = (TextView) itemView.findViewById(R.id.feed);
            net = (TextView) itemView.findViewById(R.id.net);
            mobile = (TextView) itemView.findViewById(R.id.mobile);
            district_name = (TextView) itemView.findViewById(R.id.district_name);
            block_name = (TextView) itemView.findViewById(R.id.block_name);
            village_name = (TextView) itemView.findViewById(R.id.village_name);
        }

    }
}
