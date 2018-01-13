package wallyson.lima.mobivitool.view;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
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
    private String prefixo, ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_chart);

        webview = (WebView) findViewById(R.id.webviewsimple);
        mPresenter = new SimplePresenter(this, this.getApplicationContext(), webview);

        prefixo = getIntent().getStringExtra("prefixo");
        ano = getIntent().getStringExtra("ano");
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

    // Write data in simple.csv
    public void writeData() {
        PrecipitacaoDAO preDao = new PrecipitacaoDAO();
        ArrayList<Precipitacao> arrayPre = preDao.getMediaChuvaAno(prefixo, ano);

        try {
            FileOutputStream fileout=openFileOutput("file:///android_asset/data/simple.tsv", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write("date    media");

            for (Precipitacao p: arrayPre) {
                switch (p.getMes()) {
                    case "01":
                        outputWriter.write("Jan" + "    " + p.getMedia());
                        break;
                    case "02":
                        outputWriter.write("Feb " + "    " + p.getMedia());
                        break;
                    case "03":
                        outputWriter.write("Mar " + "    " + p.getMedia());
                        break;
                    case "04":
                        outputWriter.write("Apr " + "    " + p.getMedia());
                        break;
                    case "05":
                        outputWriter.write("May " + "    " + p.getMedia());
                        break;
                    case "06":
                        outputWriter.write("Jun " + "    " + p.getMedia());
                        break;
                    case "07":
                        outputWriter.write("Jul " + "    " + p.getMedia());
                        break;
                    case "08":
                        outputWriter.write("Aug " + "    " + p.getMedia());
                        break;
                    case "09":
                        outputWriter.write("Sep " + "    " + p.getMedia());
                        break;
                    case "10":
                        outputWriter.write("Oct " + "    " + p.getMedia());
                        break;
                    case "11":
                        outputWriter.write("Nov " + "    " + p.getMedia());
                        break;
                    case "12":
                        outputWriter.write("Dec " + "    " + p.getMedia());
                        break;
                }
            }
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}