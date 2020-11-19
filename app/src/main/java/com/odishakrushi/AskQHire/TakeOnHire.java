package com.odishakrushi.AskQHire;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
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
import com.odishakrushi.Adapter.AdapterServicesAvailable;
import com.odishakrushi.Adapter.AdapterTakeOnHire;
import com.odishakrushi.AskQService.ServicesAvail;
import com.odishakrushi.Config;
import com.odishakrushi.Farmer_ViewQuestion.ViewQuestionList_Farmer;
import com.odishakrushi.Farmer_ViewQuestion.View_AskedQuestion_Detail;
import com.odishakrushi.Model.ListItem;
import com.odishakrushi.Model.ListServiceAvailable;
import com.odishakrushi.Model.ListTakeOnHire;
import com.odishakrushi.R;
import com.odishakrushi.RecyclerTouchListener;
import com.odishakrushi.Utility;

public class TakeOnHire extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListTakeOnHire> listItems;

    Spinner subgroup;
    String strsubgroup;

    TextView txtCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_on_hire);

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
        //getSupportActionBar().setTitle("Hire/Take On Hire");
        getSupportActionBar().setTitle(getString(R.string.Hire) + "/" + getString(R.string.Take_On_Hire));


        subgroup = findViewById(R.id.subgroup);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        subgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strsubgroup = subgroup.getSelectedItem().toString();

                listItems = new ArrayList<>();
                loadRecyclerViewData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                ListTakeOnHire list = listItems.get(position);
                final String call = list.getPhone();

                txtCall = findViewById(R.id.txtCall);
                txtCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL);//ACTION_DIAL
                        intent.setData(Uri.parse("tel:" + call));//"08763865936"
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void loadRecyclerViewData() {
       /* final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
*/
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
             //   "http://demo.ratnatechnology.co.in/farmerapp/api/sales/getTakeOnHire?machine_name="+strsubgroup, Doubt
                Config.baseUrl+"sales/getTakeOnHire?machine_name="+strsubgroup,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                      /*  progressDialog.dismiss();*/
                        try {

                            Log.d("onResponse:",s);
                            JSONObject jsonObject=new JSONObject(s);
                            // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                            String message=jsonObject.getString("message");
                            if(message.equals("No records found")&&!strsubgroup.equals("-Select-"))
                            {
                                Toast.makeText(TakeOnHire.this, message, Toast.LENGTH_SHORT).show();

                            }

                            else   if(message.equals("Records found"))
                            {

                                JSONArray array = jsonObject.getJSONArray("data");

                                for (int i = 0; i < array.length(); i++)
                                {
                                    String description="",operational_area="",profile_img="",first_name="",phone="",start_date="";

                                    JSONObject o = array.getJSONObject(i);
                                    description = o.getString("description");
                                    operational_area = o.getString("operational_area");
                                    profile_img = o.getString("profile_img");
                                    first_name = o.getString("first_name");
                                    phone = o.getString("phone");
                                    start_date = o.getString("start_date");


                                    ListTakeOnHire item = new ListTakeOnHire(
                                            description,operational_area,profile_img,first_name,phone,
                                            start_date);


                                    listItems.add(item);

                                }

                                adapter = new AdapterTakeOnHire(listItems, getApplicationContext());
                                recyclerView.setAdapter(adapter);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               /* progressDialog.dismiss();*/
                Toast.makeText(getApplicationContext(),"Error while loading",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
