package com.odishakrushi.AskQPurchase;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.odishakrushi.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import com.odishakrushi.Adapter.AdapterMedicineFeedPurchase;
import com.odishakrushi.Model.MedicineFeedPurchaseModel;
import com.odishakrushi.R;
import com.odishakrushi.RecyclerTouchListener;

public class MedicineFeedPurchase extends AppCompatActivity {

    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE =1 ;
    ImageButton call;
    String str_phone_number = "";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<MedicineFeedPurchaseModel> listItems;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id = "";

    private Toolbar toolbar;
    String strtitle = "";//, strrank = "";
    Intent intent;

    TextView txtnoseller;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_feed_purchase);
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
        //getSupportActionBar().setTitle("Purchase/ Fish");
        getSupportActionBar().setTitle(getString(R.string.Purchase)+"/"+getString(R.string.Medicine_Feed));

        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        user_id = sharedpreferences.getString("FLAG", "");
        Log.d("GET", user_id);


      /*  Alerter.create(MedicineFeedPurchase.this)
                .setTitle("Loading...")
                .show();*/

        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();

        txtnoseller=findViewById(R.id.txtnoseller);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        listItems = new ArrayList<>();
        loadProductlist();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {


                MedicineFeedPurchaseModel list = listItems.get(position);
                str_phone_number = list.getMobile();


                call = (ImageButton) view.findViewById(R.id.call);
                call.setOnClickListener(new View.OnClickListener() {

                    @SuppressLint("MissingPermission")
                    @Override
                    public void onClick(View v) {

                        if (checkPermission(Manifest.permission.CALL_PHONE)) {
                            call.setEnabled(true);
                            String dial = "tel:" + str_phone_number;
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                        } else {
                            call.setEnabled(false);
                            ActivityCompat.requestPermissions(MedicineFeedPurchase.this, new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
                        }


                    }
                });


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }


    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MAKE_CALL_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    call.setEnabled(true);
                    String dial = "tel:" + str_phone_number;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    //Toast.makeText(this, "You can call the number by clicking on the button", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private void loadProductlist() {


        StringRequest stringRequest=new StringRequest(Request.Method.GET,
               // "http://demo.ratnatechnology.co.in/farmerapp/api/sales/getallsalesbyuserId?user_id="+user_id+"&product_type=MedicineFeed", Doubt
                Config.baseUrl+"sales/getallsalesbyuserId?user_id="+user_id+"&product_type=MedicineFeed",
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

                               /* Alerter.create(MedicineFeedPurchase.this)
                                        .setTitle("No records found")
                                        .show();*/
                               txtnoseller.setVisibility(View.VISIBLE);
                            }
                            JSONArray array=jsonObject.getJSONArray("data");

                            for(int i=0;i<array.length();i++)
                            {
                                String sale_id="",seller_user_id="",category_name="",product_type="",
                                        quantity="",variety="",medicine_for="",medicine="",feed="",net="",
                                        price="",first_name="",phone="",village="",photo_url="",district_name="",block_name="";

                                JSONObject o=array.getJSONObject(i);

                                medicine=o.getString("medicine");
                                medicine_for=o.getString("medicine_for");
                                feed=o.getString("feed");
                                net=o.getString("net");
                                first_name=o.getString("first_name");
                                phone=o.getString("phone");
                                district_name=o.getString("district_name");
                                block_name=o.getString("block_name");
                                village=o.getString("village");
                                MedicineFeedPurchaseModel item=new MedicineFeedPurchaseModel(
                                        first_name,  medicine,  medicine_for,feed,net
                                        ,phone,district_name,block_name,village);


                                listItems.add(item);

                            }

                            adapter=new AdapterMedicineFeedPurchase(listItems,getApplicationContext());
                            //  adapter.setOnLoadMoreListener(onLoadMoreListener);
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               /* progressDialog.dismiss();*/
                Toast.makeText(getApplicationContext(),"No Network Available",Toast.LENGTH_LONG).show();

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
