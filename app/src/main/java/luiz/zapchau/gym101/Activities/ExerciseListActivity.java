package luiz.zapchau.gym101.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luiz.zapchau.gym101.Adapter.ExerciseAdapter;
import luiz.zapchau.gym101.Helper.SQLiteHelper;
import luiz.zapchau.gym101.Helper.SharedPreferencesHelper;
import luiz.zapchau.gym101.Model.Exercise;
import luiz.zapchau.gym101.R;

public class ExerciseListActivity extends AppCompatActivity {

    @BindView(R.id.lvExercises)         ListView lvExercises;
    @BindView(R.id.tvExerciseListTitle) TextView tvTitle;

    private Context                 mContext;
    private SQLiteHelper            sqLiteHelper;
    private SharedPreferencesHelper spHelper;
    private Boolean                 isNewWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        ButterKnife.bind(this);

        mContext     = this;
        sqLiteHelper = new SQLiteHelper(mContext);
        spHelper     = new SharedPreferencesHelper();
        isNewWorkout = getIntent().getExtras().getBoolean(getString(R.string.is_new_workout));

        spHelper.spSetString(mContext, getResources().getString(R.string.sp_default_exercise_day), "");
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadExerciseList();
        onLvExercisesLongClick();

        if (isNewWorkout) {
            tvTitle.setText(getResources().getString(R.string.select_exercise));
            onLvExerciseOnClick();
        }
    }

    private void loadExerciseList() {
        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this, new ArrayList<Exercise>());

        lvExercises    .setAdapter(exerciseAdapter);
        exerciseAdapter.addAll    (Exercise.fromJson(sqLiteHelper.selectAllExerciseList()));
    }

    @OnClick(R.id.ivExerciseClose)
    public void ivExerciseCloseOnClick() {
        this.onBackPressed();
    }

    //todo fix
    private void onLvExerciseOnClick() {
        lvExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView tvExerciseId    = ButterKnife.findById(view, R.id.tvExerciseId);
                final TextView tvMachineName   = ButterKnife.findById(view, R.id.tvExerciseMachineName);
                final TextView tvMachineNumber = ButterKnife.findById(view, R.id.tvExerciseMachineNumber);

                spHelper.spSetInt   (mContext, getResources().getString(R.string.sp_exercise_id)   , Integer.parseInt(tvExerciseId   .getText().toString()));
                spHelper.spSetString(mContext, getResources().getString(R.string.sp_machine_name)  ,                  tvMachineName  .getText().toString());
                spHelper.spSetString(mContext, getResources().getString(R.string.sp_machine_number),                  tvMachineNumber.getText().toString());

                finish();
            }
        });
    }

    private void onLvExercisesLongClick(){
        lvExercises.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView tvExerciseId = ButterKnife.findById(view, R.id.tvExerciseId);

                    new AlertDialog.Builder(mContext, R.style.luiz_dialog)
                        .setMessage(R.string.delete_exercise_confirmation)
                        .setPositiveButton(R.string.yes_caps, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!sqLiteHelper.selectWorkoutByExercise(Integer.parseInt(tvExerciseId.getText().toString()))) {
                                    sqLiteHelper.deleteExerciseEntry(Integer.parseInt(tvExerciseId.getText().toString()));

                                    Toast.makeText(mContext, getResources().getString(R.string.exercise_deleted), Toast.LENGTH_LONG).show();

                                    onResume();
                                } else {
                                    new AlertDialog.Builder(mContext, R.style.luiz_dialog)
                                        .setTitle(R.string.error)
                                        .setMessage(R.string.exercise_has_workout)
                                        .setPositiveButton(R.string.ok_caps, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //blank
                                            }
                                        })
                                        .show();
                                }
                            }
                        })
                        .setNegativeButton(R.string.no_caps, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //blank
                            }
                        })
                        .show();

                return true;
            }
        });
    }
}
