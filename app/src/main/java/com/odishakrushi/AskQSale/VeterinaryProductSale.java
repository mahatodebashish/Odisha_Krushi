package com.odishakrushi.AskQSale;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.AskQPurchase.PopUpPurchaseAnimal;
import com.odishakrushi.R;

public class VeterinaryProductSale extends AppCompatActivity {
    Button cowID,chickID,goatID,buffaloID;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setContentView(R.layout.activity_veterinary_product_sale);
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
        //getSupportActionBar().setTitle("Sale/ Veterinary Item ");
        getSupportActionBar().setTitle(getString(R.string.Sale)+"/"+getString(R.string.Veterinary_Item));

        cowID=findViewById(R.id.cowID);
        chickID=findViewById(R.id.chickID);
        goatID=findViewById(R.id.goatID);
        buffaloID=findViewById(R.id.buffaloID);
    }

    public void onClickCow(View view)
    {
        Intent intent=new Intent(this,SaleListVeterinary.class); // PopUpSaleAnimal

        intent.putExtra("PROD_NAME","Cow");
        intent.putExtra("#RANK","3");
        startActivity(intent);
    }

    public void onClickChick(View view)
    {
        Intent intent=new Intent(this,SaleListVeterinary.class); // PopUpSaleAnimal

        intent.putExtra("PROD_NAME","Chick");
        intent.putExtra("#RANK","3");
        startActivity(intent);
    }
    public void onClickGoat(View view)
    {
        Intent intent=new Intent(this,SaleListVeterinary.class); // PopUpSaleAnimal

        intent.putExtra("PROD_NAME","Goat");
        intent.putExtra("#RANK","3");
        startActivity(intent);
    }

    public void onClickBuffalo(View view)
    {
        Intent intent=new Intent(this,SaleListVeterinary.class); // PopUpSaleAnimal

        intent.putExtra("PROD_NAME","Buffalo");
        intent.putExtra("#RANK","3");
        startActivity(intent);
    }

    public void onClickOthers(View view)
    {
        Intent intent=new Intent(this,SaleListVeterinary.class); // PopUpSaleAnimal

        intent.putExtra("PROD_NAME","Others");
        intent.putExtra("#RANK","3");
        startActivity(intent);
    }
}
