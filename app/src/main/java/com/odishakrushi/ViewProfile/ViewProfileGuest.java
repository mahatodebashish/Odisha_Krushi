package com.odishakrushi.ViewProfile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.EditProfileActivity.EditProfileGuest;
import com.odishakrushi.R;
import com.odishakrushi.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cc.cloudist.acplibrary.ACProgressCustom;

import static com.odishakrushi.Config.getGuestProfileData;

public class ViewProfileGuest extends AppCompatActivity {

    ImageView editProfile;
    TextView first_name,phone,email,gender,district,block;
    ACProgressCustom dialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id;
    String email_str="",first_name_str="",phone_str="",gender_str="",district_str="",block_str="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_guest);

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
        //getSupportActionBar().setTitle("View Profile");
        // viewprofileLayout=findViewById(R.id.viewprofileLayout);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        user_id=sharedpreferences.getString("FLAG", null);
        Log.d("user_id:",user_id);
        editor.commit(); // commit changes

        findViewById();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(ViewProfileGuest.this, EditProfileGuest.class));
                Intent intent=new Intent(ViewProfileGuest.this,EditProfileGuest.class);
                intent.putExtra("FIRST_NAME",first_name_str);
                intent.putExtra("PHONE",phone_str);
                intent.putExtra("EMAIL",email_str);
                intent.putExtra("GENDER",gender_str);
                intent.putExtra("DISTRICT",district_str);
                intent.putExtra("BLOCK",block_str);
                startActivity(intent);
            }
        });
    }
    private void findViewById(){

        editProfile=findViewById(R.id.editProfile);
        first_name=findViewById(R.id.first_name);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        gender=findViewById(R.id.gender);
        district=findViewById(R.id.district);
        block=findViewById(R.id.block);


    }

    @Override
    protected void onResume() {
        super.onResume();

        getProfileData();
    }

    private void getProfileData() {

        dialog = new ACProgressCustom.Builder(this)
                .useImages(R.drawable.logo_new_5th_april)
                .build();
        dialog.show();

        AndroidNetworking.get(getGuestProfileData+"?user_id="+user_id)
                .setTag("getGuestProfileData")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String s) {

                        //Toast.makeText(ViewProfileFarmer.this, s, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);

                                email_str= jsonObject1.optString("email");
                                email_str= Utils.nullCheck(email_str);
                                email.setText(email_str);

                                first_name_str= jsonObject1.optString("first_name");
                                first_name_str= Utils.nullCheck(first_name_str);
                                first_name.setText(first_name_str);


                                phone_str= jsonObject1.optString("phone");
                                phone_str= Utils.nullCheck(phone_str);
                                phone.setText(phone_str);

                                gender_str= jsonObject1.optString("gender");
                                gender_str= Utils.nullCheck(gender_str);
                                gender.setText(gender_str);


                                district_str= jsonObject1.optString("district");
                                district_str= Utils.nullCheck(district_str);
                                district.setText(district_str);

                                block_str= jsonObject1.optString("block");
                                block_str= Utils.nullCheck(block_str);
                                block.setText(block_str);


                                dialog.hide();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.hide();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(ViewProfileGuest.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                        dialog.hide();

                    }
                });

    }
}
