package com.litepal.main.beans;

import org.litepal.crud.LitePalSupport;

/**
 * Code of ZHANG/ 2018/10/15
 */
public class Book extends LitePalSupport {

    private int bookId;
    private String name;

    public Book(int bookId, String name) {
        this.bookId = bookId;
        this.name = name;
    }

    @Override
    protected long getBaseObjId() {
        return super.getBaseObjId();
    }

    public long key(){
        return getBaseObjId();
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"objId\":")
                .append(getBaseObjId());
        sb.append("\"bookId\":")
                .append(bookId);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
