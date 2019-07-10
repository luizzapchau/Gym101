package luiz.zapchau.gym101.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import luiz.zapchau.gym101.Adapter.WorkoutAdapter;
import luiz.zapchau.gym101.Controller.MenuController;
import luiz.zapchau.gym101.Helper.SQLiteHelper;
import luiz.zapchau.gym101.Model.StringWithTag;
import luiz.zapchau.gym101.Model.Workout;
import luiz.zapchau.gym101.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lvWorkouts) ListView      lvWorkouts;
    @BindView(R.id.btNew)      SpeedDialView speedDialView;

    private SQLiteHelper sqLiteHelper;
    private Context      mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        sqLiteHelper = new SQLiteHelper(this);
        mContext = this;

        initSpeedDial(savedInstanceState == null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWorkoutList();
    }

    private void loadWorkoutList() {
        addWorkoutToList(sqLiteHelper.selectAllWorkout());
    }

    private void addWorkoutToList(JSONArray workoutList){
        ArrayList<Workout> arrayOfShows = new ArrayList<>();
        WorkoutAdapter     adapter      = new WorkoutAdapter(this, arrayOfShows);
        ArrayList<Workout> newShow      = Workout.fromJson(workoutList);

        lvWorkouts.setAdapter(adapter);
        adapter   .addAll(newShow);
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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //todo add animation
                            startActivity(new Intent(mContext, NewWorkoutActivity.class));
                        } else {
                            startActivity(new Intent(mContext, NewWorkoutActivity.class));
                        }

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

        final TextInputEditText tieNewMachineNumber       = ButterKnife.findById(dialogNewMachine, R.id.tieNewMachineNumber);
        final TextInputEditText tieNewMachineName         = ButterKnife.findById(dialogNewMachine, R.id.tieNewMachineName);
        final TextInputLayout   tilNewMachineNameLayout   = ButterKnife.findById(dialogNewMachine, R.id.tilNewMachineNameLayout);

        btNewMachineSave.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tieNewMachineName.getText().toString().isEmpty()) {
                    sqLiteHelper.insertMachineData(Integer.parseInt(tieNewMachineNumber.getText().toString()),
                            tieNewMachineName.getText().toString(), spNewMachineColor.getSelectedItem().toString().toLowerCase());

                    Toast.makeText(mContext, getResources().getString(R.string.machine_save_success), Toast.LENGTH_LONG).show();
                    dialogNewMachine.dismiss();
                } else {
                    tilNewMachineNameLayout.setError(getString(R.string.machine_name_not_empty));
                    tieNewMachineName.requestFocus();
                    Toast.makeText(mContext, getResources().getString(R.string.machine_name_not_empty), Toast.LENGTH_LONG).show();
                }
            }
        });

        btNewMachineCancel.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNewMachine.dismiss();
            }
        });

        tieNewMachineName.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                tilNewMachineNameLayout.setError(null);
            }
        });

        dialogNewMachine.setCanceledOnTouchOutside(true);
        dialogNewMachine.show();
    }

    private void initNewExerciseDialog() {
        final Dialog dialogNewExercise = new Dialog(this);
        final int[]  machineId         = new int[1];

        dialogNewExercise.setContentView(R.layout.dialog_new_exercise);

        final Spinner               spNewExerciseMachine = ButterKnife.findById(dialogNewExercise, R.id.spExerciseMachine);
        //todo change spinner to show all exercises
        ArrayAdapter<StringWithTag> spAdapter            = new ArrayAdapter<>(mContext, R.layout.spinner_item, sqLiteHelper.selectAllMachineSpinner());

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

        final CheckBox cbNewExerciseSunday    = ButterKnife.findById(dialogNewExercise, R.id.cbNewExerciseSunday);
        final CheckBox cbNewExerciseMonday    = ButterKnife.findById(dialogNewExercise, R.id.cbNewExerciseMonday);
        final CheckBox cbNewExerciseTuesday   = ButterKnife.findById(dialogNewExercise, R.id.cbNewExerciseTuesday);
        final CheckBox cbNewExerciseWednesday = ButterKnife.findById(dialogNewExercise, R.id.cbNewExerciseWednesday);
        final CheckBox cbNewExerciseThursday  = ButterKnife.findById(dialogNewExercise, R.id.cbNewExerciseThursday);
        final CheckBox cbNewExerciseFriday    = ButterKnife.findById(dialogNewExercise, R.id.cbNewExerciseFriday);
        final CheckBox cbNewExerciseSaturday  = ButterKnife.findById(dialogNewExercise, R.id.cbNewExerciseSaturday);

        btNewExerciseSave.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exerciseDays = "|";

                if (cbNewExerciseSunday.isChecked())
                    exerciseDays.concat(getResources().getString(R.string.sunday_db) + "|");

                if (cbNewExerciseMonday.isChecked())
                    exerciseDays.concat(getResources().getString(R.string.monday_db) + "|");

                if (cbNewExerciseTuesday.isChecked())
                    exerciseDays.concat(getResources().getString(R.string.tuesday_db) + "|");

                if (cbNewExerciseWednesday.isChecked())
                    exerciseDays.concat(getResources().getString(R.string.wednesday_db) + "|");

                if (cbNewExerciseThursday.isChecked())
                    exerciseDays.concat(getResources().getString(R.string.thursday_db) + "|");

                if (cbNewExerciseFriday.isChecked())
                    exerciseDays.concat(getResources().getString(R.string.friday_db) + "|");

                if (cbNewExerciseSaturday.isChecked())
                    exerciseDays.concat(getResources().getString(R.string.saturday_db) + "|");

                if (!sqLiteHelper.insertExerciseData(machineId[0], exerciseDays)) {
                    Toast.makeText(mContext, getResources().getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.exercise_save_success), Toast.LENGTH_LONG).show();
                    dialogNewExercise.dismiss();
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
}

