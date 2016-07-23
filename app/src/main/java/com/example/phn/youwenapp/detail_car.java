package com.example.phn.youwenapp;

import android.os.Bundle;
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

public class detail_car extends Fragment {//汽车栏具体界面

    private WebView carwebview;
    public detail_car() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_detail, container, false);
        carwebview=(WebView)view.findViewById(R.id.carwebview);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        WebSettings setting = carwebview.getSettings();
        setSettings(setting);
        carwebview.setWebChromeClient(new WebChromeClient());
        carwebview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.i("should", view.getUrl());
                return true;
            }
        });
        carwebview.loadUrl("http://m.sohu.com/c/1592/");
        return view;
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

}
