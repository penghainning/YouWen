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
        mContext=getActivity();
        activity=(MainActivity)getActivity();
        handler=activity.handler;
        if(Networkutil.isNetworkAvailable(getActivity()))
            new Thread(new load()).start();
        else
            handler.sendEmptyMessage(88);
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
        public void run() {
            try {
                doc = Jsoup.parse(new URL("http://open.sina.com.cn/"), 5000);//获取document
            } catch (MalformedURLException e1) {
                handler.sendEmptyMessage(88);
                e1.printStackTrace();
            } catch (IOException e1) {
                handler.sendEmptyMessage(88);
                e1.printStackTrace();
            }

                es = doc.select("div#sub02_c1>ul.list02>li");//总排行

            Message msg = new Message();
            msg.what = 0;
            myHandler.sendMessage(msg);

        }
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {    //接受服务器信息更新UI
            switch (msg.what) {
                case 0:
                    try {
                        mData=new LinkedList<>();
                        int i=0;
                        for (Element e : es)
                        {
                            if(i<6)
                            {
                                String number=e.getElementsByTag("em").text().trim();
                                String title=e.getElementsByTag("a").text().trim();
                                String lessonurl=e.getElementsByTag("a").attr("href");
                                String visit=e.getElementsByTag("span").text().trim();
                                mData.add(new Lesson_data(lessonurl,number,title,visit,++i));
                            }
                            else
                                break;

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
