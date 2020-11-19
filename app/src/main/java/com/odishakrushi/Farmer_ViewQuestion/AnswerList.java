package com.odishakrushi.Farmer_ViewQuestion;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.app.Fragment;//support.v4.
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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

import com.odishakrushi.Adapter.ListAnswersAdapter;
import com.odishakrushi.Config;
import com.odishakrushi.Interfaces.OnListItemClick;
import com.odishakrushi.Model.ListAnswers;
import com.odishakrushi.R;
import com.odishakrushi.RecyclerTouchListener;
import com.odishakrushi.utils.Utils;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerList extends Fragment implements OnListItemClick {

    SwipeRefreshLayout swiperefresh;
    AppCompatActivity context;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListAnswers> listItems;


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";

    TextView txt_no_questions;


    //Added for new layout change
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    OnListItemClick onListItemClick;

    public AnswerList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context= (AppCompatActivity) getActivity();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_answer_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // getActivity().setTitle(getString(R.string.View_Asked_Question));

       // txt_no_questions = getView().findViewById(R.id.txt_no_questions);
        //GETTING USER_ID
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        user_id = sharedpreferences.getString("FLAG", "");
        Log.d("GET", user_id);

       /* swiperefresh=getView().findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        listItems = new ArrayList<>();
                        loadRecyclerViewData();
                    }
                }
        );*/

  /*  Commented bu Debashish
        toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        context.setSupportActionBar(toolbar);

        context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);



       viewPager = (ViewPager)  getView().findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)  getView().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);*/


        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        //listItems = new ArrayList<>();

        listItems = new ArrayList<>();
        loadRecyclerViewData();


       /* recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Log.d("View::",""+view);

                ListAnswers list = listItems.get(position);
                String dropdown = list.getDropdown();
                String questext = list.getQuestext();
                String question_date = list.getQuestion_date();
                String url = list.getUrl();
                String qna_id=list.getQna_id();
                String anstext=list.getAnstext();
                String ans_dt=list.getAns_dt();
                String is_answered=list.getIs_answered();
                String rating=list.getRating();

                if(is_answered.equals("Yes")||is_answered.equals("No")) {
                    Intent i = new Intent(getActivity(), AnswerDetail.class);

                    i.putExtra("DROPDOWN", dropdown);
                    i.putExtra("QUESTEXT", questext);
                    i.putExtra("QUESTION_DATE", question_date);
                    i.putExtra("URL", url);
                    i.putExtra("ANSTEXT", anstext);
                    i.putExtra("ANS_DATE", ans_dt);
                    i.putExtra("QNA_ID", qna_id);
                    i.putExtra("RATING", rating);
                    startActivity(i);
                }


            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/

    }

  private void loadRecyclerViewData() {
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                //"http://demo.ratnatechnology.co.in/farmerapp/api/qna/getAnswerByUserId?user_id="+user_id, Doubt
                Config.baseUrl+"qna/getAnswerByUserId?user_id="+user_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {

                            Log.d("onResponse:",s);

                            //write the response to a file
                            Utils.write(context,"AnswerList",s);


                            JSONObject jsonObject=new JSONObject(s);
                            // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                            JSONArray array=jsonObject.getJSONArray("data");

                            if(array.length()==0)
                            {
                      //          txt_no_questions.setVisibility(View.VISIBLE);
                            }
                            for(int i=0;i<array.length();i++)
                            {
                                String qna_id="",anstext="",dropdown="",questext="",
                                        is_answered="",person_in_charge="",
                                        question_date="",ans_dt="",url="",rating="";

                                JSONObject o=array.getJSONObject(i);
                                qna_id=o.getString("qna_id");
                                anstext=o.getString("anstext");
                                dropdown=o.getString("dropdown");
                                questext=o.getString("questext");
                                is_answered=o.getString("is_answered");
                                person_in_charge=o.getString("person_in_charge");
                                question_date=o.getString("question_date");
                                ans_dt=o.getString("ans_dt");
                                url=o.getString("url");
                                rating=o.getString("rating")+".0";
                                ListAnswers item=new ListAnswers(
                                        qna_id, questext,question_date,anstext,ans_dt,dropdown,is_answered,url,rating);


                                listItems.add(item);

                            }

                            adapter=new ListAnswersAdapter(listItems,getActivity());
                            //  adapter.setOnLoadMoreListener(onLoadMoreListener);
                            recyclerView.setAdapter(adapter);
                            progressDialog.dismiss();
                           // swiperefresh.setRefreshing(false); // Disables the refresh icon

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();

                Toast.makeText(getActivity(),"APP IS OFFLINE",Toast.LENGTH_LONG).show();

                //String s= "{\"status\":true,\"data\":[{\"ans_id\":null,\"farmer_user_id\":null,\"person_in_charge\":\"194\",\"anstext\":null,\"qna_id\":\"791\",\"rating\":null,\"ans_dt\":null,\"category_id\":\"1\",\"sub_category_id\":\"1\",\"user_id\":\"1164\",\"dropdown\":\"Flower\",\"questext\":\"which flower good for growing in garden\",\"is_answered\":\"No\",\"subject\":null,\"question_date\":\"27-06-2020  12:52 PM\",\"ans_date\":null,\"upload_id\":\"222\",\"url\":\"http:\\/\\/odishakrushi.in\\/uploads\\/image\\/1593242554181.jpg\",\"file_name\":\"1593242554181.jpg\"}]}";
                String s=Utils.read(context,"AnswerList");
                try {

                    Log.d("onResponse:",s);
                    JSONObject jsonObject=new JSONObject(s);
                    // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    JSONArray array=jsonObject.getJSONArray("data");

                    if(array.length()==0)
                    {
                        //          txt_no_questions.setVisibility(View.VISIBLE);
                    }
                    for(int i=0;i<array.length();i++)
                    {
                        String qna_id="",anstext="",dropdown="",questext="",
                                is_answered="",person_in_charge="",
                                question_date="",ans_dt="",url="",rating="";

                        JSONObject o=array.getJSONObject(i);
                        qna_id=o.getString("qna_id");
                        anstext=o.getString("anstext");
                        dropdown=o.getString("dropdown");
                        questext=o.getString("questext");
                        is_answered=o.getString("is_answered");
                        person_in_charge=o.getString("person_in_charge");
                        question_date=o.getString("question_date");
                        ans_dt=o.getString("ans_dt");
                        url=o.getString("url");
                        rating=o.getString("rating")+".0";
                        ListAnswers item=new ListAnswers(
                                qna_id, questext,question_date,anstext,ans_dt,dropdown,is_answered,url,rating);


                        listItems.add(item);

                    }

                    adapter=new ListAnswersAdapter(listItems,getActivity());
                    //  adapter.setOnLoadMoreListener(onLoadMoreListener);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                    // swiperefresh.setRefreshing(false); // Disables the refresh icon

                } catch (JSONException e) {
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

        // this code is commented and added in onCreate.. There  is no need to load the page if we come back from another
        //activity
       /* listItems = new ArrayList<>();
        loadRecyclerViewData();*/
    }


    @Override
    public void onClick(View view, int position) {

        ListAnswers list = listItems.get(position);
        String dropdown = list.getDropdown();
        String questext = list.getQuestext();
        String question_date = list.getQuestion_date();
        String url = list.getUrl();
        String qna_id=list.getQna_id();
        String anstext=list.getAnstext();
        String ans_dt=list.getAns_dt();
        String is_answered=list.getIs_answered();
        String rating=list.getRating();

        if(is_answered.equals("Yes")||is_answered.equals("No")) {
            Intent i = new Intent(getActivity(), AnswerDetail.class);

            i.putExtra("DROPDOWN", dropdown);
            i.putExtra("QUESTEXT", questext);
            i.putExtra("QUESTION_DATE", question_date);
            i.putExtra("URL", url);
            i.putExtra("ANSTEXT", anstext);
            i.putExtra("ANS_DATE", ans_dt);
            i.putExtra("QNA_ID", qna_id);
            i.putExtra("RATING", rating);
            startActivity(i);
        }
    }
}
