package luiz.zapchau.gym101.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luiz.zapchau.gym101.Helper.SQLiteHelper;
import luiz.zapchau.gym101.Model.StringWithTag;
import luiz.zapchau.gym101.R;

public class NewWorkoutActivity extends AppCompatActivity {

    @BindView(R.id.iv_new_workout_close)      ImageView         ivNewWorkoutClose;
    @BindView(R.id.bt_new_workout_save)       Button            btNewWorkoutSave;
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
    @BindView(R.id.spNewWorkoutMachine)       Spinner           spNewWorkoutMachine;
    @BindView(R.id.llSetsRepetitions)         LinearLayout      llSetsRepetitions;
    @BindView(R.id.llTimeDistance)            LinearLayout      llTimeDistance;
    @BindView(R.id.tvNewWorkoutUnit)          TextView          tvNewWorkoutUnit;

    private Calendar     mCalendar;
    private Context      mContext;
    private SQLiteHelper sqLiteHelper;
    private int[]        machineId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        ButterKnife.bind(this);

        mContext     = this;
        sqLiteHelper = new SQLiteHelper(this);
        machineId    = new int[1];
        mCalendar    = new GregorianCalendar();

        mCalendar.setTime(new Date());

        initComponents();
    }

    @Override
    public void onBackPressed() {
        if (checkForDataOnFinish())
            super.onBackPressed();
    }

    private void initComponents() {
        Date mDate     = new Date();

        tieNewWorkoutDate.setText(formatDate(mDate));

        ArrayAdapter<StringWithTag> spAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item_large, sqLiteHelper.selectAllMachineSpinner());

        spAdapter          .setDropDownViewResource(R.layout.spinner_item);
        spNewWorkoutMachine.setAdapter(spAdapter);

        machineId[0] = -1;

        spNewWorkoutMachine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);

                machineId[0] = (Integer) swt.tag;

                checkTreadmill(swt.string);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Blank
            }
        });
    }

    private void checkTreadmill(String machineName) {
        String[] machineNameSplit = machineName.split(" - ");

        if (!machineNameSplit[1].toLowerCase().trim().equals(getResources().getString(R.string.treadmill))) {
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

    @OnClick(R.id.tieNewWorkoutDate)
    public void edtNewWorkoutDateOnClick() {
        tilNewWorkoutDateLayout.setError(null);

        new DatePickerDialog(mContext, R.style.luiz_date_picker_dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(year, monthOfYear, dayOfMonth);
                tieNewWorkoutDate.setText(formatDate(mCalendar.getTime()));
            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.iv_new_workout_close)
    public void ivNewWorkoutCloseOnClick() {
        NewWorkoutActivity.this.onBackPressed();
    }

    @OnClick(R.id.bt_new_workout_save)
    public void btNewWorkoutSaveOnClick() {
        if (!new Date().before(mCalendar.getTime())) {
            //todo save shit
        } else {
            tilNewWorkoutDateLayout.setError(getString(R.string.date_future));
            Toast.makeText(mContext, getResources().getString(R.string.date_future), Toast.LENGTH_LONG).show();
        }
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
}