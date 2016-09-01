package qiu.statusbarcompat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import qiu.niorgai.StatusBarCompat;
import qiu.statusbarcompat.R;
import qiu.statusbarcompat.activity.MainActivity;

public class CommonFragment extends Fragment {

    private boolean isTranslucent = false;

    private View mText;
    private View mImage;
    private SeekBar mSeekBar;
    private View mToggle;

    public CommonFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_common, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mText = view.findViewById(R.id.text);
        mImage = view.findViewById(R.id.img);
        mSeekBar = (SeekBar) view.findViewById(R.id.seek_bar);
        mToggle = view.findViewById(R.id.toggle);


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!isTranslucent) {
                    StatusBarCompat.setStatusBarColor(getActivity(), MainActivity.DEFAULT_COLOR, progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTranslucent) {
                    StatusBarCompat.setStatusBarColor(getActivity(), MainActivity.DEFAULT_COLOR);
                    mSeekBar.setVisibility(View.VISIBLE);
                    mImage.setVisibility(View.GONE);
                    mText.setVisibility(View.VISIBLE);
                } else {
                    StatusBarCompat.translucentStatusBar(getActivity());
                    mSeekBar.setVisibility(View.GONE);
                    mImage.setVisibility(View.VISIBLE);
                    mText.setVisibility(View.GONE);
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
