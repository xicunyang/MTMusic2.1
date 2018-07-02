package com.mutou.www.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.mutou.www.model.Mp3Info;

import java.util.ArrayList;



public class AudioUtils {

    private static final String TAG = "AudioUtils";

    /**
     * 获取sd卡所有的音乐文件 
     *  
     * @return 
     * @throws Exception 
     */  
    public static ArrayList<Mp3Info> getAllSongs(Context context,//上下文
                                                 String orderBy,//根据谁排序
                                                 String desc,//正序还是逆序
                                                 String queryArgs,//查询条件
                                                 String queryMessage//查询条件的值
    ) {
  
        ArrayList<Mp3Info> songs = null;
        String myOrderBy;
        String myQueryArgs = "";
        String []myQueryMessage = null;
        //判断查询的依据和顺序
        if(orderBy.equals("")){
            myOrderBy = null;
        }
        else{
            myOrderBy = orderBy+"  COLLATE LOCALIZED "+desc;
        }
        //判断查询的条件
        if(queryArgs.equals("")){
            myQueryArgs = "";
            myQueryMessage = new String[] { "audio/mpeg", "audio/x-ms-wma" };
        }
        else {
            myQueryArgs = queryArgs+" = ? and ";
            myQueryMessage = new String[]{queryMessage,"audio/mpeg", "audio/x-ms-wma"};
        }
        Cursor cursor = context.getContentResolver().query(
                //URI
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                //返回的列
                new String[] {
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.YEAR,
                        MediaStore.Audio.Media.MIME_TYPE,
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.DATA },
                //查询条件
                myQueryArgs
                + MediaStore.Audio.Media.MIME_TYPE + "=? or "
                        +MediaStore.Audio.Media.MIME_TYPE + "=?",
                //查询条件对应的值
                myQueryMessage,
                //排序方式---中文问题
                myOrderBy);
  
        songs = new ArrayList<Mp3Info>();
  
        if (cursor.moveToFirst()) {
            int id = 0;

            Mp3Info song = null;
  
            do {  
                song = new Mp3Info();
                //自定义id
                song.setId(id);
                // 文件名  
                song.setFileName(cursor.getString(1));  
                // 歌曲名  
                song.setTitle(cursor.getString(2));  
                // 时长  
                song.setDuration(cursor.getInt(3));  
                // 歌手名  
                song.setSinger(cursor.getString(4));  
                // 专辑名  
                song.setAlbum(cursor.getString(5));  
                // 年代  
                if (cursor.getString(6) != null) {  
                    song.setYear(cursor.getString(6));  
                } else {  
                    song.setYear("未知");  
                }  
                // 歌曲格式  
                if ("audio/mpeg".equals(cursor.getString(7).trim())) {  
                    song.setType("mp3");  
                } else if ("audio/x-ms-wma".equals(cursor.getString(7).trim())) {  
                    song.setType("wma");  
                }  
                // 文件大小  
                if (cursor.getString(8) != null) {  
                    float size = cursor.getInt(8) / 1024f / 1024f;
                    song.setSize((size + "").substring(0, 3) + "M");
                } else {  
                    song.setSize("未知");  
                }  
                // 文件路径  
                if (cursor.getString(9) != null) {  
                    song.setFileUrl(cursor.getString(9));  
                }  
                songs.add(song);
                id++;
            } while (cursor.moveToNext());  
  
            cursor.close();  
  
        }  
        return songs;  
    }  
  
}  