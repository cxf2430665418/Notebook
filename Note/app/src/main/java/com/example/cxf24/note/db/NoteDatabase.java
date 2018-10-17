package com.example.cxf24.note.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cxf24.note.entity.NotebookData;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase {
    private final DatabaseHelper dbHelper;

    public NoteDatabase(Context context) {
        super();
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * 增
     * 
     * @param data
     */
    public void insert(NotebookData data) {
        String sql = "insert into " + DatabaseHelper.NOTE_TABLE_NAME;

        sql += "(_id, objectid, date, username, title, content) values(?, ?, ?, ?, ?, ?)";

        SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
        sqlite.execSQL(sql, new String[] {data.getId()+ "", data.getObjectId()+ "", data.getDate() + "", data.getUsername() + "", data.getTitle() + "",
                data.getContent()+ "" });
        sqlite.close();
    }

    /**
     * 删
     * 
     * @param id
     */
    public void delete(int id) {
        SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
        String sql = ("delete from " + DatabaseHelper.NOTE_TABLE_NAME + " where _id=?");
        sqlite.execSQL(sql, new Integer[] { id });
        sqlite.close();
    }

    /**
     * 改
     * 
     * @param data
     */
    public void update(NotebookData data) {
        SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
        String sql = ("update " + DatabaseHelper.NOTE_TABLE_NAME + " set objectid=?, date=?, username=?, title=?, content=? where _id=?");
        sqlite.execSQL(sql,
                new String[] { data.getObjectId() + "",
                        data.getDate(), data.getUsername()+ "", data.getTitle()+ "", data.getContent()+ ""});
        sqlite.close();
    }

    public List<NotebookData> query() {
        return query(" ");
    }

    /**
     * 查
     * 
     * @param where
     * @return
     */
    public List<NotebookData> query(String where) {
        SQLiteDatabase sqlite = dbHelper.getReadableDatabase();
        ArrayList<NotebookData> data = null;
        data = new ArrayList<NotebookData>();
        Cursor cursor = sqlite.rawQuery("select * from "
                + DatabaseHelper.NOTE_TABLE_NAME + where, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            NotebookData notebookData = new NotebookData();
            notebookData.setId(cursor.getInt(0));
            notebookData.setObjectId(cursor.getString(1));
            notebookData.setDate(cursor.getString(4));
            notebookData.setContent(cursor.getString(5));
            data.add(notebookData);
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        sqlite.close();

        return data;
    }

    /**
     * 重置
     * 
     * @param datas
     */
    public void reset(List<NotebookData> datas) {
        if (datas != null) {
            SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
            // 删除全部
            sqlite.execSQL("delete from " + DatabaseHelper.NOTE_TABLE_NAME);
            // 重新添加
            for (NotebookData data : datas) {
                insert(data);
            }
            sqlite.close();
        }
    }

    /**
     * 保存一条数据到本地(若已存在则直接覆盖)
     * 
     * @param data
     */
    public void save(NotebookData data) {
        List<NotebookData> datas = query(" where _id=" + data.getId());
        if (datas != null && !datas.isEmpty()) {
            update(data);
        } else {
            insert(data);
        }
    }
}