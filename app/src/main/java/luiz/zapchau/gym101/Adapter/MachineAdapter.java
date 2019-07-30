package luiz.zapchau.gym101.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import luiz.zapchau.gym101.Model.Machine;
import luiz.zapchau.gym101.R;

public class MachineAdapter extends ArrayAdapter<Machine> {

    public MachineAdapter(Context mContext, ArrayList<Machine> machines){
        super(mContext, 0, machines);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Machine machine = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_machines, parent, false);

        TextView tvMachineId     = convertView.findViewById(R.id.tvMachineId);
        TextView tvMachineName   = convertView.findViewById(R.id.tvMachineName);
        TextView tvMachineNumber = convertView.findViewById(R.id.tvMachineNumber);

        assert machine != null;
        tvMachineId    .setText(machine.id);
        tvMachineName  .setText(machine.name);
        tvMachineNumber.setText(machine.number);

        if (Integer.parseInt(machine.number) != -1) {
            tvMachineNumber.setText(machine.number);
        } else {
            tvMachineNumber.setText(null);
        }

        if (machine.color.equals(getContext().getString(R.string.blue).toLowerCase())) {
            tvMachineNumber.setBackgroundColor(getContext().getColor(R.color.colorBlue));

        } else if (machine.color.equals(getContext().getString(R.string.green).toLowerCase())) {
            tvMachineNumber.setBackgroundColor(getContext().getColor(R.color.colorGreen));

        } else if (machine.color.equals(getContext().getString(R.string.orange).toLowerCase())) {
            tvMachineNumber.setBackgroundColor(getContext().getColor(R.color.colorOrange));

        } else if (machine.color.equals(getContext().getString(R.string.red).toLowerCase())) {
            tvMachineNumber.setBackgroundColor(getContext().getColor(R.color.colorRed));
        }

        return convertView;
    }
}
