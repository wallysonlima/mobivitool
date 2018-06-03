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

public class SelectTimelineActivity extends AppCompatActivity {
    private Button btSelecionar;
    private Spinner spinPrefixo1, spinPrefixo2, spinPrefixo3, spinAno1, spinAno2, spinAno3;
    private PostoDAO postoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_timeline);

        spinPrefixo1 = (Spinner) findViewById(R.id.spinPrefixo1Timeline);
        spinPrefixo2 = (Spinner) findViewById(R.id.spinPrefixo2Timeline);
        spinPrefixo3 = (Spinner) findViewById(R.id.spinPrefixo3Timeline);
        spinAno1 = (Spinner) findViewById(R.id.spinAno1Timeline);
        spinAno2 = (Spinner) findViewById(R.id.spinAno2Timeline);
        spinAno3 = (Spinner) findViewById(R.id.spinAno3Timeline);
        btSelecionar = (Button) findViewById(R.id.btSelecionarTimeline);
        postoDao = new PostoDAO();

        addPrefixoMunicipioSpinner();

        btSelecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectTimelineActivity.this, TimelineActivity.class);
                String[] pre1 = spinPrefixo1.getSelectedItem().toString().split("/");
                String[] pre2 = spinPrefixo2.getSelectedItem().toString().split("/");
                String[] pre3 = spinPrefixo3.getSelectedItem().toString().split("/");
                intent.putExtra("prefixo1", pre1[0]);
                intent.putExtra("prefixo2", pre2[0]);
                intent.putExtra("prefixo3", pre3[0]);
                intent.putExtra("ano1", spinAno1.getSelectedItem().toString());
                intent.putExtra("ano2", spinAno2.getSelectedItem().toString());
                intent.putExtra("ano3", spinAno3.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        spinPrefixo1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] pre1 = spinPrefixo1.getSelectedItem().toString().split("/");
                addAno1Spinner(pre1[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinPrefixo2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] pre2 = spinPrefixo2.getSelectedItem().toString().split("/");
                addAno2Spinner(pre2[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinPrefixo3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] pre3 = spinPrefixo3.getSelectedItem().toString().split("/");
                addAno3Spinner(pre3[0]);
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
        spinPrefixo1.setAdapter(adapter);
        spinPrefixo2.setAdapter(adapter);
        spinPrefixo3.setAdapter(adapter);
    }

    public void addAno1Spinner(String prefixo) {
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
        spinAno1.setAdapter(adapter);
    }

    public void addAno2Spinner(String prefixo) {
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
        spinAno2.setAdapter(adapter);
    }

    public void addAno3Spinner(String prefixo) {
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
        spinAno3.setAdapter(adapter);
    }
}
