package com.getlinked;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {

    Button signin,signup, loginProfessional, registerProfessional;
    EditText username,password;
    static String loggedinusername = "";
    public static int authenticated = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        loginProfessional = findViewById(R.id.loginProfessional);
        registerProfessional = findViewById(R.id.registerProfessional);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        //log in as user button click
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin.setText("Signing in, please wait..");
                String uname = username.getText() + ":user";
                String pword = password.getText().toString();

                //call api to set signin authentication by passsing username and password and get 0 or 1.
                // if 1,  go to mainactivity.
                CallAPI(uname, pword, 1);
            }
        });
        //professional login
        loginProfessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProfessional.setText("Signing in, please wait..");
                String uname = username.getText() + ":professional";
                String pword = password.getText().toString();

                //call api to set signin authentication by passsing username and password and get 0 or 1.
                // if 1,  go to mainactivity.
                CallAPI(uname, pword, 2);
            }
        });

        //professional register
        registerProfessional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                password.setText("");
                //taking to register as professional page
                startActivity(new Intent(getApplicationContext(), RegisterProfessionalActivity.class));
            }
        });

        //sign up professional
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                password.setText("");
                //taking to sign up activity
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
    }

    public void CallAPI(String uname, String pword, int usertype)
    {
        new WebClient().getService().validateSignin(uname, pword).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                int success = response.body();
                if(success == 1)    {
                    username.setText("");
                    password.setText("");
                    authenticated = 1;
                    loggedinusername = uname;
                    Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_LONG).show();
                    if(usertype == 1)   {
                        signin.setText("User sign in");
                        //taking to main activity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
//                        finish();
                    }   else if(usertype == 2)  {
                        loginProfessional.setText("Login as Professional");
                        //taking professional to my works
                        Intent intent = new Intent(getApplicationContext(), MyWorkActivity.class);
                        intent.putExtra("professionalEmail", uname);
                        startActivity(intent);
//                        finish();
                    }

                }   else    {
                    Toast.makeText(getApplicationContext(), "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                    signin.setText("User sign in");
                    loginProfessional.setText("Login as Professional");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG).show();
            }
        });
    }
//    public static String GetLoggedInUsername()  {
//        return loggedinusername;
//    }
}