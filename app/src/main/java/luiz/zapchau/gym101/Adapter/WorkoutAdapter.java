package luiz.zapchau.gym101.Adapter;

import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import luiz.zapchau.gym101.Model.Workout;
import luiz.zapchau.gym101.R;

public class WorkoutAdapter extends ArrayAdapter<Workout> {

    public WorkoutAdapter(Context mContext, ArrayList<Workout> shows){
        super(mContext, 0, shows);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Workout workout = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_workouts, parent, false);

        TextView     tvWorkoutId           = convertView.findViewById(R.id.tvWorkoutId);
        TextView     tvExerciseId          = convertView.findViewById(R.id.tvWorkoutExerciseId);
        TextView     tvMachineId           = convertView.findViewById(R.id.tvWorkoutMachineId);
        TextView     tvWorkoutSetsTime     = convertView.findViewById(R.id.tvWorkoutSetsTime);
        TextView     tvRepetitionsDistance = convertView.findViewById(R.id.tvWorkoutRepetitionsDistance);
        TextView     tvWeightSpeed         = convertView.findViewById(R.id.tvWorkoutWeightSpeed);
        TextView     tvMachineName         = convertView.findViewById(R.id.tvWorkoutMachineName);
        TextView     tvMachineNumber       = convertView.findViewById(R.id.tvWorkoutMachineNumber);
        TextView     tvUnit                = convertView.findViewById(R.id.tvWorkoutUnitWeightSpeed);
        TextView     tvDate                = convertView.findViewById(R.id.tvWorkoutDate);
        LinearLayout llDate                = convertView.findViewById(R.id.llDate);
        Date         lastDate                  = new Date();

        assert workout != null;
        tvWorkoutId      .setText(workout.id);
        tvExerciseId     .setText(workout.exerciseId);
        tvMachineId      .setText(workout.machineId);
        tvMachineName    .setText(workout.machineName);
        tvMachineNumber  .setText(workout.machineNumber);
        tvWeightSpeed    .setText(workout.weight);
        tvDate           .setText(workout.date);

        //todo compare dates then set llDate visibility to gone if the date is the same as the last item

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

        return convertView;
    }
}
