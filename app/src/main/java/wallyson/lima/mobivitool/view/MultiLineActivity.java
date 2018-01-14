package wallyson.lima.mobivitool.view;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

import wallyson.lima.mobivitool.R;
import wallyson.lima.mobivitool.dao.PostoDAO;
import wallyson.lima.mobivitool.dao.PrecipitacaoDAO;
import wallyson.lima.mobivitool.model.Precipitacao;
import wallyson.lima.mobivitool.presenter.MultiInterface;
import wallyson.lima.mobivitool.presenter.MultiPresenter;
import wallyson.lima.mobivitool.presenter.TimelinePresenter;

public class MultiLineActivity extends AppCompatActivity implements MultiInterface {
    private WebView webview;
    private MultiPresenter mPresenter;
    private String nomeArquivo, prefixo1, prefixo2, prefixo3, ano1, ano2, ano3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_line);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        webview = (WebView) findViewById(R.id.webviewmulti);
        mPresenter = new MultiPresenter(this, this.getApplicationContext(), webview);
        nomeArquivo = "multiline.csv";
        prefixo1 = getIntent().getStringExtra("prefixo1");
        prefixo2 = getIntent().getStringExtra("prefixo2");
        prefixo3 = getIntent().getStringExtra("prefixo3");
        ano1 = getIntent().getStringExtra("ano1");
        ano2 = getIntent().getStringExtra("ano2");
        ano3 = getIntent().getStringExtra("ano3");
        writeData();
        //load the chart
        loadChart("html/multilineChart.html");
    }

    // initialize the WebView and the pie chart
    public void loadChart(String remoteUrl)
    {
        String html = "";
        webview.setWebViewClient(new WebViewClient());
        WebSettings webSettings =
                webview.getSettings();

        webview.addJavascriptInterface(mPresenter, "AndroidMultiLine");
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
        ArrayList<Precipitacao> arrayPre1 = preDao.getMediaChuvaAno(prefixo1, ano1);
        ArrayList<Precipitacao> arrayPre2 = preDao.getMediaChuvaAno(prefixo2, ano2);
        ArrayList<Precipitacao> arrayPre3 = preDao.getMediaChuvaAno(prefixo3, ano3);
        ArrayList<String> arrayNome = postoDao.getNome(prefixo1, prefixo2, prefixo3);
        String texto = "";
        int tam1 = arrayPre1.size();
        int tam2 = arrayPre2.size();
        int tam3 = arrayPre3.size();
        int tamanho = 0;

        if ( tam1 < tam2 )
            if ( tam1 < tam3 )
                tamanho = tam1;
            else
                tamanho = tam3;
        else if ( tam2 < tam3 )
            tamanho = tam2;
        else
            tamanho = tam3;

        if ( arrayNome.size() == 3)
            texto = "date,Nome:" + arrayNome.get(0) + ",Nome:" + arrayNome.get(1) + ",Nome:" + arrayNome.get(2) + "\n";
        else {
            Toast.makeText(getApplicationContext(), "Erro ! Selecione Arquivos Diferentes !", Toast.LENGTH_SHORT).show();
            finish();
        }

        FileOutputStream outputStream;

        for (int i = 0; i < tamanho; i++ ) {

            switch (arrayPre1.get(i).getMes() ) {
                case "01":
                    texto += "Jan," + arrayPre1.get(i).getMedia() + "," + arrayPre2.get(i).getMedia() + "," +
                            arrayPre3.get(i).getMedia()+ "\n";
                    break;
                case "02":
                    texto += "Feb," + arrayPre1.get(i).getMedia() + "," + arrayPre2.get(i).getMedia() + "," +
                            arrayPre3.get(i).getMedia()+ "\n";
                    break;
                case "03":
                    texto += "Mar," + arrayPre1.get(i).getMedia() + "," + arrayPre2.get(i).getMedia() + "," +
                            arrayPre3.get(i).getMedia()+ "\n";
                    break;
                case "04":
                    texto += "Apr," + arrayPre1.get(i).getMedia() + "," + arrayPre2.get(i).getMedia() + "," +
                            arrayPre3.get(i).getMedia()+ "\n";
                    break;
                case "05":
                    texto += "May," + arrayPre1.get(i).getMedia() + "," + arrayPre2.get(i).getMedia() + "," +
                            arrayPre3.get(i).getMedia()+ "\n";
                    break;
                case "06":
                    texto += "Jun," + arrayPre1.get(i).getMedia() + "," + arrayPre2.get(i).getMedia() + "," +
                            arrayPre3.get(i).getMedia()+ "\n";
                    break;
                case "07":
                    texto += "Jul," + arrayPre1.get(i).getMedia() + "," + arrayPre2.get(i).getMedia() + "," +
                            arrayPre3.get(i).getMedia()+ "\n";
                    break;
                case "08":
                    texto += "Aug," + arrayPre1.get(i).getMedia() + "," + arrayPre2.get(i).getMedia() + "," +
                            arrayPre3.get(i).getMedia()+ "\n";
                    break;
                case "09":
                    texto += "Sep," + arrayPre1.get(i).getMedia() + "," + arrayPre2.get(i).getMedia() + "," +
                            arrayPre3.get(i).getMedia()+ "\n";
                    break;
                case "10":
                    texto += "Oct," + arrayPre1.get(i).getMedia() + "," + arrayPre2.get(i).getMedia() + "," +
                            arrayPre3.get(i).getMedia()+ "\n";
                    break;
                case "11":
                    texto += "Nov," + arrayPre1.get(i).getMedia() + "," + arrayPre2.get(i).getMedia() + "," +
                            arrayPre3.get(i).getMedia()+ "\n";
                    break;
                case "12":
                    texto += "Dec," + arrayPre1.get(i).getMedia() + "," + arrayPre2.get(i).getMedia() + "," +
                            arrayPre3.get(i).getMedia()+ "\n";
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
}
