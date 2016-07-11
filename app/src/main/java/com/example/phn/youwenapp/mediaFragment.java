package com.example.phn.youwenapp;

import android.annotation.SuppressLint;
import android.net.Uri;
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
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by PHN on 2016/7/4.
 */
@SuppressLint("ValidFragment")
public class mediaFragment extends Fragment {
    private WebView webView;
    private String mediaurl;
    public mediaFragment(String mediaurl){
        this.mediaurl=mediaurl;
    }
    public  void setMediaurl(String mediaurl)
    {
        this.mediaurl=mediaurl;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.media_fragment, container, false);
        Log.i("meidafragment",mediaurl);
        webView = (WebView)view. findViewById(R.id.webView);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        WebSettings setting = webView.getSettings();
        setSettings(setting);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(mediaurl);
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
      if (webView != null) {
          webView.removeAllViews();
          webView.destroy();
          webView = null;
      }
      super.onDestroy();
  }



}
