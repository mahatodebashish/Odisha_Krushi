package com.odishakrushi.Adapter;

/**
 * Created by RatnaDev008 on 8/31/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.media.MediaPlayer;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.odishakrushi.AskQSale.PopUpAgrilProductSale;
import com.odishakrushi.Model.ListViewStory;
import com.odishakrushi.Model.ProductOnSale;
import com.odishakrushi.R;



public class AdapterViewMyStory extends RecyclerView.Adapter<AdapterViewMyStory.ViewHolder> {

    MediaController mediaControls;
    private List<ListViewStory> listItems;
    private Context context;

    public AdapterViewMyStory(List<ListViewStory> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_view_mystory,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Toast.makeText(context,String.valueOf(position), Toast.LENGTH_SHORT).show();

        ListViewStory listItem = listItems.get(position);
        holder.category_name.setText(listItem.getCategory_name());
        holder.sub_category_name.setText(listItem.getSub_category_name());
        holder.dropdown.setText(listItem.getDropdown());
        holder.story_desc.setText(listItem.getQuestext());
        final String url=listItem.getUrl();

        holder.mediaUrl.setText(Html.fromHtml("<u>@Attachment</u>"));

        if(url.equals(null)||url.equals("")){
            holder.mediaUrl.setVisibility(View.GONE);
        }
      /*  else{
                if(url.contains(".jpg")||url.contains(".png")||url.contains(".bmp")||url.contains(".jpeg"))
                {

                }

                else if(url.contains(".mp4")) {


                }

        }*/

      holder.mediaUrl.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(Intent.ACTION_VIEW,
                      Uri.parse(url));
              intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              context.startActivity(intent);
          }
      });



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView category_name, sub_category_name, dropdown, story_desc;
        private TextView mediaUrl;


        @SuppressLint("NewApi")
        public ViewHolder(final View itemView) {
            super(itemView);

            category_name = (TextView) itemView.findViewById(R.id.category_name);
            sub_category_name = (TextView) itemView.findViewById(R.id.sub_category_name);
            dropdown = (TextView) itemView.findViewById(R.id.dropdown);
            story_desc = (TextView) itemView.findViewById(R.id.story_desc);
            mediaUrl = (TextView) itemView.findViewById(R.id.mediaUrl);


        }


    }

}
