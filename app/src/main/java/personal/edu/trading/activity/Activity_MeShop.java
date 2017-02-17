package personal.edu.trading.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import personal.edu.trading.R;
import personal.edu.trading.SharedPf;
import personal.edu.trading.adapter.GoodsAdapter;
import personal.edu.trading.commit.Local_preservation;
import personal.edu.trading.http.EasyShopClient;
import personal.edu.trading.user.GoodsShop;

/**
 * Created by Administrator on 2017/2/7 0007.
 */
public class Activity_MeShop extends AppCompatActivity {

    @BindView(R.id.meshop_toolbar)
    Toolbar toolbar;
    @BindView(R.id.meshop_recyclerview)
    RecyclerView meshopRecyclerview;
    @BindView(R.id.meshop_ptrclassic)
    PtrClassicFrameLayout meshopPtrclassic;

    Call call;
    GoodsAdapter ga;
    @BindView(R.id.meshop_noshop)
    TextView meshopNoshop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meshop);
        ButterKnife.bind(this);
        inClick();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void inData(int pageNo, String type, String master) {
        call = EasyShopClient.getInstance().getPersonData(pageNo, type, master);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String json = responseBody.string();
                GoodsShop goodsShop = new Gson().fromJson(json, GoodsShop.class);
                switch (goodsShop.getCode()) {
                    case 1:
                        list = new ArrayList<GoodsShop.DatasBean>();
                        list = (ArrayList<GoodsShop.DatasBean>) goodsShop.getDatas();
                        if (list.size() == 0) {

                            handler.sendEmptyMessage(2);
                            return;
                        }
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
                    meshopPtrclassic.refreshComplete();
                    meshopNoshop.setVisibility(View.GONE);
                    ga.setList(list);
                    meshopRecyclerview.setAdapter(ga);
                    ga.notifyDataSetChanged();
                    break;
                case 2:
                    meshopPtrclassic.refreshComplete();
                    meshopNoshop.setVisibility(View.VISIBLE);
                    break;

            }
        }
    };
    String type = "";
    ArrayList<GoodsShop.DatasBean> list;
    int pageno = 1;

    void inClick() {
        toolbar.setTitle("我的商品");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                2, GridLayoutManager.VERTICAL, false);
        meshopRecyclerview.setLayoutManager(gridLayoutManager);//设置布局管理器
        ga = new GoodsAdapter(this);
        inData(pageno, type, Local_preservation.getUser().getUsername());
        ga.setListener(new GoodsAdapter.onItemClickListener() {
            @Override
            public void onItemClicked(GoodsShop.DatasBean goodsInfo) {
                Intent intent = new Intent(Activity_MeShop.this, Activity_Details.class);
                SharedPf.setActivity(Activity_MeShop.this, 1);
                intent.putExtra("uuid", goodsInfo.getUuid());
                startActivity(intent);
            }
        });

        meshopPtrclassic.setLastUpdateTimeRelateObject(this);
        meshopPtrclassic.setBackgroundResource(R.color.toolbar_text);
        meshopPtrclassic.setDurationToCloseHeader(1500);
        meshopPtrclassic.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                pageno++;
                inData(pageno, "", Local_preservation.getUser().getUsername());
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageno = 1;
                inData(pageno, "", Local_preservation.getUser().getUsername());
            }
        });
    }

    /**
     * 加载菜单栏
     *
     * @param menu 菜单
     * @return 返回值
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.me_shop_menu, menu);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        inData(pageno, type, Local_preservation.getUser().getUsername());
    }
}
