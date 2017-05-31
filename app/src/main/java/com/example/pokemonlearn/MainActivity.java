package com.example.pokemonlearn;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.GetDataCallback;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.animation.ObjectAnimator.ofFloat;

/**
 * Created by Gama on 8/3/17 (Test Version) (Happy Birthday, Cloud!).
 * Created by Gama on 9/3/17 (First Version) (Happy Birthday, TaeYeon!).
 * Noted by Gama on 21/3/17 (Happy Birthday, MCX!).
 * Created by Gama on 7/4/17 (Second Version).
 * Noted by Gama on 11/5/17 (Love Seat With Me).
 * Noted by Gama on 15/5/17 (Ashamed Of Myself).
 * Noted by Gama on 18/5/17 (Result: 1:C-Bag 2:C-PokeMonBall 3:P-Learn 4:P-Evolve)
 * Noted by Gama on 25/5/17 (<<Attention>>)
 */

public class MainActivity extends AppCompatActivity implements BaiduMap.OnMarkerClickListener, View.OnClickListener, View.OnTouchListener, BaiduMap.OnMapClickListener {
    private String User;

    public LocationClient mLocClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private MapView mMapView;
    private BaiduMap mBaiduMap = null;
    private boolean isFirstLoc = true;
    private MyLocationConfiguration myLocationConfiguration;

    private Animation anim0;
    private Animation anim1;

    private PercentRelativeLayout mapMode;
    private Button SATELLITE;
    private Button FOLLOWING;
    private Button COMPASS;
    private int Open_Close = 0;
    private Button mapModeChose;

    private double OverlayPosition[][];
    private String[] Url;
    private String[] Name;
    private Bitmap[] bitmaps;

    private BDLocation myLocation;
    private Marker markerA;
    private Marker markerB;
    private Marker markerC;
    private Marker markerD;
    private Marker markerE;

    private Button littleMap;
    private Button setting;
    private Button show;
    private Button Menu;
    private PercentRelativeLayout Show_Menu;
    private ImageView Show_Background;
    private Animation animation1;
    private Animation animation2;
    private Animation Show_Menu_Show;
    private int ShowCount;

    private PercentRelativeLayout littleMapLayout;
    private Button finish;

    private PercentRelativeLayout MenuLayout;
    private Button myPet;
    private Button PokeDex;
    private Button myBag;
    private Button battle;
    private Button shop;
    private Button egg;
    private Button compete;
    private Button gym;
    private Button back;
    private int MenuCount;

    private ImageView transfer1;
    private ImageView transfer2;
    private Animation trans_out1;
    private Animation trans_out2;
    private Animation transit;

    private ImageView warning;
    private Animation Warning;
    private Animation Warning2;
    private int WarningTimes;
    private ImageView Capture_trans;
    private List<ImageView> trans_left;
    private List<ImageView> trans_right;
    private Animation cap_left;
    private Animation cap_right;

    private ImageView temp;

    private Button Database;

    private TextView MyCoins;

    private int j = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        Url = new String[5];
        Name = new String[5];

        Intent intent = getIntent();
        Url[0] = intent.getStringExtra("Url1");
        Url[1] = intent.getStringExtra("Url2");
        Url[2] = intent.getStringExtra("Url3");
        Url[3] = intent.getStringExtra("Url4");
        Url[4] = intent.getStringExtra("Url5");
        Name[0] = intent.getStringExtra("Name1");
        Name[1] = intent.getStringExtra("Name2");
        Name[2] = intent.getStringExtra("Name3");
        Name[3] = intent.getStringExtra("Name4");
        Name[4] = intent.getStringExtra("Name5");
        User = intent.getStringExtra("User");
        Log.i("User", User);


        Intent intent1 = new Intent(MainActivity.this, MusicServer.class);
        startService(intent1);
        transfer1 = (ImageView) findViewById(R.id.transfer1);
        transfer2 = (ImageView) findViewById(R.id.transfer2);
        trans_out1 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.trans_out_up);
        trans_out2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.trans_out_down);
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

        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setBuildingsEnabled(true);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMaxAndMinZoomLevel(20f, 19f);

        //mBaiduMap.getUiSettings().setScrollGesturesEnabled(false);

        myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,
                true, null);
        mBaiduMap.setMyLocationConfigeration(myLocationConfiguration);
        mBaiduMap.setOnMapClickListener(this);
        mBaiduMap.setOnMarkerClickListener(this);

        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        //option.setScanSpan(2000);

        mLocClient.setLocOption(option);
        mLocClient.start();

        initOverlay();

        anim0 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.touch2);
        anim1 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.touch1);

        littleMap = (Button) findViewById(R.id.littlemap);
        littleMap.setOnClickListener(this);
        littleMap.setOnTouchListener(this);

        show = (Button) findViewById(R.id.show);
        show.setOnClickListener(this);
        show.setOnTouchListener(this);

        setting = (Button) findViewById(R.id.setting);
        setting.setOnClickListener(this);
        setting.setOnTouchListener(this);

        Show_Menu = (PercentRelativeLayout) findViewById(R.id.show_menu);
        Show_Background = (ImageView) findViewById(R.id.show_background);
        Show_Background.getBackground().setAlpha(125);
        ShowCount = 0;

        Menu = (Button) findViewById(R.id.menu);
        Menu.setOnTouchListener(this);
        Menu.setOnClickListener(this);
        animation1 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.up);
        animation2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.down);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Menu.startAnimation(animation2);
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
                Menu.startAnimation(animation1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        Menu.startAnimation(animation1);

        littleMapLayout = (PercentRelativeLayout) findViewById(R.id.littlemapLayout);
        littleMapLayout.setVisibility(View.GONE);
        littleMapLayout.getBackground().setAlpha(125);

        finish = (Button) findViewById(R.id.finish);
        finish.setOnClickListener(this);
        finish.setOnTouchListener(this);

        MenuLayout = (PercentRelativeLayout) findViewById(R.id.menuFrame);
        MenuLayout.setVisibility(View.GONE);
        MenuLayout.getBackground().setAlpha(125);

        myPet = (Button) findViewById(R.id.myPet);
        myPet.setOnClickListener(this);
        myPet.setOnTouchListener(this);

        PokeDex = (Button) findViewById(R.id.pokeDex);
        PokeDex.setOnClickListener(this);
        PokeDex.setOnTouchListener(this);

        myBag = (Button) findViewById(R.id.myBag);
        myBag.setOnClickListener(this);
        myBag.setOnTouchListener(this);

        battle = (Button) findViewById(R.id.battle);
        battle.setOnClickListener(this);
        battle.setOnTouchListener(this);

        shop = (Button) findViewById(R.id.shop);
        shop.setOnClickListener(this);
        shop.setOnTouchListener(this);

        egg = (Button) findViewById(R.id.egg);
        egg.setOnClickListener(this);
        egg.setOnTouchListener(this);

        compete = (Button) findViewById(R.id.compete);
        compete.setOnClickListener(this);
        compete.setOnTouchListener(this);

        gym = (Button) findViewById(R.id.gym);
        gym.setOnClickListener(this);
        gym.setOnTouchListener(this);

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        back.setOnTouchListener(this);

        MenuCount = 0;

        Button pretend = (Button) findViewById(R.id.pretend);
        pretend.setOnClickListener(this);
        Button pe = (Button) findViewById(R.id.pe);
        pe.setOnClickListener(this);

        warning = (ImageView) findViewById(R.id.warning);
        warning.getBackground().setAlpha(200);
        warning.setVisibility(View.GONE);

        trans_left = new ArrayList<>();
        trans_right = new ArrayList<>();
        Capture_trans = (ImageView) findViewById(R.id.fight_trans_01);
        Capture_trans.setVisibility(View.GONE);
        trans_left.add(Capture_trans);
        Capture_trans = (ImageView) findViewById(R.id.fight_trans_02);
        Capture_trans.setVisibility(View.GONE);
        trans_right.add(Capture_trans);
        Capture_trans = (ImageView) findViewById(R.id.fight_trans_03);
        Capture_trans.setVisibility(View.GONE);
        trans_left.add(Capture_trans);
        Capture_trans = (ImageView) findViewById(R.id.fight_trans_04);
        Capture_trans.setVisibility(View.GONE);
        trans_right.add(Capture_trans);
        Capture_trans = (ImageView) findViewById(R.id.fight_trans_05);
        Capture_trans.setVisibility(View.GONE);
        trans_left.add(Capture_trans);
        Capture_trans = (ImageView) findViewById(R.id.fight_trans_06);
        Capture_trans.setVisibility(View.GONE);
        trans_right.add(Capture_trans);
        Capture_trans = (ImageView) findViewById(R.id.fight_trans_07);
        Capture_trans.setVisibility(View.GONE);
        trans_left.add(Capture_trans);
        Capture_trans = (ImageView) findViewById(R.id.fight_trans_08);
        Capture_trans.setVisibility(View.GONE);
        trans_right.add(Capture_trans);

        SATELLITE = (Button) findViewById(R.id.satellite);
        SATELLITE.setOnClickListener(this);
        SATELLITE.setOnTouchListener(this);
        FOLLOWING = (Button) findViewById(R.id.following);
        FOLLOWING.setOnClickListener(this);
        FOLLOWING.setOnTouchListener(this);
        COMPASS = (Button) findViewById(R.id.compass);
        COMPASS.setOnClickListener(this);
        COMPASS.setOnTouchListener(this);

        mapMode = (PercentRelativeLayout) findViewById(R.id.mapMode_background);
        mapMode.getBackground().setAlpha(140);
        mapModeChose = (Button) findViewById(R.id.map_mode);

        ValueAnimator animator = ValueAnimator.ofFloat(0, -380);
        animator.setTarget(mapMode);
        animator.setDuration(500).start();
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mapMode.setTranslationX((Float) animation.getAnimatedValue());
            }
        });
        mapModeChose.setOnClickListener(this);

        Database = (Button) findViewById(R.id.dataBase);
        Database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DatabaseOperate.class);
                startActivity(intent);
            }
        });
        MyCoins = (TextView) findViewById(R.id.myCoin);
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        int Number = preferences.getInt("Coins", 0);
        String Coins = "      " + String.valueOf(Number) + "  ";
        MyCoins.setText(Coins);

    }


    public void initOverlay() {
        OverlayPosition = new double[5][2];
        OverlayPosition = getRandomPosition(5);

        bitmaps = new Bitmap[5];

        for (int i = 0; i < 5; i++) {
            AVFile avFile = new AVFile("PokeMonPic.png", Url[i], new HashMap<String, Object>());
            avFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, AVException e) {
                    bitmaps[j++] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    Log.i("Test", String.valueOf(j));
                    if (j == 5) {
                        initOverlay2();
                    }
                }
            });
        }

    }

    public void initOverlay2() {
        int i = 0;
        BitmapDescriptor BDA = BitmapDescriptorFactory.fromBitmap(bitmaps[i++]);
        BitmapDescriptor BDB = BitmapDescriptorFactory.fromBitmap(bitmaps[i++]);
        BitmapDescriptor BDC = BitmapDescriptorFactory.fromBitmap(bitmaps[i++]);
        BitmapDescriptor BDD = BitmapDescriptorFactory.fromBitmap(bitmaps[i++]);
        BitmapDescriptor BDE = BitmapDescriptorFactory.fromBitmap(bitmaps[i]);

        LatLng llA = new LatLng(OverlayPosition[0][0], OverlayPosition[0][1]);
        LatLng llB = new LatLng(OverlayPosition[1][0], OverlayPosition[1][1]);
        LatLng llC = new LatLng(OverlayPosition[2][0], OverlayPosition[2][1]);
        LatLng llD = new LatLng(OverlayPosition[3][0], OverlayPosition[3][1]);
        LatLng llE = new LatLng(OverlayPosition[4][0], OverlayPosition[4][1]);

        MarkerOptions ooA = new MarkerOptions().position(llA).icon(BDA)
                .zIndex(1).draggable(false);
        MarkerOptions ooB = new MarkerOptions().position(llB).icon(BDB)
                .zIndex(1).draggable(false);
        MarkerOptions ooC = new MarkerOptions().position(llC).icon(BDC)
                .zIndex(1).draggable(false);
        MarkerOptions ooD = new MarkerOptions().position(llD).icon(BDD)
                .zIndex(1).draggable(false);
        MarkerOptions ooE = new MarkerOptions().position(llE).icon(BDE)
                .zIndex(1).draggable(false);

        ooA.animateType(MarkerOptions.MarkerAnimateType.grow);
        ooB.animateType(MarkerOptions.MarkerAnimateType.grow);
        ooC.animateType(MarkerOptions.MarkerAnimateType.grow);
        ooD.animateType(MarkerOptions.MarkerAnimateType.grow);
        ooE.animateType(MarkerOptions.MarkerAnimateType.grow);

        markerA = (Marker) mBaiduMap.addOverlay(ooA);
        markerB = (Marker) mBaiduMap.addOverlay(ooB);
        markerC = (Marker) mBaiduMap.addOverlay(ooC);
        markerD = (Marker) mBaiduMap.addOverlay(ooD);
        markerE = (Marker) mBaiduMap.addOverlay(ooE);
    }

    private void navigateTo(BDLocation location) {
        if (isFirstLoc) {
            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.zoomTo(18f);
            mBaiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.newLatLng(point);
            mBaiduMap.animateMapStatus(update);
            isFirstLoc = false;
//30.318489 120.385657
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng myPosition = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        if (marker == markerA) {
            if (DistanceUtil.getDistance(myPosition, markerA.getPosition()) < 25) {
                WarningTimes = 0;
                warning.setVisibility(View.VISIBLE);
                Warning = AnimationUtils.loadAnimation(MainActivity.this, R.anim.warning);
                Warning2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.warning2);
                warning.startAnimation(Warning);
                Warning.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        warning.startAnimation(Warning2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                Warning2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (WarningTimes < 2) {
                            WarningTimes++;
                            warning.startAnimation(Warning);
                        } else {
                            warning.setVisibility(View.GONE);
                            cap_left = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cap_trans_left);
                            cap_right = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cap_trans_right);
                            for (int i = 0; i < 4; i++) {
                                temp = trans_left.get(i);
                                temp.setVisibility(View.VISIBLE);
                                temp.startAnimation(cap_left);
                                temp = trans_right.get(i);
                                temp.setVisibility(View.VISIBLE);
                                temp.startAnimation(cap_right);
                            }
                            cap_left.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    Intent intent3 = new Intent(MainActivity.this, Capture.class);
                                    intent3.putExtra("Name", Name[0]);
                                    intent3.putExtra("User", User);
                                    startActivity(intent3);
                                    overridePendingTransition(0, 0);
                                    transit = AnimationUtils.loadAnimation(MainActivity.this, R.anim.transit);
                                    for (int i = 0; i < 4; i++) {
                                        temp = trans_left.get(i);
                                        temp.setVisibility(View.GONE);
                                        temp.startAnimation(transit);
                                        temp = trans_right.get(i);
                                        temp.setVisibility(View.GONE);
                                        temp.startAnimation(transit);
                                    }
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                //Toast.makeText(this, String.valueOf(distanceUtil.getDistance(myPosition, markerA.getPosition())), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, String.valueOf(DistanceUtil.getDistance(myPosition, markerA.getPosition())), Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Getting a little closer.", Toast.LENGTH_SHORT).show();
            }
        } else if (marker == markerB) {
            if (DistanceUtil.getDistance(myPosition, markerB.getPosition()) < 25) {
                WarningTimes = 0;
                warning.setVisibility(View.VISIBLE);
                Warning = AnimationUtils.loadAnimation(MainActivity.this, R.anim.warning);
                Warning2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.warning2);
                warning.startAnimation(Warning);
                Warning.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        warning.startAnimation(Warning2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                Warning2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (WarningTimes < 2) {
                            WarningTimes++;
                            warning.startAnimation(Warning);
                        } else {
                            warning.setVisibility(View.GONE);
                            cap_left = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cap_trans_left);
                            cap_right = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cap_trans_right);
                            for (int i = 0; i < 4; i++) {
                                temp = trans_left.get(i);
                                temp.setVisibility(View.VISIBLE);
                                temp.startAnimation(cap_left);
                                temp = trans_right.get(i);
                                temp.setVisibility(View.VISIBLE);
                                temp.startAnimation(cap_right);
                            }
                            cap_left.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    Intent intent3 = new Intent(MainActivity.this, Capture.class);
                                    intent3.putExtra("Name", Name[1]);
                                    intent3.putExtra("User", User);
                                    startActivity(intent3);
                                    overridePendingTransition(0, 0);
                                    transit = AnimationUtils.loadAnimation(MainActivity.this, R.anim.transit);
                                    for (int i = 0; i < 4; i++) {
                                        temp = trans_left.get(i);
                                        temp.setVisibility(View.GONE);
                                        temp.startAnimation(transit);
                                        temp = trans_right.get(i);
                                        temp.setVisibility(View.GONE);
                                        temp.startAnimation(transit);
                                    }
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                Toast.makeText(this, String.valueOf(DistanceUtil.getDistance(myPosition, markerB.getPosition())), Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Getting a little closer.", Toast.LENGTH_SHORT).show();
            }
        } else if (marker == markerC) {
            if (DistanceUtil.getDistance(myPosition, markerC.getPosition()) < 25) {
                WarningTimes = 0;
                warning.setVisibility(View.VISIBLE);
                Warning = AnimationUtils.loadAnimation(MainActivity.this, R.anim.warning);
                Warning2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.warning2);
                warning.startAnimation(Warning);
                Warning.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        warning.startAnimation(Warning2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                Warning2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (WarningTimes < 2) {
                            WarningTimes++;
                            warning.startAnimation(Warning);
                        } else {
                            warning.setVisibility(View.GONE);
                            cap_left = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cap_trans_left);
                            cap_right = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cap_trans_right);
                            for (int i = 0; i < 4; i++) {
                                temp = trans_left.get(i);
                                temp.setVisibility(View.VISIBLE);
                                temp.startAnimation(cap_left);
                                temp = trans_right.get(i);
                                temp.setVisibility(View.VISIBLE);
                                temp.startAnimation(cap_right);
                            }
                            cap_left.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    Intent intent3 = new Intent(MainActivity.this, Capture.class);
                                    intent3.putExtra("Name", Name[2]);
                                    intent3.putExtra("User", User);
                                    startActivity(intent3);
                                    overridePendingTransition(0, 0);
                                    transit = AnimationUtils.loadAnimation(MainActivity.this, R.anim.transit);
                                    for (int i = 0; i < 4; i++) {
                                        temp = trans_left.get(i);
                                        temp.setVisibility(View.GONE);
                                        temp.startAnimation(transit);
                                        temp = trans_right.get(i);
                                        temp.setVisibility(View.GONE);
                                        temp.startAnimation(transit);
                                    }
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                Toast.makeText(this, String.valueOf(DistanceUtil.getDistance(myPosition, markerC.getPosition())), Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Getting a little closer.", Toast.LENGTH_SHORT).show();
            }
        } else if (marker == markerD) {
            if (DistanceUtil.getDistance(myPosition, markerD.getPosition()) < 25) {
                WarningTimes = 0;
                warning.setVisibility(View.VISIBLE);
                Warning = AnimationUtils.loadAnimation(MainActivity.this, R.anim.warning);
                Warning2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.warning2);
                warning.startAnimation(Warning);
                Warning.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        warning.startAnimation(Warning2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                Warning2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (WarningTimes < 2) {
                            WarningTimes++;
                            warning.startAnimation(Warning);
                        } else {
                            warning.setVisibility(View.GONE);
                            cap_left = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cap_trans_left);
                            cap_right = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cap_trans_right);
                            for (int i = 0; i < 4; i++) {
                                temp = trans_left.get(i);
                                temp.setVisibility(View.VISIBLE);
                                temp.startAnimation(cap_left);
                                temp = trans_right.get(i);
                                temp.setVisibility(View.VISIBLE);
                                temp.startAnimation(cap_right);
                            }
                            cap_left.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    Intent intent3 = new Intent(MainActivity.this, Capture.class);
                                    intent3.putExtra("Name", Name[3]);
                                    intent3.putExtra("User", User);
                                    startActivity(intent3);
                                    overridePendingTransition(0, 0);
                                    transit = AnimationUtils.loadAnimation(MainActivity.this, R.anim.transit);
                                    for (int i = 0; i < 4; i++) {
                                        temp = trans_left.get(i);
                                        temp.setVisibility(View.GONE);
                                        temp.startAnimation(transit);
                                        temp = trans_right.get(i);
                                        temp.setVisibility(View.GONE);
                                        temp.startAnimation(transit);
                                    }
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                Toast.makeText(this, String.valueOf(DistanceUtil.getDistance(myPosition, markerD.getPosition())), Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Getting a little closer.", Toast.LENGTH_SHORT).show();
            }
        } else if (marker == markerE) {
            if (DistanceUtil.getDistance(myPosition, markerE.getPosition()) < 25) {
                WarningTimes = 0;
                warning.setVisibility(View.VISIBLE);
                Warning = AnimationUtils.loadAnimation(MainActivity.this, R.anim.warning);
                Warning2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.warning2);
                warning.startAnimation(Warning);
                Warning.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        warning.startAnimation(Warning2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                Warning2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (WarningTimes < 2) {
                            WarningTimes++;
                            warning.startAnimation(Warning);
                        } else {
                            warning.setVisibility(View.GONE);
                            cap_left = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cap_trans_left);
                            cap_right = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cap_trans_right);
                            for (int i = 0; i < 4; i++) {
                                temp = trans_left.get(i);
                                temp.setVisibility(View.VISIBLE);
                                temp.startAnimation(cap_left);
                                temp = trans_right.get(i);
                                temp.setVisibility(View.VISIBLE);
                                temp.startAnimation(cap_right);
                            }
                            cap_left.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    Intent intent3 = new Intent(MainActivity.this, Capture.class);
                                    intent3.putExtra("Name", Name[4]);
                                    intent3.putExtra("User", User);
                                    startActivity(intent3);
                                    overridePendingTransition(0, 0);
                                    transit = AnimationUtils.loadAnimation(MainActivity.this, R.anim.transit);
                                    for (int i = 0; i < 4; i++) {
                                        temp = trans_left.get(i);
                                        temp.setVisibility(View.GONE);
                                        temp.startAnimation(transit);
                                        temp = trans_right.get(i);
                                        temp.setVisibility(View.GONE);
                                        temp.startAnimation(transit);
                                    }
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                Toast.makeText(this, String.valueOf(DistanceUtil.getDistance(myPosition, markerE.getPosition())), Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Getting a little closer.", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, Setting.class);
                startActivity(intent);
                break;
            case R.id.littlemap:
                /*MenuLayout.setVisibility(View.GONE);
                littleMapLayout.setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim);
                littleMapLayout.startAnimation(anim);*/
                break;
            case R.id.show:
                if (ShowCount == 0) {
                    Show_Menu.setVisibility(View.VISIBLE);
                    Show_Menu_Show = AnimationUtils.loadAnimation(MainActivity.this, R.anim.show_menu);
                    Show_Menu.startAnimation(Show_Menu_Show);
                    ShowCount = 1;
                } else if (ShowCount == 1) {
                    Show_Menu.setVisibility(View.GONE);
                    ShowCount = 0;
                }
                break;
            case R.id.finish:
                littleMapLayout.setVisibility(View.GONE);
                MenuLayout.setVisibility(View.GONE);
                break;
            case R.id.menu:
                if (MenuCount == 0) {
                    Menu.startAnimation(animation1);
                    littleMapLayout.setVisibility(View.GONE);
                    MenuLayout.setVisibility(View.VISIBLE);
                    Animation anim1 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim);
                    MenuLayout.startAnimation(anim1);
                    MenuCount = 1;
                } else if (MenuCount == 1) {
                    MenuLayout.setVisibility(View.GONE);
                    MenuCount = 0;
                }
                break;
            case R.id.myBag:
                Intent intent0 = new Intent(MainActivity.this, Bag.class);
                intent0.putExtra("User", User);
                startActivity(intent0);
                overridePendingTransition(0, 0);
                break;
            case R.id.pokeDex:
                Intent intent1 = new Intent(MainActivity.this, PokeDex.class);
                startActivity(intent1);
                overridePendingTransition(0, 0);
                break;
            case R.id.myPet:
                Intent intent2 = new Intent(MainActivity.this, Pet.class);
                intent2.putExtra("User", User);
                startActivity(intent2);
                overridePendingTransition(0, 0);
                break;
            case R.id.back:
                MenuLayout.setVisibility(View.GONE);
                MenuCount = 0;
                break;
            case R.id.pretend:
                Intent intent5 = new Intent(MainActivity.this, MusicServer.class);
                stopService(intent5);
                Intent intent6 = new Intent(MainActivity.this, CaptureMusicServer.class);
                startService(intent6);
                WarningTimes = 0;
                warning.setVisibility(View.VISIBLE);
                Warning = AnimationUtils.loadAnimation(MainActivity.this, R.anim.warning);
                Warning2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.warning2);
                warning.startAnimation(Warning);
                Warning.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        warning.startAnimation(Warning2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                Warning2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (WarningTimes < 2) {
                            WarningTimes++;
                            warning.startAnimation(Warning);
                        } else {
                            warning.setVisibility(View.GONE);
                            cap_left = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cap_trans_left);
                            cap_right = AnimationUtils.loadAnimation(MainActivity.this, R.anim.cap_trans_right);
                            for (int i = 0; i < 4; i++) {
                                temp = trans_left.get(i);
                                temp.setVisibility(View.VISIBLE);
                                temp.startAnimation(cap_left);
                                temp = trans_right.get(i);
                                temp.setVisibility(View.VISIBLE);
                                temp.startAnimation(cap_right);
                            }
                            cap_left.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    Intent intent3 = new Intent(MainActivity.this, Capture.class);
                                    intent3.putExtra("Name", "伊布");
                                    intent3.putExtra("User", User);
                                    startActivity(intent3);
                                    overridePendingTransition(0, 0);
                                    transit = AnimationUtils.loadAnimation(MainActivity.this, R.anim.transit);
                                    for (int i = 0; i < 4; i++) {
                                        temp = trans_left.get(i);
                                        temp.setVisibility(View.GONE);
                                        temp.startAnimation(transit);
                                        temp = trans_right.get(i);
                                        temp.setVisibility(View.GONE);
                                        temp.startAnimation(transit);
                                    }
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;
            case R.id.map_mode:
                if (Open_Close == 0) {
                    ValueAnimator animator = ValueAnimator.ofFloat(-380, 0);
                    animator.setTarget(mapMode);
                    animator.setDuration(500).start();
                    animator.setInterpolator(new LinearInterpolator());
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mapMode.setTranslationX((Float) animation.getAnimatedValue());
                        }
                    });
                    Open_Close = 1;
                } else {
                    ValueAnimator animator = ValueAnimator.ofFloat(0, -380);
                    animator.setTarget(mapMode);
                    animator.setDuration(500).start();
                    animator.setInterpolator(new LinearInterpolator());
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mapMode.setTranslationX((Float) animation.getAnimatedValue());
                        }
                    });
                    Open_Close = 0;
                }
                break;
            case R.id.satellite:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.compass:
                mBaiduMap
                        .setMyLocationConfigeration(new MyLocationConfiguration(
                                MyLocationConfiguration.LocationMode.COMPASS, true, null));
                break;
            case R.id.following:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                mBaiduMap
                        .setMyLocationConfigeration(new MyLocationConfiguration(
                                MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
                break;
            case R.id.shop:
                Intent intent3 = new Intent(MainActivity.this, Shop.class);
                intent3.putExtra("User", User);
                startActivity(intent3);
                overridePendingTransition(0, 0);
                break;
            case R.id.pe:
                Intent intent4 = new Intent(MainActivity.this, Evolve.class);
                intent4.putExtra("PMName", "伊布");
                intent4.putExtra("PMStone", "火之石");
                intent4.putExtra("S-PMName", "火精灵");
                intent4.putExtra("User", User);
                startActivity(intent4);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.finish:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.finish2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.finish);
                }
                break;
            case R.id.myPet:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.pet2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.pet);
                }
                break;
            case R.id.pokeDex:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.pokedex2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.pokedex);
                }
                break;
            case R.id.myBag:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.mybag2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.mybag);
                }
                break;
            case R.id.battle:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.battle2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.battle);
                }
                break;
            case R.id.shop:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.shop2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    v.setBackgroundResource(R.drawable.shop);
                }
                break;
            case R.id.egg:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.egg2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.egg);
                }
                break;
            case R.id.compete:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.compete2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.compete);
                }
                break;
            case R.id.gym:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.gym2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.gym);
                }
                break;
            case R.id.back:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.back2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.back);
                }
                break;
            case R.id.littlemap:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.littlemap2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.littlemap);
                }
                break;
            case R.id.show:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(show, "TranslationY", 0, 30);
                    objectAnimator.setDuration(100);
                    objectAnimator.start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(show, "TranslationY", 30, 0);
                    objectAnimator.setDuration(100);
                    objectAnimator.start();
                }
                break;
            case R.id.setting:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.setting2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.setting);
                }
                break;
            case R.id.satellite:
                if (event.getAction() == MotionEvent.ACTION_DOWN && Open_Close == 1) {
                    v.startAnimation(anim1);
                } else {
                    if (event.getAction() == MotionEvent.ACTION_UP && Open_Close == 1) {
                        v.startAnimation(anim0);
                    }
                }
                break;
            case R.id.compass:
                if (event.getAction() == MotionEvent.ACTION_DOWN && Open_Close == 1) {
                    v.startAnimation(anim1);
                } else {
                    if (event.getAction() == MotionEvent.ACTION_UP && Open_Close == 1) {
                        v.startAnimation(anim0);
                    }
                }
                break;
            case R.id.following:
                if (event.getAction() == MotionEvent.ACTION_DOWN && Open_Close == 1) {
                    v.startAnimation(anim1);
                } else {
                    if (event.getAction() == MotionEvent.ACTION_UP && Open_Close == 1) {
                        v.startAnimation(anim0);
                    }
                }
                break;
            case R.id.dataBase:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ObjectAnimator objectAnimator = ofFloat(v, "TranslationY", 0, 30);
                    objectAnimator.setDuration(100);
                    objectAnimator.start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    ObjectAnimator objectAnimator = ofFloat(v, "TranslationY", 30, 0);
                    objectAnimator.setDuration(100);
                    objectAnimator.start();
                }
                break;
            case R.id.pretend:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ObjectAnimator objectAnimator = ofFloat(v, "TranslationY", 0, 30);
                    objectAnimator.setDuration(100);
                    objectAnimator.start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    ObjectAnimator objectAnimator = ofFloat(v, "TranslationY", 30, 0);
                    objectAnimator.setDuration(100);
                    objectAnimator.start();
                }
                break;
            case R.id.pe:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ObjectAnimator objectAnimator = ofFloat(v, "TranslationY", 0, 30);
                    objectAnimator.setDuration(100);
                    objectAnimator.start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    ObjectAnimator objectAnimator = ofFloat(v, "TranslationY", 30, 0);
                    objectAnimator.setDuration(100);
                    objectAnimator.start();
                }
                break;
        }
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        MenuLayout.setVisibility(View.GONE);
        MenuCount = 0;
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location == null || mMapView == null)
                return;
            myLocation = location;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            Log.i("MainActivity", String.valueOf(location.getLatitude()) + " " + String.valueOf(location.getLongitude()));


            //30.318594 120.385722
            //30.318387 120.385538

            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(19f);
                MapStatusUpdate u2 = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
                mBaiduMap.animateMapStatus(u2);


            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    public double[][] getRandomPosition(int Number) {//30.316634 30.312634  120.401974 120.388364
        int[][] result = new int[Number][2];
        double[][] results = new double[Number][2];
        int i = 0;
        while (i < Number) {
            int a = (int) (Math.random() * 4000);
            int b = (int) (Math.random() * 13610);
            int d = 0;
            for (int j = 0; j < i; j++) {
                if (Math.abs(result[j][0] - a) < 100 && Math.abs(result[j][1] - b) < 200) {
                    d = 1;
                }
            }
            if (d == 0) {
                result[i][0] = a;
                result[i][1] = b;
                i++;
            }
        }

        for (int k = 0; k < Number; k++) {
            results[k][0] = result[k][0] * 0.000001 + 30.312634;
            results[k][1] = result[k][1] * 0.000001 + 120.388364;
            BigDecimal a = new BigDecimal(results[k][0]);
            results[k][0] = a.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
            BigDecimal b = new BigDecimal(results[k][1]);
            results[k][1] = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return results;
    }





    /*public class MyLocationListener implements BDLocationListener {
        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

        @Override
        public void onReceiveLocation(BDLocation location) {

            Log.i("MainActivity", "7");

            //获取定位结果
            StringBuffer sb = new StringBuffer(256);

            sb.append("time : ");
            sb.append(location.getTime());    //获取定位时间

            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型

            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息

            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //获取经度信息

            sb.append("\nradius : ");
            sb.append(location.getRadius());    //获取定位精准度

            if (location.getLocType() == BDLocation.TypeGpsLocation){

                // GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());    // 单位：公里每小时

                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());    //获取卫星数

                sb.append("\nheight : ");
                sb.append(location.getAltitude());    //获取海拔高度信息，单位米

                sb.append("\ndirection : ");
                sb.append(location.getDirection());    //获取方向信息，单位度

                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){

                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息

                sb.append("\ndescribe : ");
                sb.append("网络定位成功");

            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");

            } else if (location.getLocType() == BDLocation.TypeServerError) {

                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");

            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

            }

            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息

            List<Poi> list = location.getPoiList();    // POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }

            Log.i("MainActivity", "8");
            Log.i("BaiduLocationApiDem", sb.toString());
        }
    }*/

    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        //退出时销毁定位
        if (mLocClient != null) {
            mLocClient.stop();
        }
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        int Number = preferences.getInt("Coins", 0);
        String Coins = "      " + String.valueOf(Number) + "  ";
        MyCoins.setText(Coins);
        overridePendingTransition(0, 0);
        Intent intent0 = new Intent(MainActivity.this, CaptureMusicServer.class);
        stopService(intent0);
        Intent intent1 = new Intent(MainActivity.this, MusicServer.class);
        startService(intent1);
        Intent intent2 = new Intent(MainActivity.this, ShopMusicServer.class);
        stopService(intent2);
        super.onRestart();
    }

    @Override
    protected void onStop() {
        Intent intent = new Intent(MainActivity.this, MusicServer.class);
        stopService(intent);
        super.onStop();
    }
}



