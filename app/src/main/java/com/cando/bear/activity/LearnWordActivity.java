package com.cando.bear.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.cando.bear.R;
import com.cando.bear.fragment.AddNoteFragment;
import com.cando.bear.fragment.AddNoteMeaningFragment;
import com.cando.bear.fragment.AddNoteOriginFragment;
import com.cando.bear.fragment.AddNoteSynAndAntFragment;
import com.cando.bear.fragment.EditSelectFragment;
import com.cando.bear.fragment.EditSelectMeaningFragment;
import com.cando.bear.fragment.EditSelectOriginFragment;
import com.cando.bear.fragment.EditSelectSynAndAntFragment;
import com.cando.bear.fragment.MeaningsFragment;
import com.cando.bear.fragment.MyNoteFragment;
import com.cando.bear.fragment.MyNoteMeaningFragment;
import com.cando.bear.fragment.MyNoteOriginFragment;
import com.cando.bear.fragment.MyNoteSynAndAntFragment;
import com.cando.bear.fragment.OriginFragment;
import com.cando.bear.fragment.ShareNoteFragment;
import com.cando.bear.fragment.ShareNoteMeaningFragment;
import com.cando.bear.fragment.ShareNoteOriginFragment;
import com.cando.bear.fragment.ShareNoteSynAndAntFragment;
import com.cando.bear.fragment.SynAndAntFragment;
import com.cando.bear.fragment.WordFormationFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class LearnWordActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {

    private FragmentPagerAdapter fragmentPaperAdapter;
    private ViewPager viewPager;
    private String planNum;//服务器上的学习计划
    private int downloadNum;
    private List<String> list;
    private List<AVObject> listWord;
    private List<AVObject> listNotMasterWord;
    private List<Fragment> listFragment = new ArrayList<>();
    private Intent intent;
    private Button home;
    private Dialog progressDialog;
    private int page, notMasterPage, reciteWordDays;
    private TextView word, prefixText, suffixText, rootText;
    private RelativeLayout master, notMaster, prefix, suffix, root;
    private MaterialTabHost tabHost, tabHostOrigin, tabHostMeaning, tabHostSynAndAnt;
    private ViewPager pager, pagerOrigin, pagerMeaning, pagerSynAndAnt;
//    private ViewPagerAdapter adapter;
//    private ViewPagerAdapterMeaning viewPagerAdapterMeaning;
//    private ViewPagerAdapterOrigin viewPagerAdapterOrigin;
//    private ViewPagerAdapterSynAndAnt viewPagerAdapterSynAndAnt;
    private MaterialTab materialTab;
    private LinearLayout alreadyMaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_word);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        viewPager = (ViewPager) findViewById(R.id.viewPaper);
        viewPager.setOnPageChangeListener(this);
        WordFormationFragment wordFormationFragment = new WordFormationFragment();
        OriginFragment originFragment = new OriginFragment();
        SynAndAntFragment synAndAntFragment = new SynAndAntFragment();
        MeaningsFragment meaningsFragment = new MeaningsFragment();
        listFragment.add(wordFormationFragment);
        listFragment.add(originFragment);
        listFragment.add(synAndAntFragment);
        listFragment.add(meaningsFragment);
        fragmentPaperAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return listFragment.size();
            }

            @Override
            public Fragment getItem(int position) {
                return listFragment.get(position);
            }
        };
        viewPager.setAdapter(fragmentPaperAdapter);
//        alreadyMaster = (LinearLayout) this.findViewById(R.id.alreadyMaster);
//        alreadyMaster.setOnClickListener(this);
//        tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
//        tabHost.setTag("tips");
//        tabHostOrigin = (MaterialTabHost) this.findViewById(R.id.tabHostOrigin);
//        tabHost.setTag("origin");
//        tabHostMeaning = (MaterialTabHost) this.findViewById(R.id.tabHostMeaning);
//        tabHost.setTag("meaning");
//        tabHostSynAndAnt = (MaterialTabHost) this.findViewById(R.id.tabHostSynAndAnt);
//        tabHost.setTag("synAndAnt");
//        pager = (ViewPager) this.findViewById(R.id.pager);
//        pagerOrigin = (ViewPager) this.findViewById(R.id.pagerOrigin);
//        pagerMeaning = (ViewPager) this.findViewById(R.id.pagerMeaning);
//        pagerSynAndAnt = (ViewPager) this.findViewById(R.id.pagerSynAndAnt);
        // insert all tabs from pagerAdapter data
//        tabHost.addTab(tabHost.newTab().setText("编辑精选").setTabListener(this));
//        tabHost.addTab(tabHost.newTab().setText("共享笔记").setTabListener(this));
//        tabHost.addTab(tabHost.newTab().setText("我的笔记").setTabListener(this));
//        tabHost.addTab(tabHost.newTab().setText("新增笔记").setTabListener(this));
//        tabHostOrigin.addTab(tabHostOrigin.newTab().setText("编辑精选").setTabListener(this));
//        tabHostOrigin.addTab(tabHostOrigin.newTab().setText("共享笔记").setTabListener(this));
//        tabHostOrigin.addTab(tabHostOrigin.newTab().setText("我的笔记").setTabListener(this));
//        tabHostOrigin.addTab(tabHostOrigin.newTab().setText("新增笔记").setTabListener(this));
//        tabHostMeaning.addTab(tabHostMeaning.newTab().setText("编辑精选").setTabListener(this));
//        tabHostMeaning.addTab(tabHostMeaning.newTab().setText("共享笔记").setTabListener(this));
//        tabHostMeaning.addTab(tabHostMeaning.newTab().setText("我的笔记").setTabListener(this));
//        tabHostMeaning.addTab(tabHostMeaning.newTab().setText("新增笔记").setTabListener(this));
//        tabHostSynAndAnt.addTab(tabHostSynAndAnt.newTab().setText("编辑精选").setTabListener(this));
//        tabHostSynAndAnt.addTab(tabHostSynAndAnt.newTab().setText("共享笔记").setTabListener(this));
//        tabHostSynAndAnt.addTab(tabHostSynAndAnt.newTab().setText("我的笔记").setTabListener(this));
//        tabHostSynAndAnt.addTab(tabHostSynAndAnt.newTab().setText("新增笔记").setTabListener(this));
//        master = (RelativeLayout) findViewById(R.id.master);
//        master.setOnClickListener(this);
//        notMaster = (RelativeLayout) findViewById(R.id.notMaster);
//        notMaster.setOnClickListener(this);
//        word = (TextView) findViewById(R.id.word);
//        home = (Button) findViewById(R.id.home);
//        home.setOnClickListener(this);
//        list = new ArrayList<>();
//        listWord = new ArrayList<>();
//        listNotMasterWord = new ArrayList<>();
//        prefixText = (TextView) findViewById(R.id.prefixText);
//        suffixText = (TextView) findViewById(R.id.suffixText);
//        rootText = (TextView) findViewById(R.id.rootText);
//        prefix = (RelativeLayout) findViewById(R.id.prefix);
//        prefix.setOnClickListener(this);
//        suffix = (RelativeLayout) findViewById(R.id.suffix);
//        suffix.setOnClickListener(this);
//        root = (RelativeLayout) findViewById(R.id.root);
//        root.setOnClickListener(this);
//        LearnWordActivity.this.progressDialog =
//                ProgressDialog.show(this, "", "数据加载中，请稍后...", true);
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
//                LearnWordActivity.this.progressDialog =
//                        ProgressDialog.show(this, "", "数据加载中，请稍后...", true);
//                progressDialog.setCancelable(true);
                break;
            case R.id.notMaster:
                skipWord();
                break;
            case R.id.alreadyMaster:
                masterWord();
                break;
            case R.id.prefix:
                break;
            case R.id.suffix:
                break;
            case R.id.root:
                break;
            case R.id.tabHost:
//                pager.setCurrentItem(materialTab.getPosition());
                break;
            case R.id.tabHostMeaning:
                break;
            case R.id.tabHostOrigin:
                break;
            case R.id.tabHostSynAndAnt:
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
                        planNum = avObjects.get(0).getString("wordPlan");
                        String isCompleteTodayWordPlan = avObjects.get(0).getString("isCompleteTodayWordPlan");
                        reciteWordDays = avObjects.get(0).getInt("reciteWordDays");
                        if (isCompleteTodayWordPlan.equals("1")) {
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

    public void findAVObjects() {
        //判断是否需要从Word表内同步数据
        AVQuery<AVUser> query = AVUser.getQuery();
        query.whereEqualTo("username", AVUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (list.get(0).getString("isCompleteAllWordPlan").equals("1")) {
                    AVQuery<AVObject> avQuery = new AVQuery<AVObject>("WordRecord");
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
                                AVQuery<AVObject> avQuery = new AVQuery<AVObject>("Word");
                                avQuery.whereContainedIn("objectId", listId);
                                avQuery.findInBackground(new FindCallback<AVObject>() {
                                    @Override
                                    public void done(List<AVObject> list, AVException e) {
                                        if (e == null) {
                                            for (int i = 0; i < list.size(); i++) {
                                                listWord.add(list.get(i));
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
                    AVQuery<AVObject> query = new AVQuery<>("Word");
                    downloadNum = Integer.parseInt(planNum);
                    int separate = 1000 / downloadNum;
                    if (0 < reciteWordDays && reciteWordDays <= separate) {
                        query.whereEqualTo("flag", "1");
                    }
                    if (reciteWordDays > separate && reciteWordDays <= separate * 2) {
                        query.whereEqualTo("flag", "2");
                    }
                    if (reciteWordDays > separate * 2 && reciteWordDays <= separate * 3) {
                        query.whereEqualTo("flag", "3");
                    }
                    if (reciteWordDays > separate * 3 && reciteWordDays <= separate * 4) {
                        query.whereEqualTo("flag", "4");
                    }
                    if (reciteWordDays > separate * 4) {
                        query.whereEqualTo("flag", "5");
                    }
                    // 按照更新时间降序排序
                    // query.orderByDescending("createdAt");
                    // 最大返回1000条
                    query.limit(1000);
                    query.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            if (e == null) {
                                if (list.get(0).getString("flag").equals("1")) {
                                    for (int i = reciteWordDays * downloadNum; i < reciteWordDays * downloadNum + downloadNum; i++) {
                                        listWord.add(list.get(i));
                                    }
                                }
                                if (list.get(0).getString("flag").equals("2")) {
                                    for (int i = reciteWordDays * downloadNum - 1000; i < reciteWordDays * downloadNum - 1000 + downloadNum; i++) {
                                        listWord.add(list.get(i));
                                    }
                                }
                                if (list.get(0).getString("flag").equals("3")) {
                                    for (int i = reciteWordDays * downloadNum - 2000; i < reciteWordDays * downloadNum - 2000 + downloadNum; i++) {
                                        listWord.add(list.get(i));
                                    }
                                }
                                if (list.get(0).getString("flag").equals("4")) {
                                    for (int i = reciteWordDays * downloadNum - 3000; i < reciteWordDays * downloadNum - 3000 + downloadNum; i++) {
                                        listWord.add(list.get(i));
                                    }
                                }
                                if (list.get(0).getString("flag").equals("5")) {
                                    for (int i = reciteWordDays * downloadNum - 4000; i < reciteWordDays * downloadNum - 4000 + downloadNum; i++) {
                                        if (i == 104) {
                                            AVUser avUser = AVUser.getCurrentUser();
                                            avUser.put("isCompleteAllWordPlan", "1");
                                            avUser.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(AVException e) {
                                                    Toast toast = Toast.makeText(LearnWordActivity.this, "本轮学习之后，您会将所有单词背诵完一遍哦~", Toast.LENGTH_LONG);
                                                    toast.show();
                                                }
                                            });
                                            break;
                                        }
                                        listWord.add(list.get(i));
                                    }
                                }
                                AVQuery<AVObject> avQuery = new AVQuery<AVObject>("WordRecord");
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
                                            AVQuery<AVObject> avQuery = new AVQuery<AVObject>("Word");
                                            avQuery.whereContainedIn("objectId", listId);
                                            avQuery.findInBackground(new FindCallback<AVObject>() {
                                                @Override
                                                public void done(List<AVObject> list, AVException e) {
                                                    if (e == null) {
                                                        for (int i = 0; i < list.size(); i++) {
                                                            listWord.add(list.get(i));
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
                        }
                    });
                }
            }
        });
    }

    public void setView(int page) {
        int position = page;
//        adapter = new ViewPagerAdapter(getSupportFragmentManager(), listWord.get(position).getObjectId());
//        viewPagerAdapterMeaning = new ViewPagerAdapterMeaning(getSupportFragmentManager(), listWord.get(position).getObjectId());
//        viewPagerAdapterOrigin = new ViewPagerAdapterOrigin(getSupportFragmentManager(), listWord.get(position).getObjectId());
//        viewPagerAdapterSynAndAnt = new ViewPagerAdapterSynAndAnt(getSupportFragmentManager(), listWord.get(position).getObjectId());
//        pager.setAdapter(adapter);
//        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                // when user do a swipe the selected tab change
//                tabHost.setSelectedNavigationItem(position);
//            }
//        });
//        pagerMeaning.setAdapter(viewPagerAdapterMeaning);
//        pagerMeaning.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                // when user do a swipe the selected tab change
//                tabHostMeaning.setSelectedNavigationItem(position);
//            }
//        });
//        pagerOrigin.setAdapter(viewPagerAdapterOrigin);
//        pagerOrigin.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                // when user do a swipe the selected tab change
//                tabHostOrigin.setSelectedNavigationItem(position);
//            }
//        });
//        pagerSynAndAnt.setAdapter(viewPagerAdapterSynAndAnt);
//        pagerSynAndAnt.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                // when user do a swipe the selected tab change
//                tabHostSynAndAnt.setSelectedNavigationItem(position);
//            }
//        });

        if (position == listWord.size()) {
            return;
        }
        word.setText(listWord.get(position).getString("word"));
        if (listWord.get(position).getString("prefix") != null) {
            prefix.setVisibility(View.VISIBLE);
            prefixText.setText(listWord.get(position).getString("prefix"));
        }
        if (listWord.get(position).getString("suffix") != null) {
            suffix.setVisibility(View.VISIBLE);
            suffixText.setText(listWord.get(position).getString("suffix"));
        }
        if (listWord.get(position).getString("root") != null) {
            root.setVisibility(View.VISIBLE);
            rootText.setText(listWord.get(position).getString("root"));
        }
    }

    public String[] convertStrToArray(String str) {
        String[] strArray = null;
        strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }

    public void uploadRecord() {
        final String userID = AVUser.getCurrentUser().getObjectId();
        if (page == listWord.size()) {
            return;
        }
        final String dictID = listWord.get(page).getObjectId();
        Date curDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final String Date = formatter.format(curDate);
        AVQuery<AVObject> avQuery = new AVQuery<>("WordRecord");
        avQuery.whereEqualTo("userID", userID);
        avQuery.whereEqualTo("dictID", dictID);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        AVObject post = new AVObject("WordRecord");
                        post.put("dictID", dictID);
                        post.put("userID", userID);
                        post.put("time", 1);
                        post.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                progressDialog.dismiss();
                                page = page + 1;
                                if (page == listWord.size()) {
                                    if (listNotMasterWord.size() == 0) {
                                        AVUser user = AVUser.getCurrentUser();
                                        user.put("isCompleteTodayWordPlan", "1");
                                        user.put("wordUpdateDate", Date);
                                        user.increment("reciteWordDays");
                                        user.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                AlertDialog dialog = getAlertDialogWithTaskComplete();
                                                dialog.show();
                                            }
                                        });
                                    } else {
                                        listWord = listNotMasterWord;
                                        listNotMasterWord = null;
                                        listNotMasterWord = new ArrayList<AVObject>();
                                        page = 0;
                                        notMasterPage = 0;
                                        setView(page);
                                    }
                                } else {
                                    if (page == listWord.size()) {

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
                                if (page == listWord.size()) {
                                    if (listNotMasterWord.size() == 0) {
                                        AVUser user = AVUser.getCurrentUser();
                                        user.put("isCompleteTodayWordPlan", "1");
                                        user.put("wordUpdateDate", Date);
                                        user.increment("reciteWordDays");
                                        user.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                AlertDialog dialog = getAlertDialogWithTaskComplete();
                                                dialog.show();
                                            }
                                        });
                                    } else {
                                        listWord = listNotMasterWord;
                                        listNotMasterWord = null;
                                        listNotMasterWord = new ArrayList<AVObject>();
                                        page = 0;
                                        notMasterPage = 0;
                                        setView(page);
                                    }
                                } else {
                                    if (page == listWord.size()) {

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
        builder.setMessage("恭喜，您完成了今日的单词学习任务~");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                intent = new Intent(LearnWordActivity.this, MainActivity.class);
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
        builder.setMessage("您已经完成了今日的单词学习任务哦~");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                intent = new Intent(LearnWordActivity.this, MainActivity.class);
                startActivity(intent);
                //新的activity进入动画，旧的activity退出的动画
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public void skipWord() {
        listNotMasterWord.add(notMasterPage, listWord.get(page));
        page = page + 1;
        if (page == listWord.size()) {
            listWord = listNotMasterWord;
            listNotMasterWord = null;
            listNotMasterWord = new ArrayList<AVObject>();
            page = 0;
            notMasterPage = 0;
            setView(page);
        } else {
            notMasterPage = notMasterPage + 1;
            setView(page);
        }
    }

    public void masterWord() {
        final String userID = AVUser.getCurrentUser().getObjectId();
        if (page == listWord.size()) {
            return;
        }
        final String dictID = listWord.get(page).getObjectId();
        Date curDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final String Date = formatter.format(curDate);
        AVQuery<AVObject> avQuery = new AVQuery<>("WordRecord");
        avQuery.whereEqualTo("userID", userID);
        avQuery.whereEqualTo("dictID", dictID);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        AVObject post = new AVObject("WordRecord");
                        post.put("dictID", dictID);
                        post.put("userID", userID);
                        post.put("time", 7);
                        post.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                progressDialog.dismiss();
                                page = page + 1;
                                if (page == listWord.size()) {
                                    if (listNotMasterWord.size() == 0) {
                                        AVUser user = AVUser.getCurrentUser();
                                        user.put("isCompleteTodayWordPlan", "1");
                                        user.put("wordUpdateDate", Date);
                                        user.increment("reciteWordDays");
                                        user.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                AlertDialog dialog = getAlertDialogWithTaskComplete();
                                                dialog.show();
                                            }
                                        });
                                    } else {
                                        listWord = listNotMasterWord;
                                        listNotMasterWord = null;
                                        listNotMasterWord = new ArrayList<AVObject>();
                                        page = 0;
                                        notMasterPage = 0;
                                        setView(page);
                                    }
                                } else {
                                    if (page == listWord.size()) {

                                    } else {
                                        setView(page);
                                    }
                                }
                            }
                        });
                    } else {
                        list.get(0).put("time", 7);
                        list.get(0).saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                progressDialog.dismiss();
                                page = page + 1;
                                if (page == listWord.size()) {
                                    if (listNotMasterWord.size() == 0) {
                                        AVUser user = AVUser.getCurrentUser();
                                        user.put("isCompleteTodayWordPlan", "1");
                                        user.put("wordUpdateDate", Date);
                                        user.increment("reciteWordDays");
                                        user.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                AlertDialog dialog = getAlertDialogWithTaskComplete();
                                                dialog.show();
                                            }
                                        });
                                    } else {
                                        listWord = listNotMasterWord;
                                        listNotMasterWord = null;
                                        listNotMasterWord = new ArrayList<AVObject>();
                                        page = 0;
                                        notMasterPage = 0;
                                        setView(page);
                                    }
                                } else {
                                    if (page == listWord.size()) {

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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

//    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
//
//        private String wordID;
//
//        public ViewPagerAdapter(FragmentManager fm, String wordIdParam) {
//            super(fm);
//            this.wordID = wordIdParam;
//        }
//
//        public ViewPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        public Fragment getItem(int num) {
//            Bundle bundle;
//            switch (num) {
//                case 0:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    EditSelectFragment editSelectFragment = new EditSelectFragment();
//                    editSelectFragment.setArguments(bundle);
//                    return editSelectFragment;
//                case 1:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    ShareNoteFragment shareNoteFragment = new ShareNoteFragment();
//                    shareNoteFragment.setArguments(bundle);
//                    return shareNoteFragment;
//                case 2:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    MyNoteFragment myNoteFragment = new MyNoteFragment();
//                    myNoteFragment.setArguments(bundle);
//                    return myNoteFragment;
//                case 3:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    AddNoteFragment addNoteFragment = new AddNoteFragment();
//                    addNoteFragment.setArguments(bundle);
//                    return addNoteFragment;
//                default:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    ShareNoteFragment shareNoteFragment1 = new ShareNoteFragment();
//                    shareNoteFragment1.setArguments(bundle);
//                    return shareNoteFragment1;
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return 4;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            String title = "";
//            switch (position) {
//                case 0:
//                    title = "编辑精选";
//                    break;
//                case 1:
//                    title = "共享笔记";
//                    break;
//                case 2:
//                    title = "我的笔记";
//                    break;
//                case 3:
//                    title = "新增笔记";
//                    break;
//            }
//            return title;
//        }
//    }
//
//    private class ViewPagerAdapterOrigin extends FragmentStatePagerAdapter {
//
//        private String wordID;
//
//        public ViewPagerAdapterOrigin(FragmentManager fm, String wordIdParam) {
//            super(fm);
//            this.wordID = wordIdParam;
//        }
//
//        public ViewPagerAdapterOrigin(FragmentManager fm) {
//            super(fm);
//        }
//
//        public Fragment getItem(int num) {
//            Bundle bundle;
//            switch (num) {
//                case 0:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    EditSelectOriginFragment editSelectOriginFragment = new EditSelectOriginFragment();
//                    editSelectOriginFragment.setArguments(bundle);
//                    return editSelectOriginFragment;
//                case 1:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    ShareNoteOriginFragment shareNoteOriginFragment = new ShareNoteOriginFragment();
//                    shareNoteOriginFragment.setArguments(bundle);
//                    return shareNoteOriginFragment;
//                case 2:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    MyNoteOriginFragment myNoteOriginFragment = new MyNoteOriginFragment();
//                    myNoteOriginFragment.setArguments(bundle);
//                    return myNoteOriginFragment;
//                case 3:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    AddNoteOriginFragment addNoteOriginFragment = new AddNoteOriginFragment();
//                    addNoteOriginFragment.setArguments(bundle);
//                    return addNoteOriginFragment;
//                default:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    ShareNoteOriginFragment shareNoteOriginFragment1 = new ShareNoteOriginFragment();
//                    shareNoteOriginFragment1.setArguments(bundle);
//                    return shareNoteOriginFragment1;
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return 4;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            String title = "";
//            switch (position) {
//                case 0:
//                    title = "编辑精选";
//                    break;
//                case 1:
//                    title = "共享笔记";
//                    break;
//                case 2:
//                    title = "我的笔记";
//                    break;
//                case 3:
//                    title = "新增笔记";
//                    break;
//            }
//            return title;
//        }
//    }
//
//    private class ViewPagerAdapterMeaning extends FragmentStatePagerAdapter {
//
//        private String wordID;
//
//        public ViewPagerAdapterMeaning(FragmentManager fm, String wordIdParam) {
//            super(fm);
//            this.wordID = wordIdParam;
//        }
//
//        public ViewPagerAdapterMeaning(FragmentManager fm) {
//            super(fm);
//        }
//
//        public Fragment getItem(int num) {
//            Bundle bundle;
//            switch (num) {
//                case 0:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    EditSelectMeaningFragment editSelectMeaningFragment = new EditSelectMeaningFragment();
//                    editSelectMeaningFragment.setArguments(bundle);
//                    return editSelectMeaningFragment;
//                case 1:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    ShareNoteMeaningFragment shareNoteFragment = new ShareNoteMeaningFragment();
//                    shareNoteFragment.setArguments(bundle);
//                    return shareNoteFragment;
//                case 2:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    MyNoteMeaningFragment myNoteFragment = new MyNoteMeaningFragment();
//                    myNoteFragment.setArguments(bundle);
//                    return myNoteFragment;
//                case 3:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    AddNoteMeaningFragment addNoteFragment = new AddNoteMeaningFragment();
//                    addNoteFragment.setArguments(bundle);
//                    return addNoteFragment;
//                default:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    ShareNoteMeaningFragment shareNoteFragment1 = new ShareNoteMeaningFragment();
//                    shareNoteFragment1.setArguments(bundle);
//                    return shareNoteFragment1;
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return 4;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            String title = "";
//            switch (position) {
//                case 0:
//                    title = "编辑精选";
//                    break;
//                case 1:
//                    title = "共享笔记";
//                    break;
//                case 2:
//                    title = "我的笔记";
//                    break;
//                case 3:
//                    title = "新增笔记";
//                    break;
//            }
//            return title;
//        }
//    }
//
//    private class ViewPagerAdapterSynAndAnt extends FragmentStatePagerAdapter {
//
//        private String wordID;
//
//        public ViewPagerAdapterSynAndAnt(FragmentManager fm, String wordIdParam) {
//            super(fm);
//            this.wordID = wordIdParam;
//        }
//
//        public ViewPagerAdapterSynAndAnt(FragmentManager fm) {
//            super(fm);
//        }
//
//        public Fragment getItem(int num) {
//            Bundle bundle;
//            switch (num) {
//                case 0:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    EditSelectSynAndAntFragment editSelectFragment = new EditSelectSynAndAntFragment();
//                    editSelectFragment.setArguments(bundle);
//                    return editSelectFragment;
//                case 1:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    ShareNoteSynAndAntFragment shareNoteFragment = new ShareNoteSynAndAntFragment();
//                    shareNoteFragment.setArguments(bundle);
//                    return shareNoteFragment;
//                case 2:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    MyNoteSynAndAntFragment myNoteFragment = new MyNoteSynAndAntFragment();
//                    myNoteFragment.setArguments(bundle);
//                    return myNoteFragment;
//                case 3:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    AddNoteSynAndAntFragment addNoteFragment = new AddNoteSynAndAntFragment();
//                    addNoteFragment.setArguments(bundle);
//                    return addNoteFragment;
//                default:
//                    bundle = new Bundle();
//                    bundle.putString("wordID", wordID);
//                    ShareNoteSynAndAntFragment shareNoteFragment1 = new ShareNoteSynAndAntFragment();
//                    shareNoteFragment1.setArguments(bundle);
//                    return shareNoteFragment1;
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return 4;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            String title = "";
//            switch (position) {
//                case 0:
//                    title = "编辑精选";
//                    break;
//                case 1:
//                    title = "共享笔记";
//                    break;
//                case 2:
//                    title = "我的笔记";
//                    break;
//                case 3:
//                    title = "新增笔记";
//                    break;
//            }
//            return title;
//        }
//    }

}
