package luiz.zapchau.gym101.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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

        TextView tvWorkoutId          = convertView.findViewById(R.id.tvWorkoutId);
        TextView tvWorkoutDate        = convertView.findViewById(R.id.tvWorkoutDate);
        TextView tvWorkoutExercise    = convertView.findViewById(R.id.tvWorkoutExercise);
        TextView tvWorkoutSets        = convertView.findViewById(R.id.tvWorkoutSets);
        TextView tvWorkoutRepetitions = convertView.findViewById(R.id.tvWorkoutRepetitions);
        TextView tvWorkoutWeight      = convertView.findViewById(R.id.tvWorkoutWeight);
        TextView tvWorkoutTime        = convertView.findViewById(R.id.tvWorkoutTime);
        TextView tvWorkoutDistance    = convertView.findViewById(R.id.tvWorkoutDistance);

        assert workout != null;
        tvWorkoutId         .setText(workout.id);
        tvWorkoutDate       .setText(workout.date);
        tvWorkoutExercise   .setText(workout.exercise);
        tvWorkoutSets       .setText(workout.sets);
        tvWorkoutRepetitions.setText(workout.repetitions);
        tvWorkoutWeight     .setText(workout.weight);
        tvWorkoutTime       .setText(workout.time);
        tvWorkoutDistance   .setText(workout.distance);

        return convertView;
    }
}
