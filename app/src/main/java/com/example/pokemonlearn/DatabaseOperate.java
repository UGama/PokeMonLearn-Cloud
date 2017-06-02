package com.example.pokemonlearn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DatabaseOperate extends AppCompatActivity {

    private String User;

    private RecyclerView recyclerView;

    public void AddCoins() {
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        int MyCoin = sharedPreferences.getInt("Coins", 0);
        MyCoin += 1000;
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putInt("Coins", MyCoin);
        editor.apply();
        Log.i("MyCoin", String.valueOf(MyCoin));
    }

    public void AddEevee() {
        final AVObject User1 = AVObject.createWithoutData("Users", User);
        OwnPet ownPet = new OwnPet("", "伊布", "eevee2", 18, "pokeball");
        final AVObject avObject = new AVObject("OwnPet");
        avObject.put("Name", ownPet.getName());
        avObject.put("ImageName", ownPet.getImageName());
        avObject.put("Dex", ownPet.getDex());
        avObject.put("BallImageName", ownPet.getBallImageName());

        AVObject.saveAllInBackground(Arrays.asList(avObject), new SaveCallback() {
            @Override
            public void done(AVException e) {
                AVRelation<AVObject> relation = User1.getRelation("OwnPet");
                relation.add(avObject);
                User1.saveInBackground();
            }
        });
    }

    public void AddItem() {
        AVQuery<AVObject> query = new AVQuery<>("PokeMonStone");
        query.whereEqualTo("Name", "火之石");
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                AVObject avObject0 = AVObject.createWithoutData("Users", User);
                AVRelation<AVObject> relation = avObject0.getRelation("OwnItem");
                AVObject avObject1 = new AVObject("OwnItem");
                avObject1.put("Name", "火之石");
                avObject1.put("Number", 1);
                avObject1.put("ImageName", avObject.getString("ImageName"));
                avObject1.put("Dex", avObject.getInt("Number"));
                avObject1.put("Type", 1);
                relation.add(avObject1);
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.databaseoperate);

        Intent intent = getIntent();
        User = intent.getStringExtra("User");

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<DatabaseButton> list = new ArrayList<>();
        DatabaseButton b1 = new DatabaseButton("＋1000钱", 1);
        DatabaseButton b2 = new DatabaseButton("伊布＋", 2);
        DatabaseButton b3 = new DatabaseButton("火之石＋", 3);

        list.add(b1);
        list.add(b2);
        list.add(b3);

        DatabaseOperateAdapter adapter = new DatabaseOperateAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    class DatabaseOperateAdapter extends RecyclerView.Adapter<DatabaseOperateAdapter.ViewHolder> {

        private List<DatabaseButton> List;

        public DatabaseOperateAdapter(List<DatabaseButton> List) {
            this.List = List;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.database_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final DatabaseButton databaseButton = List.get(position);
            holder.button.setText(databaseButton.text);
            holder.textView.setText(String.valueOf(databaseButton.Number));
            holder.ItemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    TextView numberText = (TextView) v.findViewById(R.id.TextView);
                    int number = Integer.valueOf(numberText.getText().toString());
                    switch (number) {
                        case 1:
                            AddCoins();
                            break;
                        case 2:
                            AddEevee();
                            break;
                        case 3:
                            final EditText editText = (EditText) findViewById(R.id.editText);
                            editText.setVisibility(View.VISIBLE);
                            final Button button = (Button) findViewById(R.id.button);
                            button.setVisibility(View.VISIBLE);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (editText.getText().toString().equals("wm")) {
                                        AddItem();
                                        editText.setVisibility(View.GONE);
                                        button.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(DatabaseOperate.this, "Fuck u", Toast.LENGTH_LONG).show();
                                        editText.setVisibility(View.GONE);
                                        button.setVisibility(View.GONE);
                                    }
                                }
                            });
                            break;
                    }
                }
            });
            holder.ItemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.getBackground().setAlpha(125);
                    } else {
                        v.getBackground().setAlpha(255);
                    }
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return List.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView button;
            TextView textView;
            View ItemView;
            public ViewHolder(View view) {
                super(view);
                button = (TextView) view.findViewById(R.id.TextView2);
                textView = (TextView) view.findViewById(R.id.TextView);
                ItemView = view;
            }
        }



    }

    private class DatabaseButton {
        private String text;
        private int Number;

        public DatabaseButton(String text, int number) {
            this.text = text;
            this.Number = number;
        }
    }

}
