package wallyson.lima.mobivitool.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import wallyson.lima.mobivitool.R;

public class MainActivity extends AppCompatActivity {
    private ImageButton btTimeline, btZoom, btMultiline, btMap, btSimple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btTimeline = (ImageButton) findViewById(R.id.btTimeline);
        btZoom = (ImageButton) findViewById(R.id.btZoom);
        btMultiline = (ImageButton) findViewById(R.id.btMultiline);
        btMap = (ImageButton) findViewById(R.id.btMap);
        btSimple = (ImageButton) findViewById(R.id.btSimple);

        btTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectTimelineActivity.class);
                startActivity(intent);
            }
        });

        btZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectZoomActivity.class);
                startActivity(intent);
            }
        });

        btMultiline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectMultiLineActivity.class);
                startActivity(intent);
            }
        });

        btMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectMapActivity.class);
                startActivity(intent);
            }
        });

        btSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectSimpleActivity.class);
                startActivity(intent);
            }
        });

    }
}
