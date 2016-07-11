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
public class downnewsFragment extends Fragment {
    ListView downnewslist;
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
        new  Thread(new load()).start();
        downnewslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> m=(Map<String,String>)downnewslist.getAdapter().getItem(position);
                Log.i("url",m.get("href"));
                MainActivity.mediaurl=m.get("href");
                handler.sendEmptyMessage(100);
            }
        });
        return view;
    }

    class load extends Thread {//接受服务器信息的线程
        public void run() {
            try {

                doc = Jsoup.parse(new URL("http://roll.news.sina.com.cn/s/channel.php?ch=01#col=89&spec=&type=&ch=01&k=&offset_page=0&offset_num=0&num=60&asc=&page=1"),5000);
                es=doc.select("div#d_list>ul>li");

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
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
                            map.put("title",String.valueOf(++i)+": "+ e.getElementsByClass("c_tit").tagName("a").text());
                            map.put("href",  e.getElementsByTag("a").attr("href"));
                            list.add(map);
                        }


                        downnewslist.setAdapter(new SimpleAdapter(getActivity(), list, android.R.layout.simple_list_item_1,
                                new String[] { "title","href" }, new int[] {
                                android.R.id.text1,android.R.id.text2
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
