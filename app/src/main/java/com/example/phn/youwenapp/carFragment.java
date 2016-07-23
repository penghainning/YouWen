package com.example.phn.youwenapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
public class carFragment extends Fragment {

    LinearLayout carlinerlayout;
    Handler handler;


    public carFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_fragment, container, false);
        MainActivity activity=(MainActivity) getActivity();
        handler=activity.handler;
        carlinerlayout=(LinearLayout)view.findViewById(R.id.carlinerlayout) ;
        carlinerlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(500);
            }
        });
        return view;
    }




}
