package com.odishakrushi.AskQService;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.Adapter.AdapterServicesAvailable;
import com.odishakrushi.Adapter.MyAdapter;
import com.odishakrushi.AskQPurchase.AgrilProductPurchase;
import com.odishakrushi.AskQPurchase.ProductListPurchase;
import com.odishakrushi.Model.ListItem;
import com.odishakrushi.Model.ListServiceAvailable;
import com.odishakrushi.R;
import com.odishakrushi.SignUpFarmer;

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
