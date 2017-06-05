package com.lt.library.view;

import android.util.Log;

import com.github.lzyzsd.jsbridge.BridgeWebView;

/**
 * Created by Little on 2017/6/5.
 */

public class LTWebView {

    private static final String TAG = "LTWebView";

    public void logJsBridge() {
        Log.d(TAG, "logJsBridge: " + BridgeWebView.class);
    }

}
