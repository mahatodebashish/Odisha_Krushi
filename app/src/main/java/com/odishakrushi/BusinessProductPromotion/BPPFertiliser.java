package com.odishakrushi.BusinessProductPromotion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xeoh.android.checkboxgroup.CheckBoxGroup;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.R;

public class BPPFertiliser extends AppCompatActivity {

    Button btnnpk,btnmicronutrients;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    String str_subsidy,str_fertiliser_type;
    RadioGroup radioSubsidy;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_bppfertiliser);

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

        btnnpk=findViewById(R.id.btnnpk);btnnpk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BPPFertiliser.this,SaleListPesticides.class);
                intent.putExtra("PROD_NAME","NPK_Compund");
                startActivity(intent);
            }
        });
        btnmicronutrients=findViewById(R.id.btnmicronutrients);btnmicronutrients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BPPFertiliser.this,SaleListPesticides.class);
                intent.putExtra("PROD_NAME","Micronutrients");
                startActivity(intent);
            }
        });
    }


}
