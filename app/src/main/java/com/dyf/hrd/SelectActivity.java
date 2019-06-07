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
import android.widget.Button;

import java.util.List;

public class SelectActivity extends AppCompatActivity {
    private LevelViewModel levelViewModel;
    private int currentMode;
    private RecyclerView recyclerView;
    private Button BuiltInButton, DIYButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        this.currentMode = 0;
        recyclerView = findViewById(R.id.recyclerview);
        BuiltInButton = findViewById(R.id.select_built_in_game_tag);
        DIYButton = findViewById(R.id.select_diy_game_tag);
        levelViewModel = ViewModelProviders.of(this).get(LevelViewModel.class);
        showList();
    }

    private void showList() {
        final LevelListAdapter adapter = new LevelListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (currentMode == 0) {
            BuiltInButton.setBackgroundColor(getResources().getColor(R.color.selected));
            DIYButton.setBackgroundColor(getResources().getColor(R.color.unSelected));
            levelViewModel.getmAllLevels().observe(this, new Observer<List<Level>>() {
                @Override
                public void onChanged(@Nullable final List<Level> levels) {
                    adapter.setLevels(levels);
                }
            });
        } else {
            BuiltInButton.setBackgroundColor(getResources().getColor(R.color.unSelected));
            DIYButton.setBackgroundColor(getResources().getColor(R.color.selected));
            levelViewModel.getmDIYLevels().observe(this, new Observer<List<Level>>() {
                @Override
                public void onChanged(@Nullable List<Level> levels) {
                    adapter.setLevels(levels);
                }
            });
        }
    }

    public void selectGame(View view) {
        String tag = view.getTag().toString();
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.GAMEID, tag);
        startActivity(intent);
    }

    public void switchToBuiltin(View view) {
        currentMode = 0;
        showList();
    }

    public void switchToDIY(View view) {
        currentMode = 1;
        showList();
    }
}
