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
public class newsFragment extends Fragment {
    private Spinner news_type;

    public newsFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        bindspinner(view);
        return view;
    }

    public  void  bindspinner(View view){
        news_type =(Spinner) view.findViewById(R.id.news_type);
        String[] mItems = getResources().getStringArray(R.array.news);//获取数据数组
        ArrayAdapter<String> mAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems);//绑定spinner数据适配器
        news_type.setAdapter(mAdapter);
    }

}
