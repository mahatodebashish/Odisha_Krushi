package com.odishakrushi.EditProfileActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
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
import com.odishakrushi.Config;
import com.tapadoo.alerter.Alerter;
import com.xeoh.android.checkboxgroup.CheckBoxGroup;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.Login;
import com.odishakrushi.PopUpMachineTool;
import com.odishakrushi.R;
import com.odishakrushi.SignUpBusiness_2;

public class EditProfileBusiness extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    AppCompatEditText idwebsite,id_properiter_name,input_mob_no,input_pass,input_mail,id_farm_name;
    String strwebsite,strmobile,stremail,strpass;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id;

    int TIME_OUT=6;
    String website, email,salt,name_of_farm,properiter_name,area_of_business,business_type,str_area_of_business,
    deals_in_product,phone,deals_in_product_other,fish_medicine,fish_net,fish_feed,fish_others,
            districtid,blockid,category_type,quantity,breed,feed,medicine,others,agril_product_category,
            seed,pesticides,fertiliser,catg_other,agril_machinery,sale_service_spare_parts_available;

    int position3=0;

    /*Doubt in url*/
//    private static final String URL_DATA = "http://demo.ratnatechnology.co.in/farmerapp/api/commons/districts/29";
//    private static final String URL_DATA2 = "http://demo.ratnatechnology.co.in/farmerapp/api/commons/blocks?district_id=";
//    private static final String URL_DATA3 = "http://demo.ratnatechnology.co.in/farmerapp/api/commons/gps?block_id=";


    private static final String URL_DATA = Config.baseUrl+"commons/districts/29";
    private static final String URL_DATA2 =  Config.baseUrl+"commons/blocks?district_id=";
    private static final String URL_DATA3 =  Config.baseUrl+"commons/gps?block_id=";


    String str_district_id="",str_block_id="",str_gp_id="";
    String strfathername="",strgender="",strcaste="",strvillage="",
            strdistrict="Select Your District",strblock="Select Your Block",strgp="Select Your GP";
    ArrayList<String> arraylist_districtid=new ArrayList<String>();
    ArrayList<String> arraylist_blockid;
    ArrayList<String> arraylist_gp_id;


    RadioGroup rgbusinesstype;
    TextView load_area_of_business,txtbusiness_type,txt_deals_in_product,txt_deals_in_product_other,
            txtcategory_type,txtagril_product_category,txtagril_machinery,txtsparepartavailability;

    LinearLayout layoutcheckbox;
    Button tractorID,powertillerID,rotavatorID,ploughID,ridgerID,levellerID,seeddrillID,transplanterID,weederID,sprayerID,othersID,
            reaperID,thresherID,cleanerID,graderID,combineID,pumpsID,parboilingunitID,ricemillID,decorticatorID,toolfruitvegID;

    CheckBox selectAll;
    CheckBoxGroup<String> checkBoxGroup;

    TextView txtdistrict,txtblock;
    int flagclicked=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_edit_profile_business);

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

        txtdistrict=findViewById(R.id.txtdistrict);
        txtblock=findViewById(R.id.txtblock);
        selectAll=findViewById(R.id.selectAll);
        idwebsite=findViewById(R.id.idwebsite);
        input_mob_no=findViewById(R.id.input_mob_no);
        input_pass=findViewById(R.id.input_pass);
        input_mail=findViewById(R.id.input_mail);
        id_farm_name=findViewById(R.id.id_farm_name);

        rgbusinesstype=findViewById(R.id.rgbusinesstype);
        id_properiter_name=findViewById(R.id.id_properiter_name);
        load_area_of_business=findViewById(R.id.load_area_of_business);
        txtbusiness_type=findViewById(R.id.business_type);
        txt_deals_in_product=findViewById(R.id.txt_deals_in_product);
        txt_deals_in_product_other=findViewById(R.id.txt_deals_in_product_other);
        txtcategory_type=findViewById(R.id.category_type);
        txtagril_product_category=findViewById(R.id.agril_product_category);
        txtagril_machinery=findViewById(R.id.agril_machinery);
        layoutcheckbox=findViewById(R.id.layoutcheckbox);

        /*district.setEnabled(false);
        block.setEnabled(false);*/
        txtsparepartavailability=findViewById(R.id.txtsparepartavailability);

        tractorID=findViewById(R.id.tractorID);powertillerID=findViewById(R.id.powertillerID);rotavatorID=findViewById(R.id.rotavatorID);
        ploughID=findViewById(R.id.ploughID);ridgerID=findViewById(R.id.ridgerID);levellerID=findViewById(R.id.levellerID);seeddrillID=findViewById(R.id.seeddrillID);
        transplanterID=findViewById(R.id.transplanterID);weederID=findViewById(R.id.weederID);sprayerID=findViewById(R.id.sprayerID);
        othersID=findViewById(R.id.othersID);reaperID=findViewById(R.id.reaperID);thresherID=findViewById(R.id.thresherID);cleanerID=findViewById(R.id.cleanerID);
        graderID=findViewById(R.id.graderID);combineID=findViewById(R.id.combineID);pumpsID=findViewById(R.id.pumpsID);
        parboilingunitID=findViewById(R.id.parboilingunitID);ricemillID=findViewById(R.id.ricemillID);decorticatorID=findViewById(R.id.decorticatorID);
        toolfruitvegID=findViewById(R.id.toolfruitvegID);




        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        user_id=sharedpreferences.getString("FLAG", null);
        Log.d("user_id:",user_id);
        editor.commit(); // commit changes


        //load data
        getData();


        HashMap<CheckBox, String> checkBoxMap = new HashMap<>();
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox1), "Angul/Anugul");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox2), "Balangir");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox3), "Balasore-Baleshwar");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox4), "Bargarh");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox5), "Baudh");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox6), "Bhadrak");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox7), "Cuttack");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox8), "Deogarh-Debagarh");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox9), "Dhenkanal");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox10), "Gajapati");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox11), "Ganjam");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox12), "Jagatsinghapur");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox13), "Jajpur");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox14), "Jharsuguda");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox15), "Kalahandi");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox16), "Kandhamal");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox17), "Kendrapara");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox18), "Kendujhar");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox19), "Khordha");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox20), "Koraput");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox21), "Malkangiri");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox22), "Mayurbhanj");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox23), "Nabarangpur");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox24), "Nayagarh");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox25), "Nuapada");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox26), "Puri");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox27), "Rayagada");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox28), "Sambalpur");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox29), "Subarnapur");
        checkBoxMap.put((CheckBox) findViewById(R.id.checkBox30), "Sundargarh");


/*
        CheckBoxGroup<String> checkBoxGroup = new CheckBoxGroup<>(checkBoxMap,
                new CheckBoxGroup.CheckedChangeListener<String>() {
                    @Override
                    public void onCheckedChange(ArrayList<String> values) {


                        // Convert the ArrayList into a String.

                        StringBuilder sb = new StringBuilder();
                        for (String s : values)
                        {
                            sb.append(s);
                            sb.append(",");
                        }
                        str_area_of_business= sb.toString();
                        System.out.println(sb.toString());
                          Toast.makeText(EditProfileBusiness.this,str_area_of_business,Toast.LENGTH_LONG).show();
                    }

                   *//* @Override
                    public void onOptionChange(ArrayList<String> options) {
                        Toast.makeText(BtypeDealsInS.this,
                                options.toString(),
                                Toast.LENGTH_LONG).show();
                    }*//*
                });*/

        checkBoxGroup = new CheckBoxGroup<>(checkBoxMap, checkedChangeListener);
        checkBoxGroup.setValues(new ArrayList<String>());

        // Select All checkbox
        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flagclicked==1)
                {

                    checkBoxGroup.setValues(Arrays.asList("Angul/Anugul", "Balangir", "Balasore-Baleshwar", "Bargarh", "Baudh", "Bhadrak", "Cuttack", "Deogarh-Debagarh", "Dhenkanal", "Gajapati",
                            "Ganjam", "Jagatsinghapur", "Jajpur", "Jharsuguda", "Kalahandi", "Kandhamal", "Kendrapara", "Kendujhar", "Khordha", "Koraput",
                            "Malkangiri", "Mayurbhanj", "Nabarangpur", "Nayagarh", "Nuapada", "Puri", "Rayagada", "Sambalpur", "Subarnapur", "Sundargarh"));
                    flagclicked=0;
                }

                else if(flagclicked==0)
                {
                    checkBoxGroup.setValues(Arrays.asList(""));
                    flagclicked=1;
                }
            }
        });

    }

    private CheckBoxGroup.CheckedChangeListener<String> checkedChangeListener
            = new CheckBoxGroup.CheckedChangeListener<String>() {
        @Override
        public void onCheckedChange(ArrayList<String> values) {
            // Convert the ArrayList into a String.

            StringBuilder sb = new StringBuilder();
            for (String s : values)
            {
                sb.append(s);
                sb.append(",");
            }
            str_area_of_business= sb.toString();
            System.out.println(sb.toString());
           // Toast.makeText(EditProfileBusiness.this,str_area_of_business,Toast.LENGTH_LONG).show();
        }

    };



    public void getData()
    {
        //String url ="http://demo.ratnatechnology.co.in/farmerapp/api/user/getBusinessMan?user_id="+user_id; Doubt
        String url = Config.baseUrl+"user/getBusinessMan?user_id="+user_id;

        //creating a string request to send request to the url

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String getresponse) {

                        //Toast.makeText(EditProfileBusiness.this, getresponse, Toast.LENGTH_SHORT).show();
                        try {

                            Log.d( "onResponse: ",getresponse);
                            JSONObject jsonObject=new JSONObject(getresponse);

                            JSONArray array=jsonObject.getJSONArray("data");

                            for(int i=0;i<array.length();i++) {


                                JSONObject o = array.getJSONObject(i);
                                website=o.getString("website");
                                idwebsite.setText(website);

                                phone = o.getString("phone");  input_mob_no.setText(phone);
                                salt = o.getString("salt");  input_pass.setText(salt);

                                email = o.getString("email");
                                if(email.equalsIgnoreCase("NULL"))
                                    input_mail.setText("--");
                                else
                                    input_mail.setText(email);

                                name_of_farm=o.getString("name_of_farm");  id_farm_name.setText(name_of_farm);

                                properiter_name=o.getString("properiter_name"); id_properiter_name.setText(properiter_name);

                                districtid = o.getString("district");
                                parseDistrict(districtid);

                                blockid = o.getString("block");
                                parseBlock(blockid);

                                area_of_business= o.getString("area_of_business");
                                load_area_of_business.setText("- "+area_of_business+"(your current area of business)");
                                business_type=o.getString("business_type");txtbusiness_type.setText(business_type);

                                deals_in_product=o.getString("deals_in_product");  txt_deals_in_product.setText(deals_in_product);
                                deals_in_product_other=o.getString("deals_in_product_other");
                                if(deals_in_product_other.equals(""))
                                    txt_deals_in_product_other.setText("---");
                                else
                                    txt_deals_in_product_other.setText(deals_in_product_other);

                                fish_medicine=o.getString("fish_medicine");
                                fish_net=o.getString("fish_net");
                                fish_feed=o.getString("fish_feed");
                                fish_others=o.getString("fish_others");







                                agril_product_category=o.getString("agril_product_category");
                                if (agril_product_category.equals(""))
                                    txtagril_product_category.setText("---");
                                else
                                    txtagril_product_category.setText(agril_product_category);



                                agril_machinery=o.getString("agril_machinery");
                                Log.d("agril_machinery",agril_machinery);
                                if(agril_machinery.equals("Yes"))
                                {
                                    txtagril_machinery.setText("Yes");
                                    layoutcheckbox.setVisibility(View.VISIBLE);

                                }
                                else  if(agril_machinery.equals("No"))
                                {
                                    txtagril_machinery.setText("No");
                                    layoutcheckbox.setVisibility(View.GONE);
                                }

                                sale_service_spare_parts_available=o.getString("sale_service_spare_parts_available");
                                Log.d("spare_parts",sale_service_spare_parts_available);
                                if (sale_service_spare_parts_available.equals(""))
                                    txtsparepartavailability.setText("---");
                                else if (sale_service_spare_parts_available.equals("Yes"))
                                    txtsparepartavailability.setText("Yes");

                                else if (sale_service_spare_parts_available.equals("No"))
                                    txtsparepartavailability.setText("No");





                                seed=o.getString("seed");
                                pesticides=o.getString("pesticides");
                                fertiliser=o.getString("fertiliser");
                                catg_other=o.getString("catg_other");
                                others=o.getString("others");

                             /*   quantity=o.getString("quantity");
                                breed=o.getString("breed");
                                feed=o.getString("feed");
                                medicine=o.getString("medicine");

                               category_type=o.getString("category_type");

                                if(category_type.equals(""))
                                    txtcategory_type.setText("---");
                                else
                                    txtcategory_type.setText(category_type);*/

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
                        Toast.makeText(EditProfileBusiness.this,"Error while loading", Toast.LENGTH_SHORT).show();

                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    /**
     * here goes for district get and set
     * */
    public void parseDistrict(String districtid)
    {
        String jsondistrict = null;
        try {
            InputStream is = getAssets().open("district.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsondistrict = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        System.out.print(jsondistrict);


        try {

            JSONObject jsonObject=new JSONObject(jsondistrict);
            JSONArray jsonArray=jsonObject.getJSONArray("districts");

            String id="",name="",state_id="";
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                id=jsonObject1.getString("id");
                name=jsonObject1.getString("name");
                state_id=jsonObject1.getString("state_id");

                if(id.equals(districtid))
                {
                    txtdistrict.setText(name);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * here goes the block get and set
     * */
    public void parseBlock(String blockid)
    {
        String json = null;
        try {
            InputStream is = getAssets().open("block.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        System.out.print(json);


        try {

            JSONArray jsonArray=new JSONArray(json);

            String id="",name="",district_id="";
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                id=jsonObject.getString("id");
                name=jsonObject.getString("name");
                district_id=jsonObject.getString("district_id");

                if(id.equals(blockid))
                {
                    txtblock.setText(name);
                }

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void onClickUpdate(View view)
    {
        new AsyncUpdateBusiness().execute();
    }
    private  class AsyncUpdateBusiness extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null, message = "";

        @Override
        protected void onPreExecute() {


            pDialog = new ProgressDialog(EditProfileBusiness.this);
            if(user_id!="")
            pDialog.setMessage("Updating...");
            else
                pDialog.setMessage("Registering...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id", user_id));
            cred.add(new BasicNameValuePair("area_of_business", str_area_of_business));



            //String urlRouteList ="http://demo.ratnatechnology.co.in/farmerapp/api/user/signup_businessMan"; Doubt
            String urlRouteList =Config.baseUrl+"user/signup_businessMan";
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

            }
            return null;
        }

        protected void onPostExecute(Void result) {
            pDialog.dismiss();
            if (message.equals("Update Successfull")) {
                //  Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                /*Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);

                snackbar.show();*/

               /* Alerter.create(EditProfileBusiness.this)
                        .setTitle(getString(R.string.press_two_times))
                        .show();*/

                Toast.makeText(EditProfileBusiness.this, getString(R.string.press_two_times), Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      /*  Intent i = new Intent(EditProfileBusiness.this, Login.class);
                        startActivity(i);*/
                        finish();
                    }
                }, TIME_OUT);


            } else {
                //Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                /*Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);

                snackbar.show();*/

                Alerter.create(EditProfileBusiness.this)
                        .setTitle(getString(R.string.press_two_times))
                        .show();
            }

        }



    }
    public void onClickTractor(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Tractor");

        startActivity(intent);
    }

    public void onClickPowerTiller(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Power Tiller");

        startActivity(intent);
    }

    public void onClickRotavator(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Rotavator");

        startActivity(intent);
    }

    public void onClickPlough(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Plough");

        startActivity(intent);
    }
    public void onClickRidger(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Ridger");

        startActivity(intent);
    }

    public void onClickLeveller(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Leveller");

        startActivity(intent);

    }


    public void onClickSeedDrill(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "SeedDrill");
        startActivity(intent);

    }

    public void onClickTransplanter(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Transplanter");
        startActivity(intent);

    }

    public void onClickWeeder(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Weeder");
        startActivity(intent);

    }
    public void onClickSprayer(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Sprayer");
        startActivity(intent);

    }
    public void onClickReaper(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Reaper");
        startActivity(intent);

    }
    public void onClickThresher(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Thresher");
        startActivity(intent);

    }

    public void onClickCleaner(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Cleaner");
        startActivity(intent);

    }
    public void onClickGrader(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Grader");
        startActivity(intent);

    }
    public void onClickCombine(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Combine");
        startActivity(intent);

    }
    public void onClickPumps(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Pumps");
        startActivity(intent);

    }
    public void onClickParboilingUnit(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Parboiling Unit");
        startActivity(intent);

    }
    public void onClickRiceMill(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Rice Mill");
        startActivity(intent);

    }
    public void onClickDecorticator(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Decorticator");
        startActivity(intent);

    }
    public void onClickToolsForFruitAndVegetable(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Tools For Fruit And Vegetable");
        startActivity(intent);

    }
    public void onClickOthers(View view)
    {
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Others");
        startActivity(intent);

    }

}
