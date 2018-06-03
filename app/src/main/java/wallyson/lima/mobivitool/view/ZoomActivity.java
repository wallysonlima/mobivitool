package wallyson.lima.mobivitool.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import wallyson.lima.mobivitool.R;
import wallyson.lima.mobivitool.dao.PrecipitacaoDAO;
import wallyson.lima.mobivitool.model.Precipitacao;
import wallyson.lima.mobivitool.presenter.TimelinePresenter;
import wallyson.lima.mobivitool.presenter.ZoomInterface;
import wallyson.lima.mobivitool.presenter.ZoomPresenter;

public class ZoomActivity extends AppCompatActivity implements ZoomInterface {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private WebView webview;
    private ZoomPresenter mPresenter;
    private String prefixo, municipio, ano, qtde;
    private String nomeArquivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.zoomLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webview = (WebView) findViewById(R.id.webviewzoom);
        mPresenter = new ZoomPresenter(this, this.getApplicationContext(), webview);
        nomeArquivo = "zoom.csv";
        prefixo = getIntent().getStringExtra("prefixo");
        municipio = getIntent().getStringExtra("municipio");
        ano = getIntent().getStringExtra("ano");
        qtde = getIntent().getStringExtra("qtde");
        //load the chart
        writeData();
        loadChart("html/zoomChart.html");
    }

    // initialize the WebView and the pie chart
    public void loadChart(String remoteUrl)
    {
        String html = "";
        webview.setWebViewClient(new WebViewClient());
        WebSettings webSettings =
                webview.getSettings();

        webview.addJavascriptInterface(mPresenter, "AndroidZoom");
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

    // Write data in Zoom.csv
    public void writeData() {
        PrecipitacaoDAO preDao = new PrecipitacaoDAO();
        ArrayList<Precipitacao> arrayPre = preDao.getMediaChuvaMes(prefixo, ano);
        String texto = "date,price\n";
        FileOutputStream outputStream;
        int i = 0;
        int limite = Integer.parseInt(qtde) * 12;

        for (Precipitacao p: arrayPre) {

            switch (p.getMes()) {
                case "01":
                    texto += "Jan " + p.getAno() + "," + p.getMedia() + "\n";
                    break;
                case "02":
                    texto += "Feb " + p.getAno() + "," + p.getMedia() + "\n";
                    break;
                case "03":
                    texto += "Mar " + p.getAno() + "," + p.getMedia() + "\n";
                    break;
                case "04":
                    texto += "Apr " + p.getAno() + "," + p.getMedia() + "\n";
                    break;
                case "05":
                    texto += "May " + p.getAno() + "," + p.getMedia() + "\n";
                    break;
                case "06":
                    texto += "Jun " + p.getAno() + "," + p.getMedia() + "\n";
                    break;
                case "07":
                    texto += "Jul " + p.getAno() + "," + p.getMedia() + "\n";
                    break;
                case "08":
                    texto += "Aug " + p.getAno() + "," + p.getMedia() + "\n";
                    break;
                case "09":
                    texto += "Sep " + p.getAno() + "," + p.getMedia() + "\n";
                    break;
                case "10":
                    texto += "Oct " + p.getAno() + "," + p.getMedia() + "\n";
                    break;
                case "11":
                    texto += "Nov " + p.getAno() + "," + p.getMedia() + "\n";
                    break;
                case "12":
                    texto += "Dec " + p.getAno() + "," + p.getMedia() + "\n";
                    break;
            }
            i++;
            if ( i == limite ) break;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getPrefixo() {
        return this.prefixo;
    }

    public String getMunicipio() {
        return this.municipio;
    }

    public String getAno() {
        return this.ano;
    }
}
