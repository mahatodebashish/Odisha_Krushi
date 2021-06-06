package com.odishakrushi.Fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.odishakrushi.AskQGovtSchemes.GSAsk;
import com.odishakrushi.AskQSoilCW.AskQSoilCW;
import com.odishakrushi.Ask_Agriculture;
import com.odishakrushi.Ask_Fishery;
import com.odishakrushi.Ask_Veterinary;
import com.odishakrushi.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyStory extends Fragment{


    LinearLayout agriculture,veterinary,fishery,govtschemes,soilcw;
    public MyStory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ask, container, false);//  fragment_mystory
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles

       // getActivity().setTitle("My Story");
        getActivity().setTitle(getString(R.string.My_Story));

         agriculture=getView().findViewById(R.id.agriculture);
         veterinary=getView().findViewById(R.id.veterinary);
         fishery=getView().findViewById(R.id.fishery);
         govtschemes=getView().findViewById(R.id.govtschemes);
         soilcw=getView().findViewById(R.id.soilcw);

        agriculture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Ask_Agriculture.class);
                startActivity(intent);
            }
        });
        veterinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Ask_Veterinary.class);
                startActivity(intent);
            }
        });
        fishery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Ask_Fishery.class);//
                startActivity(intent);
            }
        });
        govtschemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GSAsk.class);//Ask_GovtSchemes
                startActivity(intent);
            }
        });

        soilcw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AskQSoilCW.class);
                startActivity(intent);
            }
        });
    }


}
