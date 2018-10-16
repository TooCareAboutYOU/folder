package com.roomdata.man;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    private AppDataBase db;

    public void CLickMethod(View view) {
        switch (view.getId()) {
            case R.id.btn_GetDataBase:{
                db= Room.databaseBuilder(getApplicationContext(),AppDataBase.class,"UserBase").build();

                break;
            }
        }
    }
}
