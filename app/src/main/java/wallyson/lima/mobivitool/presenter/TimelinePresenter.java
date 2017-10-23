package wallyson.lima.mobivitool.presenter;

import android.content.Context;
import android.content.res.AssetManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by wlima on 10/23/17.
 */

public class TimelinePresenter {
    TimelineInterface mView;
    Context c;

    public TimelinePresenter(TimelineInterface view, Context context) {
        mView = view;
        c = context;
    }

    // initialize the WebView and the draw the chart
    public void loadChart(String remoteUrl, WebView webview)
    {
        String html = "";
        webview.setWebViewClient(new WebViewClient());
        WebSettings webSettings =
                webview.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        AssetManager mgr = c.getAssets();
        try {
            InputStream in = mgr.open(remoteUrl, AssetManager.ACCESS_BUFFER);
            html = readHtml(in);
            in.close();
            webview.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "UTF-8", null);
            //webview.loadUrl("file:///android_asset/html/timelineChart.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read the html file, and created a string with file.
    public String readHtml(InputStream in) throws IOException {
        if(in == null) {
            return "";
        }
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
        }
        return writer.toString();
    }
}
