package luiz.zapchau.gym101.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Workout {
    public String id;
    public String date;
    public String sets;
    public String repetitions;
    public String weight;
    public String time;
    public String distance;
    public String speed;
    public String exerciseId;
    public String machineId;
    public String machineName;
    public String machineNumber;
    public String machineColor;

    private Workout(JSONObject object) {
        try {
            this.id            = object.getString("_id");
            this.date          = object.getString("workoutdate");
            this.sets          = object.getString("workoutsets");
            this.repetitions   = object.getString("workoutrepetitions");
            this.weight        = object.getString("workoutweight");
            this.time          = object.getString("workouttime");
            this.distance      = object.getString("workoutdistance");
            this.speed         = object.getString("workoutspeed");
            this.exerciseId    = object.getString("workoutexercise");
            this.machineId     = object.getString("exercisemachine");
            this.machineName   = object.getString("machinename");
            this.machineNumber = object.getString("machinenumber");
            this.machineColor  = object.getString("machinecolor");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Workout> fromJson(JSONArray jsonObjects) {
        ArrayList<Workout> workout = new ArrayList<>();

        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                workout.add(new Workout(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return workout;
    }
}
