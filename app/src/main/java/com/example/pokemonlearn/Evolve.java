package com.example.pokemonlearn;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;

import java.util.HashMap;

import static android.animation.ObjectAnimator.ofFloat;

/**
 * Created by Gama on 18/5/17.
 */

public class Evolve extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {
    private String User;

    private int Width;
    private int Height;

    private OwnPet E_OwnPet;
    private OwnItem E_PokeMonStone;
    private com.example.pokemonlearn.PokeMon ES_PokeMon;

    private ImageView transfer1;
    private ImageView transfer2;
    private Animation trans_out1;
    private Animation trans_out2;

    private ImageView Roof1;
    private ImageView Roof2;
    private ImageView PokeMon;
    private ImageView Stone;
    private ImageView Plus;
    int[] a = new int[2];
    private int PWidth;
    private int PHeight;
    private int PTop;
    private int PLeft;
    private int SWidth;
    private int SHeight;
    private int STop;
    private int SLeft;

    private Button Evolve;
    private Button Cancel;
    private Animation Float2;
    private Animation Float3;

    private ImageView Text;
    private TextView Message;
    private ImageView Screen;
    private ImageView Next_Text;
    private Animation Float1;
    private Animation animation1;
    private Animation animation2;
    private int MessageCount;

    private ImageView White;
    private ImageView Black;
    private ImageView Light1;
    private ImageView Light2;
    private ImageView Light3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evolve);

        Stone = (ImageView) findViewById(R.id.stone);
        PokeMon = (ImageView) findViewById(R.id.pokemon);
        PokeMon.setVisibility(View.GONE);
        Stone.setVisibility(View.GONE);

        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        Width = metrics.widthPixels;
        Height = metrics.heightPixels;
        Log.i("Windows", String.valueOf(Height) + " " + String.valueOf(Width));

        Intent intent = getIntent();
        String Name = intent.getStringExtra("PMName");
        User = intent.getStringExtra("User");
        Log.i("Name", Name);

        AVObject User1 = AVObject.createWithoutData("Users", User);
        AVRelation<AVObject> relation = User1.getRelation("OwnPet");
        AVQuery<AVObject> query = relation.getQuery();
        query.whereEqualTo("Name", Name);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                E_OwnPet = new OwnPet(avObject.getObjectId(),
                        avObject.getString("Name"),
                        avObject.getString("ImageName"),
                        avObject.getInt("Dex"),
                        avObject.getString("BallImageName"));

                AVQuery<AVObject> query3 = new AVQuery<>("_File");
                query3.whereEqualTo("name", E_OwnPet.getImageName() + ".png");
                query3.getFirstInBackground(new GetCallback<AVObject>() {
                    @Override
                    public void done(AVObject avObject, AVException e) {
                        AVFile avFile = new AVFile("PokeMon.png", avObject.getString("url"), new HashMap<String, Object>());
                        avFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, AVException e) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                PokeMon.setImageBitmap(bitmap);
                            }
                        });
                    }
                });
            }
        });

        String PMStone = intent.getStringExtra("PMStone");
        Log.i("PMStone", PMStone);
        AVRelation<AVObject> relation1 = User1.getRelation("OwnItem");
        AVQuery<AVObject> query1 = relation1.getQuery();
        query1.whereEqualTo("Name", PMStone);
        query1.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                E_PokeMonStone = new OwnItem(avObject.getObjectId(),
                        avObject.getString("Name"),
                        avObject.getInt("Number"),
                        avObject.getInt("Type"),
                        avObject.getString("ImageName"),
                        avObject.getInt("Dex"));
                AVQuery<AVObject> query4 = new AVQuery<>("_File");
                query4.whereEqualTo("name", E_PokeMonStone.getImageName() + ".png");
                query4.getFirstInBackground(new GetCallback<AVObject>() {
                    @Override
                    public void done(AVObject avObject, AVException e) {
                        AVFile avFile = new AVFile("Stone.png", avObject.getString("url"), new HashMap<String, Object>());
                        avFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, AVException e) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                Stone.setImageBitmap(bitmap);
                                UIInit();
                            }
                        });
                    }
                });
            }
        });
        String SName = intent.getStringExtra("S-PMName");
        Log.i("Senior-Name", SName);

        AVQuery<AVObject> query2 = new AVQuery<>("PM");
        query2.whereEqualTo("Name", SName);
        query2.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                ES_PokeMon = new PokeMon(avObject.getObjectId(),
                        avObject.getInt("Number"),
                        avObject.getString("Name"),
                        avObject.getString("ImageName2"),
                        avObject.getInt("Weight"),
                        avObject.getString("ImageName"),
                        avObject.getString("Senior"));
            }
        });



    }

    public void UIInit() {
        Roof1 = (ImageView) findViewById(R.id.roof1);
        Roof1.setVisibility(View.GONE);
        Roof2 = (ImageView) findViewById(R.id.roof2);
        Roof2.setVisibility(View.GONE);
        Plus = (ImageView) findViewById(R.id.plus);
        Plus.setVisibility(View.GONE);

        Evolve = (Button) findViewById(R.id.evolve);
        Evolve.setVisibility(View.GONE);
        Evolve.setOnClickListener(this);
        Evolve.setOnTouchListener(this);
        Cancel = (Button) findViewById(R.id.cancel);
        Cancel.setVisibility(View.GONE);
        Cancel.setOnClickListener(this);
        Cancel.setOnTouchListener(this);
        Float2 = AnimationUtils.loadAnimation(com.example.pokemonlearn.Evolve.this, R.anim.cap_float2);
        Float3 = AnimationUtils.loadAnimation(com.example.pokemonlearn.Evolve.this, R.anim.cap_float3);

        Text = (ImageView) findViewById(R.id.evolve_text);
        Text.setOnClickListener(this);
        Text.setVisibility(View.GONE);
        Message = (TextView) findViewById(R.id.evolve_message);
        Message.setVisibility(View.GONE);
        Screen = (ImageView) findViewById(R.id.screen);
        Screen.setVisibility(View.GONE);
        Next_Text = (ImageView) findViewById(R.id.next_text);
        Next_Text.setVisibility(View.GONE);
        Float1 = AnimationUtils.loadAnimation(com.example.pokemonlearn.Evolve.this, R.anim.cap_float1);
        Float1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                String tip = "确定使用 " + E_PokeMonStone.getName();
                Next_Text.setVisibility(View.VISIBLE);
                animation1 = AnimationUtils.loadAnimation(com.example.pokemonlearn.Evolve.this, R.anim.up2);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Next_Text.startAnimation(animation2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                animation2 = AnimationUtils.loadAnimation(com.example.pokemonlearn.Evolve.this, R.anim.down2);
                animation2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Next_Text.startAnimation(animation1);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                Next_Text.startAnimation(animation1);
                Message.setText(tip);
                ScreenRun(Screen);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        MessageCount = 0;

        transfer1 = (ImageView) findViewById(R.id.transfer1);
        transfer1.setVisibility(View.VISIBLE);
        transfer2 = (ImageView) findViewById(R.id.transfer2);
        transfer2.setVisibility(View.VISIBLE);
        trans_out1 = AnimationUtils.loadAnimation(com.example.pokemonlearn.Evolve.this, R.anim.trans_out_up);
        trans_out2 = AnimationUtils.loadAnimation(com.example.pokemonlearn.Evolve.this, R.anim.trans_out_down);
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
                InitUI();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Black = (ImageView) findViewById(R.id.black);
        White = (ImageView) findViewById(R.id.white);
        Light1 = (ImageView) findViewById(R.id.light1);
        Light2 = (ImageView) findViewById(R.id.light2);
        Light3 = (ImageView) findViewById(R.id.light3);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.evolve:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Evolve.getBackground().setAlpha(120);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Evolve.getBackground().setAlpha(255);
                }
                break;
            case R.id.cancel:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Cancel.getBackground().setAlpha(120);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Cancel.getBackground().setAlpha(255);
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evolve:
                Plus.setVisibility(View.GONE);
                int a = E_PokeMonStone.getNumber() - 1;
                if (a == 0) {
                    AVQuery<AVObject> query = new AVQuery<>("OwnItem");
                    query.whereEqualTo("Name", E_PokeMonStone.getName());
                    query.getFirstInBackground(new GetCallback<AVObject>() {
                        @Override
                        public void done(AVObject avObject, AVException e) {
                            avObject.deleteInBackground();
                        }
                    });
                } else {
                    AVQuery<AVObject> query = new AVQuery<>("OwnItem");
                    query.whereEqualTo("Name", E_PokeMonStone.getName());
                    query.getFirstInBackground(new GetCallback<AVObject>() {
                        @Override
                        public void done(AVObject avObject, AVException e) {
                            avObject.put("Number", E_PokeMonStone.getNumber() - 1);
                            avObject.saveInBackground();
                        }
                    });
                }
                final AVObject User1 = AVObject.createWithoutData("Users", User);
                AVRelation<AVObject> relation = User1.getRelation("OwnPet");
                AVQuery<AVObject> query = relation.getQuery();
                query.whereEqualTo("Name", E_OwnPet.getName());
                query.getFirstInBackground(new GetCallback<AVObject>() {
                    @Override
                    public void done(AVObject avObject, AVException e) {
                        avObject.put("Name", ES_PokeMon.getName());
                        avObject.put("ImageName", ES_PokeMon.getImageName2());
                        avObject.put("Dex", ES_PokeMon.getNumber());
                        avObject.saveInBackground();
                    }
                });
                Message.setText("");
                Combine();
                Evolve.setVisibility(View.GONE);
                Cancel.setVisibility(View.GONE);
                break;
            case R.id.cancel:
                Intent intent2 = new Intent();
                setResult(RESULT_CANCELED, intent2);
                finish();
                break;
            case R.id.evolve_text:
                if (MessageCount == 0) {
                    MessageCount++;
                    String tip = "进化 " + E_OwnPet.getName() + " 吗？";
                    ScreenRun(Screen);
                    Message.setText(tip);
                    animation1.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Next_Text.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    animation2.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Next_Text.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    Next_Text.setVisibility(View.GONE);
                    Evolve.setVisibility(View.VISIBLE);
                    Evolve.startAnimation(Float2);
                    Cancel.setVisibility(View.VISIBLE);
                    Cancel.startAnimation(Float3);
                }
        }
    }

    public void InitUI() {
        Roof1.setVisibility(View.VISIBLE);
        PokeMon.setVisibility(View.VISIBLE);

        ObjectAnimator Left1 = ofFloat(Roof1, "translationX", -Width, 0);
        Left1.setDuration(300);
        ObjectAnimator Left2 = ofFloat(PokeMon, "translationX", -Width, 0);
        Left2.setDuration(300);
        ObjectAnimator cd = ofFloat(Roof1, "rotation", 0, 0);
        cd.setDuration(300);

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.play(Left1).with(Left2).before(cd);
        animatorSet1.start();
        animatorSet1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Roof2.setVisibility(View.VISIBLE);
                Stone.setVisibility(View.VISIBLE);
                ObjectAnimator Right1 = ofFloat(Roof2, "translationX", Width, 0);
                Right1.setDuration(300);
                ObjectAnimator Right2 = ofFloat(Stone, "translationX", Width, 0);
                Right2.setDuration(300);
                ObjectAnimator cd = ofFloat(Roof2, "rotation", 0, 0);
                cd.setDuration(200);
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.play(Right1).with(Right2).before(cd);
                animatorSet2.start();
                animatorSet2.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Plus.setVisibility(View.VISIBLE);
                        ObjectAnimator Plus_Show1 = ofFloat(Plus, "rotation", -90, 0);
                        Plus_Show1.setDuration(800);
                        ObjectAnimator Plus_Show2 = ofFloat(Plus, "Alpha", 0, 1);
                        Plus_Show2.setDuration(800);
                        AnimatorSet animatorSet3 = new AnimatorSet();
                        animatorSet3.play(Plus_Show1).with(Plus_Show2);
                        animatorSet3.start();
                        Text.setVisibility(View.VISIBLE);
                        Text.startAnimation(Float1);
                        Message.setVisibility(View.VISIBLE);
                        Message.startAnimation(Float1);
                        Screen.setVisibility(View.VISIBLE);
                        Screen.startAnimation(Float1);
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

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(100);
        valueAnimator.setObjectValues(new PointF());
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                point.y = (-1) * fraction * 500;
                return point;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                Light1.setY(point.y);
            }
        });

    }

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

    public void Combine() {
        ObjectAnimator objectAnimator = ofFloat(Roof1, "Alpha", 1, 0);
        objectAnimator.setDuration(800);
        objectAnimator.start();
        ObjectAnimator objectAnimator1 = ofFloat(Roof2, "Alpha", 1, 0);
        objectAnimator1.setDuration(800);
        objectAnimator1.start();
        PokeMon.getLocationInWindow(a);
        PWidth = PokeMon.getWidth();
        PHeight = PokeMon.getHeight();
        PTop = PokeMon.getTop();
        PLeft = PokeMon.getLeft();
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(2000);
        valueAnimator.setObjectValues(new PointF(PLeft, PTop));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                point.x = PLeft - fraction * (PLeft - (Width / 2 - PWidth / 2));
                point.y = PTop + fraction * ((Height * 0.48f / 2) - PHeight / 2 - PTop);
                return point;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                PokeMon.setX(point.x);
                PokeMon.setY(point.y);
            }
        });
        Stone.getLocationInWindow(a);
        SLeft = Stone.getLeft();
        SWidth = Stone.getWidth();
        SHeight = Stone.getHeight();
        STop = Stone.getTop();
        ValueAnimator valueAnimator1 = new ValueAnimator();
        valueAnimator1.setDuration(2000);
        valueAnimator1.setObjectValues(new PointF(SLeft, STop));
        valueAnimator1.setInterpolator(new LinearInterpolator());
        valueAnimator1.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                point.x = SLeft + fraction * (Width / 2 - SWidth / 2 - SLeft);
                point.y = STop - fraction * (STop - Height * 0.48f / 2 + SHeight / 2);
                return point;
            }
        });
        valueAnimator1.start();
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                Stone.setX(point.x);
                Stone.setY(point.y);
            }
        });
        valueAnimator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator cd = ofFloat(PokeMon, "rotation", 0, 0);
                cd.setDuration(200);
                cd.start();
                cd.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        PrePare();
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

        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat("scaleY", 1, 0.3f);
        PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("scaleX", 1, 0.3f);
        PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("rotation", 0, 360);
        ObjectAnimator.ofPropertyValuesHolder(Stone,
                propertyValuesHolder, propertyValuesHolder1, propertyValuesHolder2).
                setDuration(2000).start();
    }

    public void PrePare() {
        Light2.setVisibility(View.VISIBLE);
        Light2.getLocationInWindow(a);
        ValueAnimator valueAnimator0 = new ValueAnimator();
        valueAnimator0.setDuration(1000);
        valueAnimator0.setObjectValues(new PointF());
        valueAnimator0.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                point.y = (-1) * Light2.getHeight() + fraction * (Height / 2 + Light2.getHeight() / 2);
                return point;
            }
        });
        valueAnimator0.start();
        valueAnimator0.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                Light2.setY(point.y);
            }
        });
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(Light2, "Alpha", 0, 0);
        objectAnimator.setDuration(1000);
        objectAnimator.start();

        Light3.setVisibility(View.VISIBLE);
        Light3.getLocationInWindow(a);
        ValueAnimator valueAnimator3 = new ValueAnimator();
        valueAnimator3.setDuration(1000);
        valueAnimator3.setObjectValues(new PointF());
        valueAnimator3.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                point.y = (-1) * Light3.getHeight() + fraction * (Height / 2 + Light3.getHeight() / 2);
                return point;
            }
        });
        valueAnimator3.start();
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                Light3.setY(point.y);
            }
        });
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat("scaleY", 1, 50f);
        PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("scaleX", 1, 50f);
        PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("alpha", 0, 0);
        ObjectAnimator.ofPropertyValuesHolder(Light3,
                propertyValuesHolder, propertyValuesHolder1, propertyValuesHolder2).
                setDuration(1000).start();

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(Width / 2 - PWidth / 2, Height * 0.48f / 2 - PHeight / 2));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                point.x = Width / 2 - PWidth / 2;
                point.y = Height * 0.48f / 2 - PHeight / 2 + fraction * Height * 0.26f;
                return point;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                PokeMon.setX(point.x);
                PokeMon.setY(point.y);
            }
        });

        Stone.getLocationInWindow(a);

        ValueAnimator valueAnimator1 = new ValueAnimator();
        valueAnimator1.setDuration(3000);
        valueAnimator1.setObjectValues(new PointF(Width / 2 - Stone.getWidth() / 2,
                Height * 0.48f / 2 - Stone.getHeight() / 2));
        valueAnimator1.setInterpolator(new LinearInterpolator());
        valueAnimator1.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                point.x = Width / 2 - Stone.getWidth() / 2;
                point.y = Height * 0.48f / 2 - Stone.getHeight() / 2 + fraction * Height * 0.26f;
                return point;
            }
        });
        valueAnimator1.start();
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                Stone.setX(point.x);
                Stone.setY(point.y);
            }
        });

        Black.setVisibility(View.VISIBLE);
        ObjectAnimator black = ofFloat(Black, "Alpha", 0, 1.0f);
        black.setDuration(3000);
        black.setInterpolator(new LinearInterpolator());
        black.start();
        black.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Shining();
                LightDown();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void Shining() {
        Light2.setVisibility(View.VISIBLE);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(Light2, "ScaleX", 0, 0.7f, 1, 0.7f, 1, 0.7f);
        objectAnimator.setDuration(4500);
        objectAnimator.start();
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(Light2, "ScaleY", 0, 0.7f, 1, 0.7f, 1, 0.7f);
        objectAnimator1.setDuration(4500);
        objectAnimator1.start();
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(Light2, "Alpha", 0, 1);
        objectAnimator2.setDuration(400);
        objectAnimator2.start();
    }

    public void LightDown() {
        Light1.setVisibility(View.VISIBLE);
        Light1.getLocationInWindow(a);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(5000);
        valueAnimator.setStartDelay(500);
        valueAnimator.setObjectValues(new PointF(Width / 2 - Light1.getWidth() / 2,
                -Light1.getHeight()));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                point.x = Width / 2 - Light1.getWidth() / 2;
                point.y = (-1) * Light1.getHeight() + fraction * (Height / 2 + Light1.getHeight() / 2);
                return point;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                Light1.setX(point.x);
                Light1.setY(point.y);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                TheFlash();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        ObjectAnimator o = ObjectAnimator.ofFloat(Light1, "ScaleX", 1, 0.7f, 1, 0.7f, 1, 0.7f, 1);
        o.setDuration(5000);
        o.start();
        ObjectAnimator o1 = ObjectAnimator.ofFloat(Light1, "ScaleY", 1, 0.7f, 1, 0.7f, 1, 0.7f, 1);
        o1.setDuration(5000);
        o1.start();
    }

    public void TheFlash() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(Light2, "ScaleX", 1, 50);
        objectAnimator.setDuration(100);
        objectAnimator.start();
        ObjectAnimator cd = ObjectAnimator.ofFloat(Light2, "Alpha", 1, 1);
        cd.setDuration(1000);
        cd.start();
        cd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(Light2, "ScaleY", 1, 50);
                objectAnimator1.setDuration(100);
                objectAnimator1.start();
                ObjectAnimator o = ObjectAnimator.ofFloat(Light3, "Alpha", 0, 1);
                o.setDuration(100);
                o.start();
                objectAnimator1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        PokeMon.setVisibility(View.GONE);
                        Stone.setVisibility(View.GONE);
                        White.setVisibility(View.VISIBLE);
                        Light1.setVisibility(View.GONE);
                        Light2.setVisibility(View.GONE);
                        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("_File");
                        avObjectAVQuery.whereEqualTo("name", ES_PokeMon.getImageName2() + ".png");
                        avObjectAVQuery.getFirstInBackground(new GetCallback<AVObject>() {
                            @Override
                            public void done(AVObject avObject, AVException e) {
                                AVFile avFile = new AVFile("PokeMon.png", avObject.getString("url"), new HashMap<String, Object>());
                                avFile.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] bytes, AVException e) {
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        PokeMon.setImageBitmap(bitmap);
                                        ObjectAnimator cd = ObjectAnimator.ofFloat(Light3, "Rotation", 0, 0);
                                        cd.setDuration(2000);
                                        cd.start();
                                        cd.addListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                TheEnd();
                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animation) {

                                            }
                                        });
                                    }
                                });
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

    public void TheEnd() {
        PokeMon.setVisibility(View.VISIBLE);
        ObjectAnimator o = ObjectAnimator.ofFloat(PokeMon, "Alpha", 0, 1);
        o.setDuration(3000);
        o.start();
        ObjectAnimator o1 = ObjectAnimator.ofFloat(PokeMon, "ScaleX", 1, 2);
        o1.setDuration(100);
        o1.start();
        ObjectAnimator o2 = ObjectAnimator.ofFloat(PokeMon, "ScaleY", 1, 2);
        o2.setDuration(100);
        o2.start();

        ObjectAnimator cd = ObjectAnimator.ofFloat(PokeMon, "Rotation", 0, 0);
        cd.setDuration(3000);
        cd.start();

        White.setVisibility(View.GONE);

        ObjectAnimator o3 = ObjectAnimator.ofFloat(Light3, "ScaleX", 50, 2);
        o3.setDuration(1000);
        ObjectAnimator o4 = ObjectAnimator.ofFloat(Light3, "ScaleY", 50, 2);
        o4.setDuration(1000);
        AnimatorSet as = new AnimatorSet();
        as.play(o3).with(o4).after(cd);
        as.start();
        as.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator cd = ObjectAnimator.ofFloat(PokeMon, "rotation", 0, 0);
                cd.setDuration(3000);
                cd.start();
                cd.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ComeBack();
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

    public void ComeBack() {
        ObjectAnimator o0 = ObjectAnimator.ofFloat(Light3, "Alpha", 1, 0);
        o0.setDuration(1000);
        o0.start();
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(Width / 2 - PWidth / 2, Height / 2 - PHeight / 2));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                point.x = Width / 2 - PWidth / 2;
                point.y = Height / 2 - PHeight / 2 - fraction * Height * 0.26f;
                return point;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                PokeMon.setX(point.x);
                PokeMon.setY(point.y);
            }
        });
        ObjectAnimator o = ObjectAnimator.ofFloat(Black, "Alpha", 1, 0);
        o.setDuration(3000);
        o.start();
        o.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                String tip = "恭喜！成功进化 " + ES_PokeMon.getName() + " ！";
                Message.setText(tip);
                ScreenRun(Screen);
                Next_Text.setVisibility(View.VISIBLE);
                animation1 = AnimationUtils.loadAnimation(com.example.pokemonlearn.Evolve.this, R.anim.up2);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Next_Text.startAnimation(animation2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                animation2 = AnimationUtils.loadAnimation(com.example.pokemonlearn.Evolve.this, R.anim.down2);
                animation2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Next_Text.startAnimation(animation1);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                Next_Text.startAnimation(animation1);
                Text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
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

}
