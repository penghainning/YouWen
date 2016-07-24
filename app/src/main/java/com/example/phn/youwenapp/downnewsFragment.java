package com.example.phn.youwenapp;

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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


import java.util.LinkedList;

import java.util.List;
import java.util.Map;

/**
 * Created by PHN on 2016/7/4.
 */
public class downnewsFragment extends Fragment {
    ListView downnewslist;
    private List<Lesson_data> mData = null;
    Document doc;
    Elements es;
    Handler handler;

    public downnewsFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.downnews_fragment, container, false);
        downnewslist=(ListView)view.findViewById(R.id.downnewslist);
        MainActivity activity=(MainActivity) getActivity();
        handler=activity.handler;
        if(Networkutil.isNetworkAvailable(getActivity()))
            new Thread(new load()).start();
        else
            handler.sendEmptyMessage(88);
        downnewslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lesson_data d=(Lesson_data)downnewslist.getAdapter().getItem(position);
                Log.i("url",d.getLessonurl());
                MainActivity.mediaurl=d.getLessonurl();
                handler.sendEmptyMessage(600);
            }
        });
        return view;
    }

    class load extends Thread {//接受服务器信息的线程
        public void run() {
            try {

                doc = Jsoup.parse(new URL("http://www.chinanews.com/scroll-news/news1.html"),5000);
               es=doc.select("div.content_list>ul>li");
               // es=doc.select("li");
                Log.i("run: ",es.text());

            } catch (MalformedURLException e1) {
                handler.sendEmptyMessage(88);
                e1.printStackTrace();
            } catch (IOException e1) {
                handler.sendEmptyMessage(88);
                e1.printStackTrace();
            }catch (java.lang.Exception e1)
            {
                e1.printStackTrace();
            }

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
                        for (Element e : es) {

                            String h=e.text();
                            if(h.startsWith("["))
                            {
                                Element ee=e.getElementsByTag("a").get(1);
                                String num=e.select("div.dd_lm").text();
                                String title=e.select("div.dd_bt").text();
                                Log.i("handleMessage: ",num+"#"+title);
                                mData.add(new Lesson_data(ee.attr("href"),num,title,e.select("div.dd_time").text(),++i));
                            }


                        }

                         downnewsAdapter dadapter=new downnewsAdapter((LinkedList<Lesson_data>) mData,getActivity());
                         downnewslist.setAdapter(dadapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };


}
