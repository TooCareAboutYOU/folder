package com.activeandroid.main.entitys;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.Gson;

/**
 * Code of ZHANG/ 2018/10/15
 */
@Table(name = "Item")
public class Item extends Model {

    @Column(name = "Name")
    private String name;

    @Column(name = "Category")
    private Category category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"category\":")
                .append(category);
        sb.append('}');
        return sb.toString();
    }
}
