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
import com.odishakrushi.EditProfileActivity.EditProfileFarmer;
import com.odishakrushi.R;
import com.odishakrushi.SignUpBusiness;
import com.odishakrushi.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cc.cloudist.acplibrary.ACProgressCustom;

import static com.odishakrushi.Config.getBusinessManProfileData;
import static com.odishakrushi.Config.getProfileData;

public class ViewProfileBusinessMAn extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String user_id;
    ACProgressCustom dialog;
    ImageView editProfile;

    TextView email,phone,website,name_of_farm, properiter_name,
            area_of_business, business_type, deals_in_product, deals_in_product_other,
            fish_medicine, fish_net, fish_feed, fish_others , seed, pesticides, fertiliser,
            catg_other, agril_machinery, sale_service_spare_parts_available, others,
            district_name , block_name;

    CardView cardPhone,cardEmail,cardDistrict,cardWebsite,cardBlock,
            cardNameOfFarm,cardNameOfPropertier,cardPreferedAreaOfBusiness,
            cardTypeOfBusiness,cardDealsIn,cardDealsInOther,cardFisheryMedicineUsed,
            cardNet,cardFishFeed,cardOthers,cardAgrilProductCategory,cardSeed,
            cardPesticides,cardFertiliser,cardAgrilMachinery,cardSalesAndService,
            cardFishOthers;

    String email_str,name_str,phone_str,website_str,name_of_farm_str,properiter_name_str,area_of_business_str,business_type_str,deals_in_product_str,deals_in_product_other_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_business_man);

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
               // startActivity(new Intent(ViewProfileBusinessMAn.this, EditProfileBusiness.class));
                Intent intent =new Intent(ViewProfileBusinessMAn.this, SignUpBusiness.class);
                intent.putExtra("EDIT_PROFILE","1");
                intent.putExtra("NAME_OF_FARM",name_of_farm_str);
                intent.putExtra("NAME_OF_PROPERTIER",properiter_name_str);
                intent.putExtra("PHONE",phone_str);
                intent.putExtra("EMAIL",email_str);
                intent.putExtra("WEBSITE",website_str);


                startActivity(intent);
            }
        });
    }


    private void findViewById(){

        editProfile=findViewById(R.id.editProfile);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        website=findViewById(R.id.website);
        name_of_farm=findViewById(R.id.name_of_farm);
        properiter_name=findViewById(R.id.properiter_name);
        area_of_business=findViewById(R.id.area_of_business);
        business_type=findViewById(R.id.business_type);
        deals_in_product=findViewById(R.id.deals_in_product);
        deals_in_product_other=findViewById(R.id.deals_in_product_other);
        fish_medicine=findViewById(R.id.fish_medicine);
        fish_net=findViewById(R.id.fish_net);
        fish_feed=findViewById(R.id.fish_feed);
        fish_others=findViewById(R.id.fish_others);
        seed=findViewById(R.id.seed);
        pesticides=findViewById(R.id.pesticides);
        fertiliser=findViewById(R.id.fertiliser);
     //   catg_other=findViewById(R.id.catg_other);
        agril_machinery=findViewById(R.id.agril_machinery);
        sale_service_spare_parts_available=findViewById(R.id.sale_service_spare_parts_available);
        others=findViewById(R.id.others);
        district_name=findViewById(R.id.district_name);
        block_name=findViewById(R.id.block_name);

        cardPhone=findViewById(R.id.cardPhone);
        cardEmail=findViewById(R.id.cardEmail);
        cardDistrict=findViewById(R.id.cardDistrict);
        cardWebsite=findViewById(R.id.cardWebsite);
        cardBlock=findViewById(R.id.cardBlock);
        cardNameOfFarm=findViewById(R.id.cardNameOfFarm);
        cardNameOfPropertier=findViewById(R.id.cardNameOfPropertier);
        cardPreferedAreaOfBusiness=findViewById(R.id.cardPreferedAreaOfBusiness);
        cardTypeOfBusiness=findViewById(R.id.cardTypeOfBusiness);
        cardDealsIn=findViewById(R.id.cardDealsIn);
        cardDealsInOther=findViewById(R.id.cardDealsInOther);
        cardFisheryMedicineUsed=findViewById(R.id.cardFisheryMedicineUsed);
        cardNet=findViewById(R.id.cardNet);
        cardFishFeed=findViewById(R.id.cardFishFeed);
        cardOthers=findViewById(R.id.cardOthers);
        cardAgrilProductCategory=findViewById(R.id.cardAgrilProductCategory);
        cardSeed=findViewById(R.id.cardSeed);
        cardPesticides=findViewById(R.id.cardPesticides);
        cardFertiliser=findViewById(R.id.cardFertiliser);
        cardAgrilMachinery=findViewById(R.id.cardAgrilMachinery);
        cardSalesAndService=findViewById(R.id.cardSalesAndService);
        cardFishOthers=findViewById(R.id.cardFishOthers);


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

        AndroidNetworking.get(getBusinessManProfileData+"?user_id="+user_id)
                .setTag("getBusinessManProfileData")
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


                                phone_str= jsonObject1.optString("phone");
                                phone_str= Utils.nullCheck(phone_str);
                                if(phone_str.equals("")) cardPhone.setVisibility(View.GONE);
                                phone.setText(phone_str);

                                website_str= jsonObject1.optString("website");
                                website_str= Utils.nullCheck(website_str);
                                if(website_str.equals("")) cardWebsite.setVisibility(View.GONE);
                                website.setText(website_str);

                                name_of_farm_str= jsonObject1.optString("name_of_farm");
                                name_of_farm_str= Utils.nullCheck(name_of_farm_str);
                                if(name_of_farm_str.equals("")) cardNameOfFarm.setVisibility(View.GONE);
                                name_of_farm.setText(name_of_farm_str);

                                properiter_name_str= jsonObject1.optString("properiter_name");
                                properiter_name_str= Utils.nullCheck(properiter_name_str);
                                if(properiter_name_str.equals("")) cardNameOfPropertier.setVisibility(View.GONE);
                                properiter_name.setText(properiter_name_str);

                                area_of_business_str= jsonObject1.optString("preferred_area_of_business");
                                area_of_business_str= Utils.nullCheck(area_of_business_str);
                                if(area_of_business_str.equals("")) cardPreferedAreaOfBusiness.setVisibility(View.GONE);
                                area_of_business.setText(area_of_business_str);

                                business_type_str= jsonObject1.optString("type_business");
                                business_type_str= Utils.nullCheck(business_type_str);
                                if(business_type_str.equals("")) cardTypeOfBusiness.setVisibility(View.GONE);
                                business_type.setText(business_type_str);

                                deals_in_product_str= jsonObject1.optString("deals_in_product");
                                deals_in_product_str= Utils.nullCheck(deals_in_product_str);
                                if(deals_in_product_str.equals("")) cardDealsIn.setVisibility(View.GONE);
                                deals_in_product.setText(deals_in_product_str);

                                deals_in_product_other_str= jsonObject1.optString("deals_in_product_other");
                                deals_in_product_other_str= Utils.nullCheck(deals_in_product_other_str);
                                if(deals_in_product_other_str.equals("")) cardDealsInOther.setVisibility(View.GONE);
                                deals_in_product_other.setText(deals_in_product_other_str);

                                String fish_medicine_str= jsonObject1.optString("fish_medicine");
                                fish_medicine_str= Utils.nullCheck(fish_medicine_str);
                                if(fish_medicine_str.equals("")) cardFisheryMedicineUsed.setVisibility(View.GONE);
                                fish_medicine.setText(fish_medicine_str);

                                String fish_net_str= jsonObject1.optString("fish_net");
                                fish_net_str= Utils.nullCheck(fish_net_str);
                                if(fish_net_str.equals("")) cardNet.setVisibility(View.GONE);
                                fish_net.setText(fish_net_str);

                                String fish_feed_str= jsonObject1.optString("fish_feed");
                                fish_feed_str= Utils.nullCheck(fish_feed_str);
                                if(fish_feed_str.equals("")) cardFishFeed.setVisibility(View.GONE);
                                fish_feed.setText(fish_feed_str);

                                String fish_others_str= jsonObject1.optString("fish_others");
                                fish_others_str= Utils.nullCheck(fish_others_str);
                                if(fish_others_str.equals("")) cardFishOthers.setVisibility(View.GONE);
                                fish_others.setText(fish_others_str);

                                String seed_str= jsonObject1.optString("seed");
                                seed_str= Utils.nullCheck(seed_str);
                                if(seed_str.equals("")) cardSeed.setVisibility(View.GONE);
                                seed.setText(seed_str);

                                String pesticides_str= jsonObject1.optString("pesticides");
                                pesticides_str= Utils.nullCheck(pesticides_str);
                                if(pesticides_str.equals("")) cardPesticides.setVisibility(View.GONE);
                                pesticides.setText(pesticides_str);

                                String fertiliser_str= jsonObject1.optString("fertiliser");
                                fertiliser_str= Utils.nullCheck(fertiliser_str);
                                if(fertiliser_str.equals("")) cardFertiliser.setVisibility(View.GONE);
                                fertiliser.setText(fertiliser_str);

                                String agril_machinery_str= jsonObject1.optString("agril_machinery");
                                agril_machinery_str= Utils.nullCheck(agril_machinery_str);
                                if(agril_machinery_str.equals("")) cardAgrilMachinery.setVisibility(View.GONE);
                                agril_machinery.setText(agril_machinery_str);

                                String sale_service_spare_parts_available_str= jsonObject1.optString("sale_service_spare_parts_available");
                                sale_service_spare_parts_available_str= Utils.nullCheck(sale_service_spare_parts_available_str);
                                if(sale_service_spare_parts_available_str.equals("")) cardSalesAndService.setVisibility(View.GONE);
                                sale_service_spare_parts_available.setText(sale_service_spare_parts_available_str);

                                String others_str= jsonObject1.optString("others");
                                others_str= Utils.nullCheck(others_str);
                                if(others_str.equals("")) cardOthers.setVisibility(View.GONE);
                                others.setText(others_str);

                                String district_name_str= jsonObject1.optString("district_name");
                                district_name_str= Utils.nullCheck(district_name_str);
                                if(district_name_str.equals("")) cardDistrict.setVisibility(View.GONE);
                                district_name.setText(district_name_str);

                                String block_name_str= jsonObject1.optString("block_name");
                                block_name_str= Utils.nullCheck(block_name_str);
                                if(block_name_str.equals("")) cardBlock.setVisibility(View.GONE);
                                block_name.setText(block_name_str);


                                dialog.hide();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.hide();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(ViewProfileBusinessMAn.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                        dialog.hide();

                    }
                });

    }

}
