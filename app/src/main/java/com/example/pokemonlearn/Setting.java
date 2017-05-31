package com.example.pokemonlearn;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Gama on 25/5/17.
 */

public class Setting extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private CircleImageView Back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        Back = (CircleImageView) findViewById(R.id.back);
        Back.setOnClickListener(this);
        Back.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.back:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v, "TranslationY", 0, 30);
                    objectAnimator.setDuration(100);
                    objectAnimator.start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v, "TranslationY", 30, 0);
                    objectAnimator.setDuration(100);
                    objectAnimator.start();
                }
                break;
        }
        return false;
    }
}
