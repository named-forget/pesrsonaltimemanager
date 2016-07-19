package com.dragongroup.personaltimemanager;
//显示新建日程页面的activity
//该类可以使用资源文件设置布局
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewSchedule extends AppCompatActivity {
    /*String content储存日程内容
     String note储存注释
    int[] time 储存时间。实际是包含6个元素的变量，即time[6],数组中元素从0-5分别代表年月日时分秒
    String ring闹钟铃声
    int classify日程分类
    int times 重复次数
    变量详情请参考文档 表结构.doc*/
    public String content;
    public String note;
    public int[] time;
    public  String ring;
    public int classify;
    public int times;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);
    }
}
