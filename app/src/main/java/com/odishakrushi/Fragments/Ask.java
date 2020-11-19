package com.odishakrushi.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.odishakrushi.AskQSoilCW.AskQSoilCW;
import com.odishakrushi.Ask_Agriculture;
import com.odishakrushi.Ask_Fishery;
import com.odishakrushi.Ask_GovtSchemes;

import com.odishakrushi.Ask_Veterinary;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.R;
import com.odishakrushi.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class Ask extends Fragment  {


    LinearLayout agriculture,veterinary,fishery,govtschemes,soilcw;
    ImageView image_ad;
    AppCompatActivity context;

    String smallBannerUrl="";


    public Ask() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context= (AppCompatActivity) getActivity();

        /* RECEIVING PART */
        // Register the local broadcast
        LocalBroadcastManager.getInstance(context).registerReceiver(mBannerUrlReceiver,
                new IntentFilter("small-banner-url"));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ask, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // getActivity().setTitle("Ask Question");
        getActivity().setTitle(getString(R.string.Question_Answer));
        agriculture=getView().findViewById(R.id.agriculture);
        veterinary=getView().findViewById(R.id.veterinary);
        fishery=getView().findViewById(R.id.fishery);
        govtschemes=getView().findViewById(R.id.govtschemes);
        soilcw=getView().findViewById(R.id.soilcw);
        image_ad=getView().findViewById(R.id.image_ad);

        /**
         * @desc: Calling getSmallBannerAd Api for loading Small Banner Ad..
         */
        String str_zone_id= Preferences.getZone(getActivity());
        String str_group_id=Preferences.getGroupID(getActivity());
       Utils.getSmallBannerAd(getActivity(),str_zone_id,str_group_id,image_ad);


        agriculture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Ask_Agriculture.class);
                startActivity(intent);
            }
        });

        veterinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Ask_Veterinary.class);
                startActivity(intent);
            }
        });
        fishery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Ask_Fishery.class);//
                startActivity(intent);
            }
        });
        govtschemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Ask_GovtSchemes.class);//
                startActivity(intent);
            }
        });

        soilcw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AskQSoilCW.class);
                startActivity(intent);
            }
        });

        image_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!smallBannerUrl.equals("")){
                    Toast.makeText(context, smallBannerUrl, Toast.LENGTH_SHORT).show();
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
