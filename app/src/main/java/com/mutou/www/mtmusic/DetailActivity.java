 package com.mutou.www.mtmusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

 public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //设置共享元素的进入动画
        setSharedElementAnimator();
        setToolBar();
    }

    private void setSharedElementAnimator() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.welcome2main);
        transition.setDuration(600);
        getWindow().setSharedElementEnterTransition(transition);
        getWindow().setSharedElementExitTransition(null);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                Toast.makeText(DetailActivity.this, "ok", Toast.LENGTH_SHORT).show();
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


     //显示ToolBar的返回按钮
     private void setToolBar() {
         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_localMain);
         this.setSupportActionBar(toolbar);
         getSupportActionBar().setHomeButtonEnabled(true);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         this.getSupportActionBar().setDisplayShowTitleEnabled(false);
     }

     //Toolbar的事件---返回
     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         if(item.getItemId() == android.R.id.home){
             finish();
         }
         return super.onOptionsItemSelected(item);
     }

     @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
         if (keyCode == KeyEvent.KEYCODE_BACK) {
             finish();
             return true;
         }
         return super.onKeyDown(keyCode, event);
     }
}
