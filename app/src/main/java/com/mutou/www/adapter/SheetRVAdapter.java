package com.mutou.www.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mutou.www.model.SheetItemInfo;
import com.mutou.www.mtmusic.R;

import java.util.List;

/**
 * Created by 木头 on 2018/7/2.
 */

public class SheetRVAdapter extends BaseQuickAdapter<SheetItemInfo,BaseViewHolder>{
    public SheetRVAdapter(@LayoutRes int layoutResId, @Nullable List<SheetItemInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SheetItemInfo item) {
        helper.setText(R.id.sheet_recycler_item_text,item.getItemTitle());
        helper.setText(R.id.sheet_recycler_item_songNum,item.getSongNum());
        helper.setText(R.id.sheet_recycler_item_id,item.getId());


        helper.addOnClickListener(R.id.sheet_recycler_item_more);
    }
}
