package com.odishakrushi.OpenForum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.Config;
import com.odishakrushi.utils.FullScreenViewer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.odishakrushi.Adapter.MyAdapterOpenForumAnswersList;
import com.odishakrushi.Model.ListOpenFanswerers;
import com.odishakrushi.R;

public class Openforum_Detail extends AppCompatActivity {


    ImageView url,farmerProfilePic;
    EditText edittext;
    String str_posttext="";
    TextView farmer_name,date,questext,subject;
    String str_farmername="",str_questext="",str_date="",str_open_forum_question_id="",
            str_q_id="",str_answered_status="",str_exoff_name="",str_url="",str_farmer_profile_img="",str_subject="";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListOpenFanswerers> listItems;


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";

    TextView txt_no_questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_openforum__detail);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        url=findViewById(R.id.url);
        farmerProfilePic=findViewById(R.id.farmerProfilePic);
        farmer_name=findViewById(R.id.farmer_name);
        date=findViewById(R.id.date);
        questext=findViewById(R.id.questext);
        edittext=findViewById(R.id.edittext);
        subject=findViewById(R.id.subject);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            str_farmername = bundle.getString("FARMER_NAME"); farmer_name.setText(str_farmername);
            str_questext = bundle.getString("QUESTEXT"); questext.setText(str_questext);
            str_date = bundle.getString("DATE"); date.setText(str_date);
            str_open_forum_question_id=bundle.getString("OPENFORUM_QID");

            str_q_id = bundle.getString("QID");
            str_answered_status = bundle.getString("ANS_STATUS");
            str_exoff_name=bundle.getString("EXTOFF_NAME");
            str_url=bundle.getString("URL");
            str_farmer_profile_img=bundle.getString("FARMER_PROFILE_IMG");
            str_subject=bundle.getString("SUBJECT"); subject.setText(str_subject);

            if(!str_farmer_profile_img.equals("") || !str_farmer_profile_img.equals("null")
                    || str_farmer_profile_img!=null || !str_farmer_profile_img.equals(null)){
                Picasso.with(this).load(str_farmer_profile_img).error(R.drawable.ic_farmer).into(farmerProfilePic);
            }
           // url.setText(str_url);
            url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(str_url));
                startActivity(i);*/
                Intent urlIntent=new Intent(Openforum_Detail.this, FullScreenViewer.class);
                urlIntent.putExtra("URL",str_url);
                startActivity(urlIntent);
            }
        });
        }



        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", user_id);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        listItems=new ArrayList<>();
        loadRecyclerViewData();
    }

    private void loadRecyclerViewData() {
       /* final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();*/
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                //"http://demo.ratnatechnology.co.in/farmerapp/api/qna/getOpen_forumAnswerByQnaid?qna_id="+str_q_id, Doubt
                Config.baseUrl+"qna/getOpen_forumAnswerByQnaid?qna_id="+str_q_id,
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
                                String answer_text="",group="",first_name="",ans_date="";


                                JSONObject o=array.getJSONObject(i);
                                answer_text=o.optString("answer_text");
                                group=o.optString("group");
                                first_name=o.optString("first_name");
                                ans_date=o.optString("ans_date");

                                ListOpenFanswerers item=new ListOpenFanswerers(
                                        answer_text,group,first_name,ans_date);


                                listItems.add(item);

                            }

                            adapter=new MyAdapterOpenForumAnswersList(listItems,getApplicationContext());
                            //  adapter.setOnLoadMoreListener(onLoadMoreListener);
                            recyclerView.setAdapter(adapter);
                          //  progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               // progressDialog.dismiss();
                txt_no_questions.setVisibility(View.VISIBLE);
                Toast.makeText(Openforum_Detail.this,getString(R.string.Network_Error),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onClickPost(View view)
    {
        str_posttext=edittext.getText().toString();
        if(str_posttext.equalsIgnoreCase(""))
        {
           /* Alerter.create(Openforum_Detail.this)
                    .setTitle(getString(R.string.blanks))
                    .show();*/
            Toast.makeText(this, getString(R.string.blanks), Toast.LENGTH_SHORT).show();
        }
        else
        {
            //AndroidNetworking.post("http://demo.ratnatechnology.co.in/farmerapp/api/qna/open_forum_answer") Doubt
            AndroidNetworking.post(Config.baseUrl+"qna/open_forum_answer")
                    .addBodyParameter("user_id",user_id)
                    .addBodyParameter("qna_id", str_q_id)
                .addBodyParameter("answer_text", str_posttext)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String message= jsonObject.getString("message");

                               /* Alerter.create(Openforum_Detail.this)
                                        .setTitle(message)
                                        .show();*/

                                Toast.makeText(Openforum_Detail.this, message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            load();
                            edittext.setText("");
                        }

                        @Override
                        public void onError(ANError anError) {
                          /*  Alerter.create(Openforum_Detail.this)
                                    .setTitle(getString(R.string.Network_Error))
                                    .show();*/
                            Toast.makeText(Openforum_Detail.this, getString(R.string.Network_Error), Toast.LENGTH_SHORT).show();
                        }
                    } );
        }
    }

    public  void load()
    {
            recyclerView.setHasFixedSize(true);
            //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                    recyclerView.setLayoutManager(mLayoutManager);
            listItems=new ArrayList<>();
            loadRecyclerViewData();
    }
}
