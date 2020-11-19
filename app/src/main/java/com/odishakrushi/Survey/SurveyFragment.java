package com.odishakrushi.Survey;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.odishakrushi.NavDrawer.NavDrawer;
import com.odishakrushi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NavDrawer myContext;

    public SurveyFragment() {
        // Required empty public constructor

    }
    @Override
    public void onAttach(Activity activity) {
        myContext=(NavDrawer) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survey, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getActivity().setTitle(getString(R.string.Survey));

        viewPager = (ViewPager) getView().findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) getView().findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager)
    {
       // ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());//  getSupportFragmentManager()
        ViewPagerAdapter adapter = new ViewPagerAdapter(myContext.getSupportFragmentManager());//  ()
        adapter.addFragment(new GiveUrOpinion(),"Give your opinion");// "ONE"
        adapter.addFragment(new Result(),"Result");// "TWO"
        viewPager.setAdapter(adapter);

    }



    class ViewPagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {//  FragmentPagerAdapter
        private final List<android.support.v4.app.Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();



        public ViewPagerAdapter(android.support.v4.app.FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
