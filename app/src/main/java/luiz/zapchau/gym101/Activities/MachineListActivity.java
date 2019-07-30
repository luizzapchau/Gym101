package luiz.zapchau.gym101.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.crypto.Mac;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luiz.zapchau.gym101.Adapter.ExerciseAdapter;
import luiz.zapchau.gym101.Adapter.MachineAdapter;
import luiz.zapchau.gym101.Helper.SQLiteHelper;
import luiz.zapchau.gym101.Model.Exercise;
import luiz.zapchau.gym101.Model.Machine;
import luiz.zapchau.gym101.R;

public class MachineListActivity extends AppCompatActivity {

    @BindView(R.id.lvMachines) ListView lvMachines;

    private Context      mContext;
    private SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_list);

        ButterKnife.bind(this);

        mContext     = this;
        sqLiteHelper = new SQLiteHelper(mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadMachinesList();
        lvMachinesListOnItemLongClick();
    }

    private void loadMachinesList() {
        MachineAdapter machineAdapter = new MachineAdapter(this, new ArrayList<Machine>());

        lvMachines    .setAdapter(machineAdapter);
        machineAdapter.addAll    (Machine.fromJson(sqLiteHelper.selectAllMachineList()));
    }

    private void lvMachinesListOnItemLongClick() {
        lvMachines.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView tvMachineId = ButterKnife.findById(view, R.id.tvMachineId);

                new AlertDialog.Builder(mContext, R.style.luiz_dialog)
                        .setMessage(R.string.delete_machine_confirmation)
                        .setPositiveButton(R.string.yes_caps, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!sqLiteHelper.selectExerciseByMachine(Integer.parseInt(tvMachineId.getText().toString()))) {
                                    sqLiteHelper.deleteMachineEntry(Integer.parseInt(tvMachineId.getText().toString()));

                                    Toast.makeText(mContext, getResources().getString(R.string.machine_deleted), Toast.LENGTH_LONG).show();

                                    onResume();
                                } else {
                                    new AlertDialog.Builder(mContext, R.style.luiz_dialog)
                                            .setTitle(R.string.error)
                                            .setMessage(R.string.machine_has_exercise)
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

    @OnClick(R.id.ivMachineClose)
    public void ivMachineCloseOnClick() {
        this.onBackPressed();
    }
}
