package luiz.zapchau.gym101.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import luiz.zapchau.gym101.R;

public class SharedPreferencesHelper {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;

    private SharedPreferences mSharedPreferences(Context mContext){
        return mContext.getSharedPreferences(mContext.getResources().getString(R.string.sp_name), Context.MODE_PRIVATE);
    }

    public void spSetInt(Context mContext, String key, int value){
        sharedPreferences = mSharedPreferences(mContext);
        spEditor = sharedPreferences.edit();
        spEditor.putInt(key, value);
        spEditor.apply();
    }

    public void spSetString(Context mContext, String key, String value){
        sharedPreferences = mSharedPreferences(mContext);
        spEditor = sharedPreferences.edit();
        spEditor.putString(key, value);
        spEditor.apply();
    }

    public int spGetInt(Context mContext, String key, int defaultValue){
        sharedPreferences = mSharedPreferences(mContext);
        return sharedPreferences.getInt(key, defaultValue);
    }

    public String spGetString(Context mContext, String key, String defaultValue){
        sharedPreferences = mSharedPreferences(mContext);
        return sharedPreferences.getString(key, defaultValue);
    }

    public void spRemoveValue(Context mContext, String key){
        sharedPreferences = mSharedPreferences(mContext);
        spEditor = sharedPreferences.edit();
        spEditor.remove(key);
        spEditor.apply();
    }
}
