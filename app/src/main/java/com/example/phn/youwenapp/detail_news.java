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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.List;
import java.util.Map;

/**
 * Created by PHN on 2016/7/4.
 */
public class detail_news extends Fragment implements RadioGroup.OnCheckedChangeListener{
    Document doc;
    Elements es;
    ListView newslist;
    Handler handler;
    private RadioGroup news_tap;
    private RadioButton type_one;
    private RadioButton type_two;
    List<Map<String, String>> list2;

    public detail_news() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_detail, container, false);
        MainActivity activity=(MainActivity) getActivity();
        news_tap=(RadioGroup)view.findViewById(R.id.news_tab);
        type_one=(RadioButton)view.findViewById(R.id.type_one);
        type_two=(RadioButton)view.findViewById(R.id.type_two);
        news_tap.setOnCheckedChangeListener(this);
        type_one.setChecked(true);
        handler=activity.handler;
        newslist = (ListView) view.findViewById(R.id.newslist);
        newslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> m=(Map<String,String>)newslist.getAdapter().getItem(position);
                Log.i("url",m.get("href"));
                MainActivity.mediaurl=m.get("href");
                Message message=new Message();
                message.what=100;
                message.arg1=1;
                handler.sendMessage(message);

            }
        });
        new Thread(new load(0)).start();
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
                    doc = Jsoup.parse(new URL("http://news.v1.cn/"), 5000);
                    es = doc.select("div.content1_c*,div.bottomA,div.imgDec");

                }
                else if(n==1)
                {
                    doc = Jsoup.parse(new URL("http://v.ifeng.com/news/"), 5000);
                    es = doc.select("div.v-mlist>ul>li,div.v-col-bd>ul>li");

                }

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            Message msg = new Message();
            if(n==1)
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
                            if(i<45)
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

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {

            case R.id.type_one:
                new Thread(new load(0)).start();
                type_one.setChecked(true);
                break;
            case R.id.type_two:
                new Thread(new load(1)).start();
                type_two.setChecked(true);
                break;
        }
    }



}
