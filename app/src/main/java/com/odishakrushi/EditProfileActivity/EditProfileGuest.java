package com.odishakrushi.EditProfileActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import com.odishakrushi.Config;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.Login;
import com.odishakrushi.R;
import com.odishakrushi.SignUpGuest;
import com.pixplicity.easyprefs.library.Prefs;

import static com.odishakrushi.Config.ODIA_DISTRICTS;

public class EditProfileGuest extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

/*    Doubt
    private static final String URL_DATA = "http://demo.ratnatechnology.co.in/farmerapp/api/commons/districts/29";
    private static final String URL_DATA2 = "http://demo.ratnatechnology.co.in/farmerapp/api/commons/blocks?district_id=";
    private static final String URL_DATA3 = "http://demo.ratnatechnology.co.in/farmerapp/api/commons/gps?block_id=";*/

    private static final String URL_DATA = Config.baseUrl+"commons/districts/29";
    private static final String URL_DATA2 =  Config.baseUrl+"commons/blocks?district_id=";
    private static final String URL_DATA3 =  Config.baseUrl+"commons/gps?block_id=";

    AppCompatEditText fname,lname,input_mob_no,input_mail,input_pass;
    RadioGroup radioGender;
    Spinner district,block;
    String str_fname="",str_lname="",str_mob="",str_mail="",str_pass="",str_gender="",str_district,str_block="";
    String str_district_id="",str_block_id="";
    ArrayList<String> arraylist_districtid=new ArrayList<String>();
    ArrayList<String> arraylist_blockid;
    ArrayList<String> arraylist_gp_id;

    String email_str="",first_name_str="",phone_str="",gender_str="",district_str="",block_str="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_edit_profile_guest);

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



        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        input_mob_no=findViewById(R.id.input_mob_no);
        input_mail=findViewById(R.id.input_mail);
        input_pass=findViewById(R.id.input_pass);
        radioGender=findViewById(R.id.radioGender);
        district=findViewById(R.id.district);
        block=findViewById(R.id.block);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            first_name_str = bundle.getString("FIRST_NAME");
            fname.setText(first_name_str);

            phone_str = bundle.getString("PHONE");
            input_mob_no.setText(phone_str);

            email_str = bundle.getString("EMAIL");
            input_mail.setText(email_str);

            gender_str = bundle.getString("GENDER");
            if(gender_str.equals(getString(R.string.Male)))
                radioGender.check(R.id.rmale);
            else
                radioGender.check(R.id.rfemale);

            district_str = bundle.getString("DISTRICT");
            block_str = bundle.getString("BLOCK");
        }

        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_gender=rb.getText().toString();
                    Toast.makeText(EditProfileGuest.this, str_gender, Toast.LENGTH_SHORT).show();


                }

            }
        });

        load_district_in_spinner();
        // SPINNER SELECT

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_district= district.getSelectedItem().toString();
                str_district_id= String.valueOf(position+1);


                //Toast.makeText(SignUpFarmer_2.this,"CLICKED AND PASSED districtid " +str_district_id , Toast.LENGTH_SHORT).show();
                load_block_from_a_district(str_district_id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_block= block.getSelectedItem().toString();
                //str_block_id= String.valueOf(position+1);

                // TASK IS TO GET THE BLOCK_ID FROM DISTRICT_ID ..?
                str_block_id=arraylist_blockid.get(position);
                load_gps_from_blockid(str_block_id);//str_block_id
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    public void onClickUpdate(View view)
    {
        str_fname=fname.getText().toString().trim();
        str_mob=input_mob_no.getText().toString().trim();
        str_mail=input_mail.getText().toString().trim();
        str_pass=input_pass.getText().toString().trim();

        if(str_fname.equals("")||str_mob.equals("")||str_mail.equals(""))
        {

            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "One or More Field(s) Blank", Snackbar.LENGTH_LONG);

            snackbar.show();
        }

        else
        {
            new EditProfileGuestUpdate().execute();
        }

    }

    private void load_district_in_spinner() {

        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                      /*  progressDialog.dismiss();*/
                        try {
                            // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                            Log.d("onResponse:",s);

                            //code to check what  is the language selected , if odia then load odia districts in spinner
                            String langdata = Prefs.getString("language", "");
                            if(langdata.equals("or"))
                                s=ODIA_DISTRICTS;

                            //Initialize arraylist
                            ArrayList<String> dl=new ArrayList<String>();


                            JSONObject jsonObject=new JSONObject(s);
                            JSONArray array=jsonObject.getJSONArray("districts");

                            for(int i=0;i<array.length();i++)
                            {
                                String did="",dname="",sid="";

                                JSONObject o=array.getJSONObject(i);
                                did=o.getString("id");
                                dname =o.getString("name");
                                sid =o.getString("state_id");

                                dl.add(dname);
                                arraylist_districtid.add(did);

                            }

                            final List<String> districtList = new ArrayList<>(dl);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    EditProfileGuest.this,R.layout.spinner_item,districtList);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            district.setAdapter(spinnerArrayAdapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               /* progressDialog.dismiss();*/
                // Toast.makeText(getApplicationContext(),"Error while loading",Toast.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), "Error while loading", Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void load_block_from_a_district(String districtid)
    {
        //code to check what  is the language selected , if odia then load odia districts in spinner
        String langdata = Prefs.getString("language", "");
        String isOdia="";
        if(langdata.equals("or"))
            isOdia="&odia=1";

        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                URL_DATA2+districtid+isOdia,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                      /*  progressDialog.dismiss();*/
                        try {
                            // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                            Log.d("onResponse:",s);
                            JSONObject jsonObject=null;
                            JSONArray array=null;

                            //Initialize arraylist
                            ArrayList<String> al=new ArrayList<String>();
                            arraylist_blockid =new ArrayList<String>();
                            jsonObject=new JSONObject(s);
                            array=jsonObject.getJSONArray("blocks");

                            // Toast.makeText(getApplicationContext(),String.valueOf(array.length()),Toast.LENGTH_LONG).show();



                            for(int k=0;k<array.length();k++)
                            {
                                String bid="",name="",district_id="";

                                JSONObject o=array.getJSONObject(k);
                                bid=o.getString("district_id");
                                name =o.getString("name");
                                district_id =o.getString("district_id");


                                al.add(name);
                                arraylist_blockid.add(bid);

                            }
                         /*   Toast.makeText(SignUpFarmer_2.this, "arraylist_blockid", Toast.LENGTH_SHORT).show();

                            for (int x=0;x<array.length();x++)
                            {
                                Toast.makeText(SignUpFarmer_2.this, arraylist_blockid.get(x), Toast.LENGTH_SHORT).show();
                            }*/

                            //  final List<String> blockList = new ArrayList<>(Arrays.asList(strarrayblocks));
                            final List<String> blockList = new ArrayList<>(al);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                                    EditProfileGuest.this,R.layout.spinner_item_2,blockList);

                            spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_item_2);
                            block.setAdapter(spinnerArrayAdapter2);



                        } catch (JSONException e) {//JSONException
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               /* progressDialog.dismiss();*/
                //  Toast.makeText(getApplicationContext(),"Error while loading",Toast.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), "Error while loading", Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void load_gps_from_blockid(String str_block_id)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                URL_DATA3+str_block_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                      /*  progressDialog.dismiss();*/
                        try {
                            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                            Log.d("onResponse:",s);
                            JSONObject jsonObject=null;
                            JSONArray array=null;

                            //Initialize arraylist
                            ArrayList<String> gpl=new ArrayList<String>();
                            arraylist_gp_id =new ArrayList<String>();

                            jsonObject=new JSONObject(s);
                            array=jsonObject.getJSONArray("gps");

                            // Toast.makeText(getApplicationContext(),String.valueOf(array.length()),Toast.LENGTH_LONG).show();



                            for(int k=0;k<array.length();k++)
                            {
                                String gid="",name="",b_id="";

                                JSONObject o=array.getJSONObject(k);
                                gid=o.getString("id");
                                name =o.getString("name");
                                b_id =o.getString("block_id");


                                gpl.add(name);
                                arraylist_gp_id.add(gid);
                            }



                        } catch (JSONException e) {//JSONException
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private  class EditProfileGuestUpdate extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String  success = null,message="";

        @Override
        protected Void  doInBackground(Void... params) {
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            /*cred.add(new BasicNameValuePair("first_name",str_fname));
            cred.add(new BasicNameValuePair("email",str_mail ));
            cred.add(new BasicNameValuePair("phone",str_mob ));
            if(str_gender.equals(""))
            {
                Toast.makeText(EditProfileGuest.this, "Select Gender", Toast.LENGTH_SHORT).show();
            }
            else if(str_gender.equals(getString(R.string.Male)))
            {
                cred.add(new BasicNameValuePair("gender", "1"));
            }
            else if(str_gender.equals(getString(R.string.Female)))
            {
                cred.add(new BasicNameValuePair("gender", "2"));
            }*/
            cred.add(new BasicNameValuePair("district", str_district_id));
            cred.add(new BasicNameValuePair("block", str_block_id));
            //
            //String urlRouteList="http://demo.ratnatechnology.co.in/farmerapp/api/user/signup_Guest"; Doubt
            String urlRouteList=Config.baseUrl+"user/signup_Guest";
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
            if(message.equals("Registration Successfull"))
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                startActivity(new Intent(EditProfileGuest.this,Login.class));
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(EditProfileGuest.this);
            pDialog.setMessage("Updating...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

    }

}
