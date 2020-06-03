package com.example.q;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.q.bean.Major;

import java.util.List;

public class MajorAdapter extends ArrayAdapter<Major> {

    private int resourceId;

    public MajorAdapter(Context context,
                        int textViewResourceId, List<Major> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    //getView方法在每个子项被滚动到屏幕内的时候调用
    @Override
    public View getView(int postion, View convertView, ViewGroup parent){
        Major major = getItem(postion);
        View view;
        MajorViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new MajorViewHolder();
            viewHolder.majorName = (TextView) view.findViewById(R.id.major_info_item_name);
            view.setTag(viewHolder);//缓存起来
        }else{
            view = convertView;//直接对convertView进行重用
            viewHolder = (MajorViewHolder) view.getTag();
        }

        viewHolder.majorName.setText(major.getMajor());
        //返回布局
        return view;
    }
}

class MajorViewHolder{
    TextView majorName;
}
