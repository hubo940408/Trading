package personal.edu.trading.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import personal.edu.trading.Interface.Interface_A;
import personal.edu.trading.Local_preservation;
import personal.edu.trading.R;
import personal.edu.trading.SharedPf;
import personal.edu.trading.activity.Activity_Login;
import personal.edu.trading.activity.Activity_MeShop;
import personal.edu.trading.activity.Activity_Message;
import personal.edu.trading.activity.Activity_Upload;

/**
 * 我的
 * Created by Administrator on 2017/2/5 0005.
 */
public class Fragment_Me extends Fragment implements Interface_A{
    ImageView im;
    TextView name_tv;
    RelativeLayout onemessage,meshop,uploadshop;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        im= (ImageView) view.findViewById(R.id.me_fragment_photo);
        name_tv= (TextView) view.findViewById(R.id.me_fragment_name);
        onemessage= (RelativeLayout) view.findViewById(R.id.me_onemessage);
        meshop= (RelativeLayout) view.findViewById(R.id.me_meshop);
        uploadshop= (RelativeLayout) view.findViewById(R.id.me_uploadshop);
        inClick();
    }
    Intent intent;
    boolean Login;
    void inClick(){
        if (!SharedPf.getLogin(getContext())){
            name_tv.setText("登录|注册");
        }else if(SharedPf.getLogin(getContext())){
            name_tv.setText(Local_preservation.getUser().getNickname());
//            im.setImageResource(Integer.parseInt(Local_preservation.getUser().getOther()));
        }
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login= SharedPf.getLogin(getContext());
                if(!Login){
                    intent=new Intent(getContext(), Activity_Login.class);
                }else {
                    intent=new Intent(getContext(), Activity_Message.class);
                }
                startActivity(intent);
            }
        });

        name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login= SharedPf.getLogin(getContext());
                if(!Login){
                    intent=new Intent(getContext(), Activity_Login.class);
                }else {
                    intent=new Intent(getContext(), Activity_Message.class);
                }
                startActivity(intent);
            }
        });
        onemessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login= SharedPf.getLogin(getContext());
                if(!Login){
                    intent=new Intent(getContext(), Activity_Login.class);
                }else {
                    intent=new Intent(getContext(), Activity_Message.class);
                }
                startActivity(intent);
            }
        });

        meshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login= SharedPf.getLogin(getContext());
                if(!Login){
                    intent=new Intent(getContext(), Activity_Login.class);
                }else {
                    intent=new Intent(getContext(), Activity_MeShop.class);
                }
                startActivity(intent);
            }
        });

        uploadshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login= SharedPf.getLogin(getContext());
                if(!Login){
                    intent=new Intent(getContext(), Activity_Login.class);
                }else {
                    intent=new Intent(getContext(), Activity_Upload.class);
                }
                startActivity(intent);
            }
        });
    }


    @Override
    public void send() {
        if (!SharedPf.getLogin(getContext())){
            name_tv.setText("登录|注册");
        }else if(SharedPf.getLogin(getContext())){
            name_tv.setText(Local_preservation.getUser().getNickname());
        }
    }
}
