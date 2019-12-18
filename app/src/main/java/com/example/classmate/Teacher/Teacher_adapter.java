package com.example.classmate.Teacher;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        TextView fullName;
        TextView email;
        TextView phone;
        TextView skills;
        TextView improves;
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
            viewHolder.improves = rowView.findViewById(R.id.improve);
            viewHolder.linearLayout = rowView.findViewById(R.id.relativeLayout);

            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.fullName.setText(students.get(position).getFullName());
        holder.email.setText(students.get(position).getEmail());
        holder.phone.setText(students.get(position).getPhone());
        holder.improves.setText(students.get(position).getImprove());
        holder.skills.setText(students.get(position).getSkills());

        if(students.get(position).isClicked())
            holder.linearLayout.setBackgroundColor(Color.GRAY);
        else
            holder.linearLayout.setBackgroundColor(Color.WHITE);

        return rowView;
    }


    public void setSelectedIndex(int position) {
        if(students.get(position).isClicked())
            students.get(position).setClicked(false);
        else
            students.get(position).setClicked(true);
    }


}
