package com.odishakrushi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Browser;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.odishakrushi.utils.Utils;
import com.odishakrushi.utils.multiselectspinner.MultiSpinner;
import com.pixplicity.easyprefs.library.Prefs;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static android.R.layout.simple_spinner_item;
import static com.odishakrushi.Config.ODIA_DISTRICTS;

public class SignUpBusiness extends AppCompatActivity implements MultiSpinner.MultiSpinnerListener {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    } //Calligraphy

    CheckBox termcondition;
    Button register;

    TextView readTermCondition;

    AppCompatEditText name_of_farm,properiter_name,input_mob_no,input_mail,idwebsite,input_pass;
    Spinner district,block,business_type,dealsIn;
    String str_name_of_farm,strwebsite,strmobile,stremail,strpass;
    ProgressDialog progressDialog;

    String str_district_id="",str_block_id="";
    String str_business_type="",str_prefered_area_of_business="",str_dealsIn="";
    ArrayList<String> arraylist_districtid=new ArrayList<String>();
    ArrayList<String> arraylist_blockid;
    private static final String URL_DATA = Config.baseUrl+"commons/districts/29";
    private static final String URL_DATA2 = Config.baseUrl+"commons/blocks?district_id=";
    private static final String URL_DATA3 =  Config.baseUrl+"commons/gps?block_id=";


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id="";

    String isEditProfile="0";
    LinearLayout layoutLocationOfFarm,layoutDistrictBlock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_sign_up_business);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        //######################################
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
        //#########################

        //###########################################
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



        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        user_id=sharedpreferences.getString("FLAG", "");
        Log.d("user_id:",user_id);
        editor.commit(); // commit changes

        //#######################################


        name_of_farm=findViewById(R.id.name_of_farm);
        properiter_name=findViewById(R.id.properiter_name);
        input_mob_no=findViewById(R.id.input_mob_no);
        input_pass=findViewById(R.id.input_pass);
        input_mail=findViewById(R.id.input_mail);
        idwebsite=findViewById(R.id.idwebsite);

        business_type=findViewById(R.id.business_type);
        district=findViewById(R.id.district);
        block=findViewById(R.id.block);
        business_type=findViewById(R.id.business_type);
        dealsIn=findViewById(R.id.dealsIn);

        layoutLocationOfFarm=findViewById(R.id.layoutLocationOfFarm);
        layoutDistrictBlock=findViewById(R.id.layoutDistrictBlock);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            isEditProfile= bundle.getString("EDIT_PROFILE");
            name_of_farm.setText(bundle.getString("NAME_OF_FARM")); name_of_farm.setFocusable(false); name_of_farm.setVisibility(View.GONE);
            properiter_name.setText(bundle.getString("NAME_OF_PROPERTIER")); properiter_name.setFocusable(false); properiter_name.setVisibility(View.GONE);
            input_mob_no.setText(bundle.getString("PHONE")); input_mob_no.setFocusable(false); input_mob_no.setVisibility(View.GONE);
            input_mail.setText(bundle.getString("EMAIL")); input_mail.setFocusable(false); input_mail.setVisibility(View.GONE);
            idwebsite.setText(bundle.getString("WEBSITE")); idwebsite.setFocusable(false); idwebsite.setVisibility(View.GONE);

            toolbar.setTitle("Edit Profile");
        }

        if(isEditProfile.equals("1")){
            //then hide the terms and condition views
            termcondition.setVisibility(View.INVISIBLE);
            readTermCondition.setVisibility(View.INVISIBLE);
            district.setEnabled(false); layoutLocationOfFarm.setVisibility(View.GONE);
            block.setEnabled(false); layoutDistrictBlock.setVisibility(View.GONE);
            input_pass.setVisibility(View.GONE);


            register.setEnabled(true);
            register.setFocusable(true);
            register.setClickable(true);
            register.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        /*prefered area of business*/

        //String[] districtArray = getResources().getStringArray(R.array.array_district);
        List<String> districtList = Arrays.asList(getResources().getStringArray(R.array.array_district));

        /*ArrayList<String> machines=new ArrayList<>();
        machines.add("rotavator");
        machines.add("rotavator");
        machines.add("rotavator");
        machines.add("rotavator");*/

        MultiSpinner multiSpinner = (MultiSpinner) findViewById(R.id.multi_spinner);
        multiSpinner.setItems(districtList, getString(R.string.Select), this);

        load_district_in_spinner();

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // strdistrict= district.getSelectedItem().toString();
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
               // strblock= block.getSelectedItem().toString();
                //str_block_id= String.valueOf(position+1);

                // TASK IS TO GET THE BLOCK_ID FROM DISTRICT_ID ..?
                str_block_id=arraylist_blockid.get(position);
               // load_gps_from_blockid(str_block_id);//str_block_id
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        business_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 str_business_type= business_type.getSelectedItem().toString();

                //str_business_type=""+position;
                if(position==3) // Service
                {
                    /** Items entered by the user is stored in this ArrayList variable */
                  //  ArrayList<String> list = new ArrayList<String>();

                    /** Declaring an ArrayAdapter to set items to ListView */
                    ArrayAdapter<String> adapter;

                    List<String> list = Arrays.asList(getResources().getStringArray(R.array.array_deals_in_these_services));
                    /** Defining the ArrayAdapter to set items to Spinner Widget */
                    adapter = new ArrayAdapter<String>(SignUpBusiness.this, android.R.layout.simple_spinner_item, list);

                    dealsIn.setAdapter(adapter);
                }
                else{
                    ArrayAdapter<String> adapter;

                    List<String> list = Arrays.asList(getResources().getStringArray(R.array.array_deals_in));
                    /** Defining the ArrayAdapter to set items to Spinner Widget */
                    adapter = new ArrayAdapter<String>(SignUpBusiness.this, android.R.layout.simple_spinner_item, list);

                    dealsIn.setAdapter(adapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dealsIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //str_business_type= business_type.getSelectedItem().toString();

                str_dealsIn=""+position;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onItemsSelected(boolean[] selected , String prefered_area_of_business){
         str_prefered_area_of_business=prefered_area_of_business;
        Toast.makeText(this,  "" +prefered_area_of_business, Toast.LENGTH_SHORT).show();
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
                                    SignUpBusiness.this,R.layout.spinner_item,districtList);

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
               /* Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), "Error while loading", Snackbar.LENGTH_LONG);

                snackbar.show();*/

                Alerter.create(SignUpBusiness.this)
                        .setTitle(getString(R.string.Network_Error))
                        .show();

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
                                //  bid=o.getString("id");
                                name =o.getString("name");
                                bid =o.getString("block_id");


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
                                    SignUpBusiness.this,R.layout.spinner_item_2,blockList);

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
                Alerter.create(SignUpBusiness.this)
                        .setTitle(getString(R.string.Network_Error))
                        .show();

                onBackPressed();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void registerFunc()
    {
        //validation
        str_name_of_farm=name_of_farm.getText().toString().trim();
        strwebsite=idwebsite.getText().toString().trim();
        strmobile=input_mob_no.getText().toString().trim();
        stremail=input_mail.getText().toString().trim();
        strpass=input_pass.getText().toString().trim();


            if(str_name_of_farm.equals("")||strmobile.equals("")||
                    (isEditProfile.equals("0")&&strpass.equals("")))
        {
           /* Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "One or More Field Blank", Snackbar.LENGTH_LONG);

            snackbar.show();*/
            Alerter.create(SignUpBusiness.this)
                    .setTitle(getString(R.string.blanks))
                    .show();
        }

     /*  else if(!(Patterns.WEB_URL.matcher(strwebsite).matches()))
        {
           *//* Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Not a valid Website", Snackbar.LENGTH_LONG);

            snackbar.show();*//*
            Alerter.create(SignUpBusiness.this)
                    .setTitle(getString(R.string.invalidwebsite))
                    .show();
        }*/
        else if(!(isValidPhone(strmobile)))
        {
           /* Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Phone number is Invalid", Snackbar.LENGTH_LONG);

            snackbar.show();*/
            Alerter.create(SignUpBusiness.this)
                    .setTitle(getString(R.string.invalidphone))
                    .show();
        }
        else if(strpass.length()<6&&strpass.length()>8)
        {
            //Toast.makeText(this, "Length error(min 6 and max 8 characters)", Toast.LENGTH_SHORT).show();
            Alerter.create(SignUpBusiness.this)
                    .setTitle(getString(R.string.pass_length))
                    .show();
        }
        else
        {

            if(isEditProfile.equals("1"))
                updateProfileCall();
            else
                signupCall();

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

    private void signupCall() {

        if(Utils.hasNetwork(this)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.pleasewait));
            progressDialog.show();


            AndroidNetworking.post(Config.business_signup)
                    .addBodyParameter("properiter_name", properiter_name.getText().toString())
                    .addBodyParameter("email", input_mail.getText().toString())
                    .addBodyParameter("password", input_pass.getText().toString())
                    .addBodyParameter("phone", input_mob_no.getText().toString())
                    .addBodyParameter("website", idwebsite.getText().toString())
                    .addBodyParameter("name_of_farm", name_of_farm.getText().toString())
                    .addBodyParameter("district",str_district_id)
                    .addBodyParameter("block",str_block_id)
                    .addBodyParameter("type_business",str_business_type)
                    .addBodyParameter("deals_in",str_dealsIn)
                    .addBodyParameter("preferred_area_of_business",str_prefered_area_of_business)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.hide();
                            Toast.makeText(SignUpBusiness.this, response, Toast.LENGTH_SHORT).show();

                            Intent intent =new Intent (SignUpBusiness.this, Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        @Override
                        public void onError(ANError anError) {
                            progressDialog.hide();
                            Toast.makeText(SignUpBusiness.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    });

        }


    }

    private void updateProfileCall() {

        if(Utils.hasNetwork(this)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.pleasewait));
            progressDialog.show();


            AndroidNetworking.post(Config.business_signup)
                    .addBodyParameter("user_id", user_id)
                    .addBodyParameter("properiter_name", properiter_name.getText().toString())
                    .addBodyParameter("email", input_mail.getText().toString())
                    .addBodyParameter("phone", input_mob_no.getText().toString())
                    .addBodyParameter("website", idwebsite.getText().toString())
                    .addBodyParameter("name_of_farm", name_of_farm.getText().toString())
                    .addBodyParameter("district",str_district_id)
                    .addBodyParameter("block",str_block_id)
                    .addBodyParameter("type_business",str_business_type)
                    .addBodyParameter("deals_in",str_dealsIn)
                    .addBodyParameter("preferred_area_of_business",str_prefered_area_of_business)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.hide();
                            Toast.makeText(SignUpBusiness.this, response, Toast.LENGTH_SHORT).show();

                            Toast.makeText(SignUpBusiness.this, "Update Successful", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }

                        @Override
                        public void onError(ANError anError) {
                            progressDialog.hide();
                            Toast.makeText(SignUpBusiness.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    });


        }

    }
}
