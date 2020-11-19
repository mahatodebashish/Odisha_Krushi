package com.odishakrushi.AskQPurchase;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressCustom;
import cc.cloudist.acplibrary.ACProgressFlower;
import com.odishakrushi.Adapter.AdapterProductOnSale;
import com.odishakrushi.Adapter.AdapterPurchaseAP;
import com.odishakrushi.AskQSale.DeleteRecord;
import com.odishakrushi.AskQSale.PopUpAgrilProductSale;
import com.odishakrushi.AskQSale.SaleList;
import com.odishakrushi.Model.ProductOnSale;
import com.odishakrushi.Model.PurchaseAP;
import com.odishakrushi.R;
import com.odishakrushi.RecyclerTouchListener;

public class ProductListPurchase extends AppCompatActivity {

    TextView txtnoseller;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE =1 ;
    ImageButton call;
    String str_phone_number = "";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<PurchaseAP> listItems;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id = "";

    private Toolbar toolbar;
    String strtitle = "";//, strrank = "";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtnoseller=findViewById(R.id.txtnoseller);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {

            strtitle = bundle.getString("PROD_NAME");
           // strrank = bundle.getString("#RANK");

        }

        getSupportActionBar().setTitle(strtitle);

        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        user_id = sharedpreferences.getString("FLAG", "");
        Log.d("GET", user_id);


        Alerter.create(ProductListPurchase.this)
                .setTitle("Loading...")
                .show();

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


                PurchaseAP list = listItems.get(position);
                str_phone_number = list.getPhone();


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
                            ActivityCompat.requestPermissions(ProductListPurchase.this, new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
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
                //"http://demo.ratnatechnology.co.in/farmerapp/api/sales/getallsalesbyuserId?user_id="+user_id+"&product_type="+strtitle, Doubt
                Config.baseUrl+"sales/getallsalesbyuserId?user_id="+user_id+"&product_type="+strtitle,
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

                              /*  Alerter.create(ProductListPurchase.this)
                                        .setTitle("No records found")
                                        .show();*/
                                txtnoseller.setVisibility(View.VISIBLE);
                            }
                            JSONArray array=jsonObject.getJSONArray("data");

                            for(int i=0;i<array.length();i++)
                            {
                                String sale_id="",seller_user_id="",category_name="",product_type="",
                                        quantity="",variety="",
                                        price="",first_name="",phone="",village="",photo_url="",
                                        district_name="",business_district_name="",
                                        block_name="",business_block_name="",
                                        year_of_purchase="", make="", model="", condition="", image="";

                                JSONObject o=array.getJSONObject(i);
                                sale_id=o.getString("sale_id");
                                seller_user_id=o.getString("seller_user_id");
                                category_name=o.getString("category_name");
                                product_type=o.getString("product_type");
                                quantity=o.getString("quantity");
                                variety=o.getString("variety");
                                price=o.getString("price");
                                first_name=o.getString("first_name");
                                phone=o.getString("phone");
                                district_name=o.getString("district_name");
                                business_district_name=o.getString("business_district_name");
                                try {
                                    if (!business_district_name.equals(null))
                                        district_name=business_district_name;
                                }catch (Exception e ){}

                                block_name=o.getString("block_name");
                                business_block_name=o.getString("business_block_name");

                                try{
                                    if (!business_block_name.equals(null))
                                        block_name=business_block_name;
                                }catch (Exception e ){}

                                village=o.getString("village");


                                year_of_purchase=o.getString("yr_of_purchase");
                                make=o.getString("make");
                                model=o.getString("model");
                                condition=o.getString("machine_condition");
                                image=o.getString("image_url");

                                PurchaseAP item=new PurchaseAP(
                                        sale_id, seller_user_id,  product_type,  quantity,variety,price
                                ,first_name,phone,village,photo_url,district_name,block_name,
                                         year_of_purchase, make, model, condition, image);


                                listItems.add(item);

                            }

                            adapter=new AdapterPurchaseAP(listItems,getApplicationContext());
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
