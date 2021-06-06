package com.odishakrushi.Adapter;


import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.odishakrushi.Model.ListPestiOnSale;
import com.odishakrushi.R;

/**
 * Created by Debashish on 16-09-2017.
 */
public class AdapterPestiOnSale extends RecyclerView.Adapter<AdapterPestiOnSale.ViewHolder> {

    String message="",str_sale_id="";
    private List<ListPestiOnSale> listItems;
    private Context context;

    public AdapterPestiOnSale(List<ListPestiOnSale> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_pesti_sale,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        ListPestiOnSale listItem = listItems.get(position);
        str_sale_id=listItem.getSale_id();
        holder.sale_id.setText(listItem.getSale_id());

        String str_brand=listItem.getBrand();
        if(str_brand.equals("null")){
            str_brand="";
            holder.brand.setVisibility(View.GONE);
        }
        holder.brand.setText(str_brand);

        String product_type=listItem.getStr_product_type();
        if(product_type.equals("null"))
        {
            product_type="";
            holder.str_product_type.setVisibility(View.GONE);
        }
        holder.str_product_type.setText(product_type);
       /* Picasso.with(context)
                .load(listItem.getImage_url())
                .placeholder(R.drawable.tractor_placeholder) // vector images not supported
                .into(holder.image_url);*/


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AndroidNetworking.post(Config.baseUrl+"farmer/del_allsalesbyuserId")
                        .addBodyParameter("sale_id", str_sale_id)
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsString(new StringRequestListener() {

                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object=new JSONObject(response);
                                    message=object.getString("message");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                // Remove the item on remove/button click
                                listItems.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,listItems.size());
                            }

                            @Override
                            public void onError(ANError anError) {

                                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sale_id, brand,str_product_type;
        Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            sale_id=(TextView)itemView.findViewById(R.id.sale_id);
            brand = (TextView) itemView.findViewById(R.id.brand);
            str_product_type= (TextView) itemView.findViewById(R.id.str_product_type);
            deleteButton=(Button) itemView.findViewById(R.id.deleteButton);
        }

    }
}
