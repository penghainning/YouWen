package com.example.phn.youwenapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by PHN on 2016/7/4.
 */
public class movieFragment extends Fragment {
    private ListView movielist;
    private movieAdapter mmovieAdapter;
    private List<Lesson_data> mData = null;
    private Context mContext;
    private Document doc;
    private Elements es;
    private MainActivity activity;
    private Handler handler;

    public movieFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_fragment, container, false);
        mContext=getActivity();
        activity=(MainActivity)getActivity();
        new Thread(new load(0)).start();
        handler=activity.handler;
        movielist=(ListView)view.findViewById(R.id.movielist);
        movielist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lesson_data d=(Lesson_data)movielist.getAdapter().getItem(position);
                Log.i("url",d.getLessonurl());
                MainActivity.mediaurl=d.getLessonurl();
                Message message=new Message();
                message.what=300;
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
                if(n==0)
                {
                    doc = Jsoup.parse(new URL("http://top.iqiyi.com/dianshiju.html#vfrm=7-13-0-1"), 5000);
                }
                else if(n==1)
                {
                    doc = Jsoup.parse(new URL("http://top.iqiyi.com/dianying.html#vfrm=7-13-0-1"), 5000);
                }
                else if(n==2)
                {
                    doc = Jsoup.parse(new URL("http://list.iqiyi.com/www/1/----------2---11-1-1-iqiyi--.html"), 5000);
                }

                else if(n==3)
                {
                    doc=Jsoup.parse(new URL("http://list.iqiyi.com/www/1/1-------------11-1-1-iqiyi--.html"),5000);
                }
                else if(n==4)
                {
                    doc=Jsoup.parse(new URL("http://list.iqiyi.com/www/1/2-------------11-1-1-iqiyi--.html"),5000);
                }
                else
                {
                    doc=Jsoup.parse(new URL("http://list.iqiyi.com/www/1/3-------------11-1-1-iqiyi--.html"),5000);
                }


            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
                handler.sendEmptyMessage(88);
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
                        if(n<2)
                        {
                            es = doc.select("ul.tv_list>li");
                            for (Element e : es) {
                                String number = e.getElementsByTag("em").text().trim();
                                String title = e.getElementsByTag("a").attr("title").trim();
                                String lessonurl = e.getElementsByTag("a").attr("href");
                                String visit = e.getElementsByTag("span").text().trim();
                                mData.add(new Lesson_data(lessonurl,number,title,visit,++i));

                            }
                        }
                        else if(n>1) {
                            es = doc.select("ul.site-piclist*>li");
                            int j=0;
                            for (Element e : es) {
                                String number = String.valueOf(++j);
                                String title =e.getElementsByTag("a").attr("title").trim();
                                String lessonurl = e.getElementsByTag("a").attr("href");
                                String visit = e.getElementsByClass("score").text().trim();
                                mData.add(new Lesson_data(lessonurl,number,title,visit,++i));

                            }
                        }
                        mmovieAdapter= new movieAdapter((LinkedList<Lesson_data>) mData, mContext);
                        movielist.setAdapter(mmovieAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };


}
