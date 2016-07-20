package com.dragongroup.personaltimemanager;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 何宏华 on 2016/7/20.
 */
//public class Timer extends Fragment {
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle save){
//        View newLayout=inflater.inflate(R.layout.schedule,container,false);
//        return newLayout;
//    }
public class Timer extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
    }
}
