package wallyson.lima.mobivitool.presenter;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import wallyson.lima.mobivitool.dao.PrecipitacaoDAO;

/**
 * Created by wlima on 1/13/18.
 */

public class SimplePresenter {
    private SimpleInterface mView;
    private Context c;
    private WebView webview;
    private PrecipitacaoDAO preDao;

    public SimplePresenter(SimpleInterface view, Context context, WebView webview) {
        mView = view;
        c = context;
        this.webview = webview;
        preDao = new PrecipitacaoDAO();
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

    @JavascriptInterface
    public String getPrefixo() { return mView.getPrefixo(); }

    @JavascriptInterface
    public String getAno() { return mView.getAno(); }

    @JavascriptInterface
    public String getMunicipio() { return mView.getMunicipio(); }

    @JavascriptInterface
    public String getTipo() { return mView.getTipo(); }
}
