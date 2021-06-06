package com.odishakrushi.BusinessProductPromotion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import com.odishakrushi.Config;
import com.odishakrushi.R;

public class PopUpServices extends Activity {

    String str_variety="",str_sale_id="",str_services="",message="";
    RadioGroup radioService;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_pop_up_services);


        WindowManager.LayoutParams params = getWindow().getAttributes();

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.70);

        getWindow().setLayout(width, height);

        setFinishOnTouchOutside(false);// to prevent activity from dismissing on touching outside

        radioService=findViewById(R.id.radioService);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {

            str_variety = bundle.getString("PROD_NAME");
            str_sale_id= bundle.getString("SALE_ID");

        }


        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);


        radioService.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_services=rb.getText().toString();
                    Toast.makeText(PopUpServices.this, str_services, Toast.LENGTH_SHORT).show();
                    str_variety=str_services;

                }

            }
        });

    }

    public void onClickOk(View view) {
        if(str_sale_id.equals(""))
            ServicesSale();
        else
            ServicesUpdate(str_sale_id);


    }
    public void onClickCancel(View view)
    {
        onBackPressed();
    }

    public void ServicesSale()
    {
        AndroidNetworking.post(Config.farmersales)
                .addBodyParameter("user_id", str_user_id)
                .addBodyParameter("product_type", str_variety)
                .addBodyParameter("category_name", "Services")
               // .addBodyParameter("variety", str_variety)
                // .addBodyParameter("remark", remark.getText().toString())
                .setTag("saleservices")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // do anything with response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            message = jsonObject.getString("message");
                            Toast.makeText(PopUpServices.this, message, Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(PopUpServices.this, getString(R.string.Network_Error), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void ServicesUpdate(String str_sale_id)
    {
        AndroidNetworking.post(Config.farmersales)
                .addBodyParameter("user_id", str_user_id)
                .addBodyParameter("sale_id", str_sale_id)
                .addBodyParameter("product_type", str_variety)
                .addBodyParameter("category_name", "Services")
               // .addBodyParameter("variety", str_variety)
                // .addBodyParameter("remark", remark.getText().toString())
                .setTag("saleservices")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // do anything with response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            message = jsonObject.getString("message");
                            Toast.makeText(PopUpServices.this, message, Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(PopUpServices.this, getString(R.string.Network_Error), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
