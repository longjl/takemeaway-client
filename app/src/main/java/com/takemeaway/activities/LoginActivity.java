package com.takemeaway.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.takemeaway.R;
import com.takemeaway.TakeMeAwayApplication;
import com.takemeaway.URLs;
import com.takemeaway.bean.User;
import com.takemeaway.network.TMARestClient;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by longjianlin on 14/12/22.
 */
public class LoginActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private EditText edit_login;
    private EditText edit_password;
    private AutoCompleteTextView actv_email;
    private RadioGroup rg_type;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_login = (EditText) findViewById(R.id.edit_login);
        edit_password = (EditText) findViewById(R.id.edit_password);
        actv_email = (AutoCompleteTextView) findViewById(R.id.actv_email);
        rg_type = (RadioGroup) findViewById(R.id.rg_type);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        rg_type.setOnCheckedChangeListener(this);
        btn_submit.setOnClickListener(this);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
    }

    /**
     * 数据验证
     */
    private void validate() {
        if (edit_login.getText() == null || edit_login.getText().toString().length() <= 0) return;
        if (edit_password.getText() == null || edit_password.getText().toString().length() <= 0)
            return;

        switch (rg_type.getCheckedRadioButtonId()) {
            case R.id.rb_login:
                login(edit_login.getText().toString(), edit_password.getText().toString());
                break;
            case R.id.rb_create:
                if (actv_email.getText() == null || actv_email.getText().toString().length() <= 0)
                    return;

                register(edit_login.getText().toString(), edit_password.getText().toString(), actv_email.getText().toString());
                break;
            default:
                break;
        }
    }

    /**
     * 用户登录
     *
     * @param account
     * @param pwd
     */
    private void login(final String account, final String pwd) {
        RequestParams params = new RequestParams();
        params.put("account", account);
        params.put("pwd", pwd);
        TMARestClient.get(URLs.USER_LOGIN_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response == null) return;

                if (response.optInt("code") == 200) {
                    JSONObject recordJsonObj = response.optJSONObject("record");
                    if (recordJsonObj != null) {
                        User user = new User();
                        user.id = recordJsonObj.optLong("id");
                        user.username = recordJsonObj.optString("username");
                        user.email = recordJsonObj.optString("email");

                        String nickname = recordJsonObj.optString("nickname");
                        if (nickname != null && nickname.length() > 0) {
                            user.nickname = nickname;
                        }
                        String avatar = recordJsonObj.optString("avatar");
                        if (avatar != null && avatar.length() > 0) {
                            user.avatar = avatar;
                        }
                        String cover = recordJsonObj.optString("cover");
                        if (cover != null && cover.length() > 0) {
                            user.cover = cover;
                        }
                        TakeMeAwayApplication.getApp().setUser(user);
                        TakeMeAwayApplication.getApp().setIsLogin(true);
                    }

                    Intent data = new Intent();
                    setResult(200, data);
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), R.string.login_fail_prompt, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 用户注册
     *
     * @param account 账号
     * @param pwd     密码
     * @param email   电子邮件
     */
    private void register(final String account, final String pwd, final String email) {
        RequestParams params = new RequestParams();
        params.put("account", account);
        params.put("pwd", pwd);
        params.put("email", email);
        TMARestClient.get(URLs.USER_REGISTER_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response == null) return;

                if (response.optInt("code") == 200) {
                    JSONObject recordJsonObj = response.optJSONObject("record");
                    if (recordJsonObj != null) {
                        User user = new User();
                        user.id = recordJsonObj.optLong("id");
                        user.username = recordJsonObj.optString("username");
                        user.email = recordJsonObj.optString("email");

                        String nickname = recordJsonObj.optString("nickname");
                        if (nickname != null && nickname.length() > 0) {
                            user.nickname = nickname;
                        }
                        String avatar = recordJsonObj.optString("avatar");
                        if (avatar != null && avatar.length() > 0) {
                            user.avatar = avatar;
                        }
                        String cover = recordJsonObj.optString("cover");
                        if (cover != null && cover.length() > 0) {
                            user.cover = cover;
                        }
                        TakeMeAwayApplication.getApp().setUser(user);
                        TakeMeAwayApplication.getApp().setIsLogin(true);
                    }
                    Intent data = new Intent();
                    setResult(200, data);
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), R.string.register_fail_prompt, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_login:
                hiddenActvEmail();
                btn_submit.setText(R.string.account_login);
                edit_login.setHint(R.string.login_hint_prompt);
                getActionBar().setTitle(R.string.account_login);
                break;
            case R.id.rb_create:
                btn_submit.setText(R.string.account_register);
                edit_login.setHint(R.string.account_username_hint);
                getActionBar().setTitle(R.string.account_register);
                showActvEmail();
                break;
            default:
                break;
        }
    }

    /**
     * 显示Email
     */
    private void showActvEmail() {
        actv_email.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏Email
     */
    private void hiddenActvEmail() {
        actv_email.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        validate();
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
