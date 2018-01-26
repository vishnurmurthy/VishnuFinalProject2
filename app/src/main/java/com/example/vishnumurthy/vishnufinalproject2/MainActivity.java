package com.example.vishnumurthy.vishnufinalproject2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements EditFragment.FragmentEditInterface, RecycleFragment.FragmentRecycleInterface{
    private final static String TAG = "MAIN ACTIVITY";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int counter = 0;
    //static final String INTERNAL_STORAGE_FILE = "db.json";
    private Fragment mActiveFragment;
    private EditFragment mEditFragment;
    private RecycleFragment mRecycleFragment;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_SELECT_CONTACT = 2;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the ViewPager with the sections adapter.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        Log.i(TAG, mSectionsPagerAdapter+"");
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        Log.i("Here", "here");
    }
    public void onClick(View view) {
        counter++;
        if(counter==2) {counter = 0;}
        switch(counter)
        {
            case 0:
                setFragmentRecycleActive();
            case 1:
                setFragmentEditActive(null);
        }
    }
    @Override
    public void setFragmentEditActive(PictureText pt) {

        Log.i("TAG", pt.toString());
        if (mEditFragment==null)
        {
            Log.i("TAGG", "THIS IS NULL");
        }
        mEditFragment.setPicture(pt);
        mViewPager.setCurrentItem(1);
        mActiveFragment=mEditFragment;

    }



    @Override
    public void switchFragmentRecycle() {
        Log.i("TAG", "switchFragRecy");
        setFragmentRecycleActive();
    }

    @Override
    public void setFragmentRecycleActive() {
        Log.i("TAG", "setFragRecyAct");
        mViewPager.setCurrentItem(0);
        mActiveFragment = mRecycleFragment;
    }

    @Override
    public void switchToEditFragment(PictureText pt) {
        setFragmentEditActive(pt);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    mRecycleFragment =  RecycleFragment.newInstance();
                    return mRecycleFragment;

                case 1 :
                    mEditFragment =  EditFragment.newInstance();
                    return mEditFragment;

                default :
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}