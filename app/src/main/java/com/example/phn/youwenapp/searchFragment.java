package com.example.phn.youwenapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import org.jsoup.nodes.Document;

/**
 * Created by PHN on 2016/7/4.
 */
@SuppressLint("ValidFragment")
public class searchFragment extends Fragment {
    private WebView searchwebView;
    private String weburl;
    private Button close;
    MainActivity activity;
    Handler handler;
    public searchFragment(String weburl){
        this.weburl=weburl;
    }
    public  void setweburl(String weburl)
    {
        this.weburl=weburl;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        Log.i("meidafragment",weburl);
        activity=(MainActivity)getActivity();
        handler=activity.handler;
        searchwebView = (WebView)view.findViewById(R.id.searchwebView);
        close=(Button)view.findViewById(R.id.close);
        close.getBackground().setAlpha(100);//0~255透明度值
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(102);

            }
        });
        searchwebView.setWebChromeClient(new WebChromeClient());
        searchwebView.setWebViewClient(new WebViewClient());
        Document doc;

        searchwebView.loadUrl(weburl);
        WebSettings setting = searchwebView.getSettings();
        setSettings(setting);
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
      if (searchwebView != null) {
          searchwebView.removeAllViews();
          searchwebView.destroy();
          searchwebView = null;
      }
      super.onDestroy();
  }
    public boolean canGoBack() {
        return searchwebView != null && searchwebView.canGoBack();
    }



}
