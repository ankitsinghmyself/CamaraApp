package com.example.RegistrationPage;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
        private static final String TAG = "MainActivity";
        private static final int REQUEST_App_gallery = 0;
        private static final int REQUEST_SIGNUP=0;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    EditText mEmailText;
        EditText mPasswordText;
        Button mLoginButton;
        TextView mSignUpLink;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mEmailText = (EditText) findViewById(R.id.input_Email);
            mPasswordText = (EditText) findViewById(R.id.input_Password);
            mLoginButton = (Button) findViewById(R.id.btn_login);
            mSignUpLink = (TextView) findViewById(R.id.link_SignUp);
            if(ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            mLoginButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {


                                                    if (mEmailText.getText().toString().trim().length() == 0) {
                                                        mEmailText.setError("Email is not entered");
                                                        mEmailText.requestFocus();
                                                    }
                                                    if (mPasswordText.getText().toString().trim().length() == 0) {
                                                        mPasswordText.setError("Password is not entered");
                                                        mPasswordText.requestFocus();
                                                    } else {

                                                        Intent intent = new Intent(getApplicationContext(), App_gallery.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            });

            mSignUpLink.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Start the Signup activity
                    Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivityForResult(intent, REQUEST_SIGNUP);
                }
            });
        }


            public void login() {
                Log.d(TAG, "Login");

                if (!validate()) {
                    onLoginFailed();
                    return;
                }

                mLoginButton.setEnabled(false);

                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                        R.style.AppTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();

                String email = mEmailText.getText().toString();
                String password = mPasswordText.getText().toString();

                // TODO: Implement your own authentication logic here.

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed

                                onLoginSuccess();
                                // onLoginFailed();
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }


            @SuppressLint("MissingSuperCall")
            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (requestCode == REQUEST_SIGNUP) {
                    if (resultCode == RESULT_OK) {

                        // TODO: Implement successful signup logic here
                        // By default we just finish the Activity and log them in automatically
                        this.finish();
                    }
                }
            }

            @Override
            public void onBackPressed() {
                // disable going back to the MainActivity
                moveTaskToBack(true);
            }

            public void onLoginSuccess() {
                Intent intent = new Intent(
                        this, App_gallery.class);
                startActivity(intent);
                finish();

            }

            public void onLoginFailed() {
                Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

                mLoginButton.setEnabled(true);
            }

            public boolean validate() {
                boolean valid = true;

                String email = mEmailText.getText().toString();
                String password = mPasswordText.getText().toString();

                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    mEmailText.setError("enter a valid email address");
                    valid = false;
                } else {
                    mEmailText.setError(null);
                }

                if (password.isEmpty() || password.length() < 2 || password.length() > 10) {
                    mPasswordText.setError("between 2 and 10 alphanumeric characters");
                    valid = false;
                } else {
                    mPasswordText.setError(null);
                }

                return valid;
            }
        }


