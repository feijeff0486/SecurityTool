package com.jeff.securitytool.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jeff.encrypt.library.AndroidEncrypt;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public String DATA_PATH;
    public String DATA_PATH2;
    public String DATA_PATH3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DATA_PATH = getFilesDir().getPath() + "/origin.m3u8";
        DATA_PATH2 = getFilesDir().getPath() + "/origin2.m3u8";
        DATA_PATH3 = getFilesDir().getPath() + "/origin3.m3u8";
        findViewById(R.id.btn_encrypt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encrypt();
            }
        });
        findViewById(R.id.btn_encrypt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrypt();
            }
        });
    }

    private void encrypt() {
        try {
            String content = "HAHAHA I AM CONTENT";
            File file = new File(DATA_PATH);
            File file2 = new File(DATA_PATH2);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (!file2.exists()) {
                file2.createNewFile();
            }
            com.jeff.securitytool.demo.FileIOUtils.writeFileFromString(file,content);
//            FileIOUtils.writeOut(file, content.getBytes());
            AndroidEncrypt.encrypt(this, file, file2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decrypt() {
        try {
            AndroidEncrypt.decrypt(this, new File(DATA_PATH2), new File(DATA_PATH3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
