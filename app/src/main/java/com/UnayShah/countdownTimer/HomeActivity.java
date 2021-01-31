package com.UnayShah.countdownTimer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.UnayShah.countdownTimer.model.DataHolder;
import com.UnayShah.countdownTimer.popupactivity.TimerNamePopup;
import com.google.android.material.button.MaterialButton;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, IStartDragListener {

    static RecyclerView recyclerView;
    ConstraintLayout emptyHolder;
    MaterialButton homeAddButton;
    MaterialButton returnButton;
    MaterialButton settingsButton;
    ImageView titleImage;
    RecyclerAdapter recyclerAdapter;
    //    AdView adView;
    ItemTouchHelper itemTouchHelper;

    public static void autoScroll(int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TimerActivity.timerRunning = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onBackPressed() {
        DataHolder.getInstance().setDisableButtonClick(false);
        super.onBackPressed();
    }

    private void init() {
//        adView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

        emptyHolder = findViewById(R.id.empty_holder);
        if (DataHolder.getInstance().getListTimerGroup().size() > 0)
            emptyHolder.setVisibility(View.VISIBLE);
        else emptyHolder.setVisibility(View.INVISIBLE);

        titleImage = findViewById(R.id.title_image);
        homeAddButton = findViewById(R.id.home_add_button);
        returnButton = findViewById(R.id.home_button);
        settingsButton = findViewById(R.id.settings_button);
        homeAddButton.setOnClickListener(this);
        returnButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);

        recyclerView = findViewById(R.id.timerGroupScrollViewRecyclerView);
        recyclerAdapter = new RecyclerAdapter(this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();

        ItemTouchHelper.Callback callback = new ItemMoveCallback(recyclerAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        loadData();
    }

    @Override
    public void onClick(View view) {
        if (!DataHolder.getInstance().getDisableButtonClick()) {
            DataHolder.getInstance().setDisableButtonClick(true);
            if (view.getId() == homeAddButton.getId()) addTimerGroup();
            else if (view.getId() == returnButton.getId()) onBackPressed();
            else if (view.getId() == settingsButton.getId()) {
                settingsButton();
            } else DataHolder.getInstance().setDisableButtonClick(false);
        }
    }

    private void settingsButton() {
        DataHolder.getInstance().setDisableButtonClick(false);
        Intent intent = new Intent(getApplicationContext(), CommonSettings.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }

    public void requestDrag(RecyclerAdapter.ListItemViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    public void addTimerGroup() {
        View timerNamePopupWindowView = getLayoutInflater().inflate(R.layout.timer_name_popup, (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), false);
        PopupWindow timerNamePopupWindow = new TimerNamePopup(timerNamePopupWindowView, recyclerAdapter);
        timerNamePopupWindow.showAtLocation(findViewById(R.id.home_screen), Gravity.CENTER, 0, 0);
    }

    private void loadData() {
//        AppCompatDelegate.setDefaultNightMode(DataHolder.getInstance().getThemeMode());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView.setEdgeEffectFactory(DataHolder.getInstance().recyclerViewEdgeEffectFactory(getApplicationContext()));
        DataHolder.getInstance().loadData(getApplicationContext());
        homeAddButton.setIconTint(DataHolder.getInstance().getAccentColor(getApplicationContext()));
        titleImage.setImageTintList(DataHolder.getInstance().getAccentColor(getApplicationContext()));
        getWindow().setStatusBarColor(DataHolder.getInstance().getAccentColorColor(getApplicationContext()));
        recyclerAdapter.notifyDataSetChanged();
    }
}