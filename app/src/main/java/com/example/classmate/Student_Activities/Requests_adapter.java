package com.example.classmate.Student_Activities;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.classmate.Models.Request;
import com.example.classmate.R;

import java.util.List;

public class Requests_adapter extends ArrayAdapter<Request> {
    public Requests_adapter(@NonNull Context context, @NonNull List<Request> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView ==null){
            convertView=((Activity)getContext()).getLayoutInflater().inflate(R.layout.requests_listview_row,parent,false);
        }
        TextView fullname = convertView.findViewById(R.id.fullName);
        TextView subject = convertView.findViewById(R.id.subject);

        Request request = getItem(position);
        fullname.append("Full-Name : "+request.getRequesting_student());
        subject.append("Subject : "+request.getLesson_subject());

        return convertView;
    }
}
