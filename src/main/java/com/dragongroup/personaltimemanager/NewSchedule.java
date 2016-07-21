package com.dragongroup.personaltimemanager;
//显示新建日程页面的activity
//该类可以使用资源文件设置布局
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.io.File;
import java.util.Calendar;

public class NewSchedule extends AppCompatActivity {
    /*String content储存日程内容
     String note储存注释
    int[] time 储存时间。实际是包含6个元素的变量，即time[6],数组中元素从0-5分别代表年月日时分秒
    String ring闹钟铃声
    int classify日程分类
    int times 重复次数
    bool ringOnTime
    变量详情请参考文档 表结构.doc*/
    private String content;
    private String note = "无";
    private int[] time = new int[6];
    private String ring = "123";
    private int classify = 1;
    private int times = 1;
    private String state = "未完成";
    // public boolean ringOnTime;
    private String strFolder;//铃声所在位置路径
    private Button backBtn;
    private Button saveBtn;
    private Button setTimeBtn;
    private ToggleButton ringOnTimeBtn;
    private Button setDateBtn;
    private Button setRing;
    private Spinner sortTag;
    private EditText contentText;
    private EditText noteText;
    private  Calendar c;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);
        backBtn = (Button)findViewById(R.id.backBtn);
        saveBtn = (Button)findViewById(R.id.saveBtn);
        setTimeBtn = (Button) findViewById(R.id.setTimeBtn);
        ringOnTimeBtn = (ToggleButton)findViewById(R.id.ring_On_Time);
        setDateBtn = (Button)findViewById(R.id.setDate);
        setRing = (Button) findViewById(R.id.setRingstone);
        sortTag = (Spinner) findViewById(R.id.sortTag);
        contentText = (EditText) findViewById(R.id.contentText);
        noteText = (EditText) findViewById(R.id.note);
        strFolder = Environment.getExternalStorageDirectory().getPath() + "/Music";
        c=Calendar.getInstance();
        year=c.get(Calendar.YEAR);
        month=c.get(Calendar.MONTH)+1;
        day=c.get(Calendar.DAY_OF_MONTH)+1;
        hour= c.get(Calendar.HOUR_OF_DAY);
        minute=c.get(Calendar.MINUTE);

        /*
        返回按钮事件
         */

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束当前界面
            NewSchedule.this.finish();
            }
        });
        /*
                保存按钮事件
                        */
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentText.getText().toString().equals("")) {
                    Toast.makeText(NewSchedule.this, getString(R.string.nullcontentWaring), Toast.LENGTH_SHORT).show();

                }
                else {
                    content = contentText.getText().toString();
                    note = noteText.getText().toString();
                    time[0] = year;
                    time[1] = month;
                    time[2] = day;
                    time[3] = hour;
                    time[4] = minute;
                    time[5] = 0;
                    Factory factory = new Factory(NewSchedule.this);
                    factory.insert(content, note, time, ring, classify, state, times);
                    factory.close();
                    remindDialog(v);
                    //发送广播给MainAcitivity，在MainAcitivity 中调用Schedule.refresh（）刷新界面
                    Intent intent=new Intent("finish");
                    sendBroadcast(intent);
                }
            }
        });

        /*
               设置时间按钮事件
               */
        setTimeBtn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                           //   Calendar c = Calendar.getInstance();
                                              new TimePickerDialog(NewSchedule.this,
                                                      new TimePickerDialog.OnTimeSetListener() {
                                                          @Override
                                                          public void onTimeSet(TimePicker view, int hour, int minute) {
                                                              // 讲设定时间Button.text改为显示选择时间：分钟格式
                                                              setTimeBtn.setText("" + hour + ":" + minute);
                                                              NewSchedule.this.hour = hour;
                                                              NewSchedule.this.minute = minute;
                                                              Toast.makeText(NewSchedule.this, "" + hour + ";" + minute, Toast.LENGTH_LONG).show();
                                                              setTimeBtn.setText("" + hour + ":" + minute);
                                                          }
                                                      },
                                                      c.get(Calendar.HOUR_OF_DAY),
                                                      c.get(Calendar.MINUTE), true
                                              ).show();
                                          }
                                      }
        );

        /*
           定义准点提醒事件
         */

        ringOnTimeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                         if (isChecked) {
                                                             // ringOnTime = true;
                                                             ringOnTimeBtn.setTextOn(" 准时提醒");
                                                             state = "未完成";
                                                             ringOnTimeBtn.setBackgroundColor(Color.BLUE);
                                                             Toast.makeText(NewSchedule.this, "准时提醒", Toast.LENGTH_SHORT).show();                                                    /*
                                                             /*
                                                             可加入不选择和选择时的不同按钮效果
                                                              */

                                                         } else {
                                                             ringOnTimeBtn.setText("不提醒");
                                                             state = "完成";
                                                             ringOnTimeBtn.setBackgroundColor(Color.RED);
                                                             ringOnTimeBtn.setTextOff("不提醒");
                                                             Toast.makeText(NewSchedule.this, "不准时提醒", Toast.LENGTH_SHORT).show();
                                                             // ringOnTime = false;
                                                         }
                                                     }
                                                 }
        );
        /*
        设置日期事件
         */

        setDateBtn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                            //  Calendar c = Calendar.getInstance();
                                              new DatePickerDialog(NewSchedule.this, new DatePickerDialog.OnDateSetListener() {
                                                  @Override
                                                  public void onDateSet(DatePicker view, int year, int month, int day) {
                                                      // TODO Auto-generated method stub
                                                      NewSchedule.this.year = year;
                                                      NewSchedule.this.month = month + 1;
                                                      NewSchedule.this.day = day;
                                                      String dt = year + "." + month + "." + day;
                                                      setDateBtn.setText(dt);
                                                  }
                                              }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                                                      c.get(Calendar.DAY_OF_MONTH)).show();
                                          }
                                      }
        );


        /*
        定义设置铃声事件
         */

        setRing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFolder(strFolder)) {
                    //启动铃声选择界面intent
                    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    // intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALL);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "选择铃声");
                    startActivityForResult(intent, 1);

                } else {

                    Toast.makeText(NewSchedule.this, "文件夹为空\n" + strFolder, Toast.LENGTH_SHORT).show();

                }
            }
        });



        /*
             获取分类标签值
         */
        sortTag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = sortTag.getSelectedItem().toString();
                /*
                classify 分类 限定只能为为1(工作)、2（休闲）、3（其他）
                 */
                switch (str) {
                    case "工作":
                        classify = 1;
                        Toast.makeText(NewSchedule.this, "" + classify, Toast.LENGTH_SHORT).show();
                        break;
                    case "休闲":
                        classify = 2;
                        Toast.makeText(NewSchedule.this, "" + classify, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        classify = 3;
                        Toast.makeText(NewSchedule.this, "" + classify, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*
                检查该路径是否有音频文件
         */
    }

    private boolean isFolder(String strFolder) {
        boolean tmp = false;

        File f1 = new File(strFolder);
        if (!f1.exists()) {
            if (f1.mkdirs()) {
                tmp=false;

            }
            else {
                tmp =true;
            }
        } else {
            tmp = true;
        }

        return tmp;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode !=-AppCompatActivity.RESULT_OK) {
            return;
        } else if (requestCode == 1) {
            //Toast.makeText(NewSchedule.this,"Bingo",Toast.LENGTH_SHORT).show();
            try {

                //得到我们选择的铃声
                Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);//铃声文件绝对路径
                //将我们选择的铃声选择成默认Media_RingTongActivity
                if (pickedUri != null) {
                    //Toast.makeText(NewSchedule.this,""+ring,Toast.LENGTH_SHORT).show();
                   // RingtoneManager.setActualDefaultRingtoneUri(NewSchedule.this, RingtoneManager.TYPE_RINGTONE, pickedUri);
                    RingtoneManager.setActualDefaultRingtoneUri(NewSchedule.this,RingtoneManager.TYPE_RINGTONE,pickedUri);//加WRITE_SETTINGS权限
                    Ringtone ringtone =  RingtoneManager.getRingtone(NewSchedule.this, pickedUri);
                    ring = ringtone.getTitle(NewSchedule.this);
                    //ringtone.play();
                    Toast.makeText(NewSchedule.this,""+ring,Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(NewSchedule.this,"异常"+e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public  void remindDialog(View v){



        AlertDialog.Builder builder = new AlertDialog.Builder(NewSchedule.this)
                //标题
                //  .setTitle("提示")
                //内容
                .setMessage("已创建新日程");
        //确认按钮
        setPosiviveButton(builder)
                .create()
                .show();
    }
    private AlertDialog.Builder setPosiviveButton(
            AlertDialog.Builder builder)
    {
        return builder.setPositiveButton("确定",  new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(NewSchedule.this,"已创建",Toast.LENGTH_SHORT).show();
                NewSchedule.this.finish();
            }
        });
    }
}