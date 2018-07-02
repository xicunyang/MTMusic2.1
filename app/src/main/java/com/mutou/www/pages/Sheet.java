package com.mutou.www.pages;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mutou.www.adapter.SheetRVAdapter;
import com.mutou.www.application.MyApplication;
import com.mutou.www.data.SheetDBHelper;
import com.mutou.www.diy.MySheetDialog;
import com.mutou.www.diy.MySheetDialogDelete;
import com.mutou.www.model.SheetItemInfo;
import com.mutou.www.mtmusic.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class Sheet extends Fragment implements View.OnClickListener{
    private static final String TAG = "Sheet";
    private SheetRVAdapter adapter;
    private RecyclerView recyclerView;
    private ImageView iv_setting;
    private Dialog dialog_more;
    private int nowDeletePosition = -1;
    private int nowDeleteId = -1;
    private List<SheetItemInfo> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_sheet,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();
        initEvents();
        putDataToRV();
    }



    private void initViews() {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.sheet_rv);
        iv_setting = (ImageView) getActivity().findViewById(R.id.sheet_setting);
    }
    private void initEvents() {
        iv_setting.setOnClickListener(this);
    }
    private void putDataToRV() {
        list = new ArrayList<>();
        /*for (int i=0;i<=5;i++){
            list.add(new SheetItemInfo("测试歌单"+i,"55首"));
        }*/

        //从数据库查询数据
        SheetDBHelper sheetDBHelper = new SheetDBHelper(getActivity());
        Cursor c = sheetDBHelper.query();
        while(c.moveToNext()){
            list.add(new SheetItemInfo(String.valueOf(c.getInt(0)),c.getString(1),c.getString(2)));
        }


        //设置recycler的布局方向
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //设置分割线
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.rv_divider_style));
        recyclerView.addItemDecoration(divider);

        //创建适配器
        adapter = new SheetRVAdapter(R.layout.sheet_recycler_item,list);

        //设置适配器的动画
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        scaleInAnimationAdapter.setDuration(400);
        scaleInAnimationAdapter.setFirstOnly(false);
        recyclerView.setAdapter(scaleInAnimationAdapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.sheet_recycler_item_more:
                        //Toast.makeText(getActivity(), "position---"+position, Toast.LENGTH_SHORT).show();
                        showBottomDialog_more();
                        nowDeletePosition = position;
                        View v = (View) view.getParent();
                        TextView tv = (TextView) v.findViewById(R.id.sheet_recycler_item_id);
                        nowDeleteId = Integer.parseInt(tv.getText().toString());
                        break;

                    default:
                        break;
                }
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextView tv = (TextView) view.findViewById(R.id.sheet_recycler_item_id);
                Toast.makeText(getActivity(), "----"+tv.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sheet_setting:
                showBottomDialog_setting();
                break;
            case R.id.sheet_dialog_more_editSheet:
                dialog_more.dismiss();
                break;
            case R.id.sheet_dialog_more_shareSheet:
                dialog_more.dismiss();
                break;
            case R.id.sheet_dialog_more_deleteSheet:
                dialog_more.dismiss();
                getDeleteDialog();
                break;
            default:
                break;
        }
    }

    //创建delete的Dialog
    private void getDeleteDialog(){
        /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示").setMessage("确认要删除吗").setPositiveButton("确认",null).show();*/
        final MySheetDialogDelete mySheetDialogDelete = new MySheetDialogDelete(getActivity());
        mySheetDialogDelete.show();
        mySheetDialogDelete.setOnClickListener(new MySheetDialogDelete.ClickListenerInterface() {
            @Override
            public void doCancel() {
                Toast.makeText(getActivity(), "cancel", Toast.LENGTH_SHORT).show();
                mySheetDialogDelete.dismiss();
            }

            @Override
            public void doSubmit() {
                Toast.makeText(getActivity(), "submit", Toast.LENGTH_SHORT).show();
                list.remove(nowDeletePosition);
                SheetDBHelper helper = new SheetDBHelper(getActivity());
                //数据库里面删除的是id
                helper.delete(nowDeleteId);
                adapter.notifyItemRemoved(nowDeletePosition);
                mySheetDialogDelete.dismiss();
            }
        });
    }

    //显示more的底部菜单
    private void showBottomDialog_more(){
        dialog_more = new Dialog(getActivity(), R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.sheet_bottm_dialog_more,null
        );
        //设置控件
        LinearLayout ll_edit = (LinearLayout) root.findViewById(R.id.sheet_dialog_more_editSheet);
        LinearLayout ll_share = (LinearLayout) root.findViewById(R.id.sheet_dialog_more_shareSheet);
        LinearLayout ll_delete = (LinearLayout) root.findViewById(R.id.sheet_dialog_more_deleteSheet);


        ll_edit.setOnClickListener(this);
        ll_delete.setOnClickListener(this);
        ll_share.setOnClickListener(this);


        dialog_more.setContentView(root);
        Window dialogWindow = dialog_more.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;

        lp.width = getResources().getDisplayMetrics().widthPixels;
        root.measure(0,0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f;
        dialogWindow.setAttributes(lp);
        dialog_more.show();
    }

    private void showBottomDialog_setting() {
        final Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.sheet_bottm_dialog,null
        );
        //设置控件
        LinearLayout ll_add = (LinearLayout) root.findViewById(R.id.sheet_dialog_addSheet);
        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //弹出新建歌单页面
                final MySheetDialog mySheetDialog = new MySheetDialog(getActivity());
                mySheetDialog.show();
                mySheetDialog.setOnClickListener(new MySheetDialog.ClickListenerInterface() {
                    @Override
                    public void doCancel() {
                        mySheetDialog.dismiss();
                    }

                    @Override
                    public void doSubmit(String title) {
                        Toast.makeText(getActivity(), "submit--->"+title, Toast.LENGTH_SHORT).show();
                        //添加歌单
                       /* MyApplication.mySongSheet.add(new SheetItemInfo(title,"55"));
                        adapter.notifyItemInserted(MyApplication.mySongSheet.size());
                        mySheetDialog.dismiss();*/
                       //向数据库添加数据
                        ContentValues values = new ContentValues();
                        values.put("title",title);
                        values.put("songNum","0");
                        SheetDBHelper helper = new SheetDBHelper(getActivity());
                        helper.insert(values);
                        //先获取当前最大的id

                        list.add(new SheetItemInfo(String.valueOf(helper.getMaxId()),title,"0"));
                        adapter.notifyItemInserted(list.size());
                        mySheetDialog.dismiss();
                    }
                });
            }
        });

        dialog.setContentView(root);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;

        lp.width = getResources().getDisplayMetrics().widthPixels;
        root.measure(0,0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }
}













