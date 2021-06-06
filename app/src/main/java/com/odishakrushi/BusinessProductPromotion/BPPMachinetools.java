package com.odishakrushi.BusinessProductPromotion;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.R;

public class BPPMachinetools extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_bppmachinetools);

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

    public void onClickTractor(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Tractor");

        startActivity(intent);
    }

    public void onClickPowerTiller(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Power Tiller");

        startActivity(intent);
    }

    public void onClickRotavator(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Rotavator");

        startActivity(intent);
    }

    public void onClickPlough(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Plough");

        startActivity(intent);
    }
    public void onClickRidger(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Ridger");

        startActivity(intent);
    }

    public void onClickLeveller(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Leveller");

        startActivity(intent);

    }


    public void onClickSeedDrill(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "SeedDrill");
        startActivity(intent);

    }

    public void onClickTransplanter(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Transplanter");
        startActivity(intent);

    }

    public void onClickWeeder(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Weeder");
        startActivity(intent);

    }
    public void onClickSprayer(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Sprayer");
        startActivity(intent);

    }
    public void onClickReaper(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Reaper");
        startActivity(intent);

    }
    public void onClickThresher(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Thresher");
        startActivity(intent);

    }

    public void onClickCleaner(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Cleaner");
        startActivity(intent);

    }
    public void onClickGrader(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Grader");
        startActivity(intent);

    }
    public void onClickCombine(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Combine");
        startActivity(intent);

    }
    public void onClickPumps(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Pumps");
        startActivity(intent);

    }
    public void onClickParboilingUnit(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Parboiling Unit");
        startActivity(intent);

    }
    public void onClickRiceMill(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Rice Mill");
        startActivity(intent);

    }
    public void onClickDecorticator(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Decorticator");
        startActivity(intent);

    }
    public void onClickToolsForFruitAndVegetable(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Tools For Fruit And Vegetable");
        startActivity(intent);

    }
    public void onClickOthers(View view)
    {
        Intent intent=new Intent(this,PopUpBPPMachinetool.class);
        intent.putExtra("MACHINE_NAME", "Others");
        startActivity(intent);

    }
}
