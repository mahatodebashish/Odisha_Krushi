package com.odishakrushi.Class;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class KBankBroadCastReceiver extends BroadcastReceiver {

    String str_kdropdown_id="0";
    @Override
    public void onReceive(Context context, Intent intent){
        // Receive the broadcast random number
        String index = intent.getStringExtra("DROPDOWN_ID");

        str_kdropdown_id=index;


    }
}
