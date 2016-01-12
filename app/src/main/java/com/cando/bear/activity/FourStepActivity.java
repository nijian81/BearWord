package com.cando.bear.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cando.bear.R;

public class FourStepActivity extends Activity implements View.OnClickListener {

    private WebView fourStep = null;
    private ImageView back;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.four_step);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        fourStep = (WebView) findViewById(R.id.fourStep);
        fourStep.setWebViewClient(new WebViewClient());
        WebSettings webSettings = fourStep.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        String url = "http://www.shanbay.com/track/x/dkm9y5y/";
        fourStep.loadUrl(url);
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
        }
    }
}
