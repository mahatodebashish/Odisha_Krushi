package com.odishakrushi.ExtensionOffQuestionAnswer;


import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.odishakrushi.ExtensionOff_ViewQuestion.ViewQuestionList_ExtOff;
import com.odishakrushi.Farmer_ViewQuestion.ViewAnswerByExtOff;
import com.odishakrushi.NavDrawer.NavdrawerRP;
import com.odishakrushi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExtOffQuestionAnswerFragment extends Fragment {


    public ExtOffQuestionAnswerFragment() {
        // Required empty public constructor
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NavdrawerRP myContext;



    @Override
    public void onAttach(Activity activity) {
        myContext=(NavdrawerRP) activity;
        super.onAttach(activity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ext_off_question_answer, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getActivity().setTitle(getString(R.string.Question_Answer));

        viewPager = (ViewPager) getView().findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) getView().findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager)
    {
       //ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());//  getSupportFragmentManager()
        ViewPagerAdapter adapter = new ViewPagerAdapter(myContext.getSupportFragmentManager());//  ()
        adapter.addFragment(new ViewQuestionList_ExtOff(),getString(R.string.Ques_Asked_By_Farmer));
        adapter.addFragment(new ViewAnswerByExtOff(),getString(R.string.View_Answers));// "TWO"
        viewPager.setAdapter(adapter);

    }



    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();



        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
