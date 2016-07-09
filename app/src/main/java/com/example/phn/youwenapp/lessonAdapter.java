package com.example.phn.youwenapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by PHN on 2016/7/9.
 */
public class lessonAdapter extends BaseAdapter {

    private LinkedList<Lesson_data> mData;
    private Context mContext;

    public lessonAdapter(LinkedList<Lesson_data>mData,Context mContext)
    {
        this.mContext=mContext;
        this.mData=mData;
    }
    public int getCount() {
        return mData.size();
    }
    public Object getItem(int position) {
        return null;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if(convertView==null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lessondata_layout, parent, false);
           holder=new ViewHolder();
            holder.lesson_number = (TextView) convertView.findViewById(R.id.lesson_number);
            holder.lesson_title = (TextView) convertView.findViewById(R.id.lesson_title);
            holder.lesson_visit = (TextView) convertView.findViewById(R.id.lesson_visit);
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.lesson_number.setText(mData.get(position).getNumber());
        holder.lesson_title.setText(mData.get(position).getTitle());
        holder.lesson_visit.setText(mData.get(position).getVisit());
        return convertView;

    }
    static class ViewHolder{
        TextView lesson_number;
        TextView lesson_title;
        TextView lesson_visit;
    }


}
