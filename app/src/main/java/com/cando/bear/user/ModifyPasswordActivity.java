package com.cando.bear.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.cando.bear.R;


public class ModifyPasswordActivity extends Activity implements View.OnClickListener {

    private ImageButton back;
    private Intent intent;
    private RelativeLayout affirmModify;
    private EditText currentPassword, newPassword, affirmNewPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.modify_password);

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);
        currentPassword = (EditText) findViewById(R.id.currentPassword);
        currentPassword.addTextChangedListener(new watcher());
        newPassword = (EditText) findViewById(R.id.newPassword);
        newPassword.addTextChangedListener(new watcher());
        affirmNewPassword = (EditText) findViewById(R.id.affirmNewPassword);
        affirmNewPassword.addTextChangedListener(new watcher());
        affirmModify = (RelativeLayout) findViewById(R.id.affirmModify);
        affirmModify.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                intent = new Intent();
                intent.setAction("android.intent.action.AccountSetting");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                break;
            case R.id.affirmModify:
                AVUser userA = AVUser.getCurrentUser();//请确保用户当前的有效登录状态
                if (newPassword.getText().toString().equals(affirmNewPassword.getText().toString())) {
                    userA.updatePasswordInBackground(currentPassword.getText().toString(), newPassword.getText().toString(), new UpdatePasswordCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Toast.makeText(ModifyPasswordActivity.this, "修改密码成功了哦~", Toast.LENGTH_SHORT).show();
                                intent = new Intent();
                                intent.setAction("android.intent.action.AccountSetting");
                                startActivity(intent);
                                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, "两次输入的密码不一致，请重新输入！", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    public class watcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (currentPassword.length() != 0 && newPassword.length() != 0 && affirmNewPassword.length() != 0) {
                affirmModify.setBackgroundResource(R.drawable.corners_bg_pass_xz);
            } else {
                affirmModify.setBackgroundResource(R.drawable.corners_bg_pass_wxz);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

}
