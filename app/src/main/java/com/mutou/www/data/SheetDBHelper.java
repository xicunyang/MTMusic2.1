package com.mutou.www.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mutou.www.application.MyApplication;

/**
 * Created by 木头 on 2018/7/2.
 */

public class SheetDBHelper extends SQLiteOpenHelper{
    private SQLiteDatabase db;
    private String TBL_NAME = MyApplication.TBL_SHEET;
    public SheetDBHelper(Context context) {
        super(context, MyApplication.DB_NAME, null,MyApplication.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        //创建表
        String CREATE_TBL = "create table "+TBL_NAME+" (id integer primary key autoincrement ,title text,songNum text)";
        db.execSQL(CREATE_TBL);
    }

    //添加
    public void insert(ContentValues contentValues){
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TBL_NAME,null,contentValues);
        db.close();
    }

    //查询
    public Cursor query(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TBL_NAME,null,null,null,null,null,null,null);
        return cursor;
    }

    //删除
    public void delete(int id){
        if(db==null)
            db = getWritableDatabase();
        db.delete(TBL_NAME,"id=?",new String[]{String.valueOf(id)});
    }

    //更新
    public void update(ContentValues contentValues,int id){
        SQLiteDatabase db = getWritableDatabase();
        db.update(TBL_NAME,contentValues,"id=?",new String[]{String.valueOf(id)});
    }

    //获取最大的id
    public int getMaxId(){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select max(id) from "+TBL_NAME;
        Cursor c = db.rawQuery(sql,null);
        int result = -1;
        if(c.moveToNext()){
            result = c.getInt(0);
        }
        return result;
    }

    //关闭数据库
    public void close(){
        if(db!=null)
            db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
