package com.example.ffmpegtranscode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ffmpegtranscode.jni.FFmpegCmd;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private Button button;
private TextView textView;
private EditText et_input,et_output;
private ProgressDialog pd;
private ArrayList<String> cmd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if(hasWritePermission!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    }else {
                        pd=new ProgressDialog(MainActivity.this);
                        pd.show();
                        Log.i("文件目录",Environment.getExternalStorageDirectory().getAbsolutePath());
                        String input= Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+et_input.getText().toString().trim();
                        String output=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+et_output.getText().toString().trim();
                       /* cmd=new ArrayList<>();
                        cmd.add("ffmpeg");
                        cmd.add("-y");
                        cmd.add("-ss");
                        cmd.add("00:00:20");
                        cmd.add("-t");
                        cmd.add("00:00:08");
                        cmd.add("-i");
                        cmd.add(input);
                        cmd.add("-vcodec");
                        cmd.add("copy");
                        cmd.add("-acodec");
                        cmd.add("copy");
                        cmd.add(output);*/
                      /*  String cmd="ffmpeg -y -ss 00:00:20 -t 00:00:08 -i %s -vcodec copy -acodec copy %s";
                        cmd=String.format(cmd,input,output);
                        String[] cmd2=cmd.split("");*/
                        ffmpegTest(input,output);
                    }
                }

            }
        });

    }
    private void initView(){
        button=(Button)findViewById(R.id.btn_main);
        et_input=(EditText)findViewById(R.id.et_input);
        et_output=(EditText)findViewById(R.id.et_ouput);
        textView=(TextView)findViewById(R.id.time_record);
    }
private void ffmpegTest(String in,String out){
        new Thread(() -> {
            final long satrtTime=System.currentTimeMillis();
            FFmpegCmd.transcode(in,out,0,0,0,0,null);
            runOnUiThread(() -> {
                pd.dismiss();
                textView.setText("共耗时："+(System.currentTimeMillis()-satrtTime)/1000+"秒");
            });

        }).start();
}
}
