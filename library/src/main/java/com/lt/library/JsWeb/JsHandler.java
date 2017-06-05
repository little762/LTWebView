package com.lt.library.JsWeb;

import com.github.lzyzsd.jsbridge.CallBackFunction;

/**
 * Created by Little on 2017/6/2.
 */

public interface JsHandler {
    public void OnHandler(String handlerName, String responseData, CallBackFunction function);
}
