package com.odishakrushi.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.odishakrushi.Model.ListFishForSale;
import com.odishakrushi.R;

/**
 * Created by Debashish on 16-09-2017.
 */
public class AdapterFishForSale extends RecyclerView.Adapter<AdapterFishForSale.ViewHolder> {

    private List<ListFishForSale> listItems;
    private Context context;

    public AdapterFishForSale(List<ListFishForSale> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_fish_for_sale,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListFishForSale listItem = listItems.get(position);

        String str_fish_type=listItem.getFish_type();
        if(str_fish_type.equals("null")){
            str_fish_type="";
            holder.fish_type.setVisibility(View.GONE);
        }
        holder.fish_type.setText(str_fish_type);

        String category_name=  listItem.getCategory_name();
        if(category_name.equals("null")){
            category_name="";
            holder.status.setVisibility(View.GONE);
        }
        holder.status.setText("On "+category_name);

        //   Picasso.with(context).load(listItem.getImgurl()).into(holder.mobileImage);



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fish_type,status;

        public ViewHolder(View itemView) {
            super(itemView);
            fish_type = (TextView) itemView.findViewById(R.id.fish_type);
            status = (TextView) itemView.findViewById(R.id.status);

        }

    }
}
