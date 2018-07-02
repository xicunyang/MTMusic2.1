package com.mutou.www.pages;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mutou.www.adapter.LocalRVAdapter;
import com.mutou.www.application.MyApplication;
import com.mutou.www.listener.FabScrollListener;
import com.mutou.www.listener.HideOrShowFab;
import com.mutou.www.model.Mp3Info;
import com.mutou.www.mtmusic.DetailActivity;
import com.mutou.www.mtmusic.MainActivity;
import com.mutou.www.mtmusic.R;
import com.mutou.www.utils.AudioUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class Local extends Fragment implements HideOrShowFab{

    private static final String TAG = "Local";
    private RecyclerView recyclerView;
    private List<Mp3Info> mp3infs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_local,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        getMp3InfoListByFlag(MediaStore.Audio.Media.DATE_ADDED);
        initBroadCast();
    }

    private void getMp3InfoList() {
        //先获取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                mp3infs = AudioUtils.getAllSongs(getActivity(),
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        "desc",
                        "",
                        "");
                Message message = Message.obtain();
                message.arg1 = 1;
                handler.sendMessage(message);
            }
        }).start();
    }


    //找到控件
    private void initViews() {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.local_rv);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1==1){
                putDataToRV();
            }
            super.handleMessage(msg);
        }
    };

    private void putDataToRV() {
        //设置rv的横向还是纵向布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置分割线（默认是黑色的，作用不明显）
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        //那就自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.rv_divider_style));
        recyclerView.addItemDecoration(divider);
        //创建适配器
        LocalRVAdapter adapter = new LocalRVAdapter(R.layout.recycler_item,mp3infs);

        //设置适配器的items的动画
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        scaleInAnimationAdapter.setDuration(400);
        scaleInAnimationAdapter.setFirstOnly(false);
        recyclerView.setAdapter(new AlphaInAnimationAdapter(scaleInAnimationAdapter));
        recyclerView.addOnScrollListener(new FabScrollListener(this));
        //设置item点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                /*TextView tv  = (TextView) view.findViewById(R.id.rv_item_title);
                Toast.makeText(getActivity(), tv.getText(), Toast.LENGTH_SHORT).show();*/

            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onShow() {
        sendBroadCastToShowOrHideFAB("SHOW");
    }

    @Override
    public void onHide() {
        sendBroadCastToShowOrHideFAB("HIDE");
    }

    //发送广播---目的：在修改播放状态后希望更新到UI
    Intent changePlayStuate = new Intent("android.BroadCastMutou");

    private void sendBroadCastToShowOrHideFAB(String flag) {
        //不传具体参数了，根据全局isPlaying来判断
        changePlayStuate.putExtra("WHO", "ChangeMainFABShowOrHide");
        changePlayStuate.putExtra("WHAT", flag);
        getActivity().sendBroadcast(changePlayStuate);
    }


    //接受广播以更行UI
    private IntentFilter intentFilter;
    private MyBroadReceiver myBroadReceiver;

    private void initBroadCast(){
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.BroadCastMutou");
        myBroadReceiver = new MyBroadReceiver();
        getActivity().registerReceiver(myBroadReceiver,intentFilter);
    }

    public class MyBroadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String WHO = intent.getStringExtra("WHO");
            switch (WHO) {
                case "ChangeSortByFlag":
                    doSort(intent.getStringExtra("WHAT"));
                    break;
            }
        }
    }

    private void doSort(String WHAT) {
        switch (WHAT){
            case "addTime":
                getMp3InfoListByFlag(MediaStore.Audio.Media.DATE_ADDED);
                MyApplication.nowSort = "addTime";
                break;
            case "singer":
                getMp3InfoListByFlag(MediaStore.Audio.Media.ARTIST);
                MyApplication.nowSort = "singer";
                break;
            case "song":
                getMp3InfoListByFlag(MediaStore.Audio.Media.TITLE);
                MyApplication.nowSort = "song";
                break;
            case "alum":
                getMp3InfoListByFlag(MediaStore.Audio.Media.ALBUM);
                MyApplication.nowSort = "alum";
                break;
            default:
                break;
        }
    }

    private void getMp3InfoListByFlag(final String sortFlag) {
        //先获取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                mp3infs = AudioUtils.getAllSongs(getActivity(),
                        sortFlag,
                        "asc",
                        "",
                        "");
                Message message = Message.obtain();
                message.arg1 = 1;
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myBroadReceiver);
    }
}

















