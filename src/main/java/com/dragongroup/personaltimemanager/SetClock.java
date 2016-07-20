package com.dragongroup.personaltimemanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.dragongroup.personaltimemanager.ClockActivity;

import java.util.Calendar;

/**
 * Created by 陈 on 2016/7/20 0020.
 */
public class SetClock {
    public void createClock(Context context,int year, int month, int day, int hour, int minute, String title, String text){
        AlarmManager alarmManager;
        PendingIntent pi;
        alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, ClockActivity.class);//通知传送ClockActivity类
        intent.putExtra("title",title);
        intent.putExtra("text",text);
        pi = PendingIntent.getActivity(context, 0, intent, 0);
        Calendar c = Calendar.getInstance();//此为闹钟时间
        Calendar cur = Calendar.getInstance();//获取系统时间
        cur.setTimeInMillis(System.currentTimeMillis());
        //设置时间
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1);
        c.set(Calendar.DAY_OF_MONTH,day);
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,minute);
        //如果设置时间小于系统，则设置时间失败
//        if(cur.getTimeInMillis()>c.getTimeInMillis())
//            Toast.makeText(MainActivity.this, "时间设置错误", Toast.LENGTH_SHORT).show();
//        else
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
    }
}
