package com.mutou.www.diy;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mutou.www.application.MyApplication;
import com.mutou.www.mtmusic.R;

/**
 * Created by 木头 on 2018/7/1.
 */

public class MyMainDialog extends Dialog{
    Context context;
    private ClickListenerInterface clickListenerInterface;
    private ImageView iv_time;
    private ImageView iv_singer;
    private ImageView iv_alum;
    private ImageView iv_song;

    public interface ClickListenerInterface{
        void doTime();
        void doSinger();
        void doSong();
        void doAlum();
    }

    public MyMainDialog(Context context){
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init(){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.main_diy_dialog,null);
        setContentView(view);

        LinearLayout ll_time = (LinearLayout) view.findViewById(R.id.main_dialog_ll_time);
        LinearLayout ll_singer = (LinearLayout) view.findViewById(R.id.main_dialog_ll_singer);
        LinearLayout ll_alum = (LinearLayout) view.findViewById(R.id.main_dialog_ll_alum);
        LinearLayout ll_song = (LinearLayout) view.findViewById(R.id.main_dialog_ll_song);

        iv_time = (ImageView) view.findViewById(R.id.main_dialog_flag_time);
        iv_singer = (ImageView) view.findViewById(R.id.main_dialog_flag_singer);
        iv_alum = (ImageView) view.findViewById(R.id.main_dialog_flag_alum);
        iv_song = (ImageView) view.findViewById(R.id.main_dialog_flag_song);

        ll_time.setOnClickListener(new ClickListener());
        ll_singer.setOnClickListener(new ClickListener());
        ll_alum.setOnClickListener(new ClickListener());
        ll_song.setOnClickListener(new ClickListener());

        checkShow();

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();//获取屏幕的宽和高用
        lp.width =(int) (d.widthPixels*0.8);
        dialogWindow.setAttributes(lp);
    }

    private void checkShow() {
        HideAll();
        switch (MyApplication.nowSort){
            case "addTime":
                iv_time.setVisibility(View.VISIBLE);
                break;
            case "singer":
                iv_singer.setVisibility(View.VISIBLE);
                break;
            case "song":
                iv_song.setVisibility(View.VISIBLE);
                break;
            case "alum":
                iv_alum.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void HideAll(){
        iv_time.setVisibility(View.INVISIBLE);
        iv_singer.setVisibility(View.INVISIBLE);
        iv_alum.setVisibility(View.INVISIBLE);
        iv_song.setVisibility(View.INVISIBLE);
    }

    public void setClickLinstener(ClickListenerInterface clickListenerInterface){
            this.clickListenerInterface = clickListenerInterface;
    }

    private class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.main_dialog_ll_alum:
                    clickListenerInterface.doAlum();
                    break;
                case R.id.main_dialog_ll_singer:
                    clickListenerInterface.doSinger();
                    break;
                case R.id.main_dialog_ll_song:
                    clickListenerInterface.doSong();
                    break;
                case R.id.main_dialog_ll_time:
                    clickListenerInterface.doTime();
                    break;
            }
        }
    }
}




















