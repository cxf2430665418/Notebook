package com.example.cxf24.note.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cxf24.note.R;
import com.example.cxf24.note.entity.AccountUtils;
import com.example.cxf24.note.entity.SPUtils;
import com.example.cxf24.note.entity.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.exception.BmobException;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText login_name,login_pwd;
    Button btn_login;
    TextView btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(LoginActivity.this, "3877971a813d71720e3c42ad60ff9be3");
        setContentView(R.layout.activity_login);

        // 获取界面中的相关View
        login_name = (EditText) findViewById(R.id.login_name);
        login_pwd = (EditText) findViewById(R.id.login_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (TextView) findViewById(R.id.btn_register);
        // 设置登录按钮点击事件
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_login:
                //goToHomeActivity();
                login();
                break;
            case R.id.btn_register:
                goToRegisterActivity();
                break;
        }
    }
    private void login(){
        final String username=login_name.getText().toString();
        final String password=login_pwd.getText().toString();
        BmobUser user = new BmobUser();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<BmobUser>() {

            @Override
            public void done(BmobUser user, BmobException e) {
                if(e==null){
                    Toast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_SHORT).show();
                    SPUtils.put(LoginActivity.this,"user_name",username);
                    SPUtils.put(LoginActivity.this,"pwd",password);
                    //将登陆信息保存本地
                    //User user2;
                    //user2 = BmobUser.getCurrentUser(this, User.class);
                    //AccountUtils.saveUserInfos(LoginActivity.this, user, password);
                    goToHomeActivity();
                }else{
                    Toast.makeText(LoginActivity.this,"用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void goToHomeActivity(){
        Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(intent);
        this.finish();
    }
    private void goToRegisterActivity(){
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
