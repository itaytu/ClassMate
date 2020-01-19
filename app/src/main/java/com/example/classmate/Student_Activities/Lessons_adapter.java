package com.example.classmate.Student_Activities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.classmate.Models.Lesson;
import com.example.classmate.R;

import java.util.List;

public class Lessons_adapter extends ArrayAdapter<Lesson> {
    public Lessons_adapter(@NonNull Context context, @NonNull List<Lesson> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView=((Activity)getContext()).getLayoutInflater().inflate(R.layout.lessons_listview_row,parent,false);
        }
        TextView teacher = convertView.findViewById(R.id.teacher);
        TextView student = convertView.findViewById(R.id.student);
        TextView subject = convertView.findViewById(R.id.subject);
        TextView date = convertView.findViewById(R.id.Date);

        Lesson lesson = getItem(position);

        teacher.append("Teacher : " + lesson.getTeacher());
        student.append("Student : " + lesson.getsecond_student());
        subject.append("Subject : " + lesson.getSubject());
        String dateString = lesson.getLesson_date();
        date.append("Date : " + dateString);

        return convertView;
    }
}
