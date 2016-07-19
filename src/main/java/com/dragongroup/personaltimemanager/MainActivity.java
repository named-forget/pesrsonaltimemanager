package com.dragongroup.personaltimemanager;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.graphics.Color;
import android.print.PrinterCapabilitiesInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.app.Fragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Schedule schedule;//schedule Fragment的实现
    private Repeat repeat;//Repeat Fragment的实现
    private Recovery recovery;//Recovery Fragment的实现
    private Setting setting;// Setting Fragment的实现
    //消息界面布局
    private View messageLayout;
    //联系人界面布局
    private View contactsLayout;
    //动态界面布局
    private View newsLayout;
    //动态界面布局
    private View settingLayout;
    //在Tab布局上显示消息图标的控件
    private ImageView messageImage;
    //在Tab布局上显示联系人图标的控件
    private ImageView contactsImage;
    //在Tab布局上显示动态图标的控件
    private ImageView newsImage;
    //在Tab布局上显示设置图标的控件
    private ImageView settingImage;
    //在Tab布局上显示消息标题的控件
    private TextView messageText;
    //在Tab布局上显示联系人标题的控件
    private TextView contactsText;
    //在Tab布局上显示联系人标题的控件
    private TextView newsText;
    //在Tab布局上显示联系人标题的控件
    private TextView settingText;
    //用于对Fragment进行管理
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        fragmentManager=getFragmentManager();
        setTabSelection(0);

    }
    private void initViews() {
        messageLayout = findViewById(R.id.message_layout);
        contactsLayout = findViewById(R.id.contacts_layout);
        newsLayout = findViewById(R.id.news_layout);
        settingLayout = findViewById(R.id.setting_layout);
        messageImage = (ImageView) findViewById(R.id.message_image);
        contactsImage = (ImageView) findViewById(R.id.contacts_image);
        newsImage = (ImageView) findViewById(R.id.news_image);
        settingImage = (ImageView) findViewById(R.id.setting_image);
        messageText = (TextView) findViewById(R.id.message_text);
        contactsText = (TextView) findViewById(R.id.contacts_text);
        newsText = (TextView) findViewById(R.id.news_text);
        settingText = (TextView) findViewById(R.id.setting_text);
        messageLayout.setOnClickListener(this);
        contactsLayout.setOnClickListener(this);
        newsLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_layout:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.contacts_layout:
                // 当点击了联系人tab时，选中第2个tab
                setTabSelection(1);
                break;
            case R.id.news_layout:
                // 当点击了动态tab时，选中第3个tab
                setTabSelection(2);
                break;
            case R.id.setting_layout:
                // 当点击了设置tab时，选中第4个tab
                setTabSelection(3);
                break;
            default:
                break;
        }
    }
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                messageImage.setImageResource(R.drawable.message_selected);
                messageText.setTextColor(Color.WHITE);
                if (schedule == null) {
                    // 如果Schedule为空，则创建一个并添加到界面上
                    schedule = new Schedule();
                    transaction.add(R.id.content, schedule);
                } else {
                    // 如果Schedule不为空，则直接将它显示出来
                    transaction.show(schedule);
                }
//			TextView tv1=new TextView(this);
//			tv1.setText("测试");
//			ll.addView(tv1);
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                contactsImage.setImageResource(R.drawable.contacts_selected);
                contactsText.setTextColor(Color.WHITE);
                if (repeat == null) {
                    // 如果Repeat为空，则创建一个并添加到界面上
                    repeat = new Repeat();
                    transaction.add(R.id.content, repeat);
                } else {
                    // 如果Repeat不为空，则直接将它显示出来
                    transaction.show(repeat);
                }
                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                newsImage.setImageResource(R.drawable.news_selected);
                newsText.setTextColor(Color.WHITE);
                if (recovery == null) {
                    // 如果Recovery为空，则创建一个并添加到界面上
                    recovery = new Recovery();
                    transaction.add(R.id.content, recovery);
                } else {
                    // 如果Recovery不为空，则直接将它显示出来
                    transaction.show(recovery);
                }
                break;
            case 3:
            default:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                settingImage.setImageResource(R.drawable.setting_selected);
                settingText.setTextColor(Color.WHITE);
                if (setting == null) {
                    // 如果Setting为空，则创建一个并添加到界面上
                    setting = new Setting();
                    transaction.add(R.id.content, setting);
                } else {
                    // 如果Setting不为空，则直接将它显示出来
                    transaction.show(setting);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        messageImage.setImageResource(R.drawable.message_unselected);
        messageText.setTextColor(Color.parseColor("#82858b"));
        contactsImage.setImageResource(R.drawable.contacts_unselected);
        contactsText.setTextColor(Color.parseColor("#82858b"));
        newsImage.setImageResource(R.drawable.news_unselected);
        newsText.setTextColor(Color.parseColor("#82858b"));
        settingImage.setImageResource(R.drawable.setting_unselected);
        settingText.setTextColor(Color.parseColor("#82858b"));
    }
    private void hideFragments(FragmentTransaction transaction) {
        if (schedule != null) {
            transaction.hide(schedule);
        }
        if (repeat != null) {
            transaction.hide(repeat);
        }
        if (recovery != null) {
            transaction.hide(recovery);
        }
        if (setting != null) {
            transaction.hide(setting);
        }
    }
}
