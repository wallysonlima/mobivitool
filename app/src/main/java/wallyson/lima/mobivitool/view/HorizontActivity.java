package wallyson.lima.mobivitool.view;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

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
import java.util.Collections;

import wallyson.lima.mobivitool.R;
import wallyson.lima.mobivitool.dao.PostoDAO;
import wallyson.lima.mobivitool.dao.PrecipitacaoDAO;
import wallyson.lima.mobivitool.model.Precipitacao;
import wallyson.lima.mobivitool.presenter.TimelinePresenter;

public class HorizontActivity extends AppCompatActivity {
    private WebView webview;
    private String nomeArquivo, prefixo1, prefixo2, prefixo3, prefixo4, prefixo5, ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizont);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        webview = (WebView) findViewById(R.id.webviewhorizont);
        nomeArquivo = "horizont.csv";
        prefixo1 = getIntent().getStringExtra("prefixo1");
        prefixo2 = getIntent().getStringExtra("prefixo2");
        prefixo3 = getIntent().getStringExtra("prefixo3");
        prefixo4 = getIntent().getStringExtra("prefixo4");
        prefixo5 = getIntent().getStringExtra("prefixo5");
        ano = getIntent().getStringExtra("ano");
        writeData();

        //load the chart
        loadChart("html/horizontChart.html");
    }

    // initialize the WebView and the pie chart
    public void loadChart(String remoteUrl)
    {
        String html = "";
        webview.setWebViewClient(new WebViewClient());
        WebSettings webSettings =
                webview.getSettings();

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
        ArrayList<Precipitacao> pre1 = preDao.getMediaChuvaAno(prefixo1, ano);
        ArrayList<Precipitacao> pre2 = preDao.getMediaChuvaAno(prefixo2, ano);
        ArrayList<Precipitacao> pre3 = preDao.getMediaChuvaAno(prefixo3, ano);
        ArrayList<Precipitacao> pre4 = preDao.getMediaChuvaAno(prefixo4, ano);
        ArrayList<Precipitacao> pre5 = preDao.getMediaChuvaAno(prefixo5, ano);
        String municipio1 = postoDao.getMunicipioPrefixo(prefixo1);
        String municipio2 = postoDao.getMunicipioPrefixo(prefixo2);
        String municipio3 = postoDao.getMunicipioPrefixo(prefixo3);
        String municipio4 = postoDao.getMunicipioPrefixo(prefixo4);
        String municipio5 = postoDao.getMunicipioPrefixo(prefixo5);
        String texto = "";
        ArrayList<Integer> tam = new ArrayList<>();
        tam.add(pre1.size());
        tam.add(pre2.size());
        tam.add(pre3.size());
        tam.add(pre4.size());
        tam.add(pre5.size());

        Collections.sort(tam);
        int tamanho = tam.get(0);

        if ( tamanho != 0 )
            texto = "_time,WIKI/" + municipio1 + ",WIKI/" + municipio2 + ",WIKI/" + municipio3 +
                    ",WIKI/" + municipio4 + ",WIKI/" + municipio5 + "\n";
        else {
            Toast.makeText(getApplicationContext(), "Erro ! Algum municipio nao possue dados para o ano correspondente !", Toast.LENGTH_SHORT).show();
            finish();
        }

        FileOutputStream outputStream;

        for (int i = 0; i < tamanho; i++ ) {
            texto += pre1.get(i).getAno() + "-" + pre1.get(i).getMes() + "," +
                    pre1.get(i).getMedia() + "," + pre2.get(i).getMedia() + "," + pre3.get(i).getMedia() +
                    "," + pre4.get(i).getMedia() + "," + pre5.get(i).getMedia() + "\n";
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
