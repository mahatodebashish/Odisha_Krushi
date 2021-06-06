package com.odishakrushi.AskQPurchase;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.R;

public class MachineryPurchase extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    TextView txtnewmachinery,txtoldmachinery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machinery_purchase);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.Purchase) + "/" + getString(R.string.Machinery));
    }

    public void onClickTractor(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Tractor");
        startActivity(intent);
        //startActivity(new Intent(this,ProductListPurchase.class));
    }

    public void onClickPowerTiller(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "PowerTiller");
        startActivity(intent);
      //  startActivity(new Intent(this,ProductListPurchase.class));
    }

    public void onClickRotavator(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Rotavator");
        startActivity(intent);
        //startActivity(new Intent(this,ProductListPurchase.class));
    }

    public void onClickPlough(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Plough");
        startActivity(intent);
       // startActivity(new Intent(this,ProductListPurchase.class));
    }
    public void onClickRidger(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Ridger");
        startActivity(intent);
        //startActivity(new Intent(this,ProductListPurchase.class));
    }

    public void onClickLeveller(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Leveller");
        startActivity(intent);
       // startActivity(new Intent(this,ProductListPurchase.class));
    }


    public void onClickSeedDrill(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "SeedDrill");
        startActivity(intent);
       // startActivity(new Intent(this,ProductListPurchase.class));
    }

    public void onClickTransplanter(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Transplanter");
        startActivity(intent);
       // startActivity(new Intent(this,ProductListPurchase.class));
    }

    public void onClickWeeder(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Weeder");
        startActivity(intent);
       // startActivity(new Intent(this,ProductListPurchase.class));
    }
    public void onClickSprayer(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Sprayer");
        startActivity(intent);
        //startActivity(new Intent(this,ProductListPurchase.class));
    }
    public void onClickReaper(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Reaper");
        startActivity(intent);
        //startActivity(new Intent(this,ProductListPurchase.class));
    }
    public void onClickThresher(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Thresher");
        startActivity(intent);
        //startActivity(new Intent(this,ProductListPurchase.class));
    }

    public void onClickCleaner(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Cleaner");
        startActivity(intent);
       // startActivity(new Intent(this,ProductListPurchase.class));
    }
    public void onClickGrader(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Grader");
        startActivity(intent);
      //  startActivity(new Intent(this,ProductListPurchase.class));
    }
    public void onClickCombine(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Combine");
        startActivity(intent);
        //startActivity(new Intent(this,ProductListPurchase.class));
    }
    public void onClickPumps(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Pumps");
        startActivity(intent);
       // startActivity(new Intent(this,ProductListPurchase.class));
    }
    public void onClickParboilingUnit(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "ParboilingUnit");
        startActivity(intent);
       // startActivity(new Intent(this,ProductListPurchase.class));
    }
    public void onClickRiceMill(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "RiceMill");
        startActivity(intent);
       // startActivity(new Intent(this,ProductListPurchase.class));
    }
    public void onClickDecorticator(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "Decorticator");
        startActivity(intent);
       // startActivity(new Intent(this,ProductListPurchase.class));
    }
    public void onClickToolsForFruitAndVegetable(View view)
    {
        Intent intent=new Intent(this,ProductListPurchase.class);
        intent.putExtra("PROD_NAME", "ToolsForFruitAndVegetable");
        startActivity(intent);
        //startActivity(new Intent(this,ProductListPurchase.class));
    }
 /*   public void onClickOthers(View view)
    {*//*
        Intent intent=new Intent(this,PopUpMachineTool.class);
        intent.putExtra("MACHINE_NAME", "Others");
        intent.putExtra("CATEGORY_NAME", "Purchase");
        startActivity(intent);*//*
        startActivity(new Intent(this,ProductListPurchase.class));
    }*/
}
