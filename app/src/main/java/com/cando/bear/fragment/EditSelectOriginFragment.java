package com.cando.bear.fragment;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.cando.bear.R;

import java.util.List;


/**
 * Created by neokree on 16/12/14.
 */
public class EditSelectOriginFragment extends Fragment {

    private Dialog progressDialog;
    private String wordID;
    private TextView edit_select;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        progressDialog = ProgressDialog.show(getActivity(), "", "数据加载中，请稍后...", true);
//        progressDialog.setCancelable(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.edit_select, container, false);

        edit_select = (TextView) rootView.findViewById(R.id.edit_select);
        wordID = getArguments().getString("wordID");
        new RemoteDataTask().execute();

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
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            findAVObjects();
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
//            progressDialog.dismiss();
        }
    }

    public void findAVObjects() {
        // 查询当前AVObject列表
        AVQuery<AVObject> query = new AVQuery<>("Word");
        // 按照更新时间降序排序
        query.orderByDescending("updatedAt");
        // 最大返回1000条
        query.whereEqualTo("objectId", wordID);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    if (list.get(0).getString("tips") != null) {
                        edit_select.setText(list.get(0).getString("tips"));
                    } else {
                        edit_select.setText("该部分服务器暂时没有添加数据哦~");
                    }
                }
            }
        });
    }
}
