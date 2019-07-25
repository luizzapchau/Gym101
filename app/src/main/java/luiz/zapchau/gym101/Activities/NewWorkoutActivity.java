package luiz.zapchau.gym101.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luiz.zapchau.gym101.Helper.SQLiteHelper;
import luiz.zapchau.gym101.Helper.SharedPreferencesHelper;
import luiz.zapchau.gym101.R;

public class NewWorkoutActivity extends AppCompatActivity {

    @BindView(R.id.ivNewWorkoutClose)      ImageView         ivNewWorkoutClose;
    @BindView(R.id.btNewWorkoutSave)       Button            btNewWorkoutSave;
    @BindView(R.id.tilNewWorkoutSetsLayout)   TextInputLayout   tilNewWorkoutSetsLayout;
    @BindView(R.id.tilNewWorkoutWeightLayout) TextInputLayout   tilNewWorkoutWeightLayout;
    @BindView(R.id.tilNewWorkoutSpeedLayout)  TextInputLayout   tilNewWorkoutSpeedLayout;
    @BindView(R.id.tilNewWorkoutDateLayout)   TextInputLayout   tilNewWorkoutDateLayout;
    @BindView(R.id.tieNewWorkoutDate)         TextInputEditText tieNewWorkoutDate;
    @BindView(R.id.tieNewWorkoutWeight)       TextInputEditText tieNewWorkoutWeight;
    @BindView(R.id.tieNewWorkoutSpeed)        TextInputEditText tieNewWorkoutSpeed;
    @BindView(R.id.tieNewWorkoutRepetitions)  TextInputEditText tieNewWorkoutRepetitions;
    @BindView(R.id.tieNewWorkoutSets)         TextInputEditText tieNewWorkoutSets;
    @BindView(R.id.tieNewWorkoutTime)         TextInputEditText tieNewWorkoutTime;
    @BindView(R.id.tieNewWorkoutDistance)     TextInputEditText tieNewWorkoutDistance;
    @BindView(R.id.tvNewWorkoutExercise)      TextView          tvNewWorkoutExercise;
    @BindView(R.id.tvNewWorkoutExerciseId)    TextView          tvNewWorkoutExerciseId;
    @BindView(R.id.llSetsRepetitions)         LinearLayout      llSetsRepetitions;
    @BindView(R.id.llTimeDistance)            LinearLayout      llTimeDistance;
    @BindView(R.id.tvNewWorkoutUnit)          TextView          tvNewWorkoutUnit;

    private Calendar                mCalendar;
    private Context                 mContext;
    private SQLiteHelper            sqLiteHelper;
    private SharedPreferencesHelper spHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        ButterKnife.bind(this);

        mContext     = this;
        sqLiteHelper = new SQLiteHelper(mContext);
        mCalendar    = new GregorianCalendar();
        spHelper     = new SharedPreferencesHelper();

        mCalendar.setTime(new Date());

        tieNewWorkoutDate.setText(formatDate(new Date()));

        resetSP();
    }

    @Override
    public void onBackPressed() {
        if (checkForDataOnFinish())
            super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setExercise();
        checkTreadmill();
    }

    public void checkTreadmill() {
        if (!spHelper.spGetString(mContext, getResources().getString(R.string.sp_machine_name),
                getResources().getString(R.string.select_exercise)).toLowerCase().trim().contains(getResources().getString(R.string.treadmill))) {
            llSetsRepetitions        .setVisibility(View.VISIBLE);
            llTimeDistance           .setVisibility(View.GONE);
            tilNewWorkoutWeightLayout.setVisibility(View.VISIBLE);
            tilNewWorkoutSpeedLayout .setVisibility(View.GONE);
            tvNewWorkoutUnit         .setText(getResources().getString(R.string.kg));

        } else {
            llSetsRepetitions        .setVisibility(View.GONE);
            llTimeDistance           .setVisibility(View.VISIBLE);
            tilNewWorkoutWeightLayout.setVisibility(View.GONE);
            tilNewWorkoutSpeedLayout .setVisibility(View.VISIBLE);
            tvNewWorkoutUnit         .setText(getResources().getString(R.string.km_h));
        }
    }

    @OnClick(R.id.llNewWorkoutExercise)
    public void llNewWorkoutExerciseOnClick() {
        startActivity(new Intent(mContext, ExerciseListActivity.class));
    }

    @OnClick(R.id.tieNewWorkoutDate)
    public void edtNewWorkoutDateOnClick() {
        tilNewWorkoutDateLayout.setError(null);

        Locale.setDefault(getResources().getConfiguration().getLocales().get(0));

        new DatePickerDialog(mContext, R.style.luiz_date_picker_dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(year, monthOfYear, dayOfMonth);
                tieNewWorkoutDate.setText(formatDate(mCalendar.getTime()));
            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.ivNewWorkoutClose)
    public void ivNewWorkoutCloseOnClick() {
        NewWorkoutActivity.this.onBackPressed();
    }

    @OnClick(R.id.btNewWorkoutSave)
    public void btNewWorkoutSaveOnClick() {
        if (checkFields()) {
            if (sqLiteHelper.insertWorkoutData(
                    formatDate(mCalendar.getTime()),
                    tvNewWorkoutExerciseId   .getText().toString().isEmpty() ? -1   : Integer.parseInt  (tvNewWorkoutExerciseId  .getText().toString()),
                    tieNewWorkoutSets        .getText().toString().isEmpty() ? -1   : Integer.parseInt  (tieNewWorkoutSets       .getText().toString()),
                    tieNewWorkoutRepetitions .getText().toString().isEmpty() ? -1   : Integer.parseInt  (tieNewWorkoutRepetitions.getText().toString()),
                    tieNewWorkoutWeight      .getText().toString().isEmpty() ? "-1" :                    tieNewWorkoutWeight     .getText().toString(),
                    tieNewWorkoutTime        .getText().toString().isEmpty() ? "0"  :                    tieNewWorkoutTime       .getText().toString(),
                    tieNewWorkoutDistance    .getText().toString().isEmpty() ? -1   : Float  .parseFloat(tieNewWorkoutDistance   .getText().toString()),
                    tieNewWorkoutSpeed       .getText().toString().isEmpty() ? "-1" :                    tieNewWorkoutSpeed      .getText().toString())) {

                Toast.makeText(mContext, getResources().getString(R.string.workout_save_success), Toast.LENGTH_LONG).show();

                super.onBackPressed();
            } else {
                Toast.makeText(mContext, getResources().getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
            }
        }
    }

    private Boolean checkFields() {
        if (new Date().before(mCalendar.getTime())) {
            tilNewWorkoutDateLayout.setError(getString(R.string.date_future));
            Toast.makeText(mContext, getResources().getString(R.string.date_future), Toast.LENGTH_LONG).show();

            return false;
        }

        if (Integer.parseInt(tvNewWorkoutExerciseId.getText().toString()) == -1) {
            Toast.makeText(mContext, getResources().getString(R.string.no_exercise_selected), Toast.LENGTH_LONG).show();

            return false;
        }

        return true;
    }

    private Boolean checkForDataOnFinish() {
        final Boolean[] canLeave = {false};

        if ((tieNewWorkoutSpeed.getText()   .toString().isEmpty()) && (tieNewWorkoutWeight     .getText().toString().isEmpty()) &&
                (tieNewWorkoutTime.getText().toString().isEmpty()) && (tieNewWorkoutDistance   .getText().toString().isEmpty()) &&
                (tieNewWorkoutSets.getText().toString().isEmpty()) && (tieNewWorkoutRepetitions.getText().toString().isEmpty())) {
            canLeave[0] = true;

        } else {
            new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.luiz_dialog))
                    .setMessage(R.string.data_lost_leave)
                    .setPositiveButton(R.string.yes_caps, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            canLeave[0] = true;

                            NewWorkoutActivity.this.finish();
                        }
                    })
                    .setNegativeButton(R.string.no_caps, null)
                    .show();
        }

        return canLeave[0];
    }

    private String formatDate(Date mDate) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("EEEE, MMMM dd yyyy");

        return mSimpleDateFormat.format(mDate);
    }

    private void resetSP() {
        spHelper.spSetInt   (mContext, getResources().getString(R.string.sp_exercise_id)   , -2);
        spHelper.spSetString(mContext, getResources().getString(R.string.sp_machine_name)  , getResources().getString(R.string.default_machine_name));
        spHelper.spSetString(mContext, getResources().getString(R.string.sp_machine_number), getResources().getString(R.string.default_machine_number));
    }

    private void setExercise() {
        if (spHelper.spGetInt(mContext, getResources().getString(R.string.sp_exercise_id), -2) != -2) {
            tvNewWorkoutExerciseId.setText(String.valueOf(spHelper.spGetInt(mContext, getResources().getString(R.string.sp_exercise_id), -2)));
            tvNewWorkoutExercise.setText(spHelper.spGetString(mContext, getResources().getString(R.string.sp_machine_number), "") +
            " - " + spHelper.spGetString(mContext, getResources().getString(R.string.sp_machine_name), getResources().getString(R.string.error_caps)));
        }
    }
}