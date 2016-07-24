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
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PHN on 2016/7/4.
 */
public class jokeFragment extends Fragment {
    Document doc;
    Elements es;
    ListView jokelist;
    Handler handler;


    public jokeFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.joke_fragment, container, false);
        MainActivity activity=(MainActivity) getActivity();
        handler=activity.handler;
        jokelist = (ListView) view.findViewById(R.id.jokelist);
        if(Networkutil.isNetworkAvailable(getActivity()))
        new Thread(new load()).start();
        else
            handler.sendEmptyMessage(88);
        jokelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> m=(Map<String,String>)jokelist.getAdapter().getItem(position);
                MainActivity.mediaurl=m.get("title");
                handler.sendEmptyMessage(1000);
            }
        });
        return view;
    }

    class load extends Thread {//接受服务器信息的线程
        public void run() {
            try {

                    doc = Jsoup.parse(new URL("http://ishuo.cn/duanzi"), 5000);
                    es = doc.select("div#list>ul>li>div.content");



            } catch (MalformedURLException e1) {
                e1.printStackTrace();
                handler.sendEmptyMessage(88);
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
                            Map<String, String> map = new HashMap<String, String>();
                           // map.put("title", String.valueOf(++i)+" :"+e.getElementsByTag("a").text());
                            //map.put("href", "http://ishuo.cn"+e.getElementsByTag("a").attr("href"));
                            map.put("title", String.valueOf(++i)+" :\n"+e.text());
                            list.add(map);
                        }


                        jokelist.setAdapter(new SimpleAdapter(getActivity(), list, R.layout.joke_layout,
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
