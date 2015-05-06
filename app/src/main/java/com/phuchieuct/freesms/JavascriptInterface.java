package com.phuchieuct.freesms;

import android.app.Activity;
import android.webkit.WebView;

/**
 * Created by phuchieuct on 5/6/2015.
 */
public class JavascriptInterface {

    Activity activity;
    String TAG = "JsHandler";
    WebView webView;


    public JavascriptInterface(Activity _contxt, WebView _webView) {
        activity = _contxt;
        webView = _webView;
    }

    @android.webkit.JavascriptInterface
    public void onLoaded() {
        javaFnCall("01234959839");
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