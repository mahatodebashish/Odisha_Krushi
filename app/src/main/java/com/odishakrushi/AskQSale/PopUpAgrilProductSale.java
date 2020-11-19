package com.odishakrushi.AskQSale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.AskQPurchase.PopUpAgrilProductPurchase;
import com.odishakrushi.Config;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.Login;
import com.odishakrushi.R;

public class PopUpAgrilProductSale extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";

    TextView textagriprodname;
    String str_agrilprodname="";
    String str_sale_id="";

    EditText editprice, editquantity, editvariety,editsalestock;

    String streditprice="",streditquantity="",strvariety="",strsalestock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_pop_up_agril_product);

        WindowManager.LayoutParams params = getWindow().getAttributes();
     /*   params.x = 0;
        params.height = 1000;
        params.width = 700;
        params.y = 0;

        this.getWindow().setAttributes(params);*/
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.70);

        getWindow().setLayout(width, height);

        setFinishOnTouchOutside(false);// to prevent activity from dismissing on touching outside
        textagriprodname =findViewById(R.id.textagriprodname);
        editprice=findViewById(R.id.editprice);
        editquantity=findViewById(R.id.editquantity);
        editvariety=findViewById(R.id.editvariety);
        editsalestock=findViewById(R.id.editsalestock);

        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            str_agrilprodname = bundle.getString("PROD_NAME");
            str_sale_id = bundle.getString("SALE_ID"); // sale id required for editing product on sale items
            streditquantity=bundle.getString("QTY");
            strvariety=bundle.getString("VARIETY");
            streditprice=bundle.getString("PRICE");

            textagriprodname.setText(str_agrilprodname);
            editquantity.setText(streditquantity);
            editvariety.setText(strvariety);
            editprice.setText(streditprice);
        }

    }
    public void onClickOk(View view)
    {
       // Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
        streditprice=editprice.getText().toString();
        streditquantity=editquantity.getText().toString();
        strvariety=editvariety.getText().toString();
        strsalestock=editsalestock.getText().toString();
        if(streditprice.equals("")||streditquantity.equals("")||strvariety.equals(""))
        {
            Toast.makeText(this, getString(R.string.fieldempty), Toast.LENGTH_SHORT).show();
        }
        else {
            new AsyncAgrilProductSale().execute();
        }

    }

    private  class AsyncAgrilProductSale extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String  success = null,message="";

        @Override
        protected Void  doInBackground(Void... params) {
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",str_user_id));
            cred.add(new BasicNameValuePair("product_type",str_agrilprodname));
            cred.add(new BasicNameValuePair("quantity",streditquantity ));
            cred.add(new BasicNameValuePair("variety",strvariety ));
            cred.add(new BasicNameValuePair("price",streditprice ));
            cred.add(new BasicNameValuePair("category_name","AP" ));
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
            pDialog = new ProgressDialog(PopUpAgrilProductSale.this);
            pDialog.setMessage(getString(R.string.selling));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

    }

    /*public void getSalesData()
    {
        String url2="http://demo.ratnatechnology.co.in/farmerapp/api/farmer/getAgril_product_purchase_sale_by_userId_product_type?user_id="+str_user_id+"&product_type="+str_agrilprodname+"&category_name=Sale";

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String getresponse) {
                        //Toast.makeText(PopUpAgrilProductSale.this,getresponse,Toast.LENGTH_LONG).show();

                        try {


                            JSONObject jsonObject2=new JSONObject(getresponse);

                            JSONArray array2=jsonObject2.getJSONArray("data");

                            for(int j=0;j<array2.length();j++)
                            {
                                String product_type,quantity,variety,price,purchase_stock,sales_stock;

                                JSONObject obj = array2.getJSONObject(j);
                                product_type = obj.getString("product_type");
                                quantity = obj.getString("quantity");
                                variety = obj.getString("variety");
                                price = obj.getString("price");
                                purchase_stock = obj.getString("purchase_stock");
                                sales_stock = obj.getString("sales_stock");

                                editquantity.setText(quantity);
                                editvariety.setText(variety);
                                editprice.setText(price);
                                //editsalestock.setText(sales_stock);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(PopUpAgrilProductSale.this,"Error while loading data", Toast.LENGTH_SHORT).show();

                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest2);
    }*/
    public void onClickCancel(View view)
    {
        onBackPressed();
    }
}
