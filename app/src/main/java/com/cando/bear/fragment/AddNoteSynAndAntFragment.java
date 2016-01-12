package com.cando.bear.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.cando.bear.R;

import java.util.List;


/**
 * Created by neokree on 16/12/14.
 */
public class AddNoteSynAndAntFragment extends Fragment implements View.OnClickListener {

    private EditText addNote;
    private String wordID;
    private RelativeLayout add;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.add_note_syn_and_ant, container, false);

        addNote = (EditText) rootView.findViewById(R.id.addNote);
        add = (RelativeLayout) rootView.findViewById(R.id.add);
        add.setOnClickListener(this);
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
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                if (addNote.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "请不要提交空白笔记哦~", Toast.LENGTH_SHORT).show();
                } else {
                    AVQuery avQuery = new AVQuery("WordSynAndAntNote");
                    avQuery.whereEqualTo("userID", AVUser.getCurrentUser().getObjectId());
                    avQuery.whereEqualTo("wordID", wordID);
                    avQuery.whereEqualTo("note", addNote.getText().toString());
                    avQuery.findInBackground(new FindCallback() {
                        @Override
                        public void done(List list, AVException e) {
                            if (list == null || list.size() == 0) {
                                AVObject post = new AVObject("WordSynAndAntNote");
                                post.put("userID", AVUser.getCurrentUser().getObjectId());
                                post.put("wordID", wordID);
                                post.put("note", addNote.getText().toString());
                                post.saveInBackground(new SaveCallback() {
                                    public void done(AVException e) {
                                        if (e == null) {
                                            // 保存成功
                                            Toast toast = Toast.makeText(getActivity(), "新建笔记成功了哦~", Toast.LENGTH_SHORT);
                                            toast.show();
                                        } else {
                                            // 保存失败，输出错误信息
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getActivity(), "您已经提交过相同笔记了哦，请不要重复提交~", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
        }
    }

}
