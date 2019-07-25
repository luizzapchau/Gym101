package luiz.zapchau.gym101.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import luiz.zapchau.gym101.Model.Exercise;
import luiz.zapchau.gym101.R;

public class ExerciseAdapter extends ArrayAdapter<Exercise> {

    public ExerciseAdapter(Context mContext, ArrayList<Exercise> shows){
        super(mContext, 0, shows);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Exercise exercise = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_exercises, parent, false);

        TextView     tvExerciseId    = convertView.findViewById(R.id.tvExerciseId);
        TextView     tvMachineName   = convertView.findViewById(R.id.tvExerciseMachineName);
        TextView     tvMachineNumber = convertView.findViewById(R.id.tvExerciseMachineNumber);
        LinearLayout llDay           = convertView.findViewById(R.id.llDay);

        assert exercise != null;
        tvExerciseId .setText(exercise.id);
        tvMachineName.setText(exercise.machineName);

        if (Integer.parseInt(exercise.machineNumber) != -1) {
            tvMachineNumber.setText(exercise.machineNumber);
        } else {
            tvMachineNumber.setText(null);
        }

        if (exercise.machineColor.equals(getContext().getString(R.string.blue).toLowerCase())) {
            tvMachineNumber.setBackgroundColor(getContext().getColor(R.color.colorBlue));

        } else if (exercise.machineColor.equals(getContext().getString(R.string.green).toLowerCase())) {
            tvMachineNumber.setBackgroundColor(getContext().getColor(R.color.colorGreen));

        } else if (exercise.machineColor.equals(getContext().getString(R.string.orange).toLowerCase())) {
            tvMachineNumber.setBackgroundColor(getContext().getColor(R.color.colorOrange));

        } else if (exercise.machineColor.equals(getContext().getString(R.string.red).toLowerCase())) {
            tvMachineNumber.setBackgroundColor(getContext().getColor(R.color.colorRed));
        }

        return convertView;
    }
}
