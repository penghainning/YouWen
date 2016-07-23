package com.example.phn.youwenapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

/**
 * Created by PHN on 2016/7/4.
 */
public class MyFragmentPagerAdapter2 extends FragmentPagerAdapter {
   /*声明10个页面*/
    private final int PAGER_COUNT = 7;
    private FragmentManager fm;
    private downnewsFragment downnewsf =null;
    private Fragment soundf=null;
    private Fragment soundf2=null;
    private  marketFragment marketf=null;
    private jokeFragment jokef=null;
    private funFragment funf=null;
    private carbreakFragment carbreakf=null;
    private personFragment personf=null;


    public MyFragmentPagerAdapter2(FragmentManager fm) {
        super(fm);
        this.fm=fm;
        downnewsf=new downnewsFragment();
        marketf=new marketFragment();
        jokef=new jokeFragment();
        soundf=new soundFragment();
        soundf2=new soundFragment2();
        personf=new personFragment();
        funf=new funFragment();
        carbreakf=new carbreakFragment();


    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
        public Object instantiateItem(ViewGroup vg, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(vg, position);
       if (MainActivity.type==1&&position==1)
        {

            String fragmentTag = fragment.getTag();
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fragment);
            fragment=new soundFragment2();
            ft.add(vg.getId(), fragment, fragmentTag);
            ft.attach(fragment);
            ft.commit();
            MainActivity.type=0;
        }
      return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            System.out.println("position Destory" + position);
            if(position==1)
                soundf=new soundFragment2();
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
                    fragment = funf;
                    break;
                case MainActivity.DOWN_FOUR:
                   fragment = marketf;
                break;
            case MainActivity.DOWN_FIVE:
               fragment =carbreakf;
                break;
            case MainActivity.DOWN_SIX:
                fragment = jokef;
                break;
            case MainActivity.DOWN_SEVEN:
               fragment = personf;
                break;
        }
        return fragment;
    }

}
