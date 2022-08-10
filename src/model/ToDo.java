package model;

import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class ToDo {
    private int id;
    private String name;
    private boolean completed;

    public ToDo(int id, String name) {
        this.id = id;
        this.name = name;
        this.completed = false;
    }

    public ToDo(int id, String name, boolean completed) {
        this.id = id;
        this.name = name;
        this.completed = completed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void complete(){
        if(this.completed){
            System.out.println("This to do has already been completed");
        }else{
            this.completed = true;
        }
    }

    @Override
    public String toString() {
        return id+" : name='" + name + '\'' +
                ", completed=" + (completed ? "(V)" : "(X)");
    }

}
