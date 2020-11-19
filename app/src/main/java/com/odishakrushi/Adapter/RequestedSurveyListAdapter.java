package com.odishakrushi.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.odishakrushi.Model.ListFishForSale;
import com.odishakrushi.Model.RequestedSurveyList;
import com.odishakrushi.R;

import java.util.List;

import at.blogc.android.views.ExpandableTextView;


public class RequestedSurveyListAdapter extends RecyclerView.Adapter<RequestedSurveyListAdapter.ViewHolder> {

    private List<RequestedSurveyList> listItems;
    private Context context;

    public RequestedSurveyListAdapter(List<RequestedSurveyList> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_request_survey_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        RequestedSurveyList listItem = listItems.get(position);


        String strStartDate=listItem.getStart_date();
        holder.start_date.setText("START DATE : "+strStartDate);

        String strEndDate=listItem.getEnd_date();
        holder.end_date.setText("END DATE : "+strEndDate);

        String strStatus=listItem.getStatus();
        if(strStatus.equals("0")){
            holder.status.setText("Not Reviewed");
            holder.status.setTextColor(Color.RED);
        }

        else  if(strStatus.equals("1")) {
            holder.status.setText("Reviewed");
            holder.status.setTextColor(Color.GREEN);
        }

       String question=listItem.getQuestion();
       holder.expandableTextView.setText(question);

        //   Picasso.with(context).load(listItem.getImgurl()).into(holder.mobileImage);


        // set animation duration via code, but preferable in your layout files by using the animation_duration attribute
        holder.expandableTextView.setAnimationDuration(750L);

        // set interpolators for both expanding and collapsing animations
        holder.expandableTextView.setInterpolator(new OvershootInterpolator());

        // or set them separately
        holder.expandableTextView.setExpandInterpolator(new OvershootInterpolator());
        holder.expandableTextView.setCollapseInterpolator(new OvershootInterpolator());

        // toggle the ExpandableTextView
        holder.buttonToggle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                holder.buttonToggle.setText(  holder.expandableTextView.isExpanded() ? "v" : "^");
                holder.expandableTextView.toggle();
            }
        });

// but, you can also do the checks yourself
        holder.buttonToggle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                if (holder.expandableTextView.isExpanded())
                {
                    holder.expandableTextView.collapse();
                    holder.buttonToggle.setText("View More");
                }
                else
                {
                    holder.expandableTextView.expand();
                    holder.buttonToggle.setText("View Less");
                }
            }
        });

    /*    // listen for expand / collapse events
        holder.expandableTextView.setOnExpandListener(new ExpandableTextView.OnExpandListener()
        {
            @Override
            public void onExpand(final ExpandableTextView view)
            {
                Log.d("TAG", "ExpandableTextView expanded");
            }

            @Override
            public void onCollapse(final ExpandableTextView view)
            {
                Log.d("TAG", "ExpandableTextView collapsed");
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView start_date,end_date,status;
        private ExpandableTextView expandableTextView;
        private Button buttonToggle;

        public ViewHolder(View itemView) {
            super(itemView);
            expandableTextView = (ExpandableTextView) itemView.findViewById(R.id.question);
            status = (TextView) itemView.findViewById(R.id.status);
            start_date = (TextView) itemView.findViewById(R.id.start_date);
            end_date = (TextView) itemView.findViewById(R.id.end_date);
            buttonToggle= (Button) itemView.findViewById(R.id.button_toggle);

        }

    }
}
