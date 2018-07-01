package com.mutou.www.mtmusic;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mutou.www.utils.DensityUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class WelcomeActivity extends AppCompatActivity {

    private CircleImageView ci_logo;
    private ImageView iv_m1;
    private ImageView iv_t;
    private ImageView iv_m2;
    private ImageView iv_usic;
    private LinearLayout ll_mtm;
    private RelativeLayout rl_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //先找到控件
        initViews();

        //设置点击事件
        initEvents();

        //设置入场动画
        inAnimation();
    }

    //设置点击事件
    private void initEvents(){
        //用户不用等待--点击即可进行跳转
        rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toMainActivity();
            }
        });
    }

    //先找到控件
    private void initViews() {
        ci_logo = (CircleImageView) findViewById(R.id.welcome_logo);
        iv_m1 = (ImageView) findViewById(R.id.welcome_M1);
        iv_t = (ImageView) findViewById(R.id.welcome_T);
        iv_m2 = (ImageView) findViewById(R.id.welcome_M2);
        iv_usic = (ImageView) findViewById(R.id.welcome_usic);
        ll_mtm = (LinearLayout) findViewById(R.id.welcome_ll_mtm);
        rl_main = (RelativeLayout) findViewById(R.id.welcome_main);
    }

    //页面跳转动作
    private void toMainActivity(){
        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
        //使用共享元素启动activity

        //在这里判断一下安卓版本
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            View sharedView = ci_logo;
            String translationName = getString(R.string.welcome2main);

            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                    WelcomeActivity.this,sharedView,translationName
            );
            startActivity(intent,activityOptions.toBundle());
        }else{
            startActivity(intent);
        }

    }

    //设置入场动画（旋转上升--显示底部动画--就跳转进MainActivity）
    private void inAnimation() {
        //logo进场动画
        logoAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //底部进场动画
                bottomAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //exitAnimation();
                        toMainActivity();
                    }
                },2200);
            }
        }, 700);
    }
    //退场动画
    private void exitAnimation(){
        //横向拉伸
        ObjectAnimator animator_scalex = ObjectAnimator.ofFloat(ci_logo,"scaleX",1f,0.7f,3f);
        //纵向拉伸
        ObjectAnimator animator_scaleY = ObjectAnimator.ofFloat(ci_logo,"scaleY",1f,0.7f,3f);
        //纵向移动
        ObjectAnimator animator_translation = ObjectAnimator.ofFloat(ci_logo,"translationY",-400f,0f);
        //透明度
        ObjectAnimator animator_alpha = ObjectAnimator.ofFloat(ci_logo, "alpha", 1f,0.1f,0f);

        //纵向移动
        ObjectAnimator mtm_translation = ObjectAnimator.ofFloat(ll_mtm,"translationY",0f,200f,200f);
        //纵向移动
        ObjectAnimator usic_translation = ObjectAnimator.ofFloat(iv_usic,"translationY",0f,200f,200f);
        //透明度
        ObjectAnimator mtm_alpha = ObjectAnimator.ofFloat(ll_mtm, "alpha", 1f,0f,0f,0f,0f);
        //透明度
        ObjectAnimator usic_alpha = ObjectAnimator.ofFloat(iv_usic, "alpha", 1f,0f,0f,0f,0f);

        //设置动画集合
        AnimatorSet set = new AnimatorSet();
        //设置动画顺序
        set.play(animator_translation).
                with(animator_alpha).
                with(animator_scaleY).
                with(animator_scalex).
                with(mtm_translation).
                with(usic_translation).with(mtm_alpha).with(usic_alpha);
        //设置动画持续时间
        set.setDuration(500);
        //开始动画
        set.start();
    }
    //底部动画
    private void bottomAnimation(){
        //M1
        ObjectAnimator m1_scalex = ObjectAnimator.ofFloat(iv_m1,"scaleX",0.3f,1.2f,1f);
        ObjectAnimator m1_scaleY = ObjectAnimator.ofFloat(iv_m1,"scaleY",0.3f,1.2f,1f);
        ObjectAnimator m1_alpha = ObjectAnimator.ofFloat(iv_m1, "alpha", 0f,1f);

        AnimatorSet setm1 = new AnimatorSet();
        setm1.play(m1_scalex).with(m1_scaleY).with(m1_alpha);
        setm1.setDuration(1000);
        setm1.start();
        iv_m1.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //T
                ObjectAnimator t_scalex = ObjectAnimator.ofFloat(iv_t,"scaleX",0.3f,1.2f,1f);
                ObjectAnimator t_scaleY = ObjectAnimator.ofFloat(iv_t,"scaleY",0.3f,1.2f,1f);
                ObjectAnimator t_alpha = ObjectAnimator.ofFloat(iv_t, "alpha", 0f,1f);
                AnimatorSet sett = new AnimatorSet();
                sett.play(t_scalex).with(t_scaleY).with(t_alpha);
                sett.setDuration(1000);
                sett.start();
                iv_t.setVisibility(View.VISIBLE);
            }
        },300);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //M2
                ObjectAnimator m2_scalex = ObjectAnimator.ofFloat(iv_m2,"scaleX",0.3f,1.2f,1f);
                ObjectAnimator m2_scaleY = ObjectAnimator.ofFloat(iv_m2,"scaleY",0.3f,1.2f,1f);
                ObjectAnimator m2_alpha = ObjectAnimator.ofFloat(iv_m2, "alpha", 0f,1f);
                float pianyi = DensityUtil.dip2px(WelcomeActivity.this,20);
                ObjectAnimator mtm_translation = ObjectAnimator.ofFloat(ll_mtm,"translationX",0f,-pianyi,-pianyi,-pianyi);
                AnimatorSet setm2 = new AnimatorSet();
                setm2.play(m2_scalex).with(m2_scaleY).with(m2_alpha).before(mtm_translation);
                setm2.setDuration(1000);
                setm2.start();
                iv_m2.setVisibility(View.VISIBLE);
            }
        },600);

        //usic
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator usic_alpha = ObjectAnimator.ofFloat(iv_usic, "alpha", 0f,1f);
                AnimatorSet set = new AnimatorSet();
                set.play(usic_alpha);
                set.setDuration(1000);
                set.start();
                iv_usic.setVisibility(View.VISIBLE);
            }
        },1600);
    }

    //logo的动画
    private void logoAnimation(){
        //旋转动画
        ObjectAnimator ci_logo_rotation = ObjectAnimator.ofFloat(ci_logo,"rotation",150f,360f);
        //横向拉伸
        ObjectAnimator ci_logo_scaleX = ObjectAnimator.ofFloat(ci_logo,"scaleX",0.3f,1f);
        //纵向拉伸
        ObjectAnimator ci_logo_scaleY = ObjectAnimator.ofFloat(ci_logo,"scaleY",0.3f,1f);
        //纵向移动
        ObjectAnimator ci_logo_translationY = ObjectAnimator.ofFloat(ci_logo,"translationY",20f,-400f);
//        ObjectAnimator ci_logo_translationY = ObjectAnimator.ofFloat(ci_logo,"translationY",-400f,0f);
        //透明度
        ObjectAnimator ci_logo_alpha = ObjectAnimator.ofFloat(ci_logo, "alpha", 0f,1f);

        AnimatorSet set = new AnimatorSet();
        set.play(ci_logo_rotation).with(ci_logo_scaleX)
                .with(ci_logo_scaleY).with(ci_logo_translationY)
                .with(ci_logo_alpha);
        set.setDuration(1000);
        set.start();

        //设置控件的可见度---显示
        ci_logo.setVisibility(View.VISIBLE);
    }

}





























