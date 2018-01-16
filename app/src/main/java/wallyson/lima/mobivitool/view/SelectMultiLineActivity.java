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

public class SelectMultiLineActivity extends AppCompatActivity {
    private Button btSelecionar;
    private Spinner spinPrefixo1, spinAno1;
    private PostoDAO postoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_multi_line);

        spinPrefixo1 = (Spinner) findViewById(R.id.spinPrefixo1Multi);
        spinAno1 = (Spinner) findViewById(R.id.spinAno1Multi);
        btSelecionar = (Button) findViewById(R.id.btSelecionarMulti);
        postoDao = new PostoDAO();

        addPrefixoSpinner();

        btSelecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectMultiLineActivity.this, MultiLineActivity.class);
                intent.putExtra("prefixo1", spinPrefixo1.getSelectedItem().toString());
                intent.putExtra("ano1", spinAno1.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        spinPrefixo1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                addAno1Spinner(spinPrefixo1.getSelectedItem().toString());
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
        spinPrefixo1.setAdapter(adapter);
    }

    public void addAno1Spinner(String prefixo) {
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
        spinAno1.setAdapter(adapter);
    }
}
