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

public class SelectSimpleActivity extends AppCompatActivity {
    private Button btSelecionar;
    private Spinner spinPrefixo, spinAno;
    private PostoDAO postoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_simple);

        spinPrefixo = (Spinner) findViewById(R.id.spinPrefixoSimple);
        spinAno = (Spinner) findViewById(R.id.spinAnoSimple);
        btSelecionar = (Button) findViewById(R.id.btSelecionarSimple);
        postoDao = new PostoDAO();

        addPrefixoSpinner();

        btSelecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectSimpleActivity.this, SimpleChartActivity.class);
                intent.putExtra("prefixo", spinPrefixo.getSelectedItem().toString());
                intent.putExtra("ano", spinAno.getSelectedItem().toString());
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
    }

    public void addPrefixoSpinner() {
        ArrayList<String> prefixo = postoDao.getPrefixo();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, prefixo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPrefixo.setAdapter(adapter);
    }

    public void addAnoSpinner(String prefixo) {
        ArrayList<String> ano = postoDao.getAno(prefixo);
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


}
