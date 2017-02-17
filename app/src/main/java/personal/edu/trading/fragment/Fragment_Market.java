package personal.edu.trading.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import personal.edu.trading.R;
import personal.edu.trading.SharedPf;
import personal.edu.trading.activity.Activity_Details;
import personal.edu.trading.adapter.GoodsAdapter;
import personal.edu.trading.http.EasyShopClient;
import personal.edu.trading.user.GoodsShop;

/**
 * 市场
 * Created by Administrator on 2017/2/5 0005.
 */
public class Fragment_Market extends Fragment {
    ArrayList<GoodsShop.DatasBean> alllist;
    ArrayList<GoodsShop.DatasBean> list;
    @BindView(R.id.market_recyclerView)
    RecyclerView marketRecyclerView;

    @BindView(R.id.market_noshop)
    TextView marketNoshop;
    Unbinder unbinder;
    GoodsAdapter ga;
    @BindView(R.id.market_loading)
    RelativeLayout marketLoading;
    int num;
    @BindView(R.id.market_failure_tv)
    TextView marketFailureTv;
    @BindView(R.id.market_pb)
    ProgressBar marketPb;
    @BindView(R.id.market_ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),
                2, GridLayoutManager.VERTICAL, false);
        marketRecyclerView.setLayoutManager(gridLayoutManager);//设置布局管理器
        marketNoshop.setVisibility(View.GONE);
        ga = new GoodsAdapter(getContext());
        alllist = new ArrayList<>();
        inDataAll("");
        inClick();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            marketPb.setVisibility(View.GONE);
            switch (msg.what) {
                case 1:
                    num=1;
                    ptrClassicFrameLayout.refreshComplete();
                    if (list.size() == 0) {
                        marketNoshop.setVisibility(View.VISIBLE);
                    } else {

                        alllist.clear();
                        alllist = list;
                        marketNoshop.setVisibility(View.GONE);
                        ga.setList(list);
                        marketRecyclerView.setAdapter(ga);
                        ga.notifyDataSetChanged();
                    }
                    break;
                case 2:
                    ptrClassicFrameLayout.refreshComplete();

                    for (GoodsShop.DatasBean data : list) {
                        alllist.add(data);
                    }
                    marketLoading.setVisibility(View.GONE);
                    ga.setList(alllist);
                    marketRecyclerView.setAdapter(ga);
                    ga.notifyDataSetChanged();
                    break;
                case 3:
                    marketFailureTv.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    void inClick() {
        //使用本对象作为key，用来记录上一次刷新的事件，如果两次下拉刷新间隔太近，不会触发刷新方法
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        //设置刷新时显示的背景色
        ptrClassicFrameLayout.setBackgroundResource(R.color.toolbar_text);
        ptrClassicFrameLayout.setDurationToCloseHeader(1500);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            //加载更多时触发
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                marketLoading.setVisibility(View.VISIBLE);
                inDataLoading("");
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                inDataAll("");
            }
        });



        ga.setListener(new GoodsAdapter.onItemClickListener() {
            @Override
            public void onItemClicked(GoodsShop.DatasBean goodsInfo) {
                Intent intent = new Intent(getContext(), Activity_Details.class);
                SharedPf.setActivity(getContext(), 0);
                intent.putExtra("uuid", goodsInfo.getUuid());
                startActivity(intent);
            }
        });
    }

    Call call;


    public void inDataLoading(String type) {
        num += 1;
        call = EasyShopClient.getInstance().getGoods(num, type);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody res = response.body();
                String json = res.string();
                GoodsShop goods = new Gson().fromJson(json, GoodsShop.class);
                switch (goods.getCode()) {
                    case 1:
                        list = (ArrayList<GoodsShop.DatasBean>) goods.getDatas();
                        handler.sendEmptyMessage(2);
                        break;
                    case 2:
                        break;
                }
            }
        });
    }

    public void inDataAll(String type) {
        list = new ArrayList<GoodsShop.DatasBean>();

        call = EasyShopClient.getInstance().getGoods(1, type);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(3);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                ResponseBody responseBody = response.body();
                String json = responseBody.string();

                GoodsShop goodsShop = new Gson().fromJson(json, GoodsShop.class);
                switch (goodsShop.getCode()) {
                    case 1:

                        list = (ArrayList<GoodsShop.DatasBean>) goodsShop.getDatas();
                        handler.sendEmptyMessage(1);

                        break;
                    case 2:
                        Toast.makeText(getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
