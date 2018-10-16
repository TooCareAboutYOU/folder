package com.roomdata.man;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Code of ZHANG/ 2018/10/12
 */
@Database(entities = {User.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase{
    public abstract UserDao userDao();
}
