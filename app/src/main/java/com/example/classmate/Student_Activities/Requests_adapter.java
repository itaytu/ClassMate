package com.example.classmate.Student_Activities;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.classmate.Models.Request;
import com.example.classmate.R;

import java.util.ArrayList;
import java.util.List;

public class Requests_adapter extends BaseAdapter {

    private final Context context;
    private ArrayList<Request> requests;


    public Requests_adapter(Context context, ArrayList<Request> requests) {
        this.context = context;
        this.requests = new ArrayList<>();
        this.requests.addAll(requests);
    }


    static class ViewHolder{
        TextView fullName;
        TextView subject;
    }

    @Override
    public int getCount() {
        return requests.size();
    }

    @Override
    public Request getItem(int position) {
        return requests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public ArrayList<Request> getRequests() {
        return requests;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            Requests_adapter.ViewHolder viewHolder = new Requests_adapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.requests_listview_row, null);

            viewHolder.fullName = convertView.findViewById(R.id.fullName);
            viewHolder.subject = convertView.findViewById(R.id.subject);

            convertView.setTag(viewHolder);
        }

        Requests_adapter.ViewHolder viewHolder = (Requests_adapter.ViewHolder) convertView.getTag();

        viewHolder.fullName.setText("Full Name: " + requests.get(position).getRequesting_student());
        viewHolder.fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tmp = s.toString();
                tmp = tmp.replace("Full Name: " , "");
                requests.get(position).setResponding_student(tmp);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        viewHolder.subject.setText(String.format("Subject: %s", requests.get(position).getLesson_subject()));
        viewHolder.subject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tmp = s.toString();
                tmp = tmp.replace("Subject: ", "");
                requests.get(position).setLesson_subject(tmp);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        return convertView;
    }
}

