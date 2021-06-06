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

public class MachinerySale extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_machinery_sale);

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
        //getSupportActionBar().setTitle("Sale/Machinery");
        getSupportActionBar().setTitle(getString(R.string.Sale)+"/"+getString(R.string.Machinery));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onClickTractor(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Tractor");
        intent.putExtra("#RANK","2");
        startActivity(intent);
    }

    public void onClickPowerTiller(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "PowerTiller");
        intent.putExtra("#RANK","2");
        startActivity(intent);
    }

    public void onClickRotavator(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Rotavator");
        intent.putExtra("#RANK","2");
        startActivity(intent);
    }

    public void onClickPlough(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Plough");
        intent.putExtra("#RANK","2");
        startActivity(intent);
    }
    public void onClickRidger(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Ridger");
        intent.putExtra("#RANK","2");
        startActivity(intent);
    }

    public void onClickLeveller(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Leveller");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }


    public void onClickSeedDrill(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "SeedDrill");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }

    public void onClickTransplanter(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Transplanter");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }

    public void onClickWeeder(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Weeder");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }
    public void onClickSprayer(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Sprayer");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }
    public void onClickReaper(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Reaper");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }
    public void onClickThresher(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Thresher");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }

    public void onClickCleaner(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Cleaner");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }
    public void onClickGrader(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Grader");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }
    public void onClickCombine(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Combine");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }
    public void onClickPumps(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Pumps");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }
    public void onClickParboilingUnit(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "ParboilingUnit");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }
    public void onClickRiceMill(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "RiceMill");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }
    public void onClickDecorticator(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Decorticator");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }
    public void onClickToolsForFruitAndVegetable(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "ToolsForFruitAndVegetable");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }
    public void onClickOthers(View view)
    {
        Intent intent=new Intent(this,SaleListMachineTools.class);//
        intent.putExtra("PROD_NAME", "Others");
        intent.putExtra("#RANK","2");
        startActivity(intent);

    }
}
