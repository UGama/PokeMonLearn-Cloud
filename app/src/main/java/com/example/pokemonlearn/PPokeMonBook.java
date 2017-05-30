package com.example.pokemonlearn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gama on 17/5/17.
 */

public class PPokeMonBook extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private String Name;
    private String User;
    private List<OwnItem> list;
    private List<OwnPet> ownPets;
    private OwnPet P_OwnPet;
    private PokeMon P_PokeMon;

    private ImageView transfer1;
    private ImageView transfer2;
    private Animation trans_out1;
    private Animation trans_out2;

    private PercentRelativeLayout Layout_Up;
    private Animation animation2;
    private ImageView Left_Shape;
    private PercentRelativeLayout Right_Shape1;
    private PercentRelativeLayout Right_Shape2;
    private ImageView Connect1;
    private ImageView Connect2;
    private RecyclerView recyclerView;

    private TextView Item_name;
    private ImageView Bag_Pic;
    private Animation anim4;
    private Animation animation4;

    private ImageView Item;
    private Button Use;
    private Button Cancel;
    private Animation Float2;
    private Animation Float3;
    private boolean FirstTouch;

    private TextView Message;
    private ImageView Text;
    private ImageView Screen;
    private Animation Text_Show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_series);

        Intent intent = getIntent();
        Name = intent.getStringExtra("PokeMon");
        User = intent.getStringExtra("User");

        ownPets = new ArrayList<>();
        AVObject user = AVObject.createWithoutData("Users", User);
        AVRelation<AVObject> relation = user.getRelation("OwnPet");
        AVQuery<AVObject> query = relation.getQuery();
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    OwnPet ownPet = new OwnPet(avObject.getObjectId(),
                            avObject.getString("Name"),
                            avObject.getString("ImageName"),
                            avObject.getInt("Dex"),
                            avObject.getString("BallImageName"));
                    ownPets.add(ownPet);
                    if (ownPet.getName().equals(Name)) {
                        P_OwnPet = ownPet;
                    }
                }
            }
        });

        AVQuery<AVObject> query1 = new AVQuery<>("PM");
        query1.whereEqualTo("Name", Name);
        query1.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                P_PokeMon = new PokeMon(avObject.getObjectId(),
                        avObject.getInt("Number"),
                        avObject.getString("Name"),
                        avObject.getString("ImageName2"),
                        avObject.getInt("Weight"),
                        avObject.getString("ImageName"),
                        avObject.getString("Senior"));
            }
        });
        Log.i("OwnPet", Name);

        list = new ArrayList<>();
        AVObject user1 = AVObject.createWithoutData("Users", User);
        AVRelation<AVObject> relation1 = user1.getRelation("OwnItem");
        AVQuery<AVObject> query2 = relation1.getQuery();
        query2.whereEqualTo("Type", 4);
        query2.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list1, AVException e) {
                for (AVObject avObject : list1) {
                    OwnItem ownItem = new OwnItem(avObject.getObjectId(),
                            avObject.getString("Name"),
                            avObject.getInt("Number"),
                            avObject.getInt("Type"),
                            avObject.getString("ImageName"),
                            avObject.getInt("Dex"));
                    list.add(ownItem);
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(PPokeMonBook.this);
                recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
                recyclerView.setLayoutManager(layoutManager);
                ItemAdapter adapter2 = new ItemAdapter(list);
                recyclerView.setAdapter(adapter2);
                UIInit();
            }
        });
    }

    public void UIInit() {
        transfer1 = (ImageView) findViewById(R.id.transfer1);
        transfer2 = (ImageView) findViewById(R.id.transfer2);
        trans_out1 = AnimationUtils.loadAnimation(PPokeMonBook.this, R.anim.trans_out_up);
        trans_out2 = AnimationUtils.loadAnimation(PPokeMonBook.this, R.anim.trans_out_down);
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

        Layout_Up = (PercentRelativeLayout) findViewById(R.id.Layout_up);
        Layout_Up.setOnClickListener(this);
        animation2 = AnimationUtils.loadAnimation(PPokeMonBook.this, R.anim.pc_series_show);
        Left_Shape = (ImageView) findViewById(R.id.left_shape);
        Right_Shape1 = (PercentRelativeLayout) findViewById(R.id.right_shape1);
        Right_Shape2 = (PercentRelativeLayout) findViewById(R.id.right_shape2);
        Connect1 = (ImageView) findViewById(R.id.connect1);
        Connect2 = (ImageView) findViewById(R.id.connect2);
        Left_Shape.startAnimation(animation2);
        Right_Shape1.startAnimation(animation2);
        Right_Shape2.startAnimation(animation2);
        recyclerView.startAnimation(animation2);
        Connect1.startAnimation(animation2);
        Connect2.startAnimation(animation2);
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Message.setVisibility(View.VISIBLE);
                Text.setVisibility(View.VISIBLE);
                Screen.setVisibility(View.VISIBLE);
                Message.startAnimation(Text_Show);
                Text.startAnimation(Text_Show);
                Screen.startAnimation(Text_Show);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Item_name = (TextView) findViewById(R.id.item_name);
        Bag_Pic = (ImageView) findViewById(R.id.bag_pic);
        Bag_Pic.setImageResource(R.drawable.init_ball4);

        anim4 = AnimationUtils.loadAnimation(PPokeMonBook.this, R.anim.anim4);


        Item = (ImageView) findViewById(R.id.Item);
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.bag_decorate3);
        Item.setImageBitmap(bitmap);

        Use = (Button) findViewById(R.id.use);
        Use.setVisibility(View.GONE);
        Use.setOnClickListener(this);
        Use.setOnTouchListener(this);
        Cancel = (Button) findViewById(R.id.cancel);
        Cancel.setVisibility(View.GONE);
        Cancel.setOnClickListener(this);
        Cancel.setOnTouchListener(this);

        Float2 = AnimationUtils.loadAnimation(PPokeMonBook.this, R.anim.cap_float2);
        Float3 = AnimationUtils.loadAnimation(PPokeMonBook.this, R.anim.cap_float3);

        Message = (TextView) findViewById(R.id.PC_message);
        Message.setVisibility(View.GONE);
        Text = (ImageView) findViewById(R.id.PC_text);
        Text.setVisibility(View.GONE);
        Screen = (ImageView) findViewById(R.id.screen);
        Screen.setVisibility(View.GONE);
        Text_Show = AnimationUtils.loadAnimation(PPokeMonBook.this, R.anim.anim4);

        FirstTouch = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Layout_up:
                Item_name.setText(" ? ? ? ");
                Bag_Pic.setImageResource(R.drawable.init_ball4);
                Item.setVisibility(View.VISIBLE);
                Use.setVisibility(View.GONE);
                Cancel.setVisibility(View.GONE);
                FirstTouch = true;
                break;
            case R.id.use:
                String PMStone = Item_name.getText().toString();
                Intent intent1 = new Intent();
                intent1.putExtra("PMBook", PMStone);
                setResult(RESULT_OK, intent1);
                finish();
                break;
            case R.id.cancel:
                Intent intent2 = new Intent();
                setResult(RESULT_CANCELED, intent2);
                finish();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.use:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Use.getBackground().setAlpha(125);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Use.getBackground().setAlpha(255);
                }
                break;
            case R.id.cancel:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Cancel.getBackground().setAlpha(125);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Cancel.getBackground().setAlpha(255);
                }
                break;
        }
        return false;
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

        private List<OwnItem> List;

        public ItemAdapter(List<OwnItem> List) {
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
                    TextView Name = (TextView) v.findViewById(R.id.name);
                    String name = Name.getText().toString();
                    for (OwnItem ownItem : list) {
                        if (ownItem.getName().equals(name)) {
                            final int TheDex = ownItem.getDex();
                            Item_name.setText(ownItem.getName());
                            AVQuery<AVObject> query = new AVQuery<>("_File");
                            query.whereEqualTo("name", ownItem.getImageName() + ".png");
                            query.getFirstInBackground(new GetCallback<AVObject>() {
                                @Override
                                public void done(AVObject avObject, AVException e) {
                                    AVFile avFile = new AVFile("Book.png", avObject.getString("url"),
                                            new HashMap<String, Object>());
                                    avFile.getDataInBackground(new GetDataCallback() {
                                        @Override
                                        public void done(byte[] bytes, AVException e) {
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                            Bag_Pic.setImageBitmap(bitmap);
                                            Bag_Pic.startAnimation(anim4);

                                        }
                                    });
                                }
                            });
                            break;

                        }
                    }

                    if (FirstTouch) {
                        Use.setVisibility(View.VISIBLE);
                        Cancel.setVisibility(View.VISIBLE);
                        Use.startAnimation(Float2);
                        Cancel.startAnimation(Float3);
                        FirstTouch = false;
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            OwnItem ownItem = List.get(position);
            holder.Name.setText(ownItem.getName());
            holder.Number.setText(String.valueOf(ownItem.getNumber()));
        }

        @Override
        public int getItemCount() {
            return List.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView Name;
            TextView Number;
            View ItemView;

            public ViewHolder(View view) {
                super(view);
                Name = (TextView) view.findViewById(R.id.name);
                Number = (TextView) view.findViewById(R.id.number);
                ItemView = view;
            }
        }
    }
}

