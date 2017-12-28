package gradlesetup.com.roomdatabasedemo;

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
    private Context context;
    private ArrayList<UserData> userList;

    public DatabaseAsyncTask(Context context, UserData userData, int queryType) {
        this.userData = userData;
        this.queryType = queryType;
        this.context = context;
        if (queryType == 3) {
            userList = new ArrayList<>();
        }

    }

    @Override
    protected Boolean doInBackground(Boolean... booleen) {
        boolean response = false;
        switch (queryType) {
            case 1:
                if (UserDatabase.getDatabaseInstance(context).userDao().getUserFromDatabase(userData.getUserId()) != null) {
                    response = false;
                } else {
                    UserDatabase.getDatabaseInstance(context).userDao().addUserToDatabase(userData);
                    response = true;
                }
                break;
            case 2:
                UserDatabase.getDatabaseInstance(context).userDao().updateUserDataInDataBase(userData);
                response = true;
                break;
            case 3:
                userList.addAll(UserDatabase.getDatabaseInstance(context).userDao().getAllUserFromDatabase());
                response = true;
                break;
            case 4:
                UserDatabase.getDatabaseInstance(context).userDao().deleteUserFromDatabase(userData);
                response = true;
                break;

        }
        return response;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (context != null) {
            if (context instanceof AddUserActivity) {
                ((AddUserActivity) context).UpdateUI(queryType, aBoolean);
            } else if (context instanceof MainActivity) {
                if (queryType == 3) {
                    ((MainActivity)context).getDataFromDatabase(userList,aBoolean);
                } else {
                    ((MainActivity) context).UpdateUI(queryType, aBoolean);
                }
                }
            }
        }
    }
