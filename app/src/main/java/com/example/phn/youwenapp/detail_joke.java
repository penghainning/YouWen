package com.example.phn.youwenapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by PHN on 2016/7/4.
 */
public class detail_joke extends Fragment {
    private TextView jokecontent;

    public detail_joke() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.joke_detail, container, false);
        jokecontent=(TextView)view.findViewById(R.id.jokecontent);
        jokecontent.setText(MainActivity.mediaurl);
        return view;
    }





}
