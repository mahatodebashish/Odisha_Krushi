package com.odishakrushi.BusinessProductPromotion;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.odishakrushi.AskQSale.FishProductSale;
import com.odishakrushi.AskQSale.MachinerySale;
import com.odishakrushi.AskQSale.SaleList;
import com.odishakrushi.AskQSale.SaleListMachineTools;
import com.odishakrushi.AskQSale.VeterinaryProductSale;
import com.odishakrushi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProductPromotion extends Fragment {

    SharedPreferences sharedpreferences1;
    public static final String mypreference1 = "BUSINESS_PRODUCT_DEALS_IN";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String str_user_id="";

    String deals_in_product="";

    TextView machineytools,seedseedling,pesticides,fertiliser,medicinefeed,veterinary_item,fishery,services,others;
    ImageView image_ad;
    public FragmentProductPromotion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedpreferences1 = getActivity().getSharedPreferences(mypreference1,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedpreferences1.edit();
        deals_in_product=sharedpreferences1.getString("DEALS_IN_PRODUCT", "");
        editor1.commit(); // commit change

        Toast.makeText(getActivity(), deals_in_product, Toast.LENGTH_SHORT).show();


        //GETTING USER_ID
        sharedpreferences = getActivity(). getSharedPreferences(mypreference,
                Context.MODE_MULTI_PROCESS);
        str_user_id=sharedpreferences.getString("FLAG",  "");
        Log.d("GET", str_user_id);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_product_promotion, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.Product_Promotion));


        machineytools=getView().findViewById(R.id.machineytools);
        seedseedling=getView().findViewById(R.id.seedseedling);
        pesticides=getView().findViewById(R.id.pesticides);
        fertiliser=getView().findViewById(R.id.fertiliser);
        veterinary_item=(TextView) getView().findViewById(R.id.veterinary_item);
        medicinefeed=getView().findViewById(R.id.medicinefeed);
        services=getView().findViewById(R.id.services);
        fishery=getView().findViewById(R.id.fishery);
        others=getView().findViewById(R.id.others);
        image_ad=getView().findViewById(R.id.image_ad);

        switch (deals_in_product)
        {
              case "Seed and Seedlings":
                seedseedling.setVisibility(View.VISIBLE);
                break;

            case "Pesticides":
                pesticides.setVisibility(View.VISIBLE);
                break;

            case "Fertiliser":
                fertiliser.setVisibility(View.VISIBLE);
                break;

            case "Machinery":
                machineytools.setVisibility(View.VISIBLE);
                break;

            case "Veterinary Item":
                veterinary_item.setVisibility(View.VISIBLE);
                break;
            case "Services":
                services.setVisibility(View.VISIBLE);
                break;

            case "Fishery":
                fishery.setVisibility(View.VISIBLE);
                break;

            case "Others":
                others.setVisibility(View.VISIBLE);
                break;
        }
        machineytools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MachinerySale.class);
                startActivity(intent);
            }
        });

        seedseedling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BPPSeedSeedling.class);
                startActivity(intent);
            }
        });
        pesticides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BPPPesticides.class);// SaleListPesticides
                /*intent.putExtra("PROD_NAME","Pesticides");
                intent.putExtra("#RANK","1");*/
                startActivity(intent);
            }
        });

        fertiliser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BPPFertiliser.class);
                startActivity(intent);
            }
        });
        fishery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),FishProductSale.class);
                startActivity(intent);
            }
        });
        medicinefeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BPPMedicineFeed.class);
                startActivity(intent);
            }
        });

        veterinary_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),VeterinaryProductSale.class);
                startActivity(intent);
            }
        });

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SaleListServices.class);
                startActivity(intent);
            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BPPOthers.class);
                startActivity(intent);
            }
        });

        image_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Clicked ad. Goto to linked Website", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
