package com.example.phn.youwenapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * Created by PHN on 2016/7/4.
 */
public class MyFragmentPagerAdapter2 extends FragmentPagerAdapter {
   /*声明10个页面*/
    private final int PAGER_COUNT = 9;
    private downnewsFragment downnewsf =null;
    private soundFragment soundf=null;
    private  marketFragment marketf=null;
    private petFragment petf=null;
    private storeFragment storef=null;
    private jokeFragment jokef=null;
    private favourFragment favourf=null;
    private personFragment personf=null;



    public MyFragmentPagerAdapter2(FragmentManager fm) {
        super(fm);
        downnewsf=new downnewsFragment();
        marketf=new marketFragment();
        storef=new storeFragment();
        petf=new petFragment();
        jokef=new jokeFragment();
        soundf=new soundFragment();
        favourf=new favourFragment();
        personf=new personFragment();


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

                case MainActivity.DOWN_ONE:
                    fragment = downnewsf;
                    break;
                case MainActivity.DOWN_TWO:
                    fragment = soundf;
                    break;
                case MainActivity.DOWN_THREE:
                    fragment = marketf;
                    break;
                case MainActivity.DOWN_FOUR:
                    fragment = petf;
                break;
            case MainActivity.DOWN_FIVE:
                fragment = storef;
                break;
            case MainActivity.DOWN_SIX:
                fragment = jokef;
                break;
            case MainActivity.DOWN_SEVEN:
                fragment = favourf;
                break;
            case MainActivity.DOWN_EIGHT:
                fragment = personf;
                break ;
                case 8:
                    fragment=new mediaFragment(MainActivity.mediaurl);
                    break;
        }
        return fragment;
    }

}
