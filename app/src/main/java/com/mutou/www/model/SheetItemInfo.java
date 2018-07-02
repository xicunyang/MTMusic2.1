package com.mutou.www.model;

/**
 * Created by 木头 on 2018/7/2.
 */

public class SheetItemInfo {
    private String id;
    private String itemTitle;
    private String songNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getSongNum() {
        return songNum;
    }

    public void setSongNum(String songNum) {
        this.songNum = songNum;
    }

    public SheetItemInfo(String id, String itemTitle, String songNum) {
        this.id = id;
        this.itemTitle = itemTitle;
        this.songNum = songNum;
    }

    public SheetItemInfo(String itemTitle, String songNum) {
        this.itemTitle = itemTitle;
        this.songNum = songNum;
    }
}
