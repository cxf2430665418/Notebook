package com.example.cxf24.note.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cxf24.note.R;
import com.example.cxf24.note.db.NoteDatabase;
import com.example.cxf24.note.entity.AccountUtils;
import com.example.cxf24.note.entity.NotebookData;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class NoteEditActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton save;
    private EditText title;
    private EditText content;
    Button identify;
    private Uri uri;
    public static final int TAKE_POTHO=1;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private Bitmap bitmap;
    private NoteDatabase noteDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        title = (EditText)findViewById(R.id.edit_title);
        content = (EditText)findViewById(R.id.edit_content);
        save = (ImageButton)findViewById(R.id.save_edit);
        identify=(Button)findViewById(R.id.identify) ;
        identify.setOnClickListener(this);
        save.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.save_edit:
                InsertNotebookData();
                break;
            case R.id.identify:
                getImages();
                break;
        }
    }
    private void InsertNotebookData(){
        //noteDb = new NoteDatabase(this);
        String edittitle =title.getText().toString();
        String editcontent =content.getText().toString();
        String username = AccountUtils.getUserName(NoteEditActivity.this);
        NotebookData editData = new NotebookData();
        editData.setUsername(username);
        editData.setTitle(edittitle);
        editData.setContent(editcontent);
        //noteDb.insert(editData);
        editData.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Toast.makeText(NoteEditActivity.this,"添加成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NoteEditActivity.this,"添加失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Intent intent = new Intent(NoteEditActivity.this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void getImage(){
        File outImage=new File(getExternalCacheDir(),"output_image.jpg");
        try{
            if(outImage.exists())
            {
                outImage.delete();
            }
            outImage.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24)
        {
            uri= FileProvider.getUriForFile(NoteEditActivity.this,"com.example.gdzc.cameraalbumtest.fileprovider",outImage);
        }
        else
        {
            uri=Uri.fromFile(outImage);
        }
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent,TAKE_POTHO);
    }
    public void getImages() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        switch (requestCode)
        {
            case TAKE_POTHO:
                if(resultCode==RESULT_OK)
                {
                    try{
                        bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        getIdentify();
                    }catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
                break;
            case PHOTO_REQUEST_GALLERY:
                if (data != null)
                {
                    uri=data.getData();
                    try{
                        bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        getIdentify();
                    }catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }

                }

            default:
                break;
        }
    }
    public void getIdentify(){
        TessBaseAPI mTess = new TessBaseAPI();
        String datapath = "/storage/emulated/0/tesseract/"; //语言包目录
        String language = "chi_sim";
        File dir = new File(datapath + "tessdata/");
        if (!dir.exists()){ Log.e("tag","文件不存在"); }
        mTess.init(datapath, language);
        mTess.setImage(bitmap);
        String result = mTess.getUTF8Text();
        content.setText(result);
    }

}
