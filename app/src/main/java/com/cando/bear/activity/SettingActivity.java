package com.cando.bear.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.cando.bear.R;

import java.util.List;
import java.util.Set;

public class SettingActivity extends Activity implements View.OnClickListener {

    private Intent intent;
    private ImageView back;
    private RelativeLayout accountSetting, learnNumSetting, informSetting, newbieTeaching, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        accountSetting = (RelativeLayout) findViewById(R.id.accountSetting);
        accountSetting.setOnClickListener(this);
        learnNumSetting = (RelativeLayout) findViewById(R.id.learnNumSetting);
        learnNumSetting.setOnClickListener(this);
        informSetting = (RelativeLayout) findViewById(R.id.informSetting);
        informSetting.setOnClickListener(this);
        newbieTeaching = (RelativeLayout) findViewById(R.id.newbieTeaching);
        newbieTeaching.setOnClickListener(this);
        exit = (RelativeLayout) findViewById(R.id.exit);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                break;
            case R.id.accountSetting:
                intent = new Intent();
                intent.setAction("android.intent.action.AccountSetting");
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.learnNumSetting:
                intent = new Intent();
                intent.setAction("android.intent.action.LearnNumSetting");
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.informSetting:
                intent = new Intent();
                intent.setAction("android.intent.action.InformSetting");
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.newbieTeaching:
                break;
            case R.id.exit:
                AVUser.logOut();
                intent = new Intent();
                intent.setAction("android.intent.action.Initial");
                startActivity(intent);
                break;
        }
    }

}
