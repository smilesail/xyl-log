package com.testlog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.xyl.L;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.init(BuildConfig.DEBUG, true);
        L.t(3).d();

        try{
            aaa();
        } catch (Exception e){
            L.threadInfo(true).e(TAG,"sss",e);
            L.threadInfo(true).e(TAG, "sss",e);
//            e.printStackTrace();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("this is a test");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("test message");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    private void aaa() {
        bbb();
    }

    private void bbb() {
        L.t(5).i("xxx");
        throw new RuntimeException("实打实发达");
    }
}
