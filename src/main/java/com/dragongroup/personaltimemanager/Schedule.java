package com.dragongroup.personaltimemanager;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 何宏华 on 2016/7/19.
 */
public class Schedule extends Fragment {
    public MyView[] mv;
    private int i;
    private int id[];
    private View newLayout;
    private LinearLayout schedule_ll;
    private Factory factory;
/*
保存从数据库中提取的数据
*/;
   private String content[];
    private String note[];
    private String time[];
    private String state[];
    private String ring[];
    private String classify[];

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle save) {
        newLayout = inflater.inflate(R.layout.schedule, container, false);
        create();
        return newLayout;
    }

    public void create() {
        schedule_ll = (LinearLayout) newLayout.findViewById(R.id.schedule_ll);
        factory = new Factory(getActivity());
        Cursor cs;//查询结果返回对象
        // ;//储存表中元組数量
        cs = factory.selectAll(true, false);
        if (cs == null) {
            return;
        } else {
            i = cs.getCount();
            //设定控件数量
            mv = new MyView[i];
            content = new String[i];
            note=new String[i];
            time = new String[i];
            state = new String[i];
            ring = new String[i];
            classify = new String[i];
            id = new int[i];
            cs.moveToFirst();
            for (int j = 0; j < i; j++) {

                LinearLayout.LayoutParams sl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                int[] alarmtime = new int[5];
               content[j] = cs.getString(1);
                note[j]=cs.getString(2);
               time[j] = cs.getString(4);
               state[j] = cs.getString(7);
               ring[j] = cs.getString(5);
               classify[j] = cs.getString(6);
                id[j] = cs.getInt(0);
                Switch mv_sw = new Switch(getActivity());
                TextView mv_tv1 = new TextView(getActivity());
                TextView mv_tv2 = new TextView(getActivity());
                CheckBox mv_check = new CheckBox(getActivity());
              /*
              * 设置闹钟
              */
                alarmtime = factory.getTime(time[j]);
                SetClock setClock = new SetClock();
                setClock.createClock(getActivity(), alarmtime[0], alarmtime[1], alarmtime[2], alarmtime[3], alarmtime[4], content[j], time[j]);
                /*
                * 设置日程显示
                */
                mv_tv1.setText("    " + content[j]);
                mv_tv2.setText("       " + time[j]);
                ;

                if (state[j].equals("未完成")) {
                    mv_sw.setChecked(false);
                } else {
                    mv_sw.setChecked(true);
                }
                mv[j] = new MyView(getActivity(), mv_sw, mv_tv1, mv_tv2, mv_check, id[j]);

//                LinearLayout frameLayout=new LinearLayout(getActivity());
//                LinearLayout.LayoutParams fl=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//                frameLayout.setLayoutParams(fl);
//                schedule_ll.addView(frameLayout);
//              mv[j].imageButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //计时器按钮触发事件
//                        Intent intent=new Intent(getActivity(),Timer.class);
//                        startActivity(intent);
//                    }
//                });
//                mv[j].title.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //编辑按钮触发事件
//                        Log.v("触发","点击到了编辑按钮");
//                        Intent intent=new Intent(getActivity(),EditSchedule.class);
//                        startActivity(intent);
//                    }
//                });
                // mv[j].setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
//                frameLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    System.out.println("点击到了父控件");
//                    Intent intent=new Intent(getActivity(),EditSchedule.class);
//                    startActivity(intent);
//                }
//            });
                //frameLayout.setBackgroundResource(R.color.colorPrimaryDark);
//               mv[j].color();
                mv[j].onCreate();
                mv[j].setTag(j);
                schedule_ll.addView(mv[j]);
                //将ListView注册到Context Menu
                registerForContextMenu(mv[j]);
                cs.moveToNext();
            }
        }

        cs.close();
    }

    public void refresh() {
        schedule_ll.removeAllViews();
        create();
    }

    public void checkState() {
        for (int j = 0; j < i; j++) {
            mv[j].checkState();
        }
    }

    public void backState() {
        for (int j = 0; j < i; j++) {
            mv[j].backState();
        }
    }

    public void remove() {
        int a = i;
        for (int j = 0; j < a; j++) {
            if (mv[j].check_state == 1) {
                schedule_ll.removeView(mv[j]);
                factory.delete(id[j]);
                i--;

            } else {
                mv[j].backState();
            }
        }
    }

    public void markAll() {
        for (int j = 0; j < i; j++) {
            mv[j].marked();
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        /**
         * 这里使用的是代码添加弹出的菜单中内容
         * 也可通过getMenuInflater().inflate(R.menu.cmenu, menu)加载定义好的xml布局
         */
        int j= (int) v.getTag();
        menu.add(0, 3000+j*3, 1, "删除");
        menu.add(0, 3000+j*3+1, 2, "修改");
        menu.add(0,3000+j*3+2,3,"番茄钟");
        super.onCreateContextMenu(menu, v, menuInfo);

    }
    /*菜单选项时间设置*/
    public boolean onContextItemSelected(MenuItem item) {
        int a=item.getItemId()-3000;
        int j=a/3;
        int b=a%3;
                /**e`
                 * 删除操作
                 */
                if (b == 0){
                    Toast.makeText(getActivity(), "删除", Toast.LENGTH_SHORT).show();
                    schedule_ll.removeView(mv[j]);
                    factory.delete(id[j]);
                    refresh();
                }
                /**
                 * 修改操作
                 */
                if(b == 1){
                    Toast.makeText(getActivity(), "修改", Toast.LENGTH_SHORT).show();
                    int alarmtime[]=new int[5];
                    alarmtime = factory.getTime(time[j]);
                    openEditSchedule( content[j], note[j],ring[j] , state[j], 1 , 1 , alarmtime[0],alarmtime[1],
                            alarmtime[2], alarmtime[3], alarmtime[4]
                    );
                }
                /**
                 * 番茄钟操
                 */
                if(b ==2){
                    Toast.makeText(getActivity(), "番茄钟", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(),Timer.class);
                       startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }

    /*
    * 打开编辑界面*/
    public void openEditSchedule(  String content, String note,String ring ,
             String state,
             int classify ,
             int times ,
             int year,
             int month,
             int day,
             int hour,
             int minute
    ){
        Intent intent=new Intent();
        intent.setClass(getActivity(),EditSchedule.class);
        intent.putExtra("title",content);
        intent.putExtra("note",note);
        intent.putExtra("ring",ring);
        intent.putExtra("state",state);
        intent.putExtra("classify",classify);
        intent.putExtra("times",times);
        intent.putExtra("year",year);
        intent.putExtra("month",month);
        intent.putExtra("day",day);
        intent.putExtra("hour",hour);
        intent.putExtra("minute",minute);
        startActivity(intent);
    }
}
