package com.odishakrushi.AskQHire;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

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
import com.tapadoo.alerter.Alerter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.Adapter.AdapterGiveOnHire;
import com.odishakrushi.Adapter.AdapterProductOnSale;
import com.odishakrushi.AskQRepair.RepairMachines;
import com.odishakrushi.AskQSale.AgrilProductSale;
import com.odishakrushi.AskQSale.PopUpAgrilProductSale;
import com.odishakrushi.AskQSale.PopUpFish;
import com.odishakrushi.AskQSale.PopUpMachineToolSale;
import com.odishakrushi.AskQSale.PopUpOtherProductToSale;
import com.odishakrushi.AskQSale.PopUpSaleAnimal;
import com.odishakrushi.AskQSale.SaleList;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.FullscreenDialogFragment;
import com.odishakrushi.Model.GiveOnHireModel;
import com.odishakrushi.Model.ProductOnSale;
import com.odishakrushi.R;
import com.odishakrushi.RecyclerTouchListener;
import com.odishakrushi.TimePickerFragment;
import com.odishakrushi.Utility;
//import spencerstudios.com.bungeelib.Bungee;


public class GiveOnHire extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";

    ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<GiveOnHireModel> listItems;

    String str_id="",str_machine_name="",str_description="",str_start_date="",str_end_date="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_give_on_hire);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
       // getSupportActionBar().setTitle("Hire/Give On Hire");
        getSupportActionBar().setTitle(getString(R.string.Hire)+"/"+getString(R.string.Give_on_hire));

        progressBar=findViewById(R.id.progressBar);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        listItems=new ArrayList<>();
        loadProductlist();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,recyclerView,new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View view, final int position) {


                GiveOnHireModel list = listItems.get(position);
                str_id  = list.getId();
                str_machine_name= list.getMachine_name();
                str_description=list.getDescription();
                str_start_date=list.getStart_date();
                str_end_date=list.getEnd_date();

                Button delete=(Button)view.findViewById(R.id.btnDelete);
                delete.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        deleteRow(str_id, position);
                    }
                });

                Button btnEdit=(Button)view.findViewById(R.id.btnEdit);
                btnEdit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Bundle bundle = new Bundle();
                        bundle.putString("ID", str_id );
                        bundle.putString("MACHINE_NAME", str_machine_name );
                        bundle.putString("DESCRIPTION", str_description );
                        bundle.putString("START_DATE", str_start_date );
                        bundle.putString("END_DATE", str_end_date );
                        bundle.putString("UPDATE", "1" );

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        PopUpGiveOnHireFragment newFragment = new PopUpGiveOnHireFragment();
                        newFragment.setArguments(bundle);
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();

                        updateRow( str_id,str_machine_name,str_description,str_start_date,str_end_date,position);
                    }
                });

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void updateRow(String str_id,String str_machine_name,String str_description,String str_start_date,String str_end_date,final int position) {

       // AndroidNetworking.post("http://demo.ratnatechnology.co.in/farmerapp/api/sales/updateHire") Doubt
        AndroidNetworking.post(Config.baseUrl+"sales/updateHire")
                .addBodyParameter("id", str_id)
                .addBodyParameter("machine_name", str_machine_name)
                .addBodyParameter("description", str_description)
                .addBodyParameter("start_date", str_start_date)
                .addBodyParameter("end_date", str_end_date)
                .setTag("updaterow")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {


                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String data=jsonObject.optString("data");
                            Toast.makeText(GiveOnHire.this,data, Toast.LENGTH_SHORT).show();
                            listItems.remove(position);
                            adapter.notifyItemRemoved(position);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(GiveOnHire.this,"Some thing went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteRow(String str_id,final int position) {
       // AndroidNetworking.post("http://demo.ratnatechnology.co.in/farmerapp/api/sales/del_byhireId") Doubt
        AndroidNetworking.post(Config.baseUrl+"sales/del_byhireId")
                .addBodyParameter("id", str_id) //
                .setTag("deleterow")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {


                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String data=jsonObject.optString("data");
                            Toast.makeText(GiveOnHire.this,data, Toast.LENGTH_SHORT).show();
                            listItems.remove(position);
                            adapter.notifyItemRemoved(position);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(GiveOnHire.this,"Some thing went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void loadProductlist() {
        /*final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();*/
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                //"http://demo.ratnatechnology.co.in/farmerapp/api/sales/get_hire?user_id="+str_user_id, Doubt
                Config.baseUrl+"sales/get_hire?user_id="+str_user_id,
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
                                Alerter.create(GiveOnHire.this)
                                        .setTitle("No records found")
                                        .show();
                            }
                            JSONArray array=jsonObject.getJSONArray("data");

                            for(int i=0;i<array.length();i++)
                            {
                                String description="",operational_area="",start_date="",end_date="",hirer_id="",machine_name="",id="";

                                JSONObject o=array.getJSONObject(i);
                                description=o.optString("description");
                                operational_area=o.optString("operational_area");
                                start_date=o.optString("start_date");
                                end_date=o.optString("end_date");
                                hirer_id=o.optString("hirer_id");
                                machine_name=o.optString("machine_name");
                                id=o.optString("id");

                                GiveOnHireModel item=new GiveOnHireModel(
                                        description,operational_area,start_date,end_date,hirer_id,machine_name,id);


                                listItems.add(item);

                            }

                            adapter=new AdapterGiveOnHire(listItems,getApplicationContext());
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

            /*Intent intent=new Intent(this,PopUpGiveOnHire.class);
            startActivity(intent);*/

            FragmentManager fragmentManager = getSupportFragmentManager();
            PopUpGiveOnHireFragment newFragment = new PopUpGiveOnHireFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
