package personal.edu.trading.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import personal.edu.trading.Local_preservation;
import personal.edu.trading.R;
import personal.edu.trading.SharedPf;

/**
 * 个人信息界面
 * Created by Administrator on 2017/2/10 0010.
 */
public class Activity_Message extends AppCompatActivity implements View.OnClickListener{
    TextView return_tv;
    TextView username,nikename,hxid;
    CircleImageView im;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        return_tv= (TextView) findViewById(R.id.message_return_tv);
        username= (TextView) findViewById(R.id.message_username_tv);
        nikename= (TextView) findViewById(R.id.message_nichenname_tv);
        im= (CircleImageView) findViewById(R.id.me_fragment_photo);
        hxid= (TextView) findViewById(R.id.message_nameID_tv);
        inClick();
    }
    void inClick(){
        username.setText(Local_preservation.getUser().getUsername());
        nikename.setText(Local_preservation.getUser().getNickname());
        hxid.setText(Local_preservation.getUser().getUuid());



    }

    @Override
    protected void onResume() {
        super.onResume();
        username.setOnClickListener(this);
        im.setOnClickListener(this);
        hxid.setOnClickListener(this);
        nikename.setOnClickListener(this);
        return_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.me_fragment_photo :
                break;
            case R.id.message_username_tv :
                Toast.makeText(this,"账号不可更改",Toast.LENGTH_SHORT).show();
                break;
            case R.id.message_nichenname_tv :
                break;
            case R.id.message_nameID_tv :
                Toast.makeText(this,"环信ID不可更改",Toast.LENGTH_SHORT).show();
                break;
            case R.id.message_return_tv :
                SharedPf.setLogin(Activity_Message.this,false);
                Local_preservation.clearAllData();
                finish();
                break;

        }
    }
}
