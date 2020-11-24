package com.odishakrushi.ViewProfile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.EditProfileActivity.EditProfileBusiness;
import com.odishakrushi.EditProfileActivity.EditProfileExtoff;
import com.odishakrushi.R;
import com.odishakrushi.SignUpAdminManager;
import com.odishakrushi.SignUpExtensionOfficer;
import com.odishakrushi.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cc.cloudist.acplibrary.ACProgressCustom;

import static com.odishakrushi.Config.getBusinessManProfileData;
import static com.odishakrushi.Config.getExtensionOfficerProfileData;

public class ViewProfileExtensionOff extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id;
    ACProgressCustom dialog;
    ImageView editProfile;

    TextView email,name,phone,gender,typs_of_control,serviceStatus,department,dcar,post,jurisdiction,jurisdiction_name,
            expertise_in;

    CardView cardName, cardPhone , cardEmail, cardGender,cardTypeOfControl,cardServiceStatus,cardDepartment,cardDcar,
       cardPost,cardJurisdiction,cardJurisdictionName, cardExpertiseIn;

    String name_str="",phone_str="",email_str="",gender_str="",typs_of_control_str="",service_status_str="",
            department_str="",Directorate_Corporation_Agency_Research_Center_str="",
            post_str="",jurisdiction_str="",Jurisdiction_name_str="",expertise_in_str="",typs_of_control_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_extension_off);
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
                Intent intent =new Intent(ViewProfileExtensionOff.this, SignUpExtensionOfficer.class);
                intent.putExtra("EDIT_PROFILE","1");
                intent.putExtra("NAME",name_str);
                intent.putExtra("MOBILE",phone_str);
                intent.putExtra("EMAIL_ID",email_str);
                intent.putExtra("GENDER",gender_str);
                intent.putExtra("TYPE_OF_CONTROL_ID",typs_of_control_id);

                startActivity(intent);
            }
        });
    }


    private void findViewById(){

        editProfile=findViewById(R.id.editProfile);
        email=findViewById(R.id.email);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        gender=findViewById(R.id.gender);
        typs_of_control=findViewById(R.id.typs_of_control);
        serviceStatus=findViewById(R.id.serviceStatus);
        department=findViewById(R.id.department);
        dcar=findViewById(R.id.dcar);
        post=findViewById(R.id.post);
        jurisdiction=findViewById(R.id.jurisdiction);
        jurisdiction_name=findViewById(R.id.jurisdiction_name);
        expertise_in=findViewById(R.id.expertise_in);

        cardName=findViewById(R.id.cardName);
        cardPhone=findViewById(R.id.cardPhone);
        cardEmail=findViewById(R.id.cardEmail);
        cardGender=findViewById(R.id.cardGender);
        cardTypeOfControl=findViewById(R.id.cardTypeOfControl);
        cardServiceStatus=findViewById(R.id.cardServiceStatus);
        cardDepartment=findViewById(R.id.cardDepartment);
        cardDcar=findViewById(R.id.cardDcar);
        cardJurisdiction=findViewById(R.id.cardJurisdiction);
        cardPost=findViewById(R.id.cardPost);
        cardJurisdictionName=findViewById(R.id.cardJurisdictionName);
        cardExpertiseIn=findViewById(R.id.cardExpertiseIn);


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

        AndroidNetworking.get(getExtensionOfficerProfileData+"?user_id="+user_id)
                .setTag("getExtensionOfficerProfileData")
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
                                if(email_str.equals("")) cardEmail.setVisibility(View.GONE);
                                email.setText(email_str);

                                name_str= jsonObject1.optString("first_name");
                                name_str= Utils.nullCheck(name_str);
                                if(name_str.equals("")) cardName.setVisibility(View.GONE);
                                name.setText(name_str);

                                phone_str= jsonObject1.optString("phone");
                                phone_str= Utils.nullCheck(phone_str);
                                if(phone_str.equals("")) cardPhone.setVisibility(View.GONE);
                                phone.setText(phone_str);

                                gender_str= jsonObject1.optString("gender");
                                gender_str= Utils.nullCheck(gender_str);
                                if(gender_str.equals("")) cardGender.setVisibility(View.GONE);
                                gender.setText(gender_str);

                                typs_of_control_str= jsonObject1.optString("typs_control_name");
                                typs_of_control_str= Utils.nullCheck(typs_of_control_str);
                                if(typs_of_control_str.equals("")) cardTypeOfControl.setVisibility(View.GONE);
                                typs_of_control.setText(typs_of_control_str);

                                typs_of_control_id=jsonObject1.optString("typs_of_control");

                                service_status_str= jsonObject1.optString("service_status");
                                service_status_str= Utils.nullCheck(service_status_str);
                                if(service_status_str.equals("")) cardServiceStatus.setVisibility(View.GONE);
                                serviceStatus.setText(service_status_str);

                                department_str= jsonObject1.optString("department");
                                department_str= Utils.nullCheck(department_str);
                                if(department_str.equals("")) cardDepartment.setVisibility(View.GONE);
                                department.setText(department_str);

                                Directorate_Corporation_Agency_Research_Center_str= jsonObject1.optString("Directorate_Corporation_Agency_Research_Center");
                                Directorate_Corporation_Agency_Research_Center_str= Utils.nullCheck(Directorate_Corporation_Agency_Research_Center_str);
                                if(Directorate_Corporation_Agency_Research_Center_str.equals("")) cardDcar.setVisibility(View.GONE);
                                dcar.setText(Directorate_Corporation_Agency_Research_Center_str);

                                post_str= jsonObject1.optString("post");
                                post_str= Utils.nullCheck(post_str);
                                if(post_str.equals("")) cardPost.setVisibility(View.GONE);
                                post.setText(post_str);

                                jurisdiction_str= jsonObject1.optString("jurisdiction");
                                jurisdiction_str= Utils.nullCheck(jurisdiction_str);
                                if(jurisdiction_str.equals("")) cardJurisdiction.setVisibility(View.GONE);
                                jurisdiction.setText(jurisdiction_str);

                                Jurisdiction_name_str= jsonObject1.optString("Jurisdiction_name");
                                Jurisdiction_name_str= Utils.nullCheck(Jurisdiction_name_str);
                                if(Jurisdiction_name_str.equals("")) cardJurisdictionName.setVisibility(View.GONE);
                                jurisdiction_name.setText(Jurisdiction_name_str);

                                expertise_in_str= jsonObject1.optString("expertise_in");
                                expertise_in_str= Utils.nullCheck(expertise_in_str);
                                if(expertise_in_str.equals("")) cardExpertiseIn.setVisibility(View.GONE);
                                expertise_in.setText(expertise_in_str);

                                dialog.hide();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.hide();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(ViewProfileExtensionOff.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                        dialog.hide();

                    }
                });

    }
}
