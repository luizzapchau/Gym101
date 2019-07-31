package luiz.zapchau.gym101.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import luiz.zapchau.gym101.Adapter.WorkoutAdapter;
import luiz.zapchau.gym101.Controller.MenuController;
import luiz.zapchau.gym101.Helper.SQLiteHelper;
import luiz.zapchau.gym101.Helper.SharedPreferencesHelper;
import luiz.zapchau.gym101.Model.StringWithTag;
import luiz.zapchau.gym101.Model.Workout;
import luiz.zapchau.gym101.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lvWorkouts)  ListView      lvWorkouts;
    @BindView(R.id.btNew)       SpeedDialView speedDialView;

    private SQLiteHelper sqLiteHelper;
    private Context      mContext;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mContext = this;
        sqLiteHelper = new SQLiteHelper(mContext);
        sharedPreferencesHelper = new SharedPreferencesHelper();

        initSpeedDial(savedInstanceState == null);

        onLvWorkoutsLongClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferencesHelper.spSetString(mContext, getResources().getString(R.string.sp_default_date), getResources().getString(R.string.default_date));

        loadWorkoutList();
    }

    private void loadWorkoutList() {
        WorkoutAdapter workoutAdapter = new WorkoutAdapter(this, new ArrayList<Workout>());

        lvWorkouts    .setAdapter(workoutAdapter);
        workoutAdapter.addAll(Workout.fromJson(sqLiteHelper.selectAllWorkout()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuController menuController = new MenuController();

        return menuController.onOptionsSelected(item, this) || super.onOptionsItemSelected(item);
    }

    private void initSpeedDial(boolean addActionItems) {
        speedDialView.setTransitionName("reveal");

        if (addActionItems) {
            speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_workout, R.drawable.workout)
                         .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorLightGreen, getTheme()))
                         .setLabel(mContext.getResources().getString(R.string.new_workout))
                         .create());

            speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_exercise, R.drawable.exercise)
                         .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorLightCyan, getTheme()))
                         .setLabel(mContext.getResources().getString(R.string.new_exercise))
                         .create());

            speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_machine, R.drawable.machine)
                         .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorLightPink, getTheme()))
                         .setLabel(mContext.getResources().getString(R.string.new_machine))
                         .create());
        }

        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.fab_workout:
                        startActivity(new Intent(mContext, NewWorkoutActivity.class));
                        speedDialView.close();

                        return true;
                    case R.id.fab_exercise:
                        initNewExerciseDialog();
                        speedDialView.close();

                        return true;
                    case R.id.fab_machine:
                        initNewMachineDialog();
                        speedDialView.close();

                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void initNewMachineDialog(){
        final Dialog dialogNewMachine = new Dialog(this);

        dialogNewMachine.setContentView(R.layout.dialog_new_machine);

        final ImageView ivNewMachineColor = ButterKnife.findById(dialogNewMachine, R.id.ivNewMachineColor);
        final Spinner   spNewMachineColor = ButterKnife.findById(dialogNewMachine, R.id.spNewMachineColor);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, new String[]{getString(R.string.blue), getString(R.string.green), getString(R.string.orange), getString(R.string.red)});

        mAdapter         .setDropDownViewResource(R.layout.spinner_item);
        spNewMachineColor.setAdapter(mAdapter);

        spNewMachineColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        ivNewMachineColor.setBackgroundColor(getColor(R.color.colorBlue));
                        break;
                    case 1:
                        ivNewMachineColor.setBackgroundColor(getColor(R.color.colorGreen));
                        break;
                    case 2:
                        ivNewMachineColor.setBackgroundColor(getColor(R.color.colorOrange));
                        break;
                    case 3:
                        ivNewMachineColor.setBackgroundColor(getColor(R.color.colorRed));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Blank
            }
        });

        Button btNewMachineSave   = ButterKnife.findById(dialogNewMachine, R.id.btDialogNewMachineSave);
        Button btNewMachineCancel = ButterKnife.findById(dialogNewMachine, R.id.btDialogNewMachineCancel);

        final TextInputEditText tieNewMachineNumber     = ButterKnife.findById(dialogNewMachine, R.id.tieNewMachineNumber);
        final TextInputEditText tieNewMachineName       = ButterKnife.findById(dialogNewMachine, R.id.tieNewMachineName);
        final TextInputLayout   tilNewMachineNameLayout = ButterKnife.findById(dialogNewMachine, R.id.tilNewMachineNameLayout);

        tieNewMachineNumber.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        btNewMachineSave.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tieNewMachineName.getText().toString().isEmpty()) {
                    sqLiteHelper.insertMachineData(Integer.parseInt(!tieNewMachineNumber.getText().toString().isEmpty() ? tieNewMachineNumber.getText().toString() : "-1"),
                        tieNewMachineName.getText().toString(), spNewMachineColor.getSelectedItem().toString().toLowerCase());

                    Toast.makeText(mContext, getResources().getString(R.string.machine_save_success), Toast.LENGTH_SHORT).show();
                    hideKeyboard(tieNewMachineName);
                    dialogNewMachine.dismiss();
//
                } else {
                    tilNewMachineNameLayout.setError(getString(R.string.machine_name_not_empty));
                    tieNewMachineName.requestFocus();
                }
            }
        });

        btNewMachineCancel.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(tieNewMachineName);
                dialogNewMachine.dismiss();
            }
        });

        tieNewMachineName.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                tilNewMachineNameLayout.setError(null);
            }
        });

        dialogNewMachine.setCanceledOnTouchOutside(false);
        dialogNewMachine.show();
    }

    private void initNewExerciseDialog() {
        final Dialog dialogNewExercise = new Dialog(this);
        final int[]  machineId         = new int[1];

        dialogNewExercise.setContentView(R.layout.dialog_new_exercise);

        List<StringWithTag> spinnerList = sqLiteHelper.selectAllMachineSpinner();

        if (spinnerList.isEmpty())
            spinnerList.add(0, new StringWithTag(getResources().getString(R.string.no_machines), -1));

        final Spinner               spNewExerciseMachine = ButterKnife.findById(dialogNewExercise, R.id.spExerciseMachine);
        ArrayAdapter<StringWithTag> spAdapter            = new ArrayAdapter<>(mContext, R.layout.spinner_item, spinnerList);

        spAdapter           .setDropDownViewResource(R.layout.spinner_item);
        spNewExerciseMachine.setAdapter(spAdapter);

        spNewExerciseMachine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);

                machineId[0] = (Integer) swt.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Blank
            }
        });

        Button btNewExerciseSave   = ButterKnife.findById(dialogNewExercise, R.id.btDialogNewExerciseSave);
        Button btNewExerciseCancel = ButterKnife.findById(dialogNewExercise, R.id.btDialogNewExerciseCancel);

        final CheckBox cbNewExerciseA = ButterKnife.findById(dialogNewExercise, R.id.cbNewExerciseA);
        final CheckBox cbNewExerciseB = ButterKnife.findById(dialogNewExercise, R.id.cbNewExerciseB);
        final CheckBox cbNewExerciseC = ButterKnife.findById(dialogNewExercise, R.id.cbNewExerciseC);
        final CheckBox cbNewExerciseD = ButterKnife.findById(dialogNewExercise, R.id.cbNewExerciseD);

        btNewExerciseSave.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder exerciseDays = new StringBuilder("0|");
                Boolean       hasDays      = false;

                if (cbNewExerciseA.isChecked()) {
                    exerciseDays.append(getString(R.string.a_db) + "|");
                    hasDays = true;
                }

                if (cbNewExerciseB.isChecked()) {
                    exerciseDays.append(getString(R.string.b_db) + "|");
                    hasDays = true;
                }

                if (cbNewExerciseC.isChecked()) {
                    exerciseDays.append(getString(R.string.c_db) + "|");
                    hasDays = true;
                }

                if (cbNewExerciseD.isChecked()) {
                    exerciseDays.append(getString(R.string.d_db) + "|");
                    hasDays = true;
                }

                if (!hasDays) {
                    Toast.makeText(mContext, getResources().getString(R.string.no_day_selected), Toast.LENGTH_LONG).show();
                } else {
                    if (saveExercise(machineId[0], exerciseDays.toString())) {
                        dialogNewExercise.dismiss();
                    }
                }
            }
        });

        btNewExerciseCancel.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNewExercise.dismiss();
            }
        });

        dialogNewExercise.setCanceledOnTouchOutside(true);
        dialogNewExercise.show();
    }

    private Boolean saveExercise(int machineId, String days) {
        if (machineId != -1) {
            if (sqLiteHelper.selectExerciseByMachineDays(machineId, days).length() > 0) {
                Toast.makeText(mContext, getResources().getString(R.string.duplicate_exercise), Toast.LENGTH_SHORT).show();
                return false;

            } else {
                if (!sqLiteHelper.insertExerciseData(machineId, days)) {
                    Toast.makeText(mContext, getResources().getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
                    return false;

                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.exercise_save_success), Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        } else {
            Toast.makeText(mContext, getResources().getString(R.string.no_machine_selected), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void onLvWorkoutsLongClick(){
        lvWorkouts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            final TextView tvWorkoutId = ButterKnife.findById(view, R.id.tvWorkoutId);

            new AlertDialog.Builder(mContext, R.style.luiz_dialog)
                .setMessage(R.string.delete_workout_confirmation)
                .setPositiveButton(R.string.yes_caps, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sqLiteHelper.deleteWorkoutEntry(Integer.parseInt(tvWorkoutId.getText().toString()));

                        Toast.makeText(mContext, getResources().getString(R.string.workout_deleted), Toast.LENGTH_LONG).show();

                        onResume();
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

    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}

