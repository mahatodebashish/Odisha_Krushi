package com.odishakrushi.AskQService;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.WindowManager;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import com.odishakrushi.R;

public class ServicesAvail extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy



    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_services_avail);
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
       // getSupportActionBar().setTitle("Services Avail");
        getSupportActionBar().setTitle(getString(R.string.Services));


    }

    public void smallmachines(View view)
    {
        Intent intent = new Intent(ServicesAvail.this, BusinessmanList.class);//
        intent.putExtra("PROD_NAME",getString(R.string.Repairing_of_Small_Machines));
        // intent.putExtra("#RANK","1");
        startActivity(intent);
    }
    public void bigmachines(View view)
    {
        Intent intent = new Intent(ServicesAvail.this, BusinessmanList.class);//
        intent.putExtra("PROD_NAME",getString(R.string.Repairing_of_Big_Machines));
        // intent.putExtra("#RANK","1");
        startActivity(intent);
    }
    public void insurance(View view)
    {
        Intent intent = new Intent(ServicesAvail.this, BusinessmanList.class);//
        intent.putExtra("PROD_NAME",getString(R.string.Insurance));
        // intent.putExtra("#RANK","1");
        startActivity(intent);
    }
    public void loan(View view)
    {
        Intent intent = new Intent(ServicesAvail.this, BusinessmanList.class);//
        intent.putExtra("PROD_NAME",getString(R.string.Loan));
        // intent.putExtra("#RANK","1");
        startActivity(intent);
    }
    public void consultancy(View view)
    {
        Intent intent = new Intent(ServicesAvail.this, BusinessmanList.class);//
        intent.putExtra("PROD_NAME",getString(R.string.Consultancy));
        // intent.putExtra("#RANK","1");
        startActivity(intent);
    }
    public void executant(View view)
    {
        Intent intent = new Intent(ServicesAvail.this, BusinessmanList.class);//
        intent.putExtra("PROD_NAME",getString(R.string.Executant));
        // intent.putExtra("#RANK","1");
        startActivity(intent);
    }
    public void liasoning(View view)
    {
        Intent intent = new Intent(ServicesAvail.this, BusinessmanList.class);//
        intent.putExtra("PROD_NAME",getString(R.string.Liaisoning));
        // intent.putExtra("#RANK","1");
        startActivity(intent);
    }
    public void machinetestingdesigning(View view)
    {
        Intent intent = new Intent(ServicesAvail.this, BusinessmanList.class);//
        intent.putExtra("PROD_NAME",getString(R.string.Machine_testing_and_Designing));
        // intent.putExtra("#RANK","1");
        startActivity(intent);

    }
}
