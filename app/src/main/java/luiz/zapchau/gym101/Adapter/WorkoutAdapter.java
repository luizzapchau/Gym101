package luiz.zapchau.gym101.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import luiz.zapchau.gym101.Helper.SharedPreferencesHelper;
import luiz.zapchau.gym101.Model.Workout;
import luiz.zapchau.gym101.R;

public class WorkoutAdapter extends ArrayAdapter<Workout> {

    public WorkoutAdapter(Context mContext, ArrayList<Workout> shows){
        super(mContext, 0, shows);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Workout workout = getItem(position);
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_workouts, parent, false);

        TextView     tvWorkoutId           = convertView.findViewById(R.id.tvWorkoutId);
        TextView     tvExerciseId          = convertView.findViewById(R.id.tvWorkoutExerciseId);
        TextView     tvMachineId           = convertView.findViewById(R.id.tvWorkoutMachineId);
        TextView     tvWorkoutSetsTime     = convertView.findViewById(R.id.tvWorkoutSetsTime);
        TextView     tvRepetitionsDistance = convertView.findViewById(R.id.tvWorkoutRepetitionsDistance);
        TextView     tvWeightSpeed         = convertView.findViewById(R.id.tvWorkoutWeightSpeed);
        TextView     tvMachineName         = convertView.findViewById(R.id.tvExerciseMachineName);
        TextView     tvMachineNumber       = convertView.findViewById(R.id.tvExerciseMachineNumber);
        TextView     tvUnit                = convertView.findViewById(R.id.tvWorkoutUnitWeightSpeed);
        TextView     tvDate                = convertView.findViewById(R.id.tvWorkoutDate);
        TextView     tvDayA                = convertView.findViewById(R.id.tvWorkoutDayA);
        TextView     tvDayB                = convertView.findViewById(R.id.tvWorkoutDayB);
        TextView     tvDayC                = convertView.findViewById(R.id.tvWorkoutDayC);
        TextView     tvDayD                = convertView.findViewById(R.id.tvWorkoutDayD);
        LinearLayout llDate                = convertView.findViewById(R.id.llDate);
        String       lastDate              = sharedPreferencesHelper.spGetString(getContext(), getContext().getString(R.string.sp_default_date), getContext().getResources().getString(R.string.default_date));

        assert workout != null;
        tvWorkoutId  .setText(workout.id);
        tvExerciseId .setText(workout.exerciseId);
        tvMachineId  .setText(workout.machineId);
        tvMachineName.setText(workout.machineName);

        if (Integer.parseInt(workout.machineNumber) != -1) {
            tvMachineNumber.setText(workout.machineNumber);
        } else {
            tvMachineNumber.setText(null);
        }

        if (workout.date.equals(formatDate(new Date())))
            workout.date = getContext().getResources().getString(R.string.today);

        if (lastDate.equals(workout.date)) {
            llDate.setVisibility(View.GONE);
        } else {
            tvDate                 .setText      (workout.date);
            llDate                 .setVisibility(View.VISIBLE);
            sharedPreferencesHelper.spSetString  (getContext(), getContext().getString(R.string.sp_default_date), workout.date);
        }

        if (Integer.parseInt(workout.sets) != -1) {
            tvWorkoutSetsTime    .setText(workout.sets);
            tvRepetitionsDistance.setText(workout.repetitions);
            tvWeightSpeed        .setText(workout.weight);
            tvUnit               .setText(getContext().getString(R.string.kg));
        } else {
            tvWorkoutSetsTime    .setText(workout.time);
            tvRepetitionsDistance.setText(workout.distance + getContext().getString(R.string.km));
            tvWeightSpeed        .setText(workout.speed);
            tvUnit               .setText(getContext().getString(R.string.km_h));
        }

        if (workout.machineColor.equals(getContext().getString(R.string.blue).toLowerCase())) {
            tvMachineNumber.setBackgroundColor(getContext().getColor(R.color.colorBlue));

        } else if (workout.machineColor.equals(getContext().getString(R.string.green).toLowerCase())) {
            tvMachineNumber.setBackgroundColor(getContext().getColor(R.color.colorGreen));

        } else if (workout.machineColor.equals(getContext().getString(R.string.orange).toLowerCase())) {
            tvMachineNumber.setBackgroundColor(getContext().getColor(R.color.colorOrange));

        } else if (workout.machineColor.equals(getContext().getString(R.string.red).toLowerCase())) {
            tvMachineNumber.setBackgroundColor(getContext().getColor(R.color.colorRed));
        }

        if (workout.exerciseDays.contains(getContext().getResources().getString(R.string.a_db))){
            tvDayA.setVisibility(View.VISIBLE);
        } else {
            tvDayA.setVisibility(View.GONE);
        }

        if (workout.exerciseDays.contains(getContext().getResources().getString(R.string.b_db))){
            tvDayB.setVisibility(View.VISIBLE);
        } else {
            tvDayB.setVisibility(View.GONE);
        }

        if (workout.exerciseDays.contains(getContext().getResources().getString(R.string.c_db))){
            tvDayC.setVisibility(View.VISIBLE);
        } else {
            tvDayC.setVisibility(View.GONE);
        }

        if (workout.exerciseDays.contains(getContext().getResources().getString(R.string.d_db))){
            tvDayD.setVisibility(View.VISIBLE);
        } else {
            tvDayD.setVisibility(View.GONE);
        }

        return convertView;
    }

    private String formatDate(Date mDate) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("EEEE, MMMM dd yyyy");

        return mSimpleDateFormat.format(mDate);
    }
}
