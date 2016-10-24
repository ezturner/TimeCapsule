package me.kevinkang.timecapsule.activities;

import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.kevinkang.timecapsule.R;
import me.kevinkang.timecapsule.data.CurrentUser;
import me.kevinkang.timecapsule.data.firebase.TimeCapsuleUser;
import me.kevinkang.timecapsule.data.mock.MockCapsule;
import me.kevinkang.timecapsule.data.models.Capsule;
import me.kevinkang.timecapsule.data.models.User;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager layoutManager;
    private CapsuleAdapter capsuleAdapter;
    private User capsuleUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.capsule_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        capsuleUser = CurrentUser.getInstance();

        capsuleAdapter = new CapsuleAdapter(capsuleUser.getCapsules());
        recyclerView.setAdapter(capsuleAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(MainActivity.this, NewCapsuleActivity.class);
                startActivity(mainIntent);
            }
        });
    }

    public void onCapsuleClick(View view){
        Intent mainIntent = new Intent(this, CapsuleDetailActivity.class);
        this.startActivity(mainIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }
}
