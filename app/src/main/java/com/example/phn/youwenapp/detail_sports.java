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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by PHN on 2016/7/4.
 */
public class detail_sports extends Fragment implements RadioGroup.OnCheckedChangeListener{
    private ListView sportslist;
    private Context mContext;
    private Document doc;
    private Elements es;
    private MainActivity activity;
    private Handler handler;
    private RadioGroup sports_tab;
    private RadioButton type_football;
    private RadioButton type_show;
    private RadioButton type_basketball;
    private RadioButton type_table;
    private RadioButton type_other;

    public detail_sports() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sports_detail, container, false);
        bindview(view);
        return view;
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {

            case R.id.type_show:
                new Thread(new load(0)).start();
                type_show.setChecked(true);
                break;
            case R.id.type_football:
                new Thread(new load(1)).start();
                type_football.setChecked(true);
                break;
            case R.id.type_basketball:
                new Thread(new load(2)).start();
                type_basketball.setChecked(true);
                break;
            case R.id.type_table:
                new Thread(new load(3)).start();
                type_table.setChecked(true);
                break;
            case R.id.type_other:
                new Thread(new load(4)).start();
                type_other.setChecked(true);
                break;
        }
    }


    public void bindview(View view)
    {
        sports_tab=(RadioGroup)view.findViewById(R.id.sports_tab);
        type_table=(RadioButton)view.findViewById(R.id.type_table);
        type_football=(RadioButton)view.findViewById(R.id.type_football);
        type_show=(RadioButton)view.findViewById(R.id.type_show);
        type_show.setChecked(true);
        type_basketball=(RadioButton)view.findViewById(R.id.type_basketball);
        type_other=(RadioButton)view.findViewById(R.id.type_other);
        sports_tab.setOnCheckedChangeListener(this);
        sportslist=(ListView)view.findViewById(R.id.sportslist);
        mContext=getActivity();
        activity=(MainActivity)getActivity();
        new Thread(new load(0)).start();
        handler=activity.handler;
        sportslist=(ListView)view.findViewById(R.id.sportslist);
        sportslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> m=(Map<String,String>)sportslist.getAdapter().getItem(position);
                Log.i("url",m.get("href"));
                MainActivity.mediaurl=m.get("href");
                Message message=new Message();
                message.what=400;
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
                if(n==0)
                {
                    doc = Jsoup.parse(new URL("http://live.qq.com/directory/all"), 5000);

                }
                else if(n==1)
                {
                    doc = Jsoup.parse(new URL("http://live.qq.com/directory/game/Football"), 5000);
                }
                else if(n==2)
                {
                    doc = Jsoup.parse(new URL("http://live.qq.com/directory/game/Basketball"), 5000);
                }
                else if(n==3)
                {
                    doc = Jsoup.parse(new URL("http://live.qq.com/directory/game/Billiards"), 5000);
                }
                else if(n==4)
                {
                    doc = Jsoup.parse(new URL("http://live.qq.com/directory/game/Others"), 5000);
                }
                es = doc.select("ul#live-list-contentbox>li");

            } catch (MalformedURLException e1) {
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
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("title", String.valueOf(++i)+" :"+e.getElementsByTag("a").attr("title"));
                            map.put("href", "http://live.qq.com"+e.getElementsByTag("a").attr("href"));
                            list.add(map);
                        }


                        sportslist.setAdapter(new SimpleAdapter(getActivity(), list, android.R.layout.simple_list_item_1,
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
