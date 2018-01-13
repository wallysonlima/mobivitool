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

public class SelectPrefixoActivity extends AppCompatActivity {
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

        addPrefixoSpinner();

        btSelecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectPrefixoActivity.this, ZoomActivity.class);
                intent.putExtra("prefixo", spinPrefixo.getSelectedItem().toString());
                startActivity(intent);
            }
        });
    }

    public void addPrefixoSpinner() {
        ArrayList<String> prefixo = postoDao.getPrefixo();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, prefixo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPrefixo.setAdapter(adapter);
    }
}
