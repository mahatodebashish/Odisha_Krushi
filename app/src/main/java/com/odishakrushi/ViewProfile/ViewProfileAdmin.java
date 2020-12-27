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
import com.odishakrushi.EditProfileActivity.EditProfileAdminManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cc.cloudist.acplibrary.ACProgressCustom;
import com.odishakrushi.R;
import com.odishakrushi.SignUpAdminManager;
import com.odishakrushi.SignUpStudentResearcher;
import com.odishakrushi.utils.Utils;

import static com.odishakrushi.Config.getAdminManagerProfileData;

public class ViewProfileAdmin extends AppCompatActivity {

    ImageView editProfile;
    TextView /*district_name,*/jurisdiction_name,jurisdiction,dcar,department,serviceStatus,typeOfControl,
            post/*,govt_department*//*,govt_type*/,email,phone,first_name,gender,trust_name,ngo_name;
    ACProgressCustom dialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id;
    String jurisdiction_name_str="",jurisdiction_str="",dcarci_name_str="",department_name_str="",typs_control_id="",service_status_name_str="",
            typs_control_name_str="",gender_str="",email_str="",phone_str="",first_name_str,trust_name_str="",ngo_name_str="";
    CardView cardName , cardPhone , cardEmail ,cardGender,cardDepartment,cardServiceStatus/*, cardGovtType*/
           /* , cardGovtDept */, cardPost , cardTypeOfControl,cardDcar ,cardJurisdiction,cardJurisdictionName,
            cardTrustName,cardNGOName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_admin);


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
              //  startActivity(new Intent(ViewProfileAdmin.this, EditProfileAdminManager.class));
                Intent intent =new Intent(ViewProfileAdmin.this, SignUpAdminManager.class);
                intent.putExtra("EDIT_PROFILE","1");
                intent.putExtra("NAME",first_name_str);
                intent.putExtra("MOBILE",phone_str);
                intent.putExtra("EMAIL_ID",email_str);
                intent.putExtra("GENDER",gender_str);
                intent.putExtra("TYPE_OF_CONTROL_ID",typs_control_id);


                startActivity(intent);
            }
        });

    }


    private void findViewById(){

        editProfile=findViewById(R.id.editProfile);
        first_name=findViewById(R.id.first_name);
       // district_name=findViewById(R.id.district_name);
        post=findViewById(R.id.post);
        //govt_department=findViewById(R.id.govt_department);
       // govt_type=findViewById(R.id.govt_type);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        gender=findViewById(R.id.gender);
        typeOfControl =findViewById(R.id.typeOfControl);
        serviceStatus =findViewById(R.id.serviceStatus);
        department =findViewById(R.id.department);
        dcar =findViewById(R.id.dcar);
        jurisdiction =findViewById(R.id.jurisdiction);
        jurisdiction_name =findViewById(R.id.jurisdiction_name);
        trust_name =findViewById(R.id.trust_name);
        ngo_name =findViewById(R.id.ngo_name);

        cardName =findViewById(R.id.cardName);
        cardPhone =findViewById(R.id.cardPhone);
        cardEmail =findViewById(R.id.cardEmail);
        cardGender =findViewById(R.id.cardGender);
        cardTypeOfControl =findViewById(R.id.cardTypeOfControl);
        cardServiceStatus =findViewById(R.id.cardServiceStatus);
        cardDepartment =findViewById(R.id.cardDepartment);
        cardDcar =findViewById(R.id.cardDcar);
        cardJurisdictionName =findViewById(R.id.cardJurisdictionName);
        cardTrustName =findViewById(R.id.cardTrustName);
        cardNGOName =findViewById(R.id.cardNGOName);
        //cardGovtType =findViewById(R.id.cardGovtType);
      //  cardGovtDept =findViewById(R.id.cardGovtDept);
        cardPost =findViewById(R.id.cardPost);
        cardJurisdiction =findViewById(R.id.cardJurisdiction);


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

        AndroidNetworking.get(getAdminManagerProfileData+"?user_id="+user_id)
                .setTag("getAdminManagerProfileData")
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

                                first_name_str= jsonObject1.optString("first_name");
                                first_name_str= Utils.nullCheck(first_name_str);
                                if(first_name_str.equals("")) cardName.setVisibility(View.GONE);
                                first_name.setText(first_name_str);

                                gender_str= jsonObject1.optString("gender");
                                gender_str= Utils.nullCheck(gender_str);
                                if(gender_str.equals("")) cardGender.setVisibility(View.GONE);
                                gender.setText(gender_str);

                                typs_control_name_str= jsonObject1.optString("typs_control_name");
                                typs_control_name_str= Utils.nullCheck(typs_control_name_str);
                                if(typs_control_name_str.equals("")) cardTypeOfControl.setVisibility(View.GONE);
                                typeOfControl.setText(typs_control_name_str);

                                service_status_name_str= jsonObject1.optString("service_status_name");
                                service_status_name_str= Utils.nullCheck(service_status_name_str);
                                if(service_status_name_str.equals("")) cardServiceStatus.setVisibility(View.GONE);
                                serviceStatus.setText(service_status_name_str);

                                department_name_str= jsonObject1.optString("department_name");
                                department_name_str= Utils.nullCheck(department_name_str);
                                if(department_name_str.equals("")) cardDepartment.setVisibility(View.GONE);
                                department.setText(department_name_str);

                                dcarci_name_str= jsonObject1.optString("dcarci_name");
                                dcarci_name_str= Utils.nullCheck(dcarci_name_str);
                                if(dcarci_name_str.equals("")) cardDcar.setVisibility(View.GONE);
                                dcar.setText(dcarci_name_str);

                                jurisdiction_str= jsonObject1.optString("jurisdiction");
                                jurisdiction_str=Utils.nullCheck(jurisdiction_str);
                                if(jurisdiction_str.equals("")) cardJurisdiction.setVisibility(View.GONE);
                                jurisdiction.setText(jurisdiction_str);

                                jurisdiction_name_str= jsonObject1.optString("Jurisdiction_name");
                                jurisdiction_name_str=Utils.nullCheck(jurisdiction_name_str);
                                if(jurisdiction_name_str.equals("")||jurisdiction_name_str.equals("0"))
                                    cardJurisdictionName.setVisibility(View.GONE);

                                jurisdiction_name.setText(jurisdiction_name_str);

                                String post_str= jsonObject1.optString("post_name");
                                post_str=Utils.nullCheck(post_str);
                                if(post_str.equals("")) cardPost.setVisibility(View.GONE);
                                post.setText(post_str);

                                /*String govt_department_str= jsonObject1.optString("govt_department");
                                govt_department_str=Utils.nullCheck(govt_department_str);
                                if(govt_department_str.equals("")) cardGovtDept.setVisibility(View.GONE);
                                govt_department.setText(govt_department_str);*/

                              /*  String govt_type_str= jsonObject1.optString("govt_type");
                                govt_type_str=Utils.nullCheck(govt_type_str);
                                if(govt_type_str.equals("")) cardGovtType.setVisibility(View.GONE);
                                govt_type.setText(govt_type_str);*/

                                email_str= jsonObject1.optString("email");
                                email_str=Utils.nullCheck(email_str);
                                if(email_str.equals("")) cardEmail.setVisibility(View.GONE);
                                email.setText(email_str);

                                phone_str= jsonObject1.optString("phone");
                                phone_str=Utils.nullCheck(phone_str);
                                if(phone_str.equals("")) cardPhone.setVisibility(View.GONE);
                                phone.setText(phone_str);

                                trust_name_str= jsonObject1.optString("department_id");
                                trust_name_str=Utils.nullCheck(trust_name_str);
                                boolean isNumericTrustName=Utils.isNumeric(trust_name_str);
                                if(trust_name_str.equals("")||isNumericTrustName) cardTrustName.setVisibility(View.GONE);
                                trust_name.setText(trust_name_str);

                                ngo_name_str= jsonObject1.optString("dcarci_id");
                                ngo_name_str=Utils.nullCheck(ngo_name_str);
                                boolean isNumericNgoName=Utils.isNumeric(ngo_name_str);
                                if(ngo_name_str.equals("")||isNumericNgoName) cardNGOName.setVisibility(View.GONE);
                                ngo_name.setText(ngo_name_str);

                                gender_str= jsonObject1.optString("gender");
                               /* gender_str= Utils.nullCheck(gender_str);
                                if(gender_str.equals("")) cardGender.setVisibility(View.GONE);
                                gender.setText(gender_str);*/

                                typs_control_id= jsonObject1.optString("typs_control_id");

                                dialog.hide();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.hide();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(ViewProfileAdmin.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                        dialog.hide();

                    }
                });

    }
}
