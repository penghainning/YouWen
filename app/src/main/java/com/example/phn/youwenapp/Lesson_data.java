package com.example.phn.youwenapp;

/**
 * Created by PHN on 2016/7/9.
 */
public class Lesson_data {
    private  String lessonurl;//课程连接
    private  String number;//课程序号
    private  String title;//课程标题
    private  String visit;//访问数

    public Lesson_data(String lessonurl,String number,String title,String visit)
    {
        this.lessonurl=lessonurl;
        this.number=number;
        this.title=title;
        this.visit=visit;
    }

    public String getLessonurl() {
        return lessonurl;
    }

    public String getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getVisit() {
        return visit;
    }

    public void setLessonurl(String lessonurl) {
        this.lessonurl = lessonurl;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }
}
