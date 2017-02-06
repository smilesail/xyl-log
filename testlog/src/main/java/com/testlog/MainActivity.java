package com.testlog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xyl.L;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.init(BuildConfig.DEBUG, true);
        L.t(3).d();

        try{
            aaa();
        } catch (Exception e){
        L.e(e).threadInfo(true);
            e.printStackTrace();
        }
    }

    private void aaa() {
        bbb();
    }

    private void bbb() {
        L.t(5).i("xxx");
        throw new RuntimeException("实打实发达");
    }
}
