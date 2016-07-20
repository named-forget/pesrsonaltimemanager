package com.dragongroup.personaltimemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;

import com.dragongroup.personaltimemanager.R;

/**
 * Created by 陈 on 2016/7/20 0020.
 */
public class ClockActivity extends Activity{
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        mediaPlayer = MediaPlayer.create(this, R.raw.test);//调用媒体播放器播放闹铃
        //mediaPlayer.setLooping(true);
        mediaPlayer.start();

        //创建一个闹钟提醒的对话框,点击确定关闭铃声与页面
        Intent getIntent = getIntent();
        String title = getIntent.getStringExtra("title");
        String text = getIntent.getStringExtra("text");
        new AlertDialog.Builder(ClockActivity.this).setTitle(title).setMessage(text)
        .setPositiveButton("关闭闹铃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediaPlayer.stop();
                ClockActivity.this.finish();
            }
        }).show();
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("标题");
//        builder.setMessage("内容");
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                mediaPlayer.stop();
//                ClockActivity.this.finish();
//            }
//         });
//        builder.setCancelable(true);
//        AlertDialog dialog = builder.create();
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//指定会全局,可以在后台弹出
//        //dialog.setCanceledOnTouchOutside(false);
//        dialog.show();


    }

}
