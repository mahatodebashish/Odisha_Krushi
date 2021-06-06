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
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

import com.odishakrushi.Config;
import com.odishakrushi.Model.ListServices;
import com.odishakrushi.R;

/**
 * Created by RatnaDev008 on 8/9/2018.
 */


public class AdapterServices extends RecyclerView.Adapter<AdapterServices.ViewHolder> {

    String message="";
    private String str_sale_id="";
    private List<ListServices> listItems;
    private Context context;

    public AdapterServices(List<ListServices> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_services,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        // Toast.makeText(context,String.valueOf(position), Toast.LENGTH_SHORT).show();

        ListServices listItem = listItems.get(position);
        holder.variety.setText(listItem.getVariety());
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
                                if(message.equalsIgnoreCase("Records deleted"))
                                {
                                    listItems.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position,listItems.size());
                                }

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

        private TextView sale_id,variety;
        private Button editButton,deleteButton;

        public ViewHolder(final View itemView) {
            super(itemView);

            sale_id = (TextView) itemView.findViewById(R.id.sale_id);
            variety = (TextView) itemView.findViewById(R.id.variety);

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
