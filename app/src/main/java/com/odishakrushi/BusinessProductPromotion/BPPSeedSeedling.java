package com.odishakrushi.BusinessProductPromotion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.R;

public class BPPSeedSeedling extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_bppseed_seedling);


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
    }

    public void onClickCereal(View view)
    {
        Intent intent=new Intent(this,SaleListPesticides.class);
        intent.putExtra("PROD_NAME", "Cereal");
        startActivity(intent);
    }
    public void onClickPulse(View view)
    {
        Intent intent=new Intent(this,SaleListPesticides.class);
        intent.putExtra("PROD_NAME", "Pulse");
        startActivity(intent);
    }
    public void onClickOilseed(View view)
    {
        Intent intent=new Intent(this,SaleListPesticides.class);
        intent.putExtra("PROD_NAME", "Oilseed");
        startActivity(intent);
    }
    public void onClickSpices(View view)
    {
        Intent intent=new Intent(this,SaleListPesticides.class);
        intent.putExtra("PROD_NAME", "Spices");
        startActivity(intent);
    }
    public void onClickVegetable(View view)
    {
        Intent intent=new Intent(this,SaleListPesticides.class);
        intent.putExtra("PROD_NAME", "Vegetable");
        startActivity(intent);
    }
    public void onClickFlower(View view)
    {
        Intent intent=new Intent(this,SaleListPesticides.class);
        intent.putExtra("PROD_NAME", "Flower");
        startActivity(intent);
    }
    public void onClickFodder(View view)
    {
        Intent intent=new Intent(this,SaleListPesticides.class);
        intent.putExtra("PROD_NAME", "Fodder");
        startActivity(intent);
    }
    public void onClickCotton(View view)
    {
        Intent intent=new Intent(this,SaleListPesticides.class);
        intent.putExtra("PROD_NAME", "Cotton");
        startActivity(intent);
    }

}
