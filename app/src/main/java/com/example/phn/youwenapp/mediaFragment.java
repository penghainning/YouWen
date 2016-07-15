package com.example.phn.youwenapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
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
        webView.setWebViewClient(new WebViewClient()
        {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //Toast.makeText(MainActivity.this,webview.getUrl(),Toast.LENGTH_SHORT).show();
                view.loadUrl(url);
                Log.i("should",view.getUrl());
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                if(url!=null && url.contains("open.sina.com")){


                    String fun="javascript:function getClass(parent,sClass) { var aEle=parent.getElementsByTagName('div'); var aResult=[]; var i=0; for(i<0;i<aEle.length;i++) { if(aEle[i].className==sClass) { aResult.push(aEle[i]); } }; return aResult; } ";

                    view.loadUrl(fun);

                    String fun2="javascript:function hideOther() {getClass(document,'wrap')[0].style.display='none';  getClass(document,'menu')[0].style.display='none'; getClass(document,'blk_login')[0].style.display='none';getClass(document,'secondaryHeader')[0].style.display='none';getClass(document,'part03 clearfix')[0].style.display='none';getClass(document,'part04 clearfix')[0].style.display='none';getClass(document,'footer')[0].style.display='none';}";

                    view.loadUrl(fun2);

                    view.loadUrl("javascript:hideOther();");


                }
               /* else if(url!=null && url.contains("live.qq.com"))
                {
                    String fun="javascript:function getClass(parent,sClass) { var aEle=parent.getElementsByTagName('div'); var aResult=[]; var i=0; for(i<0;i<aEle.length;i++) { if(aEle[i].className==sClass) { aResult.push(aEle[i]); } }; return aResult; } ";

                    view.loadUrl(fun);

                    String fun2="javascript:function hideOther() {getClass(document,'header')[0].style.display='none';  getClass(document,'left-menu small')[0].style.display='none'; getClass(document,'live-group-buttons clearfix')[0].style.display='none';getClass(document,'live-room-normal-right fl')[0].style.display='none';getClass(document,'room-mes clearfix')[0].style.display='none';getClass(document,'stats-and-actions')[0].style.display='none';getClass(document,'column o-events')[0].style.display='none';}";

                    view.loadUrl(fun2);

                    view.loadUrl("javascript:hideOther();");
                }*/
                super.onPageFinished(view, url);
            }
        });

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
