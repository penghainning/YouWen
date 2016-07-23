package com.example.phn.youwenapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by PHN on 2016/7/4.
 */
public class detail_market extends Fragment implements RadioGroup.OnCheckedChangeListener{
    Document doc;
    Elements es;
    ListView marketlist;
    Handler handler;
    private RadioGroup market_tap;
    private RadioButton type_pet;
    private RadioButton type_marketcar;
    private RadioButton type_home;

    public detail_market() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.market_detail, container, false);
        MainActivity activity=(MainActivity) getActivity();
        market_tap=(RadioGroup)view.findViewById(R.id.market_tab);
        type_pet=(RadioButton)view.findViewById(R.id.type_pet);
        type_marketcar=(RadioButton)view.findViewById(R.id.type_marketcar);
        type_home=(RadioButton)view.findViewById(R.id.type_home);
        market_tap.setOnCheckedChangeListener(this);
        type_pet.setChecked(true);
        handler=activity.handler;
        marketlist = (ListView) view.findViewById(R.id.marketlist);
        marketlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

            case R.id.type_pet:
                type_pet.setChecked(true);
                break;
            case R.id.type_marketcar:
                type_marketcar.setChecked(true);
                break;
            case R.id.type_home:
                type_home.setChecked(true);
                break;
        }
    }



}
