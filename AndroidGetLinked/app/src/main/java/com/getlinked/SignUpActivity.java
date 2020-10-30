package com.getlinked;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    Button signup;
    EditText registeremail, registerpassword, confirmpassword, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup = findViewById(R.id.register);
        registeremail = findViewById(R.id.registeremail);
        registerpassword = findViewById(R.id.registerpassword);
        confirmpassword = findViewById(R.id.confirmpassword);
        address = findViewById(R.id.address);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup.setText("Registering user..");
                String uname = registeremail.getText().toString().trim();
                String pword = registerpassword.getText().toString().trim();
                String pword_c = confirmpassword.getText().toString().trim();
                String addr = address.getText().toString().trim();

                //password matching
                if(!pword.equals(pword_c))   {
                    Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_LONG).show();
                    signup.setText("SIGN UP");
                }   else if(uname.isEmpty() || pword.isEmpty() || addr.isEmpty())   {
                    signup.setText("SIGN UP");
                    if(pword.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Password cannot be empty/whitespace", Toast.LENGTH_LONG).show();
                    }   else    {
                        Toast.makeText(getApplicationContext(), "Please check your input and try again", Toast.LENGTH_LONG).show();
                    }
                }
                else    {
                    //password length check
                    if(pword.length() < 5 || pword.length() > 15)   {
                        Toast.makeText(getApplicationContext(), "Password should be between 5 and 15 chars in length", Toast.LENGTH_SHORT).show();
                    }   else    {
                        //call register web api to register and save details to database
                        //get response 1 or 0- 1 if successful then go to home screen.
                        new WebClient().getService().signup(uname, pword, pword, pword, addr).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Not registered. Please check email address and try again", Toast.LENGTH_LONG).show();
                                }   else    {
                                    int success = response.body();
                                    if(success == 1)    {
                                        Toast.makeText(getApplicationContext(), "Register success, please login", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                                        startActivity(intent);
                                    }   else    {
                                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
    }
}