package com.odishakrushi.Farmer_ViewQuestion;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.Adapter.MyAdapter;
import com.odishakrushi.AskQGovtSchemes.GSAsk;
import com.odishakrushi.AskQSoilCW.AskQSoilCW;
import com.odishakrushi.Ask_Agriculture;
import com.odishakrushi.Ask_Fishery;
import com.odishakrushi.Ask_Veterinary;
import com.odishakrushi.Config;
import com.odishakrushi.Model.ListItem;
import com.odishakrushi.NavDrawer.NavDrawer;
import com.odishakrushi.R;
import com.odishakrushi.RecyclerTouchListener;

public class ViewQuestionList_Farmer extends Fragment {

    AppCompatActivity context;
/*

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy
*/

   // private static final String URL_DATA="http://demo.ratnatechnology.co.in/farmerapp/api/qna/getQnaByUserId?user_id=235&user_ind=farmer";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";

    TextView txt_no_questions;


    public ViewQuestionList_Farmer() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context= (AppCompatActivity) getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_view_question_list__farmer, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // getActivity().setTitle("Ask Question");
        getActivity().setTitle(getString(R.string.View_Asked_Question));

        txt_no_questions=getView().findViewById(R.id.txt_no_questions);
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

        //loadRecyclerViewData();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,recyclerView,new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View view, int position) {

                ListItem list = listItems.get(position);
                String dropdown=list.getDropdown();
                String questext=list.getQuestext();
                String question_date=list.getQuestion_date();
                String url=list.getUrl();


                Intent i = new Intent( getActivity(), View_AskedQuestion_Detail.class);

                i.putExtra("DROPDOWN", dropdown );
                i.putExtra("QUESTEXT",questext);
                i.putExtra("QUESTION_DATE", question_date );
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
                //"http://demo.ratnatechnology.co.in/farmerapp/api/qna/getQnaByUserId?user_id="+user_id+"&user_ind=farmer", Doubt
                Config.baseUrl+"qna/getQnaByUserId?user_id="+user_id+"&user_ind=farmer",
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
                                String qna_id="",user_id="",dropdown="",questext="",
                                        is_answered="",person_in_charge="",
                                        question_date="",ans_date="",url="";

                                JSONObject o=array.getJSONObject(i);
                                qna_id=o.getString("qna_id");
                                user_id=o.getString("user_id");
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

        loadRecyclerViewData();
    }
}
