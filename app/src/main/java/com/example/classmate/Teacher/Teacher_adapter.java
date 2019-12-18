package com.example.classmate.Teacher;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.classmate.R;
import com.example.classmate.Student.Student;

import java.util.ArrayList;
import java.util.List;

public class Teacher_adapter extends BaseAdapter {

    private final Context context;
    private ArrayList<Student> students ;
    public Teacher_adapter(Context context , ArrayList<Student> list){
        this.context=context;
        this.students=list;

    }
    static class ViewHolder{
        TextView fullName ;
        TextView email ;
        TextView phone ;
        TextView skills ;
        TextView improves ;
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
            viewHolder.improves = rowView.findViewById(R.id.improve);

            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.fullName.setText(students.get(position).getFullName());
        holder.email.setText(students.get(position).getEmail());
        holder.phone.setText(students.get(position).getPhone());
        holder.improves.setText(students.get(position).getImprove());
        holder.skills.setText(students.get(position).getSkills());

        return rowView;
    }

//    public Teacher_adapter(Context context, List<Student> objects) {
//        super(context, 0, objects);
//    }
//
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        if(convertView == null){
//            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.listview_row,parent,false);
//        }
//
//        TextView fullName = convertView.findViewById(R.id.full_name);
//        TextView email = convertView.findViewById(R.id.email);
//        TextView phone = convertView.findViewById(R.id.phone);
//        TextView skills = convertView.findViewById(R.id.skills);
//        TextView improves = convertView.findViewById(R.id.improve);
//        Student student = getItem(position);
//
//        fullName.setText(student.getFullName());
//        email.setText(student.getEmail());
//        phone.setText(student.getPhone());
//        skills.setText(student.getSkills());
//        improves.setText(student.getImprove());
//
//        return convertView;
//    }
}
