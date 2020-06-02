package com.example.q;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;


public class BService extends Service {

    private  final  IBinder mBinder = new LocalBinder();
    public class LocalBinder extends Binder{
        BService getService(){
            return BService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public String getBMI(double height,double weight){
        double BMI = weight/((height/100)*(height/100));
        String bmi = null;
        if(BMI<18.5){
            bmi="体重偏瘦";
        }
        else if(BMI>=18.5&&BMI<24){
            bmi="体重正常";
        }
        else if(BMI>=18.5&&BMI<24){
            bmi="体重偏胖";
        }else {
            bmi="体重肥胖";
        }
        return bmi;
    }

}
