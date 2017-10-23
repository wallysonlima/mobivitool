package wallyson.lima.mobivitool.view;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import wallyson.lima.mobivitool.R;
import wallyson.lima.mobivitool.presenter.TimelinePresenter;
import wallyson.lima.mobivitool.presenter.TimelineInterface;

public class TimelineActivity extends AppCompatActivity implements TimelineInterface {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private WebView webview;
    private TimelinePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webview = (WebView) findViewById(R.id.webview);
        mPresenter = new TimelinePresenter(this, this.getApplicationContext() );

        //load the chart
        mPresenter.loadChart("html/timelineChart.html", webview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
