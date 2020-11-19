package com.odishakrushi.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.Config;
import com.odishakrushi.Model.GiveUrOpinionModel;
import com.odishakrushi.Model.ListAnswers;
import com.odishakrushi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;



public class GiveUrOpinionAdapter extends RecyclerView.Adapter<GiveUrOpinionAdapter.ViewHolder> {


    String path="";
    private List<GiveUrOpinionModel> listItems;
    private Context context;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public GiveUrOpinionAdapter(List<GiveUrOpinionModel> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_give_ur_opinion,parent,false);
        return new ViewHolder(v);



    }


    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {

        final GiveUrOpinionModel listItem = listItems.get(position);

        holder.id.setText("S"+listItem.getId());

        holder.question.setText(listItem.getQuestion());


    }



    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView id ,question ;


        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            question = (TextView) itemView.findViewById(R.id.question);

        }

    }

}


