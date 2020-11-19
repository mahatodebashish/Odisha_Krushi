package com.odishakrushi.OpenForum;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.odishakrushi.Adapter.MyAdapter;
import com.odishakrushi.Adapter.MyAdapterOpenForum;
import com.odishakrushi.Config;
import com.odishakrushi.Farmer_ViewQuestion.View_AskedQuestion_Detail;
import com.odishakrushi.Model.ListItem;
import com.odishakrushi.Model.ListOpenForum;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.R;
import com.odishakrushi.RecyclerTouchListener;
import com.odishakrushi.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenForumList extends Fragment {

    AppCompatActivity context;

/*

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy
*/


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListOpenForum> listItems;


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";

    TextView txt_no_questions;
    ImageView image_ad;
    String smallBannerUrl="";
    public OpenForumList() {
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
        return inflater.inflate(R.layout.fragment_open_forum_list, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // getActivity().setTitle("Ask Question");
        getActivity().setTitle(getString(R.string.Open_Forum));


        txt_no_questions=getView().findViewById(R.id.txt_no_questions);
        //GETTING USER_ID
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", user_id);


        image_ad=getView().findViewById(R.id.image_ad);

        /**
         * @desc: Calling getSmallBannerAd Api for loading Small Banner Ad..
         */
        String str_zone_id= Preferences.getZone(getActivity());
        String str_group_id=Preferences.getGroupID(getActivity());
        Utils.getSmallBannerAd(getActivity(),str_zone_id,str_group_id,image_ad);

        recyclerView=(RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        listItems=new ArrayList<>();




        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,recyclerView,new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View view, int position) {

                ListOpenForum list = listItems.get(position);
                String farmername=list.getFarmer_name();
                String questext=list.getQuestext();
                String date=list.getDate();
                String open_forum_question_id=list.getOpen_forum_question_id();
                String q_id=list.getQ_id();
                String answered_status=list.getIs_answer();
                String exoff_name=list.getFirst_name();
                String url=list.getUrl();
                String subject=list.getSubject();
                String farmer_profile_img=list.getFarmer_profile_img();

                Intent i = new Intent( getActivity(), Openforum_Detail.class);

                i.putExtra("FARMER_NAME", farmername );
                i.putExtra("QUESTEXT",questext);
                i.putExtra("DATE", date );
                i.putExtra("OPENFORUM_QID", open_forum_question_id );
                i.putExtra("QID",q_id);
                i.putExtra("ANS_STATUS", answered_status );
                i.putExtra("EXTOFF_NAME", exoff_name );
                i.putExtra("URL", url );
                i.putExtra("FARMER_PROFILE_IMG", farmer_profile_img );
                i.putExtra("SUBJECT", subject );
                startActivity(i);
            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));

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
    }
    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                //"http://demo.ratnatechnology.co.in/farmerapp/api/qna/getOpen_forumQna", Doubt
                Config.baseUrl+"qna/getOpen_forumQna",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {

                            Log.d("onResponse:",s);
                            JSONObject jsonObject=new JSONObject(s);
                            // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                            JSONArray array=jsonObject.getJSONArray("data");

                            if(array.length()==0)
                            {
                                txt_no_questions.setVisibility(View.VISIBLE);
                            }
                            for(int i=0;i<array.length();i++)
                            {
                                String url="", open_forum_question_id="",farmer_name="",q_id="",questext="",
                                        is_answer="",date="",
                                        group="",first_name="",farmer_profile_img="",subject="";

                                JSONObject o=array.getJSONObject(i);
                                url=o.getString("url");
                                open_forum_question_id=o.getString("open_forum_question_id");
                                farmer_name=o.getString("farmer_name");
                                q_id=o.getString("q_id");
                                questext=o.getString("questext");
                                is_answer=o.getString("is_answer");
                                date=o.getString("date");
                                group=o.getString("group");
                                first_name=o.getString("first_name");
                                farmer_profile_img=o.getString("farmer_profile_img");
                                subject=o.getString("subject");
                                ListOpenForum item=new ListOpenForum(
                                        url,open_forum_question_id, farmer_name,  questext,  q_id,  is_answer,  date,  group, first_name ,farmer_profile_img,subject);


                                listItems.add(item);

                            }

                            adapter=new MyAdapterOpenForum(listItems,getActivity());
                            //  adapter.setOnLoadMoreListener(onLoadMoreListener);
                            recyclerView.setAdapter(adapter);
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                txt_no_questions.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),"Error while loading",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onResume() {
        super.onResume();

            recyclerView.setHasFixedSize(true);
            //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            listItems=new ArrayList<>();
            loadRecyclerViewData();

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
