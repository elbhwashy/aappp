package com.original.original.original;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.original.original.original.Admin.LoginAdminActivity;
import com.original.original.original.Admin.MainAdminActivity;

public class MainActivity extends AppCompatActivity {
    private Button buttonSeller,buttonAdmin;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        buttonAdmin = (Button)findViewById(R.id.button_admin);

        if (user != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainAdminActivity.class));
        }
        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginAdminActivity.class);
                startActivity(i);


            }
        });
        buttonSeller = (Button)findViewById(R.id.button_seller);
        buttonSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(MainActivity.this, SignInSellerActivity.class);
                //startActivity(i);
            }
        });

    }
}

