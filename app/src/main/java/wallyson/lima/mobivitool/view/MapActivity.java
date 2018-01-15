package wallyson.lima.mobivitool.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import wallyson.lima.mobivitool.R;
import wallyson.lima.mobivitool.dao.PostoDAO;
import wallyson.lima.mobivitool.dao.PrecipitacaoDAO;
import wallyson.lima.mobivitool.model.Posto;
import wallyson.lima.mobivitool.model.Precipitacao;
import wallyson.lima.mobivitool.presenter.MapInterface;
import wallyson.lima.mobivitool.presenter.MapPresenter;
import wallyson.lima.mobivitool.presenter.MultiPresenter;

public class MapActivity extends AppCompatActivity implements MapInterface {
    private WebView webview;
    private MapPresenter mPresenter;
    private String ano, mes, nomeArquivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webview = (WebView) findViewById(R.id.webviewmap);
        mPresenter = new MapPresenter(this, this.getApplicationContext(), webview);
        nomeArquivo = "map.csv";
        ano = getIntent().getStringExtra("ano");
        mes = getIntent().getStringExtra("mes");

        //load the chart
        writeData();
        loadChart("html/mapChart.html");
    }

    // initialize the WebView and the pie chart
    public void loadChart(String remoteUrl)
    {
        String html = "";
        webview.setWebViewClient(new WebViewClient());
        WebSettings webSettings =
                webview.getSettings();

        webview.addJavascriptInterface(mPresenter, "AndroidMap");
        webview.setWebContentsDebuggingEnabled(true);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("utf-8");


        AssetManager mgr = getBaseContext().getAssets();
        try {
            InputStream in = mgr.open(remoteUrl, AssetManager.ACCESS_BUFFER);
            html = readHtml(in);
            in.close();
            webview.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "UTF-8", null);
            //webview.loadUrl("file:///android_asset/html/piechart.html");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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

    // Write data in simple.tsv
    public void writeData() {
        PrecipitacaoDAO preDao = new PrecipitacaoDAO();
        PostoDAO postoDao = new PostoDAO();
        ArrayList<Float> medias = preDao.getMediaChuvaPorMes(ano, mes);
        ArrayList<Posto> postos = postoDao.getInfoPosto();
        String texto = "prefixo,municipio,bacia,latitude,longitude,media\n";
        FileOutputStream outputStream;

        for (int i = 0; i < postos.size(); i++) {
            texto += postos.get(i).getPrefixo() + "," + postos.get(i).getMunicipio() + "," +
                    postos.get(i).getBacia() + "," + postos.get(i).getLatitude() + "," +
                    postos.get(i).getLongitude() + "," + medias.get(i) + "\n";
        }

        try {
        File file = new File(getApplicationContext().getFilesDir(), nomeArquivo);
        outputStream = openFileOutput(nomeArquivo, Context.MODE_PRIVATE);
        outputStream.write(texto.getBytes());
        outputStream.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
