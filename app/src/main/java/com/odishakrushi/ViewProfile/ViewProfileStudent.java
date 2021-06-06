package com.odishakrushi.ViewProfile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
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
import com.odishakrushi.R;
import com.odishakrushi.SignUpStudentResearcher;
import com.odishakrushi.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cc.cloudist.acplibrary.ACProgressCustom;

import static com.odishakrushi.Config.getStudentProfileData;

public class ViewProfileStudent extends AppCompatActivity {

    ImageView editProfile;
    TextView first_name,last_name,phone,email,gender,district,education_status,highest_qualification,txtSpecialization,university_college,other_detail,other_highest_qualification,other_specialization;
    CardView cardName,cardLastname,cardPhone,cardDistrict,cardEmail,cardGender,cardEducationStatus,cardHighestQualification,
            cardSpecialization,cardCollege,cardOtherDetails,cardOtherHighestQualification,cardOtherSpecialization;
    String email_str="",first_name_str="",phone_str="",gender_str="",district_str="";
    ACProgressCustom dialog;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_student);

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
               // startActivity(new Intent(ViewProfileStudent.this, EditProfileStudentResearcher.class));

                Intent intent =new Intent(ViewProfileStudent.this,SignUpStudentResearcher.class);
                intent.putExtra("EDIT_PROFILE","1");
                intent.putExtra("NAME",first_name_str);
                intent.putExtra("MOBILE",phone_str);
                intent.putExtra("EMAIL_ID",email_str);
                intent.putExtra("GENDER",gender_str);
                intent.putExtra("DISTRICT",district_str);

                startActivity(intent);
            }
        });
    }
    private void findViewById(){

        editProfile=findViewById(R.id.editProfile);
        first_name=findViewById(R.id.first_name);
        last_name=findViewById(R.id.last_name);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        gender=findViewById(R.id.gender);
        district=findViewById(R.id.district);
        education_status=findViewById(R.id.education_status);
        highest_qualification=findViewById(R.id.highest_qualification);
        txtSpecialization=findViewById(R.id.txtSpecialization);
        university_college=findViewById(R.id.university_college);
        other_detail=findViewById(R.id.other_detail);
        other_highest_qualification=findViewById(R.id.other_highest_qualification);
        other_specialization=findViewById(R.id.other_specialization);

        cardName=findViewById(R.id.cardName);
        cardLastname=findViewById(R.id.cardLastname);
        cardPhone=findViewById(R.id.cardPhone);
        cardEmail=findViewById(R.id.cardEmail);
        cardGender=findViewById(R.id.cardGender);
        cardDistrict=findViewById(R.id.cardDistrict);
        cardEducationStatus=findViewById(R.id.cardEducationStatus);
        cardHighestQualification=findViewById(R.id.cardHighestQualification);
        cardCollege=findViewById(R.id.cardCollege);
        cardOtherDetails=findViewById(R.id.cardOtherDetails);
        cardOtherHighestQualification=findViewById(R.id.cardOtherHighestQualification);
        cardSpecialization=findViewById(R.id.cardSpecialization);
        cardOtherSpecialization=findViewById(R.id.cardOtherSpecialization);

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

        AndroidNetworking.get(getStudentProfileData+"?user_id="+user_id)
                .setTag("getStudentProfileData")
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

                                first_name_str= jsonObject1.optString("first_name");
                                first_name_str= Utils.nullCheck(first_name_str);
                                if(first_name_str.equals("")) cardName.setVisibility(View.GONE);
                                first_name.setText(first_name_str);

                                String last_name_str= jsonObject1.optString("last_name");
                                last_name_str= Utils.nullCheck(last_name_str);
                                if(last_name_str.equals("")) cardLastname.setVisibility(View.GONE);
                                last_name.setText(last_name_str);

                                phone_str= jsonObject1.optString("phone");
                                phone_str= Utils.nullCheck(phone_str);
                                if(phone_str.equals("")) cardPhone.setVisibility(View.GONE);
                                phone.setText(phone_str);

                                gender_str= jsonObject1.optString("gender");
                                gender_str= Utils.nullCheck(gender_str);
                                if(gender_str.equals("")) cardGender.setVisibility(View.GONE);
                                gender.setText(gender_str);

                                district_str= jsonObject1.optString("district_name");
                                district_str= Utils.nullCheck(district_str);
                                if(district_str.equals("")) cardDistrict.setVisibility(View.GONE);
                                district.setText(district_str);

                                /*String[] district_array = getResources().getStringArray(R.array.array_district);

                                try{
                                    district.setText(district_array[Integer.parseInt(district_str)]);
                                }catch (NumberFormatException e){

                                }*/

                                String education_status_str= jsonObject1.optString("education_status");
                                education_status_str= Utils.nullCheck(education_status_str);
                                if(education_status_str.equals("")) cardEducationStatus.setVisibility(View.GONE);
                                education_status.setText(education_status_str);

                                String highest_qualification_str= jsonObject1.optString("highest_qualification");
                                highest_qualification_str= Utils.nullCheck(highest_qualification_str);
                                if(highest_qualification_str.equals("")) cardHighestQualification.setVisibility(View.GONE);
                                highest_qualification.setText(highest_qualification_str);

                                String specialization_str= jsonObject1.optString("specialization");
                                specialization_str= Utils.nullCheck(specialization_str);
                                if(specialization_str.equals("")) cardSpecialization.setVisibility(View.GONE);
                                txtSpecialization.setText(specialization_str);

                                String university_college_str= jsonObject1.optString("university_college");
                                university_college_str= Utils.nullCheck(university_college_str);
                                if(university_college_str.equals("")) cardCollege.setVisibility(View.GONE);
                                university_college.setText(university_college_str);

                                String other_detail_str= jsonObject1.optString("other_detail");
                                other_detail_str= Utils.nullCheck(other_detail_str);
                                if(other_detail_str.equals("")) cardOtherDetails.setVisibility(View.GONE);
                                other_detail.setText(other_detail_str);

                                String other_highest_qualification_str= jsonObject1.optString("other_highest_qualification");
                                other_highest_qualification_str= Utils.nullCheck(other_highest_qualification_str);
                                if(other_highest_qualification_str.equals("")) cardOtherHighestQualification.setVisibility(View.GONE);
                                other_highest_qualification.setText(other_highest_qualification_str);

                                String other_specialization_str= jsonObject1.optString("other_specialization");
                                other_specialization_str= Utils.nullCheck(other_specialization_str);
                                if(other_specialization_str.equals("")) cardOtherSpecialization.setVisibility(View.GONE);
                                other_specialization.setText(other_specialization_str);


                                dialog.hide();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.hide();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(ViewProfileStudent.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                        dialog.hide();

                    }
                });

    }
}
