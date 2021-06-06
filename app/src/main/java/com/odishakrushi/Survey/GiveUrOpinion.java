package com.odishakrushi.Survey;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.odishakrushi.Adapter.GiveUrOpinionAdapter;
import com.odishakrushi.Config;
import com.odishakrushi.Model.GiveUrOpinionModel;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.R;
import com.odishakrushi.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiveUrOpinion extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<GiveUrOpinionModel> listItems;

    AppCompatActivity context;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";

    int flag=0;

    public GiveUrOpinion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context= (AppCompatActivity) getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_give_ur_opinion, container, false);
    }
    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // getActivity().setTitle(getString(R.string.Conduct_Survey));

        recyclerView=getView().findViewById(R.id.recyclerView);


        //GETTING USER_ID
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", user_id);

        recyclerView=(RecyclerView)getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        listItems=new ArrayList<>();

        if(flag==0)
            getSurveyQuestion();


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,recyclerView,new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View view, int position) {

                GiveUrOpinionModel list = listItems.get(position);
                String questext=list.getQuestion();
                String id=list.getId();
                String option_1=list.getOpt_1();
                String option_2=list.getOpt_2();
                String option_3=list.getOpt_3();
                String option_4=list.getOpt_4();
                String option_5=list.getOpt_5();


                Intent i = new Intent( getActivity(), GiveUrOpinionFarmer.class);

                i.putExtra("id", id );
                i.putExtra("question",questext);
                i.putExtra("opt_1", option_1 );
                i.putExtra("opt_2", option_2 );
                i.putExtra("opt_3",option_3);
                i.putExtra("opt_4", option_4 );
                i.putExtra("opt_5", option_5 );
                startActivity(i);
            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }


    private void getSurveyQuestion() {
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();


        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                Config.getSurveyQuestion+"?grp="+Preferences.getGroupID(getActivity())+"&district="+ Preferences.getDistrictID(getActivity()) +"&user_id="+user_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {

                            Log.d("onResponse",s);
                            JSONObject jsonObject=new JSONObject(s);
                            // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                            String message="No records found";
                            if (message.contains(jsonObject.getString("message")))
                            {
                                progressDialog.hide();
                               /* Alerter.create(SaleList.this)
                                        .setTitle("No records found")
                                        .show();*/
                                Toast.makeText(getActivity(), "No records found", Toast.LENGTH_SHORT).show();
                            }
                            JSONArray array=jsonObject.getJSONArray("data");

                            for(int i=0;i<array.length();i++)
                            {
                                String question,opt_1,opt_2,opt_3,opt_4,opt_5,id;

                                JSONObject o=array.getJSONObject(i);
                                question=o.getString("question");
                                opt_1=o.getString("opt_1");
                                opt_2=o.getString("opt_2");
                                opt_3=o.getString("opt_3");
                                opt_4=o.getString("opt_4");
                                opt_5=o.getString("opt_5");
                                id=o.getString("id");

                                GiveUrOpinionModel item=new GiveUrOpinionModel(
                                        question,opt_1,opt_2,opt_3,opt_4,opt_5,id);


                                listItems.add(item);

                            }

                            adapter=new GiveUrOpinionAdapter(listItems,getActivity());
                            //  adapter.setOnLoadMoreListener(onLoadMoreListener);
                            recyclerView.setAdapter(adapter);

                            progressDialog.hide();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.hide();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                /* progressDialog.dismiss();*/
                Toast.makeText(getActivity(),"Error while loading",Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onPause() {
        super.onPause();
        flag=1;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("lifecycle","onResume invoked");
        if(flag==1)
        {
            recyclerView.setHasFixedSize(true);
            //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            listItems=new ArrayList<>();
            getSurveyQuestion();
        }

    }
}
