package com.dragongroup.personaltimemanager;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by 何宏华 on 2016/7/17.
 */
public class MyView extends RelativeLayout {
    private Switch sw;//标记日程是否完成的开关
    private TextView title;//日程内容
    private TextView time;//日程时间
    private CheckBox checkBox;//标记选项，用于多选删除
    public MyView(Context context,Switch sw, TextView title, TextView time,CheckBox checkBox){
        super(context);
        this.checkBox=checkBox;
        this.sw=sw;
        this.title=title;
        this.time=time;

    };
    //创建一个日程的条目，包含两个textView,一个switch,swtich要添加监听器，控制日程开关
    public void onCreate(){//函数已有内容仅用于测试，方便了解函数实现原理
        title.setText("测试");
        this.addView(title);
    }
    //改为标记状态:将swtich从条目中移除，并在原来switch的位置加入一个CheckBox。
    public void checkState(){

    }
}
