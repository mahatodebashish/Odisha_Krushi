package com.odishakrushi.Message;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.odishakrushi.Adapter.MyAdapterViewMessages;
import com.odishakrushi.Config;
import com.odishakrushi.Model.ListViewMessages;
import com.odishakrushi.R;
import com.odishakrushi.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewMessages extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListViewMessages> listItems;


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";
    String login_user="";
    TextView text_no_msg;
    public ViewMessages() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedpreferences = getContext().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("LOGGED_IN_AS", "");
        editor.commit(); // commit changes

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_messages, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles

        if(login_user.equals("2")) { // check for Farmer group then only set the title or else we donot require
            getActivity().setTitle(getString(R.string.View_Messages));
        }
        text_no_msg=getView().findViewById(R.id.text_no_msg);
        //GETTING USER_ID
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", user_id);

        recyclerView=(RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        listItems=new ArrayList<>();

    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                //"http://demo.ratnatechnology.co.in/farmerapp/api/message/get_message?user_id="+user_id, Doubt
                Config.baseUrl+"message/get_message?user_id="+user_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {

                            Log.d("onResponse:", s);

                            //write the response to a file
                            Utils.write(getContext(),"ViewMessages",s);

                            JSONObject jsonObject = new JSONObject(s);
                            // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                            if (jsonObject.getString("message").equals("No records found"))
                            {
                                progressDialog.hide();
                                text_no_msg.setVisibility(View.VISIBLE);
                            } else {
                                JSONArray array = jsonObject.getJSONArray("data");


                                if (array.length() == 0) {
                                    text_no_msg.setVisibility(View.VISIBLE);
                                    progressDialog.hide();
                                }
                                for (int i = 0; i < array.length(); i++) {
                                    String first_name = "", description = "", message_text = "",
                                            date_time = "", message_id = "";


                                    JSONObject o = array.getJSONObject(i);
                                    first_name = o.getString("first_name");
                                    description = o.getString("description");
                                    message_text = o.getString("message_text");
                                    date_time = o.getString("date_time");
                                    message_id = o.getString("message_id");
                                    ListViewMessages item = new ListViewMessages(
                                            message_id, first_name, description, message_text, date_time);


                                    listItems.add(item);

                                }

                                adapter = new MyAdapterViewMessages(listItems, getActivity());
                                //  adapter.setOnLoadMoreListener(onLoadMoreListener);
                                recyclerView.setAdapter(adapter);
                                progressDialog.dismiss();
                            }
                            } catch(JSONException e){
                                e.printStackTrace();
                            }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"APP IS OFFLINE",Toast.LENGTH_LONG).show();

                String s=Utils.read(getContext(),"AnswerList");
                try {

                    Log.d("onResponse:", s);

                    //write the response to a file
                    Utils.write(getContext(),"ViewMessages",s);

                    JSONObject jsonObject = new JSONObject(s);
                    // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    if (jsonObject.getString("message").equals("No records found"))
                    {
                        progressDialog.hide();
                        text_no_msg.setVisibility(View.VISIBLE);
                    } else {
                        JSONArray array = jsonObject.getJSONArray("data");


                        if (array.length() == 0) {
                            text_no_msg.setVisibility(View.VISIBLE);
                            progressDialog.hide();
                        }
                        for (int i = 0; i < array.length(); i++) {
                            String first_name = "", description = "", message_text = "",
                                    date_time = "", message_id = "";


                            JSONObject o = array.getJSONObject(i);
                            first_name = o.getString("first_name");
                            description = o.getString("description");
                            message_text = o.getString("message_text");
                            date_time = o.getString("date_time");
                            message_id = o.getString("message_id");
                            ListViewMessages item = new ListViewMessages(
                                    message_id, first_name, description, message_text, date_time);


                            listItems.add(item);

                        }

                        adapter = new MyAdapterViewMessages(listItems, getActivity());
                        //  adapter.setOnLoadMoreListener(onLoadMoreListener);
                        recyclerView.setAdapter(adapter);
                        progressDialog.dismiss();
                    }
                } catch(JSONException e){
                    e.printStackTrace();
                }
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
}
