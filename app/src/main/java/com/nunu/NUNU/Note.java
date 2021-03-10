package com.nunu.NUNU;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lens_table")

public class Note {
    @PrimaryKey(autoGenerate = true) //id는 PrimaryKey로 지정해주며 자동으로 생성해줌
    @ColumnInfo(name="word")
    int _id;
    String lens_name;
    String lens_type;
    int lens_cnt;
    int lens_period;
    String lens_color;
    String lens_start;
    String lens_end;

    public Note(String lens_name, String lens_type, int lens_cnt, int lens_period, String lens_color, String lens_start, String lens_end) {
        this._id = _id;
        this.lens_name = lens_name;
        this.lens_type = lens_type;
        this.lens_cnt = lens_cnt;
        this.lens_period = lens_period;
        this.lens_color = lens_color;
        this.lens_start = lens_start;
        this.lens_end = lens_end;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getLens_name() {
        return lens_name;
    }

    public void setLens_name(String lens_name) {
        this.lens_name = lens_name;
    }

    public String getLens_type() {
        return lens_type;
    }

    public void setLens_type(String lens_type) {
        this.lens_type = lens_type;
    }

    public int getLens_cnt() {
        return lens_cnt;
    }

    public void setLens_cnt(int lens_cnt) {
        this.lens_cnt = lens_cnt;
    }

    public int getLens_period() {
        return lens_period;
    }

    public void setLens_period(int lens_period) {
        this.lens_period = lens_period;
    }

    public String getLens_color() {
        return lens_color;
    }

    public void setLens_color(String lens_color) {
        this.lens_color = lens_color;
    }

    public String getLens_start() {
        return lens_start;
    }

    public void setLens_start(String lens_start) {
        this.lens_start = lens_start;
    }

    public String getLens_end() {
        return lens_end;
    }

    public void setLens_end(String lens_end) {
        this.lens_end = lens_end;
    }

}
