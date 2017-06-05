package com.lt.ltwebview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.lt.library.JsWeb.JavaCallHandler;
import com.lt.library.JsWeb.JsHandler;
import com.lt.library.JsWeb.MyWebViewClient;
import com.lt.library.view.LTWebView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private LTWebView myWebView;
    private ArrayList<String> mHandles = new ArrayList<>();

    private static CallBackFunction mfunction;

    int RESULT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebView();
    }

    private void initWebView() {
        myWebView = (LTWebView) findViewById(R.id.my_webview);
        myWebView.setWebViewClient(new MyWebViewClient(myWebView.getWebView()) {
            @Override
            public String onPageError(String url) {
                // 制定网络加载失败时的错误页面
                return "file:///android_asset/error.html";
            }
        });

        // 打开页面，也支持网络url
        myWebView.loadUrl("file:///android_asset/demo1.html");

        mHandles.add("login");
        mHandles.add("callNative");
        mHandles.add("callJs");
        mHandles.add("open");
        // 回调js方法
        myWebView.registerHandlers(mHandles, new JsHandler() {
            @Override
            public void OnHandler(String handlerName, String responseData, CallBackFunction function) {
                if (handlerName.equals("login")){
                    toast(responseData);
                }else if (handlerName.equals("callNative")){
                    toast(responseData);
                    function.onCallBack("我在苏州");
                }else if (handlerName.equals("callJs")){
                    toast(responseData);
                    function.onCallBack("好的 这是图片地址：xxxxxxxx");
                }else if (handlerName.equals("open")){
                    mfunction = function;
                    pickFile();
                }
            }
        });

        // 调用js
        myWebView.callHandler("callNative", "hello H5，我是java", new JavaCallHandler() {
            @Override
            public void OnHandler(String handlerName, String jsResponseData) {
                toast("h5返回的数据:"+jsResponseData);
            }
        });
        myWebView.send("哈喽", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                toast(data);
            }
        });
    }

    private void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent,RESULT_CODE);
    }

    private void toast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CODE){
            mfunction.onCallBack(data.getData().toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myWebView.getWebView()!=null){
            myWebView.getWebView().destroy();
        }
    }

}
