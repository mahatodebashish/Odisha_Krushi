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
import com.odishakrushi.AskQAgriculture.CultivationPractise;
import com.odishakrushi.AskQAgriculture.Disease_Pest;
import com.odishakrushi.AskQAgriculture.Fertiliser;
import com.odishakrushi.AskQAgriculture.Insurance;
import com.odishakrushi.AskQAgriculture.Irrigation;
import com.odishakrushi.AskQAgriculture.Machine_Tools;
import com.odishakrushi.AskQAgriculture.Processing;
import com.odishakrushi.AskQAgriculture.Seed;
import com.odishakrushi.AskQAgriculture.Soil;
import com.odishakrushi.AskQAgriculture.Training;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.utils.Utils;

public class Ask_Agriculture extends AppCompatActivity {

    ImageView image_ad; String smallBannerUrl="";
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    LinearLayout cultivationpractice,soil,seed,fertiliser,disease_pest,irrigation,insurance,training,processing,machinery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_ask__agriculture);
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

       // getSupportActionBar().setTitle(getString(R.string.Question)+"/"+getString(R.string.agriculture));
        getSupportActionBar().setTitle(getString(R.string.agriculture));
        /* RECEIVING PART */
        // Register the local broadcast
        LocalBroadcastManager.getInstance(this).registerReceiver(mBannerUrlReceiver,
                new IntentFilter("small-banner-url"));
        image_ad=findViewById(R.id.image_ad);

        /**
         * @desc: Calling igetSmallBannerAd Api for loading Small Banner Ad..
         */
        String str_zone_id= Preferences.getZone(this);
        String str_group_id=Preferences.getGroupID(this);
        Utils.getSmallBannerAd(this,str_zone_id,str_group_id,image_ad);

        cultivationpractice= findViewById(R.id.cultivationpractice);
        cultivationpractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Agriculture.this, CultivationPractise.class));
            }
        });
        soil= findViewById(R.id.soil);
        soil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Agriculture.this, Soil.class));
            }
        });
        seed= findViewById(R.id.seed);
        seed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Agriculture.this, Seed.class));
            }
        });
        fertiliser=findViewById(R.id.fertiliser);
        fertiliser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Agriculture.this, Fertiliser.class));
            }
        });
        disease_pest=findViewById(R.id.disease_pest);
        disease_pest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Agriculture.this, Disease_Pest.class));
            }
        });
        irrigation=findViewById(R.id.irrigation);
        irrigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Agriculture.this, Irrigation.class));
            }
        });
        insurance=findViewById(R.id.insurance);
        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Agriculture.this, Insurance.class));
            }
        });

        training=findViewById(R.id.training);
        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Agriculture.this, Training.class));
            }
        });
        processing=findViewById(R.id.processing);
        processing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Agriculture.this, Processing.class));
            }
        });
        machinery=findViewById(R.id.machinery);
        machinery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Agriculture.this, Machine_Tools.class));
            }
        });image_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!smallBannerUrl.equals("")){
                    Toast.makeText(Ask_Agriculture.this, smallBannerUrl, Toast.LENGTH_SHORT).show();
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
