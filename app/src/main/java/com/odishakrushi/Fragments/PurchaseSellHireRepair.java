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
import android.widget.Toast;

import com.odishakrushi.AskQRepair.RepairMachines;
import com.odishakrushi.AskQService.ServicesAvail;
import com.odishakrushi.Ask_Hire;
import com.odishakrushi.Ask_Purchase;
import com.odishakrushi.Ask_Sale;
import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.R;
import com.odishakrushi.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseSellHireRepair extends Fragment {


    ImageView img_purchase,img_sale,img_hire,txt_repair,txt_service,image_ad;
    String smallBannerUrl="";
    AppCompatActivity context;
    public PurchaseSellHireRepair() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context= (AppCompatActivity) getActivity(); /* RECEIVING PART */
        // Register the local broadcast
        LocalBroadcastManager.getInstance(context).registerReceiver(mBannerUrlReceiver,
                new IntentFilter("small-banner-url"));// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_purchase_sell_hire_repair, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.Purchase)+"/"+getString(R.string.Sale)+"/"+getString(R.string.Hire)+"/"+getString(R.string.Repair));

        img_purchase=getView().findViewById(R.id.id_purchase);
        img_sale=getView().findViewById(R.id.id_sale);
        img_hire=getView().findViewById(R.id.id_hire);
        txt_repair=getView().findViewById(R.id.id_repair);
        txt_service=getView().findViewById(R.id.id_service);
        img_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Ask_Purchase.class);// PurchaseFrom
                startActivity(intent);
            }
        });

        img_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), Ask_Sale.class);
                startActivity(intent);
            }
        });

        txt_repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), RepairMachines.class);
                startActivity(intent);

            }
        });
        img_hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Ask_Hire.class);
                startActivity(intent);

            }
        });
        txt_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ServicesAvail.class);
                startActivity(intent);

            }
        });

        image_ad=getView().findViewById(R.id.image_ad);

        /**
         * @desc: Calling getSmallBannerAd Api for loading Small Banner Ad..
         */
        String str_zone_id= Preferences.getZone(getActivity());
        String str_group_id=Preferences.getGroupID(getActivity());
        Utils.getSmallBannerAd(getActivity(),str_zone_id,str_group_id,image_ad);
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
