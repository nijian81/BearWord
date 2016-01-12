package com.cando.bear.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;


/**
 * Created by neokree on 16/12/14.
 */
public class ShareNoteSynAndAntFragment extends Fragment {

    private List<AVObject> list;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyAdapter myAdapter;
    private int preLast;
    private String wordID, itemID, itemNote;

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

        View rootView = inflater.inflate(R.layout.share_note, container, false);

        // 设置初期数据
        list = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RemoteDataTask().execute();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        // 设置ListView
        listView = (ListView) rootView.findViewById(R.id.listView);
        myAdapter.setArrayList(list);
        listView.setAdapter(myAdapter);
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
        new RemoteDataTask().execute();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
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
        public View getView(int index, View view, ViewGroup arg2) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.share_note_item, null);

            final int position = index;
            TextView likeNum = (TextView) view.findViewById(R.id.likeNum);
            likeNum.setText("(" + list.get(index).getInt("likeNum") + ")");
            TextView dislikeNum = (TextView) view.findViewById(R.id.dislikeNum);
            dislikeNum.setText("(" + list.get(index).getInt("dislikeNum") + ")");
            TextView note = (TextView) view.findViewById(R.id.note);
            note.setText(list.get(index).getString("note"));
            RelativeLayout like = (RelativeLayout) view.findViewById(R.id.like);
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemID = list.get(position).getObjectId();
                    AVQuery<AVObject> avQuery = new AVQuery<AVObject>("WordMarkRecord");
                    avQuery.whereEqualTo("noteID", itemID);
                    avQuery.whereEqualTo("userID", AVUser.getCurrentUser().getObjectId());
                    avQuery.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            if (e == null) {
                                if (list.size() == 0) {
                                    AVObject post = new AVObject("WordMarkRecord");
                                    post.put("noteID", itemID);
                                    post.put("userID", AVUser.getCurrentUser().getObjectId());
                                    post.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            if (e == null) {
                                                AVQuery<AVObject> avQuery1 = new AVQuery("WordSynAndAntNote");
                                                avQuery1.whereEqualTo("objectId", itemID);
                                                avQuery1.findInBackground(new FindCallback<AVObject>() {
                                                    @Override
                                                    public void done(List<AVObject> list, AVException e) {
                                                        if (e == null) {
                                                            list.get(0).increment("likeNum");
                                                            list.get(0).saveInBackground(new SaveCallback() {
                                                                @Override
                                                                public void done(AVException e) {
                                                                    Toast.makeText(getActivity(), "标记喜欢成功了哦~", Toast.LENGTH_SHORT).show();
                                                                    new RemoteDataTask().execute();
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), "该条笔记标记过了，不能重复标记哦~", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            });
            RelativeLayout dislike = (RelativeLayout) view.findViewById(R.id.dislike);
            dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemID = list.get(position).getObjectId();
                    AVQuery<AVObject> avQuery = new AVQuery<AVObject>("WordMarkRecord");
                    avQuery.whereEqualTo("noteID", itemID);
                    avQuery.whereEqualTo("userID", AVUser.getCurrentUser().getObjectId());
                    avQuery.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            if (e == null) {
                                if (list.size() == 0) {
                                    AVObject post = new AVObject("WordMarkRecord");
                                    post.put("noteID", itemID);
                                    post.put("userID", AVUser.getCurrentUser().getObjectId());
                                    post.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            if (e == null) {
                                                AVQuery<AVObject> avQuery1 = new AVQuery("WordSynAndAntNote");
                                                avQuery1.whereEqualTo("objectId", itemID);
                                                avQuery1.findInBackground(new FindCallback<AVObject>() {
                                                    @Override
                                                    public void done(List<AVObject> list, AVException e) {
                                                        if (e == null) {
                                                            list.get(0).increment("dislikeNum");
                                                            list.get(0).saveInBackground(new SaveCallback() {
                                                                @Override
                                                                public void done(AVException e) {
                                                                    Toast.makeText(getActivity(), "标记不喜欢成功了哦~", Toast.LENGTH_SHORT).show();
                                                                    new RemoteDataTask().execute();
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), "该条笔记标记过了，不能重复标记哦~", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
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
            swipeRefreshLayout.setRefreshing(false);
            myAdapter.notifyDataSetChanged();
        }
    }

    public void findAVObjects() {
        // 查询当前AVObject列表
        AVQuery<AVObject> query = new AVQuery<>("WordSynAndAntNote");
        // 按照更新时间降序排序
        query.orderByDescending("likeNum");
        // 最大返回1000条
        query.limit(1000);
        query.whereEqualTo("wordID", wordID);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list1, AVException e) {
                if (e == null) {
                    list = list1;
                    myAdapter.setArrayList(list);
                } else {
                    Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
