package com.dragongroup.personaltimemanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class EditSchedule extends AppCompatActivity {
    private String content;
    private String note;
    private String ring ;
    private String state;
    private int classify ;
    private int times ;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_schedule);
        Intent getIntent = getIntent();
        content = getIntent.getStringExtra("title");
        note  = getIntent.getStringExtra("note");
        ring = getIntent.getStringExtra("ring");
        state = getIntent.getStringExtra("state");
        classify = getIntent.getIntExtra("classify",1);
        times = getIntent.getIntExtra("times",1);
        year = getIntent.getIntExtra("year",2016);
        month = getIntent.getIntExtra("month",7);
        day = getIntent.getIntExtra("day",30);
        hour = getIntent.getIntExtra("hour",00);
        minute = getIntent.getIntExtra("minute",00);
        backBtn = (Button) findViewById(R.id.editSchedule_backBtn);
        saveBtn = (Button) findViewById(R.id.editSchedule_saveBtn);
        setTimeBtn = (Button) findViewById(R.id.editSchedule_setTimeBtn);
        ringOnTimeBtn = (ToggleButton) findViewById(R.id.editSchedule_ring_On_Time);
        ringOnTimeBtn.setTextOn(getString(R.string.RemindOnTimeOn));
        ringOnTimeBtn.setTextOff(getString(R.string.RemindOnTimeOff));
        setDateBtn = (Button) findViewById(R.id.editSchedule_setDate);
        setRing = (Button) findViewById(R.id.editSchedule_setRingstone);
        sortTag = (Spinner) findViewById(R.id.editSchedule_sortTag);
        contentText = (EditText) findViewById(R.id.editSchedule_contentText);
        noteText=(EditText)findViewById(R.id.editSchedule_note);
        strFolder = Environment.getExternalStorageDirectory().getPath() + "/Music/Subat";
        contentText.setText(content);
        noteText.setText(note);
        setTimeBtn.setText("" + hour + ":" + minute);
        switch (state){
            case "未完成":
                ringOnTimeBtn.setChecked(true);

                break;
            case "完成":
                ringOnTimeBtn.setChecked(false);

                break;
            default:
                break;

        }
        String dt = year + "." + month + "." + day;
        setDateBtn.setText(dt);
        switch (classify) {
            case 1:
                sortTag.setSelection(0, true);
                break;
            case 2:
                sortTag.setSelection(1, true);
                break;
            case 3:
                sortTag.setSelection(2, true);
                break;
            default:
                break;
        }
        String rt=getString(R.string.chooseringston)+"\n"+ring;
        setRing.setText(rt);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束当前界面
                EditSchedule.this.finish();
            }
        });
        /*
                保存按钮事件
                        */
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(NewSchedule.this, ""+(contentText.getText().toString().equals("")), Toast.LENGTH_SHORT).show();
                if (contentText.getText().toString().equals("")) {
                    Toast.makeText(EditSchedule.this, getString(R.string.nullcontentWaring), Toast.LENGTH_SHORT).show();

                }

                else {
                    content = contentText.getText().toString();
                    note = noteText.getText().toString();
//                    time[0] = year;
//                    time[1] = month;
//                    time[2] = day;
//                    time[3] = hour;
//                    time[4] = minute;
//                    time[5] = 0;
                    remindDialog(v);

                }
                //  remindDialog();

            }

        });

        /*
               设置时间按钮事件
               */
        setTimeBtn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                             // Calendar c = Calendar.getInstance();
                                              new TimePickerDialog(EditSchedule.this,
                                                      new TimePickerDialog.OnTimeSetListener() {
                                                          @Override
                                                          public void onTimeSet(TimePicker view, int hour, int minute) {
                                                              // 讲设定时间Button.text改为显示选择时间：分钟格式
                                                              setTimeBtn.setText("" + hour + ":" + minute);
                                                              EditSchedule.this.hour = hour;EditSchedule.this.minute= minute;

                                                              Toast.makeText(EditSchedule.this,""+hour+";"+minute,Toast.LENGTH_LONG).show();
                                                              setTimeBtn.setText("" + hour + ":" + minute);
                                                          }
                                                      },
                                                      hour,
                                                      day, true
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
                                                                 state = "未完成";
                                                                 ringOnTimeBtn.setTextOn(getString(R.string.RemindOnTimeOn));
                                                                 ringOnTimeBtn.setBackgroundColor(Color.BLUE);
                                                                 Toast.makeText(EditSchedule.this,getString(R.string.RemindOnTimeOn), Toast.LENGTH_SHORT).show();                                                    /*
                                                             /*
                                                             可加入不选择和选择时的不同按钮效果
                                                              */

                                                             }
                                                             else {
                                                                 state = "完成";
                                                                 ringOnTimeBtn.setBackgroundColor(Color.RED);
                                                                 ringOnTimeBtn.setTextOff(getString(R.string.RemindOnTimeOff));
                                                                 Toast.makeText(EditSchedule.this, getString(R.string.RemindOnTimeOn), Toast.LENGTH_SHORT).show();
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
                                              Calendar c = Calendar.getInstance();
                                              new DatePickerDialog(EditSchedule.this, new DatePickerDialog.OnDateSetListener() {
                                                  @Override
                                                  public void onDateSet(DatePicker view, int year, int month, int day) {
                                                      // TODO Auto-generated method stub
                                                      EditSchedule.this.year = year;
                                                      EditSchedule.this.month= month;
                                                      EditSchedule.this.day = day;
                                                  }
                                              },year,month,day).show();
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
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getString(R.string.chooseringston));
                    startActivityForResult(intent, 1);

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
                        //Toast.makeText(EditSchedule.this,""+position,Toast.LENGTH_SHORT).show();
                        break;
                    case "休闲":
                        classify = 2;
                        //Toast.makeText(EditSchedule.this,""+position,Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        classify = 3;
                        //Toast.makeText(EditSchedule.this,""+position,Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {

            return;
        } else if (requestCode == 1) {
            //Toast.makeText(NewSchedule.this,"Bingo",Toast.LENGTH_SHORT).show();
            try {

                //得到我们选择的铃声
                Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                //将我们选择的铃声选择成默认Media_RingTongActivity
                if (pickedUri != null) {
                    //Toast.makeText(NewSchedule.this,""+ring,Toast.LENGTH_SHORT).show();
                    // RingtoneManager.setActualDefaultRingtoneUri(NewSchedule.this, RingtoneManager.TYPE_RINGTONE, pickedUri);
                    RingtoneManager.setActualDefaultRingtoneUri(EditSchedule.this,RingtoneManager.TYPE_RINGTONE,pickedUri);//加WRITE_SETTINGS权限
                    Ringtone ringtone =  RingtoneManager.getRingtone(EditSchedule.this, pickedUri);
                    ring = ringtone.getTitle(EditSchedule.this);
                    //ringtone.play();
                    Toast.makeText(EditSchedule.this,""+ring,Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(EditSchedule.this,"异常"+e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void remindDialog(View v){



        AlertDialog.Builder builder = new AlertDialog.Builder(EditSchedule.this)
                //标题
                //  .setTitle("提示")
                //内容
                .setMessage("已修改！");
        //确认按钮
        setPosiviveButton(builder)
                .create()
                .show();
    }
    private AlertDialog.Builder setPosiviveButton(AlertDialog.Builder builder)
    {
        return builder.setPositiveButton("确定",  new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(EditSchedule.this,"已修改",Toast.LENGTH_SHORT).show();
                 EditSchedule.this.finish();
            }
        });
    }
}
