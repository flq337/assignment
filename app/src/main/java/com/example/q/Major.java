package com.example.q;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Major extends AppCompatActivity {
    private ListView listView;
    TextView textView;
    EditText show;
    List<Student> students = new ArrayList<>();
    ArrayList<String> list1 = new ArrayList<>();
    ArrayAdapter<String> arr_adapter2;
    EditText editText;
    String text;
    String text2;
    String str1=null;
    public static List<Activity> list2 =new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);
        show = findViewById(R.id.editText);
        textView = findViewById(R.id.textView16);
        textView.setText(getIntent().getStringExtra("str1"));//传值
        str1 = textView.getText().toString();
        list1 = new ArrayList<>(Arrays.asList(str1.split(",")));//传的值打包给List1也就是Arraylist
        //再把ArrayList放入listView中，创建一个适配器，最后注册给listView
        listView=findViewById(R.id.listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list1);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        //添加button
        Button tianjia = findViewById(R.id.button4);
        tianjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show.getText()==null||show.getText().length()==0){
                    Toast.makeText(getApplicationContext(),"专业名为空",Toast.LENGTH_SHORT).show();
                }else {
                    list1.add(show.getText().toString());
                }
            }
        });

        //返回button
        Button back = findViewById(R.id.button5);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }

            public void exit(){
                for(Activity action:list2){
                    action.finish();
                }
                System.exit(0);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.xiugaishanchu,menu);
        menu.add(0,0,0,"修改");
        menu.add(0,1,0,"删除");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String id = String.valueOf(info.id);
        int choice = item.getItemId();
        if(choice == 0){
            text = students.get(Integer.parseInt(id)).getMajor();
            text2 = text;
            text = editText.getText().toString();
        }else{
            list1.remove(Integer.parseInt(id));
            students.remove(Integer.parseInt(id));
            listView.setAdapter(arr_adapter2);
            return super.onContextItemSelected(item);
        }
        return false;
    }


    }

