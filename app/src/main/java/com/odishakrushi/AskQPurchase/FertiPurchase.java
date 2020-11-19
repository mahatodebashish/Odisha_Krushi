package com.odishakrushi.AskQPurchase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.odishakrushi.R;

public class FertiPurchase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ferti_purchase);

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

        getSupportActionBar().setTitle(getString(R.string.Purchase)+"/"+getString(R.string.Fertiliser));
    }

    public void onClicknpk(View view)
    {
        Intent intent=new Intent(this,PestiFertiListPurchase.class);
        intent.putExtra("PROD_NAME", "NPK_Compound");
        startActivity(intent);
    }
    public void onClickMicronutrients(View view)
    {
        Intent intent=new Intent(this,PestiFertiListPurchase.class);
        intent.putExtra("PROD_NAME", "Micronutrients");
        startActivity(intent);
    }
}
