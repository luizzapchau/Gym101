package luiz.zapchau.gym101.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Exercise {
    public String id;
    public String machineId;
    public String days;
    public String machineName;
    public String machineNumber;
    public String machineColor;

    private Exercise(JSONObject object) {
        try {
            this.id            = object.getString("_id");
            this.machineId     = object.getString("exercisemachine");
            this.days          = object.getString("exercisedays");
            this.machineName   = object.getString("machinename");
            this.machineNumber = object.getString("machinenumber");
            this.machineColor  = object.getString("machinecolor");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Exercise> fromJson(JSONArray jsonObjects) {
        ArrayList<Exercise> exercise = new ArrayList<>();

        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                exercise.add(new Exercise(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return exercise;
    }
}
