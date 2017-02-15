package personal.edu.trading.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import personal.edu.trading.R;

/**
 * Created by Administrator on 2017/2/7 0007.
 */
public class Activity_Guide extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acyivity_guide);
        inClick();
    }
    void inClick(){
        final Timer timer =new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
                timer.cancel();
            }
        };
        timer.schedule(task,1500);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Intent intent=new Intent(Activity_Guide.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
}
