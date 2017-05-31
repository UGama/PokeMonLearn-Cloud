package com.example.pokemonlearn;

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
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.ProgressCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gama on 8/4/17.
 */

public class PokeDex extends AppCompatActivity implements View.OnClickListener {
    private List<PokeMon> pokeMons;
    private String ChoseName;

    private Animation Fly_in;
    private Animation Flash;
    private Animation Left;
    private Animation Right;
    private Animation ConnectAnim;
    private Animation Layout_fly_in;
    private Animation Layout_fly_in2;

    private ImageView Computer;
    private ImageView flash2;
    private PercentRelativeLayout Left_shape;
    private ImageView Wanted;
    private ImageView Right_shape;
    private ImageView Connect;
    private ImageView PokeDex_init;
    private TextView PokeDex_name;

    private int times = 0;

    private TextView explain;

    private PercentRelativeLayout PokeDex_down;

    private RecyclerView recyclerView;
    private ImageView UP;
    private ImageView DOWN;
    private ImageView Decorate;

    private ImageView transfer1;
    private ImageView transfer2;
    private Animation trans_out1;
    private Animation trans_out2;

    private Animation anim4;

    private PercentRelativeLayout Support;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokedex);

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        pokeMons = new ArrayList<>();
        AVQuery<AVObject> avObjectAVQuery = new AVQuery<>("PM");
        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    PokeMon pokeMon = new PokeMon(avObject.getObjectId(),
                            avObject.getInt("Number"),
                            avObject.getString("Name"),
                            avObject.getString("ImageName2"),
                            avObject.getInt("Weight"),
                            avObject.getString("ImageName"),
                            avObject.getString("Senior"));
                    pokeMons.add(pokeMon);
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(PokeDex.this);
                recyclerView.setLayoutManager(layoutManager);
                PokeDexAdapter adapter = new PokeDexAdapter(pokeMons);
                recyclerView.setAdapter(adapter);
                UIInit();
            }
        });
    }

    public void UIInit() {
        transfer1 = (ImageView) findViewById(R.id.transfer1);
        transfer2 = (ImageView) findViewById(R.id.transfer2);
        transfer1.setVisibility(View.VISIBLE);
        transfer2.setVisibility(View.VISIBLE);
        trans_out1 = AnimationUtils.loadAnimation(PokeDex.this, R.anim.trans_out_up);
        trans_out2 = AnimationUtils.loadAnimation(PokeDex.this, R.anim.trans_out_down);
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

        Computer = (ImageView) findViewById(R.id.computer);
        flash2 = (ImageView) findViewById(R.id.flash2);
        flash2.setVisibility(View.GONE);
        Fly_in = AnimationUtils.loadAnimation(PokeDex.this, R.anim.left_fly_in);
        Fly_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                flash2.setVisibility(View.VISIBLE);
                flash2.startAnimation(Flash);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Computer.startAnimation(Fly_in);
        Computer.setOnClickListener(this);
        Flash = AnimationUtils.loadAnimation(PokeDex.this, R.anim.flash);
        Flash.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (times <= 8) {
                    flash2.startAnimation(Flash);
                    times++;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Left_shape = (PercentRelativeLayout) findViewById(R.id.choseList);
        Left = AnimationUtils.loadAnimation(PokeDex.this, R.anim.dex_shape_left);
        Left_shape.startAnimation(Left);

        Right_shape = (ImageView) findViewById(R.id.Dex);
        Right = AnimationUtils.loadAnimation(PokeDex.this, R.anim.dex_shape_right);
        Right_shape.startAnimation(Right);

        Connect = (ImageView) findViewById(R.id.connect1);
        ConnectAnim = AnimationUtils.loadAnimation(PokeDex.this, R.anim.anim3);
        Connect.startAnimation(ConnectAnim);

        Connect = (ImageView) findViewById(R.id.connect2);
        Connect.startAnimation(ConnectAnim);

        PokeDex_down = (PercentRelativeLayout) findViewById(R.id.pokeDex_Layout_down);
        Layout_fly_in = AnimationUtils.loadAnimation(PokeDex.this, R.anim.layout_fly_in);
        PokeDex_down.startAnimation(Layout_fly_in);

        Wanted = (ImageView) findViewById(R.id.wanted);
        Wanted.startAnimation(Layout_fly_in);

        PokeDex_init = (ImageView) findViewById(R.id.pokeDex_Pic);
        PokeDex_init.startAnimation(Layout_fly_in);

        PokeDex_name = (TextView) findViewById(R.id.wood_name);
        PokeDex_name.startAnimation(Layout_fly_in);

        Layout_fly_in2 = AnimationUtils.loadAnimation(PokeDex.this, R.anim.layout_fly_in2);
        recyclerView.startAnimation(Layout_fly_in2);

        UP = (ImageView) findViewById(R.id.gold_up);
        DOWN = (ImageView) findViewById(R.id.gold_down);
        UP.startAnimation(Right);
        DOWN.startAnimation(Right);
        Decorate = (ImageView) findViewById(R.id.pokeDex_decorate);
        Decorate.startAnimation(Right);

        Support = (PercentRelativeLayout) findViewById(R.id.Support);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.computer:
                PokeDex_init.setBackgroundResource(R.drawable.pokedexinit);
                PokeDex_init.startAnimation(anim4);
                PokeDex_name.setText(" ??? ");
                times = 4;
                flash2.startAnimation(Flash);
        }
    }

    class PokeDexAdapter extends RecyclerView.Adapter<PokeDexAdapter.ViewHolder> {
        private List<PokeMon> PokeDexList;
        TextView PokemonName;

        public PokeDexAdapter(List<PokeMon> myPetList) {
            this.PokeDexList = myPetList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pokedex_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            holder.PetItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    Support.setVisibility(View.VISIBLE);
                    PokemonName = (TextView) v.findViewById(R.id.pokedex_name);
                    ChoseName = PokemonName.getText().toString();
                    Log.i("Name", ChoseName);
                    AVQuery<AVObject> query = new AVQuery<>("PM");
                    query.whereEqualTo("Name", ChoseName);
                    query.getFirstInBackground(new GetCallback<AVObject>() {
                        @Override
                        public void done(AVObject avObject, AVException e) {
                            AVQuery<AVObject> query1 = new AVQuery<>("_File");
                            query1.whereEqualTo("name", avObject.getString("ImageName2") + ".png");
                            query1.getFirstInBackground(new GetCallback<AVObject>() {
                                @Override
                                public void done(AVObject avObject, AVException e) {
                                    AVFile avFile = new AVFile("PokeMon.png", avObject.getString("url"), new HashMap<String, Object>());
                                    avFile.getDataInBackground(new GetDataCallback() {
                                                                   @Override
                                                                   public void done(byte[] bytes, AVException e) {
                                                                       Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                                       anim4 = AnimationUtils.loadAnimation(PokeDex.this, R.anim.anim4);
                                                                       PokeDex_init.setImageBitmap(bitmap);
                                                                       PokeDex_init.startAnimation(anim4);
                                                                       PokeDex_name.setText(ChoseName);
                                                                       times = 4;
                                                                       flash2.startAnimation(Flash);
                                                                   }
                                                               }, new ProgressCallback() {
                                        @Override
                                        public void done(Integer integer) {
                                            if (integer == 100) {
                                                Log.i("Test", "100");
                                                progressBar.setVisibility(View.GONE);
                                                Support.setVisibility(View.GONE);
                                            }
                                        }
                                                               }
                                    );
                                }
                            });
                        }
                    });
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            PokeMon pokeMon = PokeDexList.get(position);
            holder.PokeMonName.setText(pokeMon.getName());
        }

        @Override
        public int getItemCount() {
            return PokeDexList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView PokeMonName;
            View PetItemView;

            public ViewHolder(View view) {
                super(view);
                PokeMonName = (TextView) view.findViewById(R.id.pokedex_name);
                PetItemView = view;
            }

        }
    }
}
