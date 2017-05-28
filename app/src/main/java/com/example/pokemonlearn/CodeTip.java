package com.example.pokemonlearn;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Gama on 28/5/17.
 */

public class CodeTip extends AppCompatActivity {
    /*AVQuery<AVObject> avQuery = new AVQuery<>("_File");
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
    });*/
}
