package com.mutou.www.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.mutou.www.model.Mp3Info;

import java.util.ArrayList;



public class AudioUtils {
  
    /** 
     * 获取sd卡所有的音乐文件 
     *  
     * @return 
     * @throws Exception 
     */  
    public static ArrayList<Mp3Info> getAllSongs(Context context) {
  
        ArrayList<Mp3Info> songs = null;
  
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
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
                MediaStore.Audio.Media.MIME_TYPE + "=? or "  
                        + MediaStore.Audio.Media.MIME_TYPE + "=?",  
                new String[] { "audio/mpeg", "audio/x-ms-wma" }, null);  
  
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