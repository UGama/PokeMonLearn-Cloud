package com.example.pokemonlearn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gama on 29/5/17.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {

    private List<PokeMon> pokeMons;
    private PokeMon[] Pokemon;
    private int j = 0;
    private Bitmap[] bitmaps;
    private String[] name;
    private int k = 0;
    private String[] Url;
    private byte[][] Bytes;

    private Button Login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        DataInit();

        Login = (Button) findViewById(R.id.login);
        Login.setOnClickListener(this);

    }

    public void DataInit() {
        pokeMons = new ArrayList<>();
        Pokemon = new PokeMon[5];
        name = new String[5];
        bitmaps = new Bitmap[5];
        Url = new String[5];
        Bytes = new byte[5][];
        getRandomPokemon();
    }

    public void getRandomPokemon() {
        pokeMons = new ArrayList<>();
        AVQuery<AVObject> avQuery = new AVQuery<>("PM");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> avObjects, AVException e) {
                for (AVObject avObject : avObjects) {
                    PokeMon pokeMon = new PokeMon(avObject.getObjectId(),
                            avObject.getInt("Number"),
                            avObject.getString("Name"),
                            avObject.getString("ImageName2"),
                            avObject.getInt("Weight"),
                            avObject.getString("ImageName"),
                            avObject.getString("Senior")
                    );
                    pokeMons.add(pokeMon);
                }
                int sumWeight = 0;
                for (PokeMon pokeMon1 : pokeMons) {
                    sumWeight += pokeMon1.getWeight();
                }
                Log.i("sumWeight", String.valueOf(sumWeight));
                int mPokeMon = 0;
                for (int i = 0; i < 5; i++) {
                    int Random = (int) (Math.random() * sumWeight);
                    for (PokeMon pokeMon2 : pokeMons) {
                        int Count = Random - pokeMon2.getWeight();
                        if (Count > 0) {
                            Random = Count;
                        } else {
                            boolean repeat = false;
                            for (int j = 0; j < mPokeMon; j++) {
                                if (pokeMon2.getName().equals(Pokemon[j].getName())) {
                                    repeat = true;
                                    break;
                                }
                            }
                            if (repeat) {
                                i--;
                                break;
                            } else {
                                Pokemon[mPokeMon] = pokeMon2;
                                mPokeMon++;
                                Log.i("Count", String.valueOf(Random) + "  " + pokeMon2.getName());
                                break;
                            }
                        }
                    }
                }
                OverLayInit();
            }
        });
    }

    public void OverLayInit() {
        for (j = 0; j < 5; j++) {
            AVQuery<AVObject> list = new AVQuery<>("PM");
            Log.i("PokeMonName", Pokemon[j].getName());
            list.whereEqualTo("Name", Pokemon[j].getName());
            list.getFirstInBackground(new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    String Name = avObject.getString("ImageName");
                    Log.i("ImageName", Name);
                    name[k++] = Name;
                    if (k == 5) {
                        getUrl();
                    }
                }
            });
        }
    }

    public void getUrl() {
        k = 0;
        for (j = 0; j < 5; j++) {
            AVQuery<AVObject> list = new AVQuery<>("_File");
            list.whereEqualTo("name", name[j] + ".png");
            list.getFirstInBackground(new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    String url = avObject.getString("url");
                    Url[k] = url;
                    Log.i("Url", Url[k]);
                    k++;
                    if (k == 5) {
                        getBitmap();
                    }
                }
            });
        }
    }

    public void getBitmap() {
        k = 0;
        for (j = 0; j < 5; j++) {
            AVFile avFile = new AVFile("PokeMonPic.png", Url[j], new HashMap<String, Object>());
            avFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, AVException e) {
                    Bytes[k++] = bytes;
                    Log.i("Test", String.valueOf(k));
                    if (k == 5) {
                        getBitmap2();
                    }
                }
            });
        }
    }

    public void getBitmap2() {
        for (j = 0; j < 5; j++) {
            bitmaps[j] = BitmapFactory.decodeByteArray(Bytes[j], 0, Bytes[j].length);
            Log.i("Test1", String.valueOf(j));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("Name1", name[0]);
                intent.putExtra("Name2", name[1]);
                intent.putExtra("Name3", name[2]);
                intent.putExtra("Name4", name[3]);
                intent.putExtra("Name5", name[4]);
                intent.putExtra("Url1", Url[0]);
                intent.putExtra("Url2", Url[1]);
                intent.putExtra("Url3", Url[2]);
                intent.putExtra("Url4", Url[3]);
                intent.putExtra("Url5", Url[4]);
                startActivity(intent);
                break;
        }
    }
}
