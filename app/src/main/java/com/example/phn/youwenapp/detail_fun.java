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
public class detail_fun extends Fragment implements RadioGroup.OnCheckedChangeListener{
    Document doc;
    Elements es;
    ListView funlist;
    Handler handler;
    private RadioGroup fun_tap;
    private RadioButton type_newshop;
    private RadioButton type_discount;
    private RadioButton type_recomend;
    private LinearLayout bottom;

    public detail_fun() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fun_detail, container, false);
        MainActivity activity=(MainActivity) getActivity();
        fun_tap=(RadioGroup)view.findViewById(R.id.fun_tab);
        type_newshop=(RadioButton)view.findViewById(R.id.type_newshop);
        type_discount=(RadioButton)view.findViewById(R.id.type_discount);
        type_recomend=(RadioButton)view.findViewById(R.id.type_recomoned);
        fun_tap.setOnCheckedChangeListener(this);
        type_newshop.setChecked(true);
        handler=activity.handler;
        bottom=(LinearLayout)view.findViewById(R.id.bottom);
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg=new Message();
                msg.arg1=3;
                msg.what=103;
                handler.sendMessage(msg);
            }
        });
        funlist = (ListView) view.findViewById(R.id.funlist);
        funlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message=new Message();
                message.what=100;
                message.arg1=1;
                handler.sendMessage(message);

            }
        });
        return view;
    }



    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {

            case R.id.type_newshop:
                type_newshop.setChecked(true);
                break;
            case R.id.type_discount:
                type_discount.setChecked(true);
                break;
            case R.id.type_recomoned:
                type_recomend.setChecked(true);
                break;
        }
    }



}
