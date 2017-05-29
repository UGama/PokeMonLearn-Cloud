package com.example.pokemonlearn;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;

import java.util.HashMap;
import java.util.List;

import static android.animation.ObjectAnimator.ofFloat;

/**
 * Created by Gama on 7/4/17.
 */

public class Capture extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private ImageView transfer;
    private ImageView white;
    private Animation shrink_white;
    private Animation shrink;

    private int Width;
    private int Height;

    private ImageView Pokemon;
    private ImageView Pokemon2;
    private ImageView roof;
    private ImageView character;
    private Animation show_up;
    private Animation show_up2;

    private ImageView fightText;
    private Animation fightText_show;
    private TextView fightMessage;
    private ImageView next_text;
    private ImageView Text_Screen;
    private Animation animation1;
    private Animation animation2;
    private int MessageCount;
    private PokeMon C_PokeMon;
    private ImageView PMBall;
    private PokeMonBall C_PokeMonBall;
    private boolean Judgement;
    private int StruggleCount;

    private Button Bag;
    private Button pokemonBall;
    private Button run;
    private Animation float1;
    private Animation float2;
    private Animation float3;

    private Animation trans1_in;
    private Animation trans2_in;
    private ImageView transfer21;
    private ImageView transfer22;
    private Animation transit;

    private LinearLayout LeftLayout;
    private LinearLayout RightLayout;
    private GiveUpButton giveUpButton;
    private FreeButton freeButton;

    private MediaPlayer mediaPlayer;

    int j = 0;

    private Bitmap C_PokeMonBitMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture);

        Intent intent = getIntent();
        String Name = intent.getStringExtra("Name");
        //http://ac-vuvtafsi.clouddn.com/4798581ad30a5d075a9b.png

        AVQuery<AVObject> query1 = new AVQuery<>("PM");
        query1.whereEqualTo("Name", Name);
        query1.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                C_PokeMon = new PokeMon(avObject.getObjectId(),
                        avObject.getInt("Number"),
                        avObject.getString("Name"),
                        avObject.getString("ImageName2"),
                        avObject.getInt("Weight"),
                        avObject.getString("ImageName1"),
                        avObject.getString("Senior"));
                Log.i("Test", C_PokeMon.getName());
                GetBitMap();
            }
        });



        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        Width= metrics.widthPixels;
        Height = metrics.heightPixels;
        Log.i("Windows", String.valueOf(Height) + " " + String.valueOf(Width));

    }

    public void GetBitMap() {
        AVQuery<AVObject> query = new AVQuery<>("_File");
        query.whereEqualTo("name", C_PokeMon.getImageName2() + ".png");
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                AVFile avFile = new AVFile("Image2,png", avObject.getString("url"), new HashMap<String, Object>());
                Log.i("Test", avObject.getString("url"));
                avFile.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] bytes, AVException e) {
                        C_PokeMonBitMap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        UIInit();
                    }
                });
            }
        });
    }

    public void UIInit() {
        white = (ImageView) findViewById(R.id.transfer1);
        white.setVisibility(View.VISIBLE);
        shrink_white = AnimationUtils.loadAnimation(Capture.this, R.anim.white_disapper);

        shrink_white.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                white.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Pokemon = (ImageView) findViewById(R.id.pokemon_capture);
        Pokemon.setImageBitmap(C_PokeMonBitMap);
        Pokemon.setVisibility(View.GONE);
        Pokemon2 = (ImageView) findViewById(R.id.pokemon_capture2);
        Pokemon2.setImageBitmap(C_PokeMonBitMap);
        Pokemon2.setVisibility(View.GONE);
        roof = (ImageView) findViewById(R.id.roof);
        roof.setVisibility(View.GONE);
        character = (ImageView) findViewById(R.id.character_capture);
        character.setVisibility(View.GONE);
        fightText = (ImageView) findViewById(R.id.fight_text);
        fightText.setOnClickListener(this);
        fightText.setVisibility(View.GONE);
        fightMessage = (TextView) findViewById(R.id.fight_message);
        fightMessage.setVisibility(View.GONE);
        Text_Screen = (ImageView) findViewById(R.id.screen);
        Text_Screen.setVisibility(View.GONE);
        next_text = (ImageView) findViewById(R.id.next_text);
        next_text.setVisibility(View.GONE);
        MessageCount = 0;

        Bag = (Button) findViewById(R.id.bag);
        Bag.setVisibility(View.GONE);
        Bag.setOnClickListener(this);
        Bag.setOnTouchListener(this);
        pokemonBall = (Button) findViewById(R.id.pokemonBall);
        pokemonBall.setVisibility(View.GONE);
        pokemonBall.setOnClickListener(this);
        pokemonBall.setOnTouchListener(this);
        run = (Button) findViewById(R.id.run);
        run.setVisibility(View.GONE);
        run.setOnClickListener(this);
        run.setOnTouchListener(this);
        float1 = AnimationUtils.loadAnimation(Capture.this, R.anim.cap_float1);
        float2 = AnimationUtils.loadAnimation(Capture.this, R.anim.cap_float2);
        float3 = AnimationUtils.loadAnimation(Capture.this, R.anim.cap_float3);

        show_up = AnimationUtils.loadAnimation(Capture.this, R.anim.show_up);
        show_up2 = AnimationUtils.loadAnimation(Capture.this, R.anim.show_up2);
        show_up.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fightText.setVisibility(View.VISIBLE);
                fightText.startAnimation(fightText_show);
                fightMessage.setVisibility(View.VISIBLE);
                fightMessage.startAnimation(fightText_show);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        transfer = (ImageView) findViewById(R.id.transfer2);
        transfer.setVisibility(View.VISIBLE);
        shrink = AnimationUtils.loadAnimation(Capture.this, R.anim.shrink);
        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Pokemon.setVisibility(View.VISIBLE);
                Pokemon.startAnimation(show_up);
                roof.setVisibility(View.VISIBLE);
                roof.startAnimation(show_up);
                character.setVisibility(View.VISIBLE);
                character.startAnimation(show_up2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        trans1_in = AnimationUtils.loadAnimation(Capture.this, R.anim.trans_in_up);
        trans2_in = AnimationUtils.loadAnimation(Capture.this, R.anim.trans_in_down);
        transfer21 = (ImageView) findViewById(R.id.transfer21);
        transfer22 = (ImageView) findViewById(R.id.transfer22);

        fightText_show = AnimationUtils.loadAnimation(Capture.this, R.anim.anim4);
        fightText_show.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                next_text.setVisibility(View.VISIBLE);
                animation1 = AnimationUtils.loadAnimation(Capture.this, R.anim.up2);
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
                animation2 = AnimationUtils.loadAnimation(Capture.this, R.anim.down2);
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
                fightMessage.setText("野生的 " + C_PokeMon.getName() + " 出现了！");
                MessageCount++;
                //ScreenRun(Text_Screen);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        PMBall = (ImageView) findViewById(R.id.PMBall);

        StruggleCount = 0;
        Judgement = false;

        trans1_in = AnimationUtils.loadAnimation(Capture.this, R.anim.trans_in_up);
        trans2_in = AnimationUtils.loadAnimation(Capture.this, R.anim.trans_in_down);

        LeftLayout = (LinearLayout) findViewById(R.id.LeftLayout);
        RightLayout = (LinearLayout) findViewById(R.id.RightLayout);
        giveUpButton = new GiveUpButton(Capture.this);
        freeButton = new FreeButton(Capture.this);
        LeftLayout.addView(giveUpButton);
        RightLayout.addView(freeButton);
        giveUpButton.setVisibility(View.GONE);
        freeButton.setVisibility(View.GONE);

        StartAnim();
    }

    public void StartAnim() {
        transfer.startAnimation(shrink);
        white.startAnimation(shrink_white);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bag:
                //Intent intent1 = new Intent(Capture.this, CPokeMonTool.class);
                //startActivityForResult(intent1, 1);
                //overridePendingTransition(0, 0);
                break;
            case R.id.pokemonBall:
                //Intent intent2 = new Intent(Capture.this, CPokeMonBall.class);
                //startActivityForResult(intent2, 2);
                //overridePendingTransition(0, 0);
                break;
            case R.id.run:
                transfer21.setVisibility(View.VISIBLE);
                transfer21.startAnimation(trans1_in);
                transfer22.setVisibility(View.VISIBLE);
                transfer22.startAnimation(trans2_in);
                trans1_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        finish();
                        transit = AnimationUtils.loadAnimation(Capture.this, R.anim.transit);
                        transfer21.startAnimation(transit);
                        transit.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                transfer21.setVisibility(View.GONE);
                                transfer22.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;
            case R.id.fight_text:
                switch (MessageCount) {
                    case 1:
                        ObjectAnimator anim1 = ObjectAnimator.ofFloat(Text_Screen, "scaleX",
                                1.0f, 0.0f);
                        ObjectAnimator anim2 = ObjectAnimator.ofFloat(Text_Screen, "translationX",
                                0, 450);
                        AnimatorSet animSet = new AnimatorSet();
                        animSet.setDuration(2500);
                        animSet.setInterpolator(new LinearInterpolator());
                        animSet.playTogether(anim1, anim2);
                        animSet.start();
                        fightMessage.setText("你要做什么？");
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
                        Bag.setVisibility(View.VISIBLE);
                        pokemonBall.setVisibility(View.VISIBLE);
                        run.setVisibility(View.VISIBLE);
                        Bag.startAnimation(float2);
                        pokemonBall.startAnimation(float1);
                        run.startAnimation(float3);
                        break;
                    case 3:
                        MessageCount++;
                        String tip = C_PokeMon.getName() + " 逃跑了。";
                        fightMessage.setText(tip);
                        ScreenRun(Text_Screen);
                        break;
                    case 4:
                        MessageCount += 100;
                        transfer21.setVisibility(View.VISIBLE);
                        transfer21.startAnimation(trans1_in);
                        transfer22.setVisibility(View.VISIBLE);
                        transfer22.startAnimation(trans2_in);
                        trans1_in.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                finish();
                                transit = AnimationUtils.loadAnimation(Capture.this, R.anim.transit);
                                transfer21.startAnimation(transit);
                                transit.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        transfer21.setVisibility(View.GONE);
                                        transfer22.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        break;
                    case 5:
                        MessageCount++;
                        fightMessage.setText("你的宠物数量已达上限。");
                        ScreenRun(Text_Screen);
                        break;
                    case 6:
                        tip = "放弃 " + C_PokeMon.getName() + " ，";
                        MessageCount++;
                        fightMessage.setText(tip);
                        ScreenRun(Text_Screen);
                        break;
                    case 7:
                        MessageCount++;
                        tip = "还是放生已有宠物？";
                        fightMessage.setText(tip);
                        ScreenRun(Text_Screen);
                        giveUpButton.setVisibility(View.VISIBLE);
                        giveUpButton.startAnimation(float2);
                        giveUpButton.setOnClickListener(this);
                        giveUpButton.setOnTouchListener(this);
                        freeButton.setVisibility(View.VISIBLE);
                        freeButton.startAnimation(float3);
                        freeButton.setOnClickListener(this);
                        freeButton.setOnTouchListener(this);
                        break;
                    case 8:
                        MessageCount = 2;
                        Judge();
                        break;
                    case 9:
                        String tip1 = "由于你已经拥有 " + C_PokeMon.getName() + " ，";
                        fightMessage.setText(tip1);
                        ScreenRun(Text_Screen);
                        MessageCount++;
                        break;
                    case 10:
                        String tip2 = C_PokeMon.getName() + " 被放生了。";
                        fightMessage.setText(tip2);
                        ScreenRun(Text_Screen);
                        MessageCount = 4;
                        break;
                }
                break;
            default:
                break;
        }
        if (v == giveUpButton) {
            finish();
        } else if (v == freeButton) {
            giveUpButton.setVisibility(View.GONE);
            freeButton.setVisibility(View.GONE);
            MessageCount = 8;
            fightMessage.setText("重新检测您的宠物数量...");
            ScreenRun(Text_Screen);
            //Intent intent = new Intent(Capture.this, Pet.class);
            //startActivity(intent);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.bag:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Bag.getBackground().setAlpha(120);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Bag.getBackground().setAlpha(255);
                }
                break;
            case R.id.pokemonBall:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    pokemonBall.getBackground().setAlpha(120);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    pokemonBall.getBackground().setAlpha(255);
                }
                break;
            case R.id.run:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    run.getBackground().setAlpha(120);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    run.getBackground().setAlpha(255);
                }
                break;
        }
        if (v == giveUpButton) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ObjectAnimator objectAnimator = ofFloat(giveUpButton, "TranslationY", 0, 40);
                objectAnimator.setDuration(100);
                objectAnimator.start();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                ObjectAnimator objectAnimator = ofFloat(giveUpButton, "TranslationY", 40, 0);
                objectAnimator.setDuration(100);
                objectAnimator.start();
            }
        } else if (v == freeButton) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ObjectAnimator objectAnimator = ofFloat(freeButton, "TranslationY", 0, 40);
                objectAnimator.setDuration(100);
                objectAnimator.start();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                ObjectAnimator objectAnimator = ofFloat(freeButton, "TranslationY", 40, 0);
                objectAnimator.setDuration(100);
                objectAnimator.start();
            }
        }
        return false;
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String PMTool = data.getStringExtra("PMTool");
                    Log.i("PMTool", PMTool);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Bag.setVisibility(View.GONE);
                    pokemonBall.setVisibility(View.GONE);
                    run.setVisibility(View.GONE);
                    String pmBall = data.getStringExtra("PMBall");
                    Log.i("PMBall", pmBall);
                    List<PokeMonBall> list0 = DataSupport.where("Name = ?", pmBall).find(PokeMonBall.class);
                    C_PokeMonBall = list0.get(0);
                    List<OwnItem> list = DataSupport.where("Name = ?", pmBall).find(OwnItem.class);
                    OwnItem ownItem0 = list.get(0);
                    int a = ownItem0.getNumber() - 1;
                    if (a == 0) {
                        DataSupport.deleteAll(OwnItem.class, "Name = ?", ownItem0.getName());
                    } else {
                        OwnItem ownItem = new OwnItem();
                        ownItem.setNumber(a);
                        ownItem.updateAll("Name = ?", pmBall);
                    }
                    PMBall.setBackgroundResource(ownItem0.getImageResourceId());
                    PMBall.setVisibility(View.VISIBLE);
                    ProfileMotion(PMBall);
                }
                break;
        }
    }*/

    public void ScreenRun(View view) {
        ObjectAnimator anim1 = ofFloat(view, "scaleX",
                1.0f, 0.0f);
        ObjectAnimator anim2 = ofFloat(view, "translationX",
                0, 450);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(1500);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();

    }

    public void ProfileMotion(final View V) {
        fightMessage.setVisibility(View.GONE);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(2000);
        valueAnimator.setObjectValues(new PointF(70, 475));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue) {

                PointF point = new PointF();
                point.x = 70 + 350 * fraction * 2;
                point.y = 0.5f * 450 * ((1 - fraction) * 2) * ((1 - fraction) * 2);
                return point;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                V.setX(point.x);
                V.setY(point.y);
            }
        });
        ObjectAnimator objectAnimator = ofFloat(PMBall, "rotation",
                0.0f, 360f);
        objectAnimator.setDuration(200);
        objectAnimator.setRepeatCount(9);
        objectAnimator.start();
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                PMInto();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void PMInto() {
        ObjectAnimator anim0 = ofFloat(Pokemon, "rotation", 0, 0);
        anim0.setDuration(200);
        anim0.start();
        anim0.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator anim1 = ofFloat(Pokemon, "scaleX",
                        1.0f, 0.0f);
                ObjectAnimator anim2 = ofFloat(Pokemon, "scaleY",
                        1.0f, 0.0f);
                ObjectAnimator anim3 = ofFloat(Pokemon, "rotation",
                        0.0f, 360f);
                ObjectAnimator anim4 = ofFloat(Pokemon, "translationY",
                        0, -200);
                AnimatorSet animSet = new AnimatorSet();
                animSet.setDuration(1000);
                animSet.setInterpolator(new LinearInterpolator());
                animSet.play(anim1).with(anim2).with(anim3).with(anim4);
                animSet.start();
                animSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        HitGround();
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
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void HitGround() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(2400);
        valueAnimator.setObjectValues(new PointF(770, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                if (fraction < 0.3) {
                    point.x = 770;
                    point.y = 0.5f * 1157.4f * (fraction * 2.4f) * (fraction * 2.4f);
                } else if (fraction < 0.5) {
                    fraction = 0.5f - fraction;
                    point.x = 770;
                    point.y = 300 - 133.3f + 0.5f * 1157.4f * (fraction * 2.4f) * (fraction * 2.4f);
                } else if (fraction < 0.7) {
                    fraction -= 0.5f;
                    point.x = 770;
                    point.y = 300 - 133.3f + 0.5f * 1157.4f * (fraction * 2.4f) * (fraction * 2.4f);
                } else if (fraction < 0.85) {
                    fraction = 0.85f - fraction;
                    point.x = 770;
                    point.y = 300 - 75 + 0.5f * 1157.4f * (fraction * 2.4f) * (fraction * 2.4f);
                } else {
                    fraction -= 0.85f;
                    point.x = 770;
                    point.y = 300 - 75 + 0.5f * 1157.4f * (fraction * 2.4f) * (fraction * 2.4f);
                }
                return point;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                PMBall.setX(point.x);
                PMBall.setY(point.y);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator anim0 = ofFloat(PMBall, "rotation", 0, 0);
                anim0.setDuration(200);
                anim0.start();
                anim0.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Struggle();
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
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void Struggle() {
        ObjectAnimator objectAnimator = ofFloat(PMBall, "rotation", 0, 45);
        ObjectAnimator objectAnimator1 = ofFloat(PMBall, "rotation", 0, 0);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setObjectValues(new PointF(70, 475));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue) {
                PointF point = new PointF();
                point.x = 770 + fraction * 30;
                point.y = 300;
                return point;
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                PMBall.setX(point.x);
                PMBall.setY(point.y);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.play(objectAnimator).with(valueAnimator).after(objectAnimator1);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator objectAnimator = ofFloat(PMBall, "rotation", 45, 0);
                ValueAnimator valueAnimator = new ValueAnimator();
                valueAnimator.setObjectValues(new PointF(70, 475));
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
                    // fraction = t / duration
                    @Override
                    public PointF evaluate(float fraction, PointF startValue,
                                           PointF endValue) {
                        PointF point = new PointF();
                        point.x = 800 - fraction * 30;
                        point.y = 300;
                        return point;
                    }
                });
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        PointF point = (PointF) animation.getAnimatedValue();
                        PMBall.setX(point.x);
                        PMBall.setY(point.y);
                    }
                });
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(300);
                animatorSet.play(objectAnimator).with(valueAnimator);
                animatorSet.start();
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ObjectAnimator objectAnimator = ofFloat(PMBall, "rotation", 0, -45);
                        ObjectAnimator anim0 = ofFloat(PMBall, "rotation", 0, 0);
                        ValueAnimator valueAnimator = new ValueAnimator();
                        valueAnimator.setObjectValues(new PointF(70, 475));
                        valueAnimator.setInterpolator(new LinearInterpolator());
                        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
                            // fraction = t / duration
                            @Override
                            public PointF evaluate(float fraction, PointF startValue,
                                                   PointF endValue) {
                                PointF point = new PointF();
                                point.x = 770 - fraction * 30;
                                point.y = 300;
                                return point;
                            }
                        });
                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                PointF point = (PointF) animation.getAnimatedValue();
                                PMBall.setX(point.x);
                                PMBall.setY(point.y);
                            }
                        });
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.setDuration(300);
                        animatorSet.play(objectAnimator).with(valueAnimator).after(anim0);
                        animatorSet.start();
                        animatorSet.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ObjectAnimator objectAnimator = ofFloat(PMBall, "rotation", -45, 0);
                                ValueAnimator valueAnimator = new ValueAnimator();
                                valueAnimator.setObjectValues(new PointF(70, 475));
                                valueAnimator.setInterpolator(new LinearInterpolator());
                                valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
                                    // fraction = t / duration
                                    @Override
                                    public PointF evaluate(float fraction, PointF startValue,
                                                           PointF endValue) {
                                        PointF point = new PointF();
                                        point.x = 740 + fraction * 30;
                                        point.y = 300;
                                        return point;
                                    }
                                });
                                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        PointF point = (PointF) animation.getAnimatedValue();
                                        PMBall.setX(point.x);
                                        PMBall.setY(point.y);
                                    }
                                });
                                AnimatorSet animatorSet = new AnimatorSet();
                                animatorSet.setDuration(300);
                                animatorSet.play(objectAnimator).with(valueAnimator);
                                animatorSet.start();
                                animatorSet.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        StruggleCount++;
                                        if (StruggleCount < 2) {
                                            Struggle();
                                        } else {
                                            StruggleCount = 0;
                                            Judge();
                                        }
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
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
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
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void Judge() {
        if (PMJudge()) {
            fightMessage.setVisibility(View.VISIBLE);
            fightMessage.setText("恭喜！捕获成功！");
            next_text.setVisibility(View.VISIBLE);
            animation1 = AnimationUtils.loadAnimation(Capture.this, R.anim.up2);
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
            animation2 = AnimationUtils.loadAnimation(Capture.this, R.anim.down2);
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
            ObjectAnimator anim1 = ofFloat(Text_Screen, "scaleX",
                    1.0f, 0.0f);
            ObjectAnimator anim2 = ofFloat(Text_Screen, "translationX",
                    0, 450);
            AnimatorSet animSet = new AnimatorSet();
            animSet.setDuration(2000);
            animSet.setInterpolator(new LinearInterpolator());
            animSet.playTogether(anim1, anim2);
            animSet.start();

            AVQuery<AVObject> query = new AVQuery<>("OwnPet");
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    Log.i("OwnPetNumber", String.valueOf(list.size()));
                    boolean Repeat = false;
                    for (AVObject avObject: list) {
                        if (avObject.getString("Name").equals(C_PokeMon.getName())) {
                            Repeat = true;
                        }
                    }
                    Log.i("Repeat", String.valueOf(Repeat));
                    if (Repeat) {
                        MessageCount += 7;
                    } else {
                        if (list.size() < 9) {
                            MessageCount += 2;
                            OwnPet ownPet = new OwnPet("", C_PokeMon.getName(),
                                    C_PokeMon.getImageName2(), C_PokeMon.getNumber(),
                                    C_PokeMonBall.getImageName());
                            AVObject avObject = new AVObject("OwnPet");
                            avObject.put("Name", ownPet.getName());
                            avObject.put("ImageName", ownPet.getImageName());
                            avObject.put("Dex", ownPet.getDex());
                            avObject.put("BallImageName", ownPet.getBallImageName());
                        } else {
                            MessageCount += 3;
                        }
                    }
                }
            });
        } else {
            ObjectAnimator objectAnimator = ofFloat(PMBall, "rotation", 0, 0);
            objectAnimator.setDuration(300);
            objectAnimator.start();
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    fightMessage.setVisibility(View.VISIBLE);
                    fightMessage.setText("抱歉！捕获失败！");
                    next_text.setVisibility(View.VISIBLE);
                    animation1 = AnimationUtils.loadAnimation(Capture.this, R.anim.up2);
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
                    animation2 = AnimationUtils.loadAnimation(Capture.this, R.anim.down2);
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
                    ObjectAnimator anim1 = ofFloat(Text_Screen, "scaleX",
                            1.0f, 0.0f);
                    ObjectAnimator anim2 = ofFloat(Text_Screen, "translationX",
                            0, 450);
                    AnimatorSet animSet = new AnimatorSet();
                    animSet.setDuration(2000);
                    animSet.setInterpolator(new LinearInterpolator());
                    animSet.playTogether(anim1, anim2);
                    animSet.start();
                    PMEscape();
                    MessageCount++;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

    }

    public boolean PMJudge() {
        Double a = Math.random();
        Log.i("Random", String.valueOf(a));
        Double b = C_PokeMonBall.getRate();
        if (a <= b) {
            Judgement = true;
            Intent intent = new Intent(Capture.this, CaptureMusicServer.class);
            stopService(intent);
            playSuccessFile();
        } else {
            Judgement = false;
        }
        Log.i("Judgement", String.valueOf(Judgement));
        return Judgement;
    }

    public void PMEscape() {
        PMBall.setVisibility(View.GONE);
        Pokemon2.setVisibility(View.VISIBLE);
        ObjectAnimator objectAnimator1 = ofFloat(Pokemon2, "scaleX", 0.0f, 1.0f);
        ObjectAnimator objectAnimator2 = ofFloat(Pokemon2, "scaleY", 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(objectAnimator1, objectAnimator2);
        animatorSet.start();
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        super.onPause();
    }

    class GiveUpButton extends View{
        public GiveUpButton(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint p = new Paint();
            p.setStyle(Paint.Style.FILL);
            p.setColor(Color.WHITE);
            canvas.drawCircle(Width / 4, Height / 3, Width / 8, p);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(30);
            p.setColor(Color.RED);
            canvas.drawCircle(Width / 4, Height / 3, Width / 8, p);
            p.setStyle(Paint.Style.FILL);
            p.setStrokeWidth(4);
            p.setTextSize(72);
            canvas.drawText("放弃", Width / 4 - 72, Height / 3 + 36, p);
        }
    }

    class FreeButton extends View {
        public FreeButton(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint p = new Paint();
            p.setStyle(Paint.Style.FILL);
            p.setColor(Color.WHITE);
            canvas.drawCircle(Width / 4, Height / 3, Width / 8, p);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(30);
            p.setColor(Color.RED);
            canvas.drawCircle(Width / 4, Height / 3, Width / 8, p);
            p.setStyle(Paint.Style.FILL);
            p.setStrokeWidth(4);
            p.setTextSize(72);
            canvas.drawText("放生", Width / 4 - 72, Height / 3 + 36, p);
        }
    }

    private void playSuccessFile() {
        mediaPlayer = MediaPlayer.create(this, R.raw.capture_success);
        try {
            mediaPlayer.prepareAsync();
        } catch (IllegalStateException e) {

        }
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        super.onDestroy();
    }

}