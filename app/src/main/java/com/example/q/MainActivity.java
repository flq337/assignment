package com.example.q;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import android.content.ComponentName;
import android.content.ServiceConnection;

import android.content.Context;

import android.os.IBinder;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Toast;
import android.app.Service;
import android.widget.ListView;
import android.os.Bundle;
import android.view.View;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.ContextMenu;
import android.content.Intent;
import android.widget.AdapterView;
import android.view.MenuItem;
import android.widget.CheckBox;
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
    String hobby = "";
    String sex= "";
    String major = "";
    List<Student> students = new ArrayList<Student>();
    List<String> list = new ArrayList<String>();//给listview
    List<String>list2 = new ArrayList<String>();//给spiner
    ArrayAdapter<String> arr_adapter2;
    ArrayAdapter<String> arr_adapter1;
    public int num=3;
    String string;
    String spinner_id;
    String result;
    private BService bService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jibenxinxi);

        TextView textView2 =findViewById(R.id.textView2);//姓名
        TextView textView3 =findViewById(R.id.textView3);//身高

         name = findViewById(R.id.name);//姓名文本
         height = findViewById(R.id.height);//身高文本
         weight = findViewById(R.id.weight);//身高文本

         boy =  findViewById(R.id.boy);
         girl = findViewById(R.id.girl);

        check1 = findViewById(R.id.travel);
        check2 = findViewById(R.id.sport);
        check3 = findViewById(R.id.other);

        spinner = findViewById(R.id.spinner);
        listView = findViewById(R.id.listview);

        //给listView添加字段
        list.add("赵明");
        students.add(new Student("赵明","175","72","男","旅游，运动","计算机","正常"));
        list.add("李晓");
        students.add(new Student("李晓","173","86","女","运动","软件工程","偏胖"));
        list.add("王丽");
        students.add(new Student("王丽","163","48","女","其他","物联网","正常"));

        //给spinner
        list2.add("计算机");
        list2.add("软件工程");
        list2.add("物联网");

        //给spinner
        arr_adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list2);
        arr_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arr_adapter1);

        //给listView
        arr_adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        arr_adapter2.setDropDownViewResource(android.R.layout.simple_list_item_1);
        listView.setAdapter(arr_adapter2);

        registerForContextMenu(listView);
        //快捷菜单
        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("选择操作");
                menu.add(0,0,0,"查看");
                menu.add(0,1,0,"删除");
            }
        });


        //添加按钮点击事件 并判断姓名栏是否为空
        final Button button2 = findViewById(R.id.button2);//添加button
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText()==null||name.getText().length()==0) {
                    Toast.makeText(getApplicationContext(),"姓名为空！", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(check1.isChecked()){
                        hobby+="旅游  ";
                    }
                    if(check2.isChecked()){

                        hobby+="运动  ";
                    }
                    if(check3.isChecked()){
                        hobby+="其他  ";
                    }
                    if(boy.isChecked()){
                        sex+="男";
                    }
                    else{
                        sex +="女";
                    }
                    onItemSelected();
                    list.add(name.getText().toString());
                    listView.setAdapter(arr_adapter2);
                    students.add(new Student(name.getText().toString(),height.getText().toString(),weight.getText().toString(),sex,hobby,spinner_id,result));
                    Toast.makeText(getApplicationContext(),students.get(num).speak(),Toast.LENGTH_SHORT).show();
                    num++;
                    sex="";
                    hobby="";
                }
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
                if(keyCode>=7&&keyCode<=16||keyCode==56||keyCode==67){
                    return false;
                }
                else {
                    Toast.makeText(getApplicationContext(),"error：只能输入数字和字符'.'",Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        });

        weight.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode>=7&&keyCode<=16||keyCode==56||keyCode==67){
                    return false;
                }
                else {
                    Toast.makeText(getApplicationContext(),"Error: 只能输入数字或者字符'.'",Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        });

        //计算BMI
        Button jisuan = findViewById(R.id.button6);
        final Intent intent = new Intent(MainActivity.this,BService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        jisuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = bService.getBMI(Double.valueOf(height.getText().toString()),Double.valueOf(weight.getText().toString()));
                Toast.makeText(getApplicationContext(),name.getText().toString()+result,Toast.LENGTH_SHORT).show();

            }
        });

        //返回上一级
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
//查看信息
    public boolean onContextItemSelected(MenuItem chakan){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)chakan.getMenuInfo();
        String id = String.valueOf(info.id);
        int choice = chakan.getItemId();
        if (choice==0) {
            Intent intent = new Intent(this,show_info.class);
            intent.putExtra("姓名",students.get(Integer.parseInt(id)).getName());
            intent.putExtra("身高",students.get(Integer.parseInt(id)).getHeight());
            intent.putExtra("体重",students.get(Integer.parseInt(id)).getWeight());
            intent.putExtra("性别",students.get(Integer.parseInt(id)).getSex());
            intent.putExtra("爱好",students.get(Integer.parseInt(id)).getHobby());
            intent.putExtra("专业",students.get(Integer.parseInt(id)).getMajor());
            intent.putExtra("体质",students.get(Integer.parseInt(id)).getBmi());
            startActivity(intent);
            return true;
        }

        else {
            list.remove(Integer.parseInt(id));
            students.remove(Integer.parseInt(id));
            listView.setAdapter(arr_adapter2);
            return super.onContextItemSelected(chakan);
        }
    }

    private void onItemSelected() {
        spinner_id = spinner.getSelectedItem().toString();
    }

    @Override
    //设置菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);//caidan
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.shezhi:
                Toast.makeText(this,"专业设置",Toast.LENGTH_SHORT).show();
//                break;
//            case android.R.id.home:
//                finish();
        }

        Intent intent = new Intent(MainActivity.this,Major.class);
        intent.putExtra("string",string);
        startActivity(intent);
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


}
