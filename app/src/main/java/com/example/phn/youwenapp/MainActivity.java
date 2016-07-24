package com.example.phn.youwenapp;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener  {

    /*声明所有控件*/
    public static String mediaurl;
    private EditText searchText;
    private Button search;
    private Button back;
    private RadioGroup up_tab;
    private RadioGroup down_tab;
    private RadioButton car;
    private RadioButton news;
    private RadioButton movie;
    private RadioButton sports;
    private RadioButton lesson;
    private RadioButton downnews;
    private RadioButton sound;
    private RadioButton market;
    private RadioButton fun;
    private RadioButton joke;
    private RadioButton carbreak;
    private RadioButton person;
    private ViewPager up_viewpager;
    private ViewPager down_viewpager;
    private MyFragmentPagerAdapter mAdapter;
    private MyFragmentPagerAdapter2 mAdapter2;
    private HorizontalScrollView hv;
    private LinearLayout hide1;
    private LinearLayout hide2;
    private FragmentManager fManager = null;
    private FragmentTransaction ft=null;
    private searchFragment searchf=null;
    private int width;
    private int backtype;
    private boolean ismain=true;
    public static int type=0;
    public static final int UP_ONE = 0;
    public static final int UP_TWO = 1;
    public static final int UP_THREE = 2;
    public static final int UP_FOUR = 3;
    public static final int UP_FIVE = 4;
    public static final int DOWN_ONE = 0;
    public static final int DOWN_TWO = 1;
    public static final int DOWN_THREE = 2;
    public static final int DOWN_FOUR = 3;
    public static final int DOWN_FIVE = 4;
    public static final int DOWN_SIX = 5;
    public static final int DOWN_SEVEN = 6;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                {
                    mySearch();
                    return true;
                }
                return false;
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mySearch();
            }
        });
    }

    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg!=null){
                Bundle bundle=msg.getData();
                mediaFragment mediaf=null;
                switch (msg.what) {
                    case 100://查看新闻
                        Log.i("Main_lessonurl",mediaurl);
                         mediaf=new mediaFragment(mediaurl,1);
                        if(msg.arg1==1)
                            backtype=-1;
                        else
                        backtype=1;
                        Myrelace(mediaf);
                        break;
                    case 200://查看公开课
                        Log.i("Main_lessonurl",mediaurl);
                        mediaf=new mediaFragment(mediaurl,1);
                        if(msg.arg1==1)
                            backtype=-1;
                        else
                        backtype=2;
                        Myrelace(mediaf);
                        break;
                    case 300://查看娱乐
                        Log.i("Main_lessonurl",mediaurl);
                        mediaf=new mediaFragment(mediaurl,1);
                        if(msg.arg1==1)
                            backtype=-1;
                        else
                        backtype=3;
                        Myrelace(mediaf);
                        break;
                    case 400://查看体育
                        Log.i("Main_lessonurl",mediaurl);
                        mediaf=new mediaFragment(mediaurl,1);
                        if(msg.arg1==1)
                            backtype=-1;
                        else
                            backtype=4;
                        Myrelace(mediaf);
                        break;
                    case 500://查看汽车
                        detail_car dc=new detail_car();
                        backtype=100;
                        Myrelace(dc);
                        break;
                    case 101://地区民声选择了地区
                        type=1;
                        break;
                    case 102://收起按钮及返回
                        searchText.setText("");
                        if(up_viewpager.getVisibility()==View.GONE)
                            up_viewpager.setVisibility(View.VISIBLE);
                        if(down_viewpager.getVisibility()==View.GONE)
                            down_viewpager.setVisibility(View.VISIBLE);
                        if(up_tab.getVisibility()==View.GONE)
                            up_tab.setVisibility(View.VISIBLE);
                        if(down_tab.getVisibility()==View.GONE)
                            down_tab.setVisibility(View.VISIBLE);
                        fManager.popBackStack();
                        break;
                    case 103://发布信息
                        publishFragment publishf=new publishFragment();
                        publishf.setType(msg.arg1);
                        if(fManager.getBackStackEntryCount() != 0)
                            fManager.popBackStack();
                        Myrelace(publishf);
                        break;
                    case 600://点击文字新闻
                        Log.i("Main_lessonurl",mediaurl);
                        mediaf=new mediaFragment(mediaurl,2);
                            backtype=6;
                        Myrelace(mediaf);
                        break;
                    case 601://点击文字新闻
                        backtype=1;
                        onBackPressed();
                        break;
                    case 700://点击地区民生
                        Log.i("Main_lessonurl",mediaurl);
                        mediaf=new mediaFragment(mediaurl,1);
                        backtype=7;
                        Myrelace(mediaf);
                        break;
                    case 800://点击吃喝玩乐
                        detail_fun df=new detail_fun();
                        backtype=8;
                        Myrelace(df);
                        break;
                    case 900://点击二手市场
                        detail_market mf=new detail_market();
                        backtype=9;
                        Myrelace(mf);
                        break;
                    case 1000://点击段子
                       detail_joke dj=new detail_joke();
                        backtype=10;
                        Myrelace(dj);
                        break;
                    case 105://发布地区民生
                        Toast.makeText(MainActivity.this,"发布成功！",Toast.LENGTH_SHORT).show();
                        backtype=-1;
                        onBackPressed();
                        break;
                    case 106://发布二手市场
                        Toast.makeText(MainActivity.this,"发布成功！",Toast.LENGTH_SHORT).show();
                        backtype=-1;
                        onBackPressed();
                        break;
                    case 107://发布吃喝玩乐
                       /* funFragment f2=(funFragment) down_viewpager.getAdapter().instantiateItem(down_viewpager,2);
                        Map<String, String> map3 = new HashMap<String, String>();
                        map3.put("title",bundle.getString("title"));
                        map3.put("content",bundle.getString("content"));
                        f2.loaddata(map3);*/
                        Toast.makeText(MainActivity.this,"发布成功！",Toast.LENGTH_SHORT).show();
                        backtype=-1;
                        onBackPressed();
                        break;
                    case 999://返回上一级
                        if(msg.arg1==1)
                            backtype=71;
                        else
                           backtype=6+msg.arg1;
                        onBackPressed();
                        break;
                    case 88://网络异常
                        Toast.makeText(MainActivity.this,"网络异常，请检查你的网络",Toast.LENGTH_SHORT).show();
                    default:
                        break;
                }
            }
        }

    };

    private void bindViews() {

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        width = outMetrics.widthPixels/5;
        fManager=getSupportFragmentManager();
        hide1=(LinearLayout)findViewById(R.id.hide1);
        hide2=(LinearLayout)findViewById(R.id.hide2);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mAdapter2 = new MyFragmentPagerAdapter2(getSupportFragmentManager());
        searchText=(EditText)findViewById(R.id.searchtext);
        search=(Button)findViewById(R.id.search);
        back=(Button)findViewById(R.id.back) ;
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handler.sendEmptyMessage(102);
            }
        });
        up_tab=(RadioGroup)findViewById(R.id.up_tab);
        down_tab=(RadioGroup)findViewById(R.id.down_tab);
        car=(RadioButton)findViewById(R.id.car);
        news=(RadioButton)findViewById(R.id.news);
        movie=(RadioButton)findViewById(R.id.movie);
        sports=(RadioButton)findViewById(R.id.sports);
        lesson=(RadioButton)findViewById(R.id.lesson);
        downnews=(RadioButton)findViewById(R.id.down_news);
        downnews.setWidth(width);
        sound=(RadioButton)findViewById(R.id.sound);
        sound.setWidth(width);
        market=(RadioButton)findViewById(R.id.market);
        market.setWidth(width);
        fun=(RadioButton)findViewById(R.id.fun);
        fun.setWidth(width);
        joke=(RadioButton)findViewById(R.id.joke);
        joke.setWidth(width);
        carbreak=(RadioButton)findViewById(R.id.carbreak);
        carbreak.setWidth(width);
        person=(RadioButton)findViewById(R.id.person);
        person.setWidth(width);
        hv=(HorizontalScrollView)findViewById(R.id.hv);
        up_viewpager=(ViewPager)findViewById(R.id.up_viewpager);
        down_viewpager=(ViewPager)findViewById(R.id.down_viewpager);
        up_viewpager.setAdapter(mAdapter);
        down_viewpager.setAdapter(mAdapter2);
        up_viewpager.setCurrentItem(0);
        down_viewpager.setCurrentItem(0);
        down_viewpager.setOffscreenPageLimit(7);
        up_viewpager.setOffscreenPageLimit(4);
        up_tab.setOnCheckedChangeListener(this);
        down_tab.setOnCheckedChangeListener(this);
        up_viewpager.addOnPageChangeListener(this);
        down_viewpager.addOnPageChangeListener(this);

    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {

            case R.id.car:
                up_viewpager.setCurrentItem(UP_FIVE,false);
                break;
            case R.id.news:
                up_viewpager.setCurrentItem(UP_ONE,false);
                break;
            case R.id.movie:
                up_viewpager.setCurrentItem(UP_THREE,false);
                break;
            case R.id.sports:
                up_viewpager.setCurrentItem(UP_FOUR,false);
                break;
            case R.id.lesson:
                up_viewpager.setCurrentItem(UP_TWO,false);
                break;
            case R.id.down_news:
                down_viewpager.setCurrentItem(DOWN_ONE,false);
                break;
            case R.id.sound:
                down_viewpager.setCurrentItem(DOWN_TWO,false);
                break;
            case R.id.fun:
                down_viewpager.setCurrentItem(DOWN_THREE,false);
                hv.arrowScroll(View.FOCUS_LEFT);
                break;
            case R.id.market:
                down_viewpager.setCurrentItem(DOWN_FOUR,false);
                break;
            case R.id.carbreak:
                down_viewpager.setCurrentItem(DOWN_FIVE,false);
                hv.arrowScroll(View.FOCUS_RIGHT);
                break;
            case R.id.joke:
                down_viewpager.setCurrentItem(DOWN_SIX,false);
                break;
            case R.id.person:
                down_viewpager.setCurrentItem(DOWN_SEVEN,false);
                break;
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            switch (up_viewpager.getCurrentItem()) {
                case UP_ONE:
                    news.setChecked(true);
                    break;
                case UP_TWO:
                    lesson.setChecked(true);
                    break;
                case UP_THREE:
                    movie.setChecked(true);
                    break;
                case UP_FOUR:
                    sports.setChecked(true);
                    break;
                case UP_FIVE:
                    car.setChecked(true);
                    break;
            }

            switch (down_viewpager.getCurrentItem()) {
                case DOWN_ONE:
                    downnews.setChecked(true);
                    break;
                case DOWN_TWO:
                    sound.setChecked(true);
                    break;
                case DOWN_THREE:
                    fun.setChecked(true);
                    break;
                case DOWN_FOUR:
                    market.setChecked(true);
                    break;
                case DOWN_FIVE:
                    carbreak.setChecked(true);
                    break;
                case DOWN_SIX:
                    joke.setChecked(true);
                    break;
                case DOWN_SEVEN:
                    joke.setChecked(true);
                    break;

            }
        }
    }
    public void onBackPressed() {
        Log.i("backtype: ",String.valueOf(fManager.getBackStackEntryCount()));
        //返回类型为新闻
        if(backtype==1)
        {
            fManager.popBackStack();
            Log.i("onBackPressed: ","返回新闻列表");
            detail_news ns=new detail_news();
            Myrelace(ns);
        }
        //返回类型为公开课
        else if(backtype==2)
        {
            fManager.popBackStack();
            Log.i("onBackPressed: ","返回公开课列表");
            detail_lesson ls=new detail_lesson();
            Myrelace(ls);
        }
        //返回类型为娱乐
        else if(backtype==3)
        {
            fManager.popBackStack();
            Log.i("onBackPressed: ","返回娱乐列表");
            detail_movie dm=new detail_movie();
            Myrelace(dm);
        }
        //返回类型为体育
        else if(backtype==4)
        {
            fManager.popBackStack();
            Log.i("onBackPressed: ","返回体育列表");
            detail_sports sp=new detail_sports();
            Myrelace(sp);
        }
        //返回类型为汽车
        else if(backtype==5)
        {
            fManager.popBackStack();
            Log.i("onBackPressed: ","返回汽车列表");
        }
        //返回类型为文字新闻
        else if(backtype==6)
        {
            if(ismain)
            {
                fManager.popBackStack();
                Log.i("onBackPressed: ","返回文字新闻列表");
                downnewsFragment df=new downnewsFragment();
                Myrelace(df);
                ismain=false;
            }
            else
            {
                fManager.popBackStack();
            }

        }
        //返回类型为地区民生
        else if(backtype==7)
        {
            if(ismain)
            {
                fManager.popBackStack();
                Log.i("onBackPressed: ","返回地区民生列表");
                detail_sound ds=new detail_sound();
                Myrelace(ds);
                ismain=false;
            }
            else
            {
                fManager.popBackStack();
            }

        }
        else if(backtype==71)
        {

                fManager.popBackStack();
                Log.i("onBackPressed: ","返回地区民生列表");
                detail_sound ds=new detail_sound();
                Myrelace(ds);
                ismain=false;

        }
        else if(backtype==8)
        {

            fManager.popBackStack();
            Log.i("onBackPressed: ","返回二手市场列表");
            detail_market dm=new detail_market();
            Myrelace(dm);
            ismain=false;

        }

        else if(backtype==9)
        {

                fManager.popBackStack();
                Log.i("onBackPressed: ","返回吃喝玩乐列表");
                detail_fun df=new detail_fun();
                Myrelace(df);
                ismain=false;

        }
        //返回类型为段子
        else if(backtype==10)
        {
            if(ismain)
            {
                fManager.popBackStack();
                Log.i("onBackPressed: ","返回段子列表");
                jokeFragment jf=new jokeFragment();
                Myrelace(jf);
                ismain=false;
            }
            else
            {
                fManager.popBackStack();
            }

        }
        //普通返回
        else if(backtype==-1)
        {
            fManager.popBackStack();
        }

        else
        {
            Log.i("onBackPressed: ","返回主页面");
            ismain=true;
            if(fManager.getBackStackEntryCount()>0)
                  fManager.popBackStack();
            if(up_viewpager.getVisibility()==View.GONE)
                up_viewpager.setVisibility(View.VISIBLE);
            if(down_viewpager.getVisibility()==View.GONE)
                down_viewpager.setVisibility(View.VISIBLE);
            if(up_tab.getVisibility()==View.GONE)
                up_tab.setVisibility(View.VISIBLE);
            if(down_tab.getVisibility()==View.GONE)
                down_tab.setVisibility(View.VISIBLE);
            if(fManager.getBackStackEntryCount()==0)
                super.onBackPressed();
        }
        backtype=0;



    }
    public void mySearch()
    {
        if("".equals(searchText.getText().toString()))
        {
            Toast.makeText(MainActivity.this,"内容不能为空", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String text=searchText.getText().toString();
            mediaurl="https://www.baidu.com/s?wd="+text;
            searchf=new searchFragment(mediaurl);
            ft = fManager.beginTransaction();
            if(fManager.findFragmentByTag("search")!= null)
            {
                fManager.popBackStack();
            }

            ft.setCustomAnimations(R.anim.fragment_slide_in_from_top,R.anim.fragment_slide_out_to_top,R.anim.fragment_slide_in_from_top,R.anim.fragment_slide_out_to_top);
            ft.replace(R.id.webreplace,searchf,"search");
            ft.addToBackStack(null);
            ft.commit();

        }
    }
    public void Myrelace(Fragment f)
    {
        up_viewpager.setVisibility(View.GONE);
        down_viewpager.setVisibility(View.GONE);
        up_tab.setVisibility(View.GONE);
        down_tab.setVisibility(View.GONE);
        ft = fManager.beginTransaction();
        ft.replace(R.id.webreplace,f);
        ft.addToBackStack(null);
        ft.commit();
    }
}
