package com.lt.library.JsWeb;

import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;

/**
 * Created by Little on 2017/6/1.
 */

public abstract class MyWebViewClient extends BridgeWebViewClient {

    public MyWebViewClient(BridgeWebView webView) {
        super(webView);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        view.loadUrl(onPageError(failingUrl));
    }

    /**
     * return errorUrl
     * @param url
     * @return
     */
    public abstract String onPageError(String url);

}
