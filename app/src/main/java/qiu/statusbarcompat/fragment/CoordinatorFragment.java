package qiu.statusbarcompat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qiu.niorgai.StatusBarCompat;
import qiu.statusbarcompat.R;
import qiu.statusbarcompat.activity.MainActivity;

/**
 *
 */
public class CoordinatorFragment extends Fragment {

    private View mToggle;
    private boolean isTranslucent = false;

    public CoordinatorFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coordinator, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToggle = view.findViewById(R.id.toggle);
        mToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTranslucent) {
                    StatusBarCompat.setStatusBarColor(getActivity(), MainActivity.DEFAULT_COLOR);
                    StatusBarCompat.setStatusBarColor(getActivity(), MainActivity.DEFAULT_COLOR);
                } else {
                    StatusBarCompat.translucentStatusBar(getActivity());
                }
                isTranslucent = !isTranslucent;
            }
        });

        mToggle.callOnClick();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mToggle.callOnClick();
        }
    }
}
