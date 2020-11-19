package com.odishakrushi.BusinessProductPromotion;

import android.app.Activity;
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
import com.odishakrushi.AskQPurchase.PopUpPurchaseAnimal;
import com.odishakrushi.CustomHttpClient;
import com.odishakrushi.IcarAgri;
import com.odishakrushi.Login;
import com.odishakrushi.R;
import com.odishakrushi.SignUpFarmer;

public class BPPPesticides extends AppCompatActivity {

    Button btncereal,btnpulses,btnoilseed,btnspices,btnvegetable,btnflower,btnfodder,btncotton;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_bpppesticides);

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

        btncereal=findViewById(R.id.btncereal);btncereal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BPPPesticides.this,SaleListPesticides.class);
                intent.putExtra("PROD_NAME","Cereal");
                startActivity(intent);
            }
        });
        btnpulses=findViewById(R.id.btnpulses);btnpulses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BPPPesticides.this,SaleListPesticides.class);
                intent.putExtra("PROD_NAME","Pulses");
                startActivity(intent);
            }
        });
        btnoilseed=findViewById(R.id.btnoilseed);btnoilseed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BPPPesticides.this,SaleListPesticides.class);
                intent.putExtra("PROD_NAME","Oilseed");
                startActivity(intent);
            }
        });
        btnspices=findViewById(R.id.btnspices);btnspices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BPPPesticides.this,SaleListPesticides.class);
                intent.putExtra("PROD_NAME","Spices");
                startActivity(intent);
            }
        });
        btnvegetable=findViewById(R.id.btnvegetable);btnvegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BPPPesticides.this,SaleListPesticides.class);
                intent.putExtra("PROD_NAME","Vegetable");
                startActivity(intent);
            }
        });
        btnflower=findViewById(R.id.btnflower);btnflower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BPPPesticides.this,SaleListPesticides.class);
                intent.putExtra("PROD_NAME","Flower");
                startActivity(intent);
            }
        });
        btnfodder=findViewById(R.id.btnfodder);btnfodder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BPPPesticides.this,SaleListPesticides.class);
                intent.putExtra("PROD_NAME","Fodder");
                startActivity(intent);
            }
        });
        btncotton=findViewById(R.id.btncotton);btncotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BPPPesticides.this,SaleListPesticides.class);
                intent.putExtra("PROD_NAME","Cotton");
                startActivity(intent);
            }
        });


    }

}
