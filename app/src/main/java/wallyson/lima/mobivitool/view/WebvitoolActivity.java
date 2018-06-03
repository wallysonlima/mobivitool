package wallyson.lima.mobivitool.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import wallyson.lima.mobivitool.R;

public class WebvitoolActivity extends AppCompatActivity {
    private WebView webview;

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webvitool);
        webview= findViewById(R.id.webviewweb);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new MyWebViewClient());
        openURL();
    }


    /** Opens the URL in a browser */
    private void openURL() {
        webview.loadUrl("http://192.168.0.116/webvitool/view/webvitool.php");
        webview.requestFocus();
    }
}
