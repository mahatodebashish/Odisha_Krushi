package com.odishakrushi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.odishakrushi.utils.Utils;
import com.pixplicity.easyprefs.library.Prefs;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.cloudist.acplibrary.ACProgressBaseDialog;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static com.odishakrushi.Config.ODIA_DISTRICTS;
import static java.security.AccessController.getContext;

public class SignUpFarmer extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    } //Calligraphy

    Button register;
    LinearLayout layoutGP;
    AppCompatEditText fname,fathername,input_mob_no,input_mail,input_pass;
    String strfathersname="";
    RadioGroup radioGender,radioCaste;
    String str_gender="",str_caste="";

    private static final String URL_DATA = Config.baseUrl+"commons/districts/29";
    private static final String URL_DATA2 = Config.baseUrl+"commons/blocks?district_id=";
    private static final String URL_DATA3 =  Config.baseUrl+"commons/gps?block_id=";

    //getextra
    String strname="",strfathername="",strmobile="",stremail="",strpass="",strgender="",strcaste="",strvillage="",
            strdistrict="Select Your District",strblock="Select Your Block",strgp="Select Your GP";

    Spinner district,block,gp;//

    String str_district_id="",str_block_id="",str_gp_id="";

    ArrayList<String> arraylist_districtid=new ArrayList<String>();
    ArrayList<String> arraylist_blockid;
    ArrayList<String> arraylist_gp_id;

    String gpNotFound="";
    String str_user_id="";
    String message="";
    boolean status;
    String response="";

    ImageView imgArrow;
    ScrollView scrollView;
    boolean scrollDown;

    String isEditProfile="0";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_sign_up_farmer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


        fname=findViewById(R.id.fname);
        fathername=findViewById(R.id.fathername);
        input_mob_no=findViewById(R.id.input_mob_no);
        input_mail=findViewById(R.id.input_mail);
        input_pass=findViewById(R.id.input_pass);
        radioGender=findViewById(R.id.radioGender);
        radioCaste=findViewById(R.id.radioCaste);
        district=(Spinner) findViewById(R.id.district);
        block=findViewById(R.id.block);
        gp=findViewById(R.id.gp);
        register=findViewById(R.id.register);
        layoutGP=findViewById(R.id.layoutGP);
        imgArrow=findViewById(R.id.imgArrow);
        scrollView=findViewById(R.id.scrollView);

        imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!scrollDown){
                    scrollView.fullScroll(View.FOCUS_DOWN);
                    scrollDown=true;
                    imgArrow.setRotation(0);
                }
                else {
                    scrollView.fullScroll(View.FOCUS_UP);
                    scrollDown=false;
                    imgArrow.setRotation(180);
                }
            }
        });

        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_gender=rb.getText().toString();
                    Toast.makeText(SignUpFarmer.this, str_gender, Toast.LENGTH_SHORT).show();


                }

            }
        });
        radioCaste.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_caste=rb.getText().toString();
                    Toast.makeText(SignUpFarmer.this, str_caste, Toast.LENGTH_SHORT).show();


                }

            }
        });

        gpNotFound="No";
        load_district_in_spinner();
        // SPINNER SELECT

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strdistrict= district.getSelectedItem().toString();
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
                strblock= block.getSelectedItem().toString();
                //str_block_id= String.valueOf(position+1);

                // TASK IS TO GET THE BLOCK_ID FROM DISTRICT_ID ..?
                str_block_id=arraylist_blockid.get(position);
                load_gps_from_blockid(str_block_id);//str_block_id
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        gp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strgp= gp.getSelectedItem().toString();

                // TASK IS TO GET THE GP_ID FROM BLOCK_ID ..?
                str_gp_id=arraylist_gp_id.get(position);

                //str_gp_id= String.valueOf(position+1);

                // Toast.makeText(SignUpFarmer_2.this, str_gp_id, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       /* register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fname.getText().toString().isEmpty()||str_gender.equals("")||input_pass.getText().toString().isEmpty()||
                        str_caste.equals("")||input_mob_no.getText().toString().isEmpty()){
                    Toast.makeText(SignUpFarmer.this, "Some field(s) are Empty", Toast.LENGTH_SHORT).show();
                }
                else{

                }
            }
        });*/




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
                                    SignUpFarmer.this,R.layout.spinner_item,districtList);

                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            district.setAdapter(spinnerArrayAdapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpFarmer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                            onBackPressed();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SignUpFarmer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
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

        if(arraylist_blockid!=null)
            arraylist_blockid.clear();

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
                                bid =o.getString("block_id");//district_id


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
                                    SignUpFarmer.this,R.layout.spinner_item_2,blockList);

                            spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_item_2);
                            block.setAdapter(spinnerArrayAdapter2);



                        } catch (JSONException e) {//JSONException
                            e.printStackTrace();
                            Toast.makeText(SignUpFarmer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
                            onBackPressed();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SignUpFarmer.this,getString(R.string.something_went_wrong),Toast.LENGTH_LONG);
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


                            //  final List<String> blockList = new ArrayList<>(Arrays.asList(strarrayblocks));
                            final List<String> gpList = new ArrayList<>(gpl);

                            // Initializing an ArrayAdapter
                            final ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                                    SignUpFarmer.this,R.layout.spinner_item_2,gpList);

                            spinnerArrayAdapter3.setDropDownViewResource(R.layout.spinner_item_2);
                            gp.setAdapter(spinnerArrayAdapter3);
                            layoutGP.setVisibility(View.VISIBLE);
                            gpNotFound="No";

                        } catch (JSONException e)
                        { //JSONException
                            e.printStackTrace();
                            str_gp_id="0"; // for passing in str_gp_id as zero
                            gpNotFound="Yes";
                            layoutGP.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                /* progressDialog.dismiss();*/
                // Toast.makeText(getApplicationContext(),"No GP Found",Toast.LENGTH_LONG).show();
              /*  Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), "No GP Found", Snackbar.LENGTH_LONG);

                snackbar.show();
*/
                Alerter.create(SignUpFarmer.this)
                        .setTitle(getString(R.string.no_gp))
                        .show();

                str_gp_id="0"; // for passing in str_gp_id as zero
                gpNotFound="Yes";

               // gp.setVisibility(View.GONE);
                layoutGP.setVisibility(View.GONE);
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onClickNext(View view) {
        //validation
       /* strname=fname.getText().toString().trim();
        strfathersname=fathername.getText().toString().trim();
        strmobile=input_mob_no.getText().toString().trim();
        stremail=input_mail.getText().toString().trim();
        strpass=input_pass.getText().toString().trim();*/

        if (fname.getText().toString().isEmpty() || str_gender.equals("") || input_pass.getText().toString().isEmpty() ||
                str_caste.equals("") || input_mob_no.getText().toString().isEmpty()) {
            Toast.makeText(SignUpFarmer.this, getString(R.string.blanks), Toast.LENGTH_SHORT).show();
        } else if (!(isValidPhone(input_mob_no.getText().toString()))) {
         /*   Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Phone number is Invalid", Snackbar.LENGTH_LONG);

            snackbar.show();*/
            /*Alerter.create(SignUpFarmer.this)
                    .setTitle(getString(R.string.invalidphone))
                    .show();*/

            Toast.makeText(SignUpFarmer.this, getString(R.string.invalidphone), Toast.LENGTH_SHORT).show();
        } else if (input_pass.getText().toString().length() < 6 && input_pass.getText().toString().length() > 8) {
            Toast.makeText(this, getString(R.string.pass_length), Toast.LENGTH_SHORT).show();

           /* Alerter.create(SignUpFarmer.this)
                    .setTitle(getString(R.string.pass_length))
                    .show();*/
        } else {
           /* Intent intent = new Intent (SignUpFarmer.this, SignUpFarmer_2.class);
            intent.putExtra("USER_ID","1234");
            startActivity(intent);*/

            if (Utils.hasNetwork(this)) {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage(getString(R.string.pleasewait));
                progressDialog.show();

                AndroidNetworking.post(Config.farmersignupcommon)

                         .addBodyParameter("user_id", "")
                         .addBodyParameter("name", fname.getText().toString())
                        .addBodyParameter("fathers_name", fathername.getText().toString())
                        .addBodyParameter("gender", str_gender)
                        .addBodyParameter("caste", str_caste)
                        .addBodyParameter("phone", input_mob_no.getText().toString())
                        .addBodyParameter("password", input_pass.getText().toString())
                        .addBodyParameter("district", str_district_id)
                        .addBodyParameter("block", str_block_id)
                        .addBodyParameter("gp", str_gp_id)
                        .build()
                        .getAsString(new StringRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.hide();

                                try {

                                    JSONObject jsonObject = new JSONObject(response);
                                    str_user_id = String.valueOf(jsonObject.optInt("user_id"));
                                    message = jsonObject.optString("message");
                                    status = jsonObject.optBoolean("status");
                                    Toast.makeText(SignUpFarmer.this, message, Toast.LENGTH_SHORT).show();

                                    if (status) {
                                        Intent intent = new Intent(SignUpFarmer.this, SignUpFarmer_2.class);
                                        intent.putExtra("USER_ID", str_user_id);
                                        startActivity(intent);
                                        finish();
                                    }

                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                    progressDialog.hide();
                                    try {
                                        JSONObject jsonObject1 = new JSONObject(response);
                                        message = jsonObject1.optString("message");
                                        Toast.makeText(SignUpFarmer.this, message, Toast.LENGTH_SHORT).show();
                                    } catch (JSONException ex1) {
                                        ex.printStackTrace();

                                    }
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                             progressDialog.hide();
                            Toast.makeText(SignUpFarmer.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

                            }
                        });


            }

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
}
