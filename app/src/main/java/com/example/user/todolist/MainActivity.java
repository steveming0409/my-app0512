package com.example.user.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView lstTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);

        lstTask = (ListView) findViewById(R.id.lstTask);

        loadTaskList();
    }

    private void loadTaskList() {
        ArrayList<String> taskList = dbHelper.getTaskList();
        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this, R.layout.row, R.id.task_tittle, taskList);
            lstTask.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        //change menu icon color
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.holo_purple), PorterDuff.Mode.SRC_IN);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this).setTitle("新增")
                        .setMessage("請輸入檢測的結果")
                        .setView(taskEditText)
                        .setPositiveButton("新增", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                dbHelper.insertNewTask(task);
                                loadTaskList();
                            }
                        }).setNegativeButton("取消", null).create();
                dialog.show();
                break;
            case R.id.action_author:
                AlertDialog dialog1 = new AlertDialog.Builder(this).setTitle("作者資訊")
                        .setMessage("作者: 張原銘\n組 員 : 許景勛 鄭祥祐\n開發日期: 2019年5月10日\n指導教授: 段裘慶教授")
                        .setPositiveButton("好啦", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();
                dialog1.show();
                break;
            case R.id.action_cost:
                AlertDialog dialog2 = new AlertDialog.Builder(this).setTitle("裁罰基準表")
                        .setMessage("  酒 測 值      機  車   小型車   大型車 \n0.15~0.24  15,000  19,500  22,500\n0.25~0.39  22,500  29,000  33,500\n0.40~0.54  45,000  51,500  56,000\nover 0.55  67,500  74,000  78,500")
                        .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();
                dialog2.show();
                break;
            case R.id.action_desk:
                Intent it = new Intent(MainActivity.this,desktop.class);
                startActivity(it);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public void deleteTaskClick( final View view) {
        AlertDialog dialog=new AlertDialog.Builder(this).setTitle("確定刪除嗎?")
                .setMessage("刪除後無法回復喔!")
                .setPositiveButton("確定刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View parent = (View) view.getParent();

                        TextView taskTextView = (TextView) findViewById(R.id.task_tittle);
                        String task = String.valueOf(taskTextView.getText());
                        dbHelper.deleteTask(task);
                        loadTaskList();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();

    }

}
