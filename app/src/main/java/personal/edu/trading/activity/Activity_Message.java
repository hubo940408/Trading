package personal.edu.trading.activity;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import personal.edu.trading.commit.Local_preservation;
import personal.edu.trading.PicWindow;
import personal.edu.trading.R;
import personal.edu.trading.SharedPf;
import personal.edu.trading.commit.AvatarLoadOptions;
import personal.edu.trading.http.EasyShopApi;
import personal.edu.trading.http.EasyShopClient;
import personal.edu.trading.user.User;

/**
 * 个人信息界面
 * Created by Administrator on 2017/2/10 0010.
 */
public class Activity_Message extends AppCompatActivity implements View.OnClickListener{
    TextView return_tv;
    TextView username,nikename,hxid;
    CircleImageView im;
    PicWindow picWindow;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Log.i("mmm","创建");
        return_tv= (TextView) findViewById(R.id.message_return_tv);
        username= (TextView) findViewById(R.id.message_username_tv);
        nikename= (TextView) findViewById(R.id.message_nichenname_tv);
        im= (CircleImageView) findViewById(R.id.me_fragment_photo);
        toolbar= (Toolbar) findViewById(R.id.message_toolbar);
        hxid= (TextView) findViewById(R.id.message_nameID_tv);
        updataAvatar(Local_preservation.getUser().getOther());
        Log.i("msg","进入界面加载头像"+Local_preservation.getUser().getOther());
        inClick();
    }

    void inClick(){
        toolbar.setTitle("个人信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        username.setText(Local_preservation.getUser().getUsername());
        nikename.setText(Local_preservation.getUser().getNickname());
        hxid.setText(Local_preservation.getUser().getUuid());
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
                //头像来源选择（相册，拍照）
                //如果为空，创建实例（实现监听）
                if(picWindow==null){
                    picWindow=new PicWindow(this,listener);
                }
                //如果已经显示，则关闭
                if (picWindow.isShowing()){
                    picWindow.dismiss();
                    return;
                }
                picWindow.show();//显示来源选择框
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

    private PicWindow.Listener listener=new PicWindow.Listener() {
        @Override
        public void toGallery() {
            //从相册中选择
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
            startActivityForResult(intent,CropHelper.REQUEST_CROP);
        }

        @Override
        public void toGamera() {
            //从相机中选择
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
            startActivityForResult(intent,CropHelper.REQUEST_CAMERA);
        }
    };

    //图片裁剪的handler
    private CropHandler cropHandler=new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
            //通过uri拿到图片文件
            File file = new File(uri.getPath());
            //业务类上传头像
            updataAvatarim(file);
        }


        @Override
        public void onCropCancel() {

        }

        @Override
        public void onCropFailed(String message) {

        }

        @Override
        public CropParams getCropParams() {
            //自定义裁剪大小参数
            CropParams cropParams = new CropParams();
            cropParams.aspectX = 400;
            cropParams.aspectY = 400;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            return Activity_Message.this;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //帮助我们去处理结果（裁剪完的图像）
        CropHelper.handleResult(cropHandler,requestCode,resultCode,data);
    }



    void updataAvatarim(File file){
        Call call=EasyShopClient.getInstance().uploadAvatar(file);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody=response.body();
                String json=responseBody.string();
                User user=new Gson().fromJson(json,User.class);
                if(user==null){
                    Toast.makeText(Activity_Message.this,"未知错误",Toast.LENGTH_SHORT).show();

                }else if(user.getCode()!=1){
                    Toast.makeText(Activity_Message.this,user.getMsg(),Toast.LENGTH_SHORT).show();

                }else if(user.getCode()==1){
                    dataBean=user.getData();
                    Local_preservation.clearAllData();
                    Local_preservation.setUser(dataBean);
                    handler.sendEmptyMessage(0);
                }

            }
        });
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    updataAvatar(dataBean.getOther());
                    break;
            }
        }
    };


    User.DataBean dataBean;
    public void updataAvatar(String url) {
        Log.i("msg","根据变换图片"+url);
        //头像加载操作
        ImageLoader.getInstance()
                //参数，“头像路径（服务器）”，“头像显示的控件”,“加载选项”
                .displayImage(EasyShopApi.IMAGE_URL + url,im,
                        AvatarLoadOptions.build());
    }
}
