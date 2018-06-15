package com.yan.refreshloadlayouttest.testactivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yan.refreshloadlayouttest.R;

public class DataFragment extends Fragment {

    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.data_fragment, container, false);
        }
        return root;
    }


    public void reload() {
        if (getView() == null) {
            return;
        }
        LinearLayout linearLayout = getView().findViewById(R.id.ll_container);
        TextView tvData = new TextView(getContext());
        tvData.setPadding(50, 50, 50, 50);
        tvData.setText("测试 ~~~");
        tvData.setGravity(Gravity.CENTER);
        linearLayout.addView(tvData, 0, new LinearLayout.LayoutParams(-1, -2));
    }
}
