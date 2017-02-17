package personal.edu.trading.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import personal.edu.trading.R;
import personal.edu.trading.commit.AvatarLoadOptions;
import personal.edu.trading.http.EasyShopApi;
import personal.edu.trading.user.GoodsShop;

/**
 * Created by Administrator on 2017/2/16 0016.
 */
public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsViewHolde> {

    private Context context;
    private ArrayList<GoodsShop.DatasBean> list;

    public GoodsAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public ArrayList<GoodsShop.DatasBean> getList() {
        return list;
    }

    public void setList(ArrayList<GoodsShop.DatasBean> list) {
        this.list = list;
    }

    @Override
    public GoodsViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop, null);
        GoodsViewHolde goodsViewHolde = new GoodsViewHolde(view);
        return goodsViewHolde;
    }

    @Override
    public void onBindViewHolder(GoodsViewHolde holder, final int position) {
        holder.itemshopNameTv.setText(list.get(position).getName());
        holder.itemshopPriceTv.setText(list.get(position).getPrice()+"¥");
        ImageLoader.getInstance().displayImage(EasyShopApi.IMAGE_URL+
                list.get(position).getPage(),holder.itemshopIm, AvatarLoadOptions.build_item());
        holder.itemshopIm.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.itemshopIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemClicked(list.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() ;
    }

    class GoodsViewHolde extends RecyclerView.ViewHolder {
        @BindView(R.id.itemshop_im)
        ImageView itemshopIm;
        @BindView(R.id.itemshop_name_tv)
        TextView itemshopNameTv;
        @BindView(R.id.itemshop_price_tv)
        TextView itemshopPriceTv;

        public GoodsViewHolde(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface onItemClickListener{
        //点击商品item，把商品信息传进来
        void onItemClicked(GoodsShop.DatasBean goodsInfo);
    }

    private onItemClickListener listener;

    //对外提供监听方法
    public void setListener(onItemClickListener listener){
        this.listener = listener;
    }
}
