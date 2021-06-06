package com.odishakrushi.BusinessProductPromotion;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import com.odishakrushi.R;

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
