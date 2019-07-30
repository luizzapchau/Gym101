package luiz.zapchau.gym101.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import luiz.zapchau.gym101.Activities.ExerciseListActivity;
import luiz.zapchau.gym101.Activities.MachineListActivity;
import luiz.zapchau.gym101.R;

public class MenuController {

    public boolean onOptionsSelected(MenuItem item, Context mContext) {
        Activity activity = (Activity) mContext;

        if (item.getItemId() == R.id.mExerciseList) {
            Intent intent = new Intent(mContext, ExerciseListActivity.class);

            intent.putExtra(mContext.getResources().getString(R.string.is_new_workout), false);
            activity.startActivity(intent);

            return true;
        }

        if (item.getItemId() == R.id.mMachineList) {
            activity.startActivity(new Intent(mContext, MachineListActivity.class));

            return true;
        }

        return false;
    }
}
