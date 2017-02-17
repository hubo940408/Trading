package personal.edu.trading;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 共享参数方法界面
 * Created by Administrator on 2017/2/10 0010.
 */
public class SharedPf {
    public static void setLogin(Context context,boolean login){
        SharedPreferences spf=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putBoolean("mean",login);
        ed.commit();
    }
    public static boolean getLogin(Context context){
        SharedPreferences spf=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        return spf.getBoolean("mean",false);
    }

    public static void setActivity(Context context,int login){
        SharedPreferences spf=context.getSharedPreferences("activity",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putInt("mean",login);
        ed.commit();
    }
    public static int getActivity(Context context){
        SharedPreferences spf=context.getSharedPreferences("activity",Context.MODE_PRIVATE);
        return spf.getInt("mean",0);
    }
}
