package com.dyf.hrd;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

public class SelectActivity extends AppCompatActivity {
    private LevelViewModel levelViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final LevelListAdapter adapter = new LevelListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        levelViewModel = ViewModelProviders.of(this).get(LevelViewModel.class);
        levelViewModel.getmAllLevels().observe(this, new Observer<List<Level>>() {
            @Override
            public void onChanged(@Nullable List<Level> levels) {
                adapter.setLevels(levels);
            }
        });
    }

    public void selectGame(View view) {
        String tag = view.getTag().toString();
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.GAMEID, tag);
        startActivity(intent);
    }

    public void switchToBuiltin(View view) {
    }

    public void switchToDIY(View view) {
    }
}
