package wallyson.lima.mobivitool.view;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import wallyson.lima.mobivitool.presenter.SimpleInterface;
import wallyson.lima.mobivitool.presenter.SimplePresenter;

public class SimpleChartActivity extends AppCompatActivity implements SimpleInterface {
    private WebView webview;
    private SimplePresenter mPresenter;
    private String prefixo, ano, nomeArquivo, tipo, municipio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_chart);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        webview = (WebView) findViewById(R.id.webviewsimple);
        mPresenter = new SimplePresenter(this, this.getApplicationContext(), webview);
        nomeArquivo = "simple.csv";
        prefixo = getIntent().getStringExtra("prefixo");
        ano = getIntent().getStringExtra("ano");
        tipo = getIntent().getStringExtra("tipo");
        municipio = getIntent().getStringExtra("municipio");
        //load the chart
        writeData();
        loadChart("html/simpleChart.html");
    }

    // initialize the WebView and the pie chart
    public void loadChart(String remoteUrl)
    {
        String html = "";
        webview.setWebViewClient(new WebViewClient());
        WebSettings webSettings =
                webview.getSettings();

        webview.addJavascriptInterface(mPresenter, "AndroidSimple");
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
        ArrayList<Precipitacao> arrayPre = preDao.getMediaChuvaAno(prefixo, ano);
        String texto = "letter,frequency\n";
        FileOutputStream outputStream;

        for (Precipitacao p: arrayPre) {

            switch (p.getMes()) {
                case "01":
                    texto += "Jan," + p.getMedia() + "\n";
                    break;
                case "02":
                    texto += "Feb," + p.getMedia() + "\n";
                    break;
                case "03":
                    texto += "Mar," + p.getMedia() + "\n";
                    break;
                case "04":
                    texto += "Apr," + p.getMedia() + "\n";
                    break;
                case "05":
                    texto += "May," + p.getMedia() + "\n";
                    break;
                case "06":
                    texto += "Jun," + p.getMedia() + "\n";
                    break;
                case "07":
                    texto += "Jul," + p.getMedia() + "\n";
                    break;
                case "08":
                    texto += "Aug," + p.getMedia() + "\n";
                    break;
                case "09":
                    texto += "Sep," + p.getMedia() + "\n";
                    break;
                case "10":
                    texto += "Oct," + p.getMedia() + "\n";
                    break;
                case "11":
                    texto += "Nov," + p.getMedia() + "\n";
                    break;
                case "12":
                    texto += "Dec," + p.getMedia() + "\n";
                    break;
            }
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

    public String getTipo() {
        return this.tipo;
    }

    public String getAno() { return this.ano; }

    public String getPrefixo() { return this.prefixo; }

    public String getMunicipio() { return this.municipio; }
}
