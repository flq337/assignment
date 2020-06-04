package com.example.q;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.q.bean.Major;
import com.example.q.dbstore.MajorInfoOperations;

import java.util.ArrayList;
import java.util.List;

public class MajorActivity extends AppCompatActivity {


    private ListView listView;
    EditText majorEditText;
    public static List<Major> allMajors = new ArrayList<>();
    private MajorAdapter majorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);
        majorEditText = findViewById(R.id.editText);
        //再把ArrayList放入listView中，创建一个适配器，最后注册给listView
        listView = findViewById(R.id.listView);


        refreshMajorData();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopMenu(view, position);
                return false;
            }
        });


        //添加button
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrInsertMajorWithInputData(false,null);
            }
        });

        //返回button
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    /**
     * 使用当前用户在页面上输入的数据去新增或者修改专业信息
     *
     * @param isUpdate 当前是否是更新操作
     * @param major_id 如果是更新操作，需要更新的专业信息id
     */
    private void updateOrInsertMajorWithInputData(Boolean isUpdate, Long major_id) {
        if (majorEditText.getText() == null || majorEditText.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "专业名为空！", Toast.LENGTH_SHORT).show();
        } else {

            if (isUpdate) {
                //当前是更新操作
                MajorInfoOperations.getInstance().updateMajorInfo(major_id,
                        majorEditText.getText().toString());
                Toast.makeText(getApplicationContext(), "专业信息更新成功", Toast.LENGTH_SHORT).show();
            } else {
                //当前是新增操作
                MajorInfoOperations.getInstance().insertMajorInfo(
                        majorEditText.getText().toString());
                Toast.makeText(getApplicationContext(), "创建专业信息成功", Toast.LENGTH_SHORT).show();
            }
            //数据库改变之后刷新当前的学生信息列表
            refreshMajorData();

        }
    }


    /**
     * 当长按major item的时候弹出菜单
     *
     * @param view
     * @param position
     */
    public void showPopMenu(View view, final int position) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.getMenuInflater().inflate(R.menu.major_info_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.major_item_modify:
                        updateOrInsertMajorWithInputData(true,allMajors.get(position).getMajorId());
                        break;
                    case R.id.major_item_delete:
                        int i = MajorInfoOperations.getInstance().deleteMajorById(allMajors.get(position).getMajorId());
                        if (i > 0) {
                            Toast.makeText(MajorActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MajorActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                        //删除完之后刷新数据
                        refreshMajorData();
                        break;
                }
                return true;
            }
        });
        menu.show();
    }


    /**
     * 刷新major数据
     */
    private void refreshMajorData() {
        allMajors.clear();
        allMajors = MajorInfoOperations.getInstance().getAllMajors();
        majorAdapter = new MajorAdapter(MajorActivity.this, R.layout.major_info_item, allMajors);
        listView.setAdapter(majorAdapter);
    }


}

