package com.example.phn.youwenapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by PHN on 2016/7/4.
 */
public class detail_lesson extends Fragment implements RadioGroup.OnCheckedChangeListener{
    private ListView lessonlist;
    private lessonAdapter mlessonAdapter;
    private List<Lesson_data> mData = null;
    private Context mContext;
    private Document doc;
    private Elements es;
    private MainActivity activity;
    private Handler handler;
    private RadioGroup lesson_tab;
    private RadioButton type_all;
    private RadioButton type_month;
    private RadioButton type_week;
    private RadioButton type_recent;

    public detail_lesson() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lesson_detail, container, false);
        bindview(view);
        return view;
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {

            case R.id.type_all:
                new Thread(new load(0)).start();
                type_all.setChecked(true);
                break;
            case R.id.type_week:
                new Thread(new load(1)).start();
                type_week.setChecked(true);
                break;
            case R.id.type_month:
                new Thread(new load(2)).start();
                type_month.setChecked(true);
                break;
            case R.id.type_recent:
                new Thread(new load(3)).start();
                type_recent.setChecked(true);
                break;
        }
    }


    public void bindview(View view)
    {
        lesson_tab=(RadioGroup)view.findViewById(R.id.lesson_tab);
        type_all=(RadioButton)view.findViewById(R.id.type_all);
        type_week=(RadioButton)view.findViewById(R.id.type_week);
        type_month=(RadioButton)view.findViewById(R.id.type_month);
        type_recent=(RadioButton)view.findViewById(R.id.type_recent);
        lesson_tab.setOnCheckedChangeListener(this);
        mContext=getActivity();
        activity=(MainActivity)getActivity();
        handler=activity.handler;
        type_all.setChecked(true);
        new Thread(new load(0)).start();
        lessonlist=(ListView)view.findViewById(R.id.lessonlist);
        lessonlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lesson_data d=(Lesson_data)lessonlist.getAdapter().getItem(position);
                Log.i("url",d.getLessonurl());
                MainActivity.mediaurl=d.getLessonurl();
                Message message=new Message();
                message.what=200;
                message.arg1=1;
                handler.sendMessage(message);


            }
        });

    }
    class load extends Thread {//接受服务器信息的线程
        private int n;
        load(int n){
            this.n=n;
        }
        public void run() {
            try {
                doc = Jsoup.parse(new URL("http://open.sina.com.cn/"), 5000);//获取document
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                handler.sendEmptyMessage(88);
                e1.printStackTrace();
            }
            if(n==0)
            {
                es = doc.select("div#sub02_c1>ul.list02>li");//总排行
            }
            else if(n==1)
            {
                es = doc.select("div#sub02_c2>ul.list02>li");//月排行
            }
            else if(n==2)
            {
                es = doc.select("div#sub02_c3>ul.list02>li");//周排行
            }
            else if(n==3)
            {
                es = doc.select("div#sub01_c1>ul.list01>li");//最近课程
            }
            Message msg = new Message();
            msg.what = 0;
            Bundle bundle = new Bundle();
            bundle.clear();
            bundle.putInt("num",n);
            msg.setData(bundle);
            myHandler.sendMessage(msg);

        }
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {    //接受服务器信息更新UI
            switch (msg.what) {
                case 0:
                    try {
                        Bundle bundle = msg.getData();
                        int n=bundle.getInt("num");
                        mData=new LinkedList<>();
                        int i=0;
                        for (Element e : es)
                        {
                            String number=e.getElementsByTag("em").text().trim();
                            String title=e.getElementsByTag("a").text().trim();
                            String lessonurl=e.getElementsByTag("a").attr("href");
                            String visit=e.getElementsByTag("span").text().trim();
                            Log.i("0",String.valueOf(n));
                            Log.i("1",number);
                            Log.i("2",title);
                            Log.i("3",lessonurl);
                            Log.i("4",visit);
                            if(n==3)
                                mData.add(new Lesson_data(lessonurl,String.valueOf(++i),title,visit,i));//添加到listview中
                            else
                                mData.add(new Lesson_data(lessonurl,number,title,visit,++i));
                        }

                        mlessonAdapter = new lessonAdapter((LinkedList<Lesson_data>) mData, mContext);
                        lessonlist.setAdapter(mlessonAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };




}
