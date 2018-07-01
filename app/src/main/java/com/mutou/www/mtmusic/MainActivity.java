package com.mutou.www.mtmusic;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mutou.www.adapter.MainViewPagerAdapter;
import com.mutou.www.pages.Cache;
import com.mutou.www.pages.Local;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_header_userPic;
    private TextView tv_header_username;
    private FloatingActionButton fab_main;
    private FloatingActionButton prev;
    private FloatingActionButton playOrPause;
    private FloatingActionButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermission();
    }

    //入口函数
    private void startMethods(){
        //设置进入activity的效果
        setInActivity();
        //设置共享元素的参数
        setEnterSharedElement();
        //进行元素的初始化
        initViews();
        //事件初始化
        initEvents();
        //进行顶部三个横线按钮的显示
        getActionBarImage();
        //进行Tablayout的设置
        setTabLayout();
        //注册广播
        initBroadCast();
    }

    private void setTabLayout() {
        List<Fragment> list = new ArrayList<>();
        list.add(new Local());
        list.add(new Cache());

        ViewPager viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabLayout);

        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(),list));
        //设置预加载的页面数
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("我的本地");
        tabLayout.getTabAt(1).setText("我的缓存");
    }

    //进行元素的初始化
    private void initViews() {
        NavigationView nav = (NavigationView) findViewById(R.id.main_nav);
        View nav_header = nav.getHeaderView(0);
        nav.setItemIconTintList(null);
        setNavigationMenuLineStyle(nav, Color.parseColor("#999999"),1);
        iv_header_userPic = (ImageView) nav_header.findViewById(R.id.iv_header_userpic);
        tv_header_username = (TextView) nav_header.findViewById(R.id.tv_header_username);
        fab_main = (FloatingActionButton) findViewById(R.id.main_fab);

        prev = (FloatingActionButton) findViewById(R.id.main_prev);
        playOrPause = (FloatingActionButton) findViewById(R.id.main_playOrPause);
        next = (FloatingActionButton) findViewById(R.id.main_next);

    }

    //事件初始化
    private void initEvents() {
        fab_main.setOnClickListener(this);
        fab_main.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                toDetail();
                return true;
            }
        });
    }

    //进行跳转进入详情页面
    private void toDetail(){

        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            View sharedView = fab_main;
            String translationName = getString(R.string.welcome2main);

            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                    MainActivity.this,sharedView,translationName
            );
            startActivity(intent,activityOptions.toBundle());
        }
        else{
            startActivity(intent);
        }

    }

    //设置点击事件
    boolean showing = false;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_fab:
                if(showing){
                    closeFabItems();
                    showing = false;
                }
                else{
                    showFabItems();
                    showing = true;
                }
               /* Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                View sharedView = fab_main;
                String translationName = getString(R.string.welcome2main);

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                        MainActivity.this,sharedView,translationName
                );
                startActivity(intent,activityOptions.toBundle());*/
//                startActivity(intent);
                break;

            default:
                break;
        }
    }

    //设置共享元素的参数
    private void setEnterSharedElement() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.welcome2main);
        transition.setDuration(600);
        transition.setStartDelay(100);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                //在共享元素动画结束后进行按钮的弹出

            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    //设置进入activity的效果
    private void setInActivity() {
        Fade fade = new Fade();
        getWindow().setEnterTransition(fade);
    }

    //设置返回按钮：不应该退出程序---而是返回桌面
    //复写onKeyDown事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //进行顶部三个横线按钮的显示
    private void getActionBarImage(){
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        //将标题隐藏，否则会遮挡小图标
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,R.string.open,R.string.close
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }
            @Override
            public void onDrawerOpened(View drawerView) {
                //侧边栏开启
                //侧边栏展开动作捕捉之后执行的动画
                ObjectAnimator iv_userPic_scaleX = ObjectAnimator.ofFloat(iv_header_userPic,"scaleX",1f,1.5f,0.8f,1f);
                ObjectAnimator iv_userPic_scaleY = ObjectAnimator.ofFloat(iv_header_userPic,"scaleY",1f,0.5f,1.2f,1f);
                ObjectAnimator iv_userPic_alpha = ObjectAnimator.ofFloat(iv_header_userPic,"alpha",0f,1f);
                ObjectAnimator tv_username_scaleX = ObjectAnimator.ofFloat(tv_header_username,"scaleX",0.7f,1f);
                ObjectAnimator tv_username_alpha = ObjectAnimator.ofFloat(tv_header_username,"alpha",0f,1f);

                AnimatorSet set = new AnimatorSet();
                set.setDuration(500);
                set.play(iv_userPic_scaleX).with(iv_userPic_scaleY).with(tv_username_scaleX)
                        .with(iv_userPic_alpha).with(tv_username_alpha);
                set.start();
                iv_header_userPic.setVisibility(View.VISIBLE);
                tv_header_username.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //侧边栏关闭
                iv_header_userPic.setVisibility(View.INVISIBLE);
                tv_header_username.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }
    //设置nav的分割线的颜色
    public static void setNavigationMenuLineStyle(NavigationView navigationView, @ColorInt final int color, final int height) {
        try {
            Field fieldByPressenter = navigationView.getClass().getDeclaredField("mPresenter");
            fieldByPressenter.setAccessible(true);
            NavigationMenuPresenter menuPresenter = (NavigationMenuPresenter) fieldByPressenter.get(navigationView);
            Field fieldByMenuView = menuPresenter.getClass().getDeclaredField("mMenuView");
            fieldByMenuView.setAccessible(true);
            final NavigationMenuView mMenuView = (NavigationMenuView) fieldByMenuView.get(menuPresenter);
            mMenuView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(View view) {
                    RecyclerView.ViewHolder viewHolder = mMenuView.getChildViewHolder(view);
                    if (viewHolder != null && "SeparatorViewHolder".equals(viewHolder.getClass().getSimpleName()) && viewHolder.itemView != null) {
                        if (viewHolder.itemView instanceof FrameLayout) {
                            FrameLayout frameLayout = (FrameLayout) viewHolder.itemView;
                            View line = frameLayout.getChildAt(0);
                            line.setBackgroundColor(color);
                            line.getLayoutParams().height = height;
                            line.setLayoutParams(line.getLayoutParams());
                        }
                    }
                }

                @Override
                public void onChildViewDetachedFromWindow(View view) {

                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    //权限问题
    private void getPermission(){
        //做权限列表
        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)
                !=  PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.INTERNET);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                !=  PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                !=  PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        //如果权限列表为空----代表全被授权---否则就回调
        if(!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }
        else{
            //如果为空--说明第一次有授权了--这次就不需要授权了，就开始进行服务
            startMethods();
        }
    }

    //权限问题--检查
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //返回值的检查
        switch (requestCode){
            case 1:
                //如果List不为空说明有权限需要处理
                if(grantResults.length>0){
                    for(int result:grantResults){
                        if(result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this, "必须全部授权才能继续使用", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    startMethods();
                }else{
                    Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
    //接受广播以更行UI
    private IntentFilter intentFilter;
    private MyBroadReceiver myBroadReceiver;

    private void initBroadCast(){
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.BroadCastMutou");
        myBroadReceiver = new MyBroadReceiver();
        registerReceiver(myBroadReceiver,intentFilter);
    }

    public class MyBroadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String WHO = intent.getStringExtra("WHO");
            switch (WHO){
                case "ChangeMainFABShowOrHide":
                    if(intent.getStringExtra("WHAT").equals("SHOW")){
                        showFAB();
                    }
                    else{
                        hideFAB();
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private void showFAB(){
        ObjectAnimator fab_translationY = ObjectAnimator.ofFloat(fab_main,"translationY",250f,0f);
        fab_translationY.setDuration(700);
        fab_translationY.start();
    }

    private void hideFAB(){
        ObjectAnimator fab_translationY = ObjectAnimator.ofFloat(fab_main,"translationY",0f,250f);
        fab_translationY.setDuration(700);
        fab_translationY.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadReceiver);
    }

    //悬浮按钮组的组合显示动画
    //显示悬浮按钮组动画
    private void showFabItems(){
        //给主按钮加一个旋转
        ObjectAnimator mainFAB_rotation = ObjectAnimator.ofFloat(fab_main,"rotation",0f,360f);
        ObjectAnimator prev_alpha = ObjectAnimator.ofFloat(prev,"alpha",0f,0f,0f,1f);
        ObjectAnimator prev_translationY = ObjectAnimator.ofFloat(prev,"translationY",80f,80f,80f,0f);
        ObjectAnimator playOrPause_alpha = ObjectAnimator.ofFloat(playOrPause,"alpha",0f,0f,1f);
        ObjectAnimator playOrPause_translationY = ObjectAnimator.ofFloat(playOrPause,"translationY",80f,0f);
        ObjectAnimator next_alpha = ObjectAnimator.ofFloat(next,"alpha",0f,1f);
        AnimatorSet set = new AnimatorSet();
        set.play(prev_alpha).with(playOrPause_alpha).with(next_alpha)
                .with(prev_translationY).with(playOrPause_translationY).with(mainFAB_rotation);
        set.setDuration(500);
        set.start();
//        fab_main.setVisibility(View.VISIBLE);
    }

    //隐藏悬浮按钮组动画
    //隐藏悬浮按钮组动画
    private void closeFabItems(){
        //给主按钮加一个旋转
        ObjectAnimator mainFAB_rotation = ObjectAnimator.ofFloat(fab_main,"rotation",360f,0f);
        ObjectAnimator prev_alpha = ObjectAnimator.ofFloat(prev,"alpha",1f,0.0f,0f);
        ObjectAnimator prev_translationY = ObjectAnimator.ofFloat(prev,"translationY",0f,80f,80f);

        ObjectAnimator playOrPause_alpha = ObjectAnimator.ofFloat(playOrPause,"alpha",1f,0.5f,0f);
        ObjectAnimator playOrPause_translationY = ObjectAnimator.ofFloat(playOrPause,"translationY",0f,0f,0f,80f);

        ObjectAnimator next_alpha = ObjectAnimator.ofFloat(next,"alpha",1f,1f,1f,1f,0f);
        AnimatorSet set = new AnimatorSet();
        set.play(prev_alpha).with(playOrPause_alpha).with(next_alpha)
                .with(prev_translationY).with(playOrPause_translationY).with(mainFAB_rotation);
        set.setDuration(500);
        set.start();
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fab_main.setVisibility(View.GONE);
            }
        },500);*/
    }
}
