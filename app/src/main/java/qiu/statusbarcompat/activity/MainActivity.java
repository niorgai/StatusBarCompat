package qiu.statusbarcompat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import qiu.statusbarcompat.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.margin).setOnClickListener(this);
        findViewById(R.id.parent).setOnClickListener(this);
        findViewById(R.id.content_not_translucent).setOnClickListener(this);
        findViewById(R.id.content_translucent).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.margin:
                startActivity(new Intent(this, MarginCompatActivity.class));
                break;
            case R.id.parent:
                startActivity(new Intent(this, ParentCompatActivity.class));
                break;
            case R.id.content_not_translucent:
                startActivity(new Intent(this, ContentCompatNotTranslucentActivity.class));
                break;
            case R.id.content_translucent:
                startActivity(new Intent(this, ContentCompatTranslucentActivity.class));
                break;
        }
    }
}
