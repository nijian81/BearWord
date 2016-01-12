package com.cando.bear.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.cando.bear.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LearnPrefixActivity extends Activity implements View.OnClickListener {

    private String planNum;//服务器上的学习计划
    private int downloadNum;
    private List<String> list;
    private List<AVObject> listPrefix;
    private List<AVObject> listNotMasterPrefix;
    private ListView listViewOne, listViewTwo, listViewThree, listViewFour;
    private MyAdapter myAdapterOne, myAdapterTwo, myAdapterThree, myAdapterFour;
    private Intent intent;
    private Button home;
    private LinearLayout partOne, partTwo, partThree, partFour, alreadyMaster;
    private Dialog progressDialog;
    private int page, notMasterPage, recitePrefixDays;
    private TextView prefix, meanOne, exampleTextOne, meanTwo, exampleTextTwo, meanThree, exampleTextThree, meanFour, exampleTextFour;
    private RelativeLayout master, notMaster;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.learn_prefix);

        alreadyMaster = (LinearLayout) findViewById(R.id.alreadyMaster);
        alreadyMaster.setOnClickListener(this);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        master = (RelativeLayout) findViewById(R.id.master);
        master.setOnClickListener(this);
        notMaster = (RelativeLayout) findViewById(R.id.notMaster);
        notMaster.setOnClickListener(this);
        prefix = (TextView) findViewById(R.id.prefix);
        meanOne = (TextView) findViewById(R.id.meanOne);
        exampleTextOne = (TextView) findViewById(R.id.exampleTextOne);
        meanTwo = (TextView) findViewById(R.id.meanTwo);
        exampleTextTwo = (TextView) findViewById(R.id.exampleTextTwo);
        meanThree = (TextView) findViewById(R.id.meanThree);
        exampleTextThree = (TextView) findViewById(R.id.exampleTextThree);
        meanFour = (TextView) findViewById(R.id.meanFour);
        exampleTextFour = (TextView) findViewById(R.id.exampleTextFour);
        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(this);
        list = new ArrayList<>();
        listPrefix = new ArrayList<>();
        listNotMasterPrefix = new ArrayList<>();
        myAdapterOne = new MyAdapter();
        myAdapterTwo = new MyAdapter();
        myAdapterThree = new MyAdapter();
        myAdapterFour = new MyAdapter();
        partOne = (LinearLayout) findViewById(R.id.partOne);
        partTwo = (LinearLayout) findViewById(R.id.partTwo);
        partThree = (LinearLayout) findViewById(R.id.partThree);
        partFour = (LinearLayout) findViewById(R.id.partFour);
        partOne.setVisibility(View.GONE);
        partTwo.setVisibility(View.GONE);
        partThree.setVisibility(View.GONE);
        partFour.setVisibility(View.GONE);
        listViewOne = (ListView) findViewById(R.id.relativeWordOne);
        listViewTwo = (ListView) findViewById(R.id.relativeWordTwo);
        listViewThree = (ListView) findViewById(R.id.relativeWordThree);
        listViewFour = (ListView) findViewById(R.id.relativeWordFour);
        myAdapterOne.setList(list);
        listViewOne.setAdapter(myAdapterOne);
        listViewOne.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        myAdapterTwo.setList(list);
        listViewTwo.setAdapter(myAdapterTwo);
        listViewTwo.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        myAdapterThree.setList(list);
        listViewThree.setAdapter(myAdapterThree);
        listViewThree.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        myAdapterFour.setList(list);
        listViewFour.setAdapter(myAdapterFour);
        listViewFour.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        LearnPrefixActivity.this.progressDialog =
                ProgressDialog.show(this, "", "数据加载中，请稍后...", true);
//        progressDialog.setCancelable(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
                break;
            case R.id.master:
                uploadRecord();
                LearnPrefixActivity.this.progressDialog =
                        ProgressDialog.show(this, "", "数据加载中，请稍后...", true);
//                progressDialog.setCancelable(true);
                break;
            case R.id.notMaster:
                skipPrefix();
                break;
            case R.id.alreadyMaster:
                masterPrefix();
                break;
        }
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
                        planNum = avObjects.get(0).getString("otherPlan");
                        String isCompleteTodayPrefixPlan = avObjects.get(0).getString("isCompleteTodayPrefixPlan");
                        recitePrefixDays = avObjects.get(0).getInt("recitePrefixDays");
                        if (isCompleteTodayPrefixPlan.equals("1")) {
                            AlertDialog dialog = getAlertDialogWithTodayTaskCompleted();
                            dialog.show();
                        } else {
                            findAVObjects();
                        }
                    } else {
                        Log.d("失败", "查询错误: " + e.getMessage());
                    }
                }
            });
        }
    }

    public class MyAdapter extends BaseAdapter {

        List<String> list;
        private Context context;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int index) {
            return list.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int position, View view, ViewGroup arg2) {

            view = LayoutInflater.from(LearnPrefixActivity.this).inflate(R.layout.word_item, null);

            TextView word = (TextView) view.findViewById(R.id.word);
            word.setText(list.get(position));

            return view;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }

    public void findAVObjects() {
        //判断是否需要从Root表内同步数据
        AVQuery<AVUser> query = AVUser.getQuery();
        query.whereEqualTo("username", AVUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (list.get(0).getString("isCompleteAllPrefixPlan").equals("1")) {
                    AVQuery<AVObject> avQuery = new AVQuery<AVObject>("PrefixRecord");
                    avQuery.whereEqualTo("userID", AVUser.getCurrentUser().getObjectId());
                    avQuery.whereLessThan("time", 7);
                    avQuery.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            if (e == null) {
                                List<String> listId = new ArrayList<String>();
                                for (int i = 0; i < list.size(); i++) {
                                    long gap = System.currentTimeMillis() - list.get(i).getUpdatedAt().getTime();
                                    long days = gap / 86400000;
                                    switch (list.get(i).getInt("time")) {
                                        case 1:
                                            if (days >= 1) {
                                                listId.add(list.get(i).getString("dictID"));
                                            }
                                            break;
                                        case 2:
                                            if (days >= 2) {
                                                listId.add(list.get(i).getString("dictID"));
                                            }
                                            break;
                                        case 3:
                                            if (days >= 3) {
                                                listId.add(list.get(i).getString("dictID"));
                                            }
                                            break;
                                        case 4:
                                            if (days >= 8) {
                                                listId.add(list.get(i).getString("dictID"));
                                            }
                                            break;
                                        case 5:
                                            if (days >= 5) {
                                                listId.add(list.get(i).getString("dictID"));
                                            }
                                            break;
                                        case 6:
                                            if (days >= 10) {
                                                listId.add(list.get(i).getString("dictID"));
                                            }
                                            break;
                                    }
                                }
                                AVQuery<AVObject> avQuery = new AVQuery<AVObject>("Prefix");
                                avQuery.whereContainedIn("objectId", listId);
                                avQuery.findInBackground(new FindCallback<AVObject>() {
                                    @Override
                                    public void done(List<AVObject> list, AVException e) {
                                        if (e == null) {
                                            for (int i = 0; i < list.size(); i++) {
                                                listPrefix.add(list.get(i));
                                            }
                                            setView(page);
                                            progressDialog.dismiss();
                                        } else {

                                        }
                                    }
                                });
                            } else {

                            }
                        }
                    });
                } else {
                    // 查询当前AVObject列表
                    AVQuery<AVObject> query = new AVQuery<>("Prefix");
                    // 按照更新时间降序排序
                    // query.orderByDescending("createdAt");
                    // 最大返回1000条
                    downloadNum = Integer.parseInt(planNum);
                    query.limit(1000);
                    query.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            for (int i = recitePrefixDays * downloadNum; i < recitePrefixDays * downloadNum + downloadNum; i++) {
                                if (i == 199) {
                                    AVUser avUser = AVUser.getCurrentUser();
                                    avUser.put("isCompleteAllPrefixPlan", "1");
                                    avUser.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            Toast toast = Toast.makeText(LearnPrefixActivity.this, "本轮学习之后，您会将所有前缀背诵完一遍哦~", Toast.LENGTH_LONG);
                                            toast.show();
                                        }
                                    });
                                    break;
                                }
                                listPrefix.add(list.get(i));
                            }
                            AVQuery<AVObject> avQuery = new AVQuery<AVObject>("PrefixRecord");
                            avQuery.whereEqualTo("userID", AVUser.getCurrentUser().getObjectId());
                            avQuery.whereLessThan("time", 7);
                            avQuery.findInBackground(new FindCallback<AVObject>() {
                                @Override
                                public void done(List<AVObject> list, AVException e) {
                                    if (e == null) {
                                        List<String> listId = new ArrayList<String>();
                                        for (int i = 0; i < list.size(); i++) {
                                            long gap = System.currentTimeMillis() - list.get(i).getUpdatedAt().getTime();
                                            long days = gap / 86400000;
                                            switch (list.get(i).getInt("time")) {
                                                case 1:
                                                    if (days >= 1) {
                                                        listId.add(list.get(i).getString("dictID"));
                                                    }
                                                    break;
                                                case 2:
                                                    if (days >= 2) {
                                                        listId.add(list.get(i).getString("dictID"));
                                                    }
                                                    break;
                                                case 3:
                                                    if (days >= 3) {
                                                        listId.add(list.get(i).getString("dictID"));
                                                    }
                                                    break;
                                                case 4:
                                                    if (days >= 8) {
                                                        listId.add(list.get(i).getString("dictID"));
                                                    }
                                                    break;
                                                case 5:
                                                    if (days >= 5) {
                                                        listId.add(list.get(i).getString("dictID"));
                                                    }
                                                    break;
                                                case 6:
                                                    if (days >= 10) {
                                                        listId.add(list.get(i).getString("dictID"));
                                                    }
                                                    break;
                                            }
                                        }
                                        AVQuery<AVObject> avQuery = new AVQuery<AVObject>("Prefix");
                                        avQuery.whereContainedIn("objectId", listId);
                                        avQuery.findInBackground(new FindCallback<AVObject>() {
                                            @Override
                                            public void done(List<AVObject> list, AVException e) {
                                                if (e == null) {
                                                    for (int i = 0; i < list.size(); i++) {
                                                        listPrefix.add(list.get(i));
                                                    }
                                                    setView(page);
                                                    progressDialog.dismiss();
                                                } else {

                                                }
                                            }
                                        });
                                    } else {

                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    public void setView(int page) {
        int position = page;
        if (position == listPrefix.size()) {
            return;
        }
        prefix.setText(listPrefix.get(position).getString("prefix"));
        if (listPrefix.get(position).getString("meaningOne") != null) {
            partOne.setVisibility(View.VISIBLE);
            meanOne.setText(listPrefix.get(position).getString("meaningOne"));
            exampleTextOne.setText(listPrefix.get(position).getString("meaningOne"));
            String[] relativeWordList = null;
            relativeWordList = listPrefix.get(position).getString("relativeOne").split(",");
            int size = relativeWordList.length;
            list = null;
            list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                list.add(i, relativeWordList[i]);
            }
            myAdapterOne.setList(list);
            myAdapterOne.notifyDataSetChanged();
        }
        if (listPrefix.get(position).getString("meaningTwo") != null) {
            partTwo.setVisibility(View.VISIBLE);
            meanTwo.setText(listPrefix.get(position).getString("meaningTwo"));
            exampleTextTwo.setText(listPrefix.get(position).getString("meaningTwo"));
            String[] relativeWordList = null;
            relativeWordList = listPrefix.get(position).getString("relativeTwo").split(",");
            int size = relativeWordList.length;
            list = null;
            list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                list.add(i, relativeWordList[i]);
            }
            myAdapterTwo.setList(list);
            myAdapterTwo.notifyDataSetChanged();
        }
        if (listPrefix.get(position).getString("meaningThree") != null) {
            partThree.setVisibility(View.VISIBLE);
            meanThree.setText(listPrefix.get(position).getString("meaningThree"));
            exampleTextThree.setText(listPrefix.get(position).getString("meaningThree"));
            String[] relativeWordList = null;
            relativeWordList = listPrefix.get(position).getString("relativeThree").split(",");
            int size = relativeWordList.length;
            list = null;
            list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                list.add(i, relativeWordList[i]);
            }
            myAdapterThree.setList(list);
            myAdapterThree.notifyDataSetChanged();
        }
        if (listPrefix.get(position).getString("meaningFour") != null) {
            partFour.setVisibility(View.VISIBLE);
            meanFour.setText(listPrefix.get(position).getString("meaningFour"));
            exampleTextFour.setText(listPrefix.get(position).getString("meaningFour"));
            String[] relativeWordList = null;
            relativeWordList = listPrefix.get(position).getString("relativeFour").split(",");
            int size = relativeWordList.length;
            list = null;
            list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                list.add(i, relativeWordList[i]);
            }
            myAdapterFour.setList(list);
            myAdapterFour.notifyDataSetChanged();
        }
        //让当前的scrollView滚动至屏幕顶部
        scrollView.fullScroll(View.FOCUS_UP);//if you move at the end of the scroll
    }

    public String[] convertStrToArray(String str) {
        String[] strArray = null;
        strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }

    public void uploadRecord() {
        final String userID = AVUser.getCurrentUser().getObjectId();
        if (page == listPrefix.size()) {
            return;
        }
        final String dictID = listPrefix.get(page).getObjectId();
        Date curDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final String Date = formatter.format(curDate);
        AVQuery<AVObject> avQuery = new AVQuery<>("PrefixRecord");
        avQuery.whereEqualTo("userID", userID);
        avQuery.whereEqualTo("dictID", dictID);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        AVObject post = new AVObject("PrefixRecord");
                        post.put("dictID", dictID);
                        post.put("userID", userID);
                        post.put("time", 1);
                        post.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                progressDialog.dismiss();
                                page = page + 1;
                                if (page == listPrefix.size()) {
                                    if (listNotMasterPrefix.size() == 0) {
                                        AVUser user = AVUser.getCurrentUser();
                                        user.put("isCompleteTodayPrefixPlan", "1");
                                        user.put("prefixUpdateDate", Date);
                                        user.increment("recitePrefixDays");
                                        user.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                AlertDialog dialog = getAlertDialogWithTaskComplete();
                                                dialog.show();
                                            }
                                        });
                                    } else {
                                        listPrefix = listNotMasterPrefix;
                                        listNotMasterPrefix = null;
                                        listNotMasterPrefix = new ArrayList<AVObject>();
                                        page = 0;
                                        notMasterPage = 0;
                                        setView(page);
                                    }
                                } else {
                                    if (page == listPrefix.size()) {

                                    } else {
                                        setView(page);
                                    }
                                }
                            }
                        });
                    } else {
                        list.get(0).increment("time");
                        list.get(0).saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                progressDialog.dismiss();
                                page = page + 1;
                                if (page == listPrefix.size()) {
                                    if (listNotMasterPrefix.size() == 0) {
                                        AVUser user = AVUser.getCurrentUser();
                                        user.put("isCompleteTodayPrefixPlan", "1");
                                        user.put("prefixUpdateDate", Date);
                                        user.increment("recitePrefixDays");
                                        user.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                AlertDialog dialog = getAlertDialogWithTaskComplete();
                                                dialog.show();
                                            }
                                        });
                                    } else {
                                        listPrefix = listNotMasterPrefix;
                                        listNotMasterPrefix = null;
                                        listNotMasterPrefix = new ArrayList<AVObject>();
                                        page = 0;
                                        notMasterPage = 0;
                                        setView(page);
                                    }
                                } else {
                                    if (page == listPrefix.size()) {

                                    } else {
                                        setView(page);
                                    }
                                }
                            }
                        });
                    }
                } else {

                }
            }
        });
    }

    AlertDialog getAlertDialogWithTaskComplete() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("提示");
        builder.setMessage("恭喜，您完成了今日的前缀学习任务~");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                intent = new Intent(LearnPrefixActivity.this, MainActivity.class);
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    AlertDialog getAlertDialogWithTodayTaskCompleted() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("提示");
        builder.setMessage("您已经完成了今日的前缀学习任务哦~");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                intent = new Intent(LearnPrefixActivity.this, MainActivity.class);
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public void skipPrefix() {
        listNotMasterPrefix.add(notMasterPage, listPrefix.get(page));
        page = page + 1;
        if (page == listPrefix.size()) {
            listPrefix = listNotMasterPrefix;
            listNotMasterPrefix = null;
            listNotMasterPrefix = new ArrayList<AVObject>();
            page = 0;
            notMasterPage = 0;
            setView(page);
        } else {
            notMasterPage = notMasterPage + 1;
            setView(page);
        }
    }

    public void masterPrefix() {
        final String userID = AVUser.getCurrentUser().getObjectId();
        if (page == listPrefix.size()) {
            return;
        }
        final String dictID = listPrefix.get(page).getObjectId();
        Date curDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final String Date = formatter.format(curDate);
        AVQuery<AVObject> avQuery = new AVQuery<>("PrefixRecord");
        avQuery.whereEqualTo("userID", userID);
        avQuery.whereEqualTo("dictID", dictID);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        AVObject post = new AVObject("PrefixRecord");
                        post.put("dictID", dictID);
                        post.put("userID", userID);
                        post.put("time", 7);
                        post.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                progressDialog.dismiss();
                                page = page + 1;
                                if (page == listPrefix.size()) {
                                    if (listNotMasterPrefix.size() == 0) {
                                        AVUser user = AVUser.getCurrentUser();
                                        user.put("isCompleteTodayPrefixPlan", "1");
                                        user.put("prefixUpdateDate", Date);
                                        user.increment("recitePrefixDays");
                                        user.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                AlertDialog dialog = getAlertDialogWithTaskComplete();
                                                dialog.show();
                                            }
                                        });
                                    } else {
                                        listPrefix = listNotMasterPrefix;
                                        listNotMasterPrefix = null;
                                        listNotMasterPrefix = new ArrayList<AVObject>();
                                        page = 0;
                                        notMasterPage = 0;
                                        setView(page);
                                    }
                                } else {
                                    if (page == listPrefix.size()) {

                                    } else {
                                        setView(page);
                                    }
                                }
                            }
                        });
                    } else {
                        list.get(0).put("time",7);
                        list.get(0).saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                progressDialog.dismiss();
                                page = page + 1;
                                if (page == listPrefix.size()) {
                                    if (listNotMasterPrefix.size() == 0) {
                                        AVUser user = AVUser.getCurrentUser();
                                        user.put("isCompleteTodayPrefixPlan", "1");
                                        user.put("prefixUpdateDate", Date);
                                        user.increment("recitePrefixDays");
                                        user.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                AlertDialog dialog = getAlertDialogWithTaskComplete();
                                                dialog.show();
                                            }
                                        });
                                    } else {
                                        listPrefix = listNotMasterPrefix;
                                        listNotMasterPrefix = null;
                                        listNotMasterPrefix = new ArrayList<AVObject>();
                                        page = 0;
                                        notMasterPage = 0;
                                        setView(page);
                                    }
                                } else {
                                    if (page == listPrefix.size()) {

                                    } else {
                                        setView(page);
                                    }
                                }
                            }
                        });
                    }
                } else {

                }
            }
        });
    }
}
