package com.original.original.original.Admin;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.original.original.original.R;

public class ProfileAdminActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String Database_Path;

    String profileName;

    private TextView textViewName, textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_admin);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        textViewName = (TextView) findViewById(R.id.user_name);
        textViewEmail = (TextView) findViewById(R.id.user_email);

        if (user != null) {

            Database_Path = "Admins/" + user.getUid() + "/Profile_Information";
            databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path).child("name");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        profileName = postSnapshot.getValue(String.class);
                    }
                    textViewName.setText(profileName);
                    //textViewName.setText(name);
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });


//            String name = user.getDisplayName();
            String email = user.getEmail();


            textViewEmail.setText(email);
        }

    }
}
