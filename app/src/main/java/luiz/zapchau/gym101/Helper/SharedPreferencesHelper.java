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

    public void spSetInt(Context mContext, String key, int valor){
        sharedPreferences = mSharedPreferences(mContext);
        spEditor = sharedPreferences.edit();
        spEditor.putInt(key, valor);
        spEditor.apply();
    }

    public void spSetString(Context mContext, String key, String valor){
        sharedPreferences = mSharedPreferences(mContext);
        spEditor = sharedPreferences.edit();
        spEditor.putString(key, valor);
        spEditor.apply();
    }

    public int spGetInt(Context mContext, String key, int valorDefault){
        sharedPreferences = mSharedPreferences(mContext);
        return sharedPreferences.getInt(key, valorDefault);
    }

    public String spGetString(Context mContext, String key, String valorDefault){
        sharedPreferences = mSharedPreferences(mContext);
        return sharedPreferences.getString(key, valorDefault);
    }

    public void spRemoveValue(Context mContext, String key){
        sharedPreferences = mSharedPreferences(mContext);
        spEditor = sharedPreferences.edit();
        spEditor.remove(key);
        spEditor.apply();
    }
}
