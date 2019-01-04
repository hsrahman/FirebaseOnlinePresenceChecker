package com.a000webhostapp.projecthn.firebaseonlinepresencechecker;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {

    private TextView name, status;
    private FirebaseDatabase fdatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle extra = getIntent().getExtras();
        if (extra != null && extra.getString("user") != null) {
            Toolbar actionbar  = findViewById(R.id.actionbar);
            setSupportActionBar(actionbar);
            name = findViewById(R.id.member_name);
            status = findViewById(R.id.member_status);
            loadUserData(extra.getString("user"));
        } else {
            finish();
        }
    }

    private void loadUserData (String email) {
        fdatabase.getReference().child("users").orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        FirebaseUserModel mUser =  userSnapshot.getValue(FirebaseUserModel.class);
                        name.setText(mUser.getName());
                        status.setText((mUser.isOnline()) ? "Online" : "Offline");
                    }
                } else {
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
