package luiz.zapchau.gym101.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Machine {
    public String id;
    public String name;
    public String number;
    public String color;

    private Machine(JSONObject object) {
        try {
            this.id     = object.getString("_id");
            this.name   = object.getString("machinename");
            this.number = object.getString("machinenumber");
            this.color  = object.getString("machinecolor");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Machine> fromJson(JSONArray jsonObjects) {
        ArrayList<Machine> machines = new ArrayList<>();

        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                machines.add(new Machine(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return machines;
    }
}
