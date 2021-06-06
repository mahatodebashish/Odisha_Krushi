package com.odishakrushi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.Browser;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.pixplicity.easyprefs.library.Prefs;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.ProfilePic.ProfilePic;

import static com.odishakrushi.Config.ODIA_DISTRICTS;

public class SignUpGuest extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    } //Calligraphy

    String str_user_id="";
    int user_id;
    CheckBox termcondition;
    Button register;

    TextView readTermCondition;

  /* Doubt
    private static final String URL_DATA = "http://demo.ratnatechnology.co.in/farmerapp/api/commons/districts/29";
    private static final String URL_DATA2 = "http://demo.ratnatechnology.co.in/farmerapp/api/commons/blocks?district_id=";
    private static final String URL_DATA3 = "http://demo.ratnatechnology.co.in/farmerapp/api/commons/gps?block_id=";*/

    private static final String URL_DATA = Config.baseUrl+"commons/districts/29";
    private static final String URL_DATA2 =Config.baseUrl+"commons/blocks?district_id=";
    private static final String URL_DATA3 =Config.baseUrl+"commons/gps?block_id=";

    AppCompatEditText fname,input_mob_no,input_mail,input_pass;
    RadioGroup radioGender;
    Spinner district,block;
    String str_fname="",str_lname="",str_mob="",str_mail="",str_pass="",str_gender="",str_district,str_block="";
    String str_district_id="",str_block_id="";
    ArrayList<String> arraylist_districtid=new ArrayList<String>();
    ArrayList<String> arraylist_blockid;
    ArrayList<String> arraylist_gp_id;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_sign_up_guest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


        register=findViewById(R.id.register);
        termcondition=findViewById(R.id.termcondition);

        readTermCondition=findViewById(R.id.readTermCondition);
        readTermCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://drive.google.com/open?id=1J1B7rCG1zME91dBTpRexHBm_Me1tPoeS");
                Context context = getApplicationContext();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
                try {
                    startActivity(intent);
                }catch (Exception e){}

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerFunc();
            }
        });

        termcondition.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(termcondition.isChecked())
                {
                    register.setEnabled(true);
                    register.setFocusable(true);
                    register.setClickable(true);
                    register.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }else
                {
                    register.setEnabled(false);
                    register.setFocusable(false);
                    register.setClickable(false);
                    register.setBackgroundColor(getResources().getColor(R.color.ColorName));
                }
            }
        });
        fname=findViewById(R.id.fname);
        input_mob_no=findViewById(R.id.input_mob_no);
        input_mail=findViewById(R.id.input_mail);
        input_pass=findViewById(R.id.input_pass);
        radioGender=findViewById(R.id.radioGender);
        district=findViewById(R.id.district);
        block=findViewById(R.id.block);

        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                  //  str_gender=rb.getText().toString();

                    int selectedId = radioGender.getCheckedRadioButtonId();
                    str_gender=String.valueOf(selectedId);
                    Toast.makeText(SignUpGuest.this, str_gender, Toast.LENGTH_SHORT).show();
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

    public void registerFunc()
    {
        str_fname=fname.getText().toString().trim();
        str_mob=input_mob_no.getText().toString().trim();
        str_mail=input_mail.getText().toString().trim();
        str_pass=input_pass.getText().toString().trim();

        if(str_fname.equals("")||str_mob.equals("")||str_pass.equals(""))
        {

           /* Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "One or More Field(s) Blank", Snackbar.LENGTH_LONG);

            snackbar.show();*/

          /*  Alerter.create(SignUpGuest.this)
                    .setTitle(getString(R.string.blanks))
                    .show();*/

            Toast.makeText(this, getString(R.string.blanks), Toast.LENGTH_SHORT).show();
        }
        else if(!(isValidPhone(str_mob)))
        {
        /*    Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Phone number is Invalid", Snackbar.LENGTH_LONG);

            snackbar.show();*/
            /*Alerter.create(SignUpGuest.this)
                    .setTitle(getString(R.string.invalidphone))
                    .show();*/

            Toast.makeText(this, getString(R.string.invalidphone), Toast.LENGTH_SHORT).show();
        }
        else if(str_pass.length()<6&&str_pass.length()>8)
        {
            Toast.makeText(this, getString(R.string.pass_length), Toast.LENGTH_SHORT).show();
            /*Alerter.create(SignUpGuest.this)
                    .setTitle(getString(R.string.pass_length))
                    .show();*/
        }
        else
        {
          //  new AsyncSignUpGuest().execute();

            signUp();
        }

    }


    public static boolean isValidPhone(String phone)
    {
        String expression = "^([0-9\\+]|\\(\\d{1,3}\\))[0-9\\-\\. ]{3,15}$";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);

        char ch=phone.charAt(0);
        int flag=0;
        if (ch=='9'||ch=='8'||ch=='7')
        {
            if (matcher.matches())
            {
                flag=1;
                //return true;
            }
        }

        else{
            flag=0;
            // return false;
        }
        if (flag==1)
        {
            return true;
        }
        else
        {
            return false;
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
                                    SignUpGuest.this,R.layout.spinner_item,districtList);

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
             Toast.makeText(getApplicationContext(),getString(R.string.Network_Error),Toast.LENGTH_LONG).show();
                /*Alerter.create(SignUpGuest.this)
                        .setTitle(getString(R.string.Network_Error))
                        .show();*/

                onBackPressed();
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
                                    SignUpGuest.this,R.layout.spinner_item_2,blockList);

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
               Toast.makeText(getApplicationContext(),getString(R.string.Network_Error),Toast.LENGTH_LONG).show();
            /*    Alerter.create(SignUpGuest.this)
                        .setTitle(getString(R.string.Network_Error))
                        .show();*/

            onBackPressed();

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

                onBackPressed();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private  class AsyncSignUpGuest extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String  success = null,message="",errors="";
        boolean status;

        @Override
        protected Void  doInBackground(Void... params) {
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("first_name",str_fname));
            cred.add(new BasicNameValuePair("email",str_mail ));
            cred.add(new BasicNameValuePair("password",str_pass ));
            cred.add(new BasicNameValuePair("phone",str_mob ));
            if(str_gender.equals(""))
            {
                Toast.makeText(SignUpGuest.this, "Select Gender", Toast.LENGTH_SHORT).show();
            }
            else if(str_gender.equals("Male"))
            {
                cred.add(new BasicNameValuePair("gender", "1"));
            }
            else if(str_gender.equals("Female"))
            {
                cred.add(new BasicNameValuePair("gender", "2"));
            }
            cred.add(new BasicNameValuePair("district", str_district_id));
            cred.add(new BasicNameValuePair("block", str_block_id));
            //
          //  String urlRouteList="http://demo.ratnatechnology.co.in/farmerapp/api/user/signup_Guest"; Doubt
          String urlRouteList=Config.baseUrl+"user/signup_Guest";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);
                Log.v(" ", "Response is " + route_response);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);
                // status =jsonObject.getString("status");

                status=jsonObject.getBoolean("status");

                if(status)
                {
                    message = jsonObject.getString("message");
                    user_id=jsonObject.getInt("user_id");
                   // Alerter.create(SignUpGuest.this).setTitle(message);
                    Toast.makeText(SignUpGuest.this, message, Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {


                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            Intent i = new Intent(SignUpGuest.this, ProfilePic.class);
                            i.putExtra("USER_ID",String.valueOf(user_id));
                            startActivity(i);

                        }
                    }, 3);
                }

                else
                {
                    errors = jsonObject.getString("errors");
                   // Alerter.create(SignUpGuest.this).setTitle(message);
                    Toast.makeText(SignUpGuest.this, message, Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }


            } catch (Exception e)

            {
                Log.v("ONMESSAGE", e.toString());

            }return null;
        }

        protected void onPostExecute(Void result) {
            pDialog.dismiss();





        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(SignUpGuest.this);
            pDialog.setMessage("Registering...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

    }


    private void signUp() {

        progressDialog=new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.pleasewait));

       // AndroidNetworking.post("http://demo.ratnatechnology.co.in/farmerapp/api/user/signup_Guest") Doubt
        AndroidNetworking.post(Config.baseUrl+"user/signup_Guest")
                .addBodyParameter("first_name",str_fname)
                .addBodyParameter("email",str_mail )
                .addBodyParameter("password",str_pass )
                .addBodyParameter("phone",str_mob )
                .addBodyParameter("gender", str_gender)
                .addBodyParameter("district", str_district_id)
                .addBodyParameter("block", str_block_id)
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.hide();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean status=jsonObject.getBoolean("status");

                            if (status)
                            {
                                int user_id=jsonObject.getInt("user_id");
                                str_user_id=String.valueOf(user_id);
                                String message=jsonObject.getString("message");
                               /* Alerter.create(SignUpGuest.this).setTitle(message);*/
                                Toast.makeText(SignUpGuest.this, message, Toast.LENGTH_SHORT).show();

                                Intent intent =new Intent (SignUpGuest.this, Login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                        /*        new Handler().postDelayed(new Runnable() {



                                    @Override      public void run() {
                                        // This method will be executed once the timer is over
                                        Intent i = new Intent(SignUpGuest.this, ProfilePic.class);//
                                        i.putExtra("USER_ID",str_user_id);
                                        startActivity(i);
                                        finish();
                                    }
                                }, 3);*/
                            }
                            else
                                if(!status)
                                {
                                    String message=jsonObject.getString("message");
                                    String errors=jsonObject.getString("errors");
                                    //Alerter.create(SignUpGuest.this).setTitle(message+"\n"+errors);
                                    Toast.makeText(SignUpGuest.this, message+"\n"+errors, Toast.LENGTH_SHORT).show();
                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        progressDialog.hide();
                        // handle error
                       // Alerter.create(SignUpGuest.this).setTitle(getString(R.string.Network_Error));
                        Toast.makeText(SignUpGuest.this, getString(R.string.Network_Error), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
    }
}
