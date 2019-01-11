package com.databinding.main.viewmodels;

import android.databinding.BaseObservable;

public class RecyclerViewBean extends BaseObservable{

    private String id;
    private String name;
    private String blog;

    public RecyclerViewBean(String id, String name, String blog) {
        this.id = id;
        this.name = name;
        this.blog = blog;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"blog\":\"")
                .append(blog).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
