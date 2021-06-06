package com.odishakrushi.AskQSale;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import com.odishakrushi.R;

public class AgrilProductSale extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_agril_product_sale);

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
       //getSupportActionBar().setTitle("Sale/Agril Product");
        getSupportActionBar().setTitle(getString(R.string.Sale)+"/"+getString(R.string.Agril_Product));

    }

    public void onClickPaddy(View view)
    {

        Intent intent = new Intent(AgrilProductSale.this, SaleList.class);//PopUpAgrilProductSale

        intent.putExtra("PROD_NAME","Paddy");
        intent.putExtra("#RANK","1");
        startActivity(intent);

    }

    public void onClickGreenGram(View view)
    {

        Intent intent = new Intent(AgrilProductSale.this, SaleList.class);//PopUpAgrilProductSale

        intent.putExtra("PROD_NAME","GreenGram");
        intent.putExtra("#RANK","1");
        startActivity(intent);

    }

    public void onClickBlackGram(View view)
    {

        Intent intent = new Intent(AgrilProductSale.this, SaleList.class);//PopUpAgrilProductSale

        intent.putExtra("PROD_NAME","BlackGram");
        intent.putExtra("#RANK","1");
        startActivity(intent);
    }

    public void onClickMustard(View view)
    {

        Intent intent = new Intent(AgrilProductSale.this, SaleList.class);//PopUpAgrilProductSale

        intent.putExtra("PROD_NAME","Mustard");
        intent.putExtra("#RANK","1");
        startActivity(intent);

    }

    public void onClickGroundnut(View view)
    {

        Intent intent = new Intent(AgrilProductSale.this, SaleList.class);//PopUpAgrilProductSale

        intent.putExtra("PROD_NAME","Groundnut");
        intent.putExtra("#RANK","1");
        startActivity(intent);

    }


    public void onClickVegetable(View view)
    {

        Intent intent = new Intent(AgrilProductSale.this, SaleList.class);//PopUpAgrilProductSale

        intent.putExtra("PROD_NAME","Vegetable");
        intent.putExtra("#RANK","1");
        startActivity(intent);

    }

    public void onClickFruit(View view) {

        Intent intent = new Intent(AgrilProductSale.this, SaleList.class);//PopUpAgrilProductSale

        intent.putExtra("PROD_NAME","Fruit");
        intent.putExtra("#RANK","1");
        startActivity(intent);
    }

    public void onClickFlower(View view)
    {

        Intent intent = new Intent(AgrilProductSale.this, SaleList.class);//PopUpAgrilProductSale

        intent.putExtra("PROD_NAME","Flower");
        intent.putExtra("#RANK","1");
        startActivity(intent);

    }


    public void onClickOthers(View view)
    {

        Intent intent = new Intent(AgrilProductSale.this, SaleList.class);//PopUpAgrilProductSale

        intent.putExtra("PROD_NAME","Others");
        intent.putExtra("#RANK","1");
        startActivity(intent);
    }
}
