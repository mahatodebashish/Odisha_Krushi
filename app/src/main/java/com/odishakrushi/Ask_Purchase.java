package com.odishakrushi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.AskQPurchase.AgrilProductPurchase;
import com.odishakrushi.AskQPurchase.FertiPurchase;
import com.odishakrushi.AskQPurchase.FishPurchase;
import com.odishakrushi.AskQPurchase.MachineryPurchase;
import com.odishakrushi.AskQPurchase.MedicineFeedPurchase;
import com.odishakrushi.AskQPurchase.OtherItemPurchase;
import com.odishakrushi.AskQPurchase.PestiPurchase;
import com.odishakrushi.AskQPurchase.VeterinaryItemPurchase;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.utils.Utils;

public class Ask_Purchase extends AppCompatActivity {
    String smallBannerUrl="";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    ImageView image_ad;
    ImageView img_agril_product,img_machinery,img_veterinary,img_fish,img_other,img_medicine_feed,img_pesticides,img_fertilisers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_ask__purchase);
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
       // getSupportActionBar().setTitle("Purchase");// Ask
        getSupportActionBar().setTitle(getString(R.string.Purchase));

        /* RECEIVING PART */
        // Register the local broadcast
        LocalBroadcastManager.getInstance(this).registerReceiver(mBannerUrlReceiver,
                new IntentFilter("small-banner-url"));

        image_ad=(ImageView) findViewById(R.id.image_ad);
        img_agril_product=findViewById(R.id.id_agril_product);
        img_machinery=findViewById(R.id.id_machinery);
        img_veterinary=findViewById(R.id.id_veterinary);
        img_fish=findViewById(R.id.id_fish);
        img_other=findViewById(R.id.id_other);
        img_medicine_feed=findViewById(R.id.id_medicine_feed);
        img_pesticides=findViewById(R.id.id_pesticides);
        img_fertilisers=findViewById(R.id.id_fertilisers);
        image_ad=findViewById(R.id.image_ad);

        /**
         * @desc: Calling getSmallBannerAd Api for loading Small Banner Ad..
         */
        String str_zone_id= Preferences.getZone(this);
        String str_group_id=Preferences.getGroupID(this);
        Utils.getSmallBannerAd(this,str_zone_id,str_group_id,image_ad);

        img_agril_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_Purchase.this, AgrilProductPurchase.class);
                startActivity(intent);
            }
        });

        img_machinery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_Purchase.this, MachineryPurchase.class);
                startActivity(intent);
            }
        });

        img_veterinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_Purchase.this, VeterinaryItemPurchase.class);// SAME ACTIVITY CALLING FOR FARMER PURCHASE
                startActivity(intent);
            }
        });

        img_fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_Purchase.this, FishPurchase.class);// SAME ACTIVITY CALLING FOR FARMER PURCHASE
                startActivity(intent);
            }
        });
        img_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_Purchase.this, OtherItemPurchase.class);// SAME ACTIVITY CALLING FOR FARMER PURCHASE
                startActivity(intent);
            }
        });
        img_medicine_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_Purchase.this, MedicineFeedPurchase.class);// SAME ACTIVITY CALLING FOR FARMER PURCHASE
                startActivity(intent);
            }
        });

        img_pesticides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_Purchase.this, PestiPurchase.class);// SAME ACTIVITY CALLING FOR FARMER PURCHASE
                startActivity(intent);
            }
        });

        img_fertilisers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_Purchase.this, FertiPurchase.class);// SAME ACTIVITY CALLING FOR FARMER PURCHASE
                startActivity(intent);
            }
        });

        image_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!smallBannerUrl.equals("")){
                    Toast.makeText(Ask_Purchase.this, smallBannerUrl, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(smallBannerUrl));
                    startActivity(intent);
                }


            }
        });

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