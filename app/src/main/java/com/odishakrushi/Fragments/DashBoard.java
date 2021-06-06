package com.odishakrushi.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.R;
import com.odishakrushi.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoard extends Fragment {

    LinearLayout layoutAskQuestion,layoutAnswer,layoutPurchaseSaleHireRepair,
            layoutMyStory,layoutOpenForum,layoutKnowledgeBank,layoutMessage,layoutContactUs;

    String mystory="";
    String smallBannerUrl="";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    ImageView image_ad;
    AppCompatActivity context;
    public DashBoard() {
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
        return inflater.inflate(R.layout.fragment_dash_board, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Dashboard");
        layoutAskQuestion=getView().findViewById(R.id.layoutAskQuestion);
        layoutAnswer=getView().findViewById(R.id.layoutAnswer);
        layoutPurchaseSaleHireRepair=getView().findViewById(R.id.layoutPurchaseSaleHireRepair);
        layoutMyStory=getView().findViewById(R.id.layoutMyStory);
        layoutOpenForum=getView().findViewById(R.id.layoutOpenForum);
        layoutKnowledgeBank=getView().findViewById(R.id.layoutKnowledgeBank);
        layoutMessage=getView().findViewById(R.id.layoutMessage);
        layoutContactUs=getView().findViewById(R.id.layoutContactUs);
        image_ad=getView().findViewById(R.id.image_ad);

        /**
         *@desc: Calling getSmallBannerAd Api for loading Small Banner Ad..
        */
        String str_zone_id= Preferences.getZone(getActivity());
        String str_group_id=Preferences.getGroupID(getActivity());
        Utils.getSmallBannerAd(getActivity(),str_zone_id,str_group_id,image_ad);

        layoutAskQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                Intent intent = new Intent("filter_string");
                intent.putExtra("key", R.id.ask);
                // put your all data using put extra
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);*/

           /*     Ask ask = new Ask();
                Bundle bundle = new Bundle();
                //bundle.putString("image",banner);
                ask.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, ask);
                // fragmentTransaction.addToBackStack("DashBoard");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
            }
        });

        layoutAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   Intent intent = new Intent("filter_string");
                intent.putExtra("key", R.id.answers);
                // put your all data using put extra
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);*/


                /*AnswerList answerList = new AnswerList();
                Bundle bundle = new Bundle();
                //bundle.putString("image",banner);
                answerList.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, answerList);
                // fragmentTransaction.addToBackStack("DashBoard");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
            }
        });

       /* layoutPurchaseSaleHireRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            *//*    Intent intent = new Intent("filter_string");
                intent.putExtra("key", R.id.hire);
                // put your all data using put extra
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);*//*

                PurchaseSellHireRepair purchaseSellHireRepair = new PurchaseSellHireRepair();
                Bundle bundle = new Bundle();
                //bundle.putString("image",banner);
                purchaseSellHireRepair.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, purchaseSellHireRepair);
                // fragmentTransaction.addToBackStack("DashBoard");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });*/

     /*   layoutMyStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                *//*Intent intent = new Intent("filter_string");
                intent.putExtra("key", R.id.mystory);
                // put your all data using put extra
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);*//*

                MyStory myStory = new MyStory();
                Bundle bundle = new Bundle();
                //bundle.putString("image",banner);
                myStory.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, myStory);
                // fragmentTransaction.addToBackStack("DashBoard");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });*/

       /* layoutOpenForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               *//* Intent intent = new Intent("filter_string");
                intent.putExtra("key", R.id.openforum);
                // put your all data using put extra
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);*//*


                OpenForumList openForumList = new OpenForumList();
                Bundle bundle = new Bundle();
                //bundle.putString("image",banner);
                openForumList.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, openForumList);
                // fragmentTransaction.addToBackStack("DashBoard");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });*/

     /*   layoutKnowledgeBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          *//*      Intent intent = new Intent("filter_string");
                intent.putExtra("key", R.id.knowledgebank);
                // put your all data using put extra
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);*//*

                KnowledgeBank knowledgeBank = new KnowledgeBank();
                Bundle bundle = new Bundle();
                //bundle.putString("image",banner);
                knowledgeBank.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, knowledgeBank);
                // fragmentTransaction.addToBackStack("DashBoard");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });*/

      /*  layoutMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               *//* Intent intent = new Intent("filter_string");
                intent.putExtra("key", R.id.message);
                // put your all data using put extra
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);*//*

                ViewMessages viewMessages = new ViewMessages();
                Bundle bundle = new Bundle();
                //bundle.putString("image",banner);
                viewMessages.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, viewMessages);
                // fragmentTransaction.addToBackStack("DashBoard");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });*/

     /*   layoutContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              *//*  Intent intent = new Intent("contact-us");
                intent.putExtra("keyContactUs", "CONTACT_US");
                // put your all data using put extra
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);*//*

                Contact contactFragment = new Contact();
                Bundle bundle = new Bundle();
                //bundle.putString("image",banner);
                contactFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, contactFragment);
                // fragmentTransaction.addToBackStack("DashBoard");
                 fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });*/
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

