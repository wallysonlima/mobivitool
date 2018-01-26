package wallyson.lima.mobivitool.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import wallyson.lima.mobivitool.R;
import wallyson.lima.mobivitool.dao.PostoDAO;

public class SelectZoomActivity extends AppCompatActivity {
    private Button btSelecionar;
    private Spinner spinPrefixo;
    private PostoDAO postoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_prefixo);

        spinPrefixo = (Spinner) findViewById(R.id.spinPrefixo);
        btSelecionar = (Button) findViewById(R.id.btSelecionar);
        postoDao = new PostoDAO();

        addPrefixoMunicipioSpinner();

        btSelecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectZoomActivity.this, ZoomActivity.class);
                String[] pre = spinPrefixo.getSelectedItem().toString().split("/");
                intent.putExtra("prefixo", pre[0]);
                startActivity(intent);
            }
        });
    }

    public void addPrefixoMunicipioSpinner() {
        ArrayList<String> premuni = postoDao.getPrefixoAndMunicipio();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, premuni);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPrefixo.setAdapter(adapter);
    }
}
