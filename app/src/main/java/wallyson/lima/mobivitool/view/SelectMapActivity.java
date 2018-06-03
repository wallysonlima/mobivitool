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
import wallyson.lima.mobivitool.dao.PrecipitacaoDAO;

public class SelectMapActivity extends AppCompatActivity {
    private Button btSelecionar;
    private Spinner spinPrefixo, spinAno, spinMes;
    private PostoDAO postoDao;
    private PrecipitacaoDAO preDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_map);

        spinPrefixo = (Spinner) findViewById(R.id.spinPrefixoMap);
        spinAno = (Spinner) findViewById(R.id.spinAnoMap);
        spinMes = (Spinner) findViewById(R.id.spinMesMap);
        btSelecionar = (Button) findViewById(R.id.btSelecionarMap);
        postoDao = new PostoDAO();
        preDao = new PrecipitacaoDAO();

        addPrefixoSpinner();

        btSelecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectMapActivity.this, MapActivity.class);
                intent.putExtra("ano", spinAno.getSelectedItem().toString());
                intent.putExtra("mes", spinMes.getSelectedItemPosition());
                intent.putExtra("nome_mes", spinMes.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        spinPrefixo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                addAnoSpinner(spinPrefixo.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinAno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                addMesSpinner(spinPrefixo.getSelectedItem().toString(), spinAno.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addPrefixoSpinner() {
        ArrayList<String> prefixo = postoDao.getPrefixo();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, prefixo);
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

    public void addMesSpinner(String prefixo, String ano) {
        int qtde = preDao.getQtdeMes(prefixo, ano);
        ArrayList<String> mes = new ArrayList<>();

        for ( int i = 0; i < qtde; i++) {
            switch (i) {
                case 0:
                    mes.add("Janeiro");
                    break;
                case 1:
                    mes.add("Fevereiro");
                    break;
                case 2:
                    mes.add("Março");
                    break;
                case 3:
                    mes.add("Abril");
                    break;
                case 4:
                    mes.add("Maio");
                    break;
                case 5:
                    mes.add("Junho");
                    break;
                case 6:
                    mes.add("Julho");
                    break;
                case 7:
                    mes.add("Agosto");
                    break;
                case 8:
                    mes.add("Setembro");
                    break;
                case 9:
                    mes.add("Outubro");
                    break;
                case 10:
                    mes.add("Novembro");
                    break;
                case 11:
                    mes.add("Dezembro");
                    break;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, mes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMes.setAdapter(adapter);
    }
}
