package com.example.countdownTimer.fragmentsInflater;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.countdownTimer.R;
import com.example.countdownTimer.model.DataHolder;

public class timerAnimationInflater extends Fragment {
    ImageView timerAnimation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.timer_animation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        timerAnimation = view.findViewById(R.id.timer_animation);

        Drawable outerRing = ContextCompat.getDrawable(getContext(), R.drawable.timer_animation_sweep);
        outerRing.setTint(DataHolder.getInstance().getAccentColorColor(getContext()));
        timerAnimation.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.timer_animation));
    }
}