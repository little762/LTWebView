package com.lt.library.JsWeb;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.tamic.jswebview.view.NumberProgressBar;

/**
 * Created by Little on 2017/6/1.
 */

public class MyWebChromeClient extends WebChromeClient {

    private NumberProgressBar mProgressBar;
    private final static int DEF = 95;

    public MyWebChromeClient(NumberProgressBar mProgressBar) {
        this.mProgressBar = mProgressBar;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress >= DEF){
            mProgressBar.setVisibility(View.GONE);
        } else {
            if (View.GONE == mProgressBar.getVisibility()) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            mProgressBar.setProgress(newProgress);
        }
        super.onProgressChanged(view, newProgress);
    }
}
