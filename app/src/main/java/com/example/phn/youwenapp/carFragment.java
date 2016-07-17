package com.example.phn.youwenapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class carFragment extends Fragment {//汽车栏具体界面

    private Spinner car_brand;
    private Spinner car_price;
    private Spinner car_shape;
    private Spinner car_break;
    private WebView carwebview;
    public carFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_fragment, container, false);
        bindspinner(view);
        carwebview=(WebView)view.findViewById(R.id.carwebview);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        WebSettings setting = carwebview.getSettings();
        setSettings(setting);
        carwebview.setWebChromeClient(new WebChromeClient());
        carwebview.setWebViewClient(new WebViewClient()  {
            ProgressDialog progress;
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //Toast.makeText(MainActivity.this,webview.getUrl(),Toast.LENGTH_SHORT).show();
                view.loadUrl(url);
                Log.i("should",view.getUrl());
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                if(url!=null && url.contains("http://mobile.auto.sohu.com/")){


                    String fun="javascript:function getClass(parent,sClass) { var aEle=parent.getElementsByTagName('div'); var aResult=[]; var i=0; for(i<0;i<aEle.length;i++) { if(aEle[i].className==sClass) { aResult.push(aEle[i]); } }; return aResult; } ";

                    view.loadUrl(fun);

                    String fun2="javascript:function hideOther() {getClass(document,'TB2014head')[0].style.display='none';getClass(document,'TB2014head')[0].style.display='none';getClass(document,'head')[0].style.display='none';getClass(document,'main')[0].style.display='none';getClass(document,'TB2014head')[0].style.display='none';getClass(document,'linknav area')[0].style.display='none';getClass(document,'Area')[0].style.display='none';getClass(document,'linknav area')[0].style.display='none';}";

                    view.loadUrl(fun2);

                    view.loadUrl("javascript:hideOther();");


                }
                super.onPageFinished(view, url);
            }

        });
        //carwebview.loadUrl("http://auto.sohu.com/");
        //new Thread(new load()).start();
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
        car_break.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0)
                    carwebview.loadUrl(carbreak(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void setSettings(WebSettings setting) {
        setting.setJavaScriptEnabled(true);
        setting.setPluginState(WebSettings.PluginState.ON);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        setting.setAllowFileAccess(true);
        setting.setDefaultTextEncodingName("UTF-8");
        setting.setLoadWithOverviewMode(true);
        setting.setUseWideViewPort(true);
        setting.setBuiltInZoomControls(true);
        setting.setDisplayZoomControls(false);
        setting.setSupportZoom(true);

        setting.setDomStorageEnabled(true);
        setting.setDatabaseEnabled(true);
        // 全屏显示
        setting.setLoadWithOverviewMode(true);
        setting.setUseWideViewPort(true);
    }
    public void onDestroy()
    {
        if (carwebview != null) {
            carwebview.removeAllViews();
            carwebview.destroy();
            carwebview = null;
        }
        super.onDestroy();
    }
    public String carbreak(int n)
    {
        String carurl="";
        if(n==1)
            carurl="http://www.weizhangwang.com/";
       else if(n==2)
            carurl="http://www.weizhangwang.com/jiashizheng/";
        else if(n==3)
            carurl="http://daima.weizhangwang.com/";

        return "http://mobile.auto.sohu.com/wzcx/mainPage.at";

    }
}
