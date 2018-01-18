package wallyson.lima.mobivitool.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

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
    }
}
