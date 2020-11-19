package com.odishakrushi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.odishakrushi.EditProfileActivity.EditProfileFarmer2;

public class PopUpMachineTool extends Activity {



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    } //Calligraphy

    TextView machineName;String strmname="";
    RadioGroup radioMachineQuality;
    EditText purchaseyear, make, model, remarks;
    Spinner content_spinner;
    int position=0;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";

    String str_yr_of_purchase,strmake,str_machine_condition,str_any_remark,str_machine_quality_old_new,strmodel;
    String strcategoryname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_pop_up_machine_tool);
        WindowManager.LayoutParams params = getWindow().getAttributes();
    /*    params.x = 0;
        params.height = 1000;
        params.width = 700;
        params.y = 0;

        this.getWindow().setAttributes(params);
*/
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);

        getWindow().setLayout(width, height);
        setFinishOnTouchOutside(false);// to prevent activity from dismissing on touching outside


        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);

        machineName=findViewById(R.id.machineName);
        radioMachineQuality=findViewById(R.id.radioMachineQuality);
        purchaseyear=findViewById(R.id.purchaseyear);
        make=findViewById(R.id.make);
        model=findViewById(R.id.model);
        remarks=findViewById(R.id.remarks);
        content_spinner=findViewById(R.id.content_spinner);



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            strmname = bundle.getString("MACHINE_NAME");
            strcategoryname=bundle.getString("CATEGORY_NAME");
            Log.d("strcategoryname",strcategoryname);
            machineName.setText(strmname);
        }



        radioMachineQuality.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_machine_quality_old_new=rb.getText().toString();
                    Toast.makeText(PopUpMachineTool.this, str_machine_quality_old_new, Toast.LENGTH_SHORT).show();


                }

            }
        });
        content_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_machine_condition= content_spinner.getSelectedItem().toString();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getData();
    }
    public void onClickOk(View view)
    {
        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        str_yr_of_purchase=purchaseyear.getText().toString().trim();
        strmake=make.getText().toString().trim();
        str_any_remark=remarks.getText().toString();
        strmodel=model.getText().toString();
        new AsyncSignUpMachineTool().execute();
    }
    private  class AsyncSignUpMachineTool extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String  success = null,message="";

        @Override
        protected Void  doInBackground(Void... params) {
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",str_user_id));
            cred.add(new BasicNameValuePair("machine_name",strmname));
            cred.add(new BasicNameValuePair("yr_of_purchase",str_yr_of_purchase));
            cred.add(new BasicNameValuePair("make",strmake));
            cred.add(new BasicNameValuePair("model",strmodel));
            cred.add(new BasicNameValuePair("category_name",strcategoryname));
            cred.add(new BasicNameValuePair("machine_condition",str_machine_condition));
            cred.add(new BasicNameValuePair("any_remark",str_any_remark));
            cred.add(new BasicNameValuePair("photograph",null));
            cred.add(new BasicNameValuePair("old_new",str_machine_quality_old_new));


//            String urlRouteList="http://demo.ratnatechnology.co.in/farmerapp/api/farmer/machine_tools"; Doubt
            String urlRouteList=Config.baseUrl+"farmer/machine_tools";
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
            if(message.equals("Insert Successfull")||message.equals("Update Successfull"))
            {
                Toast.makeText(getApplicationContext(),"Machine Tools Submitted",Toast.LENGTH_LONG).show();
               // startActivity(new Intent(PopUpMachineTool.this,Login.class));
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(PopUpMachineTool.this);
            pDialog.setMessage("Adding Machine tools...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

    }
    public void onClickCancel(View view)
    {
        finish();
    }

    public void getData()
    {
        //String url2="http://demo.ratnatechnology.co.in/farmerapp/api/farmer/getMachineToolsByUserIdMachineName?user_id="+str_user_id+"&machine_name="+strmname; Doubt
        String url2=Config.baseUrl+"farmer/getMachineToolsByUserIdMachineName?user_id="+str_user_id+"&machine_name="+strmname;
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String getresponse) {
                       // Toast.makeText(PopUpMachineTool.this,getresponse,Toast.LENGTH_LONG).show();

                        try {

                            //Log.d( "getMachineDetail: ",getresponse);


                            JSONObject jsonObject2=new JSONObject(getresponse);

                            JSONArray array2=jsonObject2.getJSONArray("data");

                            for(int j=0;j<array2.length();j++)
                            {
                                String get_yr_of_purchase,get_make,get_model,get_machine_condition,
                                        get_any_remark,get_photograph,get_old_new;

                                JSONObject obj = array2.getJSONObject(j);
                                get_yr_of_purchase = obj.getString("yr_of_purchase");
                                purchaseyear.setText(get_yr_of_purchase);
                                get_make = obj.getString("make");
                                make.setText(get_make);
                              get_model = obj.getString("model");
                              model.setText(get_model);

                               get_machine_condition = obj.getString("machine_condition");
                               //spinner get
                                if(get_machine_condition.equals("Very Good"))
                                    position=0;
                                else if(get_machine_condition.equals("Good"))
                                    position=1;
                                else if(get_machine_condition.equals("Needs Repair"))
                                    position=2;
                                else if(get_machine_condition.equals("Scrap"))
                                    position=2;

                                content_spinner.setSelection(position);

                                    get_any_remark = obj.getString("any_remark");
                                    remarks.setText(get_any_remark);

                                    get_photograph = obj.getString("photograph");

                                    get_old_new = obj.getString("old_new");

                                if(get_old_new.equals("Old"))
                                    radioMachineQuality.check(R.id.idold);
                                else if(get_old_new.equals("New"))
                                    radioMachineQuality.check(R.id.idnew);
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
                        Toast.makeText(PopUpMachineTool.this,"Error while loading data", Toast.LENGTH_SHORT).show();

                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest2);
    }
}
