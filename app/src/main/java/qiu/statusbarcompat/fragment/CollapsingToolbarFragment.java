package qiu.statusbarcompat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qiu.statusbarcompat.R;
import qiu.statusbarcompat.activity.MainActivity;
import qiu.statusbarcompat.compat.StatusBarCompat1;

/**
 * Created by qiu on 7/27/16.
 */
public class CollapsingToolbarFragment extends Fragment {

    private boolean isTranslucent = false;

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
        View toggle = view.findViewById(R.id.toggle);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTranslucent) {
                    StatusBarCompat1.setStatusBarColorWithCollapsingToolbar(getActivity(), MainActivity.DEFAULT_COLOR);
                } else {
                    StatusBarCompat1.translucentStatusBar(getActivity());
                }
                isTranslucent = !isTranslucent;
            }
        });

        toggle.callOnClick();
    }
}
