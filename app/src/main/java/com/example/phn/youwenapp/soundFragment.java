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
public class soundFragment extends Fragment {
    private Spinner one_province;
    private Spinner two_province;
    private Spinner one_city;
    private Spinner two_city;

    public soundFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sound_fragment, container, false);
        bindspinner(view);
        return view;
    }


    public  void  bindspinner(View view){
        one_province =(Spinner) view.findViewById(R.id.one_province);
        String[] mItems = getResources().getStringArray(R.array.province);//获取数据数组
        ArrayAdapter<String> mAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems);//绑定spinner数据适配器
        one_province.setAdapter(mAdapter);

        one_city =(Spinner) view.findViewById(R.id.one_city);
        String[] mItems2 = getResources().getStringArray(R.array.city);
        ArrayAdapter<String> mAdapter2=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems2);
        one_city.setAdapter(mAdapter2);

        two_province =(Spinner) view.findViewById(R.id.two_province);
        String[] mItems3 = getResources().getStringArray(R.array.province);
        ArrayAdapter<String> mAdapter3=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems3);
        two_province.setAdapter(mAdapter3);

        two_city =(Spinner) view.findViewById(R.id.two_city);
        String[] mItems4 = getResources().getStringArray(R.array.city);
        ArrayAdapter<String> mAdapter4=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems4);
        two_city.setAdapter(mAdapter4);
    }
}
