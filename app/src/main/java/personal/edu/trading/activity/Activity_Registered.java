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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by Administrator on 2017/2/8 0008.
 */
public class Activity_Registered extends AppCompatActivity {
    ImageView username_im,password_im,newpassword_im;
    EditText username_et,password_et,newpasword_et;
    Button login_but;
    TextView reg_tv;
    Toolbar toolbar;
    RelativeLayout pb_gone;

    String name,pwd,newpwd;
    void onIcick(){
        name=username_et.getText().toString();
        pwd=password_et.getText().toString();
        newpwd=newpasword_et.getText().toString();
        if(pwd.equals(newpwd)){
            Call call= EasyShopClient.getInstance().register(name,pwd);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful()){
                        ResponseBody responseBody=response.body();
                        String json=responseBody.string();
                        User user= new Gson().fromJson(json,User.class);
                        try {
                            User.DataBean user_reg=user.getData();
                            Local_preservation.setUser(user_reg);
                            JSONObject jsonObject=new JSONObject(json);
                            int code=jsonObject.getInt("code");
                            String msg=jsonObject.getString("msg");
                            Message ms=new Message();
                            if(code==1){
                                JSONObject data=jsonObject.getJSONObject("data");
                                String name11=data.getString("username");
                                Log.i("msg","注册成功："+name11);
                                handler.sendEmptyMessage(0);
                            }else if(code==2){
                                ms.what=1;
                                ms.obj=msg;
                                handler.sendMessage(ms);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else {
            Toast.makeText(this,"两次密码不一致",Toast.LENGTH_SHORT).show();
        }

    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent;
            pb_gone.setVisibility(View.GONE);
            switch (msg.what){
                case 0:
                    intent=new Intent(Activity_Registered.this,MainActivity.class);
                    SharedPf.setLogin(Activity_Registered.this,true);
                    startActivity(intent);
                    break;
                case 1:
                    String msg1= (String) msg.obj;
                    Toast.makeText(Activity_Registered.this,"注册失败："+msg1,
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

    void inClick(){
        toolbar= (Toolbar) findViewById(R.id.login_toolbar);
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //登录注册界面的图片控件
        username_im= (ImageView) findViewById(R.id.login_username_im);
        username_im.setImageResource(R.mipmap.ic_person_white);
        password_im= (ImageView) findViewById(R.id.login_password_im);
        password_im.setImageResource(R.mipmap.ic_lock_white);
        newpassword_im= (ImageView) findViewById(R.id.login_newpassword_im);
        newpassword_im.setImageResource(R.mipmap.ic_lock_white);
        //登录注册界面的输入框控件
        username_et= (EditText) findViewById(R.id.login_username_et);
        password_et= (EditText) findViewById(R.id.login_password_et);
        newpasword_et= (EditText) findViewById(R.id.login_newpassword_et);
        //登录注册界面的按钮控件
        login_but= (Button) findViewById(R.id.login_button);
        login_but.setText("注册");
        pb_gone= (RelativeLayout) findViewById(R.id.login_gone_pb);
        //登录注册界面的快速注册控件
        reg_tv= (TextView) findViewById(R.id.login_reg_tv);
        reg_tv.setVisibility(View.GONE);
    }
    boolean username,password,newpassword;
    void inText() {
        /**
         * 用户
         */
        username_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    username_im.setImageResource(R.mipmap.ic_person_accent);
                    password_im.setImageResource(R.mipmap.ic_lock_white);
                    newpassword_im.setImageResource(R.mipmap.ic_lock_white);
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
                    if(password&&newpassword){
                        login_but.setEnabled(true);
                        login_but.setBackgroundResource(R.color.toolbar);
                    }
                }else {
                    login_but.setEnabled(false);
                }
            }
        });

        /**
         * 密码
         */
        password_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    password_im.setImageResource(R.mipmap.ic_lock_accent);
                    username_im.setImageResource(R.mipmap.ic_person_white);
                    newpassword_im.setImageResource(R.mipmap.ic_lock_white);
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
                    if(username&&newpassword){
                        login_but.setEnabled(true);
                        login_but.setBackgroundResource(R.color.toolbar);
                    }
                }else {
                    login_but.setEnabled(false);
                }
            }
        });

        /**
         * 确认密码
         */
        newpasword_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    password_im.setImageResource(R.mipmap.ic_lock_white);
                    username_im.setImageResource(R.mipmap.ic_person_white);
                    newpassword_im.setImageResource(R.mipmap.ic_lock_accent);
                }
            }
        });
        newpasword_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()==0){
                    newpassword=false;
                    login_but.setBackgroundResource(R.color.toolbar_gray);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    newpassword=true;
                    if(username&&password){
                        login_but.setEnabled(true);
                        login_but.setBackgroundResource(R.color.toolbar);
                    }
                }else {
                    login_but.setEnabled(false);
                }
            }
        });
        login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_gone.setVisibility(View.VISIBLE);
                onIcick();
            }
        });
    }
}
