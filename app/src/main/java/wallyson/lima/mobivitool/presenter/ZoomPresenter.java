package wallyson.lima.mobivitool.presenter;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import wallyson.lima.mobivitool.dao.PrecipitacaoDAO;
import wallyson.lima.mobivitool.model.Precipitacao;

/**
 * Created by wlima on 10/30/17.
 */

public class ZoomPresenter {
    private ZoomInterface mView;
    private Context c;
    private WebView webview;
    private PrecipitacaoDAO preDao;

    public ZoomPresenter(ZoomInterface view, Context context, WebView webview) {
        mView = view;
        c = context;
        this.webview = webview;
        preDao = new PrecipitacaoDAO();
    }

    /*
    //Get the dataset
    @JavascriptInterface
    public String getDataSet() {
        int dataset[] = new int[] {5,10,15,20,35};

        String sb = "";
        for(int i=0;i<dataset.length;i++)
            sb += (dataset[i]+",");

        return sb;
    }
*/
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
    public JSONArray getMediaChuvaMes() {
        ArrayList<Precipitacao> arrayPre = preDao.getMediaChuvaMes(mView.getPrefixo());
        JSONArray arr = new JSONArray();
        JSONObject tmp;

        try {
            for(int i = 0; i < arrayPre.size(); i++) {
                tmp = new JSONObject();
                tmp.put("prefixo", arrayPre.get(i).getPrefixo()); //some public getters inside GraphUser?
                tmp.put("ano", arrayPre.get(i).getAno());
                tmp.put("mes", arrayPre.get(i).getMes());
                tmp.put("media", arrayPre.get(i).getAno());
                arr.put(tmp);
            }
        } catch(JSONException e){
            //error handling
        }

        return arr;
    }
}
