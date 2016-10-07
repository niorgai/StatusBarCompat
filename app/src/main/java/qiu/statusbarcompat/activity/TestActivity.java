package qiu.statusbarcompat.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import qiu.niorgai.StatusBarCompat;
import qiu.statusbarcompat.R;

/**
 * Created by qiu on 10/2/16.
 */
public class TestActivity extends AppCompatActivity {

    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_collapsing_tool_bar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        StatusBarCompat.setStatusBarColorForCollapsingToolbar(this, mAppBarLayout, mCollapsingToolbarLayout, mToolbar, Color.GREEN);
    }
}
