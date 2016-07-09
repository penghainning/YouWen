package com.example.phn.youwenapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class carFragment extends Fragment {//汽车栏具体界面

    private Spinner car_brand;
    private Spinner car_price;
    private Spinner car_shape;
    private Spinner car_break;

    public carFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_fragment, container, false);
        bindspinner(view);
        return view;
    }

    public  void  bindspinner(View view){
        car_brand =(Spinner) view.findViewById(R.id.car_brand);
        String[] mItems = getResources().getStringArray(R.array.brand);//获取数据数组
        ArrayAdapter<String> mAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems);//绑定spinner数据适配器
        car_brand.setAdapter(mAdapter);

        car_price =(Spinner) view.findViewById(R.id.car_price);
        String[] mItems2 = getResources().getStringArray(R.array.price);
        ArrayAdapter<String> mAdapter2=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems2);
        car_price.setAdapter(mAdapter2);

        car_shape =(Spinner) view.findViewById(R.id.car_shape);
        String[] mItems3 = getResources().getStringArray(R.array.shape);
        ArrayAdapter<String> mAdapter3=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems3);
        car_shape.setAdapter(mAdapter3);

        car_break =(Spinner) view.findViewById(R.id.car_break);
        String[] mItems4 = getResources().getStringArray(R.array.car_break);
        ArrayAdapter<String> mAdapter4=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mItems4);
        car_break.setAdapter(mAdapter4);
    }
}
