package com.odishakrushi.BusinessProductPromotion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

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
