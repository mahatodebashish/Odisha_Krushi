package com.odishakrushi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

import com.odishakrushi.Adapter.MyAdapterSearch;
import com.odishakrushi.Model.ListSearchresults;

public class SearchResults extends AppCompatActivity {

    String str_kdropdown_id="",tag="",search_url="";
    TextView txt_noresults;

    private RecyclerView recyclerView2;
    private RecyclerView.Adapter adapter2;
    private List<ListSearchresults> listItems2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Log.d("sra", "onCreate: ");

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


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            str_kdropdown_id = bundle.getString("str_kdropdown_id");
            tag= bundle.getString("tag");
        }
        txt_noresults=findViewById(R.id.txt_noresults);

        recyclerView2=(RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(mLayoutManager2);
        listItems2=new ArrayList<>();
        Search();
    }

    public void Search()
    {
        if(str_kdropdown_id.equals("1")&& !tag.equals(""))
        {
           // search_url="http://demo.ratnatechnology.co.in/farmerapp/api/farmer/search_mystory?myStory_tag="+tag; Doubt
            search_url=Config.baseUrl+"farmer/search_mystory?myStory_tag="+tag;
            loadRecyclerViewSearchResults(search_url);
        }
        else if(str_kdropdown_id.equals("2")&& !tag.equals(""))
        {
            //search_url="http://demo.ratnatechnology.co.in/farmerapp/api/farmer/search_qna?qna_tag="+tag; Doubt
            search_url=Config.baseUrl+"farmer/search_qna?qna_tag="+tag;
            loadRecyclerViewSearchResults(search_url);
        }

        else if(str_kdropdown_id.equals("3")&& !tag.equals(""))
        {
           // search_url="http://demo.ratnatechnology.co.in/farmerapp/api/farmer/search_sales?sales_tag="+tag; Doubt
            search_url=Config.baseUrl+"farmer/search_sales?sales_tag="+tag;
            loadRecyclerViewSearchResults(search_url);
        }



    }

    private void loadRecyclerViewSearchResults(String search_url) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();


        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                search_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {

                            Log.d("onResponse:",s);
                            JSONObject jsonObject=new JSONObject(s);
                            if(!jsonObject.getBoolean("status")){
                                txt_noresults.setVisibility(View.VISIBLE);
                                progressDialog.hide();;
                            }
                            // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                            JSONArray array=jsonObject.getJSONArray("data");


                            if(array.length()==0)
                            {
                                txt_noresults.setVisibility(View.VISIBLE);
                            }
                            for(int i=0;i<array.length();i++)
                            {
                                String dropdown="",  questext="",  district="",  block="",  phone="",  farmer_name="",  village="",
                                        url="",  category_name="",  sub_category_name="",  product_type="",  make="",  model="",  image_url="";

                                JSONObject o=array.getJSONObject(i);

                                if(str_kdropdown_id.equals("1")||str_kdropdown_id.equals("2"))
                                {
                                    dropdown=o.getString("dropdown");
                                    questext=o.getString("questext");
                                    district=o.getString("district");
                                    block=o.getString("block");
                                    phone=o.getString("phone");
                                    farmer_name=o.getString("farmer_name");
                                    village=o.getString("village");
                                    image_url=o.getString("url");
                                    category_name=o.getString("category_name");
                                    sub_category_name=o.getString("sub_category_name");
                                }
                                else if(str_kdropdown_id.equals("3"))
                                {
                                    category_name = o.getString("category_name");
                                    product_type = o.getString("product_type");
                                    make = o.getString("make");
                                    model = o.getString("model");
                                    image_url= o.getString("image_url");
                                }

                                ListSearchresults item=new ListSearchresults(
                                        dropdown,  questext,  district,  block,  phone,  farmer_name,  village,
                                          category_name,  sub_category_name,  product_type,  make,  model,  image_url);


                                listItems2.add(item);

                            }

                            adapter2=new MyAdapterSearch(listItems2,getApplicationContext());
                            //  adapter.setOnLoadMoreListener(onLoadMoreListener);
                            recyclerView2.setAdapter(adapter2);
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                txt_noresults.setVisibility(View.VISIBLE);
                Toast.makeText(SearchResults.this,getString(R.string.Network_Error),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
