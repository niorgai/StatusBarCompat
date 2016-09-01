package qiu.statusbarcompat.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.HashMap;

import qiu.statusbarcompat.R;
import qiu.statusbarcompat.fragment.CollapsingToolbarFragment;
import qiu.statusbarcompat.fragment.CommonFragment;
import qiu.statusbarcompat.fragment.DrawerFragment;
import qiu.statusbarcompat.fragment.TranslucentFragment;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    public static final int DEFAULT_COLOR = Color.parseColor("#319bd2");

    public static final int PAGE_COMMON = 0;
    public static final int PAGE_TRANSLUCENT = 1;
    public static final int PAGE_COORDINATOR = 2;
    public static final int PAGE_COLLAPSING_TOOLBAR = 3;

    private HashMap<Integer,Fragment> fragments = new HashMap<>();

    private RadioButton mCommon;
    private RadioButton mTranslucent;
    private RadioButton mCoordinator;
    private RadioButton mCollapsingToolbar;
    private int fragmentContentId = R.id.container;
    private int currentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);

        fragments.put(PAGE_COMMON, new CommonFragment());
        fragments.put(PAGE_TRANSLUCENT, new TranslucentFragment());
        fragments.put(PAGE_COORDINATOR, new DrawerFragment());
        fragments.put(PAGE_COLLAPSING_TOOLBAR, new CollapsingToolbarFragment());

        mCommon = (RadioButton) findViewById(R.id.common);
        mCommon.setOnCheckedChangeListener(this);
        mTranslucent = (RadioButton) findViewById(R.id.translucent);
        mTranslucent.setOnCheckedChangeListener(this);
        mCoordinator = (RadioButton) findViewById(R.id.coordinator);
        mCoordinator.setOnCheckedChangeListener(this);
        mCollapsingToolbar = (RadioButton) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbar.setOnCheckedChangeListener(this);

        FragmentTransaction ft = MainActivity.this.getSupportFragmentManager().beginTransaction();
        ft.add(fragmentContentId, fragments.get(PAGE_COMMON));
        currentTab = PAGE_COMMON;
        mCommon.setTextColor(getResources().getColor(R.color.colorAccent));
        ft.commit();
    }

    private void changeTab(int page) {
        if (currentTab == page) {
            return;
        }
        Fragment fragment = fragments.get(page);
        FragmentTransaction ft = MainActivity.this.getSupportFragmentManager().beginTransaction();
        if(!fragment.isAdded()){
            ft.add(fragmentContentId,fragment);
        }
        ft.hide(fragments.get(currentTab));
        ft.show(fragments.get(page));
        changeButtonStatus(currentTab, false);
        currentTab = page;
        changeButtonStatus(currentTab, true);
        if (!this.isFinishing()) {
            ft.commitAllowingStateLoss();
        }
    }

    private void changeButtonStatus(int index, boolean check){
        RadioButton button;
        switch (index){
            case PAGE_COMMON:
                button = mCommon;
                break;
            case PAGE_TRANSLUCENT:
                button = mTranslucent;
                break;
            case PAGE_COORDINATOR:
                button = mCoordinator;
                break;
            case PAGE_COLLAPSING_TOOLBAR:
                button = mCollapsingToolbar;
                break;
            default:
                button = mCommon;
        }
        button.setOnCheckedChangeListener(null);
        button.setChecked(check);
        button.setOnCheckedChangeListener(this);
        if (check) {
            button.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else {
            button.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.common:
                changeTab(PAGE_COMMON);
                break;
            case R.id.translucent:
                changeTab(PAGE_TRANSLUCENT);
                break;
            case R.id.coordinator:
                changeTab(PAGE_COORDINATOR);
                break;
            default:
                changeTab(PAGE_COLLAPSING_TOOLBAR);
                break;
        }
    }
}
