package com.example.phn.youwenapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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
public class lessonFragment extends Fragment {

    private Spinner lesson_type;
    private ListView mlesson;
    private lessonAdapter mlessonAdapter;
    private List<Lesson_data> mData = null;
    private Context mContext;
    private Document doc;
    private Elements es;
    private MainActivity activity;
    private Handler handler;
    public lessonFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lesson_fragment, container, false);
        bindspinner(view);
        mContext=getActivity();
        activity=(MainActivity)getActivity();
        handler=activity.handler;
        mlesson=(ListView)view.findViewById(R.id.mlesson);
        mlesson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lesson_data d=(Lesson_data)mlesson.getAdapter().getItem(position);
                Log.i("url",d.getLessonurl());
                MainActivity.mediaurl=d.getLessonurl();
                handler.sendEmptyMessage(100);

            }
        });
        return view;
    }
    public  void  bindspinner(View view){
        lesson_type =(Spinner) view.findViewById(R.id.lesson_type);
        String[] mItems = getResources().getStringArray(R.array.lesson);//获取数据数组
        ArrayAdapter<String> mAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems);//绑定spinner数据适配器
        lesson_type.setAdapter(mAdapter);
        lesson_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new Thread(new load(position)).start();            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                            mData.add(new Lesson_data(lessonurl,String.valueOf(++i),title,visit,++i));//添加到listview中
                            else
                               mData.add(new Lesson_data(lessonurl,number,title,visit,++i));
                        }

                        mlessonAdapter = new lessonAdapter((LinkedList<Lesson_data>) mData, mContext);
                        mlesson.setAdapter(mlessonAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };


}
