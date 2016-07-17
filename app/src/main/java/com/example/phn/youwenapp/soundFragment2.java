package com.example.phn.youwenapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PHN on 2016/7/4.
 */
public class soundFragment2 extends Fragment {
    ListView soundlist;
    Document doc;
    Elements es;
    Handler handler;
    RadioGroup citiselect;
    RadioButton city1;
    RadioButton city2;
    LinearLayout bottom;
    ProgressDialog progress;
    List<Map<String, String>> list;
    public soundFragment2() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sound_fragment2, container, false);
        soundlist=(ListView)view.findViewById(R.id.soundlist);
        city1=(RadioButton)view.findViewById(R.id.city1);
        city2=(RadioButton)view.findViewById(R.id.city2);
        bottom=(LinearLayout)view.findViewById(R.id.bottom);
        list = new ArrayList<Map<String, String>>();
        citiselect=(RadioGroup)view.findViewById(R.id.citiselect);
        citiselect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.city1:
                        progress= ProgressDialog.show(getActivity(),"", "正在加载数据,请稍候...");
                        new Thread(new load(1)).start();
                        city1.setChecked(true);
                        break;
                    case R.id.city2:
                        progress= ProgressDialog.show(getActivity(),"", "正在加载数据,请稍候...");
                        new Thread(new load(2)).start();
                        city2.setChecked(true);
                        break;
            }
        }});
        MainActivity activity=(MainActivity) getActivity();
        handler=activity.handler;
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(103);
            }
        });
        new Thread(new load(1)).start();
        soundlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> m=(Map<String,String>)soundlist.getAdapter().getItem(position);
                Log.i("url",m.get("href"));
                MainActivity.mediaurl=m.get("href");
                handler.sendEmptyMessage(100);
            }
        });
        return view;
    }

    class load extends Thread {//接受服务器信息的线程
        private  int n;
        load(int n){this.n=n;}
        public void run() {
            try {
                if(n==1)
                {
                    doc = Jsoup.parse(new URL("http://www.sznews.com/"),5000);
                    es=doc.select("div.newcon_01").select("ul.list_1>li");

                }
                else
                {
                    doc = Jsoup.parse(new URL("http://www.swsm.net/"),5000);
                    es=doc.select("ul>li");
                }


            } catch (MalformedURLException e1) {
                Log.i("Malfrom","超时啦");
                e1.printStackTrace();
            } catch (IOException e1) {
                Log.i("IO","其他");
                handler.sendEmptyMessage(88);
                e1.printStackTrace();
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
                        int i=0;
                        list.clear();
                        if(n==2)
                        {
                            for (Element e : es)
                            {
                                Map<String, String> map = new HashMap<String, String>();
                                String h = e.getElementsByClass("z").text() + e.getElementsByTag("a").text();
                                if (h.startsWith("[")) {
                                    map.put("title", String.valueOf(++i) + ": " + h);
                                    map.put("href", "http://www.swsm.net/" + e.getElementsByTag("a").attr("href"));
                                    list.add(map);
                                }
                            }
                        }

                            else
                            {
                                for (Element e : es)
                                {
                                    Map<String, String> map = new HashMap<String, String>();
                                    String w=e.getElementsByTag("a").attr("href");
                                    if(!w.startsWith("http://"))
                                        w="http://www.sznews.com/"+w;
                                    map.put("title",String.valueOf(++i)+": "+ e.getElementsByTag("a").text());
                                    map.put("href", w);
                                    list.add(map);
                            }


                        }


                        soundlist.setAdapter(new SimpleAdapter(getActivity(), list, android.R.layout.simple_list_item_1,
                                new String[] { "title","href" }, new int[] {
                                android.R.id.text1,android.R.id.text2
                        }));
                        if(progress!=null)
                        progress.dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
