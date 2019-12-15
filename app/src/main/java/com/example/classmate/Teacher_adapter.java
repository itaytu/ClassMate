package com.example.classmate;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Teacher_adapter extends ArrayAdapter<Student> {

    public Teacher_adapter(Context context, List<Student> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.listview_row,parent,false);
        }

        TextView fullname = convertView.findViewById(R.id.full_name);
        TextView email = convertView.findViewById(R.id.email);
        TextView phone = convertView.findViewById(R.id.phone);
        TextView skills = convertView.findViewById(R.id.skills);
        TextView improves = convertView.findViewById(R.id.improve);
        Student student = getItem(position);

        fullname.setText(student.getFullName());
        email.setText(student.getEmail());
        phone.setText(student.getPhone());
        skills.setText(student.getSkills());
        improves.setText(student.getImprove());

        return convertView;
    }
}
