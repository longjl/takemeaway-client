package com.takemeaway.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.achep.header2actionbar.HeaderFragment;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.takemeaway.R;
import com.takemeaway.TakeMeAwayApplication;
import com.takemeaway.activities.LoginActivity;
import com.takemeaway.activities.MyInfoActivity;
import com.takemeaway.activities.PavilionActivity;
import com.takemeaway.adapters.PavilionAdapter;
import com.takemeaway.bean.Photo;
import com.takemeaway.bean.User;
import com.takemeaway.view.CircleImageView;
import com.takemeaway.view.FontTextView;
import com.takemeaway.view.MyGridView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by longjianlin on 14/12/23.
 */
public class PavilionFragment extends HeaderFragment implements View.OnClickListener {

    private MyGridView mGridView;
    private List<Photo> photos;
    private boolean mLoaded;

    private CircleImageView mCivAvatar;                  //用户头像
    private FontTextView mFtvNickname;          //用户昵称
    private FrameLayout mContentOverlay;

    private PavilionAdapter adapter;

    private ImageLoader imageLoader;


    public PavilionFragment() {
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (TakeMeAwayApplication.getApp().getIsLogin()
                ) {//已经登录
            User user = TakeMeAwayApplication.getApp().getUser();
            if (user != null) {
                setFtvNicknameText(user);
            }
        } else {
            mFtvNickname.setText(R.string.go_login_prompt);
            mFtvNickname.setOnClickListener(this);
        }

        if (photos == null) photos = new ArrayList<Photo>();

        handler.sendEmptyMessageDelayed(0, 3000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (mContentOverlay != null) mContentOverlay.setVisibility(View.GONE);

                mLoaded = true;
                for (int i = 0; i < 22; i++) {
                    Photo photo = new Photo();
                    photo.id = 1;
                    photo.user_id = 1;
                    photo.photo_url = "http://test-dev-leap-image.qiniudn.com/images/course/141/219/1207/20141219120733028.png";
                    photos.add(photo);
                }

                if (adapter == null) adapter = new PavilionAdapter(getActivity(), photos);
                if (mGridView != null) {
                    mGridView.setVisibility(View.VISIBLE);
                    mGridView.setAdapter(adapter);
                }
                mCivAvatar.setImageResource(R.drawable.longjl);
            }
        }
    };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        setHeaderBackgroundScrollMode(HEADER_BACKGROUND_SCROLL_PARALLAX);
        setOnHeaderScrollChangedListener(new OnHeaderScrollChangedListener() {
            @Override
            public void onHeaderScrollChanged(float progress, int height, int scroll) {
                height -= getActivity().getActionBar().getHeight();

                progress = (float) scroll / height;
                if (progress > 1f) progress = 1f;

                progress = (1 - (float) Math.cos(progress * Math.PI)) * 0.5f;

                ((PavilionActivity) getActivity()).getFadingActionBarHelper().setActionBarAlpha((int) (255 * progress));
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateHeaderView(LayoutInflater inflater, ViewGroup container) {
        View mRootView = inflater.inflate(R.layout.fragment_header, container, false);
        mCivAvatar = (CircleImageView) mRootView.findViewById(R.id.civ_avatar);
        mCivAvatar.setOnClickListener(this);
        mFtvNickname = (FontTextView) mRootView.findViewById(R.id.ftv_nickname);
        return mRootView;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        mGridView = (MyGridView) inflater.inflate(R.layout.fragment_gridview, container, false);
        return mGridView;
    }

    @Override
    public View onCreateContentOverlayView(LayoutInflater inflater, ViewGroup container) {
        ProgressBar progressBar = new ProgressBar(getActivity());
        mContentOverlay = new FrameLayout(getActivity());
        mContentOverlay.addView(progressBar, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));

        if (mLoaded) mContentOverlay.setVisibility(View.GONE);

        return mContentOverlay;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mFtvNickname.getId()) {
            if (!TakeMeAwayApplication.getApp().getIsLogin()) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, 100);
            }
        } else if (v.getId() == mCivAvatar.getId()) {
            if (TakeMeAwayApplication.getApp().getIsLogin()) {
                Intent intent = new Intent(getActivity(), MyInfoActivity.class);
                startActivity(intent);
            } else {//去登录

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 200:
                User user = TakeMeAwayApplication.getApp().getUser();
                if (user != null) {
                    setFtvNicknameText(user);
                }
                break;
            default:
                break;
        }

    }

    /**
     * 昵称
     *
     * @param user
     */
    private void setFtvNicknameText(final User user) {
        if (user.nickname != null && user.nickname.length() > 0) {
            mFtvNickname.setText(user.nickname);
        } else if (user.username != null && user.username.length() > 0) {
            mFtvNickname.setText(user.username);
        } else {
            mFtvNickname.setText(R.string.nickname_prompt);
        }
    }
}
