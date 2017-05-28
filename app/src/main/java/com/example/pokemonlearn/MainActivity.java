package com.example.pokemonlearn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.avos.avoscloud.AVObject;

import java.util.ArrayList;
import java.util.List;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<PokeMonBook> list = new ArrayList<>();
                PokeMonBook p3 = new PokeMonBook("水之秘籍", "skill_water", 3, 1000);

                list.add(p3);

                for (PokeMonBook pokeMonBook : list) {
                    AVObject avObject = new AVObject("PokeMonBook");
                    avObject.put("Name", pokeMonBook.getName());
                    avObject.put("ImageName", pokeMonBook.getImageName());
                    avObject.put("Number", pokeMonBook.getNumber());
                    avObject.put("Price", pokeMonBook.getPrice());
                    avObject.saveInBackground();
                }
            }
        });

    }
}

