package com.odishakrushi.AskQPurchase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.R;

public class VeterinaryItemPurchase extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    Button cowID,chickID,goatID,buffaloID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_veterinary_item_purchase);
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
       // getSupportActionBar().setTitle("Purchase/Veterinary Item");
        getSupportActionBar().setTitle(getString(R.string.Purchase)+"/"+getString(R.string.Veterinary_Item));

        cowID=findViewById(R.id.cowID);
        chickID=findViewById(R.id.chickID);
        goatID=findViewById(R.id.goatID);
        buffaloID=findViewById(R.id.buffaloID);
    }


    public void onClickCow(View view)
    {
        /*Intent intent=new Intent(this,PopUpPurchaseAnimal.class); //PopUpVAnimal
        intent.putExtra("ANIMAL", "Cow");
        intent.putExtra("CATEGORY_NAME", "Purchase");
        startActivity(intent);*/

         Intent intent =new Intent(this,ProductListPurchase.class);
         intent.putExtra("PROD_NAME","Cow");
         startActivity(intent);
    }

    public void onClickChick(View view) {
      /*  Intent intent=new Intent(this,PopUpPurchaseAnimal.class);
        intent.putExtra("ANIMAL", "Chick");
        intent.putExtra("CATEGORY_NAME", "Purchase");
        startActivity(intent);*/
        Intent intent = new Intent(this, ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Chick");
        startActivity(intent);
    }
    public void onClickGoat(View view)
    {
       /* Intent intent=new Intent(this,PopUpPurchaseAnimal.class);
        intent.putExtra("ANIMAL", "Goat");
        intent.putExtra("CATEGORY_NAME", "Purchase");
        startActivity(intent);*/
        Intent intent = new Intent(this, ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Goat");
        startActivity(intent);
    }

    public void onClickBuffalo(View view)
    {
       /* Intent intent=new Intent(this,PopUpPurchaseAnimal.class);
        intent.putExtra("ANIMAL", "Buffalo");
        intent.putExtra("CATEGORY_NAME", "Purchase");
        startActivity(intent);*/
        Intent intent = new Intent(this, ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Buffalo");
        startActivity(intent);
    }
    public void onClickOther(View view)
    {
       /* Intent intent=new Intent(this,PopUpPurchaseAnimal.class);
        intent.putExtra("ANIMAL", "Buffalo");
        intent.putExtra("CATEGORY_NAME", "Purchase");
        startActivity(intent);*/
        Intent intent = new Intent(this, ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Others");
        startActivity(intent);
    }
}
