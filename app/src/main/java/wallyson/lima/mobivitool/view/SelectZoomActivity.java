package wallyson.lima.mobivitool.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import wallyson.lima.mobivitool.R;
import wallyson.lima.mobivitool.dao.PostoDAO;

public class SelectZoomActivity extends AppCompatActivity {
    private Button btSelecionar;
    private Spinner spinPrefixo, spinAno, spinQtde;
    private PostoDAO postoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_prefixo);

        spinPrefixo = (Spinner) findViewById(R.id.spinPrefixo);
        spinAno = (Spinner) findViewById(R.id.spinAnoZoom);
        spinQtde = (Spinner) findViewById(R.id.spinQtde);
        btSelecionar = (Button) findViewById(R.id.btSelecionar);
        postoDao = new PostoDAO();

        addPrefixoMunicipioSpinner();
        addQtdeSpinner();

        btSelecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectZoomActivity.this, ZoomActivity.class);
                String[] pre = spinPrefixo.getSelectedItem().toString().split("/");
                intent.putExtra("prefixo", pre[0]);
                intent.putExtra("municipio", pre[1]);
                intent.putExtra("ano", spinAno.getSelectedItem().toString());
                intent.putExtra("qtde", spinQtde.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        spinPrefixo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] pre = spinPrefixo.getSelectedItem().toString().split("/");
                addAnoSpinner(pre[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addPrefixoMunicipioSpinner() {
        ArrayList<String> premuni = postoDao.getPrefixoAndMunicipio();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, premuni);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPrefixo.setAdapter(adapter);
    }


    public void addAnoSpinner(String prefixo) {
        ArrayList<String> ano = postoDao.getAno(prefixo);

        int tam1 = ano.get(0).length();
        int tam2 = ano.get(1).length();

        if ( tam1 == 5 )
            ano.set(0, ano.get(0).substring(1));
        if ( tam2 == 5)
            ano.set(1, ano.get(1).substring(1));

        int ano_ini = Integer.parseInt(ano.get(0));
        int ano_fim = Integer.parseInt(ano.get(1));
        int duracao = ano_fim - ano_ini;
        ArrayList<String> anos = new ArrayList<>();

        for(int i = 0; i < duracao; i++ ) {
            anos.add(String.valueOf(ano_ini + i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, anos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAno.setAdapter(adapter);
    }

    public void addQtdeSpinner() {
        ArrayList<String> qtde = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            qtde.add(String.valueOf(i+1));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, qtde);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinQtde.setAdapter(adapter);
    }
}
