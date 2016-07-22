package com.example.phn.youwenapp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class MovieDetail extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup movie_tap;
    private RadioButton type_movie;
    private RadioButton type_tv;
    private RadioButton type_vip;
    private RadioButton type_china;
    private RadioButton type_usa;
    private Document doc;
    private Elements es;
    private ListView movielist;
    private movieAdapter mmovieAdapter;
    private List<Lesson_data> mData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        bindview();
    }


    public void bindview()
    {
        movie_tap=(RadioGroup)findViewById(R.id.movie_tab);
        type_china=(RadioButton)findViewById(R.id.type_china);
        type_movie=(RadioButton)findViewById(R.id.type_movie);
        type_tv=(RadioButton)findViewById(R.id.type_tv);
        type_vip=(RadioButton)findViewById(R.id.type_vip);
        type_usa=(RadioButton)findViewById(R.id.type_usa);
        movie_tap.setOnCheckedChangeListener(this);
        movielist=(ListView)findViewById(R.id.movielist);
        movielist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lesson_data d=(Lesson_data)movielist.getAdapter().getItem(position);
                Log.i("url",d.getLessonurl());
                MainActivity.mediaurl=d.getLessonurl();

            }
        });

    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {

            case R.id.type_tv:
                new Thread(new load(0)).start();
                type_tv.setChecked(true);
                break;
            case R.id.type_movie:
                new Thread(new load(1)).start();
                type_movie.setChecked(true);
                break;
            case R.id.type_vip:
                new Thread(new load(2)).start();
                type_vip.setChecked(true);
                break;
            case R.id.type_china:
                new Thread(new load(3)).start();
                type_china.setChecked(true);
                break;
            case R.id.type_usa:
                new Thread(new load(4)).start();
                type_usa.setChecked(true);
                break;
        }
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
                myHandler.sendEmptyMessage(88);
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
                                Log.i("0", String.valueOf(n));
                                Log.i("1", number);
                                Log.i("2", title);
                                Log.i("3", lessonurl);
                                Log.i("4", visit);
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
                                Log.i("0", String.valueOf(n));
                                Log.i("1", number);
                                Log.i("2", title);
                                Log.i("3", lessonurl);
                                Log.i("4", visit);
                                mData.add(new Lesson_data(lessonurl,number,title,visit,++i));

                            }
                        }
                        mmovieAdapter= new movieAdapter((LinkedList<Lesson_data>) mData, MovieDetail.this);
                        movielist.setAdapter(mmovieAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 88:
                    Toast.makeText(MovieDetail.this,"网络异常，请检查你的网络",Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
