package com.mutou.www.diy;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mutou.www.model.MypopModel;
import com.mutou.www.mtmusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 木头 on 2018/7/1.
 */

public class MyPopWindow extends PopupWindow{
    private Context context;
    private View view;
    private LinearLayout scan;
    private LinearLayout add;
    private ListView listview;
    private List<MypopModel> list;

    public MyPopWindow(Context context){
        this(context,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }



    public MyPopWindow(Context context,int width,int height){
        super(context);
        this.context = context;
        setWidth(width);
        setHeight(height);
        //设置可以获得焦点
        setFocusable(true);
        //设置弹窗内可点击
        setTouchable(true);
        //设置弹窗外可点击
        setOutsideTouchable(true);
        view = LayoutInflater.from(context).inflate(R.layout.main_diy_popwindow,null);
        setContentView(view);
        initData();
    }

    public View getListView(){
        return listview;
    }

    private void initData(){
        listview = (ListView) view.findViewById(R.id.diy_popwindow_listview);
        list = new ArrayList<>();
        list.add(new MypopModel(R.drawable.diy_popwindow_sort,"选择排序方式"));
//        list.add(new MypopModel(R.drawable.diy_popwindow_sort,"选择排序方式2"));

        //设置列表适配器
        listview.setAdapter(new BaseAdapter() {
            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView image = null;
                TextView text = null;

                if(convertView==null){
                    convertView = LayoutInflater.from(context).inflate(R.layout.diy_popwindow_item,null);
                    image = (ImageView) convertView.findViewById(R.id.diy_pop_image);
                    text = (TextView) convertView.findViewById(R.id.diy_pop_text);
                }
                image.setImageResource(list.get(position).getImageId());
                text.setText(list.get(position).getText());
               return convertView;
            }
        });
    }


}
