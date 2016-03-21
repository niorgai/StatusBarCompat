package qiu.statusbarcompat.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import qiu.statusbarcompat.R;
import qiu.statusbarcompat.compat.StatusBarCompat2;

public class ContentCompatNotTranslucentActivity extends AppCompatActivity {

    private boolean isTranslucent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_compat);
        toggleTranslucent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isTranslucent = !isTranslucent;
                toggleTranslucent();
                Snackbar.make(view, "isTranslucent : " + isTranslucent, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    protected void toggleTranslucent() {
        if (isTranslucent) {
            StatusBarCompat2.translucentStatusBar(this);
            StatusBarCompat2.translucentStatusBar(this);
        } else {
            StatusBarCompat2.setStatusBarColor(this, StatusBarCompat2.COLOR_DEFAULT_WHITE);
            StatusBarCompat2.setStatusBarColor(this);
        }
    }

}
