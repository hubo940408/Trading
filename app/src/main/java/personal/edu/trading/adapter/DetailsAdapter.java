package personal.edu.trading.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/16 0016.
 */
public class DetailsAdapter extends PagerAdapter {
    Context context;
    ArrayList<ImageView>list;

    public DetailsAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public ArrayList<ImageView> getList() {
        return list;
    }

    public void setList(ArrayList<ImageView> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=list.get(position);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClick!=null){
                    onItemClick.onitemClick();
                }
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    OnItemClick onItemClick;

    public DetailsAdapter(DetailsAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void onitemClick();
    }




}
