package com.odishakrushi.AskQHire;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.Ask_Hire;
import com.odishakrushi.Config;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.R;

public class TakeOnHireDetail extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    String str_hire_id,str_machine_type,str_date,str_start_time,str_end_time;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";

    TextView machine_name,date,start_time,end_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_on_hire_detail);

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
      //  getSupportActionBar().setTitle("Take On Hire");
        getSupportActionBar().setTitle(getString(R.string.Take_On_Hire));

        machine_name=findViewById(R.id.machine_name);
        date=findViewById(R.id.date);
        start_time=findViewById(R.id.start_time);
        end_time=findViewById(R.id.end_time);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null)
        {
            str_hire_id = bundle.getString("HIRE_ID");
            str_machine_type = bundle.getString("MACHINE_TYPE");
            str_date = bundle.getString("DATE");
            str_start_time = bundle.getString("START_TIME");
            str_end_time = bundle.getString("END_TIME");

            machine_name.setText(str_machine_type);
            date.setText("Date: "+str_date);
            start_time.setText("From: "+str_start_time);
            end_time.setText("To: "+str_end_time);

        }

        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);
    }

    public void onClickHire(View view)
    {
         new AsyncTakeOnHire().execute();
    }

    private class AsyncTakeOnHire extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String  success = null,message="";

        @Override
        protected void onPreExecute() {

            pDialog = new ProgressDialog(TakeOnHireDetail.this);
            pDialog.setMessage("Submitting...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void  doInBackground(Void... params) {



            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();

            cred.add(new BasicNameValuePair("hire_id",str_hire_id));
            cred.add(new BasicNameValuePair("user_id",str_user_id));
            cred.add(new BasicNameValuePair("machine_type",str_machine_type));
            cred.add(new BasicNameValuePair("status","Want to Take on Hire"));


            cred.add(new BasicNameValuePair("status","Given on Hire"));


          //  String urlRouteList= "http://demo.ratnatechnology.co.in/farmerapp/api/farmer/take_on_hire"; Doubt
           String urlRouteList= Config.baseUrl+"farmer/take_on_hire";
            try {

                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);
                Log.v(" ", "Response is " + route_response);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);
                // status =jsonObject.getString("status");
                message = jsonObject.getString("message");

            }
            catch (Exception e)
            {
                Log.v("ONMESSAGE", e.toString());
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            pDialog.dismiss();
            if(message.equals("Insert Successfull")||message.equals("Update Successfull"))
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
               Intent i = new Intent(TakeOnHireDetail.this, Ask_Hire.class);
                startActivity(i);
                finish();


                 /* startActivity(new Intent(SignUpFarmer_2.this,Login.class));
                finish();*/
            }
            else {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
              /*  Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);

                snackbar.show();*/
            }

        }



    }
}
