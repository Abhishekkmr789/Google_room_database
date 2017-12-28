package gradlesetup.com.roomdatabasedemo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by appinventiv on 28/12/17.
 */

@Dao
public interface UserDAO {


    @Insert
    void addUserToDatabase(UserData userData);

    @Delete
    void deleteUserFromDatabase(UserData userData);

    @Query("Select * from user_table")
    List<UserData> getAllUserFromDatabase();

    @Query("Select * from user_table where user_id = :userId")
    UserData getUserFromDatabase(String userId);

    @Update()
    void updateUserDataInDataBase(UserData userData);

    @Query("Select count(*) from user_table")
    int countUserData();
}

