package personal.edu.trading.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import personal.edu.trading.Interface.Interface_A;
import personal.edu.trading.commit.Local_preservation;
import personal.edu.trading.R;
import personal.edu.trading.fragment.Fragment_Book;
import personal.edu.trading.fragment.Fragment_Market;
import personal.edu.trading.fragment.Fragment_Me;
import personal.edu.trading.fragment.Fragment_News;

/**
 * 主页面
 */
public class MainActivity extends AppCompatActivity{

    Fragment fragment_me;
    ViewPager main_fragment;
    Toolbar toolbar;
    TextView toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment_me =new Fragment_Me();

        inCiclk();

    }

    BottomNavigationBar navigationBar;

    /**
     * viewpager适配器
     */
    private FragmentStatePagerAdapter pagerAdapter=new
            FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    //viewpager上加载的碎片
                    return new Fragment_Market();
                case 1:
                    return new Fragment_News();
                case 2:
                    return new Fragment_Book();
                case 3:

                    return fragment_me;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    };


    void inCiclk() {
        main_fragment = (ViewPager) findViewById(R.id.main_fragment);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar_title = (TextView) findViewById(R.id.main_toolbar_title);

        //底部导航栏加载
        navigationBar = (BottomNavigationBar) findViewById(R.id.main_bottombar);
        navigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        navigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        navigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_shopping_one, "市场")
                .setActiveColorResource(R.color.toolbar))
                .addItem(new BottomNavigationItem(R.mipmap.ic_chat_one, "消息")
                        .setActiveColorResource(R.color.bottombar_blue))
                .addItem(new BottomNavigationItem(R.mipmap.ic_people_one, "通讯录")
                        .setActiveColorResource(R.color.bottombar_green))
                .addItem(new BottomNavigationItem(R.mipmap.ic_perm_identity_one, "我的")
                        .setActiveColorResource(R.color.bottombar_rose))
                .setFirstSelectedPosition(0).initialise();
        main_fragment.setOffscreenPageLimit(3);//加载数
        //导航栏点击监听
        navigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

                main_fragment.setCurrentItem(position);
                if(position==0){
                    toolbar_title.setText("市场");
                }else if(position==1){
                    toolbar_title.setText("消息");
                }else if(position==2){
                    toolbar_title.setText("通讯录");
                }else if(position==3){
                    toolbar_title.setText("我的");
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

        main_fragment.setAdapter(pagerAdapter);

        toolbar.setTitle("");
        toolbar_title.setText("市场");
        setSupportActionBar(toolbar);

        main_fragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigationBar.selectTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Interface_A interface_a= (Interface_A) fragment_me;
        interface_a.send();
        interface_a.sengim(Local_preservation.getUser().getOther());
    }
}
