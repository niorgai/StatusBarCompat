package qiu.statusbarcompat.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import qiu.niorgai.StatusBarCompat;
import qiu.statusbarcompat.R;

public class MarginCompatActivity extends AppCompatActivity {

    private boolean isTranslucent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_margin_compat);
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
            StatusBarCompat.translucentStatusBar(this, true);
            StatusBarCompat.translucentStatusBar(this);
//            StatusBarUtil.setTranslucent(this);
        } else {
            StatusBarCompat.setStatusBarColor(this, Color.parseColor("#FF4F4F"));
            StatusBarCompat.setStatusBarColor(this, Color.parseColor("#FF4F4F"), 112);
//            StatusBarUtil.setColor(this, StatusBarCompat.COLOR_DEFAULT_PINK, 0);
        }
    }

}
