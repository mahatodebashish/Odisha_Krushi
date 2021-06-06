package com.odishakrushi.Adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.odishakrushi.Model.ListTakeOnHire;
import com.odishakrushi.R;

/**
 * Created by Debashish on 16-09-2017.
 */
public class AdapterTakeOnHire extends RecyclerView.Adapter<AdapterTakeOnHire.ViewHolder> {


    private List<ListTakeOnHire> listItems;
    private Context context;

    public AdapterTakeOnHire(List<ListTakeOnHire> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_take_on_hire,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListTakeOnHire listItem = listItems.get(position);
        holder.description.setText(listItem.getDescription());
        holder.operational_area.setText(listItem.getOperational_area());
        holder.first_name.setText(listItem.getFirst_name());
        holder.phone.setText(listItem.getPhone());
        holder.start_date.setText(listItem.getStart_date());
        if(listItem.getProfile_img()!=null||!listItem.getProfile_img().equals(""))
        Picasso.with(context).load(listItem.getProfile_img()).placeholder(R.drawable.man).into(holder.profile_img);



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView description,operational_area,first_name,phone,start_date,txtCall;
        ImageView profile_img;


        public ViewHolder(View itemView) {
            super(itemView);

            description = (TextView) itemView.findViewById(R.id.description);
            operational_area = (TextView) itemView.findViewById(R.id.operational_area);
            first_name = (TextView) itemView.findViewById(R.id.first_name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            start_date = (TextView) itemView.findViewById(R.id.start_date);
            txtCall= (TextView) itemView.findViewById(R.id.txtCall);
            profile_img = (ImageView) itemView.findViewById(R.id.profile_img);

        }

    }
}
