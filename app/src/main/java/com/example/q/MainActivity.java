package com.example.q;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.ServiceConnection;

import android.content.Context;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.ListView;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.AdapterView;
import android.view.MenuItem;
import android.widget.CheckBox;

import com.example.q.bean.Major;
import com.example.q.bean.Student;
import com.example.q.store.dbstore.BaseInfoOperations;
import com.example.q.store.dbstore.MajorInfoOperations;

public class MainActivity extends AppCompatActivity {
    EditText name;
    CheckBox check1;
    CheckBox check2;
    CheckBox check3;
    EditText height;
    EditText weight;
    RadioButton boy;
    RadioButton girl;
    Spinner spinner;
    ListView listView;



    String string;

    private BService bService;

    private List<Student> allStudent = new ArrayList<>();
    private StudentAdapter studentAdapter;


    private ArrayAdapter<String> arr_adapter1;
    private List<String> list2 = new ArrayList<String>();;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                //用户拒绝授权
                Toast.makeText(this, "无法获取SD卡读写权限", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jibenxinxi);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //android 6.0以上需要动态申请权限
            int permission_write = ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int permission_read = ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permission_write != PackageManager.PERMISSION_GRANTED
                    || permission_read != PackageManager.PERMISSION_GRANTED) {
                //申请权限，特征码自定义为1，可在回调时进行相关判断
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

        name = findViewById(R.id.name);//姓名文本
        height = findViewById(R.id.height);//身高文本
        weight = findViewById(R.id.weight);//身高文本

        boy = findViewById(R.id.boy);
        girl = findViewById(R.id.girl);

        check1 = findViewById(R.id.travel);
        check2 = findViewById(R.id.sport);
        check3 = findViewById(R.id.other);

        spinner = findViewById(R.id.spinner);
        listView = findViewById(R.id.listview);

        //如果没有初始化数据，初始化数据
        firstLaunchInitializeData();

        //给listView
        refreshStudentData();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopMenu(view, position);
                return false;
            }
        });


        //添加按钮点击事件 并判断姓名栏是否为空
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrInsertBaseInfoWithInputData(false, null);
            }
        });

        //删除按钮点击事件，弹出确认框提示用户是否确认删除
        findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示：")
                        .setMessage("是否删除数据库中的全部数据")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(MainActivity.this, "你点击了取消按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (Student student : allStudent) {
                                    BaseInfoOperations.getInstance().deleteStudentById(student.getId());
                                }
                                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                refreshStudentData();
                            }
                        })
                        .create();             //创建AlertDialog对象
                alert.show();                    //显示对话框
            }
        });


        final Button button = findViewById(R.id.button);//关闭button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });


        //设置heght监听器 通过键盘按键事件

        height.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode >= 7 && keyCode <= 16 || keyCode == 56 || keyCode == 67) {
                    return false;
                } else {
                    Toast.makeText(getApplicationContext(), "error：只能输入数字和字符'.'", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        });

        weight.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode >= 7 && keyCode <= 16 || keyCode == 56 || keyCode == 67) {
                    return false;
                } else {
                    Toast.makeText(getApplicationContext(), "Error: 只能输入数字或者字符'.'", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        });

        //计算BMI
        Button jisuan = findViewById(R.id.button6);
        final Intent intent = new Intent(MainActivity.this, BService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        jisuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double validWeight = Double.parseDouble(weight.getText().toString());
                    double validHeight = Double.parseDouble(height.getText().toString());
                    Toast.makeText(getApplicationContext(), name.getText().toString() + bService.getBMI(validHeight, validWeight), Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException exception) {
                    Toast.makeText(getApplicationContext(), "请输入有效的身高体重", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //返回上一级
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //恢复状态
        restoreState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshMajorData();
    }

    /**
     * 刷新专业信息数据
     */
    private void refreshMajorData() {
        //给spinner创建数据源
        list2.clear();
        for (Major allMajor : MajorInfoOperations.getInstance().getAllMajors()) {
            list2.add(allMajor.getMajor());
        }
        arr_adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list2);
        arr_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arr_adapter1);
    }


    /**
     * 刷新学生数据，去数据库中查询最新的学生数据并刷新学生信息列表
     */
    private void refreshStudentData() {
        allStudent.clear();
        allStudent = BaseInfoOperations.getInstance().getAllStudent();
        studentAdapter = new StudentAdapter(MainActivity.this, R.layout.base_info_item, allStudent);
        listView.setAdapter(studentAdapter);
    }


    /**
     * 使用当前用户在页面上输入的数据去新增或者修改用户
     *
     * @param isUpdate   当前是否是更新操作
     * @param student_id 如果是更新操作，需要更新的学生id
     */
    private void updateOrInsertBaseInfoWithInputData(Boolean isUpdate, Long student_id) {
        if (name.getText() == null || name.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "姓名为空！", Toast.LENGTH_SHORT).show();
        } else {
            String hobby = "";
            if (check1.isChecked()) {
                hobby += "旅游  ";
            }
            if (check2.isChecked()) {

                hobby += "运动  ";
            }
            if (check3.isChecked()) {
                hobby += "其他  ";
            }
            String sex = "";
            if (boy.isChecked()) {
                sex += "男";
            } else {
                sex += "女";
            }
            try {
                //插入成员到数据库中
                String weightText = weight.getText().toString();
                String heightText = height.getText().toString();
                double validWeight = Double.parseDouble(weightText);
                double validHeight = Double.parseDouble(heightText);
                if (isUpdate) {
                    //当前是更新操作
                    BaseInfoOperations.getInstance().updateBaseInfo(student_id, name.getText().toString(),
                            Float.parseFloat(heightText),
                            Float.parseFloat(weightText),
                            sex, hobby, spinner.getSelectedItem().toString(), bService.getBMI(validHeight, validWeight));
                    Toast.makeText(getApplicationContext(), "学生信息更新成功", Toast.LENGTH_SHORT).show();
                } else {
                    //当前是新增操作
                    BaseInfoOperations.getInstance().insertBaseInfo(name.getText().toString(),
                            Float.parseFloat(heightText),
                            Float.parseFloat(weightText),
                            sex, hobby, spinner.getSelectedItem().toString(), bService.getBMI(validHeight, validWeight));
                    Toast.makeText(getApplicationContext(), "创建学生信息成功", Toast.LENGTH_SHORT).show();
                }
                //数据库改变之后刷新当前的学生信息列表
                refreshStudentData();
            } catch (NumberFormatException exception) {
                Toast.makeText(getApplicationContext(), "请输入有效的身高体重", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void showPopMenu(View view, final int position) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.getMenuInflater().inflate(R.menu.base_info_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.look_item:
                        Intent intent = new Intent(MainActivity.this, show_info.class);
                        intent.putExtra("姓名", allStudent.get(position).getName());
                        intent.putExtra("身高", allStudent.get(position).getHeight());
                        intent.putExtra("体重", allStudent.get(position).getWeight());
                        intent.putExtra("性别", allStudent.get(position).getSex());
                        intent.putExtra("爱好", allStudent.get(position).getHobby());
                        intent.putExtra("专业", allStudent.get(position).getMajor());
                        intent.putExtra("体质", allStudent.get(position).getBmi());
                        startActivity(intent);
                        break;
                    case R.id.modify_item:
                        updateOrInsertBaseInfoWithInputData(true, allStudent.get(position).getId());
                        break;
                    case R.id.delete_item:
                        int i = BaseInfoOperations.getInstance().deleteStudentById(allStudent.get(position).getId());
                        if (i > 0) {
                            Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                        //删除完之后刷新数据
                        refreshStudentData();
                        break;
                }
                return true;
            }
        });
        menu.show();
    }


    /**
     * 创建设置菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.zhuanyeshezhi:
                startActivity(new Intent(MainActivity.this, MajorActivity.class));
                break;
            case R.id.cunchushezhi:
                finish();
            case R.id.contacts:
                Toast.makeText(MainActivity.this, "ssss", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bService = ((BService.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bService = null;
        }

    };


    /**
     * 当第一次启动application的时候初始化最初的数据
     */
    private void firstLaunchInitializeData(){
        SharedPreferences sharedPreferences = getSharedPreferences("info2", MODE_PRIVATE);
        boolean initialized = sharedPreferences.getBoolean("initialized", false);
        if (!initialized) {
            BaseInfoOperations.getInstance().insertInitialData();
            MajorInfoOperations.getInstance().insertInitialData();
            sharedPreferences.edit().putBoolean("initialized",true).apply();
        }
    }

    private void restoreState() {
        SharedPreferences sharedPreferences = getSharedPreferences("info2", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", null);
        if (name != null) {
            this.name.setText(name);
        }
        String height = sharedPreferences.getString("height", null);
        if (height != null) {
            this.height.setText(height);
        }
        String weight = sharedPreferences.getString("weight", null);
        if (weight != null) {
            this.weight.setText(weight);
        }
        boolean boy = sharedPreferences.getBoolean("boy", false);
        this.boy.setChecked(boy);
        boolean girl = sharedPreferences.getBoolean("girl", false);
        this.girl.setChecked(girl);
        boolean check1 = sharedPreferences.getBoolean("check1", false);
        this.check1.setChecked(check1);
        boolean check2 = sharedPreferences.getBoolean("check2", false);
        this.check2.setChecked(check2);
        boolean check3 = sharedPreferences.getBoolean("check3", false);
        this.check3.setChecked(check3);
        String spinner = sharedPreferences.getString("spinner", null);
        if (spinner != null) {
            for (int i = 0; i < list2.size(); i++) {
                if (list2.get(i).equals(spinner)) {
                    this.spinner.setSelection(i);
                    break;
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.i("deepcode", "onsaveInstance");
        SharedPreferences sharedPreferences = getSharedPreferences("info2", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        String name = this.name.getText().toString();
        edit.putString("name", name);
        String height = this.height.getText().toString();
        edit.putString("height", height);
        String weight = this.weight.getText().toString();
        edit.putString("weight", weight);
        //save sex state
        edit.putBoolean("boy", this.boy.isChecked());
        edit.putBoolean("girl", this.girl.isChecked());
        //save interest state
        edit.putBoolean("check1", this.check1.isChecked());
        edit.putBoolean("check2", this.check2.isChecked());
        edit.putBoolean("check3", this.check3.isChecked());
        //save major state
        edit.putString("spinner", spinner.getSelectedItem().toString());
        edit.commit();


        super.onSaveInstanceState(outState);
    }
}
