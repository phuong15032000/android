package com.mycode.noteapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mycode.noteapp.Dao.UserDAO;
import com.mycode.noteapp.Entities.User;
import com.mycode.noteapp.R;
import com.mycode.noteapp.database.UserDb;

public class RegisterActivity extends AppCompatActivity {
    Button btnOK;
    EditText etPass1,etPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firststep();
        setContentView(R.layout.activity_register);

        btnOK = findViewById(R.id.btnOK);
        etPass1 = findViewById(R.id.etPIN1);
        etPass2 = findViewById(R.id.etPIN2);
    }
    public void firststep(){
        UserDb userDB = UserDb.getUserDB(getApplicationContext());
        final UserDAO userDAO = userDB.userDao();
        new Thread(new Runnable() {
            @Override
            public void run() {
                User userEntity = userDAO.login1();
                if(!(userEntity==null)){
                    startActivityForResult(
                            new Intent(RegisterActivity.this,
                                    PasswordActivity.class),
                            6
                    );
                }
            }
        }).start();
    }

    public void Click_btnOK(View v){
        User user = new User();
        user.setId(1);
        user.setPassword(etPass1.getText().toString());
        if (validateInput(user)) {
            // do insert
            UserDb userDB = UserDb.getUserDB(getApplicationContext());
            final UserDAO userDAO = userDB.userDao();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    userDAO.registerUser(user);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),PasswordActivity.class);
                            startActivity(intent);                        }
                    });
                }
            }).start();
        } else{
            Toast.makeText(RegisterActivity.this, "Check again!", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean validateInput(User user){
        if (user.getPassword().isEmpty()) return false;
        if (!etPass1.getText().toString().equals(etPass2.getText().toString())) return false;
        return true;
    }
}
