package com.example.cxf24.note.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cxf24.note.R;
import com.example.cxf24.note.adapter.NotebookAdapter;
import com.example.cxf24.note.db.NoteDatabase;
import com.example.cxf24.note.entity.AccountUtils;
import com.example.cxf24.note.entity.NotebookData;

import com.example.cxf24.note.entity.OnResponseListener;
import com.example.cxf24.note.entity.Response;
import com.example.cxf24.note.entity.User;

import com.example.cxf24.note.utils.Constants;


import java.util.List;
import java.util.Map;



public class NoteBookFragment extends Fragment {
    //管理Fragment
    private NoteDatabase noteDb;
    private List<NotebookData> datas;
    private NotebookAdapter adapter;
    private RecyclerView recyclerview;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Activity aty;
    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;
    public static final int STATE_NOMORE = 3;
    public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态
    public static int mState = STATE_NONE;
    /**
     * 用来做更进一步人性化的防手抖策略时使用<br>
     * 比如由于手抖动上下拉菜单时拉动一部分，但又没有达到可刷新的时候，暂停一段时间，这个时候用户的逻辑应该是要移动item的。<br>
     * 其实应该还有一种根据setOnFocusChangeListener来改写的方法，我没有尝试。
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_note, null,
                false);
        aty = getActivity();
        //initData();
        //initView(rootView);
        return rootView;
    }


    /***************************** initialization method ***************************/


    /**public void initData() {
        noteDb = new NoteDatabase(aty);
        datas = noteDb.query();// 查询操作，忽略耗时
        if (datas != null) {
            adapter = new NotebookAdapter(aty,datas);
        }
        else //如果本地无数据则尝试从服务器端获取数据
        {
            getServerData();
        }
    }

    public void getServerData()
    {
        String username =AccountUtils.getUserName(getActivity());

        User.getUserNotes(getActivity(), username, new OnResponseListener() {
            @Override
            public void onResponse(Response response) {
                if(response.isSucces())
                {
                    datas.clear();//先将datas中的数据全部清除掉
                    datas.addAll(response.getNoteItemList());
                    if (datas.size() > 0) {
                        adapter = new NotebookAdapter(aty, datas);

                    } else {
                        Snackbar.make(recyclerview,"暂无数据哦！赶快添加一个：)",Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    public void initView(View view) {
        recyclerview = (RecyclerView) view.findViewById(R.id.note_list);
        //recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
    }**/

}