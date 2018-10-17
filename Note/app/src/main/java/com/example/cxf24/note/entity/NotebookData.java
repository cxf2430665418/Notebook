package com.example.cxf24.note.entity;
import android.content.Context;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import okhttp3.Response;


public class NotebookData extends BmobObject{
    private Integer id;
    private String username;//用于服务器端存储需要
    private String date;
    private String title;
    private String content;

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) {
            return true;
        } else {
            if (o instanceof NotebookData) {
                NotebookData data = (NotebookData) o;
                try {
                    return (this.username == data.getUsername())
                            && (this.id == data.getId())
                            && (this.title == data.getTitle())
                            && (this.date.equals(data.getDate()))
                            && (this.content == data.getContent());
                } catch (NullPointerException e) {
                    return false;
                }
            } else {
                return false;
            }
        }
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer content) {
        this.id =content;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String content) {
        this.title =content;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String userId) {
        this.username = userId;
    }
}