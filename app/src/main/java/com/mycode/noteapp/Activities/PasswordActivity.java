package com.mycode.noteapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mycode.noteapp.Dao.UserDAO;
import com.mycode.noteapp.Entities.User;
import com.mycode.noteapp.R;
import com.mycode.noteapp.database.UserDb;

public class PasswordActivity extends AppCompatActivity {

    private String pass = "1234";
    private EditText etPassword;
    private Button btnSign;
    private FloatingActionButton btnCamera;
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSign = (Button) findViewById(R.id.btnSign);
        btnCamera = (FloatingActionButton) findViewById(R.id.btnCamera);
    }
    public void Click_btnCamera(View v){
        Intent intent=new Intent(getApplicationContext(),DetectFace.class);
        startActivity(intent);
    }
    public void Click_btnSign(View v){
        String pass = etPassword.getText().toString();
        UserDb userDB = UserDb.getUserDB(getApplicationContext());
        final UserDAO userDAO = userDB.userDao();
        new Thread(new Runnable() {
            @Override
            public void run() {
                User userEntity = userDAO.login2(pass);
                if(userEntity==null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PasswordActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    startActivity(new Intent(
                            PasswordActivity.this, MainActivity.class)
                    );
                }
            }
        }).start();
    }
    private void checkPass(){
        String pass = etPassword.getText().toString();
        UserDb userDB = UserDb.getUserDB(getApplicationContext());
        final UserDAO userDAO = userDB.userDao();
        new Thread(new Runnable() {
            @Override
            public void run() {
                User userEntity = userDAO.login2(pass);
                if(userEntity==null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PasswordActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    startActivity(new Intent(
                            PasswordActivity.this, MainActivity.class)
                    );
                }
            }
        }).start();



    }
}