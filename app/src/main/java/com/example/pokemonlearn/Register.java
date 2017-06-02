package com.example.pokemonlearn;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.List;

/**
 * Created by Gama on 1/6/17.
 */

public class Register extends AppCompatActivity implements View.OnClickListener {
    private String Name;
    private String PassWord1;
    private String PassWord2;

    private Button Register;
    private Button Cancel;

    private EditText UserName;
    private EditText PassWord;
    private EditText Confirm;

    private ImageView transfer1;
    private ImageView transfer2;
    private Animation trans_out1;
    private Animation trans_out2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Register = (Button) findViewById(R.id.register);
        Register.setOnClickListener(this);
        Cancel = (Button) findViewById(R.id.cancel);
        Cancel.setOnClickListener(this);
        UserName = (EditText) findViewById(R.id.UserName);
        PassWord = (EditText) findViewById(R.id.PassWord);
        Confirm = (EditText) findViewById(R.id.Confirm);
        transfer1 = (ImageView) findViewById(R.id.transfer1);
        transfer1.setVisibility(View.VISIBLE);
        transfer2 = (ImageView) findViewById(R.id.transfer2);
        transfer2.setVisibility(View.VISIBLE);
        trans_out1 = AnimationUtils.loadAnimation(Register.this, R.anim.trans_out_up);
        trans_out2 = AnimationUtils.loadAnimation(Register.this, R.anim.trans_out_down);
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

        transfer1.startAnimation(trans_out1);
        transfer2.startAnimation(trans_out2);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                Name = UserName.getText().toString();
                PassWord1 = PassWord.getText().toString();
                PassWord2 = Confirm.getText().toString();
                if (Name.equals("") || PassWord1.equals("") || PassWord2.equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("错误")
                            .setMessage("请输入正确的用户名或密码！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else if (PassWord1.equals(PassWord2)) {
                    AVQuery<AVObject> query = new AVQuery<>("Users");
                    query.whereEqualTo("UserName", Name);
                    query.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            if (list.size() == 0) {
                                AVObject avObject = new AVObject("Users");
                                avObject.put("UserName", Name);
                                avObject.put("PassWord", PassWord1);
                                avObject.put("Scene", "http://ac-vuvtafsi.clouddn.com/c6daebf33ab031318726.png");
                                avObject.saveInBackground();
                                AlertDialog alertDialog = new AlertDialog.Builder(Register.this).setTitle("完成")
                                        .setMessage("创建成功！")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                            else {
                                AlertDialog alertDialog = new AlertDialog.Builder(Register.this).setTitle("错误")
                                        .setMessage("该用户名已被注册！")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();
                            }
                        }
                    });
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("错误")
                            .setMessage("两次密码不一致！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }
}
