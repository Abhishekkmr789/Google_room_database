package gradlesetup.com.roomdatabasedemo;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by appinventiv on 28/12/17.
 */

public class DatabaseAsyncTask extends AsyncTask<Boolean, Void, Boolean> {
    private UserData userData;
    private int queryType;
    private UserViewModel context;
    private AddUserActivity addUserActivity;
    private LiveData<List<UserData>> userList;
    private UserDatabase userDatabase;

    public DatabaseAsyncTask(UserViewModel context, UserDatabase userDatabase, UserData userData, int queryType) {
        this.userData = userData;
        this.queryType = queryType;
        this.context = context;
        this.userDatabase = userDatabase;
       /* if (queryType == 3) {
            userList = new ArrayList<>();
        }*/

    }

    public DatabaseAsyncTask(AddUserActivity addUserActivity, UserDatabase userDatabase, UserData userData, int queryType) {
        this.userData = userData;
        this.queryType = queryType;
        this.addUserActivity = addUserActivity;
        this.userDatabase = userDatabase;
    }

    @Override
    protected Boolean doInBackground(Boolean... booleen) {
        boolean response = false;
        switch (queryType) {
            case 1:
                if (userDatabase.userDao().getUserFromDatabase(userData.getUserId()) != null) {
                    response = false;
                } else {
                    userDatabase.userDao().addUserToDatabase(userData);
                    response = true;
                }
                break;
            case 2:
                userDatabase.userDao().updateUserDataInDataBase(userData);
                response = true;
                break;
            case 3:
                userList = userDatabase.userDao().getAllUserFromDatabase();
                response = true;
                break;
            case 4:
                userDatabase.userDao().deleteUserFromDatabase(userData);
                response = true;
                break;

        }
        return response;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (context != null) {
            if (queryType == 3) {
                ((UserViewModel) context).getDataFromDatabase(userList, aBoolean);
            }
        } else if (addUserActivity != null) {
            ((AddUserActivity) addUserActivity).UpdateUI(queryType, aBoolean);
        }


    }
    }
