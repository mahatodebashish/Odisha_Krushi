package com.odishakrushi.Adapter;


import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.Config;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.odishakrushi.Model.MachineOnSale;
import com.odishakrushi.R;

/**
 * Created by Debashish on 16-09-2017.
 */
public class AdapterMachineOnSale extends RecyclerView.Adapter<AdapterMachineOnSale.ViewHolder> {

    String message="",str_sale_id="";
    private List<MachineOnSale> listItems;
    private Context context;

    public AdapterMachineOnSale(List<MachineOnSale> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_machine_sale,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        MachineOnSale listItem = listItems.get(position);
        str_sale_id=listItem.getSale_id();
        holder.sale_id.setText(listItem.getSale_id());

        String str_yr_of_purchase=listItem.getYr_of_purchase();
        if(str_yr_of_purchase.equals("null")){
            str_yr_of_purchase="";
            holder.yr_of_purchase.setVisibility(View.GONE);
        }
        holder.yr_of_purchase.setText(str_yr_of_purchase);


        String str_make=listItem.getMake();
        if(str_make.equals("null")){
            str_make="";
            holder.make.setVisibility(View.GONE);
        }
        holder.make.setText(str_make);

        String str_model=listItem.getModel();
        if(str_model.equals("null")){
            str_model="";
            holder.model.setVisibility(View.GONE);
        }
        holder.model.setText(str_model);

        String str_machineCondition=listItem.getMachine_condition();
        if(str_machineCondition.equals("null"))
        {
            str_machineCondition="";
            holder.machine_condition.setVisibility(View.GONE);
        }
        holder.machine_condition.setText(str_machineCondition);

        String str_capacity=listItem.getCapacity();
        if(str_capacity.equals("null")){
            str_capacity="";
            holder.capacity.setVisibility(View.GONE);
        }
        holder.capacity.setText(str_capacity);


        String str_power_source=listItem.getPower_source();
        if(str_power_source.equals("null")){
            str_power_source="";
            holder.power_source.setVisibility(View.GONE);
        }
        holder.power_source.setText(str_power_source);

        String str_sutable_for_crop=listItem.getSutable_for_crop();
        if(str_sutable_for_crop.equals("null")){
            str_sutable_for_crop="";
            holder.sutable_for_crop.setVisibility(View.GONE);
        }
        holder.sutable_for_crop.setText(str_sutable_for_crop);


        String str_subsidy=listItem.getSubsidy();
        if(str_subsidy.equals("null")){
            str_subsidy="";
            holder.subsidy.setVisibility(View.GONE);
        }
        holder.subsidy.setText(str_subsidy);

        String str_remark=listItem.getRemark();
        if(str_remark.equals("null")){
            str_remark="";
            holder.remark.setVisibility(View.GONE);
        }
        holder.remark.setText(str_remark);

        Picasso.with(context)
                .load(listItem.getImage_url())
               .placeholder(R.drawable.tractor_placeholder) // vector images not supported
                .into(holder.image_url);


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

      ImageView image_url;
        TextView sale_id, yr_of_purchase,make,model,machine_condition,capacity,power_source,sutable_for_crop,subsidy,remark;
        Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            sale_id=(TextView)itemView.findViewById(R.id.sale_id);
            image_url = (ImageView) itemView.findViewById(R.id.image_url);
            yr_of_purchase = (TextView) itemView.findViewById(R.id.yr_of_purchase);
            make = (TextView) itemView.findViewById(R.id.make);
            model = (TextView) itemView.findViewById(R.id.model);
            machine_condition = (TextView) itemView.findViewById(R.id.machine_condition);
            capacity = (TextView) itemView.findViewById(R.id.capacity);
            power_source = (TextView) itemView.findViewById(R.id.power_source);
            sutable_for_crop = (TextView) itemView.findViewById(R.id.sutable_for_crop);
            subsidy = (TextView) itemView.findViewById(R.id.subsidy);
            remark = (TextView) itemView.findViewById(R.id.remark);
            deleteButton=(Button) itemView.findViewById(R.id.deleteButton);
        }

    }
}
