package luiz.zapchau.gym101.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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

    @BindView(R.id.lvExercises) ListView lvExercises;

    private Context                 mContext;
    private SQLiteHelper            sqLiteHelper;
    private SharedPreferencesHelper spHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        ButterKnife.bind(this);

        mContext     = this;
        sqLiteHelper = new SQLiteHelper(mContext);
        spHelper     = new SharedPreferencesHelper();

        loadExerciseList();
        onLvExerciseOnClick();
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

    //todo check click on A instead of exercise
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
}
