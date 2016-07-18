package com.example.phn.youwenapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by PHN on 2016/7/4.
 */
public class publishFragment extends Fragment {

    Spinner publishpovince;
    Spinner publishcity;
    Spinner publishdistrict;
    Spinner publishtown;
    Spinner publishmodule;
    EditText publishtitle;
    EditText publishcontent;
    Button publishcancel;
    Button publishsend;
    Handler handler;
    public publishFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_fragment, container, false);
        MainActivity activity=(MainActivity)getActivity();
        handler=activity.handler;
        bindviewr(view);
        return view;
    }
    public  void  bindviewr(View view) {
        publishtitle=(EditText)view.findViewById(R.id.publishtitle);
        publishcontent=(EditText)view.findViewById(R.id.publishcontent);
        publishcancel=(Button)view.findViewById(R.id.publishcancel);
        publishcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(102);
            }
        });
        publishsend=(Button)view.findViewById(R.id.publishsend);
        publishsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(publishcontent.getText().toString())||"".equals(publishtitle.getText().toString()))
                {
                    Toast.makeText(getActivity(),"标题或内容不能为空！请输入",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Message message=new Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("title",publishtitle.getText().toString());
                    bundle.putString("content",publishcontent.getText().toString());
                    message.setData(bundle);
                    message.what=105+publishmodule.getSelectedItemPosition();
                    handler.sendMessage(message);

                }
            }
        });
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
