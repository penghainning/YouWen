package com.example.phn.youwenapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by PHN on 2016/7/4.
 */
public class publishFragment extends Fragment {

    Spinner publishpovince;
    Spinner publishcity;
    Spinner publishdistrict;
    Spinner publishtown;
    Spinner publishmodule;
    public publishFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_fragment, container, false);
        bindviewr(view);
        return view;
    }
    public  void  bindviewr(View view) {
        publishpovince =(Spinner) view.findViewById(R.id.publishpovince);
        String[] mItems = getResources().getStringArray(R.array.province);//获取数据数组
        ArrayAdapter<String> mAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems);//绑定spinner数据适配器
        publishpovince.setAdapter(mAdapter);

        publishcity =(Spinner) view.findViewById(R.id.publishcity);
        String[] mItems2 = getResources().getStringArray(R.array.city);
        ArrayAdapter<String> mAdapter2=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems2);
        publishcity.setAdapter(mAdapter2);

        publishdistrict =(Spinner) view.findViewById(R.id.publishdistrict);
        String[] mItems3 = getResources().getStringArray(R.array.district);
        ArrayAdapter<String> mAdapter3=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems3);
        publishdistrict.setAdapter(mAdapter3);

        publishtown =(Spinner) view.findViewById(R.id.publishtown);
        String[] mItems4 = getResources().getStringArray(R.array.town);
        ArrayAdapter<String> mAdapter4=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems4);
        publishtown.setAdapter(mAdapter4);

        publishmodule =(Spinner) view.findViewById(R.id.publishmodule);
        String[] mItems5 = getResources().getStringArray(R.array.module);
        ArrayAdapter<String> mAdapter5=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems5);
        publishmodule.setAdapter(mAdapter5);
    }
}
