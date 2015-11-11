package com.phuchieuct.freesms;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by phuchieuct on 5/6/2015.
 */
public class JavascriptInterface {

    Activity activity;
    String TAG = "JsHandler";
    WebView webView;
    Context context;


    public JavascriptInterface(Activity _contxt, WebView _webView,Context context) {
        activity = _contxt;
        webView = _webView;
        this.context = context;
    }

    @android.webkit.JavascriptInterface
    public void onLoaded() {
        javaFnCall("01234959839");
    }

    @android.webkit.JavascriptInterface
    public void toast(String toast) {
        Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();

    }

    public void javaFnCall(String jsString) {

        final String webUrl = "javascript:setToNumber('" + jsString + "')";
        // Add this to avoid android.view.windowmanager$badtokenexception unable to add window
        if (!activity.isFinishing())
            // loadurl on UI main thread
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    webView.loadUrl(webUrl);
                }
            });
    }
}