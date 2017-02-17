package personal.edu.trading.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import personal.edu.trading.R;
import personal.edu.trading.SharedPf;
import personal.edu.trading.adapter.DetailsAdapter;
import personal.edu.trading.commit.AvatarLoadOptions;
import personal.edu.trading.http.EasyShopApi;
import personal.edu.trading.http.EasyShopClient;
import personal.edu.trading.user.DeleteShop;
import personal.edu.trading.user.Detail_User;

/**
 * 商品详情界面
 * Created by Administrator on 2017/2/16 0016.
 */
public class Activity_Details extends AppCompatActivity {
    @BindView(R.id.detail_toolbar)
    Toolbar detailToolbar;
    @BindView(R.id.detail_viewpager)
    ViewPager detailViewpager;
    @BindView(R.id.detail_name)
    TextView detailName;
    @BindView(R.id.detail_price)
    TextView detailPrice;
    @BindView(R.id.detail_people)
    TextView detailPeople;
    @BindView(R.id.detail_content)
    TextView detailContent;
    @BindView(R.id.detail_delete)
    TextView detailDelete;
    @BindView(R.id.detail_send_message)
    Button detailSendMessage;
    @BindView(R.id.detail_Contact_her)
    Button detailContactHer;
    @BindView(R.id.detai_gone)
    LinearLayout detaiGone;
    ArrayList<ImageView> imlist;
    DetailsAdapter da;
    @BindView(R.id.indicator)
    CircleIndicator indicator;

    /*使用开源库CircleIndicator来实现ViewPager的圆点指示器。*/

    private ArrayList<String> list_uri;//存放图片路径的集合

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        imlist = new ArrayList<>();
        da = new DetailsAdapter(this);
        inClick();
    }
    String uuid;
    AlertDialog.Builder builder;
    void inClick() {
        if (SharedPf.getActivity(this) == 0) {
            detailDelete.setVisibility(View.GONE);
            detailToolbar.setTitle("商品详情");
        } else {
            detailToolbar.setTitle("我的商品");
            detaiGone.setVisibility(View.GONE);
        }
        setSupportActionBar(detailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        uuid= intent.getStringExtra("uuid");
        getData(uuid);
        detailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder=new AlertDialog.Builder(Activity_Details.this);
                builder.setTitle("警告");
                builder.setMessage("是否确认删除该商品？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //执行删除方法
                        call=EasyShopClient.getInstance().deleteGoods(uuid);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                ResponseBody responseBody=response.body();
                                String json=responseBody.string();
                                DeleteShop deleteShop=new Gson().fromJson(json,DeleteShop.class);
                                switch (deleteShop.getCode()){
                                    case 1:
                                        handler.sendEmptyMessage(3);
                                        break;
                                    case 2:
                                        handler.sendEmptyMessage(4);
                                        break;
                                }
                            }
                        });
                    }
                });
                //设置取消按钮
                builder.setNegativeButton("取消",null);
                builder.create().show();
            }
        });
    }

    Call call;
    Detail_User.DatasBean datasBean;

    void getData(String uuid) {
        call = EasyShopClient.getInstance().getGoodsData(uuid);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String json = responseBody.string();
                Detail_User detail_user = new Gson().fromJson(json, Detail_User.class);
                switch (detail_user.getCode()) {
                    case 1:
                        datasBean = detail_user.getDatas();

                        handler.sendEmptyMessage(1);
                        break;
                    case 2:
                        break;
                }
            }
        });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    detailName.setText(datasBean.getName());
                    detailPrice.setText(datasBean.getPrice() + " ¥");
                    detailPeople.setText("发布者："+datasBean.getMaster());
                    detailContent.setText(datasBean.getDescription());
                    list_uri = new ArrayList<String>();
                    for (int i = 0; i < datasBean.getPages().size(); i++) {
                        String page = datasBean.getPages().get(i).getUri();
                        list_uri.add(page);
                    }
                    //加载图片
                    for (int i = 0; i < list_uri.size(); i++) {
                        ImageView imageView = new ImageView(Activity_Details.this);
                        ImageLoader.getInstance()
                                .displayImage(EasyShopApi.IMAGE_URL + list_uri.get(i)
                                        , imageView, AvatarLoadOptions.build_item());
                        //添加到imageView的集合中
                        imlist.add(imageView);
                    }
                    da.setList(imlist);
                    detailViewpager.setAdapter(da);
                    da.notifyDataSetChanged();
                    //确认图片数量后，创建圆点指示器
                    indicator.setViewPager(detailViewpager);
                    break;
                case 3:
                    Toast.makeText(Activity_Details.this,"删除成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case 4:
                    builder.create().show();
                    break;
            }
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
