package com.original.original.original.Admin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.original.original.original.MainActivity;
import com.original.original.original.R;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginAdminActivity extends AppCompatActivity {
    private TextInputLayout  textInputLayoutName, textInputLayoutEmail, textInputLayoutPassword;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private TextView textViewForgetPassword;
    private TextView textViewSignUp;
    private Button buttonSignIn;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

//        editTextName = (EditText) findViewById(R.id.textInputEditText_name);
        editTextEmail = (EditText) findViewById(R.id.textInputEditText_email);
        editTextPassword = (EditText) findViewById(R.id.textInputEditText_password);

//        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInput_layout_name);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInput_layout_email);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInput_layout_password);

        textViewSignUp = (TextView)findViewById(R.id.textView_sign_up);
        textViewSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(LoginAdminActivity.this, AddAdminActivity.class);
                startActivity(i);
            }
        });
//        textViewForgetPassword = (TextView) findViewById(R.id.textView_forget_password);
        buttonSignIn = (Button) findViewById(R.id.Button_sign_in);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signInUser();
               // startActivity(new Intent(getApplicationContext(), MainAdminActivity.class));
            }
        });


    }

    private void signInUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputLayoutEmail.setError(getString(R.string.enter_availed_email));
            return;
        } else {
            textInputLayoutEmail.setErrorEnabled(false);
        }
        if (password.isEmpty()) {
            textInputLayoutPassword.setError(getString(R.string.enter_password));
            return;
        } else {
            textInputLayoutPassword.setErrorEnabled(false);
        }
        progressDialog.setMessage("Registering...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainAdminActivity.class));
                        //chechUserType();
                    }
                    //finish();
                    progressDialog.dismiss();
                    //startActivity(new Intent(getApplicationContext(), ChatCalendarProfileActivity.class));
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginAdminActivity.this, R.string.faild, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

   @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }


}

