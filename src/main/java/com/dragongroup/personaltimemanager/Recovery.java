package com.dragongroup.personaltimemanager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 何宏华 on 2016/7/19.
 */
public class Recovery extends Fragment {
    public View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View newLayout=inflater.inflate(R.layout.recovery,container,false);
        return newLayout;
    }
}
