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
//        if (id == R.id.mTrocarClinica) {
//            if (spHelper.spGetInt(activity, activity.getString(R.string.sp_numero_clinicas_recentes), 0) != 0) {
//                activity.startActivity(new Intent(activity, LoginClinicaActivity.class));
//            } else {
//                activity.startActivity(new Intent(activity, BarcodeScannerActivity.class));
//            }
//            activity.finish();
//            return true;
//        }
//
//        if (id == R.id.mPerfilUsuario) {
//            activity.startActivity(new Intent(activity, PerfilUsuarioActivity.class));
//        }
//
//        if (id == R.id.mSair) {
//            spHelper.spSetInt(activity, activity.getString(R.string.sp_user_id), 0);
//            spHelper.spSetInt(activity, activity.getString(R.string.sp_codclisponte), 0);
//            spHelper.spSetInt(activity, activity.getString(R.string.sp_clinica_id), 0);
//            activity.startActivity(new Intent(activity, LoginActivity.class));
//            activity.finish();
//            return true;
//        }

        return false;
    }
}
