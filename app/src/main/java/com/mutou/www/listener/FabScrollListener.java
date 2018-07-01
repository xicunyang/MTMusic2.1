package com.mutou.www.listener;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by 木头 on 2018/6/30.
 */

public class FabScrollListener extends RecyclerView.OnScrollListener{
    private HideOrShowFab hideOrShowFab;
    private static final String TAG = "FabScrollListener";
    private int length = 0;
    private boolean visible = true;
    private int JIEXIAN = 50;

    public FabScrollListener(HideOrShowFab hideOrShowFab){
        this.hideOrShowFab = hideOrShowFab;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);


        //现在正在显示&&向下滑    当先不显示&&向上滑
        if(visible == true && dy>0 ||(visible == false && dy<0)){
            length = length+dy;
        }
        //看看length先到-20还是先到20
        if(visible == true && length>=JIEXIAN){
            //手指向上---数据向下加载--隐藏掉
            visible = false;
            hideOrShowFab.onHide();
            length = 0;
        }

        if(visible==false && length<=-JIEXIAN){
            //手指向下---数据向上加载--显示出来
            visible = true;
            hideOrShowFab.onShow();
            length = 0;
        }
    }
}
