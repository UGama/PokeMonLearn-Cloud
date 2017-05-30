package com.example.pokemonlearn;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Gama on 16/5/17.
 */

public class Shop extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private ImageView transfer1;
    private ImageView transfer2;
    private Animation trans_out1;
    private Animation trans_out2;

    private int Width;
    private int Height;
    private ImageView Coin;
    private int width;
    private int height;

    private Button Sell;
    private Button Buy;
    private Button Leave;
    private Animation float1;
    private Animation float2;
    private Animation float3;

    private ImageView ShopText;
    private Animation ShopText_show;
    private TextView ShopMessage;
    private ImageView next_text;
    private ImageView Text_Screen;
    private Animation animation1;
    private Animation animation2;
    private int MessageCount;

    private ImageView Waitress;
    private Animation waitress;

    private TextView Shop_Wood;
    private ImageView Cast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        Width= metrics.widthPixels;
        Height = metrics.heightPixels;
        Log.i("Windows", String.valueOf(Height) + " " + String.valueOf(Width));

        transfer1 = (ImageView) findViewById(R.id.transfer1);
        transfer1.setVisibility(View.VISIBLE);
        transfer2 = (ImageView) findViewById(R.id.transfer2);
        transfer2.setVisibility(View.VISIBLE);
        trans_out1 = AnimationUtils.loadAnimation(Shop.this, R.anim.trans_out_up);
        trans_out2 = AnimationUtils.loadAnimation(Shop.this, R.anim.trans_out_down);
        transfer1.startAnimation(trans_out1);
        transfer2.startAnimation(trans_out2);
        trans_out2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                transfer1.setVisibility(View.GONE);
                transfer2.setVisibility(View.GONE);
                CoinShow();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Coin = (ImageView) findViewById(R.id.coin);

        Sell = (Button) findViewById(R.id.sell);
        Sell.setVisibility(View.GONE);
        Sell.setOnClickListener(this);
        Sell.setOnTouchListener(this);
        Buy = (Button) findViewById(R.id.buy);
        Buy.setVisibility(View.GONE);
        Buy.setOnClickListener(this);
        Buy.setOnTouchListener(this);
        Leave = (Button) findViewById(R.id.leave);
        Leave.setVisibility(View.GONE);
        Leave.setOnClickListener(this);
        Leave.setOnTouchListener(this);
        float1 = AnimationUtils.loadAnimation(Shop.this, R.anim.cap_float1);
        float2 = AnimationUtils.loadAnimation(Shop.this, R.anim.cap_float2);
        float3 = AnimationUtils.loadAnimation(Shop.this, R.anim.cap_float3);

        ShopText = (ImageView) findViewById(R.id.shop_text);
        ShopText.setOnClickListener(this);
        ShopText.setVisibility(View.GONE);
        ShopMessage = (TextView) findViewById(R.id.shop_message);
        ShopMessage.setVisibility(View.GONE);
        Text_Screen = (ImageView) findViewById(R.id.screen);
        Text_Screen.setVisibility(View.GONE);
        next_text = (ImageView) findViewById(R.id.next_text);
        next_text.setVisibility(View.GONE);
        MessageCount = 0;

        ShopText_show = AnimationUtils.loadAnimation(Shop.this, R.anim.anim4);
        ShopText_show.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                next_text.setVisibility(View.VISIBLE);
                animation1 = AnimationUtils.loadAnimation(Shop.this, R.anim.up2);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        next_text.startAnimation(animation2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                animation2 = AnimationUtils.loadAnimation(Shop.this, R.anim.down2);
                animation2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        next_text.startAnimation(animation1);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                next_text.startAnimation(animation1);
                Text_Screen.setVisibility(View.VISIBLE);
                ShopMessage.setText("欢迎光临！");
                MessageCount++;
                ScreenRun(Text_Screen);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Waitress = (ImageView) findViewById(R.id.waitress);
        Waitress.setVisibility(View.GONE);
        waitress = AnimationUtils.loadAnimation(Shop.this, R.anim.waitress);
        waitress.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Shop_Wood.setVisibility(View.VISIBLE);
                Cast.setVisibility(View.VISIBLE);
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(Shop_Wood, "Alpha", 0, 1);
                objectAnimator1.setDuration(400);
                objectAnimator1.start();
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(Cast, "Alpha", 0, 1);
                objectAnimator2.setDuration(400);
                objectAnimator2.start();
                Cast();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ObjectAnimator cd = ObjectAnimator.ofFloat(Coin, "rotation", 0, 0);
        cd.setDuration(3000);
        cd.start();
        cd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ShopText.setVisibility(View.VISIBLE);
                ShopText.startAnimation(ShopText_show);
                ShopMessage.setVisibility(View.VISIBLE);
                ShopMessage.startAnimation(ShopText_show);
                Waitress.setVisibility(View.VISIBLE);
                Waitress.startAnimation(waitress);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        Shop_Wood = (TextView) findViewById(R.id.shop_wood);
        Shop_Wood.setVisibility(View.GONE);
        Cast = (ImageView) findViewById(R.id.cast);
        Cast.setVisibility(View.GONE);

        Intent intent = new Intent(Shop.this, ShopMusicServer.class);
        startService(intent);
    }

    public void CoinShow() {
        Coin.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams para = Coin.getLayoutParams();
        width = para.width;
        height = para.height;
        Log.i("Coin", String.valueOf(width) + " " + String.valueOf(height));

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(Coin, "scaleX", 1, 0, -1, 0, 1);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(400);
        objectAnimator.setRepeatCount(-1);
        objectAnimator.start();

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(2000);
        valueAnimator.setObjectValues(new PointF(Width / 2 - width / 2, Height));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                Float a = Height * 2 * 0.8f / (0.5f * 0.5f);
                if (fraction < 0.25) {
                    fraction = 0.25f - fraction;
                    point.x = Width / 2 - width / 2;
                    point.y = Height * 0.2f + 0.5f * a * (fraction * 2) * (fraction * 2);
                } else if (fraction < 0.75) {
                    point.x = Width / 2 - width / 2;
                    point.y = Height * 0.2f;
                } else {
                    fraction -= 0.75f;
                    point.x = Width / 2 - width / 2;
                    point.y = Height * 0.2f + 0.5f * a * (fraction * 2) * (fraction * 2);
                }
                return point;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                Coin.setX(point.x);
                Coin.setY(point.y);
            }
        });
    }

    public void Cast() {
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(Shop_Wood, "translationY", 0, 40);
        objectAnimator1.setDuration(400);
        objectAnimator1.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(Shop_Wood, "translationY", 40, 0);
        objectAnimator2.setDuration(400);
        objectAnimator2.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.play(objectAnimator1).before(objectAnimator2);
        animatorSet1.start();

        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(Cast, "translationY", 0, 40);
        objectAnimator3.setDuration(400);
        objectAnimator3.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(Cast, "translationY", 40, 0);
        objectAnimator4.setDuration(400);
        objectAnimator4.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.play(objectAnimator3).before(objectAnimator4);
        animatorSet2.start();
        animatorSet2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Cast();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_text:
                switch (MessageCount) {
                    case 1:
                        ShopMessage.setText("您需要什么？");
                        ScreenRun(Text_Screen);
                        animation1.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                next_text.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                next_text.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        animation2.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                next_text.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                next_text.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        MessageCount++;
                        Sell.setVisibility(View.VISIBLE);
                        Buy.setVisibility(View.VISIBLE);
                        Leave.setVisibility(View.VISIBLE);
                        Sell.startAnimation(float2);
                        Buy.startAnimation(float1);
                        Leave.startAnimation(float3);
                        break;
                }
                break;
            case R.id.buy:
                //Intent intent1 = new Intent(Shop.this, SBuy.class);
                //startActivity(intent1);
                break;
            case R.id.sell:
                //Intent intent2 = new Intent(Shop.this, SSell.class);
                //startActivity(intent2);
                break;
            case R.id.leave:
                finish();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.sell:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Sell.getBackground().setAlpha(120);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Sell.getBackground().setAlpha(255);
                }
                break;
            case R.id.buy:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Buy.getBackground().setAlpha(120);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Buy.getBackground().setAlpha(255);
                }
                break;
            case R.id.leave:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Leave.getBackground().setAlpha(120);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Leave.getBackground().setAlpha(255);
                }
                break;
        }
        return false;
    }

    public void ScreenRun(View view) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "scaleX",
                1.0f, 0.0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "translationX",
                0, 450);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(1500);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();

    }

    @Override
    protected void onDestroy() {
        Log.i("Shop", "Destroy");
        Intent intent = new Intent(Shop.this, ShopMusicServer.class);
        stopService(intent);
        super.onDestroy();
    }

}
