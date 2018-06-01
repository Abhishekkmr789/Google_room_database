package gradlesetup.com.roomdatabasedemo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Appinventiv on 6/1/2018.
 */

public class UserViewModel extends AndroidViewModel {

    private LiveData<List<UserData>> userList;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userList = UserDatabase.getDatabaseInstance(getApplication()).userDao().getAllUserFromDatabase();
    }

    public LiveData<List<UserData>> getAllUserData() {
        return userList;
    }

    public void deleteUser(UserData userData) {
        new DatabaseAsyncTask(this, UserDatabase.getDatabaseInstance(getApplication()), userData, 4).execute();
    }


    public void getDataFromDatabase(LiveData<List<UserData>> userList, Boolean aBoolean) {
        this.userList = userList;
    }
}
