package com.original.original.original.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.original.original.original.R;

public class AddAdminActivity extends AppCompatActivity {
    private TextInputLayout textInputLayoutName, textInputLayoutEmail, textInputLayoutPassword , textInputLayoutConfirmPassword;
    private EditText editTextEmail,editTextPassword,editTextConfirmPassword,editTextName;
    private Button buttonCreate;
    private ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String Database_Path;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        getSupportActionBar().hide();
        firebaseAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);
        editTextName = (EditText) findViewById(R.id.textInputEditText_name);
        editTextEmail = (EditText) findViewById(R.id.textInputEditText_email);
        editTextPassword = (EditText) findViewById(R.id.textInputEditText_password);
        editTextConfirmPassword = (EditText) findViewById(R.id.textInputEditText_confirm_password);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInput_layout_name);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInput_layout_email);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInput_layout_password);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInput_layout_confirm_password);

        buttonCreate = (Button) findViewById(R.id.Button_create);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addUser();
                // startActivity(new Intent(getApplicationContext(), MainAdminActivity.class));
            }
        });
    }

    private void addUser() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        if (name.isEmpty() ) {
            textInputLayoutName.setError(getString(R.string.enter_profile_name));
            return;
        } else {
            textInputLayoutName.setErrorEnabled(false);
        }
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
        if (confirmPassword.isEmpty()) {
            textInputLayoutConfirmPassword.setError(getString(R.string.enter_password_again));
            return;
        } else {
            textInputLayoutConfirmPassword.setErrorEnabled(false);
        }
        if (!confirmPassword.equals(password)) {
            textInputLayoutConfirmPassword.setError(getString(R.string.password_should_match));
            textInputLayoutPassword.setError(getString(R.string.password_should_match));
            return;
        } else {
            textInputLayoutConfirmPassword.setErrorEnabled(false);
            textInputLayoutPassword.setErrorEnabled(false);
        }

        progressDialog.setMessage("Registering...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            createNewUser(user);
                            Intent i = new Intent(AddAdminActivity.this, LoginAdminActivity.class);
                            startActivity(i);
                            finish();
                            progressDialog.dismiss();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(AddAdminActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }


                    }
                }
        );

    }

    private void createNewUser(FirebaseUser user) {
        String username = editTextName.getText().toString().trim();
        String email = user.getEmail();
        String userId = user.getUid();

//        user = firebaseAuth.getCurrentUser();
        Database_Path = "Admins/"+userId+"/Profile_Information";
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

//        databaseReference = FirebaseDatabase.getInstance().getReference()
//                .child("users").child(userId);
        AdminData admin = new AdminData(email,username);

        databaseReference.setValue(admin);
    }
}
