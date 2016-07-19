package com.dragongroup.personaltimemanager;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
/**
 * Created by 何宏华 on 2016/7/19.
 */
public class Schedule extends Fragment {
    public MyView[] mv;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle save){
        View newLayout=inflater.inflate(R.layout.schedule,container,false);
        LinearLayout schedule_ll=(LinearLayout)newLayout.findViewById(R.id.schedule_ll) ;
        Factory factory=new Factory();
        int[] b={1,2,3,4,5,6};
        Cursor cs;//查询结果返回对象
        int i;//储存表中元組数量
        factory.createDB(getActivity());
        factory.createTable();
        factory.insert("测试","测试",b,"123",1,"未完成",1);
        cs=factory.selectAll();
        i=cs.getCount();
        //设定控件数量
        mv=new MyView[i];
        cs.moveToFirst();
        for(int j=0;j<i;j++){
            LinearLayout.LayoutParams sl=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            String content=cs.getString(1);
            String time=cs.getString(3);
            String state=cs.getString(7);
            String ring=cs.getString(5);
            String classify=cs.getString(6);
            Switch mv_sw=new Switch(getActivity());
            TextView mv_tv1=new TextView(getActivity());
            TextView mv_tv2=new TextView(getActivity());
            CheckBox mv_check=new CheckBox(getActivity());
            mv_tv1.setText(content);
            mv_tv2.setText(time);
            if(state=="未完成"){
                mv_sw.setChecked(false);
            }else {
                mv_sw.setChecked(true);
            }
          mv[j]=new MyView(getActivity(),mv_sw,mv_tv1,mv_tv2,mv_check);
            mv[j].onCreate();
            schedule_ll.addView(mv[j],sl);
            cs.moveToNext();
        }
        cs.close();
        return newLayout;
    }
}
