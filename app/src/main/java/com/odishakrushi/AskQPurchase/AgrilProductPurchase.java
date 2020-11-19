package com.odishakrushi.AskQPurchase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.odishakrushi.AskQSale.SaleList;
import com.odishakrushi.R;

public class AgrilProductPurchase extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_agril_product_purchase);

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
       // getSupportActionBar().setTitle("Purchase/Agril Product");
        getSupportActionBar().setTitle(getString(R.string.Purchase)+"/"+getString(R.string.Agril_Product));

    }

    public void onClickPaddy(View view)
    {

        Intent intent = new Intent(AgrilProductPurchase.this, ProductListPurchase.class);//
        intent.putExtra("PROD_NAME","Paddy");
       // intent.putExtra("#RANK","1");
        startActivity(intent);

    }

    public void onClickGreenGram(View view)
    {

        Intent intent = new Intent(AgrilProductPurchase.this, ProductListPurchase.class);
        intent.putExtra("PROD_NAME","GreenGram");
      //  intent.putExtra("#RANK","2");
        startActivity(intent);

    }

    public void onClickBlackGram(View view)
    {

        Intent intent = new Intent(AgrilProductPurchase.this, ProductListPurchase.class);
        intent.putExtra("PROD_NAME","BlackGram");
      //  intent.putExtra("#RANK","3");
        startActivity(intent);

    }

    public void onClickMustard(View view)
    {

        Intent intent = new Intent(AgrilProductPurchase.this, ProductListPurchase.class);
        intent.putExtra("PROD_NAME","Mustard");
       // intent.putExtra("#RANK","4");
        startActivity(intent);

    }

    public void onClickGroundnut(View view)
    {

        Intent intent = new Intent(AgrilProductPurchase.this, ProductListPurchase.class);
        intent.putExtra("PROD_NAME","Groundnut");
       // intent.putExtra("#RANK","5");
        startActivity(intent);

    }


    public void onClickVegetable(View view)
    {

        Intent intent = new Intent(AgrilProductPurchase.this, ProductListPurchase.class);
        intent.putExtra("PROD_NAME","Vegetable");
        //intent.putExtra("#RANK","6");
        startActivity(intent);

    }

    public void onClickFruit(View view)
    {

        Intent intent = new Intent(AgrilProductPurchase.this, ProductListPurchase.class);
        intent.putExtra("PROD_NAME","Fruit");
       // intent.putExtra("#RANK","7");
        startActivity(intent);

    }

    public void onClickFlower(View view)
    {

        Intent intent = new Intent(AgrilProductPurchase.this, ProductListPurchase.class);
        intent.putExtra("PROD_NAME","Flower");
        //intent.putExtra("#RANK","8");
        startActivity(intent);

    }


    public void onClickOthers(View view)
    {

        Intent intent = new Intent(AgrilProductPurchase.this, ProductListPurchase.class);
        intent.putExtra("PROD_NAME","Others");
     //   intent.putExtra("#RANK","9");
        startActivity(intent);

    }

}
