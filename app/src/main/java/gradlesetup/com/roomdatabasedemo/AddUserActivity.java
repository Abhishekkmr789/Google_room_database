package gradlesetup.com.roomdatabasedemo;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AddUserActivity extends AppCompatActivity {

    private EditText etName, etCity, etPhone,etUserId;
    private Button btnAdd;
    private TextView tvTitle;
    private ProgressBar pbProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        initializeViews();
        setListeners();
        setData();
    }

    /*
    *
    * method to initialize views
    * */

    private void initializeViews() {
        etUserId = findViewById(R.id.et_user_id);
        etName = findViewById(R.id.et_name);
        etCity = findViewById(R.id.et_city);
        etPhone = findViewById(R.id.et_phone);
        btnAdd = findViewById(R.id.btn_add);
        tvTitle = findViewById(R.id.tv_title);
        pbProgress=findViewById(R.id.pb_progress);
    }


    /*
    *
    * method to set listeners
    * */
    private void setListeners() {
        findViewById(R.id.iv_back).setOnClickListener(view -> finish());
        btnAdd.setOnClickListener(view -> {
            if (etUserId.getText().toString().isEmpty()) {
                Toast.makeText(AddUserActivity.this, getString(R.string.text_error_user_id),Toast.LENGTH_LONG).show();
            } else if (etName.getText().toString().isEmpty()) {
                Toast.makeText(AddUserActivity.this, getString(R.string.text_error_name),Toast.LENGTH_LONG).show();
            } else if (etCity.getText().toString().isEmpty()) {
                Toast.makeText(AddUserActivity.this, getString(R.string.text_error_city),Toast.LENGTH_LONG).show();
            } else if (etPhone.getText().toString().isEmpty()) {
                Toast.makeText(AddUserActivity.this, getString(R.string.text_error_phone),Toast.LENGTH_LONG).show();
            } else {
                if (getIntent().hasExtra("user_info")) {
                    AddUserToDatabase(true);
                } else {
                    AddUserToDatabase(false);
                  /*  if (UserDatabase.getDatabaseInstance(AddUserActivity.this).userDao().getUserFromDatabase(etUserId.getText().toString().trim()) != null) {
                        Toast.makeText(AddUserActivity.this, getString(R.string.text_error_user_exist), Toast.LENGTH_LONG).show();
                    } else {
                        AddUserToDatabase(false);
                    }*/
                }
            }
        });
    }



      /*
    *
    * method to set data
    * */

    private void setData() {
        if (getIntent().hasExtra("user_info")){
            tvTitle.setText(getString(R.string.text_update_user));
            btnAdd.setText(getString(R.string.text_update));
            UserData userData= (UserData) getIntent().getSerializableExtra("user_info");
            if (userData!=null){
                etUserId.setText(userData.getUserId());
                etName.setText(userData.getUserName());
                etCity.setText(userData.getUserCity());
                etPhone.setText(userData.getUserPhone());
                etUserId.setEnabled(false);
                etUserId.setTextColor(ContextCompat.getColor(this,android.R.color.darker_gray));
            }
        }else {
            tvTitle.setText(getString(R.string.text_add_user));
            btnAdd.setText(getString(R.string.text_add));
        }
    }


      /*
    *
    * method to add user to database
    *
    * */

    private void AddUserToDatabase(boolean isUpdate) {
        UserData userData=new UserData();
        userData.setUserId(etUserId.getText().toString());
        userData.setUserName(etName.getText().toString());
        userData.setUserCity(etCity.getText().toString());
        userData.setUserPhone(etPhone.getText().toString());
        pbProgress.setVisibility(View.VISIBLE);
        if (isUpdate) {
            new DatabaseAsyncTask(this,userData,2).execute();
          //  UserDatabase.getDatabaseInstance(this).userDao().updateUserDataInDataBase(userData);
        }else {
            new DatabaseAsyncTask(this,userData,1).execute();
          //  UserDatabase.getDatabaseInstance(this).userDao().addUserToDatabase(userData);
        }
    }


    /*
    * method to update ui
    * */

    public void UpdateUI(int queryType, Boolean aBoolean) {
        pbProgress.setVisibility(View.GONE);
        if (queryType==1 && !aBoolean){
            Toast.makeText(AddUserActivity.this, getString(R.string.text_error_user_exist), Toast.LENGTH_LONG).show();
        }else {
            finish();
        }
    }
}
