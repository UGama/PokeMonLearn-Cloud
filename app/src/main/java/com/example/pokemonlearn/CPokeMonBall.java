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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gama on 11/5/17.
 */

public class CPokeMonBall extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private String User;
    private List<OwnItem> ownItems;
    private String Url;

    private ImageView transfer1;
    private ImageView transfer2;
    private Animation trans_out1;
    private Animation trans_out2;

    private PercentRelativeLayout Layout_Up;
    private RecyclerView recyclerView;

    private TextView Item_name;
    private ImageView Bag_Pic;
    private Animation anim4;

    private ImageView Item;
    private Button Use;
    private Button Cancel;
    private Animation float2;
    private Animation float3;

    private boolean FirstTouch;

    private PercentRelativeLayout Support;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pc_series);

        Intent intent = getIntent();
        User = intent.getStringExtra("User");

        ownItems = new ArrayList<>();
        AVObject user = AVObject.createWithoutData("Users", User);
        AVRelation<AVObject> relation = user.getRelation("OwnItem");
        AVQuery<AVObject> query = relation.getQuery();
        query.whereEqualTo("Type", 1);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    OwnItem ownItem = new OwnItem("", avObject.getString("Name"),
                            avObject.getInt("Number"),
                            avObject.getInt("Type"),
                            avObject.getString("ImageName"),
                            avObject.getInt("Dex"));
                    ownItems.add(ownItem);
                }
                Log.i("Count", String.valueOf(ownItems.size()));
                UIInit();
            }
        });

        FirstTouch = true;
    }

    public void UIInit() {
        transfer1 = (ImageView) findViewById(R.id.transfer1);
        transfer2 = (ImageView) findViewById(R.id.transfer2);
        trans_out1 = AnimationUtils.loadAnimation(CPokeMonBall.this, R.anim.trans_out_up);
        trans_out2 = AnimationUtils.loadAnimation(CPokeMonBall.this, R.anim.trans_out_down);
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

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        Item_name = (TextView) findViewById(R.id.item_name);
        Bag_Pic = (ImageView) findViewById(R.id.bag_pic);
        Bag_Pic.setBackgroundResource(R.drawable.init_ball);
        Bag_Pic.setVisibility(View.VISIBLE);

        anim4 = AnimationUtils.loadAnimation(CPokeMonBall.this, R.anim.anim4);

        LinearLayoutManager layoutManager = new LinearLayoutManager(CPokeMonBall.this);
        recyclerView.setLayoutManager(layoutManager);
        ItemAdapter adapter2 = new ItemAdapter(ownItems);
        recyclerView.setAdapter(adapter2);

        Item = (ImageView) findViewById(R.id.Item);
        Item.setBackgroundResource(R.drawable.bag_decorate);

        Use = (Button) findViewById(R.id.use);
        Use.setVisibility(View.GONE);
        Use.setOnClickListener(this);
        Use.setOnTouchListener(this);
        Cancel = (Button) findViewById(R.id.cancel);
        Cancel.setOnClickListener(this);
        Cancel.setOnTouchListener(this);
        Cancel.setVisibility(View.GONE);
        float2 = AnimationUtils.loadAnimation(CPokeMonBall.this, R.anim.cap_float2);
        float3 = AnimationUtils.loadAnimation(CPokeMonBall.this, R.anim.cap_float3);

        Support = (PercentRelativeLayout) findViewById(R.id.Support);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Layout_up:
                Item_name.setText(" ? ? ? ");
                Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.init_ball);
                Bag_Pic.setImageBitmap(bitmap);
                Item.setVisibility(View.VISIBLE);
                Use.setVisibility(View.GONE);
                Cancel.setVisibility(View.GONE);
                FirstTouch = true;
                break;
            case R.id.use:
                String PMBall = Item_name.getText().toString();
                Intent intent1 = new Intent();
                intent1.putExtra("PMBall", PMBall);
                intent1.putExtra("Url", Url);
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
                    Support.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    TextView Name = (TextView) v.findViewById(R.id.name);
                    String name = Name.getText().toString();
                    for (OwnItem ownItem : ownItems) {
                        if (ownItem.getName().equals(name)) {
                            Item_name.setText(ownItem.getName());
                            String ImageName = ownItem.getImageName();
                            AVQuery<AVObject> query = new AVQuery<>("_File");
                            query.whereEqualTo("name", ImageName + ".png");
                            query.getFirstInBackground(new GetCallback<AVObject>() {
                                @Override
                                public void done(AVObject avObject, AVException e) {
                                    Url = avObject.getString("url");
                                    Log.i("Url", Url);
                                    AVFile avFile = new AVFile("PokeMonPic.png", Url, new HashMap<String, Object>());
                                    avFile.getDataInBackground(new GetDataCallback() {
                                        @Override
                                        public void done(byte[] bytes, AVException e) {
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                            Bag_Pic.setImageBitmap(bitmap);
                                            Bag_Pic.startAnimation(anim4);
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
                    }

                    if (FirstTouch) {
                        Bag_Pic.getBackground().setAlpha(0);
                        FirstTouch = false;
                        Use.setVisibility(View.VISIBLE);
                        Use.startAnimation(float2);
                        Cancel.setVisibility(View.VISIBLE);
                        Cancel.startAnimation(float3);
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
