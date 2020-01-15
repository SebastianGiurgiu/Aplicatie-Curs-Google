package com.example.prototip;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class Aliment {


    private String name;

    private Integer proteine;

    private Integer carbohidrati;

    private Integer grasimi;


    public Aliment(){

    }

    public Aliment(String name, Integer proteine, Integer carbohidrati, Integer grasimi) {
        this.name = name;
        this.proteine = proteine;
        this.carbohidrati = carbohidrati;
        this.grasimi = grasimi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProteine() {
        return proteine;
    }

    public void setProteine(Integer proteine) {
        this.proteine = proteine;
    }

    public Integer getCarbohidrati() {
        return carbohidrati;
    }

    public void setCarbohidrati(Integer carbohidrati) {
        this.carbohidrati = carbohidrati;
    }

    public Integer getGrasimi() {
        return grasimi;
    }

    public void setGrasimi(Integer grasimi) {
        this.grasimi = grasimi;
    }
}
