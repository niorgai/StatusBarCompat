package qiu.statusbarcompat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qiu.niorgai.StatusBarCompat;
import qiu.statusbarcompat.R;
import qiu.statusbarcompat.activity.MainActivity;

/**
 * Compat CollapsingToolbarLayout
 * Created by qiu on 7/27/16.
 */
public class CollapsingToolbarFragment extends Fragment {

    public CollapsingToolbarFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collapsing_tool_bar, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        StatusBarCompat.setStatusBarColorWithCollapsingToolbar(getActivity(), MainActivity.DEFAULT_COLOR);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.setStatusBarColorWithCollapsingToolbar(getActivity(), MainActivity.DEFAULT_COLOR);
        }
    }
}
