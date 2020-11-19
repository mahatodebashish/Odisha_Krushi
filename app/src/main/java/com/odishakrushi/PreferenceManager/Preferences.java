package com.odishakrushi.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {



    // Editor reference for Shared preferences
    static SharedPreferences.Editor editor;

    Context context;
    // Shared pref mode
    static int PRIVATE_MODE = 0;


    // Sharedpref file name
    private static final String PREFER_NAME = "ZonePref";

    // User name (make variable public to access from outside)
    private static final String KEY_ZONE_ID = "zone_id";
    private static final String KEY_GROUP_ID = "group_id";
    private static final String KEY_DISTRICT_ID = "district_id";

    // Sharedpref file name
    private static final String PREFER_IS_LOGGEDIN = "isLoggedIn";
    private static final String IS_LOGGEDIN = "IS_LOGGEDIN";


    // Shared Preferences reference
    private static SharedPreferences pref;


    //get Login userid
    public static String get_isLoggedIn(Context context){
        pref=context.getSharedPreferences(PREFER_IS_LOGGEDIN, PRIVATE_MODE);
        String strLoggedIn= pref.getString(IS_LOGGEDIN,"");
        return strLoggedIn;
    }

    //Set Login userid
    public static void set_isLoggedIn(Context context,String user_id){
        pref=context.getSharedPreferences(PREFER_IS_LOGGEDIN, PRIVATE_MODE);
        editor = pref.edit();
        editor.putString(IS_LOGGEDIN, user_id);
        editor.commit();
    }


        //get Zone
    public static String getZone(Context context){
        pref=context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        String strZone= pref.getString(KEY_ZONE_ID,"");
        return strZone;
    }

    //Set Zone
    public static void setZone(Context context,String zone_id){
        pref=context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.putString(KEY_ZONE_ID, zone_id);
        editor.commit();
    }

    //get GroupID
    public static String getGroupID(Context context){
        pref=context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        String strGroupID= pref.getString(KEY_GROUP_ID,"");
        return strGroupID;
    }

    //Set GroupID
    public static void setGroupID(Context context,String group_id){
        pref=context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.putString(KEY_GROUP_ID, group_id);
        editor.commit();
    }

    //get districtID
    public static String getDistrictID(Context context){
        pref=context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        String strDistrictID= pref.getString(KEY_DISTRICT_ID,"");
        return strDistrictID;
    }

    //Set districtID
    public static void setDistrictID(Context context,String district_id){
        pref=context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.putString(KEY_DISTRICT_ID, district_id);
        editor.commit();
    }
}
