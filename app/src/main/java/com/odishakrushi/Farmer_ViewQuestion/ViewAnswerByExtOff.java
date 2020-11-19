package com.odishakrushi.Farmer_ViewQuestion;

//import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.Adapter.MyAdapter;
import com.odishakrushi.Adapter.MyAdapterExt;
import com.odishakrushi.Config;
import com.odishakrushi.Model.ListItem;
import com.odishakrushi.Model.ListItemExt;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.R;
import com.odishakrushi.RecyclerTouchListener;
import com.odishakrushi.utils.Utils;

public class ViewAnswerByExtOff  extends Fragment {


    AppCompatActivity context;
/*
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy*/

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItemExt> listItems;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";

    TextView txt_no_answers_to_asked_questions;
    String str_user_ind="";

    ImageView image_ad;
    String smallBannerUrl="";

    public ViewAnswerByExtOff() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context= (AppCompatActivity) getActivity();

        /* RECEIVING PART */
        // Register the local broadcast
        LocalBroadcastManager.getInstance(context).registerReceiver(mBannerUrlReceiver,
                new IntentFilter("small-banner-url"));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_view_answers_by_extoff, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // getActivity().setTitle("Ask Question");
        getActivity().setTitle(getString(R.string.View_Answers));
        txt_no_answers_to_asked_questions=getView().findViewById(R.id.txt_no_answers_to_asked_questions);


        //getting user_id
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);
        user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", user_id);

/*
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            str_user_ind = bundle.getString("USER_IND");

        }*/

        image_ad=getView().findViewById(R.id.image_ad);
        recyclerView=(RecyclerView)getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listItems=new ArrayList<>();
        loadRecyclerViewData();

        /**
         * @desc: Calling getSmallBannerAd Api for loading Small Banner Ad..
         */
        String str_zone_id= Preferences.getZone(getActivity());
        String str_group_id=Preferences.getGroupID(getActivity());
        Utils.getSmallBannerAd(getActivity(),str_zone_id,str_group_id,image_ad);
/*
        image_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!smallBannerUrl.equals("")){
                    Toast.makeText(context, smallBannerUrl, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(smallBannerUrl));
                    startActivity(intent);
                }


            }
        });
*/

    }


    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
       if (str_user_ind.equals(""))
       {
           str_user_ind="farmer";
       }
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                //"http://demo.ratnatechnology.co.in/farmerapp/api/qna/getAnswerByUserId?user_id="+user_id+"&user_id="+str_user_ind, Doubt
                Config.baseUrl+"qna/getAnswerByUserId?user_id="+user_id+"&user_id="+str_user_ind,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {

                            Log.d("onResponse:",s);


                            //write the response to a file
                            Utils.write(getContext(),"ViewAnswerByExtOff",s);

                            JSONObject jsonObject=new JSONObject(s);
                            // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                            if(jsonObject.getBoolean("status")==false)
                            {

                                txt_no_answers_to_asked_questions.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }
                            JSONArray array=jsonObject.getJSONArray("data");

                            if(array.length()==0)
                            {
                                txt_no_answers_to_asked_questions.setVisibility(View.VISIBLE);
                               progressDialog.dismiss();
                            }
                            for(int i=0;i<array.length();i++)
                            {
                                 String questext;
                                 String question_date;
                                 String ans_dt;
                                 String anstext;
                                 String dropdown;

                                JSONObject o=array.getJSONObject(i);
                                questext=o.getString("questext");
                                question_date=o.getString("question_date");
                                ans_dt=o.getString("ans_dt");
                                anstext=o.getString("anstext");
                                dropdown=o.getString("dropdown");

                                ListItemExt item=new ListItemExt(
                                        questext,question_date,ans_dt,anstext,dropdown);


                                listItems.add(item);

                            }

                            adapter=new MyAdapterExt(listItems,getActivity());
                            recyclerView.setAdapter(adapter);
                             progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

               // Toast.makeText(getActivity(),"Error while loading",Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(),"APP IS OFFLINE",Toast.LENGTH_LONG).show();

                String s=Utils.read(getContext(),"ViewAnswerByExtOff");

                try {

                    Log.d("onResponse:",s);


                    JSONObject jsonObject=new JSONObject(s);
                    // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    if(jsonObject.getBoolean("status")==false)
                    {

                        txt_no_answers_to_asked_questions.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                    JSONArray array=jsonObject.getJSONArray("data");

                    if(array.length()==0)
                    {
                        txt_no_answers_to_asked_questions.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                    for(int i=0;i<array.length();i++)
                    {
                        String questext;
                        String question_date;
                        String ans_dt;
                        String anstext;
                        String dropdown;

                        JSONObject o=array.getJSONObject(i);
                        questext=o.getString("questext");
                        question_date=o.getString("question_date");
                        ans_dt=o.getString("ans_dt");
                        anstext=o.getString("anstext");
                        dropdown=o.getString("dropdown");

                        ListItemExt item=new ListItemExt(
                                questext,question_date,ans_dt,anstext,dropdown);


                        listItems.add(item);

                    }

                    adapter=new MyAdapterExt(listItems,getActivity());
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mBannerUrlReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            smallBannerUrl = intent.getStringExtra("small_url");

        }
    };
}
