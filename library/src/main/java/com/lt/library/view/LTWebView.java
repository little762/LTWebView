package com.lt.library.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.widget.LinearLayout;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.lt.library.JsWeb.JavaCallHandler;
import com.lt.library.JsWeb.JsHandler;
import com.lt.library.JsWeb.MyWebChromeClient;
import com.lt.library.JsWeb.MyWebViewClient;
import com.tamic.jswebview.view.NumberProgressBar;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Little on 2017/6/5.
 */

public class LTWebView extends LinearLayout {

    private BridgeWebView mWebView;
    private NumberProgressBar mProgressBar;

    public LTWebView(Context context) {
        super(context);
        init(context, null);
    }

    public LTWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LTWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LTWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private final int default_reached_color = Color.rgb(255, 0, 0);
    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.VERTICAL);

        // 初始化mProgressBar
        if (mProgressBar == null){
            mProgressBar = new NumberProgressBar(context,attrs);
            mProgressBar.setProgressTextSize(0);
            mProgressBar.setReachedBarColor(default_reached_color);
            mProgressBar.setReachedBarHeight(mProgressBar.dp2px(2.5f));
            mProgressBar.setProgressTextVisibility(NumberProgressBar.ProgressTextVisibility.Invisible);
        }
        addView(mProgressBar);

        // 初始化webview
        if (mWebView == null) {
            mWebView = new BridgeWebView(context);
        }
        mWebView.setWebChromeClient(new MyWebChromeClient(mProgressBar));
        WebSettings webSettings = mWebView.getSettings();
        // 判断系统版本是不是5.0或之上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 让系统不屏蔽混合内容和第三方Cookie
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 不支持缩放
        webSettings.setSupportZoom(false);
        // 自适应屏幕大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        addView(mWebView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public NumberProgressBar getProgressBar() {
        return mProgressBar;
    }

    public BridgeWebView getWebView() {
        return mWebView;
    }

//    /**
//     * Loads the given URL
//     *
//     * @param url the URL of the resource to load
//     */
//    public void loadUrl(String url){
//        loadUrl(url,null);
//    }
//
//    /**
//     * Loads the given URL with the specified additional HTTP headers.
//     *
//     * @param url                   the URL of the resource to load
//     * @param additionalHttpHeaders the additional headers to be used in the
//     *                              HTTP request for this URL, specified as a map from name to
//     *                              value. Note that if this map contains any of the headers
//     *                              that are set by default by this WebView, such as those
//     *                              controlling caching, accept types or the User-Agent, their
//     *                              values may be overriden by this WebView's defaults.
//     */
//    private void loadUrl(String url, Map<String,String> additionalHttpHeaders) {
//        loadUrl(url,additionalHttpHeaders,null);
//    }
//
//    /**
//     * Loads the given URL with the specified additional HTTP headers.
//     *
//     * @param url                   the URL of the resource to load
//     * @param additionalHttpHeaders the additional headers to be used in the
//     *                              HTTP request for this URL, specified as a map from name to
//     *                              value. Note that if this map contains any of the headers
//     *                              that are set by default by this WebView, such as those
//     *                              controlling caching, accept types or the User-Agent, their
//     *                              values may be overriden by this WebView's defaults.
//     * @param returnCallback        the CallBackFunction to be Used call js registerHandler Function,
//     *                              rerurn response data.
//     */
//    private void loadUrl(String url, Map<String, String> additionalHttpHeaders, CallBackFunction returnCallback) {
//        mWebView.loadUrl(url, additionalHttpHeaders, returnCallback);
//    }

    /**
     * Loads the given URL
     *
     * @param url the URL of the resource to load
     */
    public void loadUrl(String url) {
        loadUrl(url, null);
    }

    /**
     * Loads the given URL with the specified additional HTTP headers.
     *
     * @param url            the URL of the resource to load
     * @param returnCallback the CallBackFunction to be Used call js registerHandler Function,
     *                       rerurn response data.
     */
    public void loadUrl(final String url, final CallBackFunction returnCallback) {
        mWebView.loadUrl(url, returnCallback);
    }

    public void setWebViewClient(MyWebViewClient client) {
        mWebView.setWebViewClient(client);
    }

    public void setWebChromeClient(MyWebChromeClient chromeClient) {
        mWebView.setWebChromeClient(chromeClient);
    }

    /**
     * @param handler default handler,handle messages send by js without assigned handler name,
     *                if js message has handler name, it will be handled by named handlers registered by native
     */
    public void setDefaultHandler(BridgeHandler handler) {
        mWebView.setDefaultHandler(handler);
    }

    public void send(String data) {
        mWebView.send(data);
    }

    public void send(String data, CallBackFunction responseCallback) {
        mWebView.send(data, responseCallback);
    }

    /**
     * 注册本地java方法，以供js端调用
     *
     * @param handlerName
     * @param handler
     */
    public void registerHandler(final String handlerName, final JsHandler handler) {
        mWebView.registerHandler(handlerName, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (handler != null) {
                    handler.OnHandler(handlerName, data, function);
                }
            }
        });
    }

    /**
     * 批量注册本地java方法，以供js端调用
     *
     * @param handlerNames 方法名称数组
     * @param handler      回调接口
     */
    public void registerHandlers(ArrayList<String> handlerNames, final JsHandler handler) {
        if (handler != null) {
            for (final String handlerName : handlerNames) {
                mWebView.registerHandler(handlerName, new BridgeHandler() {
                    @Override
                    public void handler(String data, CallBackFunction function) {
                        handler.OnHandler(handlerName, data, function);
                    }
                });
            }
        }
    }

    /**
     * 调用js端已经注册好的方法
     *
     * @param handlerName 方法名称
     * @param javaData    本地端传递给js端的参数，json字符串
     * @param handler     回调接口
     */
    public void callHandler(final String handlerName, String javaData, final JavaCallHandler handler) {
        mWebView.callHandler(handlerName, javaData, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                if (handler != null) {
                    handler.OnHandler(handlerName, data);
                }
            }
        });
    }

    /**
     * 批量调用js端已经注册好的方法
     *
     * @param handlerInfos 方法名称与参数的map，方法名为key
     * @param handler      回调接口
     */
    public void callHandler(Map<String, String> handlerInfos, final JavaCallHandler handler) {
        if (handler != null) {
            for (final Map.Entry<String, String> entry : handlerInfos.entrySet()) {
                mWebView.callHandler(entry.getKey(), entry.getValue(), new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        handler.OnHandler(entry.getKey(), data);
                    }
                });
            }
        }
    }


}
