package com.litepal.main;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.litepal.main.beans.Book;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.litepal.Operator;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MMainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] strings={Permission.READ_EXTERNAL_STORAGE,Permission.WRITE_EXTERNAL_STORAGE};
        AndPermission.with(this)
                .runtime()
                .permission(strings)
                .onGranted(data -> {
                    Toast.makeText(this, "存储权限开启成功", Toast.LENGTH_SHORT).show();
                })
                .onDenied(data -> {
                    Toast.makeText(this, "存储权权限开启失败", Toast.LENGTH_LONG).show();
                })
                .start();


    }

    public void ClickMethod(View view) {
        switch (view.getId()) {
            case R.id.btn_Insert:{
                Book book=new Book(new Random().nextInt(1000),"当前时间"+System.currentTimeMillis());
                if (book.save()) {
                    Toast.makeText(this, "添加成功😁", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "添加失败😭", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.btn_Select:{
                List<Book> books=Operator.findAll(Book.class);
                for (Book book1 : books) {
                    Log.i(TAG, "打印: "+book1.toString());
                }
                break;
            }
            case R.id.btn_AsyncSelect:{
                Operator.findAllAsync(Book.class).listen(list -> {
                    for (Book book : list) {
                        Log.i(TAG, "onFinish: "+book.toString()+"\n");
                    }
                });
                break;
            }
            case R.id.btn_Update:{
                AppCompatEditText etBookID=findViewById(R.id.acEt_BookId);
                AppCompatEditText etBookName=findViewById(R.id.acEt_BookName);
                if (!TextUtils.isEmpty(etBookID.getText().toString().trim()) && !TextUtils.isEmpty(etBookName.getText().toString().trim())) {

                    List<Book> bookList=Operator.where("bookId=?",etBookID.getText().toString().trim()).find(Book.class);
                    if (bookList.size() ==0) {
                        Toast.makeText(this, "书不存在", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String remark="";
                    for (Book book : bookList) {
                        remark=book.getName();

                        ContentValues contentValue=new ContentValues();
                        contentValue.put("name",etBookName.getText().toString());
                        Operator.update(Book.class,contentValue,book.key());
                    }

                    List<Book> bookLists=Operator.where("bookId=?",etBookID.getText().toString().trim()).find(Book.class);
                    if (bookList.size() != 0) {
                        if (!bookLists.get(0).getName().equals(remark)) {
                            Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "更新成功: "+bookLists.get(0).toString());
                        }
                    }


                }else {
                    Toast.makeText(this, "书编号或书名不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.btn_Delete:{
                AppCompatEditText bookId=findViewById(R.id.acEt_BookId_delete);
                if (!TextUtils.isEmpty(bookId.getText().toString())) {

                    List<Book> bookList=Operator.where("bookId=?",bookId.getText().toString().trim()).find(Book.class);
                    if (bookList.size() == 0) {
                        Toast.makeText(this, "书不存在", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (Book book : bookList) {
//                        Operator.delete(Book.class,book.key());
                        if (book.isSaved()) {
                            Log.w(TAG, "删除: "+book.delete());
                        }

                    }

                    List<Book> bookLists=Operator.where("bookId=?",bookId.getText().toString().trim()).find(Book.class);
                    if (bookLists.size() == 0) {
                        Toast.makeText(this, "书删除成功！", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }else {
                    Toast.makeText(this, "书编号不能为空!", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            case R.id.btn_Clear:{
                Operator.deleteAll(Book.class,"");
                break;
            }
        }
    }

}
