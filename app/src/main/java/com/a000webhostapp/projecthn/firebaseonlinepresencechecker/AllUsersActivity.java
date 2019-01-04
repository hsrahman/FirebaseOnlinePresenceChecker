package com.a000webhostapp.projecthn.firebaseonlinepresencechecker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllUsersActivity extends AuthenticatedActivity {
    private ListView allUsers;
    private FirebaseDatabase fdatabase = FirebaseDatabase.getInstance();
    private ArrayAdapter<FirebaseUserModel> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        DatabaseReference databaseRef = fdatabase.getReference().child("users");
        allUsers = (ListView) findViewById(R.id.all_users);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, new ArrayList<FirebaseUserModel>());
        allUsers.setAdapter(adapter);
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<FirebaseUserModel> users = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        FirebaseUserModel mUser =  userSnapshot.getValue(FirebaseUserModel.class);
                        if (!mAuth.getCurrentUser().getEmail().equals(mUser.getEmail())) { users.add(mUser); }
                    }
                    adapter.addAll(users);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        allUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent chat = new Intent(AllUsersActivity.this, ChatActivity.class);
                chat.putExtra("user", adapter.getItem(i).getEmail());
                startActivity(chat);
                System.out.println(adapter.getItem(i).getName());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout :
                mAuth.signOut();
                Intent main = new Intent(AllUsersActivity.this, MainActivity.class);
                main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(main);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
