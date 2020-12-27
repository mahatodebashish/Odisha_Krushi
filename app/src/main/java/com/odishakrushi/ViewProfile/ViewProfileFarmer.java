package com.odishakrushi.ViewProfile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.odishakrushi.Config;
import com.odishakrushi.EditProfileActivity.EditProfileFarmer;
import com.odishakrushi.R;
import com.odishakrushi.SignUpFarmer;
import com.odishakrushi.SignUpFarmer_2;
import com.odishakrushi.utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cc.cloudist.acplibrary.ACProgressCustom;

import static com.odishakrushi.Config.getProfileData;

public class ViewProfileFarmer extends AppCompatActivity {


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id;
    ACProgressCustom dialog;
    ImageView editProfile;
    TextView first_name,email,phone,gender,fathers_name,district,block,gp,
            irrigated_land_under_cultivation,irrigation_source,non_irrigated_land_under_cultivation
            ,village,machine_tool_own,any_special_crop_grown,caste,last_crop_grown_in,
            area,soil_testing_done,crop,seed_seedling_variety,fertiliser_used,
            pesticide_used,lcp_machines_used,last_crop_insurane_done,loan_taken,fishery_insurance_done,
            fishery_activity,owner_of_pond,area_of_pond_acres,fingerling_from,fishery_trained_for_farming,
            fishery_medicine_used,fishery_lone_taken,area_of_pond_own_lease,fishery_lone_taken_for,
            veterinary_activity, animals, animal_type,covered_by_insurance,veterinary_lone_taken,
            veterinary_lone_taken_for,veterinary_insurance_done,veterinary_trained_for_farming,veterinary_medicine_used,
            animal_variety,animal_numbers;

    CardView cardName, cardFatherName , cardGender , cardCaste , cardDistrict , cardBlock ,
            cardGp , cardVillage , cardPhone , cardEmail , cardIluc , cardIrrigationSource ,
            cardNiluc ,cardMachineAndToolOwn , cardLastCropGrownIn , cardSoilTestingDone ,
            cardCrop , cardSeedSeedlingVariety ,cardFertiliserUsed, cardPesticidesUsed ,
            cardLcpUsed , cardCoveredByInsurance , cardArea, cardOwnerOfPond, cardFingerlingFrom ,cardFisheryLoneTaken ,
            cardFisheryInsuranceDone,   cardAreaOfPondOwnLease , cardFisheryTrainedForFarming , cardFisheryMedicineUsed ,
            cardFisheryLoanTakenFor , cardVeterinaryInsuranceDone ,cardVeterinaryActivity ,cardAnimals , cardAnimalType , cardAnimalVariety
            , cardAnimalNumbers , cardCoveredByInsuranceAnimal , cardLoanTaken ,
            cardAnySpecialCropGrown , cardFisheryActivity , cardAreaOfPond , cardVeterinaryLoneTaken
            , cardVeterinaryLoneTakenFor , cardVeterinaryTrainedForFarming , cardVeterinaryMedicineUsed ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_farmer);// constraint_test

        Log.d("lifecycle", "onCreate: ");
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
                //startActivity(new Intent(ViewProfileFarmer.this, EditProfileFarmer.class));
                Intent intent=new Intent(ViewProfileFarmer.this, SignUpFarmer_2.class);
                intent.putExtra("EDIT_PROFILE","1");
                startActivity(intent);
            }
        });

        getProfileData();
    }

    private void findViewById(){

        editProfile=findViewById(R.id.editProfile);
        first_name=findViewById(R.id.first_name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        gender=findViewById(R.id.gender);
        fathers_name=findViewById(R.id.fathers_name);
        district=findViewById(R.id.district);
        block=findViewById(R.id.block);
        gp=findViewById(R.id.gp);
        irrigated_land_under_cultivation=findViewById(R.id.irrigated_land_under_cultivation);
        irrigation_source=findViewById(R.id.irrigation_source);
        non_irrigated_land_under_cultivation=findViewById(R.id.non_irrigated_land_under_cultivation);
        village=findViewById(R.id.village);
        machine_tool_own=findViewById(R.id.machine_tool_own);
        any_special_crop_grown=findViewById(R.id.any_special_crop_grown);
        caste=findViewById(R.id.caste);
        last_crop_grown_in=findViewById(R.id.last_crop_grown_in);
        area=findViewById(R.id.area);
        soil_testing_done=findViewById(R.id.soil_testing_done);
        crop=findViewById(R.id.crop);
        seed_seedling_variety=findViewById(R.id.seed_seedling_variety);
        fertiliser_used=findViewById(R.id.fertiliser_used);
        pesticide_used=findViewById(R.id.pesticide_used);
        lcp_machines_used=findViewById(R.id.lcp_machines_used);
        last_crop_insurane_done=findViewById(R.id.last_crop_insurane_done);
        loan_taken=findViewById(R.id.loan_taken);
        fishery_activity=findViewById(R.id.fishery_activity);
        owner_of_pond=findViewById(R.id.owner_of_pond);
        fishery_insurance_done=findViewById(R.id.fishery_insurance_done);
        area_of_pond_acres=findViewById(R.id.area_of_pond_acres);
        fingerling_from=findViewById(R.id.fingerling_from);
        fishery_trained_for_farming=findViewById(R.id.fishery_trained_for_farming);
        fishery_medicine_used=findViewById(R.id.fishery_medicine_used);
        fishery_lone_taken=findViewById(R.id.fishery_lone_taken);
        area_of_pond_own_lease=findViewById(R.id.area_of_pond_own_lease);
        fishery_lone_taken_for=findViewById(R.id.fishery_lone_taken_for);
        veterinary_activity=findViewById(R.id.veterinary_activity);
        animals=findViewById(R.id.animals);
        animal_type=findViewById(R.id.animal_type);
        covered_by_insurance=findViewById(R.id.covered_by_insurance);
        veterinary_insurance_done=findViewById(R.id.veterinary_insurance_done);
        veterinary_lone_taken=findViewById(R.id.veterinary_lone_taken);
        veterinary_lone_taken_for=findViewById(R.id.veterinary_lone_taken_for);
        veterinary_trained_for_farming=findViewById(R.id.veterinary_trained_for_farming);
        veterinary_medicine_used=findViewById(R.id.veterinary_medicine_used);
        animal_variety=findViewById(R.id.animal_variety);
        animal_numbers=findViewById(R.id.animal_numbers);

        cardName=findViewById(R.id.cardName);
        cardFatherName=findViewById(R.id.cardFatherName);
        cardGender=findViewById(R.id.cardGender);
        cardCaste=findViewById(R.id.cardCaste);
        cardDistrict=findViewById(R.id.cardDistrict);
        cardBlock=findViewById(R.id.cardBlock);
        cardGp=findViewById(R.id.cardGp);
        cardVillage=findViewById(R.id.cardVillage);
        cardPhone=findViewById(R.id.cardPhone);
        cardEmail=findViewById(R.id.cardEmail);
        cardIluc=findViewById(R.id.cardIluc);
        cardIrrigationSource=findViewById(R.id.cardIrrigationSource);
        cardNiluc=findViewById(R.id.cardNiluc);
        cardMachineAndToolOwn=findViewById(R.id.cardMachineAndToolOwn);
        cardLastCropGrownIn=findViewById(R.id.cardLastCropGrownIn);
        cardSoilTestingDone=findViewById(R.id.cardSoilTestingDone);
        cardCrop=findViewById(R.id.cardCrop);
        cardSeedSeedlingVariety=findViewById(R.id.cardSeedSeedlingVariety);
        cardFertiliserUsed=findViewById(R.id.cardFertiliserUsed);
        cardPesticidesUsed=findViewById(R.id.cardPesticidesUsed);
        cardLcpUsed=findViewById(R.id.cardLcpUsed);
        cardCoveredByInsurance=findViewById(R.id.cardCoveredByInsurance);
        cardArea=findViewById(R.id.cardArea);
        cardFisheryLoneTaken=findViewById(R.id.cardFisheryLoneTaken);
        cardFingerlingFrom=findViewById(R.id.cardFingerlingFrom);
        cardOwnerOfPond=findViewById(R.id.cardOwnerOfPond);
        cardFisheryInsuranceDone=findViewById(R.id.cardFisheryInsuranceDone);
        cardAreaOfPondOwnLease=findViewById(R.id.cardAreaOfPondOwnLease);
        cardFisheryTrainedForFarming=findViewById(R.id.cardFisheryTrainedForFarming);
        cardFisheryMedicineUsed=findViewById(R.id.cardFisheryMedicineUsed);
        cardFisheryLoanTakenFor=findViewById(R.id.cardFisheryLoanTakenFor);
        cardVeterinaryInsuranceDone=findViewById(R.id.cardVeterinaryInsuranceDone);
        cardVeterinaryActivity=findViewById(R.id.cardVeterinaryActivity);
        cardAnimals=findViewById(R.id.cardAnimals);
        cardAnimalType=findViewById(R.id.cardAnimalType);
        cardAnimalVariety=findViewById(R.id.cardAnimalVariety);
        cardAnimalNumbers=findViewById(R.id.cardAnimalNumbers);
        cardCoveredByInsuranceAnimal=findViewById(R.id.cardCoveredByInsuranceAnimal);
        cardLoanTaken=findViewById(R.id.cardLoanTaken);
        cardAnySpecialCropGrown=findViewById(R.id.cardAnySpecialCropGrown);
        cardFisheryActivity=findViewById(R.id.cardFisheryActivity);
        cardAreaOfPond=findViewById(R.id.cardAreaOfPond);
        cardVeterinaryLoneTaken=findViewById(R.id.cardVeterinaryLoneTaken);
        cardVeterinaryLoneTakenFor=findViewById(R.id.cardVeterinaryLoneTakenFor);
        cardVeterinaryTrainedForFarming=findViewById(R.id.cardVeterinaryTrainedForFarming);
        cardVeterinaryMedicineUsed=findViewById(R.id.cardVeterinaryMedicineUsed);


    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("lifecycle", "onResume: ");
        //getProfileData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle", "onStart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle", "onPause: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle", "onRestart: ");
        //getProfileData();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle", "onDestroy: ");
    }

    private void getProfileData() {

        dialog = new ACProgressCustom.Builder(this)
                .useImages(R.drawable.logo_new_5th_april)
                .build();
        dialog.show();

        AndroidNetworking.get(getProfileData+"?user_id="+user_id)
                .setTag("getProfileData")
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

                                String email_str= jsonObject1.optString("email");
                                email_str=Utils.nullCheck(email_str);
                                if(email_str.equals("")) cardEmail.setVisibility(View.GONE);
                                email.setText(email_str);

                                String first_name_str= jsonObject1.optString("first_name");
                                first_name_str=Utils.nullCheck(first_name_str);
                                if(first_name_str.equals("")) cardName.setVisibility(View.GONE);
                                first_name.setText(first_name_str);

                                String phone_str= jsonObject1.optString("phone");
                                phone_str=Utils.nullCheck(phone_str);
                                if(phone_str.equals("")) cardPhone.setVisibility(View.GONE);
                                phone.setText(phone_str);

                                String gender_str= jsonObject1.optString("gender");
                                gender_str=Utils.nullCheck(gender_str);
                                if(gender_str.equals("")) cardGender.setVisibility(View.GONE);
                                gender.setText(gender_str);

                                String fathers_name_str= jsonObject1.optString("fathers_name");
                                fathers_name_str=Utils.nullCheck(fathers_name_str);
                                if(fathers_name_str.equals("")) cardFatherName.setVisibility(View.GONE);
                                fathers_name.setText(fathers_name_str);

                                String district_str= jsonObject1.optString("district_name");
                                district_str=Utils.nullCheck(district_str);
                                if(district_str.equals("")) cardDistrict.setVisibility(View.GONE);
                                district.setText(district_str);

                                String block_str= jsonObject1.optString("block_name");
                                block_str=Utils.nullCheck(block_str);
                                if(block_str.equals("")) cardBlock.setVisibility(View.GONE);
                                block.setText(block_str);

                                String gp_str= jsonObject1.optString("gp_name");
                                gp_str=Utils.nullCheck(gp_str);
                                if(gp_str.equals("")) cardGp.setVisibility(View.GONE);
                                gp.setText(gp_str);

                                String irrigated_land_under_cultivation_str= jsonObject1.optString("irrigated_land_under_cultivation");
                                irrigated_land_under_cultivation_str=Utils.nullCheck(irrigated_land_under_cultivation_str);
                                if(irrigated_land_under_cultivation_str.equals("")) cardIluc.setVisibility(View.GONE);
                                irrigated_land_under_cultivation.setText(irrigated_land_under_cultivation_str);

                                String irrigation_source_str= jsonObject1.optString("irrigation_source");
                                irrigation_source_str=Utils.nullCheck(irrigation_source_str);
                                if(irrigation_source_str.equals("")) cardIrrigationSource.setVisibility(View.GONE);
                                irrigation_source.setText(irrigation_source_str);

                                String non_irrigated_land_under_cultivation_str= jsonObject1.optString("non_irrigated_land_under_cultivation");
                                non_irrigated_land_under_cultivation_str=Utils.nullCheck(non_irrigated_land_under_cultivation_str);
                                if(non_irrigated_land_under_cultivation_str.equals("")) cardNiluc.setVisibility(View.GONE);
                                non_irrigated_land_under_cultivation.setText(non_irrigated_land_under_cultivation_str);

                                String village_str= jsonObject1.optString("village");
                                village_str=Utils.nullCheck(village_str);
                                if(village_str.equals("")) cardVillage.setVisibility(View.GONE);
                                village.setText(village_str);

                                String machine_tool_own_str= jsonObject1.optString("machine_tool_own");
                                machine_tool_own_str=Utils.nullCheck(machine_tool_own_str);
                                if(machine_tool_own_str.equals("")) cardMachineAndToolOwn.setVisibility(View.GONE);
                                machine_tool_own.setText(machine_tool_own_str);

                                String any_special_crop_grown_str= jsonObject1.optString("any_special_crop_grown");
                                any_special_crop_grown_str=Utils.nullCheck(any_special_crop_grown_str);
                                if(any_special_crop_grown_str.equals("")) cardAnySpecialCropGrown.setVisibility(View.GONE);
                                any_special_crop_grown.setText(any_special_crop_grown_str);

                                String caste_str= jsonObject1.optString("caste");
                                caste_str=Utils.nullCheck(caste_str);
                                if(caste_str.equals("")) cardCaste.setVisibility(View.GONE);
                                caste.setText(caste_str);

                                String last_crop_grown_in_str= jsonObject1.optString("last_crop_grown_in");
                                last_crop_grown_in_str=Utils.nullCheck(last_crop_grown_in_str);
                                if(last_crop_grown_in_str.equals("")) cardLastCropGrownIn.setVisibility(View.GONE);
                                last_crop_grown_in.setText(last_crop_grown_in_str);

                                String area_str= jsonObject1.optString("area");
                                area_str=Utils.nullCheck(area_str);
                                if(area_str.equals("")) cardArea.setVisibility(View.GONE);
                                area.setText(area_str);

                                String soil_testing_done_str= jsonObject1.optString("soil_testing_done");
                                soil_testing_done_str=Utils.nullCheck(soil_testing_done_str);
                                if(soil_testing_done_str.equals("")) cardSoilTestingDone.setVisibility(View.GONE);
                                soil_testing_done.setText(soil_testing_done_str);

                                String crop_str= jsonObject1.optString("crop");
                                crop_str=Utils.nullCheck(crop_str);
                                if(crop_str.equals("")) cardCrop.setVisibility(View.GONE);
                                crop.setText(crop_str);

                                String seed_seedling_variety_str= jsonObject1.optString("seed_seedling_variety");
                                seed_seedling_variety_str=Utils.nullCheck(seed_seedling_variety_str);
                                if(seed_seedling_variety_str.equals("")) cardSeedSeedlingVariety.setVisibility(View.GONE);
                                seed_seedling_variety.setText(seed_seedling_variety_str);

                                String fertiliser_used_str= jsonObject1.optString("fertiliser_used");
                                fertiliser_used_str=Utils.nullCheck(fertiliser_used_str);
                                if(fertiliser_used_str.equals("")) cardFertiliserUsed.setVisibility(View.GONE);
                                fertiliser_used.setText(fertiliser_used_str);

                                String pesticide_used_str= jsonObject1.optString("pesticide_used");
                                pesticide_used_str=Utils.nullCheck(pesticide_used_str);
                                if(pesticide_used_str.equals("")) cardPesticidesUsed.setVisibility(View.GONE);
                                pesticide_used.setText(pesticide_used_str);

                                String lcp_machines_used_str= jsonObject1.optString("lcp_machines_used");
                                lcp_machines_used_str=Utils.nullCheck(lcp_machines_used_str);
                                if(lcp_machines_used_str.equals("")) cardLcpUsed.setVisibility(View.GONE);
                                lcp_machines_used.setText(lcp_machines_used_str);

                                String last_crop_insurane_done_str= jsonObject1.optString("last_crop_insurane_done");
                                last_crop_insurane_done_str=Utils.nullCheck(last_crop_insurane_done_str);
                                if(last_crop_insurane_done_str.equals("")) cardCoveredByInsurance.setVisibility(View.GONE);
                                last_crop_insurane_done.setText(last_crop_insurane_done_str);

                                String loan_taken_str= jsonObject1.optString("loan_taken");
                                loan_taken_str=Utils.nullCheck(loan_taken_str);
                                if(loan_taken_str.equals("")) cardLoanTaken.setVisibility(View.GONE);
                                loan_taken.setText(loan_taken_str);

                                String fishery_activity_str= jsonObject1.optString("fishery_activity");
                                fishery_activity_str=Utils.nullCheck(fishery_activity_str);
                                if(fishery_activity_str.equals("")) cardFisheryActivity.setVisibility(View.GONE);
                                fishery_activity.setText(fishery_activity_str);

                                String area_of_pond_acres_str= jsonObject1.optString("area_of_pond_acres");
                                area_of_pond_acres_str=Utils.nullCheck(area_of_pond_acres_str);
                                if(area_of_pond_acres_str.equals("")) cardAreaOfPond.setVisibility(View.GONE);
                                area_of_pond_acres.setText(area_of_pond_acres_str);

                                String owner_of_pond_str= jsonObject1.optString("owner_of_pond");
                                owner_of_pond_str=Utils.nullCheck(owner_of_pond_str);
                                if(owner_of_pond_str.equals("")) cardOwnerOfPond.setVisibility(View.GONE);
                                owner_of_pond.setText(owner_of_pond_str);

                                String fingerling_from_str= jsonObject1.optString("fingerling_from");
                                fingerling_from_str=Utils.nullCheck(fingerling_from_str);
                                if(fingerling_from_str.equals("")) cardFingerlingFrom.setVisibility(View.GONE);
                                fingerling_from.setText(fingerling_from_str);

                                String fishery_insurance_done_str= jsonObject1.optString("fishery_insurance_done");
                                fishery_insurance_done_str=Utils.nullCheck(fishery_insurance_done_str);
                                if(fishery_insurance_done_str.equals("")) cardFisheryInsuranceDone.setVisibility(View.GONE);
                                fishery_insurance_done.setText(fishery_insurance_done_str);

                                String fishery_trained_for_farming_str= jsonObject1.optString("fishery_trained_for_farming");
                                fishery_trained_for_farming_str=Utils.nullCheck(fishery_trained_for_farming_str);
                                if(fishery_trained_for_farming_str.equals("")) cardFisheryTrainedForFarming.setVisibility(View.GONE);
                                fishery_trained_for_farming.setText(fishery_trained_for_farming_str);

                                String fishery_medicine_used_str= jsonObject1.optString("fishery_medicine_used");
                                fishery_medicine_used_str=Utils.nullCheck(fishery_medicine_used_str);
                                if(fishery_medicine_used_str.equals("")) cardFisheryMedicineUsed.setVisibility(View.GONE);
                                fishery_medicine_used.setText(fishery_medicine_used_str);

                                String fishery_lone_taken_str= jsonObject1.optString("fishery_lone_taken");
                                fishery_lone_taken_str=Utils.nullCheck(fishery_lone_taken_str);
                                if(fishery_lone_taken_str.equals("")) cardFisheryLoneTaken.setVisibility(View.GONE);
                                fishery_lone_taken.setText(fishery_lone_taken_str);

                                String area_of_pond_own_lease_str= jsonObject1.optString("area_of_pond_own_lease");
                                area_of_pond_own_lease_str=Utils.nullCheck(area_of_pond_own_lease_str);
                                if(area_of_pond_own_lease_str.equals("")) cardAreaOfPondOwnLease.setVisibility(View.GONE);
                                area_of_pond_own_lease.setText(area_of_pond_own_lease_str);

                                String fishery_lone_taken_for_str= jsonObject1.optString("fishery_lone_taken_for");
                                fishery_lone_taken_for_str=Utils.nullCheck(fishery_lone_taken_for_str);
                                if(fishery_lone_taken_for_str.equals("")) cardFisheryLoanTakenFor.setVisibility(View.GONE);
                                fishery_lone_taken_for.setText(fishery_lone_taken_for_str);

                                String veterinary_activity_str= jsonObject1.optString("veterinary_activity");
                                veterinary_activity_str=Utils.nullCheck(veterinary_activity_str);
                                if(veterinary_activity_str.equals("")) cardVeterinaryActivity.setVisibility(View.GONE);
                                veterinary_activity.setText(veterinary_activity_str);

                                String animals_str= jsonObject1.optString("animals");
                                animals_str=Utils.nullCheck(animals_str);
                                if(animals_str.equals("")) cardAnimals.setVisibility(View.GONE);
                                animals.setText(animals_str);

                                String veterinary_insurance_done_str= jsonObject1.optString("veterinary_insurance_done");
                                veterinary_insurance_done_str=Utils.nullCheck(veterinary_insurance_done_str);
                                if(veterinary_insurance_done_str.equals("")) cardVeterinaryInsuranceDone.setVisibility(View.GONE);
                                veterinary_insurance_done.setText(veterinary_insurance_done_str);

                                String animal_type_str= jsonObject1.optString("animal_type");
                                animal_type_str=Utils.nullCheck(animal_type_str);
                                if(animal_type_str.equals("")) cardAnimalType.setVisibility(View.GONE);
                                animal_type.setText(animal_type_str);

                                String covered_by_insurance_str= jsonObject1.optString("covered_by_insurance");
                                covered_by_insurance_str=Utils.nullCheck(covered_by_insurance_str);
                                if(covered_by_insurance_str.equals("")) cardCoveredByInsurance.setVisibility(View.GONE);
                                covered_by_insurance.setText(covered_by_insurance_str);

                                String veterinary_lone_taken_str= jsonObject1.optString("veterinary_lone_taken");
                                veterinary_lone_taken_str=Utils.nullCheck(veterinary_lone_taken_str);
                                if(veterinary_lone_taken_str.equals("")) cardVeterinaryLoneTaken.setVisibility(View.GONE);
                                veterinary_lone_taken.setText(veterinary_lone_taken_str);

                                String veterinary_lone_taken_for_str= jsonObject1.optString("veterinary_lone_taken_for");
                                veterinary_lone_taken_for_str=Utils.nullCheck(veterinary_lone_taken_for_str);
                                if(veterinary_lone_taken_for_str.equals("")) cardVeterinaryLoneTakenFor.setVisibility(View.GONE);
                                veterinary_lone_taken_for.setText(veterinary_lone_taken_for_str);

                                String veterinary_trained_for_farming_str= jsonObject1.optString("veterinary_trained_for_farming");
                                veterinary_trained_for_farming_str=Utils.nullCheck(veterinary_trained_for_farming_str);
                                if(veterinary_trained_for_farming_str.equals("")) cardVeterinaryTrainedForFarming.setVisibility(View.GONE);
                                veterinary_trained_for_farming.setText(veterinary_trained_for_farming_str);

                                String veterinary_medicine_used_str= jsonObject1.optString("veterinary_medicine_used");
                                veterinary_medicine_used_str=Utils.nullCheck(veterinary_medicine_used_str);
                                if(veterinary_medicine_used_str.equals("")) cardVeterinaryMedicineUsed.setVisibility(View.GONE);
                                veterinary_medicine_used.setText(veterinary_medicine_used_str);

                                String animal_variety_str= jsonObject1.optString("animal_variety");
                                animal_variety_str=Utils.nullCheck(animal_variety_str);
                                if(animal_variety_str.equals("")) cardAnimalVariety.setVisibility(View.GONE);
                                animal_variety.setText(animal_variety_str);

                                String animal_numbers_str= jsonObject1.optString("animal_numbers");
                                animal_numbers_str=Utils.nullCheck(animal_numbers_str);
                                if(animal_numbers_str.equals("")) cardAnimalNumbers.setVisibility(View.GONE);
                                animal_numbers.setText(animal_numbers_str);

                                dialog.hide();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.hide();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                          Toast.makeText(ViewProfileFarmer.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                        dialog.hide();

                    }
                });

    }


}
