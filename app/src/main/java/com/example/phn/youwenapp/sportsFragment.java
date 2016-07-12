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
import java.util.List;
import java.util.Map;

/**
 * Created by PHN on 2016/7/4.
 */
public class sportsFragment  extends Fragment {
private Spinner sports_type;
    Document doc;
    Elements es;
    ListView sportslist;
    Handler handler;

    public sportsFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sports_fragment, container, false);
        bindspinner(view);
        MainActivity activity=(MainActivity)getActivity();
        handler=activity.handler;
        sportslist=(ListView)view.findViewById(R.id.sportslist);
        sportslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> m=(Map<String,String>)sportslist.getAdapter().getItem(position);
                Log.i("url",m.get("href"));
                MainActivity.mediaurl=m.get("href");
                handler.sendEmptyMessage(100);
            }
        });
        return view;
    }
    public  void  bindspinner(View view){
        sports_type =(Spinner) view.findViewById(R.id.sports_type);
        String[] mItems = getResources().getStringArray(R.array.sports);//获取数据数组
        ArrayAdapter<String> mAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems);//绑定spinner数据适配器
        sports_type.setAdapter(mAdapter);
        sports_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new Thread(new load(position)).start();
            }

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
