package com.dragongroup.personaltimemanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

/**
 * Created by 何宏华 on 2016/7/17.
 */
public class Factory {
    //创建数据库
    public SQLiteDatabase mDataBase;//数据库对象
    public boolean createDB(){
        return true;
    }
    //创建表格
    public boolean createTable(){
        return true;
    }
    //int[] time 保存时间。实际是包含6个元素的变量，即time[6],数组中元素从0-5分别代表年月日时分秒
    //其余变量参考文档 表结构.doc
    public boolean insert(int id, String content, String note, int[] time,String ring,int classify,String state,int times){
        return false;
    }
    //删除表中数据，按id
    public boolean delete(int id){
        return  true;
    }
    //更新表中数据 name为字段名，value为修改后得值
    public boolean update(String name,String value){
        return true;
    }
    //查询，返回一个Cursor
    public Cursor selectALl(){
        return null;//这里写的时候记得改掉>_<
    }
    //条件查询，返回Cursor,
    //name是字段名，value是值
    public Cursor select(String name,String value){
        return null;//这里写完也是要改的
    }
}
