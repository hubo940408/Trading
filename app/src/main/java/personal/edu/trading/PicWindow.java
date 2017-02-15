package personal.edu.trading;


import butterknife.ButterKnife;
import butterknife.OnClick;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 *
 * Created by Administrator on 2017/2/15 0015.
 */
public class PicWindow extends PopupWindow{
    private  Activity activity;
    private Listener listener;


    @OnClick({R.id.btn_popu_album, R.id.btn_popu_camera})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_popu_album:
                Log.i("msg","1111");
                listener.toGallery();
                break;
            case R.id.btn_popu_camera:
                listener.toGamera();
                break;
        }
        dismiss();
    }

    public interface Listener{
        /**
         * 来自相册
         */
        void toGallery();
        /**
         * 来自相机
          */
        void toGamera();
    }


    @SuppressWarnings("all")
    public PicWindow(Activity activity,Listener listener){
        super(activity.getLayoutInflater().inflate(R.layout.layout_popu,null),
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        this.activity=activity;
        this.listener=listener;
        ButterKnife.bind(this, getContentView());
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());

    }

    public void show() {
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }


}
