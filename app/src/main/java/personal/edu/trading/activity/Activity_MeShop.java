package personal.edu.trading.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import personal.edu.trading.R;

/**
 * Created by Administrator on 2017/2/7 0007.
 */
public class Activity_MeShop extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meshop);
        inClick();
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

    void inClick(){
        toolbar= (Toolbar) findViewById(R.id.meshop_toolbar);
        toolbar.setTitle("我的商品");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /**
     * 加载菜单栏
     * @param menu 菜单
     * @return 返回值
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.me_shop_menu,menu);
        return true;
    }


}
