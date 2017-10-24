package wallyson.lima.mobivitool.presenter;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

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

    //Get the dataset
    @JavascriptInterface
    public int[] getDataSet() {
        int dataset[] = new int[] {5,10,15,20,35};
        return dataset;
    }

    //Get width webview
    @JavascriptInterface
    public int getWidth(WebView webview) {
        return webview.getWidth();
    }

    //Get height webview
    @JavascriptInterface
    public int getHeight(WebView webview) {
        return webview.getHeight();
    }

}
