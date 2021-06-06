package com.odishakrushi.Adapter;

/**
 * Created by RatnaDev008 on 8/22/2018.
 */

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.odishakrushi.Model.MedicineFeed;
import com.odishakrushi.R;

/**
 * Created by Debashish on 16-09-2017.
 */
public class AdapterMedicineFeed extends RecyclerView.Adapter<AdapterMedicineFeed.ViewHolder> {

    private List<MedicineFeed> listItems;
    private Context context;

    public AdapterMedicineFeed(List<MedicineFeed> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_medicine_feed_for_sale,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MedicineFeed listItem = listItems.get(position);

        String str_medicine=listItem.getMedicine();
        if(str_medicine.equals("null")){
            str_medicine="";
            holder.txtmedicine.setVisibility(View.GONE);
        }
        holder.txtmedicine.setText(str_medicine);

        String str_medicine_for=listItem.getMedicinefor();
        if(str_medicine_for.equals("null")){
            str_medicine_for="";

            holder.txtmedicinefor.setVisibility(View.GONE);
        }
        holder.txtmedicinefor.setText(str_medicine_for);

        String str_feed=listItem.getFeed();
        if(str_feed.equals("null")){
            str_feed="";

            holder.txtfeed.setVisibility(View.GONE);
        }
        holder.txtfeed.setText(str_feed);


        String str_net=listItem.getNet();
        if(str_net.equals("null")){
            str_net="";

            holder.txtnet.setVisibility(View.GONE);
        }
        holder.txtnet.setText(str_net);
        //   Picasso.with(context).load(listItem.getImgurl()).into(holder.mobileImage);



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtmedicine,txtmedicinefor,txtfeed,txtnet;
        public ViewHolder(View itemView) {
            super(itemView);

            txtmedicine = (TextView) itemView.findViewById(R.id.txtmedicine);
            txtmedicinefor = (TextView) itemView.findViewById(R.id.txtmedicinefor);
            txtfeed = (TextView) itemView.findViewById(R.id.txtfeed);
            txtnet = (TextView) itemView.findViewById(R.id.txtnet);
        }

    }
}
