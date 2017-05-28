package com.example.pokemonlearn;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;

import java.io.FileNotFoundException;

/**
 * Created by Gama on 27/5/17.
 */

public class DataBaseOperate extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.databaseoperate);

        try {
            final AVFile file = AVFile.withAbsoluteLocalPath("evolve_cancel.png",
                    Environment.getExternalStorageDirectory() + "/Users/Gama/Desktop/drawable/evolve_cancel.png");

            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        Log.i("DataBase", file.getUrl());//返回一个唯一的 Url 地址
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
