package com.dragongroup.personaltimemanager;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Chronometer;
import android.os.SystemClock;
import android.widget.Toast;

public class Timer extends AppCompatActivity {
    private TimePicker timePicker;
    private Button startBtn;
    private Button stopBtn;
    private Button resetBtn;
    private NumberPicker hourPicker;
    private NumberPicker minutePicker;
    private NumberPicker secondPicker;
    private Chronometer chronometer;
    private int picked_hour;
    private int picked_minute;
    private  int picked_second;
    private Long time;
    private int savedTime[];
    private boolean isStopBtnClicked =false;
    private boolean isStartBtnClicked =false;
    private boolean isTimeChoosed =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        // timePicker=(TimePicker)findViewById(R.id.timePicker);
        hourPicker=(NumberPicker)findViewById(R.id.hourPicker);
        minutePicker=(NumberPicker)findViewById(R.id.minutePicker);
        secondPicker=(NumberPicker)findViewById(R.id.secondPicker);
        startBtn=(Button)findViewById(R.id.startBtn);
        stopBtn=(Button)findViewById(R.id.stopBtn);
        resetBtn=(Button)findViewById(R.id.resetBtn) ;
        chronometer=(Chronometer)findViewById(R.id.chronometer);
        savedTime=new int[3];
        hourPicker.setMinValue(0);
        minutePicker.setMinValue(0);
        secondPicker.setMinValue(0);
        hourPicker.setMaxValue(60);
        minutePicker.setMaxValue(60);
        secondPicker.setMaxValue(60);
        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                picked_hour =newVal;
                isTimeChoosed =true;
                //startBtn.setText("");
            }
        });
        minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                picked_minute =newVal;
                isTimeChoosed =true;
            }
        });
        secondPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                picked_second =newVal;
                isTimeChoosed =true;
            }
        });
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isStopBtnClicked){
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    isStartBtnClicked =true;
                    startBtn.setEnabled(false);
                }
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(isStartBtnClicked) {
                    startBtn.setEnabled(false);
                    switch (stopBtn.getText().toString()){
                        case "停止":
                            chronometer.stop();
                            time=SystemClock.elapsedRealtime()- chronometer.getBase();
                            save_Timertime(time);
                            isStopBtnClicked = true;
                            stopBtn.setText("继续");
                            isStartBtnClicked=true;
                            // time=SystemClock.elapsedRealtime()- chronometer.getBase();
                            break;
                        case "继续":
                            chronometer.setBase(SystemClock.elapsedRealtime()-time);
                            chronometer.start();
                            stopBtn.setText("停止");
                            //isStartBtnClicked=false;
                            break;
                        default:
                            break;
                    }
                }

                //  Toast.makeText(Timer.this,""+time,Toast.LENGTH_SHORT).show();
            }


        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStopBtnClicked =false;
                isStartBtnClicked =false;
                startBtn.setText("开始计时");
                startBtn.setEnabled(true);
                chronometer.stop();

                chronometer.setBase(SystemClock.elapsedRealtime());
            }
        });

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (isTimeChoosed) {
                    if (SystemClock.elapsedRealtime() - chronometer.getBase() > (picked_hour * 60 * 60 * 1000 + picked_minute * 60 * 1000 + picked_second * 1000)) {
                        chronometer.stop();
                        remindDialog();


                    }

                }
            }
        });
    }
    private void remindDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Timer.this)
                //标题
                //  .setTitle("提示")
                //内容
                .setMessage("时间到！");
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
                //  Toast.makeText(Timer.this,"已创建",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void save_Timertime(Long time){
        long second,minute,real_minute,real_hour;
        second=time/1000;
        savedTime[2]=new Long(second%60).intValue();
        minute=second/60;
        savedTime[1]=new Long(minute%60).intValue();
        savedTime[0]=new Long(minute/60).intValue();
        Toast.makeText(Timer.this,"时间：0"+savedTime[0]+":"+savedTime[1]+":"+savedTime[2],Toast.LENGTH_SHORT).show();



    }


}



