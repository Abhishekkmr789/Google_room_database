package gradlesetup.com.roomdatabasedemo;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by appinventiv on 28/12/17.
 */

@Database(entities = UserData.class,version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static  UserDatabase instance;


    public abstract UserDAO userDao();


    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    public static UserDatabase getDatabaseInstance(Context context){
        if (instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "user_database")
               //     .allowMainThreadQueries()
                    .build();
        }
        return instance;

    }

    public static void destroyDatabaseInstance(){
        instance=null;
    }
}
