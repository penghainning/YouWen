package com.example.phn.youwenapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by PHN on 2016/7/4.
 */
public class storeFragment  extends Fragment {

    LinearLayout bottom;
    ListView storelist;
    Handler handler;
    List<Map<String, String>> list;
    SimpleAdapter storeAdapter;

    public storeFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment, container, false);
        bottom=(LinearLayout)view.findViewById(R.id.bottom);
        storelist=(ListView)view.findViewById(R.id.storelist);
        list = new ArrayList<Map<String, String>>();
        storeAdapter=new SimpleAdapter(getActivity(), list, android.R.layout.simple_list_item_1,
                new String[] { "title","href" }, new int[] {
                android.R.id.text1,android.R.id.text2});
        storelist.setAdapter(storeAdapter);
        MainActivity activity=(MainActivity) getActivity();
        handler=activity.handler;
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(103);
            }
        });
        return view;
    }

    public void loaddata(Map<String, String>m)
    {
        list.add(m);
        storeAdapter.notifyDataSetChanged();

    }
}