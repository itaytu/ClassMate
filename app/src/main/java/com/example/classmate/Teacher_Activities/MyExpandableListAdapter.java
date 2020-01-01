package com.example.classmate.Teacher_Activities;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.classmate.Models.Student;
import com.example.classmate.R;

import java.util.HashMap;
import java.util.List;

public class  MyExpandableListAdapter extends BaseExpandableListAdapter {
    private HashMap<String, List<Student>> hashMap ;
    private HashMap<String, List<Student>> listHashMap ;
    private String [] mListHeaderGroup;

    public MyExpandableListAdapter(HashMap<String, List<Student>> hashMap) {
        this.hashMap = hashMap;
        this.mListHeaderGroup = hashMap.keySet().toArray(new String[0]);
    }

    @Override
    public int getGroupCount() {
        return mListHeaderGroup.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return hashMap.get(mListHeaderGroup[groupPosition]).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListHeaderGroup[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return hashMap.get(mListHeaderGroup[groupPosition]).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition*childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_list_group, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.textView);
        textView.setText(String.valueOf(getGroup(groupPosition)));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView =LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_list_item, parent, false);
        }
        TextView fullName = convertView.findViewById(R.id.full_name);
        TextView email = convertView.findViewById(R.id.email);
        TextView phone = convertView.findViewById(R.id.phone);
        TextView skills = convertView.findViewById(R.id.skills);
        TextView weaknesses = convertView.findViewById(R.id.weaknesses);

        Student student = (Student) getChild(groupPosition,childPosition);
        fullName.setText("Full-name: "+student.getFullName());
        email.setText("Email: "+student.getEmail());
        phone.setText("Phone: "+student.getPhone());
        String join = TextUtils.join(", ",student.getSkills());
        join ="Skills: "+join;
        skills.setText(join);
        join=TextUtils.join(", ",student.getWeaknesses());
        join ="Weaknesses: "+join;
        weaknesses.setText(join);



        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
