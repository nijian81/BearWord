package com.cando.bear.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.cando.bear.R;


public class ModifyHoneyNameActivity extends Activity implements View.OnClickListener {

    private ImageButton back;
    private Intent intent;
    private RelativeLayout affirmModify;
    private EditText honeyName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.modify_honey_name);

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);
        honeyName = (EditText) findViewById(R.id.honeyName);
        honeyName.addTextChangedListener(new watcher());
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
                AVUser avUser = AVUser.getCurrentUser();
                avUser.put("honeyName", honeyName.getText().toString());
                avUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            intent = new Intent();
                            intent.setAction("android.intent.action.AccountSetting");
                            startActivity(intent);
                            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                            Toast.makeText(ModifyHoneyNameActivity.this, "修改成功了哦~", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ModifyHoneyNameActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    public class watcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (honeyName.length() != 0) {
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
