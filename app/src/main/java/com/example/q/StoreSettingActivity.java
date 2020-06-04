package com.example.q;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class StoreSettingActivity extends AppCompatActivity {


    private StoreSettingType tempStoreSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_setting);
        //根据当前的存储设置恢复页面
        tempStoreSetting = MainActivity.storeSettingType;
        if (MainActivity.storeSettingType == StoreSettingType.DATABASE) {
            ((RadioButton) findViewById(R.id.database_store)).setChecked(true);
        }else if (MainActivity.storeSettingType == StoreSettingType.INTERNAL){
            ((RadioButton) findViewById(R.id.internal_store)).setChecked(true);
        }else if (MainActivity.storeSettingType == StoreSettingType.EXTERNAL){
            ((RadioButton) findViewById(R.id.external_store)).setChecked(true);
        }
        ((RadioGroup) findViewById(R.id.store_radioGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.database_store:
                        tempStoreSetting = StoreSettingType.DATABASE;
                        break;
                    case R.id.internal_store:
                        tempStoreSetting = StoreSettingType.INTERNAL;
                        break;
                    case R.id.external_store:
                        tempStoreSetting = StoreSettingType.EXTERNAL;
                        break;
                }
            }
        });
        //当点击存储设置的时候更新首页的存储配置,并关闭本页面
        findViewById(R.id.ensure_store_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.storeSettingType = tempStoreSetting;
                finish();
            }
        });



    }
}