package com.example.q;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

public class show_info extends AppCompatActivity {
    private TextView name;
    private TextView height;
    private TextView weight;
    private TextView sex;
    private TextView hobby;
    private TextView major;
    private TextView bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);
        name = findViewById(R.id.name2);
        name.setText(getIntent().getStringExtra("姓名"));

        height = findViewById(R.id.height2);
        float heightValue = getIntent().getFloatExtra("身高", 0f);
        height.setText(String.valueOf(heightValue));

        weight = findViewById(R.id.weight2);
        float weightValue = getIntent().getFloatExtra("体重", 0f);
        weight.setText(String.valueOf(weightValue));

        sex = findViewById(R.id.sex2);
        sex.setText(getIntent().getStringExtra("性别"));

        hobby = findViewById(R.id.hobby2);
        hobby.setText(getIntent().getStringExtra("爱好"));

        major = findViewById(R.id.major2);
        major.setText(getIntent().getStringExtra("专业"));

        bmi = findViewById(R.id.bmi2);
        bmi.setText(getIntent().getStringExtra("体质"));

        //返回button的事件
        Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(show_info.this,MainActivity.class);
                startActivity(intent);
            }
        });
}

    }

