package com.dragongroup.personaltimemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 何宏华 on 2016/7/17.
 */
public class Factory{
    private static final String DATABASE_NAME = "my_db";
    private static final String DATABASE_TABLE ="Schedule";

    public SQLiteDatabase mDataBase;//数据库对象

    //创建数据库
    public boolean createDB(Context context)
    {
        try{
            mDataBase= context.openOrCreateDatabase(DATABASE_NAME,SQLiteDatabase.CREATE_IF_NECESSARY,null);
            System.out.println("create database succeed");
            return true;
        }catch(SQLException e) {
            System.out.println("create database fail");
            e.printStackTrace();
            return false;
        }
    }

    //创建表格
    public boolean createTable()
    {
        try{
            Cursor cursor=mDataBase.query(DATABASE_TABLE,null,null,null,null,null,null);
            cursor.close();
            System.out.println("table exist");
            return true;
        }catch(SQLException e1) {
            try{
                String sql="create table " + DATABASE_TABLE + " ( " +
                        "id integer primary key autoincrement, " +
                        "content varchar2(100) not null, " +
                        "note varchar2(30) default '无', " +
                        "createTime date not null default (datetime('now','localtime')), " +
                        "time date not null, " +
                        "ring varchar2(20) not null default '123', " +
                        "classify integer not null, " +
                        "state varchar2(5) not null, " +
                        "times integer not null" +
                        " );";
                mDataBase.execSQL(sql);
                System.out.println("create table succeed");
                return true;
            }catch(SQLException e2) {
                System.out.println("create table fail");
                e2.printStackTrace();
                return false;
            }
        }
    }

    //int[] time 保存时间。实际是包含6个元素的变量，即time[6],数组中元素从0-5分别代表年月日时分秒
    //其余变量参考文档 表结构.doc
    public boolean insert(String content, String note, int[] time,String ring,int classify,String state,int times)
    {
        //ContentValue是用来存储插入信息的容器，put用来填充容器
        ContentValues values = new ContentValues();
        values.put("content", content);
        values.put("note", note);
        String timeStr=""+time[0]+"-"+
                ((time[1]/10==0)?"0":"")+time[1]+"-"+
                ((time[2]/10==0)?"0":"")+time[2]+" "+
                ((time[3]/10==0)?"0":"")+time[3]+":"+
                ((time[4]/10==0)?"0":"")+time[4]+":"+
                ((time[5]/10==0)?"0":"")+time[5];
        values.put("time",timeStr);
        values.put("ring",ring);
        values.put("classify",classify);
        values.put("state",state);;
        values.put("times",times);

        try{
            mDataBase.insert(DATABASE_TABLE,null,values);
            System.out.println("insert succeed");
            return true;
        }catch(SQLException e) {
            System.out.println("insert fail");
            e.printStackTrace();
            return false;
        }
    }

    //删除表中数据，按id
    public boolean delete(int id)
    {
        try{
            mDataBase.delete(DATABASE_TABLE,"id=?",new String[]{""+id});
            System.out.println("delete succeed");
            return true;
        }catch(SQLException e) {
            System.out.println("delete fail");
            e.printStackTrace();
            return false;
        }
    }

    //更新表中数据 name为字段名，value为修改后得值
    public boolean update(int id, String name,String value)
    {
        try{
            ContentValues values = new ContentValues();
            values.put(name, value);
            mDataBase.update(DATABASE_TABLE,values,"id=?",new String[]{""+id});
            System.out.println("update succeed");
            return true;
        }catch(SQLException e){
            System.out.println("update fail");
            e.printStackTrace();
            return false;
        }
    }

    //查询，返回一个Cursor
    public Cursor selectAll()
    {
        Cursor cursor =null;
        try{
            cursor=mDataBase.query(DATABASE_TABLE,null,null,null,null,null,null);
            System.out.println("selectAll succeed");
        }catch(SQLException e){
            System.out.println("selectAll fail");
            e.printStackTrace();
        }finally {
            return cursor;
        }
    }

    //条件查询，返回Cursor,
    //name是字段名，operator是运算符，value是值
    public Cursor select(String name,String operator,String value)
    {
        Cursor cursor =null;
        try{
            cursor=mDataBase.query(DATABASE_TABLE,null,name+operator+"?",new String[]{value},null,null,null);
            System.out.println("select succeed");
        }catch(SQLException e){
            System.out.println("select fail");
            e.printStackTrace();
        }finally {
            return cursor;
        }
    }

    // drop the table
    public void dropTable()
    {
        String sql="drop table " + DATABASE_TABLE;
        try{
            mDataBase.execSQL(sql);
            System.out.println("drop table succeed");
        }catch(SQLException e)
        {
            System.out.println("drop table fail");
            e.printStackTrace();
        }
    }

    // close database, must be called after using
    public void close()
    {
        mDataBase.close();
    }

    // used for testing/debugging
    public void test(Context context)
    {
        if(createDB(context))
        {
            if(createTable())
            {
                if(insert("fuck","damn",new int[]{2016,7,18,14,5,6},"ring",1,"state",2))
                {
                    Cursor cursor1=selectAll();
                    cursor1.moveToFirst();  // use this function before fetching content
                    System.out.println("testing cursor:"+cursor1.getString(3));
                    cursor1.close();    // close cursor after using

                    update(1,"content","aaa");  // auto_increment_id begin with 1

                    Cursor cursor2=select("content","!=","fuck");
                    cursor2.moveToFirst();
                    System.out.println("testing cursor:"+cursor2.getString(4));
                    cursor2.close();

                    delete(1);
                }
                dropTable();
            }
            close();
        }
    }
}
