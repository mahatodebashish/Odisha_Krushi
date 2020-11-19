package com.odishakrushi.AskQSale;

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

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.Adapter.AdapterMachineOnSale;
import com.odishakrushi.Adapter.AdapterProductOnSale;
import com.odishakrushi.Model.MachineOnSale;
import com.odishakrushi.Model.ProductOnSale;
import com.odishakrushi.R;
import com.odishakrushi.RecyclerTouchListener;
//import spencerstudios.com.bungeelib.Bungee;

public class SaleListMachineTools extends AppCompatActivity {

    String str_purchase_yr="",str_make="",str_model="", str_capacity="", str_power_source="",
            str_suitable_for_crop="", str_subsidy="", str_remark="",str_machine_condition="";
    ProgressBar progressBar;
    String message;
    String str_sale_id="",str_product_type="",strquantity="",strvariety="",strprice="";
    int flag=0;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<MachineOnSale> listItems;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";

    private Toolbar toolbar;
    String strtitle="",strrank="";
    Intent intent;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_list_machine_tools);


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

        progressBar=findViewById(R.id.progressBar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {

            strtitle = bundle.getString("PROD_NAME");
            Toast.makeText(this, strtitle, Toast.LENGTH_SHORT).show();
            strrank = bundle.getString("#RANK");

        }

        getSupportActionBar().setTitle(strtitle);


        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", user_id);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
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


                MachineOnSale list = listItems.get(position);
                str_sale_id  = list.getSale_id();
                str_product_type=list.getProduct_type();
                str_purchase_yr=list.getYr_of_purchase();
                str_make=list.getMake();
                str_model=list.getModel();
                str_capacity=list.getCapacity();
                str_power_source=list.getPower_source();
                str_suitable_for_crop=list.getSutable_for_crop();
                str_remark=list.getRemark();
                str_subsidy=list.getSubsidy();
                str_machine_condition=list.getMachine_condition();


                Button edit=(Button)view.findViewById(R.id.editButton);
                edit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                       /* Toast.makeText(SaleList.this, "Single Click on Image :"+position,
                                Toast.LENGTH_SHORT).show();
                        Toast.makeText(SaleList.this, str_sale_id, Toast.LENGTH_SHORT).show();*/
                        Intent i = new Intent(SaleListMachineTools.this, PopUpMachineToolSale.class);
                        i.putExtra("SALE_ID",str_sale_id);
                        i.putExtra("PROD_NAME",str_product_type);
                        i.putExtra("PURCHASE_YR", str_purchase_yr );
                        i.putExtra("MAKE",str_make);
                        i.putExtra("MODEL", str_model );
                        i.putExtra("CAPACITY", str_capacity );
                        i.putExtra("POWER_SOURCE",str_power_source);
                        i.putExtra("SUITABLE_FOR_CROP", str_suitable_for_crop );
                        i.putExtra("REMARK",str_remark);
                        i.putExtra("SUBSIDY", str_subsidy );
                        i.putExtra("MACHINE_CONDITION",str_machine_condition);
                        startActivity(i);
                    }
                });

                Button deleteButton=(Button)view.findViewById(R.id.deleteButton);
               /* deleteButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        // showDeleteDialog();
                        Intent intentDelete=new Intent(SaleListMachineTools.this, DeleteRecord.class);
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
                //"http://demo.ratnatechnology.co.in/farmerapp/api/sales/getallsalesbyuserId?user_id="+user_id+"&product_type="+strtitle+"&category_name=Machine", Doubt
                Config.baseUrl+"sales/getallsalesbyuserId?user_id="+user_id+"&product_type="+strtitle+"&category_name=Machine",

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
                            /*    Alerter.create(SaleListMachineTools.this)
                                        .setTitle("No records found")
                                        .show();*/
                                Toast.makeText(SaleListMachineTools.this, "No records found", Toast.LENGTH_SHORT).show();
                            }
                            JSONArray array=jsonObject.getJSONArray("data");

                            for(int i=0;i<array.length();i++)
                            {
                                String sale_id="",product_type="",make="",yr_of_purchase="",model="",
                                        machine_condition="",capacity="",
                                        power_source="",sutable_for_crop="",
                                        subsidy="",remark="",image_url="",video_url="";

                                JSONObject o=array.getJSONObject(i);
                                sale_id=o.getString("sale_id");
                                product_type=o.getString("product_type");
                                make=o.getString("make");
                                yr_of_purchase=o.getString("yr_of_purchase");
                                model=o.getString("model");
                                machine_condition=o.getString("machine_condition");
                                capacity=o.getString("capacity");
                                power_source=o.getString("power_source");
                                sutable_for_crop=o.getString("sutable_for_crop");
                                subsidy=o.getString("subsidy");
                                remark=o.getString("remark");
                                image_url=o.getString("image_url");
                                video_url=o.getString("video_url");

                                MachineOnSale item=new MachineOnSale(
                                        sale_id,product_type, yr_of_purchase,  make,  model,machine_condition,capacity,power_source,
                                        sutable_for_crop,subsidy,remark,image_url,video_url);


                                listItems.add(item);

                            }

                            adapter=new AdapterMachineOnSale(listItems,getApplicationContext());
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

            if(strrank.equals("1"))
            {
                intent=new Intent(this,PopUpAgrilProductSale.class);
                intent.putExtra("PROD_NAME",strtitle);
            }
            else if(strrank.equals("2"))
            {
                intent=new Intent(this,PopUpMachineToolSale.class);
                intent.putExtra("PROD_NAME",strtitle);
                intent.putExtra("SALE_ID",str_sale_id);
                intent.putExtra("PURCHASE_YR", str_purchase_yr );
                intent.putExtra("MAKE",str_make);
                intent.putExtra("MODEL", str_model );
                intent.putExtra("CAPACITY", str_capacity );
                intent.putExtra("POWER_SOURCE",str_power_source);
                intent.putExtra("SUITABLE_FOR_CROP", str_suitable_for_crop );
                intent.putExtra("REMARK",str_remark);
                intent.putExtra("SUBSIDY", str_subsidy );
                intent.putExtra("MACHINE_CONDITION",str_machine_condition);

            }
            else if(strrank.equals("3"))
            {
                intent=new Intent(this,PopUpSaleAnimal.class);
                intent.putExtra("PROD_NAME",strtitle);
            }
            else if(strrank.equals("4"))
            {
                intent=new Intent(this,PopUpFish.class);
                intent.putExtra("PROD_NAME",strtitle);
            }
            else if(strrank.equals("5"))
            {
                intent=new Intent(this,PopUpOtherProductToSale.class);
                intent.putExtra("PROD_NAME",strtitle);
            }

            startActivity(intent);
//            Bungee.zoom(this);
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
