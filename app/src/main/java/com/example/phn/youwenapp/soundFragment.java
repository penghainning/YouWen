package com.example.phn.youwenapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PHN on 2016/7/4.
 */
public class soundFragment extends Fragment {
    private Spinner one_province;
    private Spinner two_province;
    private Spinner one_city;
    private Spinner two_city;
    private Button sound_sure;
    private List<Provence> provences;
    private Provence provence;
    ArrayAdapter<Provence> adapter01;
    ArrayAdapter<City> adapter02;
    MainActivity activity;
    Handler handler;
    public soundFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sound_fragment, container, false);
        sound_sure=(Button)view.findViewById(R.id.sound_sure);
        activity=(MainActivity)getActivity();
        handler=activity.handler;
        sound_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                soundFragment2 s2=new soundFragment2();
                ft.replace(R.id.soundreplace,s2);
                ft.commit();
                handler.sendEmptyMessage(101);
            }
        });
        bindspinner(view);
        return view;
    }



    public  void  bindspinner(View view){
       /* one_province =(Spinner) view.findViewById(R.id.one_province);
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
        two_city.setAdapter(mAdapter4);*/

        one_province =(Spinner) view.findViewById(R.id.one_province);
        one_city =(Spinner) view.findViewById(R.id.one_city);
        two_province =(Spinner) view.findViewById(R.id.two_province);
        two_city =(Spinner) view.findViewById(R.id.two_city);
        try {
            provences = getProvinces();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        adapter01 = new ArrayAdapter<Provence>(getActivity(), android.R.layout.simple_list_item_1, provences);
        one_province.setAdapter(adapter01);
        one_province.setSelection(0, true);
        two_province.setAdapter(adapter01);
        two_province.setSelection(0,true);
        adapter02 = new ArrayAdapter<City>(getActivity(), android.R.layout.simple_list_item_1, provences.get(0).getCitys());
        one_city.setAdapter(adapter02);
        one_city.setSelection(0, true);
        two_city.setAdapter(adapter02);
        two_city.setSelection(0, true);

        one_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                provence = provences.get(position);
                adapter02 = new ArrayAdapter<City>(getActivity(), android.R.layout.simple_list_item_1, provences.get(position).getCitys());
                one_city.setAdapter(adapter02);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        two_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                provence = provences.get(position);
                adapter02 = new ArrayAdapter<City>(getActivity(), android.R.layout.simple_list_item_1, provences.get(position).getCitys());
                two_city.setAdapter(adapter02);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public List<Provence> getProvinces() throws XmlPullParserException,
            IOException {
        List<Provence> provinces = null;
        Provence province = null;
        List<City> citys = null;
        City city = null;
        Resources resources = getResources();

        InputStream in = resources.openRawResource(R.raw.citys_weather);

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();

        parser.setInput(in, "utf-8");
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    provinces = new ArrayList<Provence>();
                    break;
                case XmlPullParser.START_TAG:
                    String tagName = parser.getName();
                    if ("p".equals(tagName)) {
                        province = new Provence();
                        citys = new ArrayList<City>();
                        int count = parser.getAttributeCount();
                        for (int i = 0; i < count; i++) {
                            String attrName = parser.getAttributeName(i);
                            String attrValue = parser.getAttributeValue(i);
                            if ("p_id".equals(attrName))
                                province.setId(attrValue);
                        }
                    }
                    if ("pn".equals(tagName)) {
                        province.setName(parser.nextText());
                    }
                    if ("c".equals(tagName)) {
                        city = new City();
                        int count = parser.getAttributeCount();
                        for (int i = 0; i < count; i++) {
                            String attrName = parser.getAttributeName(i);
                            String attrValue = parser.getAttributeValue(i);
                            if ("c_id".equals(attrName))
                                city.setId(attrValue);
                        }
                    }
                    if ("cn".equals(tagName)) {
                        city.setName(parser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("c".equals(parser.getName())) {
                        citys.add(city);
                    }
                    if ("p".equals(parser.getName())) {
                        province.setCitys(citys);
                        provinces.add(province);
                    }

                    break;

            }
            event = parser.next();

        }
        return provinces;
    }
}
