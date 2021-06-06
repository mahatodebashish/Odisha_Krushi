package com.odishakrushi.AskQPurchase;

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
import com.odishakrushi.Config;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.Login;
import com.odishakrushi.R;

public class PopUpPurchaseAnimal extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    TextView animalName;
    String str_animalname="";

    String str_farm_name,str_website,str_mobile,str_pass,str_email,str_propertier_name,str_district_id,str_block_id,
            str_area_of_business,strbusinesstype,str_deals_in_product;

    EditText eqty,ebreed,efeed,emedicine,eothers;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";

    String str_animal_type,str_category_name,str_quantity,
            str_breed,str_feed,str_medicine,str_others;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_pop_up_purchase_animal);
        WindowManager.LayoutParams params = getWindow().getAttributes();

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.70);

        getWindow().setLayout(width, height);

        setFinishOnTouchOutside(false);// to prevent activity from dismissing on touching outside
        animalName =findViewById(R.id.animalName);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            str_animalname = bundle.getString("ANIMAL");
            str_category_name=bundle.getString("CATEGORY_NAME");
            animalName.setText(str_animalname);


        }
        eqty=findViewById(R.id.qty);
        ebreed=findViewById(R.id.breed);
        efeed=findViewById(R.id.feed);
        emedicine=findViewById(R.id.medicine);
        eothers=findViewById(R.id.others);

        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);

        getAnimalDetails();
    }


    public void getAnimalDetails()
    {
       // String url2="http://demo.ratnatechnology.co.in/farmerapp/api/farmer/getveterinary_item_by_userId_animal_type?user_id="+str_user_id+"&animal_type="+str_animalname+"&category_name=Purchase"; Doubt
        String url2=Config.baseUrl+"farmer/getveterinary_item_by_userId_animal_type?user_id="+str_user_id+"&animal_type="+str_animalname+"&category_name=Purchase";
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String getresponse) {
                        // Toast.makeText(PopUpAgrilProductPurchase.this,getresponse,Toast.LENGTH_LONG).show();

                        try {


                            JSONObject jsonObject2=new JSONObject(getresponse);

                            JSONArray array2=jsonObject2.getJSONArray("data");

                            for(int j=0;j<array2.length();j++)
                            {
                                String quantity,breed,feed,medicine,others;

                                JSONObject obj = array2.getJSONObject(j);
                                quantity = obj.getString("quantity");
                                breed = obj.getString("breed");
                                feed = obj.getString("feed");
                                medicine = obj.getString("medicine");
                                others = obj.getString("others");

                                eqty.setText(quantity);
                                ebreed.setText(breed);
                                efeed.setText(feed);
                                emedicine.setText(medicine);
                                eothers.setText(others);
                                //  editpurchasestock.setText(purchase_stock);


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
                        Toast.makeText(PopUpPurchaseAnimal.this,"Error while loading data", Toast.LENGTH_SHORT).show();

                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest2);
    }
    public void onClickOk(View view)
    {
       // Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
        str_quantity=eqty.getText().toString();
        str_breed=ebreed.getText().toString();
        str_feed=efeed.getText().toString();
        str_medicine=emedicine.getText().toString();
        str_others=eothers.getText().toString();
        new AsyncPurchaseAnimal().execute();

    }
    public void onClickCancel(View view)
    {
        finish();
    }

    private class AsyncPurchaseAnimal extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String  success = null,message="";

        @Override
        protected void onPreExecute() {

            pDialog = new ProgressDialog(PopUpPurchaseAnimal.this);
            pDialog.setMessage("Buying...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected Void  doInBackground(Void... params) {



            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();

            cred.add(new BasicNameValuePair("user_id",str_user_id));
            cred.add(new BasicNameValuePair("animal_type",str_animalname));
            cred.add(new BasicNameValuePair("category_name","Purchase"));
            cred.add(new BasicNameValuePair("quantity",str_quantity));
            cred.add(new BasicNameValuePair("breed",str_breed));
            cred.add(new BasicNameValuePair("feed",str_feed));
            cred.add(new BasicNameValuePair("medicine",str_medicine));
            cred.add(new BasicNameValuePair("others",str_others));


          //  String urlRouteList= "http://demo.ratnatechnology.co.in/farmerapp/api/farmer/veterinary_items"; Doubt
            String urlRouteList= Config.baseUrl+"farmer/veterinary_items";
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
                Intent i = new Intent(PopUpPurchaseAnimal.this, Login.class);
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
