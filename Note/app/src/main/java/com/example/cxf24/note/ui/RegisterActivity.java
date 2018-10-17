package com.example.cxf24.note.ui;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cxf24.note.R;
import com.example.cxf24.note.entity.User;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.exception.BmobException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText accountEt;
    EditText pwdEt;
    EditText twicePwdEt;
    Button registerBtn;
    ImageButton imgBtnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        accountEt = (EditText) findViewById(R.id.et_username);
        pwdEt = (EditText) findViewById(R.id.et_password);
        twicePwdEt = (EditText) findViewById(R.id.et_email);
        registerBtn = (Button) findViewById(R.id.btn_register);
        imgBtnBack= (ImageButton) findViewById(R.id.img_back);
        registerBtn.setOnClickListener(this);
        imgBtnBack.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.img_back:
                back();
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    public void back() {
        finish();
    }



    public void register(){
        String name =accountEt.getText().toString();
        String password = pwdEt.getText().toString();
        String pwd_again = twicePwdEt.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(RegisterActivity.this,"账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this,"密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd_again.equals(password)) {
            Toast.makeText(RegisterActivity.this,"两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        final BmobUser bu = new BmobUser();
        bu.setUsername(name);
        bu.setPassword(password);
        bu.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser s, BmobException e) {
                if(e==null){
                    Toast.makeText(RegisterActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this,"注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

