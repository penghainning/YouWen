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
        new Thread(new load(0)).start();
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
                Message message=new Message();
                message.what=200;
                message.arg1=0;
                handler.sendMessage(message);
            }
        });
        return view;
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
                           if(n==3)
                            mData.add(new Lesson_data(lessonurl,String.valueOf(++i),title,visit,i));//添加到listview中
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
