package com.example.q;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.q.bean.Student;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {

    private int resourceId;

    public StudentAdapter(Context context,
                        int textViewResourceId, List<Student> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    //getView方法在每个子项被滚动到屏幕内的时候调用
    @Override
    public View getView(int postion, View convertView, ViewGroup parent){
        Student student = getItem(postion);
        View view;
        StudentViewHolder studentViewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            studentViewHolder = new StudentViewHolder();
            studentViewHolder.studentName = (TextView) view.findViewById(R.id.base_info_item_name);
            view.setTag(studentViewHolder);//缓存起来
        }else{
            view = convertView;//直接对convertView进行重用
            studentViewHolder = (StudentViewHolder) view.getTag();
        }

        studentViewHolder.studentName.setText(student.getName());
        //返回布局
        return view;
    }
}
class StudentViewHolder {
    TextView studentName;
}
