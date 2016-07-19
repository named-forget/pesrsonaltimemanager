package com.dragongroup.personaltimemanager;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 何宏华 on 2016/7/17.
 */
public class MyView extends RelativeLayout {
    private Switch sw;//标记日程是否完成的开关
    private TextView title;//日程内容
    private TextView time;//日程时间
    private CheckBox checkBox;//标记选项，用于多选删除
//    private String str_title;
//    private String str_time;
//    private String str_state;
    int title_id;
    public MyView(Context context,Switch sw, TextView title, TextView time,CheckBox checkBox){
        super(context);
        this.checkBox=checkBox;
        this.sw=sw;
        this.title=title;
        this.time=time;
//        this.str_state=str_state;
//        this.str_time=str_time;
//        this.str_title=str_title;

    }
    //创建一个日程的条目，包含两个textView,一个switch,swtich要添加监听器，控制日程开关
    public void onCreate(){//函数已有内容仅用于测试，方便了解函数实现原理
        title.setEms(8);
        title.setTextSize(27);
        title.setId(View.generateViewId());
        title_id = title.getId();
        time.setTextSize(17);
        sw.setId(View.generateViewId());
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getContext(), isChecked + "",
                        Toast.LENGTH_SHORT).show();
            }
        });
        RelativeLayout.LayoutParams lp1=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lp1.addRule(RelativeLayout.BELOW,title_id);
        RelativeLayout.LayoutParams lp2=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,title_id);
        lp2.addRule(RelativeLayout.CENTER_IN_PARENT,-1);
        sw.setLayoutParams(lp2);
        time.setLayoutParams(lp1);
        this.setBackgroundResource(R.drawable.background);
        this.addView(title);
        this.addView(time);
        this.addView(sw);


    }
    //改为标记状态:将swtich从条目中移除，并在原来switch的位置加入一个CheckBox。
    public void checkState(){
        this.removeView(sw);
        RelativeLayout.LayoutParams lp3=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lp3.addRule(RelativeLayout.RIGHT_OF ,title_id);
        lp3.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        checkBox.setLayoutParams(lp3);
        checkBox.setText(null);
        this.addView(checkBox);
    }

}
