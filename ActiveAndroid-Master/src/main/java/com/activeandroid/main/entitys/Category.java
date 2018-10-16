package com.activeandroid.main.entitys;

import android.content.Intent;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.Gson;

import java.security.acl.Group;
import java.util.List;
import java.util.PrimitiveIterator;

/**
 * Code of ZHANG/ 2018/10/15
 */
@Table(name = "Category")
public class Category extends Model {

    @Column(name = "UserId")
    private int userId;

    @Column(name = "Name")
    private String name;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> items(){
        return getMany(Item.class,"Category");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"userId\":")
                .append(userId);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
