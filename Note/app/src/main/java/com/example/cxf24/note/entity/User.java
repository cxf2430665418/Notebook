package com.example.cxf24.note.entity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class User extends BmobUser implements Serializable {

    private String username;
    private String password;




    public String getUsername() {
            return username;
    }

    public void setUsername(String nickname) {
        this.username = nickname;
    }

    public String getPassword() {
            return password;
    }

    public void setPassword(String notePwd) {
        this.password = notePwd;
    }

    static public void getUserNotes(Context context, String user_id,final OnResponseListener listener){
        //  Bmob.initialize(context, Constants.APPLICATION_ID);
        final List<NotebookData> list = new ArrayList<NotebookData>();
        BmobQuery<NotebookData> query = new BmobQuery<NotebookData>();

        query.addWhereEqualTo("username", user_id);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //按更新日期降序排列
        query.order("-updatedAt");
        //执行查询方法
        query.findObjects(new FindListener<NotebookData>() {
            @Override
            public void done(List<NotebookData> object, BmobException e) {
                if(e==null){
                    Response response = new Response();
                    response.setIsSucces(true);
                    response.setNoteItemList(object);
                }else{
                    Log.i("bmob","失败：");
                }
            }
        });
    }

}
