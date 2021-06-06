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
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.odishakrushi.Adapter.RequestedSurveyListAdapter;
import com.odishakrushi.Config;
import com.odishakrushi.Model.RequestedSurveyList;
import com.odishakrushi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyExtOff extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<RequestedSurveyList> listItems;

    AppCompatActivity context;
    ImageButton fab;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";

    int flag=0;

    public SurveyExtOff() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context= (AppCompatActivity) getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survey_ext_off, container, false);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getString(R.string.Conduct_Survey));

        recyclerView=getView().findViewById(R.id.recyclerView);
        fab=getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PostSurvey.class);
                startActivity(intent);
            }
        });

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
            getReqQuestion();
/*
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,recyclerView,new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View view, final int position) {

                RequestedSurveyList list = listItems.get(position);
                str_sale_id  = list.getSale_id();
                str_product_type=list.getProduct_type();
                strquantity=list.getQuantity();
                strvariety=list.getVariety();
                strprice=list.getPrice();

                Button edit=(Button)view.findViewById(R.id.editButton);
                edit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SaleList.this, "Single Click on Image :"+position,
                                Toast.LENGTH_SHORT).show();
                        Toast.makeText(SaleList.this, str_sale_id, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SaleList.this, PopUpAgrilProductSale.class);
                        i.putExtra("PROD_NAME",str_product_type);
                        i.putExtra("SALE_ID", str_sale_id );
                        i.putExtra("QTY",strquantity);
                        i.putExtra("VARIETY", strvariety );
                        i.putExtra("PRICE", strprice );
                        startActivity(i);
                    }
                });


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/
    }


    private void getReqQuestion() {
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();


        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                Config.getReqQuestion+"?user_id="+user_id,
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
                                String id,grp,question,zone,start_date,end_date,status,deleted;

                                JSONObject o=array.getJSONObject(i);
                                id=o.getString("id");
                                grp=o.getString("grp");
                                question=o.getString("question");
                                zone=o.getString("zone");
                                start_date=o.getString("start_date");
                                end_date=o.getString("end_date");
                                status=o.getString("status");
                                deleted=o.getString("deleted");

                                RequestedSurveyList item=new RequestedSurveyList(
                                        id,grp,question,zone,start_date,end_date,status,deleted);


                                listItems.add(item);

                            }

                            adapter=new RequestedSurveyListAdapter(listItems,getActivity());
                            //  adapter.setOnLoadMoreListener(onLoadMoreListener);
                            recyclerView.setAdapter(adapter);

                            progressDialog.hide();
                        } catch (JSONException e) {
                            e.printStackTrace();
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
            getReqQuestion();
        }

}
}
