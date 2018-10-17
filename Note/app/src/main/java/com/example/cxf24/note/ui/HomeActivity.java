package com.example.cxf24.note.ui;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.cxf24.note.R;
import com.example.cxf24.note.adapter.NotebookAdapter;
import com.example.cxf24.note.db.NoteDatabase;
import com.example.cxf24.note.entity.AccountUtils;
import com.example.cxf24.note.entity.NotebookData;
import com.example.cxf24.note.utils.SystemUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    protected Boolean isFirstUse;
    private FloatingActionButton fab;
    private  NoteBookFragment noteBookFragment;
    private NoteDatabase noteDb;
    public List<NotebookData> datas;
    public NotebookAdapter adapter;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        datas = new ArrayList<>();
        /**NotebookData data=new NotebookData();
        data.setTitle("123");
        data.setContent("123");
        data.setUsername("123");
        datas.add(data);*/
        //adapter=new NotebookAdapter(HomeActivity.this,datas);
        //如果是第一次启动该Activity，则创建数据库，否则加载数据库
        /**isFirstUse=new SystemUtils(HomeActivity.this).isFirstUse();
        if(isFirstUse) {
            //initIntruduceData();
        }**/
        //initData();
        getServerData();
        //String username = datas.get(1).getTitle();
        //Toast.makeText(HomeActivity.this,username, Toast.LENGTH_SHORT).show();
        //adapter=new NotebookAdapter(HomeActivity.this,datas);
        /**adapter.setOnItemClickListner(new OnItemClickListener() {
            @Override
            public void OnItemclick(View view, int position) {
                Intent intent = new Intent(HomeActivity.this, NoteEditActivity.class);
                Bundle bundle = new Bundle();
                startActivity(intent);
                //this.finish();
            }
        });*/

        //initMainFragment();//加载数据库

    }
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.fab:
                goToNoteEditActivity();
                break;
        }
    }
    private void goToNoteEditActivity(){
        Intent intent = new Intent(HomeActivity.this, NoteEditActivity.class);
        Bundle bundle = new Bundle();
        startActivity(intent);
        this.finish();
    }
    public void getServerData()
    {
        //List<NotebookData> datas2 = new ArrayList<>();
        String username = AccountUtils.getUserName(HomeActivity.this);
        BmobQuery<NotebookData> query = new BmobQuery<NotebookData>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("username", username);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        //query.setLimit(50);
        //执行查询方法
        query.findObjects(new FindListener<NotebookData>() {
            @Override
            public void done(List<NotebookData> object, BmobException e) {
                if(e==null){
                    datas.clear();
                    for(int i=0;i<object.size();i++){//通过循环来赋值给另一个List
                        NotebookData object1=object.get(i);
                        //String username = object1.getUsername();
                        //Toast.makeText(HomeActivity.this,username, Toast.LENGTH_SHORT).show();
                        datas.add(object1);
                    }
                    Collections.reverse(datas);
                    adapter=new NotebookAdapter(HomeActivity.this,datas);
                    adapter.setOnItemClickListener(new NotebookAdapter.OnItemClickListener(){
                        @Override
                        public void onClick(int position) {
                            Intent intent = new Intent(HomeActivity.this, NoteChangeActivity.class);
                            NotebookData data = datas.get(position);
                            intent.putExtra("title",data.getTitle());
                            intent.putExtra("content",data.getContent());
                            intent.putExtra("objectid",data.getObjectId());
                            startActivity(intent);
                            finish();
                        }
                    });
                    adapter.setOnItemLongClickListener(new NotebookAdapter.OnItemLongClickListener() {
                        @Override
                        public void onLongClick(int position) {
                            //长按删除数据
                            NotebookData data = datas.get(position);
                            String str=data.getObjectId();

                            adapter.notifyDataSetChanged();
                        }
                    });
                    recyclerview = (RecyclerView)findViewById(R.id.note_list);
                    recyclerview.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                    recyclerview.setAdapter(adapter);
                    //datas.clear();//先将datas中的数据全部清除掉
                    //datas.addAll(object);
                    //adapter=new NotebookAdapter(HomeActivity.this,object);
                    //Toast.makeText(HomeActivity.this,datas.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(HomeActivity.this,"加载失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initIntruduceData()
    {
        noteDb=  new NoteDatabase(HomeActivity.this);
        new SystemUtils(HomeActivity.this).set("isFirstUse","false");
    }
    private void initMainFragment()
    {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        noteBookFragment=new  NoteBookFragment();
        ft.replace(R.id.note_list, noteBookFragment,null);
        ft.commit();
    }
    public void initData() {
        noteDb = new NoteDatabase(HomeActivity.this);
        datas = noteDb.query();// 查询操作，忽略耗时
        if (datas != null) {
            adapter = new NotebookAdapter(HomeActivity.this,datas);
        }
        else //如果本地无数据则尝试从服务器端获取数据
        {
            getServerData();
        }
    }
}
