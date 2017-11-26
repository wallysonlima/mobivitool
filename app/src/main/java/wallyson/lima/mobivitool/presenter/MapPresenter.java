package wallyson.lima.mobivitool.presenter;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * Created by wlima on 11/26/17.
 */

public class MapPresenter {
    private MapInterface mView;
    private Context c;
    private WebView webview;

    public MapPresenter(MapInterface view, Context context, WebView webview) {
        mView = view;
        c = context;
        this.webview = webview;
    }

    //Get the dataset
    @JavascriptInterface
    public String getDataSet() {
        int dataset[] = new int[] {5,10,15,20,35};

        String sb = "";
        for(int i=0;i<dataset.length;i++)
            sb += (dataset[i]+",");

        return sb;
    }

    //Get width webview
    @JavascriptInterface
    public int getWidth() {
        return webview.getWidth();
    }

    //Get height webview
    @JavascriptInterface
    public int getHeight() {
        return webview.getHeight();
    }
}
