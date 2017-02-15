package personal.edu.trading;

import android.content.Context;
import android.content.SharedPreferences;

import personal.edu.trading.user.User;

/**
 * Created by Administrator on 2017/2/14 0014.
 */
public class Local_preservation {
    private static final String NAME =Local_preservation.class.getSimpleName();
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_PWD = "userPwd";
    private static final String KEY_USER_HX_ID = "userHxID";
    private static final String KEY_USER_TABLE_ID = "userUuid";
    private static final String KEY_USER_HEAD_IMAGE = "userHeadImage";
    private static final String KEY_USER_NICKNAME = "userNickName";

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public Local_preservation() {
    }
    public static void init(Context context){
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static void clearAllData() {
        editor.clear();
        editor.apply();
    }

    public static void setUser(User.DataBean user) {
        editor.putString(KEY_USER_NAME, user.getUsername());
        editor.putString(KEY_USER_PWD, user.getPassword());
        editor.putString(KEY_USER_HX_ID, user.getUuid());
        editor.putString(KEY_USER_TABLE_ID, user.getName());
        editor.putString(KEY_USER_HEAD_IMAGE, user.getOther());
        editor.putString(KEY_USER_NICKNAME, user.getNickname());

        editor.apply();
    }

    public static User.DataBean getUser() {
        User.DataBean user = new User.DataBean();
        user.setUsername(preferences.getString(KEY_USER_NAME, null));
        user.setPassword(preferences.getString(KEY_USER_PWD, null));
        user.setUuid(preferences.getString(KEY_USER_HX_ID, null));
        user.setName(preferences.getString(KEY_USER_TABLE_ID, null));
        user.setOther(preferences.getString(KEY_USER_HEAD_IMAGE, null));
        user.setNickname(preferences.getString(KEY_USER_NICKNAME, null));
        return user;
    }

}
