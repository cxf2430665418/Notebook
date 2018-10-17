package com.example.cxf24.note.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cxf24.note.R;
import com.example.cxf24.note.entity.NotebookData;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class NoteChangeActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText title2,content2;
    private ImageButton sava2;
    Button delete;
    private String str1,str2,str3,str4,str5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_change);
        title2=(EditText)findViewById(R.id.edit_title2);
        content2=(EditText)findViewById(R.id.edit_content2);
        sava2=(ImageButton)findViewById(R.id.save_edit2);
        sava2.setOnClickListener(this);
        delete=(Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        Intent intent=getIntent();
        str1=intent.getStringExtra("title");
        str2=intent.getStringExtra("content");
        str3=intent.getStringExtra("objectid");
        Toast.makeText(NoteChangeActivity.this,"id"+str3, Toast.LENGTH_SHORT).show();
        title2.setText(str1);
        content2.setText(str2);
    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.save_edit2:
                str4=title2.getText().toString();
                str5=content2.getText().toString();
                UpdateNote(str3,str4,str5);
                break;
            case R.id.delete:
                Delete(str3);
        }
    }
    public void UpdateNote(String str,String str1,String str2){
        NotebookData data1 = new NotebookData();
        data1.setValue("title", str1);
        data1.setValue("content", str2);
        data1.update(str, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(NoteChangeActivity.this,"成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NoteChangeActivity.this,"失败", Toast.LENGTH_SHORT).show();
                }
            }

        });
        goToNoteEditActivity();
    }
    public void goToNoteEditActivity(){
        Intent intent = new Intent(NoteChangeActivity.this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void Delete(String str){
        NotebookData gameScore = new NotebookData();
        gameScore.setObjectId(str);
        gameScore.delete(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    goToNoteEditActivity();
                }else{

                }
            }
        });
    }
}