package com.odishakrushi;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.AskQAgriculture.CultivationPractise;
import com.odishakrushi.AskQSale.SaleList;
import com.odishakrushi.ProfilePic.ProfilePic;

public class BtypeDealsInMDF extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    String strname,str_website,str_mobile,str_email,str_pass,str_farm_name,
            str_propertier_name,str_district_id,str_block_id,str_area_of_business,strbusinesstype;
    String str_deals_in_product="";
    String message="",user_id="";

    RadioGroup radioDealsInProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btype_deals_in_mdf);
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


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            strname = bundle.getString("NAME");
            str_website = bundle.getString("WEBSITE");
            str_mobile = bundle.getString("MOBILE");
            str_email = bundle.getString("EMAIL");
            str_pass = bundle.getString("PASSWORD");
            str_farm_name = bundle.getString("FARM_NAME");
            str_propertier_name = bundle.getString("PROPERTIER_NAME");
            str_district_id= bundle.getString("DISTRICT");
            str_block_id=bundle.getString("BLOCK");
            str_area_of_business=bundle.getString("AREA_OF_BUSINESS");
            strbusinesstype=bundle.getString("BUSINESS_TYPE");

            Log.d("WEBSITE",str_website );
            Log.d("MOBILE",str_mobile );
            Log.d("EMAIL",str_email );
            Log.d("PASSWORD",str_pass );
            Log.d("FARM_NAME",str_farm_name );
            Log.d("PROPERTIER_NAME",str_propertier_name );
            Log.d("DISTRICT",str_district_id );
            Log.d("BLOCK",str_block_id );
            Log.d("AREA_OF_BUSINESS",str_area_of_business );
            Log.d("BUSINESS_TYPE",strbusinesstype );
        }

        radioDealsInProduct=findViewById(R.id.radioDealsInProduct);
        radioDealsInProduct.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_deals_in_product=rb.getText().toString();
                    Toast.makeText(BtypeDealsInMDF.this, str_deals_in_product, Toast.LENGTH_SHORT).show();


                }

            }
        });

    }

    public void onClickSignUp(View view)
    {

        Alerter.create(BtypeDealsInMDF.this)
                .setText(getString(R.string.Sign_Up)+"....")
                .setIcon(R.drawable.ic_upload)
                .setBackgroundColorRes(R.color.colorPrimary)
                .setIconColorFilter(0)
                .enableProgress(true)
                .setProgressColorRes(R.color.white)
                .show();

        AndroidNetworking.post(Config.business_signup)
                .addBodyParameter("first_name", strname)
                .addBodyParameter("name_of_farm", str_farm_name)
                .addBodyParameter("properiter_name",str_propertier_name)
                .addBodyParameter("district", str_district_id)
                .addBodyParameter("block", str_block_id)
                .addBodyParameter("phone", str_mobile)
                .addBodyParameter("email", str_email)
                .addBodyParameter("website", str_website)
                .addBodyParameter("area_of_business", str_area_of_business)
                .addBodyParameter("business_type",strbusinesstype)
                .addBodyParameter("deals_in_product", str_deals_in_product)
                .addBodyParameter("password",str_pass)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            message=jsonObject.getString("message");
                            user_id=jsonObject.getString("user_id");

                           /* Alerter.create(BtypeDealsInMDF.this)
                                    .setTitle(message)
                                    .show();

                            new Handler().postDelayed(new Runnable()
                            {

                                @Override
                                public void run() {
                                    // This method will be executed once the timer is over

                                    startActivity(new Intent(BtypeDealsInMDF.this,Login.class));
                                    finish();
                                }
                            }, 3);*/

                            if(message.equals("Registration Successfull"))
                            {

                                Alerter.create(BtypeDealsInMDF.this)
                                        .setTitle(message)
                                        .show();

                                new Handler().postDelayed(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        Intent i = new Intent(BtypeDealsInMDF.this, ProfilePic.class);//Login
                                        i.putExtra("USER_ID",user_id);
                                        startActivity(i);
                                        finish();
                                    }
                                }, 3000);

       /* startActivity(new Intent(SignUpFarmer_2.this,Login.class));
                finish();*/
                            }
                            else {
                                //Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                Alerter.create(BtypeDealsInMDF.this)
                                        .setTitle(message)
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Alerter.create(BtypeDealsInMDF.this)
                                .setTitle(getString(R.string.Network_Error))
                                .show();
                    }
                });
    }
  /*  public void onClickAgrilProduct(View view)
    {
        Intent intent=new Intent(this,AgrilProductBusiness.class);

        intent.putExtra("WEBSITE",str_website );
        intent.putExtra("MOBILE",str_mobile );
        intent.putExtra("EMAIL",str_email );
        intent.putExtra("PASSWORD",str_pass );
        intent.putExtra("FARM_NAME",str_farm_name );
        intent.putExtra("PROPERTIER_NAME",str_propertier_name );
        intent.putExtra("DISTRICT",str_district_id );
        intent.putExtra("BLOCK",str_block_id );
        intent.putExtra("AREA_OF_BUSINESS",str_area_of_business );
        intent.putExtra("BUSINESS_TYPE",strbusinesstype );
        intent.putExtra("DEALS_IN_PRODUCT","Agril Product" );

        startActivity(intent);
    }


    public void onClickVeterinary(View view)
    {
        Intent intent=new Intent(this,VeterinaryItemBusiness.class);

        intent.putExtra("WEBSITE",str_website );
        intent.putExtra("MOBILE",str_mobile );
        intent.putExtra("EMAIL",str_email );
        intent.putExtra("PASSWORD",str_pass );
        intent.putExtra("FARM_NAME",str_farm_name );
        intent.putExtra("PROPERTIER_NAME",str_propertier_name );
        intent.putExtra("DISTRICT",str_district_id );
        intent.putExtra("BLOCK",str_block_id );
        intent.putExtra("AREA_OF_BUSINESS",str_area_of_business );
        intent.putExtra("BUSINESS_TYPE",strbusinesstype );
        intent.putExtra("DEALS_IN_PRODUCT","Veterinary Item" );
        startActivity(intent);
    }

    public void onClickFish(View view)
    {
        Intent intent=new Intent(this,FishBusiness.class);

        intent.putExtra("WEBSITE",str_website );
        intent.putExtra("MOBILE",str_mobile );
        intent.putExtra("EMAIL",str_email );
        intent.putExtra("PASSWORD",str_pass );
        intent.putExtra("FARM_NAME",str_farm_name );
        intent.putExtra("PROPERTIER_NAME",str_propertier_name );
        intent.putExtra("DISTRICT",str_district_id );
        intent.putExtra("BLOCK",str_block_id );
        intent.putExtra("AREA_OF_BUSINESS",str_area_of_business );
        intent.putExtra("BUSINESS_TYPE",strbusinesstype );
        intent.putExtra("DEALS_IN_PRODUCT","Fish" );
        startActivity(intent);
    }

    public void onClickOthers(View view)
    {
        Intent intent=new Intent(this,DealsinOtherBusiness.class);

        intent.putExtra("WEBSITE",str_website );
        intent.putExtra("MOBILE",str_mobile );
        intent.putExtra("EMAIL",str_email );
        intent.putExtra("PASSWORD",str_pass );
        intent.putExtra("FARM_NAME",str_farm_name );
        intent.putExtra("PROPERTIER_NAME",str_propertier_name );
        intent.putExtra("DISTRICT",str_district_id );
        intent.putExtra("BLOCK",str_block_id );
        intent.putExtra("AREA_OF_BUSINESS",str_area_of_business );
        intent.putExtra("BUSINESS_TYPE",strbusinesstype );
        intent.putExtra("DEALS_IN_PRODUCT","Others" );
        startActivity(intent);
    }*/
}
