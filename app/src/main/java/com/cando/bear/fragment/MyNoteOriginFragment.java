package com.cando.bear.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.cando.bear.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by neokree on 16/12/14.
 */
public class MyNoteOriginFragment extends Fragment implements AbsListView.OnScrollListener, View.OnClickListener {

    private List<AVObject> list;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyAdapter myAdapter;
    private int preLast;
    private static int showNum;
    private String wordID, itemID, itemNote;
    private PopDialog popDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        new RemoteDataTask().execute();
        myAdapter = new MyAdapter();
        list = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.my_note, container, false);

        // 设置初期数据
        list = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showNum = 0;
                new RemoteDataTask().execute();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        // 设置ListView
        listView = (ListView) rootView.findViewById(R.id.listView);
        myAdapter.setArrayList(list);
        listView.setAdapter(myAdapter);
        listView.setOnScrollListener(this);
        wordID = getArguments().getString("wordID");

        return rootView;
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
        showNum = 0;
        new RemoteDataTask().execute();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    public class MyAdapter extends BaseAdapter {

        List<AVObject> list;
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
        public View getView(final int index, View view, ViewGroup arg2) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.my_note_item, null);
            final TextView myNote = (TextView) view.findViewById(R.id.myNote);
            RelativeLayout edit = (RelativeLayout) view.findViewById(R.id.edit);
            myNote.setText(list.get(index).getString("note"));
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemNote = myNote.getText().toString();
                    itemID = list.get(index).getObjectId();
                    popDialog = new PopDialog(getActivity());
                    popDialog.setCancelable(false);
                    popDialog.show();
                }
            });
            return view;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public void setArrayList(List<AVObject> list) {
            this.list = list;
        }

    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            list = findAVObjects();
            myAdapter.setArrayList(list);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            // 设置初期数据
            swipeRefreshLayout.setRefreshing(false);
            myAdapter.notifyDataSetChanged();
        }
    }

    public List<AVObject> findAVObjects() {
        // 查询当前AVObject列表
        AVQuery<AVObject> query = new AVQuery<>("WordOriginNote");
        // 按照更新时间降序排序
        query.orderByDescending("updatedAt");
        // 最大返回1000条
        showNum = showNum + 10;
        query.limit(showNum);
        query.whereEqualTo("userID", AVUser.getCurrentUser().getObjectId());
        query.whereEqualTo("wordID", wordID);
        try {
            return query.find();
        } catch (AVException exception) {
            Log.e("tag", "Query AVObjects failed.", exception);
            return Collections.emptyList();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        switch (view.getId()) {
            case R.id.listView:
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        if (lastItem % 10 == 0) {
                            new RemoteDataTask().execute();
                        } else {
                            Toast toast = Toast.makeText(getActivity(), "没有更多了哦~", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        preLast = lastItem;
                    }
                }
        }
    }

    //定制弹出对话框的内容
    public class PopDialog extends Dialog implements View.OnClickListener {

        private RelativeLayout close, delete, save;
        private EditText note;

        public PopDialog(Context context) {
            super(context);
        }

        public PopDialog(Context context, int theme) {
            super(context, theme);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.edit_my_note_dialog);

            note = (EditText) findViewById(R.id.note);
            note.setText(itemNote);
            close = (RelativeLayout) findViewById(R.id.close);
            close.setOnClickListener(this);
            delete = (RelativeLayout) findViewById(R.id.delete);
            delete.setOnClickListener(this);
            save = (RelativeLayout) findViewById(R.id.save);
            save.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.close:
                    dismiss();
                    break;
                case R.id.delete:
                    AVQuery avQuery = new AVQuery("WordOriginNote");
                    avQuery.whereEqualTo("objectId", itemID);
                    avQuery.deleteAllInBackground(new DeleteCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Toast.makeText(getActivity(), "删除成功了哦", Toast.LENGTH_SHORT).show();
                                dismiss();
                                showNum = 0;
                                new RemoteDataTask().execute();
                            }
                        }
                    });
                    break;
                case R.id.save:
                    if (note.getText().toString().length() == 0) {
                        Toast.makeText(getActivity(), "不能提交空内容哦~", Toast.LENGTH_SHORT).show();
                    } else {
                        AVQuery<AVObject> avQuery1 = new AVQuery("WordOriginNote");
                        avQuery1.whereEqualTo("objectId",itemID);
                        avQuery1.findInBackground(new FindCallback<AVObject>() {
                            @Override
                            public void done(List<AVObject> list, AVException e) {
                                list.get(0).put("note",note.getText().toString());
                                list.get(0).saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        Toast.makeText(getActivity(), "修改成功了哦~", Toast.LENGTH_SHORT).show();
                                        dismiss();
                                        showNum = 0;
                                        new RemoteDataTask().execute();
                                    }
                                });
                            }
                        });
                    }
                    break;
            }
        }
    }

}
