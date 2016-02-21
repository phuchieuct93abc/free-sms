package com.phuchieuct.freesms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;

import com.google.gson.Gson;
import com.phuchieuct.freesms.history.History;
import com.phuchieuct.freesms.history.HistoryAdapter;
import com.phuchieuct.freesms.history.Message;

import java.util.ArrayList;


public class MainActivity extends Activity {
    JavascriptInterface javascriptInterface;
    final int PICK_CONTACT = 123;
    WebView mWebView;
    String phoneNumber = null;
    SharedPreferences sharedPref;
    History history;
    Gson gson = new Gson();
    ListView listView;
    HistoryAdapter historyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        sharedPref = getApplicationContext().getSharedPreferences("NEWS_PREFERENCE", Context.MODE_PRIVATE);
        initWebview();
        getHistory();
        historyAdapter =new HistoryAdapter(getApplicationContext(), R.layout.list_history, history.getMessages());
        listView.setAdapter(historyAdapter);

    }
    private void getHistory(){
        Gson gson = new Gson();
        history = gson.fromJson(  sharedPref.getString("HISTORY",null), History.class);
        if(history==null){
            history=new History();
            history.setMessages(new ArrayList<Message>());
        }


    }

    public void openContact(View v) {
        openContact();
    }

    private void initWebview() {
        mWebView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(false);

        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccessFromFileURLs(true); //Maybe you don't need this rule
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptThirdPartyCookies(mWebView, true);
        }
        javascriptInterface = new JavascriptInterface(this, mWebView, this);
        mWebView.addJavascriptInterface(javascriptInterface, "JsHandler1");
        mWebView.setWebChromeClient(new WebChromeClient());

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                //Toast.makeText(TableContentsWithDisplay.this, "url "+url, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (phoneNumber == null) {
                    javascriptInterface.javaFnCall("01234959839");

                } else {
                    javascriptInterface.javaFnCall(phoneNumber);

                }
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                super.onReceivedSslError(view, handler, error);
                //Toast.makeText(TableContentsWithDisplay.this, "error "+error, Toast.LENGTH_SHORT).show();

            }
        });
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.requestFocus(View.FOCUS_DOWN);
        mWebView.loadUrl("file:///android_asset/index.html");
    }

    void openContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);

    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                    cursor.moveToFirst();

                    int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    phoneNumber = cursor.getString(phoneIndex);
                    javascriptInterface.javaFnCall(phoneNumber);


                }
                break;
        }
    }


    public void addHistory(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Message obj = gson.fromJson(message, Message.class);
                history.getMessages().add(0, obj);
                historyAdapter.setHistory(history.getMessages());
                historyAdapter.notifyDataSetChanged();
                sharedPref.edit().putString("HISTORY", gson.toJson(history)).apply();


            }
        });
    }


}
