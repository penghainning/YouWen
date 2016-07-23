package com.example.phn.youwenapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PHN on 2016/7/4.
 */
public class marketFragment  extends Fragment {
    ListView marketlist;
    Handler handler;
    List<Map<String, String>> list;
    SimpleAdapter marketAdapter;
    public marketFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.market_fragment, container, false);
        marketlist=(ListView)view.findViewById(R.id.marketlist);
        list = new ArrayList<Map<String, String>>();
        for(int i=0;i<5;i++)
        {
            Map<String, String> m=new HashMap<>();
            m.put("title","用户发布的二手市场消息。");
            list.add(m);
        }
        marketAdapter=new SimpleAdapter(getActivity(), list, android.R.layout.simple_list_item_1,
                new String[] { "title","href" }, new int[] {
                android.R.id.text1,android.R.id.text2});
        marketlist.setAdapter(marketAdapter);
        MainActivity activity=(MainActivity) getActivity();
        handler=activity.handler;
        marketlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handler.sendEmptyMessage(900);
            }
        });
        return view;
    }

    public void loaddata(Map<String, String>m)
    {
        list.add(m);
        marketAdapter.notifyDataSetChanged();

    }
}
