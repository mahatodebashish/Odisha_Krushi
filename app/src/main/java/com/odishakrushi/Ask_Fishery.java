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
import android.widget.LinearLayout;
import android.widget.Toast;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.AskQFishery.FFish;
import com.odishakrushi.AskQFishery.FOthers;
import com.odishakrushi.AskQFishery.FPrawn;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.utils.Utils;

public class Ask_Fishery extends AppCompatActivity {

    ImageView image_ad; String smallBannerUrl="";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    LinearLayout cultivation_practice,feed, disease, processing, training, insurance , pondmanagement;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_ask__fishery);
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

       // getSupportActionBar().setTitle(getString(R.string.Question)+"/"+getString(R.string.Fishery));
        getSupportActionBar().setTitle(getString(R.string.Fishery));
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

        cultivation_practice=findViewById(R.id.cultivation_practice);
        feed=findViewById(R.id.feed);
        disease=findViewById(R.id.disease);
        processing=findViewById(R.id.processing);
        training=findViewById(R.id.training);
        insurance=findViewById(R.id.insurance);
        pondmanagement=findViewById(R.id.pondmanagement);

        cultivation_practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_Fishery.this, FFish.class);
                intent.putExtra("SUBCATEGORY","1");
                startActivity(intent);
            }
        });
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_Fishery.this, FFish.class);
                intent.putExtra("SUBCATEGORY","2");
                startActivity(intent);
            }
        });
        disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_Fishery.this, FFish.class);
                intent.putExtra("SUBCATEGORY","3");
                startActivity(intent);
            }
        });

        processing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_Fishery.this, FFish.class);
                intent.putExtra("SUBCATEGORY","4");
                startActivity(intent);
            }
        });
        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_Fishery.this, FFish.class);
                intent.putExtra("SUBCATEGORY","5");
                startActivity(intent);
            }
        });
        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_Fishery.this, FOthers.class);
                intent.putExtra("SUBCATEGORY","6");
                startActivity(intent);
            }
        });
        pondmanagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ask_Fishery.this, FOthers.class);
                intent.putExtra("SUBCATEGORY","7");
                startActivity(intent);
            }
        });

        image_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!smallBannerUrl.equals("")){
                    Toast.makeText(Ask_Fishery.this, smallBannerUrl, Toast.LENGTH_SHORT).show();
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
