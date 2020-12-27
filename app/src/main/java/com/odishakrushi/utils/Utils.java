package com.odishakrushi.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.Farmer_ViewQuestion.AnswerList;
import com.odishakrushi.ProfilePic.ProfilePic;
import com.odishakrushi.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.odishakrushi.Config.getAd;

public class Utils {


    public static String small_banner="";
    public static String large_banner="";
    public static String large_url="";
    public static String small_url="";

    /**
     * @desc: Calling Get Ad API for loading Ad
     */
    public static void getAd(final Context context, String zone_id, String group_id, final ImageView goProDialogImage){

        AndroidNetworking.get(getAd+"?zone_id="+zone_id+"&group_id="+group_id)
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String s) {

                      /*  s="{\n" +
                                "   \"status\": true,\n" +
                                "   \"large\": false,\n" +
                                "   \"large_url\": \"https://unsplash.com/\",\n" +
                                "   \"small_banner\": \"Capture9.PNG\",\n" +
                                "   \"small_url\": \"http://odishakrushi.in/\",\n" +
                                "   \"message\": \"Record found\"\n" +
                                "}";*/


                        try {
                            JSONObject jsonObject=new JSONObject(s);

                            try {
                                if(jsonObject.getBoolean("large")==false){
                                    large_url=jsonObject.getString("large_url");
                                    large_banner="http://odishakrushi.in/uploads/advertise/"+jsonObject.getString("large");
                                }
                                else{
                                    JSONObject jsonObject1=jsonObject.getJSONObject("large");
                                    large_url=jsonObject1.getString("large_url");
                                    large_banner="http://odishakrushi.in/uploads/advertise/"+jsonObject1.getString("large_banner");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                                JSONObject jsonObject1=jsonObject.getJSONObject("large");
                                large_banner="http://odishakrushi.in/uploads/advertise/"+jsonObject1.getString("large_banner");
                                large_url=jsonObject.getString("large_url");

                            }


                            Display display =((WindowManager) context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
                            int width = display.getWidth();
                            int height=display.getHeight();

                            Picasso.with(context).load(large_banner).resize(width, height).error(R.drawable.imgad).into(goProDialogImage);


                            // " Local broadcast Manager " Sending part
                            Intent intent = new Intent("large-banner-url");
                            // You can also include some extra data.
                            intent.putExtra("large_url", large_url);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } catch (JSONException e) {
                            try {

                                // " Local broadcast Manager " Sending part
                                Intent intent = new Intent("large-banner-url");
                                // You can also include some extra data.
                                intent.putExtra("large_url", large_url);
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


                                JSONObject jsonObject1=new JSONObject(s);
                                String data=jsonObject1.optString("data");
                                if(data.equals(""))
                                    Picasso.with(context).load(R.drawable.imgad).error(R.drawable.imgad).into(goProDialogImage);

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        //  Toast.makeText(context, "Something Went wrong", Toast.LENGTH_SHORT).show();
                        Picasso.with(context).load(R.drawable.imgad).error(R.drawable.imgad).into(goProDialogImage);
                    }
                });

    }

    /**
     * @desc: Calling Get Ad API for loading Small Banner Ad
     */
    public static void getSmallBannerAd(final Context context, String zone_id, String group_id, final ImageView smallBannerImage){

        AndroidNetworking.get(getAd+"?zone_id="+zone_id+"&group_id="+group_id)
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String s) {

                     /*   s="{\n" +
                                "   \"status\": true,\n" +
                                "   \"large\": false,\n" +
                                "   \"large_url\":  \"https://unsplash.com\",\n" +
                                "   \"small_banner\": \"Capture9.PNG\",\n" +
                                "   \"small_url\": \"http://odishakrushi.in/\",\n" +
                                "   \"message\": \"Record found\"\n" +
                                "}";*/

                        try {
                            JSONObject jsonObject=new JSONObject(s);
                           /* JSONArray jsonArray=jsonObject.getJSONArray("small");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(0);
                                small_banner="http://odishakrushi.in/uploads/advertise/"+jsonObject1.getString("small_banner");
                                Picasso.with(context).load(small_banner).error(R.drawable.small_ad).into(smallBannerImage);
                            }*/

                            small_url=jsonObject.getString("small_url");
                            small_banner="http://odishakrushi.in/uploads/advertise/"+jsonObject.getString("small_banner");
                            Picasso.with(context).load(small_banner).error(R.drawable.imgad).into(smallBannerImage);



                            // " Local broadcast Manager " Sending part
                            Intent intent = new Intent("small-banner-url");
                            // You can also include some extra data.
                            intent.putExtra("small_url", small_url);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);



                        } catch (JSONException e) {
                            try {


                                // " Local broadcast Manager " Sending part
                                Intent intent = new Intent("small-banner-url");
                                // You can also include some extra data.
                                intent.putExtra("small_url", small_url);
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


                                JSONObject jsonObject1=new JSONObject(s);
                                String data=jsonObject1.optString("data");
                                if(data.equals(""))
                                    Picasso.with(context).load(R.drawable.small_ad).error(R.drawable.small_ad).into(smallBannerImage);


                            } catch (JSONException e1) {
                                e1.printStackTrace();

                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        //    Toast.makeText(context, "Something Went wrong", Toast.LENGTH_SHORT).show();
                        Picasso.with(context).load(R.drawable.small_ad).error(R.drawable.small_ad).into(smallBannerImage);

                    }
                });


    }

    public static void write(Context context,String filename, String s) {
        try {
            FileOutputStream fileout=context.openFileOutput(filename+".txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(s);
            outputWriter.close();

            //display file saved message
            /*Toast.makeText(context, "Offline response saved successfully!",
                    Toast.LENGTH_SHORT).show();
*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  static String  read(Context context, String filename){
        String s="";
        final int READ_BLOCK_SIZE = 100;
        try {
            FileInputStream fileIn=context.openFileInput(filename+".txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[READ_BLOCK_SIZE];

            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();

            //read
           /* Toast.makeText(context, "Offline response Reading & Showing!",
                    Toast.LENGTH_SHORT).show();*/




        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public void openProfilePicScreen(Context context)
    {
        Intent intent=new Intent(context, ProfilePic.class);
        context.startActivity(intent);

    }

    public static boolean hasNetwork(Context context){
       /* boolean have_WIFI= false;
        boolean have_MobileData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info:networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI"))if (info.isConnected())have_WIFI=true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE DATA"))if (info.isConnected())have_MobileData=true;
        }
        return have_WIFI||have_MobileData;*/


        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connec.getActiveNetworkInfo();

        boolean hasNetwork=false;
        // Check for network connections
        /*connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED*/

        hasNetwork= activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return hasNetwork;

    }


    public static String nullCheck(String str){
        if(str==null|str.equals("null")||str.equals("")||str.equals(" ")||str.isEmpty())
            str="";
        return str;
    }

    //checking for string if it has numeric values
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}
