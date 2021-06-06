package com.odishakrushi.BusinessProductPromotion;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
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

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import com.odishakrushi.Config;
import com.odishakrushi.R;

public class BPPServices extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }  // Calligraphy

    String str_services="",message="";
    RadioGroup radioService;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_bppservices);

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

        //GETTING USER_ID
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);


        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);

        radioService=findViewById(R.id.radioService);

        radioService.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    str_services=rb.getText().toString();
                    Toast.makeText(BPPServices.this, str_services, Toast.LENGTH_SHORT).show();


                }

            }
        });
    }


    public void onClickOk(View view)
    {
        AndroidNetworking.post(Config.farmersales)
                .addBodyParameter("user_id", str_user_id)
                .addBodyParameter("product_type", str_services)
                .addBodyParameter("category_name", "Services")
                // .addBodyParameter("remark", remark.getText().toString())
                .setTag("saleservices")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // do anything with response
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            message=jsonObject.getString("message");
                            Toast.makeText(BPPServices.this, message, Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(BPPServices.this, getString(R.string.Network_Error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onClickCancel(View view)
    {
        finish();
    }
}
