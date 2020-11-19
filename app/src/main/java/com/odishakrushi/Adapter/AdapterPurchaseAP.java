package com.odishakrushi.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.odishakrushi.AskQPurchase.ProductListPurchase;
import com.odishakrushi.Model.ProductOnSale;
import com.odishakrushi.Model.PurchaseAP;
import com.odishakrushi.R;

public class AdapterPurchaseAP extends RecyclerView.Adapter<AdapterPurchaseAP.ViewHolder> {

    private String str_product_type,str_sale_id;
    private List<PurchaseAP> listItems;
    private Context context;

    public AdapterPurchaseAP(List<PurchaseAP> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_purchase_ap_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Toast.makeText(context,String.valueOf(position), Toast.LENGTH_SHORT).show();

        PurchaseAP listItem = listItems.get(position);

        String str_firstname=listItem.getFirst_name();
        String str_phone=listItem.getPhone();
        String str_district=listItem.getDistrict_name();
        if(str_district.equals("null")){
            str_district="";
        }

        String str_block= listItem.getBlock_name();
        if(str_block.equals("null")){
            str_block="";
        }

        String str_village=listItem.getVillage();
        if(str_village.equals("null")){
            str_village="";
        }

        String str_variety=listItem.getVariety();
        if(str_variety.equals("null")){
            str_variety="";
        }
        //------------------------------------------------------

        String year_of_purchase=listItem.getYear_of_purchase();
        if(!year_of_purchase.equals("")||!year_of_purchase.equals("null"))
        {
           // holder.machine_layout.setVisibility(View.VISIBLE);
            year_of_purchase="";
            holder.year_of_purchase.setVisibility(View.GONE);

        }

        String make=listItem.getMake();
        if(make.equals("null")){
            make="";
            holder.make.setVisibility(View.GONE);
        }

        String model=listItem.getModel();
        if(model.equals("null")){
            model="";
            holder.model.setVisibility(View.GONE);
        }


        String condition=listItem.getCondition();
        if(condition.equals("null")){
            condition="";
            holder.condition.setVisibility(View.GONE);
        }


        holder.seller_name.setText(context.getResources().getString(R.string.Seller)+":"+str_firstname);
        holder.mobile.setText(context.getResources().getString(R.string.Mobile)+":"+str_phone);
        holder.district_name.setText(context.getResources().getString(R.string.District)+":"+str_district);
        holder.block_name.setText(context.getResources().getString(R.string.Block)+":"+str_block);
        holder.village_name.setText(context.getResources().getString(R.string.Village)+":"+str_village);
        holder.variety.setText(context.getResources().getString(R.string.Variety)+":"+str_variety);
        holder.year_of_purchase.setText(context.getResources().getString(R.string.Year_Of_Purchase)+":"+year_of_purchase);
        holder.make.setText(context.getResources().getString(R.string.Make)+":"+make);
        holder.model.setText(context.getResources().getString(R.string.Model)+":"+model);
        holder.condition.setText(context.getResources().getString(R.string.Condition)+":"+condition);

        String imageurl=listItem.getImage();

        if(!imageurl.equals("")||!imageurl.equals("null"))
        Picasso.with(context).load(imageurl).into(holder.image);
        else holder.image.setVisibility(View.GONE);

        if(str_firstname.equalsIgnoreCase("null"))
        {
            holder.seller_name.setVisibility(View.GONE);
        }
        if(str_phone.equalsIgnoreCase("null"))
        {

            holder.mobile.setVisibility(View.GONE);
        }
        if(str_district.equalsIgnoreCase("null"))
        {

            holder.district_name.setVisibility(View.GONE);
        }
        if(str_block.equalsIgnoreCase("null"))
        {

            holder.block_name.setVisibility(View.GONE);
        }
        if(str_village.equalsIgnoreCase("null"))
        {
            holder.village_name.setVisibility(View.GONE);
        }
        if(str_variety.equalsIgnoreCase("null"))
        {
            holder.variety.setVisibility(View.GONE);
        }


//        Picasso.with(context).load(listItem.getPhoto_url()).into(holder.image_url);


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView seller_name,mobile,district_name,block_name,village_name,variety;
        private ImageView image_url;
        private TextView year_of_purchase, make, model, condition;
        private ImageView image;

        private LinearLayout machine_layout;

        public ViewHolder(final View itemView) {
            super(itemView);

            image_url = (ImageView) itemView.findViewById(R.id.image_url);
            seller_name=(TextView)itemView.findViewById(R.id.seller_name);
            mobile=(TextView)itemView.findViewById(R.id.mobile);
            district_name=(TextView)itemView.findViewById(R.id.district_name);
            block_name=(TextView)itemView.findViewById(R.id.block_name);
            village_name=(TextView)itemView.findViewById(R.id.village_name);
            variety=(TextView)itemView.findViewById(R.id.variety);

            year_of_purchase=(TextView)itemView.findViewById(R.id.yr_of_purchase);
            make=(TextView)itemView.findViewById(R.id.make);
            model=(TextView)itemView.findViewById(R.id.model);
            condition=(TextView)itemView.findViewById(R.id.condition);
            image=(ImageView) itemView.findViewById(R.id.image);
            machine_layout=(LinearLayout) itemView.findViewById(R.id.machine_layout);
        }


    }

}
