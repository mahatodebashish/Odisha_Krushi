package com.odishakrushi.BusinessProductPromotion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.odishakrushi.Config;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.odishakrushi.Adapter.AdapterPestiOnSale;
import com.odishakrushi.Adapter.AdapterServices;
import com.odishakrushi.Model.ListPestiOnSale;
import com.odishakrushi.Model.ListServiceAvailable;
import com.odishakrushi.Model.ListServices;
import com.odishakrushi.R;
import com.odishakrushi.RecyclerTouchListener;
//import spencerstudios.com.bungeelib.Bungee;

public class SaleListServices extends AppCompatActivity {

    ProgressBar progressBar;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListServices> listItems;

    int flag=0;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";

    Intent intent;
    String str_sale_id="",str_variety="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_list_services);

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
        getSupportActionBar().setTitle(getString(R.string.Services));

        progressBar=findViewById(R.id.progressBar);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);

        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", user_id);


        recyclerView.setHasFixedSize(true);
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        listItems=new ArrayList<>();

        if(flag==0)
            loadProductlist();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,recyclerView,new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View view, final int position) {


                ListServices list = listItems.get(position);
                str_sale_id  = list.getSale_id();
                str_variety=list.getVariety();

                Button edit=(Button)view.findViewById(R.id.editButton);
                edit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                       /* Toast.makeText(SaleList.this, "Single Click on Image :"+position,
                                Toast.LENGTH_SHORT).show();
                        Toast.makeText(SaleList.this, str_sale_id, Toast.LENGTH_SHORT).show();*/
                        Intent i = new Intent(SaleListServices.this, PopUpServices.class);
                        i.putExtra("PROD_NAME",str_variety);
                        i.putExtra("SALE_ID", str_sale_id );
                        startActivity(i);
                    }
                });

                Button deleteButton=(Button)view.findViewById(R.id.deleteButton);
               /* deleteButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        // showDeleteDialog();
                        Intent intentDelete=new Intent(SaleList.this, DeleteRecord.class);
                        intentDelete.putExtra("SALE_ID", str_sale_id );
                        startActivity(intentDelete);


                    }
                }); commented by deb*/
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    private void loadProductlist() {
        /*final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();*/
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                //"http://demo.ratnatechnology.co.in/farmerapp/api/sales/getallsalesbyuserId?user_id="+user_id+"&product_type="+str_variety+"&category_name=Services", Doubt
                Config.baseUrl+"sales/getallsalesbyuserId?user_id="+user_id+"&product_type="+str_variety+"&category_name=Services",
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
                                progressBar.setVisibility(View.GONE);
                               /* Alerter.create(SaleListServices.this)
                                        .setTitle("No records found")
                                        .show();*/
                                Toast.makeText(SaleListServices.this, "No records found", Toast.LENGTH_SHORT).show();
                            }
                            JSONArray array=jsonObject.getJSONArray("data");

                            for(int i=0;i<array.length();i++)
                            {
                                String sale_id="",variety="";

                                JSONObject o=array.getJSONObject(i);
                                sale_id=o.getString("sale_id");
                                variety=o.getString("product_type");
                                ListServices item=new ListServices(
                                        sale_id,variety);


                                listItems.add(item);

                            }

                            adapter=new AdapterServices(listItems,getApplicationContext());
                            //  adapter.setOnLoadMoreListener(onLoadMoreListener);
                            recyclerView.setAdapter(adapter);

                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               /* progressDialog.dismiss();*/
                Toast.makeText(getApplicationContext(),"Error while loading",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {


            intent=new Intent(this,PopUpServices.class);
            intent.putExtra("PROD_NAME",str_variety);
            intent.putExtra("SALE_ID",str_sale_id);

            startActivity(intent);
//            Bungee.slideLeft(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        flag=1;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle","onResume invoked");
        if(flag==1)
        {
            recyclerView.setHasFixedSize(true);
            //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            listItems=new ArrayList<>();
            loadProductlist();
        }

    }
}
