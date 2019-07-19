package luiz.zapchau.gym101.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import luiz.zapchau.gym101.R;

public class MenuController {

    public boolean onOptionsSelected(MenuItem item, Context mContext) {
        //SharedPreferencesHelper spHelper = new SharedPreferencesHelper();
        Activity activity = (Activity) mContext;
        int id = item.getItemId();

        //todo menu
//        if (id == R.id.mChange) {
//            if (spHelper.spGetInt(activity, activity.getString(R.string.sp), 0) != 0) {
//                activity.startActivity(new Intent(activity, Login.class));
//            } else {
//                activity.startActivity(new Intent(activity, BarcodeScannerActivity.class));
//            }
//            activity.finish();
//            return true;
//        }
//
//        if (id == R.id.mUser) {
//            activity.startActivity(new Intent(activity, User.class));
//        }
//
//        if (id == R.id.mExit) {
//            spHelper.spSetInt(activity, activity.getString(R.string.sp_user_id), 0);
//            spHelper.spSetInt(activity, activity.getString(R.string.sp_codcli), 0);
//            spHelper.spSetInt(activity, activity.getString(R.string.sp_id), 0);
//            activity.startActivity(new Intent(activity, LoginActivity.class));
//            activity.finish();
//            return true;
//        }

        return false;
    }
}
