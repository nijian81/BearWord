package com.cando.bear.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.cando.bear.R;
import com.cando.bear.activity.MainActivity;


/**
 * Created by nijian on 2015/6/1.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

    private ImageButton back;
    private Intent intent;
    private RelativeLayout complete;
    private EditText password, username, passwordAffirm;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);
        complete = (RelativeLayout) findViewById(R.id.complete);
        complete.setOnClickListener(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        passwordAffirm = (EditText) findViewById(R.id.passwordAffirm);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.complete:
                progressDialog = ProgressDialog.show(this, "", "数据加载中，请稍后...", true);
                AVUser user = new AVUser();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    public void done(AVException e) {
                        if (e == null) {
                            progressDialog.dismiss();
                            intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toast toast = Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });
                break;
            case R.id.back:
                intent = new Intent();
                intent.setAction("android.intent.action.Initial");
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                break;
        }
    }
}
