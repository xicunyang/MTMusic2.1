package com.mutou.www.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mutou.www.model.Mp3Info;
import com.mutou.www.mtmusic.R;

import java.util.List;

/**
 * Created by 木头 on 2018/6/30.
 */

public class LocalRVAdapter extends BaseQuickAdapter<Mp3Info,BaseViewHolder>{
    public LocalRVAdapter(@LayoutRes int layoutResId, @Nullable List<Mp3Info> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Mp3Info item) {
        //设置标题
        helper.setText(R.id.rv_item_title,item.getTitle());

        //设置歌手--专辑
        helper.setText(R.id.rv_item_title_singerAlum,item.getSinger()+"-"+item.getAlbum());

        //设置hq是否显示
        if(isHQ(item.getSize())){
            helper.setVisible(R.id.rv_item_title_hq,true);
        }
    }

    private boolean isHQ(String S){
        if(S==null){
           return false;
        }

        float size = Float.parseFloat(S.substring(0,S.length()-1));
        if(size<=10){
            return false;
        }
        else{
            return true;
        }
    }
}
