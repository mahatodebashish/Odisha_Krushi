package com.odishakrushi.Adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.cardview.widget.CardView;
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
import com.odishakrushi.Model.GiveOnHireModel;
import com.odishakrushi.R;

/**
 * Created by RatnaDev008 on 2/3/2019.
 */

public class AdapterGiveOnHire extends RecyclerView.Adapter<AdapterGiveOnHire.ViewHolder> {

    String id="";
    private List<GiveOnHireModel> listItems;
    private Context context;

    public AdapterGiveOnHire(List<GiveOnHireModel> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_give_on_hire,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if(position%2==0){
            holder.card_view.setCardBackgroundColor(Color.parseColor("#FFFFE0"));
        }
        else {
            holder.card_view.setCardBackgroundColor(Color.parseColor("#FFEFD5"));
        }

         final  GiveOnHireModel listItem = listItems.get(position);

        String str_machineName= listItem.getMachine_name();
        if(str_machineName.equals("null")){
            str_machineName="";
            holder.machineName.setVisibility(View.GONE);
        }
        holder.machineName.setText(str_machineName);

        String str_description=listItem.getDescription();
        if(str_description.equals("null")){
            str_description="";
            holder.description.setVisibility(View.GONE);
        }
        holder.description.setText(str_description);


        String str_operationalArea=listItem.getOperational_area();
        if(str_operationalArea.equals("null")){
            str_operationalArea="";
            holder.operationalArea.setVisibility(View.GONE);
        }
        holder.operationalArea.setText(str_operationalArea);

        String str_startDate=listItem.getStart_date();
        if(str_startDate.equals("null")){
            str_startDate="";
            holder.startDate.setVisibility(View.GONE);
        }
        holder.startDate.setText(str_startDate);


        String str_endDate=listItem.getEnd_date();
        if(str_endDate.equals("null")){
            str_endDate="";
            holder.endDate.setVisibility(View.GONE);
        }
        holder.endDate.setText(str_endDate);
       // id=listItem.getId();

       /* holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                deleteRow(String.valueOf(getItemId(position)),position);

            }
        });*/


    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView machineName, description , operationalArea ,  startDate, endDate;;
        private CardView card_view;
        private Button btnEdit,btnDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            card_view= (CardView) itemView.findViewById(R.id.card_view);
            machineName = (TextView) itemView.findViewById(R.id.machineName);
            description = (TextView) itemView.findViewById(R.id.description);
            operationalArea = (TextView) itemView.findViewById(R.id.operationalArea);
            startDate = (TextView) itemView.findViewById(R.id.startDate);
            endDate = (TextView) itemView.findViewById(R.id.endDate);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete =  itemView.findViewById(R.id.btnDelete);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                }
            });




        }



    }
    public void deleteRow(String id, final int position) {
        AndroidNetworking.post(Config.baseUrl+"sales/del_byhireId")
                .addBodyParameter("id", id) //
                .setTag("deleterow")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {


                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String data=jsonObject.optString("data");
                            Toast.makeText(context,data, Toast.LENGTH_SHORT).show();
                            listItems.remove(position);
                            notifyItemRemoved(position);
                            //notifyItemRangeChanged(getAdapterPosition(),listItems.size()-1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(context,"Some thing went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
