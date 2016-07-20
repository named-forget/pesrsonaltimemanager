package com.dragongroup.personaltimemanager;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.dragongroup.personaltimemanager.MainActivity;
/**
 * Created by 何宏华 on 2016/7/17.
 */
public class MyView extends RelativeLayout {
    private Switch sw;//标记日程是否完成的开关
    private TextView title;//日程内容
    private TextView time;//日程时间
    private CheckBox checkBox;//标记选项，用于多选删除
    public ImageButton imageButton;
    public  Button editBt;
    private int id;
    private int imageButton_id;
    //public int sw_state;//记录Swith的开关状态  0为关，1为开
    public int check_state=0;//记录checkbox 的选中状态  0为未选中，1为选中
    int title_id;
    public MyView(Context context,Switch sw, TextView title, TextView time,CheckBox checkBox,int id){
        super(context);
        this.checkBox=checkBox;
        this.sw=sw;
        this.title=title;
        this.time=time;
        this.id=id;
        imageButton=new ImageButton(getContext());
        editBt=new Button(getContext());
//        this.str_state=str_state;
//        this.str_time=str_time;
//        this.str_title=str_title;

    }
    //创建一个日程的条目，包含两个textView,一个switch,swtich要添加监听器，控制日程开关
    public void onCreate(){//函数已有内容仅用于测试，方便了解函数实现原理
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);

        this.requestFocus();
        this.requestFocusFromTouch();
      this.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View view) {
              click();
          }
      });
       init();

    }
    public void init(){
        this.removeAllViews();
        addtitle();
        addtime();
        addsw();
    }
    //改为标记状态:将swtich从条目中移除，并在原来switch的位置加入一个CheckBox。
    public void checkState(){
        this.removeView(sw);
        addCheckBox();
    }
    public void backState(){
        this.removeView(checkBox);
         addsw();
    }
    public void marked(){
        checkBox.setChecked(true);
        check_state=1;
    }
    public void addtitle(){
        title.setEms(8);
        title.setTextSize(27);
        title.setId(View.generateViewId());
        title_id = title.getId();
        this.addView(title);
    }
    public void addtime(){
        time.setTextSize(17);
        RelativeLayout.LayoutParams lp1=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lp1.addRule(RelativeLayout.BELOW,title_id);
        this.addView(time,lp1);
    }
    public void addsw(){
        RelativeLayout.LayoutParams lp2=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,title_id);
        lp2.addRule(RelativeLayout.CENTER_IN_PARENT,-1);
        sw.setLayoutParams(lp2);
        this.addView(sw);
    }public void addCheckBox(){
        RelativeLayout.LayoutParams lp3=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lp3.addRule(RelativeLayout.RIGHT_OF ,title_id);
        lp3.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        checkBox.setLayoutParams(lp3);
        checkBox.setText(null);
        checkBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    check_state=1;
                }else{
                    check_state=0;
                }
            }
        });
        this.addView(checkBox);
    }
    public void click(){
        this.removeAllViews();
        addtitle();
        addtime();
        addImageButton();
        addeditBt();
    }
    public void lostfocus(){
        sw.setFocusable(false);
        sw.setFocusableInTouchMode(false);
    }
    public void addImageButton(){
        imageButton_id=imageButton.getId();
        RelativeLayout.LayoutParams lp2=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,title_id);
        lp2.addRule(RelativeLayout.CENTER_IN_PARENT,-1);
        imageButton.setLayoutParams(lp2);
        this.addView(imageButton);
    }
    public void addeditBt(){
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.LEFT_OF,imageButton_id);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT,-1);
        this.addView(editBt,lp);
    }
}
