package com.odishakrushi.AskQPurchase;

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
import com.odishakrushi.AskQSale.PopUpAgrilProductSale;
import com.odishakrushi.Ask_Hire;
import com.odishakrushi.Ask_Purchase;
import com.odishakrushi.Config;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.Login;
import com.odishakrushi.R;

public class ViewFishDetail extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    String fish_id,fish_type,category_name,user_id,medicine,feed,net,others;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";

    TextView txt_fish_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fish_detail);

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

        txt_fish_type=findViewById(R.id.txt_fish_type);

        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {

            fish_id = bundle.getString("FISH_ID");
            fish_type=bundle.getString("FISH_TYPE");
            txt_fish_type.setText(fish_type);
            category_name = bundle.getString("CATEGORY_NAME");
            user_id = bundle.getString("USER_ID" );
            medicine = bundle.getString("MEDICINE" );
            feed  = bundle.getString("FEED");
            net  = bundle.getString("NET" );
            others  = bundle.getString("OTHERS" );
        }
    }

    public void onClickBuy(View view)
    {
        new AsyncBuyFish().execute();
    }
    private  class AsyncBuyFish extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String  success = null,message="";

        @Override
        protected Void  doInBackground(Void... params) {
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",str_user_id));
            cred.add(new BasicNameValuePair("fish_type",fish_type));
            cred.add(new BasicNameValuePair("medicine",medicine));
            cred.add(new BasicNameValuePair("feed",feed));
            cred.add(new BasicNameValuePair("net",net));
            cred.add(new BasicNameValuePair("category_name","Purchased"));
            cred.add(new BasicNameValuePair("others",others));
            cred.add(new BasicNameValuePair("fish_id",fish_id));
            //
           // String urlRouteList="http://demo.ratnatechnology.co.in/farmerapp/api/farmer/fish_purchase_sales"; Doubt
           String urlRouteList= Config.baseUrl+"farmer/fish_purchase_sales";

            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);
                Log.v(" ", "Response is " + route_response);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);
                // status =jsonObject.getString("status");
                message = jsonObject.getString("message");

            } catch (Exception e)

            {
                Log.v("ONMESSAGE", e.toString());

            }return null;
        }

        protected void onPostExecute(Void result) {
            pDialog.dismiss();
            if(message.equals("Records found")||message.equals("Insert successful")||message.equals("Update Successful"))
            {
                Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_LONG).show();
                startActivity(new Intent(ViewFishDetail.this,Ask_Purchase.class));
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                finish();
            }

        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(ViewFishDetail.this);
            pDialog.setMessage("Purchasing...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

    }
}
