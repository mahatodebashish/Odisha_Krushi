package com.odishakrushi.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.odishakrushi.PreferenceManager.Preferences;
import com.odishakrushi.R;
import com.odishakrushi.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class Contact extends Fragment {


    String smallBannerUrl="";
    LinearLayout location,website;
    ImageView call,whatsapp,telegram,email;
    //TextView txtemail;
    String strEmail="mail@odishakrushi.in";
    ImageButton facebook,youtube,instagram,twitter;
    TextView phone;
    ImageView image_ad;
    AppCompatActivity context;
    Preferences preferences;
    public Contact() {
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

        View rootView =inflater.inflate(R.layout.fragment_contact, container, false);


        // Inflate the layout for this fragment
        return  rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.Contact_Us));
        Log.d( "onViewCreated","Contact US");

        String user_group_id=Preferences.getGroupID(getContext());

        phone=getView().findViewById(R.id.phone);
        email=getView().findViewById(R.id.email);
       // txtemail=getView().findViewById(R.id.txtemail);

       

        //Setting dynamic email id as per user group
        if(!user_group_id.equals("") && Integer.parseInt(user_group_id)==4)
            strEmail="business@odishakrushi.in";

        else
            strEmail="mail@odishakrushi.in";


       // txtemail.setText(strEmail);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+strEmail)));
            }
        });

        call=getView().findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);//ACTION_DIAL
                intent.setData(Uri.parse("tel:" + "8763865936"));//"08763865936"
                startActivity(intent);
            }
        });
        location=getView().findViewById(R.id.location);
        website=getView().findViewById(R.id.website);
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.odishakrushi.in"));
                startActivity(intent);
            }
        });

        image_ad=getView().findViewById(R.id.image_ad);

        facebook=getView().findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Odisha-Krushi-105172567815632/"));
                startActivity(intent);
            }
        });

        whatsapp=getView().findViewById(R.id.whatsapp);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intentMessageWhatsapp("Hi ! This is Odisha Krushi",context);
            }
        });
        telegram=getView().findViewById(R.id.telegram);
        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentMessageTelegram("Hi ! This is Odisha Krushi",context);
            }
        });

        youtube=getView().findViewById(R.id.youtube);
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCVg-AtT8kaUxqvknBf6-qJA?view_as=subscriber"));
                startActivity(intent);
            }
        });
        instagram=getView().findViewById(R.id.instagram);
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/odishakrushi"));
                startActivity(intent);
            }
        });
        twitter=getView().findViewById(R.id.twitter);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/odishakrushi"));
                startActivity(intent);
            }
        });

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

    void intentMessageTelegram(String msg, Context context)
    {
        final String appName = "org.telegram.messenger";
        final boolean isAppInstalled = isAppAvailable(context, appName);
        if (isAppInstalled)
        {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            myIntent.setPackage(appName);
            myIntent.putExtra(Intent.EXTRA_TEXT, msg);//
            this.startActivity(Intent.createChooser(myIntent, "Share with"));
        }
        else
        {
            Toast.makeText(context, "Telegram not Installed", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=org.telegram.messenger"));
            startActivity(i);
        }
    }

    void intentMessageWhatsapp(String msg, Context context)
    {
        final String appName = "com.whatsapp";
        final boolean isAppInstalled = isAppAvailable(context, appName);
        if (isAppInstalled)
        {
            Uri uri = Uri.parse("smsto:" + "8763865936");
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            startActivity(Intent.createChooser(i, msg));
        }
        else
        {
            Toast.makeText(context, "Whatsapp not Installed", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp"));
            startActivity(i);
        }
    }
    public static boolean isAppAvailable(Context context, String appName)
    {
        PackageManager pm = context.getPackageManager();
        try
        {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
