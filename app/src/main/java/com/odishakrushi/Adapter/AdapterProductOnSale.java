package com.odishakrushi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.odishakrushi.AskQSale.PopUpAgrilProductSale;
import com.odishakrushi.Config;
import com.odishakrushi.Model.ProductOnSale;
import com.odishakrushi.R;

/**
 * Created by RatnaDev008 on 8/9/2018.
 */


public class AdapterProductOnSale extends RecyclerView.Adapter<AdapterProductOnSale.ViewHolder> {

    String message="";
    private String str_product_type,str_sale_id;
    private List<ProductOnSale> listItems;
    private Context context;

    public AdapterProductOnSale(List<ProductOnSale> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_sale_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
      // Toast.makeText(context,String.valueOf(position), Toast.LENGTH_SHORT).show();

        ProductOnSale listItem = listItems.get(position);

        String str_variety=listItem.getVariety();
        if(str_variety.equals("null")){
            str_variety="";
            holder.variety.setVisibility(View.GONE);
        }
        holder.variety.setText(str_variety);

        String str_price=listItem.getPrice();
        if(str_price.equals("null")){
            str_price="";
            holder.price.setVisibility(View.GONE);
        }
        holder.price.setText((context.getString(R.string.Rs))+" "+(str_price)+" "+(context.getString(R.string.perkg)));

        String str_quantity=listItem.getQuantity();
        if(str_quantity.equals("null")){
            str_quantity="";
            holder.quantity.setVisibility(View.GONE);
        }
        holder.quantity.setText(str_quantity);


        str_product_type=listItem.getProduct_type();
        if(str_product_type.equals("null")){
            str_product_type="";
            holder.prod_type.setVisibility(View.GONE);
        }
        holder.prod_type.setText(str_product_type);

        str_sale_id=listItem.getSale_id();
        holder.sale_id.setText(listItem.getSale_id());
        //   Picasso.with(context).load(listItem.getImgurl()).into(holder.mobileImage);

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

        private TextView sale_id,variety,price,quantity,prod_type;
        private Button editButton,deleteButton;

        public ViewHolder(final View itemView) {
            super(itemView);

            sale_id = (TextView) itemView.findViewById(R.id.sale_id);
            variety = (TextView) itemView.findViewById(R.id.variety);
            price = (TextView) itemView.findViewById(R.id.price);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            prod_type= (TextView) itemView.findViewById(R.id.prod_type);
            editButton= (Button) itemView.findViewById(R.id.editButton);
            deleteButton= (Button) itemView.findViewById(R.id.deleteButton);

            /*editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, str_sale_id, Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(context, PopUpAgrilProductSale.class);
                    intent.putExtra("PROD_NAME",str_product_type);
                    //intent.putExtra("SALE_ID",str_sale_id);
                    v.getContext().startActivity(intent);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  Toast.makeText(context, "delete product details", Toast.LENGTH_SHORT).show();

                }
            });*/
        }


    }

}
