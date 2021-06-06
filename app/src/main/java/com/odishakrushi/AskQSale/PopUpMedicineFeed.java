package com.odishakrushi.AskQSale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.Config;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.R;

public class PopUpMedicineFeed extends Activity {

    TextView textmedicinefeed;
    EditText editmedicine,editmedicinefor,editfeed,editnet;

    String streditmedicine,streditmedicinefor,streditfeed,streditnet;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";

    String str_medicine="",str_sale_id="",str_medicinefor="",str_feed="",str_net="";
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_pop_up_medicine_feed);

        WindowManager.LayoutParams params = getWindow().getAttributes();

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.70);

        getWindow().setLayout(width, height);

        setFinishOnTouchOutside(false);// to prevent activity from dismissing on touching outside

        textmedicinefeed=findViewById(R.id.textmedicinefeed);
        editmedicine=findViewById(R.id.editmedicine);
        editmedicinefor=findViewById(R.id.editmedicinefor);
        editfeed=findViewById(R.id.editfeed);
        editnet=findViewById(R.id.editnet);

        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            str_medicine = bundle.getString("MEDICINE");
            str_sale_id = bundle.getString("SALE_ID"); // sale id required for editing product on sale items
            str_medicinefor=bundle.getString("MEDICINE_FOR");
            str_feed=bundle.getString("FEED");
            str_net=bundle.getString("NET");

            editmedicine.setText(str_medicine);
            editmedicinefor.setText(str_medicinefor);
            editfeed.setText(str_feed);
            editnet.setText(str_net);
        }


    }
    public void onClickOk(View view)
    {
        // Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
        streditmedicine=editmedicine.getText().toString();
        streditmedicinefor=editmedicinefor.getText().toString();
        streditfeed=editfeed.getText().toString();
        streditnet=editnet.getText().toString();


        new AsyncMedicineFeedSale().execute();

    }
    private  class AsyncMedicineFeedSale extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String  success = null,message="";

        @Override
        protected Void  doInBackground(Void... params) {
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",str_user_id));
            cred.add(new BasicNameValuePair("product_type","MedicineFeed"));
            cred.add(new BasicNameValuePair("medicine",streditmedicine ));
            cred.add(new BasicNameValuePair("medicine_for",streditmedicinefor ));
            cred.add(new BasicNameValuePair("feed",streditfeed ));
            cred.add(new BasicNameValuePair("category_name","MF" ));
            cred.add(new BasicNameValuePair("net",streditnet ));
            cred.add(new BasicNameValuePair("sale_id",str_sale_id ));

            //
            String urlRouteList= Config.farmersales;
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
                //  Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_LONG).show();
               /* startActivity(new Intent(PopUpAgrilProductSale.this,SaleList.class));
                finish();*/
                onBackPressed();
            }
            else {
                //Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
              /*  finish();*/
                onBackPressed();
            }

        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(PopUpMedicineFeed.this);
            pDialog.setMessage(getString(R.string.selling));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

    }
    public void onClickCancel(View view)
    {
        onBackPressed();
    }
}
