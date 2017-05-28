package com.example.pokemonlearn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.ProgressCallback;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private AVObject avObject0;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button DataBase = (Button) findViewById(R.id.Button);
        DataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DataBaseOperate.class);
                startActivity(intent);
            }
        });

        imageView = (ImageView) findViewById(R.id.test);

        AVQuery<AVObject> avQuery = new AVQuery<>("_File");
        avQuery.getInBackground("592a24edac502e006c6ee7ab", new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                AVFile file = new AVFile("charizard2.png", avObject.getString("url"), new HashMap<String, Object>());
                file.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] bytes, AVException e) {
                        if (e == null) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            imageView.setImageBitmap(bitmap);
                            Log.d("saved", "文件大小" + bytes.length);
                        } else {
                            Log.d("saved", "出错了" + e.getMessage());
                        }
                    }
                }, new ProgressCallback() {
                    @Override
                    public void done(Integer integer) {
                        Log.d("saved", "文件下载进度" + integer);
                    }
                });
                Log.i("test", avObject.getString("url"));
                // object 就是 id 为 558e20cbe4b060308e3eb36c 的 Todo 对象实例
            }
        });
    }
}
