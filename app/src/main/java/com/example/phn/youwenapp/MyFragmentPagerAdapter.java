package com.example.phn.youwenapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * Created by PHN on 2016/7/4.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
   /*声明10个页面*/
    private final int PAGER_COUNT = 5;
    private carFragment carf = null;
    private newsFragment newsf = null;
    private movieFragment movief = null;
    private sportsFragment sportsf = null;
    private lessonFragment lessonf = null;


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        carf=new carFragment();
        newsf=new newsFragment();
        movief=new movieFragment();
        sportsf=new sportsFragment();
        lessonf=new lessonFragment();

    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.UP_ONE:
                fragment = carf;
                break;
            case MainActivity.UP_TWO:
                fragment = newsf;
                break;
            case MainActivity.UP_THREE:
                fragment = movief;
                break;
            case MainActivity.UP_FOUR:
                fragment = sportsf;
                break;
            case MainActivity.UP_FIVE:
                fragment = lessonf;
                break;

        }
        return fragment;
    }

}
