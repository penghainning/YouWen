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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

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
public class newsFragment extends Fragment {
    private Spinner news_type;
    Document doc;
    Elements es;
    ListView newslist;
    Handler handler;
    List<Map<String, String>> list2;
    public newsFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        MainActivity activity=(MainActivity) getActivity();
        handler=activity.handler;
        newslist = (ListView) view.findViewById(R.id.newslist);
        newslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> m=(Map<String,String>)newslist.getAdapter().getItem(position);
                Log.i("url",m.get("href"));
                MainActivity.mediaurl=m.get("href");
                handler.sendEmptyMessage(100);
            }
        });
        if(Networkutil.isNetworkAvailable(getActivity()))
            new Thread(new load()).start();
        else
            handler.sendEmptyMessage(88);
        return view;
    }


    class load extends Thread {//接受服务器信息的线程
        public void run() {
            try {

                    doc = Jsoup.parse(new URL("http://news.v1.cn/"), 5000);
                    es = doc.select("div.content1_c*,div.bottomA,div.imgDec");

            } catch (MalformedURLException e1) {
                handler.sendEmptyMessage(88);
                e1.printStackTrace();
            } catch (IOException e1) {
                handler.sendEmptyMessage(88);
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
                        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                        int i=0;
                        for (Element e : es) {
                            if(i<6)
                            {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("title", e.getElementsByTag("a").text());
                                map.put("href", e.getElementsByTag("a").attr("href"));
                                list.add(map);
                                i++;
                            }
                            else
                                break;
                        }


                        newslist.setAdapter(new SimpleAdapter(getActivity(), list, R.layout.news_layout,
                                new String[] {"title" }, new int[] {
                                R.id.text1
                        }));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };



}
