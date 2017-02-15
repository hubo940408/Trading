package personal.edu.trading.activity;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import personal.edu.trading.Local_preservation;
import personal.edu.trading.PicWindow;
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
    PicWindow picWindow;
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
}
