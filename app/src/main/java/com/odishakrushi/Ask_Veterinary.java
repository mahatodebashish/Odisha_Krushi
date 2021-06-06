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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.AskQVeterinary.VBreed;
import com.odishakrushi.AskQVeterinary.VCultivationPractise;
import com.odishakrushi.AskQVeterinary.VDisease;
import com.odishakrushi.AskQVeterinary.VFeed;
import com.odishakrushi.AskQVeterinary.VHousing;
import com.odishakrushi.AskQVeterinary.VTraining;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.utils.Utils;

public class Ask_Veterinary extends AppCompatActivity {

    ImageView image_ad; String smallBannerUrl="";
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    LinearLayout cultivation_practice,breed,feed,disease,housing,training,insurance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask__veterinary);
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

       // getSupportActionBar().setTitle(getString(R.string.Question)+"/"+getString(R.string.Veterinary));
        getSupportActionBar().setTitle(getString(R.string.Veterinary));
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
        cultivation_practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Veterinary.this, VCultivationPractise.class));
            }
        });

        breed=findViewById(R.id.breed);
        breed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Veterinary.this, VBreed.class));
            }
        });
        feed=findViewById(R.id.feed);
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Veterinary.this, VFeed.class));
            }
        });
        disease=findViewById(R.id.disease);
        disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Veterinary.this, VDisease.class));
            }
        });
        housing=findViewById(R.id.housing);
        housing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Veterinary.this, VHousing.class));
            }
        });
        training=findViewById(R.id.training);
        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Veterinary.this, VTraining.class));
            }
        });image_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!smallBannerUrl.equals("")){
                    Toast.makeText(Ask_Veterinary.this, smallBannerUrl, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(smallBannerUrl));
                    startActivity(intent);
                }


            }
        });
        /*insurance=findViewById(R.id.insurance);
        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ask_Veterinary.this, VInsurance.class));
            }
        });*/
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
