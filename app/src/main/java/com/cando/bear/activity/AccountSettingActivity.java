package com.cando.bear.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.cando.bear.R;
import com.cando.bear.utils.StatusNetAsyncTask;
import com.cando.bear.utils.StatusUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.ByteArrayOutputStream;

public class AccountSettingActivity extends Activity implements View.OnClickListener {

    private Spinner languageSpinner;
    private ImageView back,portrait_view;
    private Intent intent,dataIntent;
    private RelativeLayout modifyPassword,modifyMailbox,modifyHoneyName,modifyCellphone,portrait;
    //表示选择图片
    private static final int IMAGE_PICK_REQUEST = 0;
    //表示裁剪图片
    private static final int CROP_REQUEST = 1;
    private Dialog progressDialog;
    private String picturePath;

    @Override
    protected void onResume() {
        super.onResume();
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser.getAVFile("portrait") != null) {
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
            ImageLoader.getInstance().displayImage(currentUser.getAVFile("portrait").getUrl(), portrait_view, StatusUtils.normalImageOptions);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.account_setting);

        languageSpinner = (Spinner)findViewById(R.id.languageSpinner);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (((TextView)view).getText().toString()){
                    case "简体中文":
                        AVUser avUser = AVUser.getCurrentUser();
                        avUser.put("language","zh");
                        avUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if ((e == null)){
                                    Toast.makeText(AccountSettingActivity.this,"语言更新成功！",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                    case "英文":
                        AVUser avUser1 = AVUser.getCurrentUser();
                        avUser1.put("language","en");
                        avUser1.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if ((e == null)){
                                    Toast.makeText(AccountSettingActivity.this,"语言更新成功！",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                }
            }

            @Override
               public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        portrait_view = (ImageView)findViewById(R.id.portrait_view);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        modifyPassword = (RelativeLayout) findViewById(R.id.modifyPassword);
        modifyPassword.setOnClickListener(this);
        modifyMailbox = (RelativeLayout) findViewById(R.id.modifyMailbox);
        modifyMailbox.setOnClickListener(this);
        modifyHoneyName = (RelativeLayout) findViewById(R.id.modifyHoneyName);
        modifyHoneyName.setOnClickListener(this);
        modifyCellphone = (RelativeLayout) findViewById(R.id.modifyCellphone);
        modifyCellphone.setOnClickListener(this);
        portrait = (RelativeLayout) findViewById(R.id.portrait);
        portrait.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                intent = new Intent();
                intent.setAction("android.intent.action.Setting");
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                break;
            case R.id.modifyPassword:
                intent = new Intent();
                intent.setAction("android.intent.action.ModifyPassword");
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.modifyMailbox:
                intent = new Intent();
                intent.setAction("android.intent.action.ModifyMailbox");
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.modifyHoneyName:
                intent = new Intent();
                intent.setAction("android.intent.action.ModifyHoneyName");
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.modifyCellphone:
                intent = new Intent();
                intent.setAction("android.intent.action.ModifyCellphone");
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.portrait:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(i, IMAGE_PICK_REQUEST);
//                新的activity进入动画，旧的activity退出的动画
//                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_PICK_REQUEST) {
                dataIntent = data;
                Uri uri = data.getData();
                StatusUtils.startAvatarCrop(AccountSettingActivity.this, uri, 200, 200, CROP_REQUEST, getCachePath());
            } else if (requestCode == CROP_REQUEST) {
                final Bitmap bitmap = data.getExtras().getParcelable("data");
                progressDialog = ProgressDialog.show(this, "", "头像上传中，请稍后...", true);
                progressDialog.setCancelable(false);
                new StatusNetAsyncTask(this) {
                    @Override
                    protected void doInBack() throws Exception {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                        byte[] bytes = stream.toByteArray();
                        final AVFile file = new AVFile("photo", bytes);
                        AVUser currentUser = AVUser.getCurrentUser();
                        currentUser.put("portrait", file);
                        currentUser.saveInBackground(new SaveCallback() {
                            public void done(AVException e) {
                                if (e == null) {
                                    progressDialog.dismiss();
                                    Toast toast = Toast.makeText(AccountSettingActivity.this, "上传成功", Toast.LENGTH_LONG);
                                    toast.show();
                                } else {
                                    // 保存失败，输出错误信息
                                    Toast toast = Toast.makeText(AccountSettingActivity.this, "上传失败", Toast.LENGTH_LONG);
                                    toast.show();
                                    Log.e("aaa", e + "");
                                }
                            }
                        });
                    }

                    @Override
                    protected void onPost(Exception e) {
                        if (StatusUtils.filterException(AccountSettingActivity.this, e)) {
                            Uri selectedImage = dataIntent.getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            picturePath = cursor.getString(columnIndex);
                            cursor.close();
                            portrait_view.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                        }
                    }
                }.execute();
            }
        }
    }

    public String getCachePath() {
        return getCacheDir() + "tmp";
    }


}
