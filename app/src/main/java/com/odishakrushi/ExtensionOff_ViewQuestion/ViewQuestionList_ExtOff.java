package com.odishakrushi.ExtensionOff_ViewQuestion;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
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

import com.odishakrushi.Adapter.MyAdapter;
import com.odishakrushi.Config;
import com.odishakrushi.Model.ListItem;
import com.odishakrushi.R;
import com.odishakrushi.RecyclerTouchListener;
import com.odishakrushi.utils.Utils;

public class ViewQuestionList_ExtOff extends Fragment {// extends AppCompatActivity

    AppCompatActivity context;
    int flag=0;

    //private static final String URL_DATA="http://demo.ratnatechnology.co.in/farmerapp/api/qna/getQnaByUserId?user_id=235&user_ind=farmer";
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public List<ListItem> listItems;


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";

    TextView textView;

    public ViewQuestionList_ExtOff() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context= (AppCompatActivity) getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_view_question_list__ext_off, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // getActivity().setTitle("Ask Question");
        getActivity().setTitle(getString(R.string.Ques_Asked_By_Farmer));

        textView=getView().findViewById(R.id.textView);

        //GETTING USER_ID
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);

        recyclerView=(RecyclerView)getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listItems=new ArrayList<>();

        if(flag==0)
        {
            loadRecyclerViewData();

        }
        //loadRecyclerViewData();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,recyclerView,new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View view, int position) {

                ListItem list = listItems.get(position);

                String qna_id  = list.getQna_id();
                String user_id=list.getUser_id();
                String dropdown=list.getDropdown();
                String questext=list.getQuestext();
                String is_answered=list.getIs_answered();
                String person_in_charge=list.getPerson_in_charge();
                String question_date=list.getQuestion_date();
                String ans_date=list.getAns_date();
                String url=list.getUrl();

                Intent i = new Intent(getActivity(), ViewQuestionDetail_ExtOff.class);
                i.putExtra("QNA_ID", qna_id );
                // i.putExtra("USER_ID",user_id);
                i.putExtra("DROPDOWN", dropdown );
                i.putExtra("QUESTEXT",questext);
                i.putExtra("IS_ANSWERED", is_answered );
                i.putExtra("PERSON_IN_CHARGE",person_in_charge);
                i.putExtra("QUESTION_DATE", question_date );
                i.putExtra("ANS_DATE",ans_date);

                i.putExtra("URL", url );
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                //"http://demo.ratnatechnology.co.in/farmerapp/api/qna/getQnaByUserId?user_id="+str_user_id+"&user_ind=resource", Doubt
                Config.baseUrl+"qna/getQnaByUserId?user_id="+str_user_id+"&user_ind=resource",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {

                            Log.d("onResponse:",s);

                            //write the response to a file
                            Utils.write(getContext(),"ViewQuestionList_ExtOff",s);

                            JSONObject jsonObject=new JSONObject(s);
                            if(jsonObject.getBoolean("status")==false)
                            {

                                textView.setVisibility(View.VISIBLE);
                                 progressDialog.dismiss();
                            }
                            JSONArray array=jsonObject.getJSONArray("data");

                            if(array.length()==0)
                            {
                                textView.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }

                            for(int i=0;i<array.length();i++)
                            {
                                String qna_id="",user_id="",dropdown="",questext="",
                                        is_answered="",person_in_charge="",
                                        question_date="",ans_date="",url="";

                                JSONObject o=array.getJSONObject(i);
                                qna_id=o.getString("qna_id");
                                user_id=o.getString("farmer_user_id");
                                dropdown=o.getString("dropdown");
                                questext=o.getString("questext");
                                is_answered=o.getString("is_answered");
                                person_in_charge=o.getString("person_in_charge");
                                question_date=o.getString("question_date");
                                ans_date=o.getString("ans_date");
                                url=o.getString("url");
                                ListItem item=new ListItem(
                                        qna_id,user_id,dropdown,questext,is_answered,person_in_charge,question_date,ans_date,url);


                                listItems.add(item);

                            }

                            adapter=new MyAdapter(listItems,getActivity());
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

                //Toast.makeText(getActivity(),getString(R.string.something_went_wrong),Toast.LENGTH_LONG).show();

                Toast.makeText(getActivity(),"APP IS OFFLINE",Toast.LENGTH_LONG).show();

                String s=Utils.read(getContext(),"ViewQuestionList_ExtOff");


                try {

                    Log.d("onResponse:",s);


                    JSONObject jsonObject=new JSONObject(s);
                    if(jsonObject.getBoolean("status")==false)
                    {

                        textView.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                    JSONArray array=jsonObject.getJSONArray("data");

                    if(array.length()==0)
                    {
                        textView.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }

                    for(int i=0;i<array.length();i++)
                    {
                        String qna_id="",user_id="",dropdown="",questext="",
                                is_answered="",person_in_charge="",
                                question_date="",ans_date="",url="";

                        JSONObject o=array.getJSONObject(i);
                        qna_id=o.getString("qna_id");
                        user_id=o.getString("farmer_user_id");
                        dropdown=o.getString("dropdown");
                        questext=o.getString("questext");
                        is_answered=o.getString("is_answered");
                        person_in_charge=o.getString("person_in_charge");
                        question_date=o.getString("question_date");
                        ans_date=o.getString("ans_date");
                        url=o.getString("url");
                        ListItem item=new ListItem(
                                qna_id,user_id,dropdown,questext,is_answered,person_in_charge,question_date,ans_date,url);


                        listItems.add(item);

                    }

                    adapter=new MyAdapter(listItems,getActivity());
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

    @Override
    public void onResume() {
        super.onResume();
        flag=1;
        if(flag==1)
        {
            recyclerView.setHasFixedSize(true);
            //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            listItems=new ArrayList<>();
            loadRecyclerViewData();
        }

    }

}
