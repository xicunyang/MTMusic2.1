package com.mutou.www.application;

import com.mutou.www.model.Mp3Info;
import com.mutou.www.model.SheetItemInfo;

import java.util.List;

/**
 * Created by 木头 on 2018/6/30.
 */

public class MyApplication {
    //当前排序方式
    public static String nowSort = "addTime";
    //当前歌单
    public static List<SheetItemInfo> mySongSheet;
    //数据库的名字
    public static final String DB_NAME = "mtmusic.db";
    //sheet表的名字
    public static final String TBL_SHEET = "t_sheet";
    //数据库版本
    public static final int DB_VERSION = 1;
    /*//设置正在播放的歌曲列表
    public List<Mp3Info> nowPlayMp3list;
    //设置正在播放的歌曲位置
    public int nowPlayLocalPosition;
    //设置正在播放的是那种音乐
    public boolean isLocaling;
    //判断是不是第一次打开app
    */
}
