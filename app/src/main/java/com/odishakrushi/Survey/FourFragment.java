package com.odishakrushi.Survey;

/**
 * Created by RatnaDev008 on 2/22/2018.
 */

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.odishakrushi.R;
import com.odishakrushi.SurveyFarmer;


public class FourFragment extends Fragment{

    public FourFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SurveyFarmer activity = (SurveyFarmer) getActivity();
        String getData = activity.sendData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_four, container, false);
    }

}