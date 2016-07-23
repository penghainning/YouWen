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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
public class detail_sound extends Fragment implements RadioGroup.OnCheckedChangeListener{
    Document doc;
    Elements es;
    ListView soundlist;
    Handler handler;
    List<Map<String, String>> list;
    SimpleAdapter adapter;
    private boolean success=false;
    private RadioGroup sound_tap;
    private RadioButton type_one;
    private RadioButton type_two;
    private RadioButton type_three;
    private LinearLayout bottom;
    public detail_sound() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sound_detail, container, false);
        MainActivity activity=(MainActivity) getActivity();
        sound_tap=(RadioGroup)view.findViewById(R.id.sound_tab);
        type_one=(RadioButton)view.findViewById(R.id.type_one);
        type_two=(RadioButton)view.findViewById(R.id.type_two);
        type_three=(RadioButton)view.findViewById(R.id.type_three);
        sound_tap.setOnCheckedChangeListener(this);
        type_one.setChecked(true);
        handler=activity.handler;
        soundlist=(ListView)view.findViewById(R.id.soundlist);
        list = new ArrayList<Map<String, String>>();
        adapter=new SimpleAdapter(getActivity(), list, android.R.layout.simple_list_item_1,
                new String[] { "title","href" }, new int[] {android.R.id.text1,android.R.id.text2});
        soundlist.setAdapter(adapter);
        soundlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> m=(Map<String,String>)soundlist.getAdapter().getItem(position);
                Log.i("url",m.get("href"));
                MainActivity.mediaurl=m.get("href");
                handler.sendEmptyMessage(700);
            }
        });
        bottom=(LinearLayout)view.findViewById(R.id.bottom);
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg=new Message();
                msg.arg1=1;
                msg.what=103;
                handler.sendMessage(msg);
            }
        });
        new Thread(new load(1)).start();
        success=false;
        new Thread(new timeError()).start();
        return view;
    }



    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {

            case R.id.type_one:
                new Thread(new load(1)).start();
                success=false;
                new Thread(new timeError()).start();
                type_one.setChecked(true);
                break;
            case R.id.type_two:
                new Thread(new load(2)).start();
                success=false;
                new Thread(new timeError()).start();
                type_two.setChecked(true);
                break;
            case R.id.type_three:
                type_three.setChecked(true);
                break;
        }
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
                handler.sendEmptyMessage(1);
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
            success=true;
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

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    Toast.makeText(getActivity(),"网络状态不好，请重试！",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;

            }
            super.handleMessage(msg);
        }
    };


    public class timeError extends Thread
    {
        @Override
        public void run() {
            for(int i=0;i<5;i++)
            {
                try {
                    Thread.sleep(200);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                if(success)
                    break;
            }
            if(!success)
                myHandler.sendEmptyMessage(1);
        }
    }
}





