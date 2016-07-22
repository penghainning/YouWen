package com.example.phn.youwenapp;

import android.os.Handler;
import android.os.Message;
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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
    private FrameLayout fl;
    private FragmentManager fManager = null;
    private FragmentTransaction ft=null;
    private searchFragment searchf=null;
    private int width;
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
        fManager=getSupportFragmentManager();
        fl=(FrameLayout)findViewById(R.id.webreplace);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mAdapter2 = new MyFragmentPagerAdapter2(getSupportFragmentManager());
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
                switch (msg.what) {
                    case 100://查看新闻、体育、公开课、影视、
                        Log.i("Main_lessonurl",mediaurl);
                        mediaFragment mediaf=new mediaFragment(mediaurl,1);
                        break;
                    case 101://地区民声选择了地区
                        type=1;
                        break;
                    case 102://收起按钮及返回
                        if(up_viewpager.getVisibility()==View.GONE)
                            up_viewpager.setVisibility(View.VISIBLE);
                        if(down_viewpager.getVisibility()==View.GONE)
                            down_viewpager.setVisibility(View.VISIBLE);
                        searchText.setText("");
                        fManager.popBackStack();
                        break;
                    case 103://发布信息
                        publishFragment publishf=new publishFragment();
                        if(fManager.getBackStackEntryCount() != 0)
                            fManager.popBackStack();
                        ft = fManager.beginTransaction();
                        ft.replace(R.id.webreplace,publishf);
                        up_viewpager.setVisibility(View.GONE);
                        down_viewpager.setVisibility(View.GONE);
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case 104://点击文字新闻
                        Log.i("Main_lessonurl",mediaurl);
                        mediaFragment downmedia=new mediaFragment(mediaurl,2);
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case 105://发布地区民生
                        soundFragment2 s2=(soundFragment2) down_viewpager.getAdapter().instantiateItem(down_viewpager,1);
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("title",bundle.getString("title"));
                        map.put("content",bundle.getString("content"));
                        s2.loaddata(map);
                        Toast.makeText(MainActivity.this,"发布成功！",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        break;
                    case 106://发布二手市场
                        marketFragment market=(marketFragment) down_viewpager.getAdapter().instantiateItem(down_viewpager,2);
                        Map<String, String> map2= new HashMap<String, String>();
                        map2.put("title",bundle.getString("title"));
                        map2.put("content",bundle.getString("content"));
                        market.loaddata(map2);
                        Toast.makeText(MainActivity.this,"发布成功！",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        break;
                    case 107://发布宠物
                        Map<String, String> map3= new HashMap<String, String>();
                        map3.put("title",bundle.getString("title"));
                        map3.put("content",bundle.getString("content"));
                        petFragment pet=(petFragment)down_viewpager.getAdapter().instantiateItem(down_viewpager,3);
                        pet.loaddata(map3);
                        Toast.makeText(MainActivity.this,"发布成功！",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        break;
                    case 108://发布新店开张
                        Map<String, String> map4= new HashMap<String, String>();
                        map4.put("title",bundle.getString("title"));
                        map4.put("content",bundle.getString("content"));
                        storeFragment store=(storeFragment)down_viewpager.getAdapter().instantiateItem(down_viewpager,4);
                        store.loaddata(map4);
                        Toast.makeText(MainActivity.this,"发布成功！",Toast.LENGTH_SHORT).show();
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
        searchText=(EditText)findViewById(R.id.searchtext);
        search=(Button)findViewById(R.id.search);
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
                break;
            case R.id.market:
                down_viewpager.setCurrentItem(DOWN_FOUR,false);
                break;
            case R.id.carbreak:
                down_viewpager.setCurrentItem(DOWN_FIVE,false);
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
                    car.setChecked(true);
                    break;
                case UP_TWO:
                    news.setChecked(true);
                    break;
                case UP_THREE:
                    movie.setChecked(true);
                    break;
                case UP_FOUR:
                    sports.setChecked(true);
                    break;
                case UP_FIVE:
                    lesson.setChecked(true);
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
                    hv.arrowScroll(View.FOCUS_LEFT);
                    break;
                case DOWN_FOUR:
                    market.setChecked(true);
                    break;
                case DOWN_FIVE:
                    carbreak.setChecked(true);
                    hv.arrowScroll(View.FOCUS_RIGHT);
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
        if(up_viewpager.getVisibility()==View.GONE)
           up_viewpager.setVisibility(View.VISIBLE);
        if(down_viewpager.getVisibility()==View.GONE)
            down_viewpager.setVisibility(View.VISIBLE);
        super.onBackPressed();
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
}
