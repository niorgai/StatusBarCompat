package qiu.statusbarcompat.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qiu.niorgai.StatusBarCompat;
import qiu.statusbarcompat.R;

/**
 * Test for translucent
 */
public class TranslucentFragment extends Fragment {

    private boolean isHide = false;

    public TranslucentFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_translucent, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View text = view.findViewById(R.id.text);
        View toggle = view.findViewById(R.id.toggle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            text.setVisibility(View.VISIBLE);
            toggle.setVisibility(View.VISIBLE);
        } else {
            text.setVisibility(View.GONE);
            toggle.setVisibility(View.GONE);
        }


        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusBarCompat.translucentStatusBar(getActivity(), isHide);
                isHide = !isHide;
            }
        });

        toggle.callOnClick();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.translucentStatusBar(getActivity(), isHide);
            isHide = !isHide;
        }
    }
}
