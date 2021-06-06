package com.odishakrushi.AskQSale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.odishakrushi.Config;
import com.odishakrushi.R;
//import spencerstudios.com.bungeelib.Bungee;

public class DeleteRecord extends Activity {

    Button btnOK,btnCancel;
    String str_sale_id="", message="";
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_delete_record);


        WindowManager.LayoutParams params = getWindow().getAttributes();
     /*   params.x = 0;
        params.height = 1000;
        params.width = 700;
        params.y = 0;

        this.getWindow().setAttributes(params);*/
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.20);

        getWindow().setLayout(width, height);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            str_sale_id = bundle.getString("SALE_ID");


        }


        btnOK=findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAnItem();

            }
        });
        btnCancel=findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public  void deleteAnItem()
    {
        AndroidNetworking.post(Config.del_allsalesbyuserId)
                .addBodyParameter("sale_id", str_sale_id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object=new JSONObject(response);
                            message=object.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(DeleteRecord.this, message, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(DeleteRecord.this, "Network Error", Toast.LENGTH_SHORT).show();

                    }
                });

       onBackPressed();

       /* String message= FastNetworkingClass.deleteSaleItem(this,str_sale_id, Config.del_allsalesbyuserId);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        if(message.equals("Something went wrong"))
             onBackPressed();
        else if(message.equals("Records deleted"))
            onBackPressed();*/

    }

}
