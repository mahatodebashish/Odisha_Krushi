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
import android.widget.LinearLayout;
import android.widget.Toast;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.AskQGovtSchemes.GSAsk;
import com.odishakrushi.AskQGovtSchemes.GSFishery;
import com.odishakrushi.AskQGovtSchemes.GSVeterinary;
import com.odishakrushi.AskQGovtSchemes.GS_scw;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.utils.Utils;

public class Ask_GovtSchemes extends AppCompatActivity {

    ImageView image_ad; String smallBannerUrl="";
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    LinearLayout agriculture,horticulture,veterinary,fishery,soilcw;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_ask__govt_schemes);
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

     //   getSupportActionBar().setTitle(getString(R.string.Question)+"/"+getString(R.string.Govt_Schemes));
        getSupportActionBar().setTitle(getString(R.string.Govt_Schemes));
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

        agriculture=findViewById(R.id.agriculture);
        horticulture=findViewById(R.id.horticulture);
        veterinary=findViewById(R.id.veterinary);
        fishery=findViewById(R.id.fishery);
        soilcw=findViewById(R.id.soilcw);

        agriculture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_GovtSchemes.this, GSAsk.class);
                intent.putExtra("SUBCATEGORY","1");
                startActivity(intent);
            }
        });

        horticulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_GovtSchemes.this, GSAsk.class);
                intent.putExtra("SUBCATEGORY","2");
                startActivity(intent);
            }
        });

        veterinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_GovtSchemes.this, GSVeterinary.class);
                intent.putExtra("SUBCATEGORY","3");
                startActivity(intent);
            }
        });
        fishery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_GovtSchemes.this, GSFishery.class);
                intent.putExtra("SUBCATEGORY","4");
                startActivity(intent);
            }
        });
        soilcw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_GovtSchemes.this, GS_scw.class);
                intent.putExtra("SUBCATEGORY","5");
                startActivity(intent);
            }
        });image_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!smallBannerUrl.equals("")){
                    Toast.makeText(Ask_GovtSchemes.this, smallBannerUrl, Toast.LENGTH_SHORT).show();
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
