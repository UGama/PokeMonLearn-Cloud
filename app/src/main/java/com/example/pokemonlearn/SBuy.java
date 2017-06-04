package com.example.pokemonlearn;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gama on 22/5/17.
 */

public class SBuy extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private ImageView transfer1;
    private ImageView transfer2;
    private Animation trans_out1;
    private Animation trans_out2;

    private String User;
    private List<OwnItem> ownItems;
    private String ChoseName;

    private PercentRelativeLayout Layout_Left1;
    private PercentRelativeLayout Layout_Left2;
    private PercentRelativeLayout Layout_Right;
    private Animation float2;
    private Animation float3;
    private ImageView Connect1;
    private ImageView Connect2;
    private ImageView Pet_Init;
    private RecyclerView recyclerView;
    private Animation animation2;
    private Animation animation3;
    private boolean FirstTouch;
    private ImageView S_Pic;
    private ImageView Item_Pic;
    private TextView Item_Name;
    private Animation anim4;

    private ImageView RightArrow;
    private ImageView LeftArrow;
    private AnimatorSet Right;
    private AnimatorSet Left;

    private Button Buy;
    private Button Cancel;

    private TextView SBuy_Message;
    private ImageView Screen;
    private int Price;

    private ViewPage v1;
    private ViewPage v2;
    private ViewPage v3;
    private ViewPage v4;
    private ViewPage v5;

    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private ViewPagerIndicator indicator;
    private int PagesCount;

    private int MessageCount;

    private ImageView Black;
    private PercentRelativeLayout Dialog;
    private EditText editText;
    private Button D_Confirm;
    private Button D_Cancel;
    private int number;//(Buy Count)
    private int NumberInDex;
    private int SceneResource;

    private List<Item> items1;
    private List<Item> items2;
    private List<Item> items3;
    private List<Item> items4;
    private List<Item> items5;

    private PercentRelativeLayout Support;
    private ProgressBar progressBar;

    private int MyCoin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_series);

        S_Pic = (ImageView) findViewById(R.id.S_Pic);
        S_Pic.setVisibility(View.GONE);
        Item_Pic = (ImageView) findViewById(R.id.item_pic);
        Item_Pic.setVisibility(View.GONE);
        Item_Name = (TextView) findViewById(R.id.item_name);
        Item_Name.setVisibility(View.GONE);

        Intent intent = getIntent();
        User = intent.getStringExtra("User");
        ownItems = new ArrayList<>();
        AVObject user = AVObject.createWithoutData("Users", User);
        AVRelation<AVObject> relation = user.getRelation("OwnItem");
        AVQuery<AVObject> query = relation.getQuery();
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    OwnItem ownItem = new OwnItem(avObject.getObjectId(),
                            avObject.getString("Name"),
                            avObject.getInt("Number"),
                            avObject.getInt("Type"),
                            avObject.getString("ImageName"),
                            avObject.getInt("Dex"));
                    ownItems.add(ownItem);
                }
            }
        });
        items1 = new ArrayList<>();
        AVQuery<AVObject> query1 = new AVQuery<>("PokeMonBall");
        query1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    Item item = new Item(avObject.getString("Name"),
                            avObject.getString("ImageName"), 1,
                            avObject.getInt("Price"));
                    items1.add(item);
                }

            }
        });

        items2 = new ArrayList<>();
        AVQuery<AVObject> query2 = new AVQuery<>("PokeMonTool");
        query2.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    Item item = new Item(avObject.getString("Name"),
                            avObject.getString("ImageName"), 2,
                            avObject.getInt("Price"));
                    items2.add(item);
                }
            }
        });

        items3 = new ArrayList<>();
        AVQuery<AVObject> query3 = new AVQuery<>("PokeMonStone");
        query3.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    Item item = new Item(avObject.getString("Name"),
                            avObject.getString("ImageName"), 3,
                            avObject.getInt("Price"));
                    items3.add(item);
                }

            }
        });

        items4 = new ArrayList<>();
        AVQuery<AVObject> query4 = new AVQuery<>("PokeMonBook");
        query4.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    Item item = new Item(avObject.getString("Name"),
                            avObject.getString("ImageName"), 4,
                            avObject.getInt("Price"));
                    items4.add(item);
                }
            }
        });

        items5 = new ArrayList<>();
        AVQuery<AVObject> query5 = new AVQuery<>("Scene");
        query5.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    Item item = new Item(String.valueOf(avObject.getInt("Number")),
                            avObject.getString("ImageName"), 5,
                            avObject.getInt("Price"));
                    items5.add(item);
                }
                recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
                myPagerAdapter = new MyPagerAdapter();
                viewPager = (ViewPager) findViewById(R.id.viewPager);
                viewPager.setVisibility(View.GONE);
                viewPager.setAdapter(myPagerAdapter);
                indicator = (ViewPagerIndicator) findViewById(R.id.indicator);
                indicator.setLength(myPagerAdapter.List.size());
                anim4 = AnimationUtils.loadAnimation(SBuy.this, R.anim.anim4);
                UIInit();
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        indicator.setSelected(position);
                        position = position % 5;
                        FirstTouch = true;
                        Buy.setVisibility(View.GONE);
                        Cancel.setVisibility(View.GONE);
                        Log.i("Page", String.valueOf(position));
                        switch (position) {
                            case 0:
                                Item_Pic.setVisibility(View.VISIBLE);
                                Item_Name.setVisibility(View.VISIBLE);
                                Pet_Init.setVisibility(View.GONE);
                                S_Pic.startAnimation(anim4);
                                S_Pic.setImageResource(v1.SourceId1);
                                Item_Pic.setImageResource(v1.SourceId2);
                                Item_Name.setText(" ? ? ? ");
                                PagesCount = 1;
                                break;
                            case 1:
                                Item_Pic.setVisibility(View.VISIBLE);
                                Item_Name.setVisibility(View.VISIBLE);
                                Pet_Init.setVisibility(View.GONE);
                                S_Pic.startAnimation(anim4);
                                S_Pic.setImageResource(v2.SourceId1);
                                Item_Pic.setImageResource(v2.SourceId2);
                                Item_Name.setText(" ? ? ? ");
                                PagesCount = 2;
                                break;
                            case 2:
                                Item_Pic.setVisibility(View.VISIBLE);
                                Item_Name.setVisibility(View.VISIBLE);
                                Pet_Init.setVisibility(View.GONE);
                                S_Pic.startAnimation(anim4);
                                S_Pic.setImageResource(v3.SourceId1);
                                Item_Pic.setImageResource(v3.SourceId2);
                                Item_Name.setText(" ? ? ? ");
                                PagesCount = 3;
                                break;
                            case 3:
                                Item_Pic.setVisibility(View.VISIBLE);
                                Item_Name.setVisibility(View.VISIBLE);
                                Pet_Init.setVisibility(View.GONE);
                                S_Pic.startAnimation(anim4);
                                S_Pic.setImageResource(v4.SourceId1);
                                Item_Pic.setImageResource(v4.SourceId2);
                                Item_Name.setText(" ? ? ? ");
                                PagesCount = 4;
                                break;
                            case 4:
                                Pet_Init.setVisibility(View.VISIBLE);
                                S_Pic.setImageResource(v5.SourceId1);
                                Pet_Init.setImageResource(v5.SourceId2);
                                Item_Pic.setVisibility(View.GONE);
                                Item_Name.setVisibility(View.GONE);
                                PagesCount = 5;
                                break;
                        }
                        Log.i("Page", String.valueOf(PagesCount));
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        });


    }

    public void UIInit() {
        transfer1 = (ImageView) findViewById(R.id.transfer1);
        transfer1.setVisibility(View.VISIBLE);
        transfer2 = (ImageView) findViewById(R.id.transfer2);
        transfer2.setVisibility(View.VISIBLE);
        trans_out1 = AnimationUtils.loadAnimation(SBuy.this, R.anim.trans_out_up);
        trans_out2 = AnimationUtils.loadAnimation(SBuy.this, R.anim.trans_out_down);
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
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Pet_Init = (ImageView) findViewById(R.id.pet_init);
        Pet_Init.setVisibility(View.GONE);
        Pet_Init.setOnClickListener(this);

        animation2 = AnimationUtils.loadAnimation(SBuy.this, R.anim.anim2);
        animation3 = AnimationUtils.loadAnimation(SBuy.this, R.anim.anim3);
        Layout_Left1 = (PercentRelativeLayout) findViewById(R.id.Layout_Left1);
        Layout_Right = (PercentRelativeLayout) findViewById(R.id.Layout_Right);
        Layout_Left1.startAnimation(animation2);
        Layout_Right.startAnimation(animation2);
        Connect1 = (ImageView) findViewById(R.id.connect1);
        Connect1.startAnimation(animation3);
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewPager.setVisibility(View.VISIBLE);
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(viewPager, "translationX", 800, 0);
                objectAnimator1.setDuration(600);
                objectAnimator1.setInterpolator(new LinearInterpolator());
                objectAnimator1.start();
                S_Pic.setVisibility(View.VISIBLE);
                Item_Name.setVisibility(View.VISIBLE);
                Item_Pic.setVisibility(View.VISIBLE);
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(S_Pic, "translationX", -800, 0);
                objectAnimator2.setDuration(600);
                objectAnimator2.setInterpolator(new LinearInterpolator());
                objectAnimator2.start();
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(Item_Name, "translationX", -800, 0);
                objectAnimator3.setDuration(600);
                objectAnimator3.setInterpolator(new LinearInterpolator());
                objectAnimator3.start();
                ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(Item_Pic, "translationX", -800, 0);
                objectAnimator4.setDuration(600);
                objectAnimator4.setInterpolator(new LinearInterpolator());
                objectAnimator4.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        indicator.startAnimation(animation3);
        animation3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RightArrow.setVisibility(View.VISIBLE);
                LeftArrow.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        RightArrow = (ImageView) findViewById(R.id.right_arrow);
        RightArrow.setVisibility(View.GONE);
        LeftArrow = (ImageView) findViewById(R.id.left_arrow);
        LeftArrow.setVisibility(View.GONE);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(RightArrow, "translationX", 0, 50);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(RightArrow, "translationX", 50, 0);
        Right = new AnimatorSet();
        Right.setDuration(400);
        Right.play(objectAnimator2).after(objectAnimator1);
        Right.start();
        Right.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Right.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        Left = new AnimatorSet();
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(LeftArrow, "translationX", 0, -50);
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(LeftArrow, "translationX", -50, 0);
        Left.setDuration(400);
        Left.play(objectAnimator4).after(objectAnimator3);
        Left.start();
        Left.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Left.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        Layout_Left2 = (PercentRelativeLayout) findViewById(R.id.Layout_Left2);
        Layout_Left2.startAnimation(animation2);
        Connect2 = (ImageView) findViewById(R.id.connect2);
        Connect2.startAnimation(animation3);
        FirstTouch = true;

        Buy = (Button) findViewById(R.id.action);
        Buy.setOnClickListener(this);
        Buy.setOnTouchListener(this);
        Buy.setBackgroundResource(R.drawable.s_buy);
        Buy.setVisibility(View.GONE);
        Cancel = (Button) findViewById(R.id.cancel);
        Cancel.setOnClickListener(this);
        Cancel.setOnTouchListener(this);
        Cancel.setVisibility(View.GONE);

        float2 = AnimationUtils.loadAnimation(SBuy.this, R.anim.cap_float2);
        float3 = AnimationUtils.loadAnimation(SBuy.this, R.anim.cap_float3);

        SBuy_Message = (TextView) findViewById(R.id.s_message);
        Screen = (ImageView) findViewById(R.id.screen);

        PagesCount = 1;
        MessageCount = 0;

        Black = (ImageView) findViewById(R.id.black);
        Black.getBackground().setAlpha(120);
        Dialog = (PercentRelativeLayout) findViewById(R.id.dialog);
        editText = (EditText) findViewById(R.id.editText);
        D_Confirm = (Button) findViewById(R.id.d_confirm);
        D_Confirm.setOnClickListener(this);
        D_Confirm.setOnTouchListener(this);
        D_Cancel = (Button) findViewById(R.id.d_cancel);
        D_Cancel.setOnTouchListener(this);
        D_Cancel.setOnClickListener(this);

        Support = (PercentRelativeLayout) findViewById(R.id.Support);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action:
                if (MessageCount == 0) {
                    Buy.setBackgroundResource(R.drawable.s_comfirm);
                    Buy.startAnimation(float2);
                    Cancel.startAnimation(float3);
                    String tip = "确定要购买 " + Item_Name.getText().toString() + " 吗？";
                    SBuy_Message.setText(tip);
                    ScreenRun(Screen);
                    MessageCount = 1;
                } else if (MessageCount == 1) {
                    if (PagesCount == 5) {
                        AVQuery<AVObject> query0 = new AVQuery<>("Users");
                        query0.getInBackground(User, new GetCallback<AVObject>() {
                            @Override
                            public void done(AVObject avObject, AVException e) {
                                MyCoin = avObject.getInt("Coin");
                                if (MyCoin >= Price) {
                                    MyCoin -= Price;
                                    AVQuery<AVObject> query = new AVQuery<>("Users");
                                    query.getInBackground(User, new GetCallback<AVObject>() {
                                        @Override
                                        public void done(AVObject avObject, AVException e) {
                                            avObject.put("Coin", MyCoin);
                                            avObject.saveInBackground();
                                        }
                                    });
                                    AVQuery<AVObject> query5 = new AVQuery<>("Scene");
                                    query5.whereEqualTo("Number", Integer.valueOf(ChoseName));
                                    query5.getFirstInBackground(new GetCallback<AVObject>() {
                                        @Override
                                        public void done(AVObject avObject, AVException e) {
                                            AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("_File");
                                            avObjectAVQuery.whereEqualTo("name", avObject.getString("ImageName") + ".png");
                                            avObjectAVQuery.getFirstInBackground(new GetCallback<AVObject>() {
                                                @Override
                                                public void done(AVObject avObject, AVException e) {
                                                    final String Url = avObject.getString("url");
                                                    AVQuery<AVObject> query = new AVQuery<>("Users");
                                                    query.getInBackground(User, new GetCallback<AVObject>() {
                                                        @Override
                                                        public void done(AVObject avObject, AVException e) {
                                                            avObject.put("Scene", Url);
                                                            avObject.saveInBackground();
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                                    AlertDialog alertDialog = new AlertDialog.Builder(SBuy.this).setTitle("恭喜")
                                            .setMessage("购买成功！")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    MessageCount = 0;
                                                    Buy.setBackgroundResource(R.drawable.s_buy);
                                                    SBuy_Message.setText("");
                                                    FirstTouch = true;
                                                }
                                            })
                                            .show();
                                } else {
                                    AlertDialog alertDialog = new AlertDialog.Builder(SBuy.this).setTitle("抱歉")
                                            .setMessage("余额不足请充值。")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .show();
                                }
                            }
                        });

                    } else {
                        Black.setVisibility(View.VISIBLE);
                        Dialog.setVisibility(View.VISIBLE);
                        Buy.setVisibility(View.GONE);
                        Cancel.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.cancel:
                if (MessageCount == 0) {
                    finish();
                } else if (MessageCount == 1) {
                    MessageCount = 0;
                    Buy.setBackgroundResource(R.drawable.s_buy);
                    Buy.startAnimation(float2);
                    Cancel.startAnimation(float3);
                }
                break;
            case R.id.d_confirm:
                if (editText.getText().toString().equals("")) {
                    Dialog_Show();
                } else if (Integer.valueOf(editText.getText().toString()) <= 0) {
                    Dialog_Show();
                } else {
                    number = Integer.valueOf(editText.getText().toString());
                    AVQuery<AVObject> query = new AVQuery<>("Users");
                    query.getInBackground(User, new GetCallback<AVObject>() {
                        @Override
                        public void done(AVObject avObject, AVException e) {
                            MyCoin = avObject.getInt("Coin");
                            if (MyCoin >= Price * number) {
                                MyCoin -= Price * number;
                                AVQuery<AVObject> query2 = new AVQuery<>("Users");
                                query2.getInBackground(User, new GetCallback<AVObject>() {
                                    @Override
                                    public void done(AVObject avObject, AVException e) {
                                        avObject.put("Coin", MyCoin);
                                        avObject.saveInBackground();
                                    }
                                });
                                String item_name = Item_Name.getText().toString();
                                boolean isHaven = false;
                                for (final OwnItem ownItem : ownItems) {
                                    if (ownItem.getName().equals(item_name)) {
                                        isHaven = true;
                                        final AVObject User1 = AVObject.createWithoutData("Users", User);
                                        AVRelation<AVObject> relation = User1.getRelation("OwnItem");
                                        AVQuery<AVObject> query1 = relation.getQuery();
                                        query1.whereEqualTo("Name", item_name);
                                        query1.getFirstInBackground(new GetCallback<AVObject>() {
                                            @Override
                                            public void done(AVObject avObject, AVException e) {
                                                avObject.put("Number", ownItem.getNumber() + number);
                                                avObject.saveInBackground();
                                            }
                                        });
                                        break;
                                    }
                                }
                                if (!isHaven) {
                                    switch (PagesCount) {
                                        case 1:
                                            AVQuery<AVObject> query = new AVQuery<>("PokeMonBall");
                                            query.whereEqualTo("Name", Item_Name.getText().toString());
                                            query.getFirstInBackground(new GetCallback<AVObject>() {
                                                @Override
                                                public void done(AVObject avObject, AVException e) {
                                                    final AVObject User1 = AVObject.createWithoutData("Users", User);
                                                    final AVObject avObject1 = new AVObject("OwnItem");
                                                    avObject1.put("Name", avObject.getString("Name"));
                                                    avObject1.put("Number", number);
                                                    avObject1.put("ImageName", avObject.getString("ImageName"));
                                                    avObject1.put("Dex", avObject.getInt("Number"));
                                                    avObject1.put("Type", 1);

                                                    AVObject.saveAllInBackground(Arrays.asList(avObject1), new SaveCallback() {
                                                        @Override
                                                        public void done(AVException e) {
                                                            AVRelation<AVObject> relation = User1.getRelation("OwnItem");
                                                            relation.add(avObject1);
                                                            User1.saveInBackground();
                                                        }
                                                    });
                                                }
                                            });
                                            break;
                                        case 2:
                                            AVQuery<AVObject> query3 = new AVQuery<>("PokeMonTool");
                                            query3.whereEqualTo("Name", Item_Name.getText().toString());
                                            query3.getFirstInBackground(new GetCallback<AVObject>() {
                                                @Override
                                                public void done(AVObject avObject, AVException e) {
                                                    final AVObject User1 = AVObject.createWithoutData("Users", User);
                                                    final AVObject avObject1 = new AVObject("OwnItem");
                                                    avObject1.put("Name", avObject.getString("Name"));
                                                    avObject1.put("Number", number);
                                                    avObject1.put("ImageName", avObject.getString("ImageName"));
                                                    avObject1.put("Dex", avObject.getInt("Number"));
                                                    avObject1.put("Type", 2);

                                                    AVObject.saveAllInBackground(Arrays.asList(avObject1), new SaveCallback() {
                                                        @Override
                                                        public void done(AVException e) {
                                                            AVRelation<AVObject> relation = User1.getRelation("OwnItem");
                                                            relation.add(avObject1);
                                                            User1.saveInBackground();
                                                        }
                                                    });
                                                }
                                            });
                                            break;
                                        case 3:
                                            AVQuery<AVObject> query4 = new AVQuery<>("PokeMonStone");
                                            query4.whereEqualTo("Name", Item_Name.getText().toString());
                                            query4.getFirstInBackground(new GetCallback<AVObject>() {
                                                @Override
                                                public void done(AVObject avObject, AVException e) {
                                                    final AVObject User1 = AVObject.createWithoutData("Users", User);
                                                    final AVObject avObject1 = new AVObject("OwnItem");
                                                    avObject1.put("Name", avObject.getString("Name"));
                                                    avObject1.put("Number", number);
                                                    avObject1.put("ImageName", avObject.getString("ImageName"));
                                                    avObject1.put("Dex", avObject.getInt("Number"));
                                                    avObject1.put("Type", 3);

                                                    AVObject.saveAllInBackground(Arrays.asList(avObject1), new SaveCallback() {
                                                        @Override
                                                        public void done(AVException e) {
                                                            AVRelation<AVObject> relation = User1.getRelation("OwnItem");
                                                            relation.add(avObject1);
                                                            User1.saveInBackground();
                                                        }
                                                    });
                                                }
                                            });
                                            break;
                                        case 4:
                                            AVQuery<AVObject> query5 = new AVQuery<>("PokeMonBook");
                                            query5.whereEqualTo("Name", Item_Name.getText().toString());
                                            query5.getFirstInBackground(new GetCallback<AVObject>() {
                                                @Override
                                                public void done(AVObject avObject, AVException e) {
                                                    final AVObject User1 = AVObject.createWithoutData("Users", User);
                                                    final AVObject avObject1 = new AVObject("OwnItem");
                                                    avObject1.put("Name", avObject.getString("Name"));
                                                    avObject1.put("Number", number);
                                                    avObject1.put("ImageName", avObject.getString("ImageName"));
                                                    avObject1.put("Dex", avObject.getInt("Number"));
                                                    avObject1.put("Type", 4);

                                                    AVObject.saveAllInBackground(Arrays.asList(avObject1), new SaveCallback() {
                                                        @Override
                                                        public void done(AVException e) {
                                                            AVRelation<AVObject> relation = User1.getRelation("OwnItem");
                                                            relation.add(avObject1);
                                                            User1.saveInBackground();
                                                        }
                                                    });
                                                }
                                            });
                                            break;
                                    }
                                }
                                AlertDialog alertDialog = new AlertDialog.Builder(SBuy.this).setTitle("恭喜")
                                        .setMessage("购买成功！")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                Dialog.setVisibility(View.GONE);
                                                Black.setVisibility(View.GONE);
                                                editText.setText("");
                                            }
                                        })
                                        .show();
                                MessageCount = 0;
                                Buy.setBackgroundResource(R.drawable.s_buy);
                                SBuy_Message.setText("");
                                FirstTouch = true;
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(SBuy.this).setTitle("抱歉")
                                        .setMessage("余额不足请充值。")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();
                                MessageCount = 0;
                                Buy.setBackgroundResource(R.drawable.s_buy);
                                SBuy_Message.setText("");
                                FirstTouch = true;
                            }
                        }
                    });
                }
                break;
            case R.id.d_cancel:
                Dialog.setVisibility(View.GONE);
                Black.setVisibility(View.GONE);
                editText.setText("");
                Buy.setVisibility(View.VISIBLE);
                Buy.setBackgroundResource(R.drawable.s_buy);
                Buy.startAnimation(float2);
                Cancel.setVisibility(View.VISIBLE);
                Cancel.startAnimation(float3);
                MessageCount = 0;
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.action:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Buy.getBackground().setAlpha(120);
                } else {
                    Buy.getBackground().setAlpha(255);
                }
                break;
            case R.id.cancel:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Cancel.getBackground().setAlpha(120);
                } else {
                    Cancel.getBackground().setAlpha(255);
                }
                break;
            case R.id.d_confirm:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    D_Confirm.getBackground().setAlpha(120);
                } else {
                    D_Confirm.getBackground().setAlpha(255);
                }
                break;
            case R.id.d_cancel:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    D_Cancel.getBackground().setAlpha(120);
                } else {
                    D_Cancel.getBackground().setAlpha(255);
                }
                break;
        }
        return false;
    }

    private class MyPagerAdapter extends android.support.v4.view.PagerAdapter {

        List<ViewPage> List = new ArrayList<>();

        public MyPagerAdapter() {
            v1 = new ViewPage("精灵球", R.drawable.bag_decorate, R.drawable.init_ball, 1);
            v2 = new ViewPage("道具", R.drawable.bag_decorate1, R.drawable.init_ball2, 2);
            v3 = new ViewPage("进化石", R.drawable.bag_decorate2, R.drawable.init_ball3, 3);
            v4 = new ViewPage("秘籍", R.drawable.bag_decorate3, R.drawable.init_ball4, 4);
            v5 = new ViewPage("场景", R.drawable.bag_decorate4, R.drawable.init_ball5, 5);
            List.add(v1);
            List.add(v2);
            List.add(v3);
            List.add(v4);
            List.add(v5);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % List.size();
            View view = LayoutInflater.from(SBuy.this).inflate(R.layout.bag_item, null);
            TextView textView = (TextView) view.findViewById(R.id.item);
            textView.setText(List.get(position).Name);
            recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
            switch (List.get(position).Number) {
                case 1:
                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(SBuy.this);
                    recyclerView.setLayoutManager(layoutManager1);
                    ItemAdapter adapter1 = new ItemAdapter(items1);
                    recyclerView.setAdapter(adapter1);
                    break;
                case 2:
                    LinearLayoutManager layoutManager2 = new LinearLayoutManager(SBuy.this);
                    recyclerView.setLayoutManager(layoutManager2);
                    ItemAdapter adapter2 = new ItemAdapter(items2);
                    recyclerView.setAdapter(adapter2);
                    break;
                case 3:
                    LinearLayoutManager layoutManager3 = new LinearLayoutManager(SBuy.this);
                    recyclerView.setLayoutManager(layoutManager3);
                    ItemAdapter adapter3 = new ItemAdapter(items3);
                    recyclerView.setAdapter(adapter3);
                    break;
                case 4:
                    LinearLayoutManager layoutManager4 = new LinearLayoutManager(SBuy.this);
                    recyclerView.setLayoutManager(layoutManager4);
                    ItemAdapter adapter4 = new ItemAdapter(items4);
                    recyclerView.setAdapter(adapter4);
                    break;
                case 5:
                    LinearLayoutManager layoutManager5 = new LinearLayoutManager(SBuy.this);
                    recyclerView.setLayoutManager(layoutManager5);
                    ItemAdapter adapter5 = new ItemAdapter(items5);
                    recyclerView.setAdapter(adapter5);
                    break;
            }
            container.addView(view);
            return view;
        }

    }

    class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

        private List<Item> List;

        public ItemAdapter(List<Item> List) {
            this.List = List;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bag_item_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);

            holder.ItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Support.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    TextView Name = (TextView) v.findViewById(R.id.name);
                    ChoseName = Name.getText().toString();
                    if (FirstTouch) {
                        Buy.setVisibility(View.VISIBLE);
                        Buy.startAnimation(float2);
                        Cancel.setVisibility(View.VISIBLE);
                        Cancel.startAnimation(float3);
                        FirstTouch = false;
                    }
                    switch (PagesCount) {
                        case 1:
                            AVQuery<AVObject> query1 = new AVQuery<>("PokeMonBall");
                            query1.whereEqualTo("Name", ChoseName);
                            query1.getFirstInBackground(new GetCallback<AVObject>() {
                                @Override
                                public void done(AVObject avObject, AVException e) {
                                    Item_Name.setText(ChoseName);
                                    Price = avObject.getInt("Price");
                                    NumberInDex = avObject.getInt("Number");
                                    AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("_File");
                                    avObjectAVQuery.whereEqualTo("name", avObject.getString("ImageName") + ".png");
                                    avObjectAVQuery.getFirstInBackground(new GetCallback<AVObject>() {
                                        @Override
                                        public void done(AVObject avObject, AVException e) {
                                            AVFile avFile = new AVFile("Item.png", avObject.getString("url"), new HashMap<String, Object>());
                                            avFile.getDataInBackground(new GetDataCallback() {
                                                                           @Override
                                                                           public void done(byte[] bytes, AVException e) {
                                                                               Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                                               Item_Pic.setImageBitmap(bitmap);
                                                                           }
                                                                       }, new ProgressCallback() {
                                                                           @Override
                                                                           public void done(Integer integer) {
                                                                               if (integer == 100) {
                                                                                   Support.setVisibility(View.GONE);
                                                                                   progressBar.setVisibility(View.GONE);
                                                                               }
                                                                           }
                                                                       }
                                            );
                                        }
                                    });
                                }
                            });
                            break;
                        case 2:
                            AVQuery<AVObject> query2 = new AVQuery<>("PokeMonTool");
                            query2.whereEqualTo("Name", ChoseName);
                            query2.getFirstInBackground(new GetCallback<AVObject>() {
                                @Override
                                public void done(AVObject avObject, AVException e) {
                                    Item_Name.setText(ChoseName);
                                    Price = avObject.getInt("Price");
                                    NumberInDex = avObject.getInt("Number");
                                    AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("_File");
                                    avObjectAVQuery.whereEqualTo("name", avObject.getString("ImageName") + ".png");
                                    avObjectAVQuery.getFirstInBackground(new GetCallback<AVObject>() {
                                        @Override
                                        public void done(AVObject avObject, AVException e) {
                                            AVFile avFile = new AVFile("Item.png", avObject.getString("url"), new HashMap<String, Object>());
                                            avFile.getDataInBackground(new GetDataCallback() {
                                                @Override
                                                public void done(byte[] bytes, AVException e) {
                                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    Item_Pic.setImageBitmap(bitmap);
                                                }
                                            }, new ProgressCallback() {
                                                @Override
                                                public void done(Integer integer) {
                                                    if (integer == 100) {
                                                        Support.setVisibility(View.GONE);
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                            break;
                        case 3:
                            AVQuery<AVObject> query3 = new AVQuery<>("PokeMonStone");
                            query3.whereEqualTo("Name", ChoseName);
                            query3.getFirstInBackground(new GetCallback<AVObject>() {
                                @Override
                                public void done(AVObject avObject, AVException e) {
                                    Item_Name.setText(ChoseName);
                                    Price = avObject.getInt("Price");
                                    NumberInDex = avObject.getInt("Number");
                                    AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("_File");
                                    avObjectAVQuery.whereEqualTo("name", avObject.getString("ImageName") + ".png");
                                    avObjectAVQuery.getFirstInBackground(new GetCallback<AVObject>() {
                                        @Override
                                        public void done(AVObject avObject, AVException e) {
                                            AVFile avFile = new AVFile("Item.png", avObject.getString("url"), new HashMap<String, Object>());
                                            avFile.getDataInBackground(new GetDataCallback() {
                                                @Override
                                                public void done(byte[] bytes, AVException e) {
                                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    Item_Pic.setImageBitmap(bitmap);
                                                }
                                            }, new ProgressCallback() {
                                                @Override
                                                public void done(Integer integer) {
                                                    if (integer == 100) {
                                                        Support.setVisibility(View.GONE);
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                            break;
                        case 4:
                            AVQuery<AVObject> query4 = new AVQuery<>("PokeMonBook");
                            query4.whereEqualTo("Name", ChoseName);
                            query4.getFirstInBackground(new GetCallback<AVObject>() {
                                @Override
                                public void done(AVObject avObject, AVException e) {
                                    Item_Name.setText(ChoseName);
                                    Price = avObject.getInt("Price");
                                    NumberInDex = avObject.getInt("Number");
                                    AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("_File");
                                    avObjectAVQuery.whereEqualTo("name", avObject.getString("ImageName") + ".png");
                                    avObjectAVQuery.getFirstInBackground(new GetCallback<AVObject>() {
                                        @Override
                                        public void done(AVObject avObject, AVException e) {
                                            AVFile avFile = new AVFile("Item.png", avObject.getString("url"), new HashMap<String, Object>());
                                            avFile.getDataInBackground(new GetDataCallback() {
                                                @Override
                                                public void done(byte[] bytes, AVException e) {
                                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    Item_Pic.setImageBitmap(bitmap);
                                                }
                                            }, new ProgressCallback() {
                                                @Override
                                                public void done(Integer integer) {
                                                    if (integer == 100) {
                                                        Support.setVisibility(View.GONE);
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                            break;
                        case 5:
                            AVQuery<AVObject> query5 = new AVQuery<>("Scene");
                            query5.whereEqualTo("Number", Integer.valueOf(ChoseName));
                            query5.getFirstInBackground(new GetCallback<AVObject>() {
                                @Override
                                public void done(AVObject avObject, AVException e) {
                                    Price = avObject.getInt("Price");
                                    NumberInDex = avObject.getInt("Number");
                                    AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("_File");
                                    avObjectAVQuery.whereEqualTo("name", avObject.getString("ImageName") + ".png");
                                    avObjectAVQuery.getFirstInBackground(new GetCallback<AVObject>() {
                                        @Override
                                        public void done(AVObject avObject, AVException e) {
                                            AVFile avFile = new AVFile("Item.png", avObject.getString("url"), new HashMap<String, Object>());
                                            avFile.getDataInBackground(new GetDataCallback() {
                                                @Override
                                                public void done(byte[] bytes, AVException e) {
                                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    Pet_Init.setImageBitmap(bitmap);
                                                }
                                            }, new ProgressCallback() {
                                                @Override
                                                public void done(Integer integer) {
                                                    if (integer == 100) {
                                                        Support.setVisibility(View.GONE);
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                            break;
                    }
                    Log.i("Price", String.valueOf(Price));
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Item item = List.get(position);
            holder.Name.setText(item.Name);
            holder.Number.setText(String.valueOf(item.Price));
            holder.Coin.setVisibility(View.VISIBLE);
        }

        @Override
        public int getItemCount() {
            return List.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView Name;
            TextView Number;
            ImageView Coin;
            View ItemView;

            public ViewHolder(View view) {
                super(view);
                Name = (TextView) view.findViewById(R.id.name);
                Number = (TextView) view.findViewById(R.id.number);
                Coin = (ImageView) view.findViewById(R.id.coin);
                ItemView = view;
            }
        }
    }

    class Item {
        String Name;
        String ImageName;
        int Type;
        int Price;

        public Item(String Name, String ImageName, int Type, int Price) {
            this.Name = Name;
            this.ImageName = ImageName;
            this.Type = Type;
            this.Price = Price;
        }
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

    public void Dialog_Show() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("错误")
                .setMessage("请输入正确的数字。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
