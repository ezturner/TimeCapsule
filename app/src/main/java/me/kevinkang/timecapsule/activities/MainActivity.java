package me.kevinkang.timecapsule.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.kevinkang.timecapsule.R;
import me.kevinkang.timecapsule.data.firebase.TimeCapsuleUser;
import me.kevinkang.timecapsule.data.mock.MockCapsule;
import me.kevinkang.timecapsule.data.models.Capsule;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager layoutManager;
    private CapsuleAdapter capsuleAdapter;
    private TimeCapsuleUser capsuleUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.capsule_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<Capsule> capsuleList = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            capsuleList.add(new MockCapsule());
        }

        capsuleAdapter = new CapsuleAdapter(capsuleList);
        recyclerView.setAdapter(capsuleAdapter);
        capsuleUser = new TimeCapsuleUser();
    }
}
