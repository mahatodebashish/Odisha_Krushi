package com.odishakrushi.Adapter;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.Rating;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.Config;
import com.odishakrushi.Farmer_ViewQuestion.AnswerDetail;
import com.odishakrushi.Interfaces.OnListItemClick;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.odishakrushi.Model.ListAnswers;
import com.odishakrushi.Model.ListItem;
import com.odishakrushi.R;

/**
 * Created by Debashish on 16-09-2017.
 */
public class ListAnswersAdapter extends RecyclerView.Adapter<ListAnswersAdapter.ViewHolder> {


    String path="";
    private List<ListAnswers> listItems;
    private Context context;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;


    public ListAnswersAdapter(List<ListAnswers> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_answer_list,parent,false);
        return new ViewHolder(v);



    }


    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {

        final ListAnswers listItem = listItems.get(position);

            holder.questext.setText(listItem.getQuestext());
            holder.question_date.setText("Dt: " + listItem.getQuestion_date());

            final String url=listItem.getUrl();

            if(url.equals("")||url.equals(null)|url.equals("null")){
                holder.fileType.setVisibility(View.GONE);
            }
            else{
                Picasso.with(context).load(url).error(android.R.drawable.ic_menu_gallery).into(holder.fileType);
            }
            String rating = listItem.getRating();
            if (rating.equalsIgnoreCase("0.0") || rating.equalsIgnoreCase("1.0") || rating.equalsIgnoreCase("2.0")
                    || rating.equalsIgnoreCase("3.0") || rating.equalsIgnoreCase("4.0") || rating.equalsIgnoreCase("5.0")) {
                holder.layout_rating.setVisibility(View.VISIBLE);
                rating=rating + " out of 5";
                holder.txtRating.setText(rating);
            }


            String answer_status = listItem.getIs_answered();
            if (answer_status.equals("No")) {
                holder.is_answered.setText(context.getString(R.string.not_answered));
                holder.is_answered.setBackgroundColor(Color.parseColor("#FF0000"));
            } else if (answer_status.equals("Yes")) {
                holder.is_answered.setText(context.getString(R.string.answered)+" --> ");
                holder.is_answered.setBackgroundColor(Color.parseColor("#008000"));
            } else if(answer_status.equals("Answered in open forum")){
                holder.is_answered.setText(context.getString(R.string.answered_in_open_forum));
                holder.is_answered.setBackgroundColor(Color.parseColor("#0000FF"));
            }

            holder.card_view.setCardBackgroundColor(Color.parseColor("#e6ffff"));


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Sure to delete message ?");
                alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //AndroidNetworking.post("http://demo.ratnatechnology.co.in/farmerapp/api/qna/del_byqnaId") // Doubt in base url
                        AndroidNetworking.post(Config.baseUrl+"qna/del_byqnaId")
                                .addBodyParameter("qna_id",listItem.getQna_id())
                                .setTag("test")
                                .setPriority(Priority.HIGH)
                                .build()
                                .getAsString(new StringRequestListener() {

                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject=new JSONObject(response);
                                            String message=jsonObject.getString("message");
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                            listItems.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position,listItems.size());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(ANError error) {
                                        // handle error
                                        Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();


            }
        });

        final String finalRating = rating;
        holder.layoutArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,AnswerDetail.class);
                intent.putExtra("DROPDOWN",listItem.dropdown);
                intent.putExtra("QUESTEXT",listItem.getQuestext());
                intent.putExtra("QUESTION_DATE","Dt: " + listItem.getQuestion_date());
                intent.putExtra("URL",url);
                intent.putExtra("ANSTEXT",listItem.getAnstext());
                intent.putExtra("ANS_DATE",listItem.getAns_dt());
                intent.putExtra("QNA_ID",listItem.getQna_id());
                intent.putExtra("RATING", finalRating);
                context.startActivity(intent);

            }
        });

        }



    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView questext ,question_date ,is_answered,txtRating;
        public ImageView fileType;
        public ProgressBar progressBar;
        public Button deleteButton;
        public LinearLayout layout_rating,layoutArrow;
        public CardView card_view;
        //public RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            questext = (TextView) itemView.findViewById(R.id.questext);
            fileType = (ImageView) itemView.findViewById(R.id.fileType);
            question_date = (TextView) itemView.findViewById(R.id.question_date);
            is_answered = (TextView) itemView.findViewById(R.id.is_answered);
            deleteButton= (Button) itemView.findViewById(R.id.deleteButton);
            layout_rating= itemView.findViewById(R.id.layout_rating);
            layoutArrow= itemView.findViewById(R.id.layoutArrow);
           // ratingBar= itemView.findViewById(R.id.ratingBar);
            txtRating= (TextView) itemView.findViewById(R.id.txtRating);
            card_view= (CardView) itemView.findViewById(R.id.card_view);

        }

    }

}


