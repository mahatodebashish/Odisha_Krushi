package com.odishakrushi.AskQSale;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
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
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.odishakrushi.Adapter.AdapterProductOnSale;
import com.odishakrushi.Config;
import com.odishakrushi.Model.ProductOnSale;
import com.odishakrushi.R;
import com.odishakrushi.RecyclerTouchListener;
//import spencerstudios.com.bungeelib.Bungee;

public class SaleList extends AppCompatActivity {

    ProgressBar progressBar;
    String message;
    String str_sale_id="",str_product_type="",strquantity="",strvariety="",strprice="";
    int flag=0;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ProductOnSale> listItems;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";

    private Toolbar toolbar;
    String strtitle="",strrank="";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_list);
        Log.d("lifecycle","onCreate invoked");

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


                ProductOnSale list = listItems.get(position);
                str_sale_id  = list.getSale_id();
                str_product_type=list.getProduct_type();
                strquantity=list.getQuantity();
                strvariety=list.getVariety();
                strprice=list.getPrice();

                Button edit=(Button)view.findViewById(R.id.editButton);
                edit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                       /* Toast.makeText(SaleList.this, "Single Click on Image :"+position,
                                Toast.LENGTH_SHORT).show();
                        Toast.makeText(SaleList.this, str_sale_id, Toast.LENGTH_SHORT).show();*/
                        Intent i = new Intent(SaleList.this, PopUpAgrilProductSale.class);
                        i.putExtra("PROD_NAME",str_product_type);
                        i.putExtra("SALE_ID", str_sale_id );
                        i.putExtra("QTY",strquantity);
                        i.putExtra("VARIETY", strvariety );
                        i.putExtra("PRICE", strprice );
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
               // "http://demo.ratnatechnology.co.in/farmerapp/api/sales/getallsalesbyuserId?user_id="+user_id+"&product_type="+strtitle+"&category_name=AP", Doubt
                Config.baseUrl+"sales/getallsalesbyuserId?user_id="+user_id+"&product_type="+strtitle+"&category_name=AP",
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
                               /* Alerter.create(SaleList.this)
                                        .setTitle("No records found")
                                        .show();*/
                                Toast.makeText(SaleList.this, "No records found", Toast.LENGTH_SHORT).show();
                            }
                            JSONArray array=jsonObject.getJSONArray("data");

                            for(int i=0;i<array.length();i++)
                            {
                                String sale_id="",seller_user_id="",category_name="",product_type="",
                                        quantity="",variety="",
                                        price="";

                                JSONObject o=array.getJSONObject(i);
                                sale_id=o.getString("sale_id");
                                seller_user_id=o.getString("seller_user_id");
                                category_name=o.getString("category_name");
                                product_type=o.getString("product_type");
                                quantity=o.getString("quantity");
                                variety=o.getString("variety");
                                price=o.getString("price");

                                ProductOnSale item=new ProductOnSale(
                                        sale_id, quantity,  variety,  price,product_type);


                                listItems.add(item);

                            }

                            adapter=new AdapterProductOnSale(listItems,getApplicationContext());
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
            }
            else if(strrank.equals("2"))
            {
                intent=new Intent(this,PopUpMachineToolSale.class);

            }
            else if(strrank.equals("3"))
            {
                intent=new Intent(this,PopUpSaleAnimal.class);
            }
            else if(strrank.equals("4"))
            {
                intent=new Intent(this,PopUpFish.class);
            }
            else if(strrank.equals("5"))
            {
                intent=new Intent(this,PopUpOtherProductToSale.class);
            }
            intent.putExtra("PROD_NAME",strtitle);
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

    public void showDeleteDialog()
    {
        new AlertDialog.Builder(this)
                .setTitle("Sure to delete?")
                //  .setMessage("Note that nuking planet Jupiter will destroy everything in there.")
                .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                      deleteAnItem();
                    }
                })
                .setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public  void deleteAnItem()
    {

        AndroidNetworking.post(Config.del_allsalesbyuserId)
                .addBodyParameter("sale_id", str_sale_id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            message="";
                            JSONObject object=new JSONObject(response);
                            message=object.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(SaleList.this, message, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(SaleList.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
