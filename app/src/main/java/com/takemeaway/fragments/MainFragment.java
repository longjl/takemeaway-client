package com.takemeaway.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.takemeaway.R;

/**
 * Created by longjianlin on 14/12/21.
 */
public class MainFragment extends Fragment {
    private View mRootView;
    private PullToRefreshGridView mPullRefreshGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null)
            mRootView = inflater.inflate(R.layout.fragment_main, container, false);

        mPullRefreshGridView = (PullToRefreshGridView) mRootView.findViewById(R.id.pull_to_refresh_grid);

        TextView tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setText("Empty View, Pull Down/Up to Add Items");
        mPullRefreshGridView.setEmptyView(tv);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
