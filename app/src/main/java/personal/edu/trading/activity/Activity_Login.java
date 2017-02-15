package personal.edu.trading.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import personal.edu.trading.Local_preservation;
import personal.edu.trading.R;
import personal.edu.trading.SharedPf;
import personal.edu.trading.http.EasyShopClient;
import personal.edu.trading.user.User;

/**
 * 登录界面
 * Created by Administrator on 2017/2/8 0008.
 */
public class Activity_Login extends AppCompatActivity {
    LinearLayout gone_ll;
    ImageView username_im, password_im;
    EditText username_et, password_et;
    Button login_but;
    TextView reg_tv;
    Toolbar toolbar;
    RelativeLayout pb_gone;
    String name,pwd;

    void oninClick(){
        name=username_et.getText().toString();
        pwd=password_et.getText().toString();
        Call call= EasyShopClient.getInstance().login(name,pwd);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    ResponseBody responseBody=response.body();
                    String json=responseBody.string();
                    User user=new Gson().fromJson(json,User.class);
                    int code =user.getCode();
                    String msg=user.getMsg();
                    Message ms=new Message();
                    if(code==1){
                        User.DataBean data=user.getData();
                        Local_preservation.setUser(data);
                        ms.what=0;
                        handler.sendMessage(ms);
                    }else if(code==2){
                        ms.what=1;
                        ms.obj=msg;
                        handler.sendMessage(ms);
                    }else{
                        ms.what=2;
                        handler.sendMessage(ms);
                    }
                }
                if(!response.isSuccessful()){
                    throw new IOException("error code"+response.code());
                }
            }
        });
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 0:
                    //广播传值
                    SharedPf.setLogin(Activity_Login.this,true);
                    pb_gone.setVisibility(View.GONE);
                    finish();
                    break;
                case 1:
                    String msg1= (String) msg.obj;
                    pb_gone.setVisibility(View.GONE);
                    Toast.makeText(Activity_Login.this,"登录失败："+msg1,
                            Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(Activity_Login.this,"未知错误",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inClick();
        inText();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void inClick() {
        toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //登录界面隐藏确认密码行
        gone_ll = (LinearLayout) findViewById(R.id.login_gone_ll);
        gone_ll.setVisibility(View.GONE);
        //登录注册界面的图片控件
        username_im = (ImageView) findViewById(R.id.login_username_im);
        username_im.setImageResource(R.mipmap.ic_person_white);
        password_im = (ImageView) findViewById(R.id.login_password_im);
        password_im.setImageResource(R.mipmap.ic_lock_white);
        //登录注册界面的输入框控件
        username_et = (EditText) findViewById(R.id.login_username_et);
        password_et = (EditText) findViewById(R.id.login_password_et);
        //登录注册界面的按钮控件
        pb_gone= (RelativeLayout) findViewById(R.id.login_gone_pb);
        login_but = (Button) findViewById(R.id.login_button);
        login_but.setText("登录");
        login_but.setEnabled(false);
        //登录注册界面的快速注册控件
        reg_tv = (TextView) findViewById(R.id.login_reg_tv);
        reg_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Login.this, Activity_Registered.class);
                startActivity(intent);
            }
        });
    }


    boolean username,password;
    void inText() {
        username_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    username_im.setImageResource(R.mipmap.ic_person_accent);
                    password_im.setImageResource(R.mipmap.ic_lock_white);
                }
            }
        });
        username_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()==0){
                    username=false;
                    login_but.setBackgroundResource(R.color.toolbar_gray);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    username=true;
                    if(password){
                        login_but.setEnabled(true);
                        login_but.setBackgroundResource(R.color.toolbar);
                    }
                }else {
                    username=false;
                    login_but.setEnabled(false);
                }
            }
        });


        password_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    password_im.setImageResource(R.mipmap.ic_lock_accent);
                    username_im.setImageResource(R.mipmap.ic_person_white);
                }

            }
        });
        password_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()==0){
                    password=false;
                    login_but.setBackgroundResource(R.color.toolbar_gray);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    password=true;
                    if(username){
                        login_but.setEnabled(true);
                        login_but.setBackgroundResource(R.color.toolbar);
                    }
                }else {
                    password=false;
                    login_but.setEnabled(false);
                }
            }
        });

        login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_gone.setVisibility(View.VISIBLE);
                oninClick();

            }
        });
    }



}
