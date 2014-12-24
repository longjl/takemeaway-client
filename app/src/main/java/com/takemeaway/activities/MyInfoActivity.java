package com.takemeaway.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.takemeaway.R;

/**
 * 个人信息
 * Created by longjianlin on 14/12/24.
 */
public class MyInfoActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout relative_layout_avatar;          //头像
    private RelativeLayout relative_layout_nickname;        //昵称
    private RelativeLayout relative_layout_mobile;          //手机号
    private RelativeLayout relative_layout_age;             //年龄
    private RelativeLayout relative_layout_sex;             //性别
    private RelativeLayout relative_layout_educational;     //学历
    private RelativeLayout relative_layout_area;            //地区
    private RelativeLayout relative_layout_cost;            //伴游费
    private Button btn_authentication;                      //认证

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        relative_layout_avatar = (RelativeLayout) findViewById(R.id.relative_layout_avatar);
        relative_layout_avatar.setOnClickListener(this);

        relative_layout_nickname = (RelativeLayout) findViewById(R.id.relative_layout_nickname);
        relative_layout_nickname.setOnClickListener(this);

        relative_layout_mobile = (RelativeLayout) findViewById(R.id.relative_layout_mobile);
        relative_layout_mobile.setOnClickListener(this);

        relative_layout_age = (RelativeLayout) findViewById(R.id.relative_layout_age);
        relative_layout_age.setOnClickListener(this);

        relative_layout_sex = (RelativeLayout) findViewById(R.id.relative_layout_sex);
        relative_layout_sex.setOnClickListener(this);

        relative_layout_educational = (RelativeLayout) findViewById(R.id.relative_layout_educational);
        relative_layout_educational.setOnClickListener(this);

        relative_layout_area = (RelativeLayout) findViewById(R.id.relative_layout_area);
        relative_layout_area.setOnClickListener(this);

        relative_layout_cost = (RelativeLayout) findViewById(R.id.relative_layout_cost);
        relative_layout_cost.setOnClickListener(this);

        btn_authentication = (Button) findViewById(R.id.btn_authentication);
        btn_authentication.setOnClickListener(this);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == relative_layout_avatar.getId()) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
