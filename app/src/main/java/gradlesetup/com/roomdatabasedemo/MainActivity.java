package gradlesetup.com.roomdatabasedemo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemClick {

    private RecyclerView rvUser;
    private TextView tvNoData, tvTitle;
    private FloatingActionButton fabAddUser;
    private UserListAdapter mAdapter;
    private ProgressBar pbProgress;
    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariables();
        initializeViews();
        setListeners();
        setData();
        pbProgress.setVisibility(View.VISIBLE);
        setAdapterToRecyclerView();

    }


    @Override
    protected void onResume() {
        super.onResume();
      //  new DatabaseAsyncTask(this, null, 3).execute();
    }


    /*
    *
    * method to initialize variables
    *
    * */


    private void initializeVariables() {
        mAdapter = new UserListAdapter(this, new ArrayList<UserData>(), this);
    }

    /*
    *
    * method to initialize views
    * */

    private void initializeViews() {
        rvUser = findViewById(R.id.rv_user_data);
        tvNoData = findViewById(R.id.tv_no_data);
        tvTitle = findViewById(R.id.tv_title);
        fabAddUser = findViewById(R.id.fab_add);
        pbProgress = findViewById(R.id.pb_progress);
    }


    /*
    *
    * method to set listeners
    * */
    private void setListeners() {
        fabAddUser.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddUserActivity.class)));
    }

      /*
    *
    * method to set data
    * */

    private void setData() {
        tvTitle.setText(getString(R.string.text_user_list));
        findViewById(R.id.iv_back).setVisibility(View.GONE);
    }



    /*
    *
    *
    * method to set adapter to recyclerview
    * */


    private void setAdapterToRecyclerView() {
        rvUser.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvUser.setAdapter(mAdapter);
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        if (viewModel.getAllUserData() != null) {
            viewModel.getAllUserData().observe(this, new Observer<List<UserData>>() {
                @Override
                public void onChanged(@Nullable List<UserData> userData) {
                    pbProgress.setVisibility(View.GONE);
                    mAdapter.notifyData(userData);
                    if (userData.size() > 0) {
                        tvNoData.setVisibility(View.GONE);
                        rvUser.scrollToPosition(userData.size() - 1);
                    } else {
                        tvNoData.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

    }


    /*
    *
    * method to get data from database
    * */


   /* public void getDataFromDatabase(ArrayList<UserData> userlist, Boolean aBoolean) {
        pbProgress.setVisibility(View.GONE);
        if (userlist != null && aBoolean) {
            mUserList.clear();
            mUserList.addAll(userlist);
            if (mUserList != null && mUserList.size() > 0) {
                tvNoData.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
                rvUser.scrollToPosition(mUserList.size() - 1);
            } else {
                tvNoData.setVisibility(View.VISIBLE);
            }
        }
    }
*/

    @Override
    public void onItemClick(UserData userData) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_custom_alert, null);
        dialogBuilder.setView(view);
        AlertDialog dialog = dialogBuilder.create();
        view.findViewById(R.id.tv_update).setOnClickListener(view1 -> {
            dialog.dismiss();
            startActivity(new Intent(MainActivity.this, AddUserActivity.class).putExtra("user_info", userData));
        });
        view.findViewById(R.id.tv_delete).setOnClickListener(view1 -> {
            dialog.dismiss();
            pbProgress.setVisibility(View.VISIBLE);
            viewModel.deleteUser(userData);
           /* mUserList.remove(position);
            if (mUserList.size() > 0) {
                mAdapter.notifyDataSetChanged();
                rvUser.scrollToPosition(mUserList.size() - 1);
            } else {
                tvNoData.setVisibility(View.VISIBLE);
            }*/
            //     UserDatabase.getDatabaseInstance(MainActivity.this).userDao().deleteUserFromDatabase(mUserList.get(position));
        });
        view.findViewById(R.id.tv_cancel).setOnClickListener(view1 -> dialog.dismiss());

        dialog.show();
    }

       /*
    * method to update ui
    * */

    public void UpdateUI(int queryType, Boolean aBoolean) {
        pbProgress.setVisibility(View.GONE);
    }
}
