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

public class SelectHorizontActivity extends AppCompatActivity {
    private Button btSelecionar;
    private Spinner spinPrefixo1, spinPrefixo2, spinPrefixo3, spinPrefixo4, spinPrefixo5,
            spinAno;
    private PostoDAO postoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_horizont);

        spinPrefixo1 = (Spinner) findViewById(R.id.spinPrefixo1Horizont);
        spinPrefixo2 = (Spinner) findViewById(R.id.spinPrefixo2Horizont);
        spinPrefixo3 = (Spinner) findViewById(R.id.spinPrefixo3Horizont);
        spinPrefixo4 = (Spinner) findViewById(R.id.spinPrefixo4Horizont);
        spinPrefixo5 = (Spinner) findViewById(R.id.spinPrefixo5Horizont);
        spinAno = (Spinner) findViewById(R.id.spinAnoHorizont);
        btSelecionar = (Button) findViewById(R.id.btSelecionarHorizont);
        postoDao = new PostoDAO();

        addMunicipioSpinner();

        btSelecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectHorizontActivity.this, HorizontActivity.class);

                intent.putExtra("prefixo1", postoDao.getPrefixoMunicipio(spinPrefixo1.getSelectedItem().toString()));
                intent.putExtra("prefixo2", postoDao.getPrefixoMunicipio(spinPrefixo2.getSelectedItem().toString()));
                intent.putExtra("prefixo3", postoDao.getPrefixoMunicipio(spinPrefixo3.getSelectedItem().toString()));
                intent.putExtra("prefixo3", postoDao.getPrefixoMunicipio(spinPrefixo4.getSelectedItem().toString()));
                intent.putExtra("prefixo3", postoDao.getPrefixoMunicipio(spinPrefixo5.getSelectedItem().toString()));
                intent.putExtra("ano1", spinAno.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        spinPrefixo1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                addAnoSpinner(spinPrefixo1.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addMunicipioSpinner() {
        ArrayList<String> municipio = postoDao.getMunicipio();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, municipio);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPrefixo1.setAdapter(adapter);
        spinPrefixo2.setAdapter(adapter);
        spinPrefixo3.setAdapter(adapter);
        spinPrefixo4.setAdapter(adapter);
        spinPrefixo5.setAdapter(adapter);
    }

    public void addAnoSpinner(String municipio) {
        String prefixo = postoDao.getPrefixoMunicipio(municipio);
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
