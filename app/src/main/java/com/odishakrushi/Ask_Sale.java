package com.odishakrushi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.AskQSale.AgrilProductSale;
import com.odishakrushi.AskQSale.FishProductSale;
import com.odishakrushi.AskQSale.MachinerySale;
import com.odishakrushi.AskQSale.MedicineFeedSale;
import com.odishakrushi.AskQSale.OtherProducttoSale;
import com.odishakrushi.AskQSale.SeePurchaseData;
import com.odishakrushi.AskQSale.VeterinaryProductSale;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.utils.Utils;

public class Ask_Sale extends AppCompatActivity {

    ImageView image_ad; String smallBannerUrl="";
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_ask__sale);
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
      //  getSupportActionBar().setTitle("Sale"); //Ask a
        getSupportActionBar().setTitle(getString(R.string.Sale));

        /* RECEIVING PART */
        // Register the local broadcast
        LocalBroadcastManager.getInstance(this).registerReceiver(mBannerUrlReceiver,
                new IntentFilter("small-banner-url"));

        image_ad=findViewById(R.id.image_ad);

        /**
         * @desc: Calling getSmallBannerAd Api for loading Small Banner Ad..
         */
        String str_zone_id= Preferences.getZone(this);
        String str_group_id=Preferences.getGroupID(this);
        Utils.getSmallBannerAd(this,str_zone_id,str_group_id,image_ad);
        image_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!smallBannerUrl.equals("")){
                    Toast.makeText(Ask_Sale.this, smallBannerUrl, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(smallBannerUrl));
                    startActivity(intent);
                }


            }
        });
    }



    public void onClickAgrilProduct(View view)
    {
        Intent intent=new Intent(this, AgrilProductSale.class);
        startActivity(intent);
    }
    public void onClickMachineSale(View view)
    {
        Intent intent=new Intent(this, MachinerySale.class);
        startActivity(intent);
    }
    public void onClickVeterinaryProduct(View view)
    {
        Intent intent=new Intent(this, VeterinaryProductSale.class);
        startActivity(intent);
    }
    public void onClickFishSale(View view)
    {
        Intent intent=new Intent(this, FishProductSale.class);
        startActivity(intent);
    }


    public void onClickOtherProductSale(View view)
    {
        Intent intent=new Intent(this, OtherProducttoSale.class);
        startActivity(intent);
    }

    public void onClickMedicineFeedProductSale(View view)
    {
        Intent intent=new Intent(this, MedicineFeedSale.class);
        startActivity(intent);
    }


    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mBannerUrlReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            smallBannerUrl = intent.getStringExtra("small_url");

        }
    };
}
