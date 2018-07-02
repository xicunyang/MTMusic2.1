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
import android.widget.EditText;
import android.widget.TextView;

import com.mutou.www.mtmusic.R;

/**
 * Created by 木头 on 2018/7/2.
 */

public class MySheetDialog extends Dialog{
    Context context;
    ClickListenerInterface clickListenerInterface;
    private EditText et_title;

    public MySheetDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public interface ClickListenerInterface{
        void doCancel();
        void doSubmit(String title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void setOnClickListener(ClickListenerInterface clickListener){
        this.clickListenerInterface = clickListener;
    }

    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.sheet_dialog_addsheet,null);

        setContentView(view);

        TextView tv_submit = (TextView) view.findViewById(R.id.sheet_dialog_addSheet_submit);
        TextView tv_cancel = (TextView) view.findViewById(R.id.sheet_dialog_addSheet_cancel);
        et_title = (EditText) view.findViewById(R.id.sheet_dialog_addSheet_et);

        tv_submit.setOnClickListener(new ClickListener());
        tv_cancel.setOnClickListener(new ClickListener());

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();//获取屏幕的宽和高用
        lp.width = (int)(d.widthPixels*0.8);

        window.setAttributes(lp);
    }

    private class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sheet_dialog_addSheet_submit:
                    clickListenerInterface.doSubmit(et_title.getText().toString());
                    break;
                case R.id.sheet_dialog_addSheet_cancel:
                    clickListenerInterface.doCancel();
                    break;
                default:
                    break;
            }
        }
    }
}

















