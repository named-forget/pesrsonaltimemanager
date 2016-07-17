package com.dragongroup.personaltimemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView sc=new ScrollView(this);
        LinearLayout l1=new LinearLayout(this);
        l1.setOrientation(LinearLayout.VERTICAL);
        for(int i=0;i<5;i++){
            TextView tv1=new TextView(this);
            TextView tv2=new TextView(this);
            Switch sw=new Switch(this);
            MyView my=new MyView(this,sw,tv1,tv2,null);
            my.onCreate();
            l1.addView(my);
        }
        sc.addView(l1);
        setContentView(sc);
    }
    //主界面初始化函数 项目主要函数，内容较多
    public void init(){

    }
}
