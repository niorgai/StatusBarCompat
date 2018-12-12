package qiu.statusbarcompat.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import qiu.niorgai.StatusBarCompat;
import qiu.statusbarcompat.R;

/**
 * Created by jianqiu on 12/12/18.
 */
public class BaseFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    protected CheckBox mCheckBox;

    protected void initCheckBox() {
        if (getView() == null) {
            return;
        }
        mCheckBox = getView().findViewById(R.id.light_status_bar);
        mCheckBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCheckBox();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            StatusBarCompat.changeToLightStatusBar(getActivity());
        } else {
            StatusBarCompat.cancelLightStatusBar(getActivity());
        }
    }
}
