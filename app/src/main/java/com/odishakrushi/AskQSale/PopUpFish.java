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
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.Config;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.R;

public class PopUpFish extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    TextView textfishprodname;
    String   str_sale_id="",str_title="",streditprice="",streditquantity="",strvariety="";
    EditText editprice,editquantity,editvariety;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_pop_up_fish);
        WindowManager.LayoutParams params = getWindow().getAttributes();

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.70);

        getWindow().setLayout(width, height);

        setFinishOnTouchOutside(false);// to prevent activity from dismissing on touching outside

        //getting references from xml
        textfishprodname=findViewById(R.id.textfishprodname);
        textfishprodname =findViewById(R.id.textfishprodname);
        editprice=findViewById(R.id.editprice);
        editquantity=findViewById(R.id.editquantity);
        editvariety=findViewById(R.id.editvariety);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            str_title = bundle.getString("PROD_NAME"); textfishprodname.setText(str_title);
            str_sale_id= bundle.getString("SALE_ID");
            streditquantity= bundle.getString("QTY"); editquantity.setText(streditquantity);
            strvariety= bundle.getString("VARIETY"); editvariety.setText(strvariety);
            streditprice= bundle.getString("PRICE"); editprice.setText(streditprice);

        }


        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);


    }

    public void onClickOk(View view)
    {
        // Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
        streditprice=editprice.getText().toString();
        streditquantity=editquantity.getText().toString();
        strvariety=editvariety.getText().toString();

        if(streditprice.equals("")||streditquantity.equals("")||strvariety.equals(""))
        {
            Toast.makeText(this, getString(R.string.fieldempty), Toast.LENGTH_SHORT).show();
        }
        else {
            new AsyncFishProductSale().execute();
        }

    }

    private  class AsyncFishProductSale extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String  success = null,message="";

        @Override
        protected Void  doInBackground(Void... params) {
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",str_user_id));
            cred.add(new BasicNameValuePair("product_type","Fish"));
            cred.add(new BasicNameValuePair("quantity",streditquantity ));
            cred.add(new BasicNameValuePair("variety",strvariety ));
            cred.add(new BasicNameValuePair("price",streditprice ));
            cred.add(new BasicNameValuePair("category_name","Fishery" ));
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
            pDialog = new ProgressDialog(PopUpFish.this);
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
