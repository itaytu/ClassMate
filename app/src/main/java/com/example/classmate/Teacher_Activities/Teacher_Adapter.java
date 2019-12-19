package com.example.classmate.Teacher_Activities;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.classmate.Models.Student;
import com.example.classmate.R;

import java.util.ArrayList;

public class Teacher_Adapter extends BaseAdapter {

    private final Context context;
    private ArrayList<Student> students;
    public boolean [] isCliced;

    public Teacher_Adapter(Context context, ArrayList<Student> list){
        this.context=context;
        students = new ArrayList<>();
        isCliced = new boolean[list.size()];
        for(Student student : list) {
            this.students.add(student);
        }

    }


    static class ViewHolder{
        TextView fullName;
        TextView email;
        TextView phone;
        TextView skills;
        TextView weaknesses;
        LinearLayout linearLayout;
    }


    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Student getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.listview_row,null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.fullName=rowView.findViewById(R.id.full_name);
            viewHolder.email = rowView.findViewById(R.id.email);
            viewHolder.phone = rowView.findViewById(R.id.phone);
            viewHolder.skills = rowView.findViewById(R.id.skills);
            viewHolder.weaknesses = rowView.findViewById(R.id.improve);
            viewHolder.linearLayout = rowView.findViewById(R.id.relativeLayout);

            rowView.setTag(viewHolder);
        }
        Log.d("Teacher adapter", "reach here");
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.fullName.setText(students.get(position).getFullName());
        holder.email.setText(students.get(position).getEmail());
        holder.phone.setText(students.get(position).getPhone());
        String listString = TextUtils.join(", ", students.get(position).getWeaknesses());
        holder.weaknesses.setText(listString);
        listString=TextUtils.join(", ", students.get(position).getSkills());
        holder.skills.setText(listString);

        if(isCliced[position])
            holder.linearLayout.setBackgroundColor(Color.GRAY);
        else
            holder.linearLayout.setBackgroundColor(Color.WHITE);

        return rowView;
    }


    public void setSelectedIndex(int position) {
        if(isCliced[position]){
            isCliced[position]=false;
        }
        else
            isCliced[position]=true;
    }



}
