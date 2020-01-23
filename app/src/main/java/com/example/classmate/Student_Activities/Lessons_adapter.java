package com.example.classmate.Student_Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.classmate.Models.Lesson;
import com.example.classmate.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Lessons_adapter extends BaseAdapter{
    private final Context context;
    private ArrayList<Lesson> lessons;

    public Lessons_adapter(Context context,ArrayList<Lesson> lessons) {
        this.context = context;
        this.lessons = new ArrayList<>();
        this.lessons=lessons;
    }
    static class ViewHolder{
        TextView Teacher;
        TextView Student;
        TextView Subject;
        TextView Date;
    }

    @Override
    public int getCount() {
        return lessons.size();
    }

    @Override
    public Lesson getItem(int position) {
        return lessons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            ViewHolder viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lessons_listview_row, null);

            viewHolder.Teacher = convertView.findViewById(R.id.teacher);
            viewHolder.Student = convertView.findViewById(R.id.student);
            viewHolder.Subject = convertView.findViewById(R.id.subject);
            viewHolder.Date = convertView.findViewById(R.id.Date);

            convertView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        Lesson lesson = getItem(position);
        viewHolder.Teacher.setText("Teacher: "+lesson.getTeacher());
        viewHolder.Teacher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tmp = s.toString();
                tmp = tmp.replace("Teacher: " , "");
                lessons.get(position).setTeacher(tmp);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        viewHolder.Student.setText("Student: "+lesson.getsecond_student());
        viewHolder.Student.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String tmp = s.toString();
                tmp = tmp.replace("Student: " , "");
                lessons.get(position).setsecond_student(tmp);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        viewHolder.Subject.setText("Subject: "+lesson.getSubject());
        viewHolder.Subject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tmp = s.toString();
                tmp = tmp.replace("Subject: ", "");
                lessons.get(position).setSubject(tmp);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        viewHolder.Date.setText("Date: "+lesson.getLesson_date().toString());
        viewHolder.Date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tmp = s.toString();
                tmp = tmp.replace("Date: ", "");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                try {
                    date = dateFormat.parse(tmp);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                lessons.get(position).setLesson_date(date);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return convertView;
    }
}