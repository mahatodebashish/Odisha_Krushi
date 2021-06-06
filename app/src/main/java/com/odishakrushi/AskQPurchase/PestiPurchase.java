package com.odishakrushi.AskQPurchase;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.odishakrushi.R;

public class PestiPurchase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesti_purchase);

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

        getSupportActionBar().setTitle(getString(R.string.Purchase)+"/"+getString(R.string.Pesticides));
    }


      public void onClickCereal(View view)
      {
          Intent intent=new Intent(this,PestiFertiListPurchase.class);
          intent.putExtra("PROD_NAME", "Cereal");
          startActivity(intent);
      }

        public void onClickPulses(View view)
        {
            Intent intent=new Intent(this,PestiFertiListPurchase.class);
            intent.putExtra("PROD_NAME", "Pulses");
            startActivity(intent);
        }

    public void onClickOilseed(View view)
    {
        Intent intent=new Intent(this,PestiFertiListPurchase.class);
        intent.putExtra("PROD_NAME", "Oilseed");
        startActivity(intent);
    }

    public void onClickVegetable(View view)
    {
        Intent intent=new Intent(this,PestiFertiListPurchase.class);
        intent.putExtra("PROD_NAME", "Vegetable");
        startActivity(intent);
    }

    public void onClickFlower(View view)
    {
        Intent intent=new Intent(this,PestiFertiListPurchase.class);
        intent.putExtra("PROD_NAME", "Flower");
        startActivity(intent);
    }
    public void onClickFodder(View view)
    {
        Intent intent=new Intent(this,PestiFertiListPurchase.class);
        intent.putExtra("PROD_NAME", "Fodder");
        startActivity(intent);
    }
    public void onClickCotton(View view)
    {
        Intent intent=new Intent(this,PestiFertiListPurchase.class);
        intent.putExtra("PROD_NAME", "Cotton");
        startActivity(intent);
    }
    public void onClickSpices(View view)
    {
        Intent intent=new Intent(this,PestiFertiListPurchase.class);
        intent.putExtra("PROD_NAME", "Spices");
        startActivity(intent);
    }
}
