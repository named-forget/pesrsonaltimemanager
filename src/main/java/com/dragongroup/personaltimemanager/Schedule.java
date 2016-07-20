package com.dragongroup.personaltimemanager;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
/**
 * Created by 何宏华 on 2016/7/19.
 */
public class Schedule extends Fragment {
    public MyView[] mv;
    private int i;
    private int id[];
    private  View newLayout;
    private LinearLayout schedule_ll;
    private Factory factory;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle save){
        newLayout=inflater.inflate(R.layout.schedule,container,false);
       create();
        return newLayout;
    }
    public void create(){
        schedule_ll=(LinearLayout)newLayout.findViewById(R.id.schedule_ll) ;
        factory=new Factory(getActivity());
        Cursor cs;//查询结果返回对象
        // ;//储存表中元組数量
            cs = factory.selectAll(true, true);
            if(cs==null){
                return;
            }else {
            i = cs.getCount();
            //设定控件数量
            mv = new MyView[i];
            id = new int[i];
            cs.moveToFirst();
            for (int j = 0; j < i; j++) {
                int[] alarmtime=new int[5];
                LinearLayout.LayoutParams sl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                String content = cs.getString(1);
                String time = cs.getString(3);
                String state = cs.getString(7);
                String ring = cs.getString(5);
                String classify = cs.getString(6);
                id[j] = cs.getInt(0);
                Switch mv_sw = new Switch(getActivity());
                TextView mv_tv1 = new TextView(getActivity());
                TextView mv_tv2 = new TextView(getActivity());
                CheckBox mv_check = new CheckBox(getActivity());
                mv_tv1.setText("    " + content);
                mv_tv2.setText("       " + time);
                alarmtime=factory.getTime(time);
                if (state == "未完成") {
                    mv_sw.setChecked(false);
                } else {
                    mv_sw.setChecked(true);
                }
                mv[j] = new MyView(getActivity(), mv_sw, mv_tv1, mv_tv2, mv_check, id[j]);
                SetClock setClock=new SetClock();
                setClock.createClock(getActivity(),alarmtime[0],alarmtime[1],alarmtime[2],alarmtime[3],alarmtime[4],content,time);
                mv[j].imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //计时器按钮触发事件
                        Intent intent=new Intent(getActivity(),Timer.class);
                        startActivity(intent);
                    }
                });
                mv[j].editBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //编辑按钮触发事件
                        Intent intent=new Intent(getActivity(),EditSchedule.class);
                        startActivity(intent);
                    }
                });
//            mv[j].setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
//            mv[j].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    System.out.println("点击到了父控件");
//                }
//            });
                mv[j].editBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                mv[j].onCreate();
                schedule_ll.addView(mv[j], sl);
                cs.moveToNext();
            }
        }
        cs.close();
    }
    public void checkState(){
        for(int j=0;j<i;j++){
            mv[j].checkState();
        }
    }
    public void backState(){
        for(int j=0;j<i;j++){
            mv[j].backState();
        }
    }
    public void remove(){
        int a=i;
        for(int j=0;j<a;j++){
            if(mv[j].check_state==1){
                schedule_ll.removeView(mv[j]);
                factory.delete(id[j]);
                i--;

            }else{
                mv[j].backState();
            }
        }
    }
    public void markAll(){
        for(int j=0;j<i;j++){
            mv[j].marked();
        }
    }
}
