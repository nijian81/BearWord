package com.cando.bear.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.cando.bear.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements View.OnClickListener {

    private Intent intent;
    private Button find;
    private Button fourStep;
    private RelativeLayout word, root, prefix, suffix, dashboard, setting, calendar;
    private TextView rootsTodayNum, prefixTodayNum, suffixTodayNum, cardDays,wordTodayNum,learnWordNum,masterWordNum ;
    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //leancloud
        AVOSCloud.initialize(this, "z99pDop2DRQnREIsfbwhNRhj", "Liu2TSxqjRSPWCNzqfWqg3wG");
        AVOSCloud.setDebugLogEnabled(true);
        AVAnalytics.trackAppOpened(getIntent());
        AVAnalytics.enableCrashReport(this, true);
        AVOSCloud.setLastModifyEnabled(true);
        AVUser currentUser = AVUser.getCurrentUser();
        //判断是否有用户登录
        if (currentUser != null) {
            // 允许用户使用应用
        } else {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.Initial");
            startActivity(intent);
            finish();
            return;
        }
        Resources res = this.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        if(currentUser.getString("language").toString().equals("zh")){
            conf.locale = new Locale("zh");
        }
        else{
            conf.locale = new Locale("en");
        }
        res.updateConfiguration(conf, dm);

        setContentView(R.layout.activity_main);


        find = (Button) findViewById(R.id.find);
        find.setOnClickListener(this);
        fourStep = (Button) findViewById(R.id.fourStep);
        fourStep.setOnClickListener(this);
        word = (RelativeLayout) findViewById(R.id.word);
        word.setOnClickListener(this);
        root = (RelativeLayout) findViewById(R.id.root);
        root.setOnClickListener(this);
        prefix = (RelativeLayout) findViewById(R.id.prefix);
        prefix.setOnClickListener(this);
        suffix = (RelativeLayout) findViewById(R.id.suffix);
        suffix.setOnClickListener(this);
        dashboard = (RelativeLayout) findViewById(R.id.dashboard);
        dashboard.setOnClickListener(this);
        setting = (RelativeLayout) findViewById(R.id.setting);
        setting.setOnClickListener(this);
        calendar = (RelativeLayout) findViewById(R.id.calendar);
        calendar.setOnClickListener(this);
        rootsTodayNum = (TextView) findViewById(R.id.rootsTodayNum);
        prefixTodayNum = (TextView) findViewById(R.id.prefixTodayNum);
        suffixTodayNum = (TextView) findViewById(R.id.suffixTodayNum);
        cardDays = (TextView) findViewById(R.id.cardDays);
        wordTodayNum = (TextView) findViewById(R.id.wordTodayNum);
        learnWordNum = (TextView) findViewById(R.id.learnWordNum);
        masterWordNum = (TextView) findViewById(R.id.masterWordNum);
        MainActivity.this.progressDialog =
                ProgressDialog.show(this, "", "数据加载中，请稍后...", true);
        progressDialog.setCancelable(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {
            // 允许用户使用应用
            AVQuery<AVUser> query = AVUser.getQuery();
            query.whereEqualTo("username", AVUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<AVUser>() {
                public void done(List<AVUser> avObjects, AVException e) {
                    if (e == null) {
                        rootsTodayNum.setText(avObjects.get(0).get("otherPlan") + "");
                        prefixTodayNum.setText(avObjects.get(0).get("otherPlan") + "");
                        suffixTodayNum.setText(avObjects.get(0).get("otherPlan") + "");
                        wordTodayNum.setText(avObjects.get(0).get("wordPlan")+"");
                        cardDays.setText(avObjects.get(0).get("cardDays") + "");
                        learnWordNum.setText("0");
                        masterWordNum.setText("0");
                        Date curDate = new Date(System.currentTimeMillis());
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String date = formatter.format(curDate);
                        if (date.equals(avObjects.get(0).getString("rootUpdateDate"))) {

                        } else {
                            avObjects.get(0).put("isCompleteTodayRootPlan", "0");
                        }
                        if (date.equals(avObjects.get(0).getString("prefixUpdateDate"))) {

                        } else {
                            avObjects.get(0).put("isCompleteTodayPrefixPlan", "0");
                        }
                        if (date.equals(avObjects.get(0).getString("suffixUpdateDate"))) {

                        } else {
                            avObjects.get(0).put("isCompleteTodaySuffixPlan", "0");
                        }
                        if (date.equals(avObjects.get(0).getString("wordUpdateDate"))) {

                        } else {
                            avObjects.get(0).put("isCompleteTodayWordPlan", "0");
                        }
                        avObjects.get(0).saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    progressDialog.dismiss();
                                } else {

                                }
                            }
                        });
                    } else {
                        Log.d("失败", "查询错误: " + e.getMessage());
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fourStep:
                intent = new Intent();
                intent.setAction("android.intent.action.FourStep");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.find:
                intent = new Intent();
                intent.setAction("android.intent.action.FindWord");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.word:
                intent = new Intent();
                intent.setAction("android.intent.action.LearnWord");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.root:
                intent = new Intent();
                intent.setAction("android.intent.action.LearnRoot");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.prefix:
                intent = new Intent();
                intent.setAction("android.intent.action.LearnPrefix");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.suffix:
                intent = new Intent();
                intent.setAction("android.intent.action.LearnSuffix");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.dashboard:
                intent = new Intent();
                intent.setAction("android.intent.action.Dashboard");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.setting:
                finish();
                intent = new Intent();
                intent.setAction("android.intent.action.Setting");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.calendar:
                intent = new Intent();
                intent.setAction("android.intent.action.Calendar");
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }

}
